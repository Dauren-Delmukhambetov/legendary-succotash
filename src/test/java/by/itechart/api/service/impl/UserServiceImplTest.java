package by.itechart.api.service.impl;

import by.itechart.api.dto.CreateUserDTO;
import by.itechart.api.dto.UpdateUserDTO;
import by.itechart.api.dto.UserDTO;
import by.itechart.api.entity.User;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        assertThat(resultDTO.getEmail()).isEqualTo(createUserDTO.getEmail());
        assertThat(resultDTO.getFirstName()).isEqualTo(createUserDTO.getFirstName());
        assertThat(resultDTO.getLastName()).isEqualTo(createUserDTO.getLastName());
        verify(userRepository).save(any());
        //1) initialize argument captor for user entity
        //2) pass argument captor to verify method
        //3) verify arguments values (UserEntity.getEmail()) against input data (CreateUserDTO.getEmail())
        //TODO check how to do with argument captor
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
        when(userRepository.findById(id)).thenReturn(java.util.Optional.of(userEntity));
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
        when(userRepository.findById(id)).thenReturn(java.util.Optional.of(userEntity));
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
        when(userRepository.findByEmail(any())).thenReturn(java.util.Optional.of(userEntity));
        UserDTO expectedDTO = userService.getCurrentUser(authentication);
        assertThat(expectedDTO).isNotNull();
    }
}
