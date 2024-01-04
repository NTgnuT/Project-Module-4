package com.ra.model.entity;

import com.ra.model.validate.FileNotNull;

import javax.validation.constraints.*;
import java.io.File;

public class User {
    private int userId;
    @NotBlank(message = "User name is not empty !!!")
    private String userName;
    @Email(message = "Incorrect email format.")
    private String email;
    @Size(min = 3, max = 100, message = "Password must be at least 3 characters")
    @NotBlank(message = "Password is not empty !!!")
    private String password;
//    @NotBlank(message = "Avatar is not empty !!!")
    private String avatar;
//    @Pattern(regexp = "/(((\\+|)84)|0)([3|5|7|8|9])+([0-9]{8})\\b/", message = "Incorrect phoneNumber format.")
    private String phoneNumber;
    private String address;
    private boolean status;
    private boolean role;

    public User() {
    }

    public User(int userId, String userName, String email, String password, String avatar, String phoneNumber, String address, boolean status, boolean role) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.status = status;
        this.role = role;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isRole() {
        return role;
    }

    public void setRole(boolean role) {
        this.role = role;
    }
}
