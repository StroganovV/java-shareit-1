package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {
    ItemRepository itemInMemoryRepositoryImpl;

    @Override
    public Item create(long userId, Item item) {
        return itemInMemoryRepositoryImpl.create(userId, item);
    }

    @Override
    public Item update(long userId, long itemId, Item item) {
        return itemInMemoryRepositoryImpl.update(userId, itemId, item);
    }

    @Override
    public Item get(long itemId) {
        return itemInMemoryRepositoryImpl.get(itemId);
    }

    @Override
    public void delete(long userId, long itemId) {
        itemInMemoryRepositoryImpl.delete(userId, itemId);
    }

    @Override
    public List<Item> getAllUsersItems(long userId) {
        return itemInMemoryRepositoryImpl.getAllUsersItems(userId);
    }


    @Override
    public List<Item> search(String text) { //Поиск вещи по наличию текста в имени или описании
        List<Item> items = new ArrayList<>();
        for (Item i : itemInMemoryRepositoryImpl.getAll()) {
            if (i.getName().toLowerCase(Locale.ROOT).contains(text) || i.getDescription().toLowerCase().contains(text)) {
                if (i.isAvailable()) {
                    items.add(i);
                }
            }
        }
        return items;
    }
}

