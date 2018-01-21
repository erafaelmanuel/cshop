package io.ermdev.cshop.commons.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:cshop.properties")
public class CShopProperties {

    @Value("${default.item.image}")
    String defaultImage;

    public String getDefaultImage() {
        return defaultImage;
    }
}
