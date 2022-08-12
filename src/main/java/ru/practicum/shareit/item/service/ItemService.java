package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemBookingDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {

    Item create(long userId, Item item);

    Item update(long userId, long itemId, Item item);

    ItemBookingDto getItemById(long itemId, long userId);

    void delete(long userId, long itemId);

    List<ItemBookingDto> getAllUsersItems(long userId);

    List<Item> search(String text);

    CommentDto createComment(CommentDto commentDto, long itemId, long userId);
}
