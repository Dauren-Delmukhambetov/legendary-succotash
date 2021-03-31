package by.itechart.api.service;

import by.itechart.api.entity.UserRole;

import java.util.List;

public interface UserRoleService {
    UserRole create(UserRole userRole);

    void delete(Long id);

    List<UserRole> findAll();

    UserRole update(Long id, UserRole userRole);
}
