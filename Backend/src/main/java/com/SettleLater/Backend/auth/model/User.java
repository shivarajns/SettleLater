package com.SettleLater.Backend.auth.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "Users",
        indexes = {
            @Index(
                    name = "idx_users_email",
                    columnList = "email"
            )
        }
)
public class User implements UserDetails {
    @Id
    @Column(name = "user_id", nullable = false, unique = true)
    private String userId;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "is_verified", nullable = false)
    private boolean isVerified = false;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
        return isVerified;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    @PrePersist
    public void prePersist(){
        if(userId == null){
            this.userId = "USR_"+generateRandomUserId(6);
        }
        this.createdAt = LocalDateTime.now();
    }

    private String generateRandomUserId(int length){
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        for(int i=0; i<length; i++){
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }

        return sb.toString();
    }
}
