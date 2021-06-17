package by.itechart.api.service;

import by.itechart.api.dto.CreateUserDTO;
import by.itechart.api.dto.UpdateUserDTO;
import by.itechart.api.dto.UserDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {
    UserDTO update(Long id, UpdateUserDTO updateUserDTO);

    UserDTO create(CreateUserDTO userDTO);

    void delete(Long id);

    List<UserDTO> findAll(Pageable pageable, String keyword);

    UserDTO getCurrentUser(Authentication authentication);

    List<UserDTO> findActiveUsers(Pageable pageable, String keyword);

}
