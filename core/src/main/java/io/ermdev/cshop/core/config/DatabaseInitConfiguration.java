package io.ermdev.cshop.core.config;

import io.ermdev.cshop.data.repository.RoleRepository;
import io.ermdev.cshop.data.repository.TokenRepository;
import io.ermdev.cshop.data.repository.UserRepository;
import io.ermdev.cshop.data.repository.UserRoleRepository;
import io.ermdev.cshop.data.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseInitConfiguration implements CommandLineRunner {

    private UserRepository userRepo;
    private RoleRepository roleRepo;
    private UserRoleRepository userRoleRepo;
    private TokenRepository tokenRepo;

    @Autowired
    public DatabaseInitConfiguration(UserRepository userRepo, RoleRepository roleRepo, UserRoleRepository userRoleRepo,
                                     TokenRepository tokenRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.userRoleRepo = userRoleRepo;
        this.tokenRepo = tokenRepo;
    }

    @Override
    public void run(String... strings) throws Exception {
        userRepo.createTable();
        roleRepo.createTable();
        userRoleRepo.createTable();
        tokenRepo.createTable();
    }
}
