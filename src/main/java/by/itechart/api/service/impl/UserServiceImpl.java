package by.itechart.api.service.impl;

import by.itechart.api.dto.CreateUserDTO;
import by.itechart.api.dto.UpdateUserDTO;
import by.itechart.api.dto.UserDTO;
import by.itechart.api.entity.Role;
import by.itechart.api.entity.User;
import by.itechart.api.entity.UserRole;
import by.itechart.api.exception.UserNotFoundException;
import by.itechart.api.repository.UserRepository;
import by.itechart.api.repository.UserRoleRepository;
import by.itechart.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final UserRoleRepository userRoleRepository;

    @Override
    public UserDTO create(CreateUserDTO createUserDTO) {
        User user = convertToEntity(createUserDTO);
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
        createDefaultUserRole(user);
        UserDTO userDTO = new UserDTO();
        this.modelMapper.map(user, userDTO);
        return userDTO;
    }

    @Override
    public UserDTO update(Long id, UpdateUserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        this.modelMapper.map(userDTO, user);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.saveAndFlush(user);
        UserDTO returnedUser = new UserDTO();
        this.modelMapper.map(user, returnedUser);
        return returnedUser;
    }

    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        user.setDeletedAt(LocalDateTime.now());
        userRepository.saveAndFlush(user);
    }

    @Override
    public List<UserDTO> findAll() {
        List<User> userList = userRepository.findAll();
        return userList.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private User convertToEntity(CreateUserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    private UserDTO convertToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    private void createDefaultUserRole(User user) {
        UserRole userRole = new UserRole();
        userRole.setRole(Role.USER);
        userRole.setUser(user);
        userRoleRepository.save(userRole);
    }
}
