package com.bettest.site.bt.dao.business;

import java.text.ParseException;
import java.util.List;

import com.bettest.site.bt.model.Bet;
import com.bettest.site.bt.model.Card;
import com.bettest.site.bt.model.User;

public interface BetDAO {
	public int create(Bet b);
	
	public List<Bet> readByUserId(int id);
	
	public long getCountByUserId(int id);
	
	public float getUserTotalBetted(int id);
	
	//public Bet read(int id);
	
		//public List<Bet> readAll();
	
	//public void update(Bet u);
	
	//public void delete(int id);
	
}
