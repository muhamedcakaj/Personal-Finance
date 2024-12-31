package com.example.User;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
public class AdminController {

    private UserService userService;

    public AdminController(UserService userService){
        this.userService=userService;
    }

    @GetMapping(path = "/v1/admin")
    public List<UserEntity> getAllUser(){
        return this.userService.findAll();
    }

    @PostMapping(path="/v1/admin")
    public ResponseEntity<?> createUser(@RequestBody UserEntity user) {
        try {
            UserEntity createdUser = userService.save(user);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping(path = "/v1/admin/{userName}")
    public ResponseEntity<String> updateUserColumns(
            @PathVariable(name="userName") String userName,
            @RequestBody Map<String, String> columnUpdates) {
        try {

            String updatedUser = userService.updateBilancColumns(userName, columnUpdates);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping(path="/v1/admin/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(name = "id") int id) {
        try {
            userService.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with ID: " + id);
        }
    }
}
