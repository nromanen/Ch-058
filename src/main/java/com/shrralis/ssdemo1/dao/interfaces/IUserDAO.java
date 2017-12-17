package com.shrralis.ssdemo1.dao.interfaces;

import com.shrralis.ssdemo1.entity.User;
import com.shrralis.tools.model.SystemDAOException;

import java.util.List;

public interface IUserDAO {
    void add(User user) throws SystemDAOException;

    void delete(User user) throws SystemDAOException;

    void edit(User user) throws SystemDAOException;

    List<User> getAll();

    User getById(Integer id);

    User getByLogin(String login);
}
