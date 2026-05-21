package com.dam.DI.RecuperacionFinal.model;

public class User {
    private int id;
	private String username;
	private String email;
	private String password;
	private String role;

    private Integer favoriteCarId;
	public User() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getFavoriteCarId() {
        return favoriteCarId;
    }

    public void setFavoriteCarId(Integer favoriteCarId) {
        this.favoriteCarId = favoriteCarId;
    }

    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public User(String username, String email, String password, String role) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role;
	}

}
