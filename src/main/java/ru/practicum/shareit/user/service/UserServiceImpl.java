package ru.practicum.shareit.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.UserNotFoundException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserById(long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UserNotFoundException("Пользователь не найден");
        }
    }

    @Override
    public User update(User user, long userId) {
        Optional<User> userData = userRepository.findById(userId);
        if (userData.isPresent()) {
            User userUpd = userData.get();

            if (user.getEmail() != null) {
                userUpd.setEmail(user.getEmail());
            }

            if (user.getName() != null) {
                userUpd.setName(user.getName());
            }
            userRepository.save(userUpd);
            return userUpd;
        } else {
            throw new UserNotFoundException("Пользователь не найден");
        }
    }

    @Override
    public Collection<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(long userId) {
        userRepository.deleteById(userId);
    }
}
