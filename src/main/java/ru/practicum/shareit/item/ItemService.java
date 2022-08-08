package ru.practicum.shareit.item;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {

    Item create(long userId, Item item);

    Item update(long userId, long itemId, Item item);

    Item get(long itemId);

    void delete(long userId, long itemId);

    List<Item> getAllUsersItems(long userId);

    List<Item> search(String text);
}
