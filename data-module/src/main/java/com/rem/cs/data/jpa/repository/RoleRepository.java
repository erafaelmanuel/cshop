package com.rem.cs.data.jpa.repository;

import com.rem.cs.data.jpa.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

    Role findByName(String name);
}
