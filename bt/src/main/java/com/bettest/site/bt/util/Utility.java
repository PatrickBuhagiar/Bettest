package com.bettest.site.bt.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bettest.site.bt.dao.ImplBetDAO;
import com.bettest.site.bt.model.Bet;
import com.bettest.site.bt.model.User;

public class Utility {
	Validation v = new Validation(this);
	ImplBetDAO bdao;
	ClassPathXmlApplicationContext context;
	List<Bet> thelist;
	
	public Utility(){
		thelist = null;
	}
	public Utility(List<Bet> injection){
		thelist = injection;
	}
	
	public Date DbToDate(String date){
			Date initDate = null;
			try {
				initDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
			} catch (ParseException e) {
				return null;
			}
			return initDate;
	}
	
	public Date DateToDb(String date){
			Date initDate = null;
			try {
				initDate = new SimpleDateFormat("dd/MM/yyyy").parse(date);
			} catch (ParseException e) {
				//e.printStackTrace();
				return null;
			}
			return initDate;
	}
	
	public Date ExpDateToDb(String date){
		Date initDate = null;
		try {
			initDate = new SimpleDateFormat("MM/yy").parse(date);
			System.out.println(initDate);
		} catch (ParseException e) {
			//e.printStackTrace();
			return null;
		}
		return initDate;
	}

	
	public String getBets(User u){
		String out = "";
		context = new ClassPathXmlApplicationContext("Spring-Module.xml");
		bdao = (ImplBetDAO) context.getBean("ImplBetDAO");
		//thelist = null;
		if(u != null){
			thelist = (ArrayList<Bet>) bdao.readByUserId(u.getUser_id());
		} 
		out += "<div class=\"col-md-3\"></div>";
		out += "<div class=\"col-md-6\">";
		if(thelist == null){
			out += "<h4 class=\"caption\">Your Have No Bets to View</h4>";
		} else if(thelist.size() == 0){
			out += "<h4 class=\"caption\">Your Have No Bets to View</h4>";
		} else {
			out +="<h4 class=\"caption\">Your Previously Placed Bets</h4>";
			out +="<table class=\"table table-bordered thumbnail bettable\">";
			out +="<thead>";
			out +="<tr><th>Bet Id</th><th>Bet Timestamp</th><th>Bet Risk Level</th><th>Bet Amount</th></tr>";
			out +="</thead>";
			out +="<tbody>";
			for(int i = 0; i<= thelist.size() - 1; i++){
				out+="<tr>";
				out+="<td>" + thelist.get(i).getBet_id() + "</td>";
				out+="<td>" + thelist.get(i).getBet_timestamp().toString() + "</td>";
				String betrisk;
	   			 if(thelist.get(i).getBet_risk_level() == 1) { betrisk = "Low"; }
	   			 else if(thelist.get(i).getBet_risk_level() == 2) { betrisk = "Medium"; }
	   			 else { betrisk = "High"; }
				out+="<td>" + betrisk + "</td>";
				out+="<td>" + thelist.get(i).getBet_amount() + "</td>";
				out+="</tr>";
			}
			out+="</tbody>";
			out+="</table>";
			out+="</div>";
			out+="<div class=\"col-md-3\"></div>";
		}
		return out;
	} 
}
