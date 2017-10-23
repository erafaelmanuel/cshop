package io.ermdev.ecloth.data.repository;

import io.ermdev.ecloth.model.entity.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {

    private SessionFactory sessionFactory;

    @Autowired
    public UserRepository(SessionFactory sessionFactory) {
        this.sessionFactory=sessionFactory;
    }

    public User getById(final long userId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            User user = session.get(User.class, userId);
            session.getTransaction().commit();
            session.close();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            session.close();
            return null;
        }
    }

    public List<User> getAll() {
        List<User> userList = new ArrayList<>();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            final Query query = session.createQuery("from User");
            for(Object object : query.list()) {
                userList.add((User) object);
            }
            session.getTransaction().commit();
            session.close();
            return userList;
        } catch (Exception e) {
            e.printStackTrace();
            session.close();
            return userList;
        }
    }

    public User add(final User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            session.save(user);
            session.getTransaction().commit();
            session.close();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            session.close();
            return null;
        }
    }

    public User update(final long userId, final User nUser) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            User user = session.get(User.class, userId);
            user.setUsername(nUser.getUsername());
            user.setPassword(nUser.getPassword());

            session.getTransaction().commit();
            session.close();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            session.close();
            return null;
        }
    }

    public User deleteById(final long userId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            User user = session.get(User.class, userId);

            session.delete(user);
            session.getTransaction().commit();
            session.close();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            session.close();
            return null;
        }
    }

}
