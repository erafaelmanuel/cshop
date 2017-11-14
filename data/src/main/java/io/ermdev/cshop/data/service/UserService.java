package io.ermdev.cshop.data.service;

import io.ermdev.cshop.data.exception.EmailExistsException;
import io.ermdev.cshop.data.exception.EntityNotFoundException;
import io.ermdev.cshop.data.exception.UnsatisfiedEntityException;
import io.ermdev.cshop.data.mapper.UserRepository;
import io.ermdev.cshop.model.entity.User;
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

    public User findByName(String email) throws EntityNotFoundException {
        User user = userRepository.findByEmail(email);
        if(user != null)
            return user;
        else
            throw new EntityNotFoundException("No user found with email: " + email);
    }

    public List<User> findAll() throws EntityNotFoundException{
        List<User> users = userRepository.findAll();
        if(users != null)
            return users;
        else
            throw new EntityNotFoundException("No user found");
    }

    public User add(User user) throws UnsatisfiedEntityException, EmailExistsException {
        if(user == null)
            throw new UnsatisfiedEntityException("User is null");
        if(user.getName() == null || user.getName().trim().equals(""))
            throw new UnsatisfiedEntityException("Name is required");
        if(user.getEmail() == null || user.getEmail().trim().equals(""))
            throw new UnsatisfiedEntityException("Email is required");
        if(userRepository.findByEmail(user.getEmail()) != null)
            throw new EmailExistsException("Email is already exists");
        if(user.getUsername() == null || user.getUsername().trim().equals(""))
            throw new UnsatisfiedEntityException("Username is required");
        if(user.getPassword() == null || user.getPassword().trim().equals(""))
            throw new UnsatisfiedEntityException("Password is required");
        if(user.getActivated() == null)
            user.setActivated(false);
        userRepository.add(user);
        return user;
    }

    public User updateById(Long userId, User user) throws EntityNotFoundException {
        final User oldUser = findById(userId);
        if(user == null)
            return oldUser;
        user.setId(userId);
        if(user.getName() == null || user.getName().trim().equals(""))
            user.setName(oldUser.getName());
        if(user.getEmail() == null || user.getEmail().trim().equals(""))
            user.setEmail(oldUser.getEmail());
        if(user.getUsername() == null || user.getUsername().trim().equals(""))
            user.setUsername(oldUser.getUsername());
        if(user.getPassword() == null || user.getPassword().trim().equals(""))
            user.setPassword(oldUser.getPassword());
        if(user.getActivated() == null)
            user.setActivated(oldUser.getActivated());
        userRepository.updateById(user);
        return user;
    }

    public User deleteById(Long userId) throws EntityNotFoundException {
        User user = findById(userId);
        userRepository.deleteById(userId);
        return user;
    }
}
