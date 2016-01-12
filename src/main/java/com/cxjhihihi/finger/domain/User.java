package com.cxjhihihi.finger.domain;

/**
 * @author hzcaixinjia
 */
public class User {
    private int id;

    private String username;

    private String password;

    private int user_type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getUser_type() {
        return user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", password="
            + password + ", user_type=" + user_type + "]";
    }

}
