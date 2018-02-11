package io.ermdev.cshop.core.config;

import io.ermdev.cshop.rest.category.CategoryResource;
import io.ermdev.cshop.rest.image.ImageResource;
import io.ermdev.cshop.rest.item.ItemResource;
import io.ermdev.cshop.rest.role.RoleResource;
import io.ermdev.cshop.rest.token.TokenResource;
import io.ermdev.cshop.rest.user.UserResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;

@Component
@ApplicationPath("api")
public class JerseyConfiguration extends ResourceConfig {

    public JerseyConfiguration() {
        register(UserResource.class);
        register(RoleResource.class);
        register(TokenResource.class);
        register(ItemResource.class);
        register(ImageResource.class);
        register(CategoryResource.class);
        register(TagResource.class);
    }
}
