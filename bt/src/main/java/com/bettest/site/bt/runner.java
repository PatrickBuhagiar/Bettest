package com.bettest.site.bt;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bettest.site.bt.dao.ImplBetDAO;
import com.bettest.site.bt.dao.ImplCardDAO;
import com.bettest.site.bt.dao.ImplUserDAO;
import com.bettest.site.bt.model.Bet;
import com.bettest.site.bt.model.Card;
import com.bettest.site.bt.model.User;

public class runner {
	public static void main(String[]args) throws ParseException{
		//ApplicationContext context = 
	    //		new ClassPathXmlApplicationContext("Spring-Module.xml");
		//Trying out Adding a User AND a card at the same time.
		/* SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		Card c = new Card(2, 243, 897676214,
				"Visa", "John Doe", sdf.parse("2012-12-12"));
		ImplCardDAO cdao = (ImplCardDAO) context.getBean("ImplCardDAO");
		int id = cdao.create(c);
		System.out.println("Card:" + id);
		User u = new User();
		u.setUser_address("19, Some House ");
		u.setUser_card_id(id);
		u.setUser_city("New York City");
		u.setUser_country("USA");
		u.setUser_id(-1);
		u.setUser_name("Elaine");
		u.setUser_password("somepassword");
		u.setUser_email("email@email.com");
		u.setUser_postcode("NYC1234");
		u.setUser_surname("Doe");
		u.setUser_dob(sdf.parse("1984-12-12"));
		u.setUser_type_id(1);
		u.setUser_username("jdoe21");
		ImplUserDAO udao = (ImplUserDAO) context.getBean("ImplUserDAO");
		int uid = udao.create(u);
		Bet b = new Bet();
		b.setBet_amount((float) 25.00);
		b.setBet_id(-1);
		b.setBet_risk_level(5);
		b.setBet_user_id(uid);
		b.setBet_timestamp(new Timestamp(System.currentTimeMillis()));
		ImplBetDAO bdao = (ImplBetDAO) context.getBean("ImplBetDAO");
		bdao.create(b); */
		//ImplUserDAO udao = (ImplUserDAO) context.getBean("ImplUserDAO");
		//User u = udao.readByLogin("jdoe21", "somepassword");
		//if(u == null){ System.out.println("NULL!"); } else { System.out.println("NOT NULL!"); }
	}

}
