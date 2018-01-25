package io.ermdev.cshop.data.service;

import io.ermdev.cshop.commons.IdGenerator;
import io.ermdev.cshop.data.entity.Role;
import io.ermdev.cshop.data.repository.RoleRepository;
import io.ermdev.cshop.exception.EntityException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findById(Long roleId) throws EntityException {
       Role role =  roleRepository.findById(roleId);
       if(role != null) {
           return role;
       } else {
           throw new EntityException("No role found");
       }
    }

    public List<Role> findAll() throws EntityException {
        List<Role> roles = roleRepository.findAll();
        if(roles != null) {
            return roles;
        } else {
            throw new EntityException("No role found");
        }
    }

    public Role save(Role role) throws EntityException {
        if(role != null) {
            if (role.getId() == null) {
                if (role.getName() == null || role.getName().trim().isEmpty()) {
                    throw new EntityException("Name is required");
                }
                final Long generatedId = IdGenerator.randomUUID();
                role.setId(generatedId);
                roleRepository.add(role);
                return role;
            } else {
                final Role o = roleRepository.findById(role.getId());
                if(o != null) {
                    if(role.getName() == null || role.getName().trim().isEmpty()) {
                        role.setName(o.getName());
                    }
                    roleRepository.update(role);
                    return role;
                } else {
                    role.setId(null);
                    return save(role);
                }
            }
        } else {
            return role;
        }
    }

    public Role delete(Long roleId) throws EntityException {
        final Role role = roleRepository.findById(roleId);
        if (role != null) {
            roleRepository.delete(role);
            return role;
        } else {
            throw new EntityException("No role found");
        }

    }

    public Role delete(Role role) throws EntityException {
        final Role o = roleRepository.findById(role.getId());
        if (o != null) {
            roleRepository.delete(role);
            return o;
        } else {
            throw new EntityException("No role found");
        }

    }
}
