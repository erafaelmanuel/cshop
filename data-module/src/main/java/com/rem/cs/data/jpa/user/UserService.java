package com.rem.cs.data.jpa.user;

import com.rem.cs.exception.EntityException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(String id) throws EntityException {
        return userRepository.findById(id).orElseThrow(() -> new EntityException("No user found"));
    }

    public User findByEmail(String email) throws EntityException {
        return userRepository.findByEmail(email).orElseThrow(() -> new EntityException("No user found"));
    }

    public long countByEmail(String email) {
        return userRepository.countByEmail(email);
    }

    public void save(User user) {
        userRepository.save(user);
    }
}
