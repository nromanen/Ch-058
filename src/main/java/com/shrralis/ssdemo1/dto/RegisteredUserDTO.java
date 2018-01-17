package com.shrralis.ssdemo1.dto;

/**
 * A DTO that is returned and contains
 *
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0
 * Created 12/27/17 at 5:00 PM
 * @see com.shrralis.ssdemo1.entity.User
 * that have been registered.
 */
public class RegisteredUserDTO {

	private int id;
	private String login;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Override
	public String toString() {
		return "RegisteredUserDTO{" +
				"login='" + login + '\'' +
				'}';
	}
}
