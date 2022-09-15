package com.bl.bookstore.controller;

import com.bl.bookstore.dto.ResponseDTO;
import com.bl.bookstore.dto.UserDTO;
import com.bl.bookstore.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookstore/user")
public class UserController {
    @Autowired
    IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> registerUser(@RequestBody UserDTO userDTO){
        ResponseDTO responseDTO = new ResponseDTO("User Registration Successful!", userService.addUser(userDTO));
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestParam String email, String password){
        ResponseDTO responseDTO = new ResponseDTO("User Registration Successful!", userService.login(email, password));
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PostMapping("/changepassword")
    public ResponseEntity<ResponseDTO> changePassword(@RequestParam String email, String token, String newPassword){
        ResponseDTO responseDTO = new ResponseDTO("User Registration Successful!", userService.changePassword(email, token, newPassword));
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/getall")
    public ResponseEntity<ResponseDTO> getAllUsers(){
        ResponseDTO responseDTO = new ResponseDTO("GET call successful", userService.getAllUsers());
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ResponseDTO> getUserById(@PathVariable Long id){
        ResponseDTO responseDTO = new ResponseDTO("GET call successful", userService.getUserById(id));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<ResponseDTO> getUserByEmail(@RequestParam String email){
        ResponseDTO responseDTO = new ResponseDTO("GET call successful", userService.getUserByEmail(email));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateUserByEmail(@RequestParam String email, @RequestBody UserDTO userDTO ){
        ResponseDTO responseDTO = new ResponseDTO("User Updated Successfully", userService.updateUserByEmail(email, userDTO));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO> deleteUserById(@PathVariable Long id){
        ResponseDTO responseDTO = new ResponseDTO("User Deleted Successfully", userService.deleteUserById(id));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
