package com.bl.bookstore.service;

import com.bl.bookstore.dto.UserDTO;
import com.bl.bookstore.exception.BookStoreException;
import com.bl.bookstore.model.UserData;
import com.bl.bookstore.repository.UserRepository;
import com.bl.bookstore.utility.TokenUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenUtility tokenUtility;

    public UserData addUser(UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.getEmail()) == null) {
            UserData userData = new UserData(userDTO);
            return userRepository.save(userData);
        } else
            throw new BookStoreException("User with email " + userDTO.getEmail() + " is already exists");
    }

    public List<UserData> getAllUsers() {
        if (!userRepository.findAll().isEmpty()) {
            return userRepository.findAll();
        } else
            throw new BookStoreException("Users Table is empty!");
    }

    public Optional<UserData> getUserById(Long id) {
        if (userRepository.existsById(id)) {
            return userRepository.findById(id);
        } else
            throw new BookStoreException("User id " + id + " Not found!");
    }

    public UserData getUserByEmail(String email) {
        if (userRepository.findByEmail(email) != null) {
            return userRepository.findByEmail(email);
        } else
            throw new BookStoreException("User with email " + email + " is Not Found");
    }

    public UserData updateUserByEmail(String email, UserDTO userDTO) {
        if (userRepository.findByEmail(email) != null) {
            long userID = userRepository.findByEmail(email).getUserId();
            UserData userData = new UserData(userDTO);
            userData.setUserId(userID);
            return userRepository.save(userData);
        } else
            throw new BookStoreException("User with email " + email + " is Not Found");
    }

    public String deleteUserById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return "User Deleted of id: " + id;
        } else
            throw new BookStoreException("User id " + id + " Not found!");
    }

    public String login(String email, String password) {
        if (userRepository.findByEmail(email) != null) {
            UserData userData = userRepository.findByEmail(email);
            if (userData.getPassword().equals(password)) {
                userData.setLogin(true);
                String token = tokenUtility.generateToken(userData.getUserId());
                userRepository.save(userData);
                return "Login SuccessFull! token = " + token;
            } else return "Incorrect password";
        } else
            throw new BookStoreException("User with email " + email + " is Not Found");
    }

    public String changePassword(String email, String token, String newPassword) {
        if (userRepository.findByEmail(email) != null) {
            UserData userData = userRepository.findByEmail(email);
            long id = tokenUtility.decodeToken(token);
            if (userData.getUserId().equals(id)) {
                userData.setPassword(newPassword);
                userRepository.save(userData);
                return "Password Changed SuccessFull";
            } else return "Incorrect token";
        } else
            throw new BookStoreException("User with email " + email + " is Not Found");
    }
}
