package ru.practicum.shareit.user;

import java.util.Collection;

public interface UserService {
    User get(long userId);

    User update(User user, long userId);

    Collection<User> getAll();

    User create(User user);

    void delete(long userId);
}
