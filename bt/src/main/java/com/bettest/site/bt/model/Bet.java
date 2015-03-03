package com.bettest.site.bt.model;

import java.sql.Timestamp;
// some comment
public class Bet {
	private int bet_id, bet_user_id, bet_risk_level;
	private float bet_amount;
	private Timestamp bet_timestamp;
	
	public Bet(){
		
	}
	public Bet(int id, int userid, int risklevel, float amount, Timestamp timestamp){
		this.bet_id = id;
		this.bet_user_id = userid;
		this.bet_risk_level = risklevel;
		this.bet_amount = amount;
		this.bet_timestamp = timestamp;
	}
	
	/**
	 * @return the bet_id
	 */
	public int getBet_id() {
		return bet_id;
	}
	/**
	 * @param bet_id the bet_id to set
	 */
	public void setBet_id(int bet_id) {
		this.bet_id = bet_id;
	}
	/**
	 * @return the bet_user_id
	 */
	public int getBet_user_id() {
		return bet_user_id;
	}
	/**
	 * @param bet_user_id the bet_user_id to set
	 */
	public void setBet_user_id(int bet_user_id) {
		this.bet_user_id = bet_user_id;
	}
	/**
	 * @return the bet_risk_level
	 */
	public int getBet_risk_level() {
		return bet_risk_level;
	}
	/**
	 * @param bet_risk_level the bet_risk_level to set
	 */
	public void setBet_risk_level(int bet_risk_level) {
		this.bet_risk_level = bet_risk_level;
	}
	/**
	 * @return the bet_amount
	 */
	public float getBet_amount() {
		return bet_amount;
	}
	/**
	 * @param bet_amount the bet_amount to set
	 */
	public void setBet_amount(float bet_amount) {
		this.bet_amount = bet_amount;
	}
	/**
	 * @return the bet_timestamp
	 */
	public Timestamp getBet_timestamp() {
		return bet_timestamp;
	}
	/**
	 * @param bet_timestamp the bet_timestamp to set
	 */
	public void setBet_timestamp(Timestamp bet_timestamp) {
		this.bet_timestamp = bet_timestamp;
	}
}
