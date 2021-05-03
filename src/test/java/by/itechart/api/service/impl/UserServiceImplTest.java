package by.itechart.api.service.impl;

import by.itechart.api.dto.CreateUserDTO;
import by.itechart.api.dto.UpdateUserDTO;
import by.itechart.api.dto.UserDTO;
import by.itechart.api.entity.User;
import by.itechart.api.exception.UserNotAuthenticatedException;
import by.itechart.api.exception.UserNotFoundException;
import by.itechart.api.repository.UserRepository;
import by.itechart.api.repository.UserRoleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;
    @Mock
    UserRoleRepository userRoleRepository;
    @Spy
    ModelMapper modelMapper;
    @Captor
    ArgumentCaptor<User> argumentCaptor;
    @Mock
    Authentication authentication;
    @InjectMocks
    UserServiceImpl userService;


    @Test
    @DisplayName("Create new user")
    void testCreateNewUser() {
        CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setEmail("karen@karen.by");
        createUserDTO.setFirstName("karen");
        createUserDTO.setLastName("bagratyan");
        UserDTO resultDTO = userService.create(createUserDTO);
        verify(userRepository, times(1)).save(argumentCaptor.capture());
        verify(userRepository).save(any());
        User userEntity = argumentCaptor.getValue();
        assertThat(resultDTO.getFirstName()).isEqualTo(userEntity.getFirstName());
        assertThat(resultDTO.getLastName()).isEqualTo(userEntity.getLastName());
        assertThat(resultDTO.getEmail()).isEqualTo(userEntity.getEmail());
    }

    @DisplayName("Update existing user")
    @Test
    void testUpdateExistingUser() {
        Long id = 1L;
        UpdateUserDTO userDTO = new UpdateUserDTO();
        userDTO.setEmail("karen@karen.by");
        userDTO.setFirstName("karen");
        userDTO.setLastName("bagratyan");
        User userEntity = new User();
        when(userRepository.findById(id)).thenReturn(of(userEntity));
        UserDTO resultUserDTO = userService.update(id, userDTO);
        assertThat(resultUserDTO.getEmail()).isEqualTo(userDTO.getEmail());
        assertThat(resultUserDTO.getFirstName()).isEqualTo(userDTO.getFirstName());
        assertThat(resultUserDTO.getLastName()).isEqualTo(userDTO.getLastName());
        verify(userRepository).saveAndFlush(any());
    }

    @Test
    @DisplayName("Delete existing user")
    void testDeleteExistingUser() {
        Long id = 1L;
        User userEntity = new User();
        when(userRepository.findById(id)).thenReturn(of(userEntity));
        userService.delete(id);
        assertThat(userEntity.getDeletedAt()).isNotNull();
    }

    @Test
    @DisplayName("Find all users")
    void testFindAllUsers() {
        List<User> suggesterAllUsers = new ArrayList<>();
        User userEntityOne = new User();
        User userEntityTwo = new User();
        suggesterAllUsers.add(userEntityOne);
        suggesterAllUsers.add(userEntityTwo);
        when(userRepository.findAll()).thenReturn(suggesterAllUsers);
        List<UserDTO> resultAllUsers = userService.findAll();
        assertThat(resultAllUsers).isNotEmpty();
    }

    @Test
    @DisplayName("Get current user")
    void testGetCurrentUser() {
        User userEntity = new User();
        userEntity.setEmail("karen@karen.by");
        userEntity.setFirstName("karen");
        userEntity.setLastName("bagratyan");
        when(userRepository.findByEmail(any())).thenReturn(of(userEntity));
        UserDTO expectedDTO = userService.getCurrentUser(authentication);
        assertThat(expectedDTO).isNotNull();
    }

    @Test
    @DisplayName("Update user but user not found")
    void testUpdateUser_UserNotFound_ExceptionThrown() {
        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            Long notExistedIDForUser = 2L;
            UpdateUserDTO userDTO = new UpdateUserDTO();
            userDTO.setEmail("karen@karen.by");
            userDTO.setFirstName("karen");
            userDTO.setLastName("bagratyan");
            userService.update(notExistedIDForUser, userDTO);
        });
        String expectedMessage = "User with ID 2 is not found";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Delete user but user not found")
    void testDeleteUser_UserNotFound_ExceptionThrown() {
        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            Long notExistedIDForUser = 2L;
            userService.delete(notExistedIDForUser);
        });
        String expectedMessage = "User with ID 2 is not found";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Get current user but not found")
    void testGetCurrentUser_UserNotFoundByEmail_ExceptionThrown() {
        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            userService.getCurrentUser(authentication);
        });
        String expectedMessage = "User with email null is not found";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Get current user but not authenticated")
    void testGetCurrentUser_UserNotAuthenticated_ExceptionThrown() {
        Exception exception = assertThrows(UserNotAuthenticatedException.class, () -> {
            userService.getCurrentUser(null);
        });
        String expectedMessage = "Unauthorized operation provided";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
