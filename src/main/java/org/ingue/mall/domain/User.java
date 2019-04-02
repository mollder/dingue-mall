package org.ingue.mall.domain;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Getter
public class User implements UserDetails {

    @Id
    private String userId;

    @Column(nullable = false)
    private String userPassword;

    private String userEmail;
    private String userName;
    private String userPhoneNum;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updateAt;

    @Builder
    public User(String userId, String userPassword, String userEmail, String userName, String userPhoneNum) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
        this.userName = userName;
        this.userPhoneNum = userPhoneNum;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.getUserPassword();
    }

    @Override
    public String getUsername() {
        return this.getUserId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
