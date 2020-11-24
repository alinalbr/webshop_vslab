package com.vslab.UserCoreApplication.Model;

import io.micrometer.core.lang.NonNull;

import javax.persistence.*;

@Entity
@Table(name="User")
public class User {

    //Primärschlüssel, dessen ID automatische erzeugt wird
    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String firstname;

    @NonNull
    private String lastname;

    @NonNull
    private String username;

    @NonNull
    private String password;

    @NonNull
    private Long role; // 1 = admin, 2 = user

    public User(String firstname, String lastname, String username, String password, Long role) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getRole() {
        return role;
    }

    public void setRole(Long role) {
        this.role = role;
    }
}
