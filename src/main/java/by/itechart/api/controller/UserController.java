package by.itechart.api.controller;

import by.itechart.api.dto.CreateUserDTO;
import by.itechart.api.dto.UpdateUserDTO;
import by.itechart.api.dto.UserDTO;
import by.itechart.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController implements UserControllerInfo {

    private final UserService userService;

    @GetMapping("/all")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/current")
    public ResponseEntity<UserDTO> getCurrentUser(Authentication authentication) {
        return new ResponseEntity<>(userService.getCurrentUser(authentication), HttpStatus.FOUND);
    }

    @RolesAllowed("ROLE_USER")
    @PatchMapping("/current")
    public ResponseEntity<UserDTO> updateCurrentUser(Authentication authentication, @Valid @RequestBody UpdateUserDTO userDTO) {
        UserDTO user = userService.getCurrentUser(authentication);
        return new ResponseEntity<>(userService.update(user.getId(), userDTO), HttpStatus.OK);
    }

    @RolesAllowed("ROLE_ADMIN")
    @PatchMapping("{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UpdateUserDTO userDTO) {
        return new ResponseEntity<>(userService.update(id, userDTO), HttpStatus.OK);
    }

    @RolesAllowed("ROLE_ADMIN")
    @DeleteMapping("{id}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody CreateUserDTO user) {
        return new ResponseEntity<>(userService.create(user), HttpStatus.CREATED);
    }
}
