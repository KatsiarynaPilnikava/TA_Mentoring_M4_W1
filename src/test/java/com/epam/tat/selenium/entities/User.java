package com.epam.tat.selenium.entities;

public class User {
    private String username;
    private String password;
    private String mail;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.mail = username + "@mail.ru";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        this.mail = username + "@mail.ru";
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }
}
