package io.ermdev.ecloth.data.service;

import io.ermdev.ecloth.data.exception.EntityNotFoundException;
import io.ermdev.ecloth.data.exception.UnsatisfiedEntityException;
import io.ermdev.ecloth.data.mapper.UserRepository;
import io.ermdev.ecloth.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(Long userId) throws EntityNotFoundException {
        User user = userRepository.findById(userId);
        if(user != null)
            return user;
        else
            throw new EntityNotFoundException("No user found with id " + userId);
    }

    public List<User> findAll() throws EntityNotFoundException{
        List<User> users = userRepository.findAll();
        if(users != null)
            return users;
        else
            throw new EntityNotFoundException("No user found");
    }

    public User add(User user) throws UnsatisfiedEntityException {
        if(user == null)
            throw new UnsatisfiedEntityException("User is null");
        if(user.getUsername() == null || user.getUsername().trim().equals(""))
            throw new UnsatisfiedEntityException("Username is required");
        if(user.getPassword() == null || user.getPassword().trim().equals(""))
            throw new UnsatisfiedEntityException("Password is required");
        userRepository.add(user);
        return user;
    }

    public User updateById(Long userId, User user) throws EntityNotFoundException {
        final User oldUser = findById(userId);
        if(user == null)
            return oldUser;
        user.setId(userId);
        if(user.getUsername() == null || user.getUsername().trim().equals(""))
            user.setUsername(oldUser.getUsername());
        if(user.getPassword() == null || user.getPassword().trim().equals(""))
            user.setPassword(oldUser.getPassword());
        userRepository.updateById(user);
        return user;
    }

    public User deleteById(Long userId) throws EntityNotFoundException {
        User user = findById(userId);
        userRepository.deleteById(userId);
        return user;
    }
}
