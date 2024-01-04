package com.ra.model.service.user;

import com.ra.model.dao.user.UsersDAO_Impl;
import com.ra.model.entity.User;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceIMPL implements IUserService {
    @Autowired
    private UsersDAO_Impl usersDAO;
    @Override
    public List<User> findAll() {
        return usersDAO.findAll();
    }

    @Override
    public boolean saveOrUpdate(User user) {
        return usersDAO.saveOrUpdate(user);
    }

    @Override
    public User findById(Integer id) {
        return usersDAO.findById(id);
    }

    @Override
    public List<User> findByName(String name) {
        return usersDAO.findByName(name);
    }

    @Override
    public List<User> pagination(Integer limit, Integer currentPage) {
        return null;
    }

    @Override
    public void changeStatus(Integer id) {
        usersDAO.changeStatus(id);
    }

    @Override
    public void changeRole(Integer id) {
        usersDAO.changeRole(id);
    }

    @Override
    public User login(String email, String password) {
        User user = usersDAO.checkEmail(email);
        if (user != null) {
            if (BCrypt.checkpw(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    public boolean checkPassword (String newPass, User user) {
//        if (BCrypt.checkpw(newPass, user.getPassword())) {
//            return true;
//        }
//        return false;
        return BCrypt.checkpw(newPass, user.getPassword());

    }

    @Override
    public User checkEmail(String email) {
        return usersDAO.checkEmail(email);
    }
}
