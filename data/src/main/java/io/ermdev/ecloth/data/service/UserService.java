package io.ermdev.ecloth.data.service;

import io.ermdev.ecloth.data.exception.EntityNotFoundException;
import io.ermdev.ecloth.data.repository.UserRepository;
import io.ermdev.ecloth.model.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getById(final long userId) throws EntityNotFoundException {
        User user = userRepository.getById(userId);
        if(user != null)
            return user;
        else
            throw new EntityNotFoundException("No user found with id " + userId);
    }

    public List<User> getAll() throws EntityNotFoundException {
        return userRepository.getAll();
    }

    public User add(final User user) {
        return userRepository.add(user);
    }

    public User updateById(final long userId, final User nUser) throws EntityNotFoundException {
        User user = userRepository.getById(userId);
        if(user==null)
            throw new EntityNotFoundException("No user found with id " + userId);
        else if(nUser==null)
            return user;
        else {
            if(nUser.getUsername()==null || nUser.getUsername().trim().equals(""))
                nUser.setUsername(user.getUsername());

            if(nUser.getPassword()==null || nUser.getPassword().trim().equals(""))
                nUser.setPassword(user.getPassword());

            return userRepository.update(userId, nUser);
        }
    }

    public User deleteById(final long userId) throws EntityNotFoundException {
        User user = userRepository.getById(userId);
        if(user==null)
            throw new EntityNotFoundException("No user found with id " + userId);
        else
            return userRepository.deleteById(userId);
    }
}
