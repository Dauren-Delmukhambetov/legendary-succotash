package by.itechart.api.controller;

import by.itechart.api.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        //TODO return users, not just status
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * status FOUND here is just for test. It will be changed later
     * this method will get a authentication parameter later, but for now there will be no params
     * @return current user
     */
    @GetMapping("/current")
    public ResponseEntity<User> getCurrentUser() {
        //TODO return current user, not just status
        return new ResponseEntity<>(HttpStatus.FOUND);
    }
}
