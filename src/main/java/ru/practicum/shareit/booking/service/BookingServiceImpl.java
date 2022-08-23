package ru.practicum.shareit.booking.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingUpdDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.model.StateEnum;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exceptions.BookingNotFoundException;
import ru.practicum.shareit.exceptions.ItemNotFoundException;
import ru.practicum.shareit.exceptions.NotAvailableException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {
    private BookingRepository bookingRepository;
    private ItemRepository itemRepository;
    private UserService userService;
    private BookingMapper bookingMapper;

    @Override
    public List<Booking> getAllByBookerId(StateEnum state, long bookerId) {
        userService.getUserById(bookerId);
        LocalDateTime now = LocalDateTime.now();

        switch (state) {
            case PAST:
                return bookingRepository.findByBookerIdAndEndBeforeOrderByCreatedDateDesc(bookerId, now);
            case WAITING:
                return bookingRepository.findByBookerIdAndStatusOrderByCreatedDateDesc(bookerId, BookingStatus.WAITING);
            case FUTURE:
                return bookingRepository.getFutureBookings(bookerId, now);
            case REJECTED:
                return bookingRepository.findByBookerIdAndStatusOrderByCreatedDateDesc(bookerId, BookingStatus.REJECTED);
            case CURRENT:
                return bookingRepository.getCurrentBookings(bookerId, now);
            default:
                return bookingRepository.findByBookerIdOrderByCreatedDateDesc(bookerId);
        }
    }

    @Override
    public List<Booking> getAllWhereOwnerOfItems(StateEnum state, long ownerId) {
        userService.getUserById(ownerId);
        List<Item> items = itemRepository.getAllByOwnerId(ownerId);
        LocalDateTime now = LocalDateTime.now();

        if (items == null || items.size() == 0) {
            return new ArrayList<>();
        }

        switch (state) {
            case PAST:
                return bookingRepository.findAllByItemOwnerIdAndEndBeforeOrderByCreatedDateDesc(ownerId, now);
            case WAITING:
                return bookingRepository.findByItemOwnerIdAndStatusOrderByCreatedDateDesc(ownerId, BookingStatus.WAITING);
            case FUTURE:
                return bookingRepository.findAllByItemOwnerIdAndStartAfterOrderByCreatedDateDesc(ownerId, now);
            case REJECTED:
                return bookingRepository.findByItemOwnerIdAndStatusOrderByCreatedDateDesc(ownerId, BookingStatus.REJECTED);
            case CURRENT:
                return bookingRepository.findAllCurrentBookings(ownerId, now);
            default:
                return bookingRepository.getBookingsByItemOwnerIdOrderByCreatedDateDesc(ownerId);
        }
    }

    @Override
    public Booking getBookingById(long bookingId, long userId) {
        Optional<Booking> booking = bookingRepository.findById(bookingId);

        if (booking.isPresent()) {
            if (booking.get().getBooker().getId() == userId || booking.get().getItem().getOwnerId() == userId) {
                return booking.get();
            } else throw new BookingNotFoundException("Бронирование не найдено");
        } else {
            throw new BookingNotFoundException("Бронирование не найдено");
        }
    }

    @Override
    public BookingUpdDto create(Booking booking) {
        Optional<Item> item = itemRepository.findById(booking.getItem().getId());

        if (item.isPresent() && item.get().getAvailable()) {
            if (!Objects.equals(item.get().getOwnerId(), booking.getBooker().getId())) {
                return bookingMapper.toBookingUpdDto(bookingRepository.save(booking));
            } else {
                throw new ItemNotFoundException("Вы неможете создать бронирование для своей вещи");
            }
        } else {
            throw new NotAvailableException("Вещь недоступна");
        }
    }

    @Override
    public void delete(long bookingId) {
        bookingRepository.deleteById(bookingId);
    }

    @Override
    public BookingUpdDto update(boolean approved, long bookingId, long userId) {
        Booking booking = getBookingById(bookingId, userId);

        if (userId != booking.getItem().getOwnerId()) {
            throw new BookingNotFoundException("Недостаточно прав для просмотра бронирования");
        }

        if (booking.getStatus() == BookingStatus.APPROVED) {
            throw new NotAvailableException("Бронирование уже одобрено");
        }

        if (approved) {
            booking.setStatus(BookingStatus.APPROVED);
        } else {
            booking.setStatus(BookingStatus.REJECTED);
        }

        bookingRepository.save(booking);

        return bookingMapper.toBookingUpdDto(booking);
    }
}
