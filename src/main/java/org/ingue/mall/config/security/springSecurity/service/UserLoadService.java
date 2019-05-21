package org.ingue.mall.config.security.springSecurity.service;

import lombok.RequiredArgsConstructor;
import org.ingue.mall.user.Users;
import org.ingue.mall.user.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
@RequiredArgsConstructor
public class UserLoadService implements UserDetailsService {

    private final UserRepository userRepository;

    Authentication authentication;

   @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        Users users = userRepository.findUsersByUserEmail(userEmail);

        if(ObjectUtils.isEmpty(users)) throw new UsernameNotFoundException(userEmail);
        //ObjectUtil 사용, assert

        return users;
    }
}
