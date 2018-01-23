package io.ermdev.cshop.data.service;

import io.ermdev.cshop.data.entity.Role;
import io.ermdev.cshop.data.exception.EntityException;
import io.ermdev.cshop.data.repository.UserRoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleService {

    private UserRoleRepository userRoleRepository;

    public UserRoleService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    public List<Role> findRoleByUserId(Long userId) throws EntityException {
        List<Role> roles = userRoleRepository.findRoleByUserId(userId);
        if(roles != null) {
            return roles;
        } else {
            throw new EntityException("No role found");
        }
    }
}
