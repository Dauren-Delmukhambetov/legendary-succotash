package by.itechart.api.service;

import by.itechart.api.dto.CreateUserDTO;
import by.itechart.api.dto.UpdateUserDTO;
import by.itechart.api.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO update(Long id, UpdateUserDTO updateUserDTO);

    UserDTO create(CreateUserDTO userDTO);

    void delete(Long id);

    List<UserDTO> findAll();
}
