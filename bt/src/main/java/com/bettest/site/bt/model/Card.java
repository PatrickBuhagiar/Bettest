package com.bettest.site.bt.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Card {

	private int card_id, card_cvv;
	private long card_number;
	private String card_type_name, card_holder_name;
	private Date card_exp_date;
	
	public Card(int card_id, int card_cvv, long card_number,
			String card_type_name, String card_holder_name, Date card_exp_date) {
		super();
		this.card_id = card_id;
		this.card_cvv = card_cvv;
		this.card_number = card_number;
		this.card_type_name = card_type_name;
		this.card_holder_name = card_holder_name;
		this.card_exp_date = card_exp_date;
	}
	
	public Card() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the card_id
	 */
	public int getCard_id() {
		return card_id;
	}

	/**
	 * @param card_id the card_id to set
	 */
	public void setCard_id(int card_id) {
		this.card_id = card_id;
	}

	/**
	 * @return the card_cvv
	 */
	public int getCard_cvv() {
		return card_cvv;
	}

	/**
	 * @param card_cvv the card_cvv to set
	 */
	public void setCard_cvv(int card_cvv) {
		this.card_cvv = card_cvv;
	}

	/**
	 * @return the card_number
	 */
	public long getCard_number() {
		return card_number;
	}

	/**
	 * @param card_number the card_number to set
	 */
	public void setCard_number(long card_number) {
		this.card_number = card_number;
	}

	/**
	 * @return the card_type_name
	 */
	public String getCard_type_name() {
		return card_type_name;
	}

	/**
	 * @param card_type_name the card_type_name to set
	 */
	public void setCard_type_name(String card_type_name) {
		this.card_type_name = card_type_name;
	}

	/**
	 * @return the card_holder_name
	 */
	public String getCard_holder_name() {
		return card_holder_name;
	}

	/**
	 * @param card_holder_name the card_holder_name to set
	 */
	public void setCard_holder_name(String card_holder_name) {
		this.card_holder_name = card_holder_name;
	}

	/**
	 * @return the card_exp_date
	 * @throws ParseException 
	 */
	public String getCard_exp_date() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		return sdf.format(card_exp_date);
	}

	/**
	 * @param card_exp_date the card_exp_date to set
	 */
	public void setCard_exp_date(Date card_exp_date) {
		this.card_exp_date = card_exp_date;
	}
	
}
