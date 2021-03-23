package by.itechart.api.controller;

import by.itechart.api.dto.UserDTO;
import by.itechart.api.service.impl.UserServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userService;

    @ApiOperation(value = "Get all users", notes = "This method will return all users from DB")
    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    /**
     * status FOUND here is just for test. It will be changed later
     * this method will get a authentication parameter later, but for now there will be no params
     *
     * @return current user
     */
    @ApiOperation(value = "Get current user", notes = "This method will return current user")
    @GetMapping("/current")
    public ResponseEntity<UserDTO> getCurrentUser() {
        return new ResponseEntity<>(HttpStatus.FOUND);
    }

    @ApiOperation(value = "Update user", notes = "This method is used to update user depending on user's id")
    @PatchMapping("{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO user) {
        return new ResponseEntity<>(userService.update(id, user), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete user", notes = "This method is used to delete user depending on user's id")
    @DeleteMapping("{id}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Create new user", notes = "This method is used to create new user")
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO user) {
        return new ResponseEntity<>(userService.create(user), HttpStatus.CREATED);
    }
}
