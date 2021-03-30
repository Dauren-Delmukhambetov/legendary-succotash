package by.itechart.api.service;

import by.itechart.api.dto.UpdateUserDTO;
import by.itechart.api.dto.UserDTO;

public interface UserService extends BasicService<UserDTO> {
    UserDTO update(Long id, UpdateUserDTO updateUserDTO);
}
