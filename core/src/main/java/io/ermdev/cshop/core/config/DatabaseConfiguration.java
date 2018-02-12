package io.ermdev.cshop.core.config;

import io.ermdev.cshop.data.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfiguration implements CommandLineRunner {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private UserRoleRepository userRoleRepository;
    private TokenRepository tokenRepository;
    private TokenUserRepository tokenUserRepository;
    private ItemRepository itemRepository;
    private ImageRepository imageRepository;
    private ItemImageRepository imageItemRepository;
    private TagRepository tagRepository;
    private CategoryRepository categoryRepository;

    @Override
    public void run(String... strings) {
        userRepository.createTable();
        roleRepository.createTable();
        userRoleRepository.createTable();
        tokenRepository.createTable();
        tokenUserRepository.createTable();
        itemRepository.createTable();
        imageRepository.createTable();
        imageItemRepository.createTable();
        tagRepository.createTable();
        categoryRepository.createTable();
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setUserRoleRepository(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Autowired
    public void setTokenRepository(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Autowired
    public void setTokenUserRepository(TokenUserRepository tokenUserRepository) {
        this.tokenUserRepository = tokenUserRepository;
    }

    @Autowired
    public void setItemRepository(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Autowired
    public void setImageRepository(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Autowired
    public void setImageItemRepository(ItemImageRepository imageItemRepository) {
        this.imageItemRepository = imageItemRepository;
    }

    @Autowired
    public void setTagRepository(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
}
