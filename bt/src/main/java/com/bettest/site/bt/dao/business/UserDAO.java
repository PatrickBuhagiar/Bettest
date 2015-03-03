package com.bettest.site.bt.dao.business;

import java.text.ParseException;
import java.util.List;

import com.bettest.site.bt.model.Bet;
import com.bettest.site.bt.model.Card;
import com.bettest.site.bt.model.User;

public interface UserDAO {
	public int create(User u) throws ParseException;
	
	public User readByLogin(String un, String pw);
	public User read(int id);
	// public List<User> readAll();
	
	//public void update(User u);
	
	//public void delete(int id);
}
