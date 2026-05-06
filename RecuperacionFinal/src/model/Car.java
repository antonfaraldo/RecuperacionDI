package model;

import java.time.LocalDateTime;

public class Car {
	private int id;
	private String brand;
	private String model;
	private int horsePower; 	// Potencia de motor
	private String type;		// Tipo de coche por ejemplo un deportivo o una furgoneta
	private LocalDateTime registrationDate; 	// La fecha de matriculacion
	private String imageUrl;
	private boolean isFavorite;
	
	public Car() {}

	public Car(int id, String brand, String model, int horsePower, String type, LocalDateTime registrationDate,
			String imageUrl, boolean isFavorite) {
		super();
		this.id = id;
		this.brand = brand;
		this.model = model;
		this.horsePower = horsePower;
		this.type = type;
		this.registrationDate = registrationDate;
		this.imageUrl = imageUrl;
		this.isFavorite = isFavorite;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getHorsePower() {
		return horsePower;
	}

	public void setHorsePower(int horsePower) {
		this.horsePower = horsePower;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public LocalDateTime getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(LocalDateTime registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public boolean isFavorite() {
		return isFavorite;
	}

	public void setFavorite(boolean isFavorite) {
		this.isFavorite = isFavorite;
	}
	
	
}
