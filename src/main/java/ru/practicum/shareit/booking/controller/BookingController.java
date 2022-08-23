package ru.practicum.shareit.booking.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.Constants;
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
    private BookingService bookingService;
    private BookingMapper bookingMapper;

    @PostMapping
    public BookingUpdDto create(@Valid @RequestBody BookingCreateDto bookingCreateDto,
                                @RequestHeader(Constants.USER_ID_HEADER) long userId) {
        return bookingService.create(bookingMapper.toBookingFromCreatedDto(bookingCreateDto, userId));
    }

    @PatchMapping("/{bookingId}")
    public BookingUpdDto update(@RequestParam boolean approved,
                                @RequestHeader(Constants.USER_ID_HEADER) long userId,
                                @PathVariable long bookingId) {
        return bookingService.update(approved, bookingId, userId);
    }

    @GetMapping("/{bookingId}")
    public BookingUpdDto get(@PathVariable long bookingId,
                             @RequestHeader(Constants.USER_ID_HEADER) long userId) {
        return bookingMapper.toBookingUpdDto(bookingService.getBookingById(bookingId, userId));
    }

    @GetMapping
    public Collection<BookingUpdDto> getAll(@RequestParam(value = "state", required = false, defaultValue = "ALL") StateEnum state,
                                            @RequestHeader(Constants.USER_ID_HEADER) long userId) {

        return bookingService.getAllByBookerId(state, userId).stream()
                .map(bookingMapper::toBookingUpdDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/owner")
    public Collection<BookingUpdDto> getAllWhereOwnerOfItems(@RequestParam(value = "state", required = false, defaultValue = "ALL") StateEnum state,
                                                             @RequestHeader(Constants.USER_ID_HEADER) long ownerId) {
        return bookingService.getAllWhereOwnerOfItems(state, ownerId).stream()
                .map(bookingMapper::toBookingUpdDto)
                .collect(Collectors.toList());
    }


    @DeleteMapping("/{bookingId}")
    public void delete(@PathVariable long bookingId) {
        bookingService.delete(bookingId);
    }
}
