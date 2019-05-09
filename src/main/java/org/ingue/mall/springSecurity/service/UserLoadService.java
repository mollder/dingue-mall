package org.ingue.mall.springSecurity.service;

import lombok.RequiredArgsConstructor;
import org.ingue.mall.domain.Users;
import org.ingue.mall.repository.UsersRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
@RequiredArgsConstructor
public class UserLoadService implements UserDetailsService {

    private final UsersRepository usersRepository;

    Authentication authentication;

   @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        Users users = usersRepository.findUsersByUserEmail(userEmail);

        if(ObjectUtils.isEmpty(users)) throw new UsernameNotFoundException(userEmail);
        //ObjectUtil 사용, assert

        return users;
    }
}
