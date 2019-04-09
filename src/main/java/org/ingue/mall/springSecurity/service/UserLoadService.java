package org.ingue.mall.springSecurity.service;

import lombok.RequiredArgsConstructor;
import org.ingue.mall.domain.User;
import org.ingue.mall.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserLoadService implements UserDetailsService {

    private final UserRepository userRepository;

    Authentication authentication;

   @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUserId(username);

        if(user == null) throw new UsernameNotFoundException(username);

        return user;
    }
}
