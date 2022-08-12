package ru.practicum.shareit.item.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exceptions.IncorrectItemException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemBookingDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemMapper;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/items")
@AllArgsConstructor
public class ItemController {
    ItemService itemServiceImpl;

    @PostMapping
    public ItemDto create(@RequestHeader("X-Sharer-User-Id") long userId,
                          @Valid @RequestBody ItemDto itemDto) {
        if (itemDto.getName() == null || itemDto.getName().isBlank() || itemDto.getDescription() == null || itemDto.getDescription().isBlank()) {
            throw new IncorrectItemException("Имя или описание заполнены неправильно");
        }

        if (itemDto.getAvailable() == null) {
            throw new IncorrectItemException("Не заполнено поле доступности вещи");
        }
        return ItemMapper.toItemDto(itemServiceImpl.create(userId, ItemMapper.toItem(itemDto)));
    }

    @PatchMapping("/{itemId}")
    public ItemDto update(@RequestHeader("X-Sharer-User-Id") long userId,
                          @RequestBody ItemDto itemDto,
                          @PathVariable long itemId) {
        Item item = ItemMapper.toItem(itemDto);
        return ItemMapper.toItemDto(itemServiceImpl.update(userId, itemId, item));
    }

    @GetMapping("/{itemId}")
    public ItemBookingDto get(@PathVariable long itemId,
                              @RequestHeader("X-Sharer-User-Id") long userId) {
        return itemServiceImpl.getItemById(itemId, userId);
    }

    @DeleteMapping("/{itemId}")
    public void delete(@RequestHeader("X-Sharer-User-Id") long userId,
                       @PathVariable long itemId) {
        itemServiceImpl.delete(userId, itemId);
    }

    @GetMapping
    public List<ItemBookingDto> getAllUsersItems(@RequestHeader("X-Sharer-User-Id") long userId) {
        return itemServiceImpl.getAllUsersItems(userId);
    }

    @GetMapping("/search")
    public List<ItemDto> search(@RequestParam String text) {
        if (text == null || text.isBlank()) {
            return new ArrayList<>();
        }
        return itemServiceImpl.search(text.toLowerCase(Locale.ROOT)).stream().map(ItemMapper::toItemDto).collect(Collectors.toList());
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto createComment(@RequestHeader("X-Sharer-User-Id") long userId,
                                    @PathVariable long itemId,
                                    @Valid @RequestBody CommentDto commentDto) {

        return itemServiceImpl.createComment(commentDto, itemId, userId);
    }
}
