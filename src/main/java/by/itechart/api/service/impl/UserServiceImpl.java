package by.itechart.api.service.impl;

import by.itechart.api.dto.UserDTO;
import by.itechart.api.entity.User;
import by.itechart.api.repository.UserRepository;
import by.itechart.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserDTO create(UserDTO userDTO) {
        User user = convertToEntity(userDTO);
        user.setCreatedAt(LocalDate.now());
        userRepository.save(user);
        return userDTO;
    }

    @Override
    public UserDTO update(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid User id"));
        this.modelMapper.map(userDTO, user);
        user.setUpdatedAt(LocalDate.now());
        userRepository.saveAndFlush(user);
        return userDTO;
    }

    @Override
    public void delete(Long id) {
        Optional<User> user = userRepository.findById(id);
        user.ifPresent(currentUser -> {
            currentUser.setDeletedAt(LocalDate.now());
            userRepository.saveAndFlush(currentUser);
        });
    }

    @Override
    public List<UserDTO> findAll() {
        List<User> userList = userRepository.findAll();
        return userList.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private User convertToEntity(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        System.out.println(user.toString());
        return user;
    }

    private UserDTO convertToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

}
