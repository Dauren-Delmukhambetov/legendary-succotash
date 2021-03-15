package by.itechart.api.controller;

import by.itechart.api.entity.User;
import by.itechart.api.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
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
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.update(user), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.create(user),HttpStatus.CREATED);
    }
}
