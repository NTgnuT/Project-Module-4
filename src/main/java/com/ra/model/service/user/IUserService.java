package com.ra.model.service.user;

import com.ra.model.entity.User;
import com.ra.model.service.IGenericService;

public interface IUserService extends IGenericService<User, Integer> {
    void changeStatus(Integer id);
    void changeRole(Integer id);
    User login (String email, String password);
    User checkEmail(String email);
    boolean checkPassword(String newPass, User user);
}
