package io.ermdev.cshop.typeconverter;

import io.ermdev.cshop.data.entity.User;
import io.ermdev.cshop.data.service.UserService;
import io.ermdev.cshop.exception.EntityException;
import io.ermdev.mapfierj.TypeConverter;
import io.ermdev.mapfierj.TypeConverterAdapter;
import io.ermdev.mapfierj.TypeException;
import org.springframework.stereotype.Component;

@Component
@TypeConverter
public class UserConverter extends TypeConverterAdapter<Long, User>{

    private UserService userService;

    public UserConverter(UserService userService){
        this.userService = userService;
    }

    @Override
    public User convertTo(Long id) throws TypeException {
       try {
           return userService.findById(id);
       } catch (EntityException e) {
           return null;
       }
    }

    @Override
    public Long convertFrom(User user) throws TypeException {
        try {
            return user.getId();
        } catch (NullPointerException e) {
            return null;
        }
    }
}
