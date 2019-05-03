package org.ingue.mall.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@NoArgsConstructor
public class Users implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String userPassword;

    @Column(nullable = false)
    private String userNickName;

    @Column(unique = true, nullable = false)
    private String userEmail;

    @Column(unique = true)
    private String userPhoneNum;

    @OneToMany(mappedBy = "user")
    private Set<Postings> postingsSet = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Comments> commentsSet = new HashSet<>();

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updateAt;

    private String developer;

    @Builder
    public Users(String userEmail, String userPassword, String userNickName, String userPhoneNum) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userNickName = userNickName;
        this.userPhoneNum = userPhoneNum;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantList = new ArrayList<>();
        grantList.add(new SimpleGrantedAuthority("ROLE_USER"));
        return grantList;
    }

    @Override
    public String getPassword() {
        return this.getUserPassword();
    }

    @Override
    public String getUsername() {
        return this.getUserEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
