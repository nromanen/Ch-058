package com.shrralis.ssdemo1.dao;

import com.shrralis.ssdemo1.dao.base.SystemDAO;
import com.shrralis.ssdemo1.dao.interfaces.IUserDAO;
import com.shrralis.ssdemo1.entity.User;
import com.shrralis.tools.model.SystemDAOException;
import org.hibernate.Session;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class UserDAOImpl extends SystemDAO implements IUserDAO {
    public void add(User user) throws SystemDAOException {
        if (user == null) {
            throw new SystemDAOException("Passed `null` as User!");
        }

        if (!hibernateTemplate
                .findByNamedParam("FROM User WHERE login = :login", "login", user.getLogin())
                .isEmpty()) {
            throw new SystemDAOException(SystemDAOException.Exception.ALREADY);
        }
        throwableInsert(user);
    }

    public void delete(User user) throws SystemDAOException {
        throwableDelete(user);
    }

    public void edit(User user) throws SystemDAOException {
        throwableUpdate(user);
    }

    public List<User> getAll() {
        return (List<User>) hibernateTemplate.find("FROM User");
    }

    public User getById(final Integer id) {
        return hibernateTemplate
                .execute((Session session) -> (User) session
                        .createQuery("FROM User WHERE id = :id")
                        .setParameter("id", id)
                        .uniqueResult());
    }

    public User getByLogin(String login) {
        return hibernateTemplate
                .execute((Session session) -> (User) session
                        .createQuery("FROM User WHERE login = :login")
                        .setParameter("login", login)
                        .uniqueResult());
    }
}
