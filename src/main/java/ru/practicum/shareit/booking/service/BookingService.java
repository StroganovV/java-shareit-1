package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingUpdDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.StateEnum;

import java.util.List;

public interface BookingService {
    List<Booking> getAllByBookerId(StateEnum state, long userId);

    Booking getBookingById(long bookingId, long userId);

    BookingUpdDto create(Booking booking);

    void delete(long bookingId);

    BookingUpdDto update(boolean approved, long bookingId, long userId);

    List<Booking> getAllWhereOwnerOfItems(StateEnum state, long ownerId);
}
