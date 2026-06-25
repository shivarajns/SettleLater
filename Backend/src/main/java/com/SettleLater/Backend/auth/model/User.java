package com.SettleLater.Backend.auth.model;

import jakarta.persistence.*;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Entity
@Table(name = "Users",
        indexes = {
            @Index(
                    name = "idx_users_email",
                    columnList = "email"
            )
        }
)
public class User {
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

    public String getPassword() {
        return password;
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
