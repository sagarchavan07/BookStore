package com.bl.bookstore.service;

import com.bl.bookstore.dto.UserDTO;
import com.bl.bookstore.model.UserData;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    UserData addUser(UserDTO userDTO);
    List<UserData> getAllUsers();
    Optional<UserData> getUserById(Long id);
    UserData getUserByEmail(String email);
    UserData updateUserByEmail(String email, UserDTO userDTO);
    String deleteUserById(Long id);
    String login(String email, String password);
    String changePassword(String email, String token, String newPassword);
}
