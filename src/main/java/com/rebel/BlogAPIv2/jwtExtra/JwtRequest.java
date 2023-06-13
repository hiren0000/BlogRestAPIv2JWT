package com.rebel.BlogAPIv2.jwtExtra;

public class JwtRequest
{
    private String email;
    private String pass;

    public JwtRequest() {
    }

    public JwtRequest(String userName, String password) {
        this.email = userName;
        this.pass = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String userName) {
        this.email = userName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

}
