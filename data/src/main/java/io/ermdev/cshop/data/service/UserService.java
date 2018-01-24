package io.ermdev.cshop.data.service;

import io.ermdev.cshop.commons.util.IdGenerator;
import io.ermdev.cshop.data.entity.Role;
import io.ermdev.cshop.data.entity.User;
import io.ermdev.cshop.data.exception.EntityException;
import io.ermdev.cshop.data.repository.UserRepository;
import io.ermdev.cshop.data.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;

    @Autowired
    public UserService(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    public User findById(Long userId) throws EntityException {
        final User user = userRepository.findById(userId);
        if(user != null) {
            List<Role> roles = userRoleRepository.findRolesByUserId(userId);
            user.setRoles(roles);
            return user;
        } else {
            throw new EntityException("No user found");
        }
    }

    public User findByEmail(String email) throws EntityException {
        final User user = userRepository.findByEmail(email);
        if(user != null) {
            List<Role> roles = userRoleRepository.findRolesByUserId(user.getId());
            user.setRoles(roles);
            return user;
        } else {
            throw new EntityException("No user found");
        }
    }

    public User findByUsername(String username) throws EntityException {
        final User user = userRepository.findByUsername(username);
        if(user != null) {
            List<Role> roles = userRoleRepository.findRolesByUserId(user.getId());
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
                List<Role> roles = userRoleRepository.findRolesByUserId(user.getId());
                user.setRoles(roles);
            });
            return users;
        } else {
            throw new EntityException("No user found");
        }
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
                if(o != null) {
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
                } else {
                    user.setId(null);
                    return save(user);
                }
            }
        } else {
            return user;
        }
    }

    public User delete(Long userId) throws EntityException {
        User user = userRepository.findById(userId);
        if(user != null) {
            final List<Role> roles = userRoleRepository.findRolesByUserId(userId);
            userRepository.delete(user);
            user.setRoles(roles);
            return user;
        } else {
            throw new EntityException("No user found");
        }
    }

    public User delete(User user) throws EntityException {
        User o = userRepository.findById(user.getId());
        if(user != null) {
            final List<Role> roles = userRoleRepository.findRolesByUserId(user.getId());
            o.setRoles(roles);
            userRepository.delete(user);
            return o;
        } else {
            throw new EntityException("No user found");
        }
    }
}
