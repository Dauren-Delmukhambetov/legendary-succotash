package by.itechart.api.service;

import by.itechart.api.entity.User;
import java.util.Optional;

public interface UserService extends BasicService<User> {
    Optional<User> findById(Long id);
}
