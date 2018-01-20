package io.ermdev.cshop.core.config;

import io.ermdev.cshop.core.bean.DatabaseMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfiguration implements CommandLineRunner{

    private DatabaseMapper initMapper;

    public DatabaseConfiguration(DatabaseMapper initMapper) {
        this.initMapper = initMapper;
    }

    @Override
    public void run(String... strings) throws Exception {
        initMapper.createUserTable();
        initMapper.createRoleTable();
        initMapper.createUserRoleTable();
        initMapper.createCategoryTable();
        initMapper.createTagTable();
        initMapper.createItemTable();
        initMapper.createItemTagTable();
        initMapper.createRelatedTagTable();
        initMapper.createAttributeTable();
        initMapper.createItemAttributeTable();
        initMapper.createItemImageTable();
        initMapper.createVerificationTokenTable();
    }
}
