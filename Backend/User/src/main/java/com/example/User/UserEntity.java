package com.example.User;

import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name="Userr")
public class UserEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String surname;
    @Column(name="userName")
    private String userName;

    private String password;

    private String email;

    private int mfaCode;

    private LocalDateTime mfaCodeExpiration;

    private String role;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id=id;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getMfaCode() {
        return mfaCode;
    }

    public void setMfaCode(int mfaCode) {
        this.mfaCode = mfaCode;
    }

    public LocalDateTime getMfaCodeExpiration() {
        return mfaCodeExpiration;
    }

    public void setMfaCodeExpiration(LocalDateTime mfaCodeExpiration) {
        this.mfaCodeExpiration = mfaCodeExpiration;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
