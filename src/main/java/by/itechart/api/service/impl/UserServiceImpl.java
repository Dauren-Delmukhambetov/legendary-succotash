package by.itechart.api.service.impl;

import by.itechart.api.dto.CreateUserDTO;
import by.itechart.api.dto.UpdateUserDTO;
import by.itechart.api.dto.UserDTO;
import by.itechart.api.entity.Role;
import by.itechart.api.entity.User;
import by.itechart.api.entity.UserRole;
import by.itechart.api.exception.UserEmailDuplicationException;
import by.itechart.api.exception.UserNotAuthenticatedException;
import by.itechart.api.exception.UserNotFoundException;
import by.itechart.api.repository.UserRepository;
import by.itechart.api.repository.UserRoleRepository;
import by.itechart.api.service.UserService;
import by.itechart.api.util.specification.UserSpecification;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO create(CreateUserDTO createUserDTO) {
        var fetchedUserWithThisEmail = userRepository.findByEmail(createUserDTO.getEmail());
        fetchedUserWithThisEmail.ifPresent(user -> {
            throw new UserEmailDuplicationException(createUserDTO.getEmail());
        });
        var user = convertToEntity(createUserDTO);
        user.setCreatedAt(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(createUserDTO.getPassword()));
        userRepository.save(user);
        createDefaultUserRole(user);
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTO update(Long id, UpdateUserDTO userDTO) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        this.modelMapper.map(userDTO, user);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.saveAndFlush(user);
        var returnedUser = new UserDTO();
        this.modelMapper.map(user, returnedUser);
        return returnedUser;
    }

    @Override
    public void delete(Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        user.setDeletedAt(LocalDateTime.now());
        userRepository.saveAndFlush(user);
    }

    @Override
    public List<UserDTO> findAll(Pageable pageable, String keyword) {
        var userSpecification = new UserSpecification(keyword);
        Page<User> userPage = userRepository.findAll(userSpecification, pageable);
        return userPage.stream().map(this::convertToDTO).collect(Collectors.toList());

    }

    @Override
    public List<UserDTO> findAllActiveUsers(Pageable pageable, String keyword) {
        var userSpecification = new UserSpecification(keyword, true);
        Page<User> userPage = userRepository.findAll(userSpecification, pageable);
        return userPage.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public UserDTO getCurrentUser(Authentication authentication) {
        if (authentication == null) {
            throw new UserNotAuthenticatedException("Unauthorized operation provided");
        }
        Optional<User> user = userRepository.findByEmail(authentication.getName());
        if (user.isEmpty()) {
            throw new UserNotFoundException(format("User with email %s is not found", authentication.getName()));
        }
        return convertToDTO(user.get());
    }

    private User convertToEntity(CreateUserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    private UserDTO convertToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    private void createDefaultUserRole(User user) {
        var userRole = new UserRole();
        userRole.setRole(Role.USER);
        userRole.setUser(user);
        userRoleRepository.save(userRole);
    }
}
