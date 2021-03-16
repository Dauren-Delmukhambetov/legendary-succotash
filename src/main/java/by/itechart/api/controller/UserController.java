package by.itechart.api.controller;

import by.itechart.api.dto.UserDTO;
import by.itechart.api.entity.User;
import by.itechart.api.service.impl.UserServiceImpl;
import by.itechart.api.util.annotation.DTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userService;

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    /**
     * status FOUND here is just for test. It will be changed later
     * this method will get a authentication parameter later, but for now there will be no params
     *
     * @return current user
     */
    @GetMapping("/current")
    public ResponseEntity<User> getCurrentUser() {
        return new ResponseEntity<>(HttpStatus.FOUND);
    }

    @PatchMapping
    public ResponseEntity<User> updateUser(@DTO(UserDTO.class) User user) {
        user.setUpdatedAt(LocalDate.now());
        return new ResponseEntity<>(userService.update(user), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        user.ifPresent(user1 -> {
            user1.setDeletedAt(LocalDate.now());
            userService.update(user1);
        });
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@DTO(UserDTO.class) User user) {
        return new ResponseEntity<>(userService.create(user),HttpStatus.CREATED);
    }
}
