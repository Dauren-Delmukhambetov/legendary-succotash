package by.itechart.api.service.impl;

import by.itechart.api.dto.UserDTO;
import by.itechart.api.entity.User;
import by.itechart.api.repository.UserRepository;
import by.itechart.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserDTO create(UserDTO userDTO) {
        User user = convertToEntity(userDTO);
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
        return userDTO;
    }

    @Override
    public UserDTO update(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid User id"));
        this.modelMapper.map(userDTO, user);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.saveAndFlush(user);
        return userDTO;
    }

    //TODO change this DTO to required DTO
    public UserDTO updateUserPartially(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user id"));
        //TODO create method here to update user partially according to received fields
        this.modelMapper.map(userDTO, user);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.saveAndFlush(user);
        return userDTO;
    }

    @Override
    public void delete(Long id) {
        Optional<User> user = userRepository.findById(id);
        user.ifPresent(currentUser -> {
            currentUser.setDeletedAt(LocalDateTime.now());
            userRepository.saveAndFlush(currentUser);
        });
    }

    @Override
    public List<UserDTO> findAll() {
        List<User> userList = userRepository.findAll();
        return userList.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private User convertToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    private UserDTO convertToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

}
