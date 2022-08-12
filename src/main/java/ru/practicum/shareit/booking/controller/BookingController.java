package ru.practicum.shareit.booking.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingUpdDto;
import ru.practicum.shareit.booking.model.StateEnum;
import ru.practicum.shareit.booking.service.BookingMapper;
import ru.practicum.shareit.booking.service.BookingService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/bookings")
@AllArgsConstructor
public class BookingController {
    BookingService bookingService;
    BookingMapper bookingMapper;

    @PostMapping
    public BookingUpdDto create(@Valid @RequestBody BookingCreateDto bookingCreateDto,
                                @RequestHeader("X-Sharer-User-Id") long userId) {
        return bookingService.create(bookingMapper.toBookingFromCreatedDto(bookingCreateDto, userId));
    }

    @PatchMapping("/{bookingId}")
    public BookingUpdDto update(@RequestParam boolean approved,
                                @RequestHeader("X-Sharer-User-Id") long userId,
                                @PathVariable long bookingId) {
        return bookingService.update(approved, bookingId, userId);
    }

    @GetMapping("/{bookingId}")
    public BookingUpdDto get(@PathVariable long bookingId,
                             @RequestHeader("X-Sharer-User-Id") long userId) {
        return bookingMapper.toBookingUpdDto(bookingService.getBookingById(bookingId, userId));
    }

    @GetMapping
    public Collection<BookingUpdDto> getAll(@RequestParam(value = "state", required = false, defaultValue = "ALL") StateEnum state,
                                            @RequestHeader("X-Sharer-User-Id") long userId) {

        return bookingService.getAllByBookerId(state, userId).stream()
                .map(bookingMapper::toBookingUpdDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/owner")
    public Collection<BookingUpdDto> getAllWhereOwnerOfItems(@RequestParam(value = "state", required = false, defaultValue = "ALL") StateEnum state,
                                                             @RequestHeader("X-Sharer-User-Id") long ownerId) {
        return bookingService.getAllWhereOwnerOfItems(state, ownerId).stream()
                .map(bookingMapper::toBookingUpdDto)
                .collect(Collectors.toList());
    }


    @DeleteMapping("/{bookingId}")
    public void delete(@PathVariable long bookingId) {
        bookingService.delete(bookingId);
    }
}
