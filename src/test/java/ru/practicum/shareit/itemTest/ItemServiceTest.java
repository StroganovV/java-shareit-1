package ru.practicum.shareit.itemTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.booking.service.BookingMapper;
import ru.practicum.shareit.exceptions.ItemNotFoundException;
import ru.practicum.shareit.exceptions.NotOwnerException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.item.service.ItemServiceImpl;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {
    @Mock
    private ItemRepository repository;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private UserServiceImpl userService;
    @Mock
    private BookingMapper bookingMapper;

    @InjectMocks
    ItemServiceImpl service;

    ItemDto dto = new ItemDto(1L, "name", "desc", true, null);
    Item item = new Item(1L, "name", "desc", true, 1L, null);

    @Test
    void shouldNotNullWhenCreate() {
        when(userService.getUserById(anyLong())).thenReturn(new User());
        when(repository.save(any())).thenReturn(item);

        ItemDto dto1 = service.create(1L, dto);

        assertEquals(dto1.getId(), item.getId());
        assertEquals(dto1.getDescription(), item.getDescription());
        assertEquals(dto1.getName(), item.getName());
        assertEquals(dto1.getAvailable(), item.getAvailable());
    }

    @Test
    void shouldNotNullWhenTryUpdateMethod() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(item));
        when(repository.save(any())).thenReturn(item);

        ItemDto dto1 = service.update(1L, 1L, dto);

        assertNotNull(dto1);
    }

    @Test
    void shouldExceptionWhenTryUpdateNotExistItem() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ItemNotFoundException.class, () -> service.update(1L, 1L, dto));
    }

    @Test
    void shouldExceptionWhenTryUpdateNotOwnerUser() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(item));
        assertThrows(NotOwnerException.class, () -> service.update(2L, 1L, dto));
    }


}
