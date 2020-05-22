package com.example.loginapplication;

public class User {
    private String fio;
    private String email;
    private String password;
    private String telephone;

    public User(String fio, String email, String password, String telephone) {
        this.fio = fio;
        this.email = email;
        this.password = password;
        this.telephone = telephone;
    }
    public User(String email, String password) {
        this("", email,password, "");
    }

    public String getFio() {
        return fio;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getTelephone() {
        return telephone;
    }
}
