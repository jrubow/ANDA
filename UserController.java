package com.anda.rest.controller;

import com.anda.rest.model.User;
import com.anda.rest.model.Coordinates;
import com.anda.rest.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * User API processing
 * @author Gleb Bereziuk, @author Jinhoo Yoon
 */

@RestController
@RequestMapping("/user")
public class UserController {

    UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{username}")
    public User getUserDetails(@PathVariable("username") String username) {
        return userService.getUser(username);
    }

    @GetMapping()
    public List<User> getAllUserDetails(String username) {
        return userService.getAllUsers();
    }

    @GetMapping()
    public Coordinates getCoordinates(String username){ return userService.getCoordinates(username); }

    @PostMapping
    public String createUserDetails(@RequestBody User user) {
        userService.createUser(user);
        return "USER CREATED";
    }

    @PutMapping
    public String updateUserDetails(@RequestBody User user) {
        userService.updateUser(user);
        return "USER UPDATED";
    }

    @DeleteMapping("{username}")
    public String deleteUserDetails(@PathVariable String username) {
        userService.deleteUser(username);
        return "USER DELETED";
    }
}
