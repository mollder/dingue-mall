package org.ingue.mall.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] AUTH_WHITELIST = {
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
}
