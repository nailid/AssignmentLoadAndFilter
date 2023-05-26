package com.backend.assignment.resources;

import com.backend.assignment.entities.User;
import com.backend.assignment.entities.UserResponse;
import com.backend.assignment.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserResource {
    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/users")
    public ResponseEntity<User> addUser(@RequestBody User user){
        if (user.getId()!=null){
            throw new RuntimeException("User already exist!!");
        }
        User savedUser = userService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody User user){
        if (user.getId()==null){
            throw new RuntimeException("User not Found ! please resgister if not yet !!");
        }
        User updatedUser = userService.addUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // api for get all Post by pagination and sorting
    @GetMapping("/users/page")
    public ResponseEntity<UserResponse> getAllPostByPagingSorting
    (@RequestParam(value = "pageNumber", defaultValue = "0",required = false) Integer pageNumber,
     @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
     @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
     @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) throws Exception {
        /*"REST request to get all Post with pagination"*/
        UserResponse userResponse = this.userService.getAllUsersByPagingSorting(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<UserResponse>(userResponse, HttpStatus.OK);
    }
}
