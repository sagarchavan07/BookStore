package com.bl.bookstore.model;

import com.bl.bookstore.dto.UserDTO;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "users")
public class UserData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userID;
    private String firstName;
    private String lastName;
    @NotNull
    private String email;
    private String address;
    private LocalDate dob;
    private String password;
    private String token;
    private boolean isAdmin;
    private boolean login;;

    public UserData() {
    }

    public UserData(String firstName, String lastName, String email, String address, LocalDate dob, String token, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.dob = dob;
        this.password = password;
        this.token = token;
    }

    public UserData(UserDTO userDTO) {
        this.firstName = userDTO.getFirstName();
        this.lastName = userDTO.getLastName();
        this.email = userDTO.getEmail();
        this.address = userDTO.getAddress();
        this.dob = userDTO.getDob();
        this.password = userDTO.getPassword();
        this.isAdmin = userDTO.isAdmin();
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "userID=" + userID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", dob=" + dob +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }
}
