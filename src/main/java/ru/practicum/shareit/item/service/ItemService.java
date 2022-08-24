package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemBookingDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {

    Item create(long userId, Item item);

    ItemDto update(long userId, long itemId, ItemDto itemDto);

    ItemBookingDto getItemById(long itemId, long userId);

    void delete(long userId, long itemId);

    List<ItemBookingDto> getAllUsersItems(long userId, int from, int size);

    List<Item> search(String text, int from, int size);

    CommentDto createComment(CommentDto commentDto, long itemId, long userId);
}
