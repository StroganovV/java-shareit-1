//package ru.practicum.shareit.user;
//
//import org.springframework.stereotype.Component;
//import ru.practicum.shareit.exceptions.DuplicateEmailException;
//import ru.practicum.shareit.exceptions.UserNotFoundException;
//
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.Map;
//
//@Component
//public class UserInMemoryRepositoryImpl {
//    private final Map<Long, User> users = new HashMap<>();
//    private final Map<Long, String> emails = new HashMap<>();
//    Long id = 0L;
//
//    public User (long userId) {
//        if (!users.containsKey(userId)) {
//            throw new UserNotFoundException("Пользователь не найден");
//        }
//        return users.get(userId);
//    }
//
//    public Collection<User> getAll() {
//        return users.values();
//    }
//
//    public User create(User user) {
//
//        if (emails.containsValue(user.getEmail())) {
//            throw new DuplicateEmailException("Пользователь с таким email уже был создан");
//        }
//
//        user.setId(++id);
//        users.put(user.getId(), user);
//        emails.put(user.getId(), user.getEmail());
//        return users.get(user.getId());
//    }
//
//    public User update(User user, long userId) {
//        User userUpd = users.get(userId);
//
//        if (user.getEmail() != null) {
//            if (!user.getEmail().equals(emails.get(userId)) && emails.containsValue(user.getEmail())) {
//                throw new DuplicateEmailException("Пользователь с таким email уже создан");
//            }
//            emails.put(userId, user.getEmail());
//            userUpd.setEmail(user.getEmail());
//        }
//
//        if (user.getName() != null) {
//            userUpd.setName(user.getName());
//        }
//
//        users.put(userUpd.getId(), userUpd);
//        return userUpd;
//    }
//
//    public void delete(long userId) {
//        users.remove(userId);
//        emails.remove(userId);
//    }
//}
