package org.ingue.mall.springSecurity.service;

import lombok.RequiredArgsConstructor;
import org.ingue.mall.domain.Users;
import org.ingue.mall.repository.UserRepository;
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = userRepository.findUserByUserId(username);

        if(ObjectUtils.isEmpty(users)) throw new UsernameNotFoundException(username);
        //ObjectUtil 사용, assert

        return users;
    }
}
