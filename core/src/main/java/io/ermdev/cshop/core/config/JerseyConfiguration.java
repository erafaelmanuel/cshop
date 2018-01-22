package io.ermdev.cshop.core.config;

import io.ermdev.cshop.webservice.item.CategoryResource;
import io.ermdev.cshop.webservice.item.ItemResource;
import io.ermdev.cshop.webservice.item.TagResource;
import io.ermdev.cshop.webservice.user.UserApi;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;

@Component
@ApplicationPath("api")
public class JerseyConfiguration extends ResourceConfig {

    public JerseyConfiguration() {
        register(UserApi.class);
        register(ItemResource.class);
        register(CategoryResource.class);
        register(TagResource.class);
    }
}
