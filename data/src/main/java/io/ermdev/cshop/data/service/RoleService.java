package io.ermdev.cshop.data.service;

import io.ermdev.cshop.data.mapper.RoleRepository;
import io.ermdev.cshop.model.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> findByUserId(Long userId) {
        return roleRepository.findByUserId(userId);
    }
}
