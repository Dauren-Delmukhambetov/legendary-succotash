package by.itechart.api.service;

import by.itechart.api.entity.UserRole;

public interface UserRoleService extends BasicService<UserRole>{
    UserRole update(Long id, UserRole userRole);
}
