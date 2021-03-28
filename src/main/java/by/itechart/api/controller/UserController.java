package by.itechart.api.controller;

import by.itechart.api.dto.UserDTO;
import by.itechart.api.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController implements UserControllerInfo {

    private final UserServiceImpl userService;

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
    @GetMapping("/current")
    public ResponseEntity<UserDTO> getCurrentUser() {
        return new ResponseEntity<>(HttpStatus.FOUND);
    }

    @PatchMapping("{id}")
    public ResponseEntity<UserDTO> updateUserPartially(@PathVariable Long id, @RequestBody UserDTO user) {
        return new ResponseEntity<>(userService.updateUserPartially(id, user), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userService.update(id, userDTO), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO user) {
        return new ResponseEntity<>(userService.create(user), HttpStatus.CREATED);
    }
}
