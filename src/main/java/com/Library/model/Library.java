package com.Library.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Library {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String name;
	private String email;
	private String password;
	private String contact;
	private String location;
	private String city;
	private String role;
	private int seat;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	private double hours24;
	private double hours12;
	private double hours8;
	private double reserve;

	public double getReserve() {
		return reserve;
	}

	public void setReserve(double reserve) {
		this.reserve = reserve;
	}

	private String image;

	@OneToMany(mappedBy = "library", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Student> student=new ArrayList<Student>();

	public int getSeat() {
		return seat;
	}

	public void setSeat(int seat) {
		this.seat = seat;
	}

	public double getHours24() {
		return hours24;
	}

	public void setHours24(double hours24) {
		this.hours24 = hours24;
	}

	public double getHours12() {
		return hours12;
	}

	public void setHours12(double hours12) {
		this.hours12 = hours12;
	}

	public double getHours8() {
		return hours8;
	}

	public void setHours8(double hours8) {
		this.hours8 = hours8;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<Student> getStudent() {
		return student;
	}

	public void setStudent(List<Student> student) {
		this.student = student;
	}

	public Library(int id, String name, String email, String password, String contact, String location, String role,
			int seat, double hours24, double hours12, double hours8, String image, List<Student> student) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.contact = contact;
		this.location = location;
		this.role = role;
		this.seat = seat;
		this.hours24 = hours24;
		this.hours12 = hours12;
		this.hours8 = hours8;
		this.image = image;
		this.student = student;
	}

	public Library() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Library [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", contact="
				+ contact + ", location=" + location + ", city=" + city + ", role=" + role + ", seat=" + seat
				+ ", hours24=" + hours24 + ", hours12=" + hours12 + ", hours8=" + hours8 + ", reserve=" + reserve
				+ ", image=" + image + ", student=" + student + "]";
	}

}
