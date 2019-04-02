package org.ingue.mall.springSecurity.config;

import lombok.RequiredArgsConstructor;
import org.ingue.mall.springSecurity.service.UserLoadService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserLoadService userLoadService;

    private final String[] AUTH_WHITELIST = {
            "/",
            "/static/**",
            "/dist/**",
    };

    protected void configure(HttpSecurity http) throws Exception {
         http
            .authorizeRequests()
                 .antMatchers(AUTH_WHITELIST).permitAll()
                 .antMatchers("/success/**").hasRole("USER")
                 .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/")
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // TODO Auto-generated method stub
        auth
                .authenticationProvider(new DaoAuthenticationProvider())
                .userDetailsService(userLoadService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        String idForEncode = "bcrypt";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(idForEncode, new BCryptPasswordEncoder());

        return new DelegatingPasswordEncoder(idForEncode, encoders);
    }
}