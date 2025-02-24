package com.anda.rest.controller;

import com.anda.rest.model.User;
import com.anda.rest.model.Coordinates;
import com.anda.rest.model.WeatherEvent;
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
    public User getUserDetails(String key, @PathVariable("username") String username) {
        return userService.getUser(key, username);
    }

    @GetMapping()
    public List<User> getAllUserDetails(String username) {
        return userService.getAllUsers();
    }

    @GetMapping()
    public Coordinates getCoordinates(String key, String username){ return userService.getCoordinates(key, username); }

    @GetMapping()
    public WeatherEvent getWeatherEvent(Integer weatherId) {
        return userService.getWeatherEvent(weatherId);
    }

    @PostMapping
    public String createUserDetails(@RequestBody User user) {
        userService.createUser(user);
        return "USER CREATED";
    }


    @PutMapping
    public String updateUserDetails(String key, String username, @RequestBody User user) {
        userService.updateUser(key, username, user);
        return "USER UPDATED";
    }

    @DeleteMapping("{username}")
    public String deleteUserDetails(String key, @PathVariable String username) {
        userService.deleteUser(key, username);
        return "USER DELETED";
    }
}
