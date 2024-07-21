package com.habittracker.tracker.user;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Retrieves a list of all users
    @GetMapping
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    // retrieves a user by their ID
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    // Handles user login based on email and password.
    public String login(String email, String password) {
        Optional<User> optionalUser = userRepository.findUserByEmail(email);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getPass().equals(password)) {
                // User exists and password is correct
                return "SUCCESS";
            } else {
                // Password is incorrect
                return "WRONG_PASSWORD";
            }
        } else {
            // User with the provided email doesn't exist
            return "NO_USER";
        }
    }

    // Adds a new user to the database
    public void addNewUser(User user) {
        Optional<User> userOptional = userRepository.findUserByEmail(user.getEmail());
        if (userOptional.isPresent()) {

            throw new IllegalStateException("Email taken");
        }
        // if email is not present then saves user
        userRepository.save(user);
    }

    // Deletes a user from the data base based on the provided ID
    public void deleteUser(Long userId) {
        boolean exists = userRepository.existsById(userId);
        if (!exists) {
            throw new IllegalStateException(
                    "User with id " + userId + " does not exist");
        }
        userRepository.deleteById(userId);
    }

    // Updates habit data of an existing habit
    @Transactional
    public void updateUser(Long userId, String name, String email, String pass) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalStateException("user with id " + userId + " does not exist"));

        if (name != null && name.length() > 0 && !Objects.equals(user.getName(), name)) {
            user.setName(name);
        }

        if (email != null && email.length() > 0 && !Objects.equals(user.getEmail(), email)) {
            Optional<User> userOptional = userRepository.findUserByEmail(email);
            if (userOptional.isPresent()) {
                throw new IllegalStateException("email taken");
            }
            user.setEmail(email);
        }
        if (pass != null && pass.length() > 0 && !Objects.equals(user.getPass(), pass)) {
            user.setPass(pass);
        }

    }
}
