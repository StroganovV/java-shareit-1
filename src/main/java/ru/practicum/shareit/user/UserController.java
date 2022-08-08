package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exceptions.IncorrectUserException;

import javax.validation.Valid;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    UserService userService;

    @GetMapping
    public Collection<UserDto> getAll() {
        return userService.getAll().stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{userId}")
    public UserDto get(@PathVariable long userId) {
        return UserMapper.toUserDto(userService.get(userId));
    }

    @PatchMapping("/{userId}")
    public UserDto update(@RequestBody UserDto userDto,
                          @PathVariable long userId) {
        return UserMapper.toUserDto(userService.update(UserMapper.toUser(userDto), userId));
    }

    @PostMapping
    public UserDto create(@Valid @RequestBody UserDto userDto) {
        if (userDto.getEmail() == null || userDto.getName() == null) {
            throw new IncorrectUserException("Некорректно заполно имя или email");
        }
        return UserMapper.toUserDto(userService.create(UserMapper.toUser(userDto)));
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable long userId) {
        userService.delete(userId);
    }
}
