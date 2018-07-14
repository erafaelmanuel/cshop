package com.rem.cs;

import com.rem.cs.data.jpa.role.Role;
import com.rem.cs.data.jpa.user.User;
import com.rem.cs.data.jpa.user.UserRepository;
import io.ermdev.cshop.exception.EntityException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class UserDetailServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    public UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userRepository.findByEmail(username).orElse(null);
        try {
            if (user == null) {
                throw new EntityException("No user found");
            } else {
                boolean enabled = user.isActivated();
                boolean accountNonExpired = true;
                boolean credentialsNonExpired = true;
                boolean accountNonLocked = true;

                return new org.springframework.security.core.userdetails.User(user.getEmail(),
                        "{noop}" + user.getPassword(), enabled, accountNonExpired, credentialsNonExpired,
                        accountNonLocked, grantedAuthorities(userRepository.findRolesById(user.getId())));
            }
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