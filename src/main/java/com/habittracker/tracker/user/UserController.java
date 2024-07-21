package com.habittracker.tracker.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/user")
@CrossOrigin
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    // Get method to fetch all users
    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    // Get method to fetch users based on the provided userId
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable Long userId) {
        Optional<User> optionalUser = userService.getUserById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST method to register a new user
    @PostMapping
    public void registerNewUser(@RequestBody User user) {
        userService.addNewUser(user);
    }

    // POST method to handle user login
    @PostMapping(path = "/login")
    public ResponseEntity<String> loginUser(@RequestParam String email, @RequestParam String password) {
        // Call the login method in the service and return appropriate response
        String response = userService.login(email, password);
        return ResponseEntity.ok(response);
    }

    // DELETE method to delete a user by their userId
    @DeleteMapping(path = "{userId}")
    public void deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
    }

    // PUT method to update a user's information
    @PutMapping(path = "{userId}")
    public void updateUser(
            @PathVariable("userId") Long userId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String pass
    ){
        userService.updateUser(userId, name, email, pass);
    }
}
