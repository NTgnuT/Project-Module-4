package com.ra.model.dao.user;

import com.ra.model.dao.IGenericDAO;
import com.ra.model.entity.User;

public interface IUsersDAO extends IGenericDAO<User, Integer> {
    void changeStatus(Integer id);
    void changeRole(Integer id);
    User checkEmail(String email);
}
