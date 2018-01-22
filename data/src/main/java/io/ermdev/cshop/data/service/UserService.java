package io.ermdev.cshop.data.service;

import io.ermdev.cshop.commons.util.IdGenerator;
import io.ermdev.cshop.data.entity.Role;
import io.ermdev.cshop.data.entity.User;
import io.ermdev.cshop.data.exception.EntityException;
import io.ermdev.cshop.data.repository.RoleRepository;
import io.ermdev.cshop.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public User findById(Long userId) throws EntityException {
        final User user = userRepository.findById(userId);
        if(user != null) {
            List<Role> roles = roleRepository.findByUserId(userId);
            user.setRoles(roles);
            return user;
        } else {
            throw new EntityException("No user found");
        }
    }

    public User findByEmail(String email) throws EntityException {
        final User user = userRepository.findByEmail(email);
        if(user != null) {
            List<Role> roles = roleRepository.findByUserId(user.getId());
            user.setRoles(roles);
            return user;
        } else {
            throw new EntityException("No user found");
        }
    }

    public User findByUsername(String username) throws EntityException {
        final User user = userRepository.findByEmail(username);
        if(user != null) {
            List<Role> roles = roleRepository.findByUserId(user.getId());
            user.setRoles(roles);
            return user;
        } else {
            throw new EntityException("No user found");
        }
    }

    public List<User> findAll() throws EntityException{
        final List<User> users = userRepository.findAll();
        if(users != null) {
            users.parallelStream().forEach(user -> {
                List<Role> roles = roleRepository.findByUserId(user.getId());
                user.setRoles(roles);
            });
            return users;
        } else
            throw new EntityException("No user found");
    }

    public User save(User user) throws EntityException {
        if(user != null) {
            if(user.getId() == null) {
                if(user.getName() == null || user.getName().trim().isEmpty()) {
                    throw new EntityException("Name is required");
                }
                if(user.getEmail() == null || user.getEmail().trim().isEmpty()) {
                    throw new EntityException("Email is required");
                }
                if(user.getUsername() == null || user.getUsername().trim().isEmpty()) {
                    throw new EntityException("Username is required");
                }
                if(user.getPassword() == null || user.getPassword().trim().isEmpty()) {
                    throw new EntityException("Password is required");
                }
                final long generatedId = IdGenerator.randomUUID();
                user.setId(generatedId);
                userRepository.add(user);
                return user;
            } else {
                User o = userRepository.findById(user.getId());
                if(o == null) {
                    user.setId(null);
                    return save(user);
                } else {
                    if(user.getName() == null || user.getName().trim().isEmpty()) {
                        user.setName(o.getName());
                    }
                    if(user.getEmail() == null || user.getEmail().trim().isEmpty()) {
                        user.setEmail(o.getEmail());
                    }
                    if(user.getUsername() == null || user.getUsername().trim().isEmpty()) {
                        user.setUsername(o.getUsername());
                    }
                    if(user.getPassword() == null || user.getPassword().trim().isEmpty()) {
                        user.setPassword(o.getPassword());
                    }
                    if(user.getEnabled() == null) {
                        user.setEnabled(o.getEnabled());
                    }
                    userRepository.update(user);
                    return user;
                }
            }
        } else {
            return user;
        }
    }

    public User delete(Long userId) {
        try {
            final User user = findById(userId);
            final List<Role> roles = roleRepository.findByUserId(userId);
            userRepository.delete(user);
            user.setRoles(roles);
            return user;
        } catch (EntityException e) {
            return null;
        }
    }
}
