package com.anda.rest.service.impl;

import com.anda.rest.model.User;
import com.anda.rest.model.Coordinates;
import com.anda.rest.repository.UserRepository;
import com.anda.rest.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation for User services
 * @author Gleb Bereziuk (gl3bert)
 */

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String createUser(User user) {
        // TODO add necessary logic here
        userRepository.save(user);
        return "USER ADDED TO DATABASE";
    }

    @Override
    public String updateUser(User user) {
        // TODO add necessary logic here
        userRepository.save(user);
        return "USER UPDATED IN DATABASE";
    }

    @Override
    public String deleteUser(String username) {
        // TODO add necessary logic here
        userRepository.deleteById(username);
        return "USER DELETED FROM DATABASE";
    }

    @Override
    public User getUser(String username) {
        // TODO add necessary logic here
        return userRepository.findById(username).get();
    }

    @Override
    public List<User> getAllUsers() {
        // TODO add necessary logic here
        return userRepository.findAll();
    }

    public Coordinates getCoordinates(String username) {
        return userRepository.findById(username).get().getCoords();
    }
}
