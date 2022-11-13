package com.vk.totality.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class TotalityUserDetailsService implements UserDetailsService {


    private UserRepository userRepository;

    @Autowired
    public TotalityUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserLoginIgnoreCaseAndActiveTrue(username);

        if (user == null)
            throw new UsernameNotFoundException(username + "doesn't exist");
        return new org.springframework.security.core.userdetails.User(
                user.getUserLogin(), "{noop}" + user.getUserPassword(),
                Stream.of(user.getRoles())
                        .map(SimpleGrantedAuthority::new).collect(Collectors.toList())
        );
    }
}
