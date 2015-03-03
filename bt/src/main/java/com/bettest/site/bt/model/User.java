package com.bettest.site.bt.model;
import java.util.Date;

public class User{

	private int user_id, user_type_id, user_card_id;
	private String user_username, user_password, user_email, user_name, user_surname,
	user_address, user_city, user_country, user_postcode;
	private Date user_dob; 

	public User(int id, int type_id,int card,
			String username, String password, String email, String name, String surname, 
			Date dob, String address, String city,String country, String postcode){
		user_id = id;
		user_type_id = type_id;
		user_card_id = card;
		user_username = username;
		user_password = password;
		setUser_email(email);
		user_name = name;
		user_surname = surname;
		setUser_dob(dob);
		user_address = address;
		user_city = city;
		user_country = country;
		user_postcode = postcode;
		
	}
	
	public User() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString(){
		return "Username=" + this.user_username + ", password= " + this.user_password;
	}
	
	/**
	 * @return the user_id
	 */
	public int getUser_id() {
		return user_id;
	}

	/**
	 * @param user_id the user_id to set
	 */
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	/**
	 * @return the user_type_id
	 */
	public int getUser_type_id() {
		return user_type_id;
	}

	/**
	 * @param user_type_id the user_type_id to set
	 */
	public void setUser_type_id(int user_type_id) {
		this.user_type_id = user_type_id;
	}

	/**
	 * @return the user_card_id
	 */
	public int getUser_card_id() {
		return user_card_id;
	}

	/**
	 * @param user_card_id the user_card_id to set
	 */
	public void setUser_card_id(int user_card_id) {
		this.user_card_id = user_card_id;
	}

	/**
	 * @return the user_username
	 */
	public String getUser_username() {
		return user_username;
	}

	/**
	 * @param user_username the user_username to set
	 */
	public void setUser_username(String user_username) {
		this.user_username = user_username;
	}

	/**
	 * @return the user_password
	 */
	public String getUser_password() {
		return user_password;
	}

	/**
	 * @param user_password the user_password to set
	 */
	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}

	/**
	 * @return the user_name
	 */
	public String getUser_name() {
		return user_name;
	}

	/**
	 * @param user_name the user_name to set
	 */
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	/**
	 * @return the user_surname
	 */
	public String getUser_surname() {
		return user_surname;
	}

	/**
	 * @param user_surname the user_surname to set
	 */
	public void setUser_surname(String user_surname) {
		this.user_surname = user_surname;
	}

	/**
	 * @return the user_address
	 */
	public String getUser_address() {
		return user_address;
	}

	/**
	 * @param user_address the user_address to set
	 */
	public void setUser_address(String user_address) {
		this.user_address = user_address;
	}

	/**
	 * @return the user_city
	 */
	public String getUser_city() {
		return user_city;
	}

	/**
	 * @param user_city the user_city to set
	 */
	public void setUser_city(String user_city) {
		this.user_city = user_city;
	}

	/**
	 * @return the user_country
	 */
	public String getUser_country() {
		return user_country;
	}

	/**
	 * @param user_country the user_country to set
	 */
	public void setUser_country(String user_country) {
		this.user_country = user_country;
	}

	/**
	 * @return the user_postcode
	 */
	public String getUser_postcode() {
		return user_postcode;
	}

	/**
	 * @param user_postcode the user_postcode to set
	 */
	public void setUser_postcode(String user_postcode) {
		this.user_postcode = user_postcode;
	}

	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public Date getUser_dob() {
		return user_dob;
	}
	public void setUser_dob(Date user_dob) {
		this.user_dob = user_dob;
	}
}
