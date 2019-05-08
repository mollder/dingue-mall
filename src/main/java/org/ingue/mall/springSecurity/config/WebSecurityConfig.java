package org.ingue.mall.springSecurity.config;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.RequiredArgsConstructor;
import org.ingue.mall.springSecurity.config.loginHandler.UserLoginFailureHandler;
import org.ingue.mall.springSecurity.config.loginHandler.UserLoginSuccessHandler;
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
    private final UserLoginSuccessHandler userLoginSuccessHandler;
    private final UserLoginFailureHandler userLoginFailureHandler;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    private final String[] AUTH_WHITELIST = {
            "/",
            "/static/**",
            "/dist/**",
            "/failure",
            "/auth/**",
            "/api/posts/**",
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
                .loginProcessingUrl("/auth")
                .successHandler(userLoginSuccessHandler)
                .failureHandler(userLoginFailureHandler)
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // TODO Auto-generated method stub
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userLoadService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        auth
                .authenticationProvider(daoAuthenticationProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        String idForEncode = "bcrypt";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(idForEncode, new BCryptPasswordEncoder());

        return new DelegatingPasswordEncoder(idForEncode, encoders);
    }
}