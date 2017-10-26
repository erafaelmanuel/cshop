package io.ermdev.ecloth.core.config;

import io.ermdev.ecloth.webservice.item.CategoryResource;
import io.ermdev.ecloth.webservice.item.ItemResource;
import io.ermdev.ecloth.webservice.item.TagResource;
import io.ermdev.ecloth.webservice.user.UserResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;

@Component
@ApplicationPath("api")
public class JerseyConfiguration extends ResourceConfig {

    public JerseyConfiguration() {
        register(UserResource.class);
        register(ItemResource.class);
        register(CategoryResource.class);
        register(TagResource.class);
    }

}
