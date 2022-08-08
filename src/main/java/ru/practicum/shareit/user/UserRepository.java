package ru.practicum.shareit.user;

import java.util.Collection;

public interface UserRepository {

    User get(long userId);

    Collection<User> getAll();

    User create(User user);

    User update(User user, long userId);

    void delete(long userId);
}
