package by.itechart.api.service;

import by.itechart.api.dto.UpdateUserDTO;
import by.itechart.api.dto.UserDTO;

public interface UserService extends BasicService<UserDTO> {
    UpdateUserDTO update(Long id, UpdateUserDTO updateUserDTO);
}
