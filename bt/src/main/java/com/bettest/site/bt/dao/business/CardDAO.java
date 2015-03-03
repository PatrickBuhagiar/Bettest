package com.bettest.site.bt.dao.business;

import java.text.ParseException;
import java.util.List;

import com.bettest.site.bt.model.Card;
import com.bettest.site.bt.model.User;

public interface CardDAO {
	public int create(Card c) throws ParseException;
	
	// public Card read(int id);
	
	//public List<Card> readAll();
	
	//public void update(Card c);
	
	//public void delete(int id);
}
