package com.SOA.service;

import com.SOA.model.User;
import com.SOA.dao.UserDAO;
import java.util.List;

public class UserService {
    private UserDAO userDao = new UserDAO();

    public List<User> getAll() {
        return userDao.getAllUsers();
    }

    public boolean save(User user) {
        return userDao.saveUser(user);
    }

    public boolean update(User user) {
        return userDao.updateUser(user);
       }

    public boolean delete(User user) {
        return userDao.deleteUser(user);
       }
}

