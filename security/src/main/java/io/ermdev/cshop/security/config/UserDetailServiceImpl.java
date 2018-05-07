package io.ermdev.cshop.security.config;

import io.ermdev.cshop.data.entity.Role;
import io.ermdev.cshop.data.entity.User;
import io.ermdev.cshop.data.service.UserService;
import io.ermdev.cshop.exception.EntityException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserDetailServiceImpl implements UserDetailsService {

    private UserService userService;

    public UserDetailServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            final User user = userService.findByUsername(username);
            boolean enabled = user.getEnabled();
            boolean accountNonExpired = true;
            boolean credentialsNonExpired = true;
            boolean accountNonLocked = true;
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                    enabled, accountNonExpired, credentialsNonExpired, accountNonLocked,
                    grantedAuthorities(user.getRoles()));
        } catch (EntityException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }

    private static Set<GrantedAuthority> grantedAuthorities(List<Role> roleList) {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        roleList.parallelStream().forEach(role -> grantedAuthorities.add(new SimpleGrantedAuthority(role.getName())));
        return grantedAuthorities;
    }
}
