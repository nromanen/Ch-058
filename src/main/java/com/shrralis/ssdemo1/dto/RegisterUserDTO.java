/*
 * The following code have been created by Yaroslav Zhyravov (shrralis).
 * The code can be used in non-commercial way for everyone.
 * But for any commercial way it needs a author's agreement.
 * Please contact the author for that:
 *  - https://t.me/Shrralis
 *  - https://twitter.com/Shrralis
 *  - shrralis@gmail.com
 *
 * Copyright (c) 2017 by shrralis (Yaroslav Zhyravov).
 */

package com.shrralis.ssdemo1.dto;

/**
 * A special container that contains information
 * that is necessary for creating new account
 *
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0
 * Created 12/21/17 at 5:38 PM
 */
public class RegisterUserDTO {
    private String login;
    private String email;
    private String password;
    private String name;
    private String surname;

    public String getLogin() {
	    return login.trim();
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
	    return email.trim();
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
	    return password.trim();
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
	    return name.trim();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
	    return surname.trim();
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
