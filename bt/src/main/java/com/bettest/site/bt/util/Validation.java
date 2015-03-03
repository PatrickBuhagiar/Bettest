package com.bettest.site.bt.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bettest.site.bt.dao.ImplUserDAO;
import com.bettest.site.bt.model.Card;
import com.bettest.site.bt.model.User;

public class Validation {
	Utility u;
	
	public Validation(){
		u = new Utility();
	}
	
	public Validation(Utility U){
		u = U;
	}
	
	//ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("Spring-Module.xml");
	//ImplUserDAO udao = (ImplUserDAO) context.getBean("ImplUserDAO");
	User user;

	public boolean validate_name(String name){
		if(validate_empty(name) & name.matches("[a-zA-Z_ ]+$")){
			return true;
		} else return false;		
	}
	
	public boolean validate_password(String password){
		if (validate_empty(password) & password.length()>=8){
			return true;
		} else return false;
		
	}
	
	public boolean validate_empty(String s){
		if (s != null){
			if (s.length()>0){
//				System.out.println("not empty username");
				return true;
			}else {
//				System.out.println("empty username");
				return false;
			}
		}else{
//			System.out.println("null username");
			return false;
		}
	}
	
	public boolean validate_integer(String integer){
		try{
			Integer.parseInt(integer);
			return true;
		} catch(NumberFormatException nfe){
			return false;
		}
	}
	
	public boolean validate_longInt(String longint){
		try{
			Long.parseLong(longint);
			return true;
		} catch(NumberFormatException nfe){
			return false;
		}
	}
	
	public boolean validate_decimal(String decimal){
		try{
			Float.parseFloat(decimal);
			return true;
		} catch(NumberFormatException nfe){
			return false;
		}
	}
	
	public boolean validate_credit_card(String credit){
		String regex = "^(?:(?<visa>4[0-9]{12}(?:[0-9]{3})?)|" +
		        "(?<mastercard>5[1-5][0-9]{14})|" +
		        "(?<amex>3[47][0-9]{13}))$";
		Pattern pattern = Pattern.compile(regex);        
		 Matcher matcher = pattern.matcher(credit);
		 return matcher.matches();
	}
	
	public boolean validate_email(String e){
		Pattern p = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		if (validate_empty(e) & p.matcher(e).matches()){
			return true;
		}else return false;
	}
	
	public boolean validate_dob(String dob){
		if (validate_empty(dob)){
			Date date = new Date();
			Date initDate = null;
			try {
				initDate = new SimpleDateFormat("dd/MM/yyyy").parse(dob);
			} catch (ParseException e) {
				//e.printStackTrace();
				return false;
			}
			SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
			int b = Integer.parseInt(formatYear.format(date));
			int a= Integer.parseInt(formatYear.format(initDate));
			long age = Math.abs(a-b);
			if(age > 18){
			    return true;
			}else{
			    return false;
			}		
		} else return false;
	}
	
	public boolean validate_date(String date){
		if(date == null){
			return false;
		}
 
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setLenient(false);
 
		try {
			Date d = sdf.parse(date);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

	public boolean validate_username(String username, ImplUserDAO udao){
		if(validate_empty(username)){
			user = udao.find_username(username); 
//			System.out.println("username from udao: " + user);
			//context.close();
			if(user==null){
//				System.out.println("Got Here! 2");
				return true;
			} else return false;			
		} else return false;	
	}
	
	public boolean validate_ccv(String ccv){
		if(validate_empty(ccv) & validate_integer(ccv) && ccv.length()==3){
			return true;
		} else return false;
	}
	
	public boolean validate_exp_date(String expdate){
		if (validate_empty(expdate)){
			Date date = new Date();
			Date initDate = null;
			try {
				initDate = new SimpleDateFormat("MM/yy").parse(expdate);
//				System.out.println(initDate);
			} catch (ParseException e) {
				//e.printStackTrace();
				return false;
			}
			SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
			int b = Integer.parseInt(formatYear.format(date));
			int a= Integer.parseInt(formatYear.format(initDate));
			long gap = b - a;
			if(gap >=0){
			    return false;
			}else{
			    return true;
			}		
		} else return false;
	}
}
