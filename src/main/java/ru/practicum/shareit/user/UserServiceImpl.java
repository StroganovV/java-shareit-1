package ru.practicum.shareit.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserServiceImpl implements UserService {
    UserRepository userInMemoryRepositoryImpl;

    @Autowired
    public UserServiceImpl(UserInMemoryRepositoryImpl userInMemoryRepositoryImpl) {
        this.userInMemoryRepositoryImpl = userInMemoryRepositoryImpl;
    }

    @Override
    public User get(long userId) {
        return userInMemoryRepositoryImpl.get(userId);
    }

    @Override
    public User update(User user, long userId) {
        return userInMemoryRepositoryImpl.update(user, userId);
    }

    @Override
    public Collection<User> getAll() {
        return userInMemoryRepositoryImpl.getAll();
    }

    @Override
    public User create(User user) {
        return userInMemoryRepositoryImpl.create(user);
    }

    @Override
    public void delete(long userId) {
        userInMemoryRepositoryImpl.delete(userId);
    }
}
