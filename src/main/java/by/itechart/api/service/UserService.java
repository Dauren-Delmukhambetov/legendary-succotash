package by.itechart.api.service;

import by.itechart.api.dto.UserDTO;

public interface UserService extends BasicService<UserDTO> {
    UserDTO updateUserPartially(Long id, UserDTO userDTO);
}
