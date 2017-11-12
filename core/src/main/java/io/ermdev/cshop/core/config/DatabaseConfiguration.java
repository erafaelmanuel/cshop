package io.ermdev.cshop.core.config;

import io.ermdev.cshop.data.mapper.InitMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfiguration implements CommandLineRunner{

    private InitMapper initMapper;

    public DatabaseConfiguration(InitMapper initMapper) {
        this.initMapper = initMapper;
    }

    @Override
    public void run(String... strings) throws Exception {
        initMapper.createUserTable();
        initMapper.createCategoryTable();
        initMapper.createTagTable();
        initMapper.createItemTable();
        initMapper.createItemTagTable();
        initMapper.createRelatedTagTable();
        initMapper.createAttributeTable();
        initMapper.createItemAttributeTable();
        initMapper.createItemImageTable();
    }
}
