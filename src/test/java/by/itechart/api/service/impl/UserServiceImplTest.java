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

import java.util.List;

import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
    ArgumentCaptor<User> userArgumentCaptor;
    @Mock
    Authentication authentication;
    @InjectMocks
    UserServiceImpl userService;

    private static final Long EXISTING_USER_ID = 1L;
    private static final Long NOT_EXISTED_ID_FOR_USER = 2L;


    @Test
    @DisplayName("Create new user")
    void testCreateNewUser() {
        CreateUserDTO createUserDTO = buildCreateUserDTO();
        UserDTO resultDTO = userService.create(createUserDTO);
        verify(userRepository, times(1)).save(userArgumentCaptor.capture());
        User userEntity = userArgumentCaptor.getValue();
        assertThat(resultDTO.getFirstName()).isEqualTo(userEntity.getFirstName());
        assertThat(resultDTO.getLastName()).isEqualTo(userEntity.getLastName());
        assertThat(resultDTO.getEmail()).isEqualTo(userEntity.getEmail());
    }

    @DisplayName("Update existing user")
    @Test
    void testUpdateExistingUser() {
        UpdateUserDTO userDTO = buildUpdateUserDTO();
        User userEntity = new User();
        when(userRepository.findById(EXISTING_USER_ID)).thenReturn(of(userEntity));
        UserDTO resultUserDTO = userService.update(EXISTING_USER_ID, userDTO);
        assertThat(resultUserDTO.getEmail()).isEqualTo(userDTO.getEmail());
        assertThat(resultUserDTO.getFirstName()).isEqualTo(userDTO.getFirstName());
        assertThat(resultUserDTO.getLastName()).isEqualTo(userDTO.getLastName());
        verify(userRepository).saveAndFlush(any());
    }

    @Test
    @DisplayName("Delete existing user")
    void testDeleteExistingUser() {
        User userEntity = new User();
        when(userRepository.findById(EXISTING_USER_ID)).thenReturn(of(userEntity));
        userService.delete(EXISTING_USER_ID);
        assertThat(userEntity.getDeletedAt()).isNotNull();
    }

    @Test
    @DisplayName("Find all users")
    void testFindAllUsers() {
        List<User> suggestedAllUsers = List.of(new User(), new User());
        when(userRepository.findAll()).thenReturn(suggestedAllUsers);
        List<UserDTO> resultAllUsers = userService.findAll();
        assertThat(resultAllUsers).isNotEmpty().size().isEqualTo(2);
    }

    @Test
    @DisplayName("Get current user")
    void testGetCurrentUser() {
        User userEntity = buildUserEntity();
        when(userRepository.findByEmail(any())).thenReturn(of(userEntity));
        UserDTO expectedDTO = userService.getCurrentUser(authentication);
        assertThat(expectedDTO).isNotNull();
    }

    @Test
    @DisplayName("Update user but user not found")
    void testUpdateUser_UserNotFound_ExceptionThrown() {
        UpdateUserDTO userDTO = buildUpdateUserDTO();
        assertThatThrownBy(() -> userService.update(NOT_EXISTED_ID_FOR_USER, userDTO))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User with ID 2 is not found");
    }

    @Test
    @DisplayName("Delete user but user not found")
    void testDeleteUser_UserNotFound_ExceptionThrown() {
        assertThatThrownBy(() -> userService.delete(NOT_EXISTED_ID_FOR_USER))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User with ID 2 is not found");
    }

    @Test
    @DisplayName("Get current user but not found")
    void testGetCurrentUser_UserNotFoundByEmail_ExceptionThrown() {
        assertThatThrownBy(() -> userService.getCurrentUser(authentication))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User with email null is not found");
    }

    @Test
    @DisplayName("Get current user but not authenticated")
    void testGetCurrentUser_UserNotAuthenticated_ExceptionThrown() {
        assertThatThrownBy(() -> userService.getCurrentUser(null))
                .isInstanceOf(UserNotAuthenticatedException.class)
                .hasMessageContaining("Unauthorized operation provided");
    }

    private CreateUserDTO buildCreateUserDTO() {
        CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setEmail("karen@karen.by");
        createUserDTO.setFirstName("karen");
        createUserDTO.setLastName("bagratyan");
        return createUserDTO;
    }

    private User buildUserEntity() {
        User userEntity = new User();
        userEntity.setEmail("karen@karen.by");
        userEntity.setFirstName("karen");
        userEntity.setLastName("bagratyan");
        return userEntity;
    }


    private UpdateUserDTO buildUpdateUserDTO() {
        UpdateUserDTO userDTO = new UpdateUserDTO();
        userDTO.setEmail("karen@karen.by");
        userDTO.setFirstName("karen");
        userDTO.setLastName("bagratyan");
        return userDTO;
    }
}
