package vn.hoidanit.jobhunter.controller;

import org.springframework.web.bind.annotation.RestController;

import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.service.UserService;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public User createNewUser(@RequestBody User newUser) {

        User user = this.userService.handleCreateUser(newUser);

        return user;
    }

    @GetMapping("/user")
    public List<User> getAllUser() {

        return this.userService.fetchAllUser();
    }

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable("id") long id) {

        return this.userService.fetchUserById(id);
    }

    @PutMapping("/user")
    public User updateUser(@RequestBody User user) {

        User userUpdate = this.userService.handleUpdateUser(user);

        return userUpdate;
    }

    @DeleteMapping("/user/{id}")
    public String deleteUser(@PathVariable("id") long id) {

        this.userService.handleDeleteUser(id);

        return "delete user success";
    }

}
