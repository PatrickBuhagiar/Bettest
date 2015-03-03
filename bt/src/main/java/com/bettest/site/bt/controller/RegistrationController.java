package com.bettest.site.bt.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bettest.site.bt.dao.ImplCardDAO;
import com.bettest.site.bt.dao.ImplUserDAO;
import com.bettest.site.bt.model.Card;
import com.bettest.site.bt.model.User;
import com.bettest.site.bt.util.Utility;
import com.bettest.site.bt.util.Validation;


public class RegistrationController extends HttpServlet {
	//private static final long serialVersionUID = 3720733758637196504L;
	//private Validation validation = new Validation();
	//private ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("Spring-Module.xml");
	//private ImplUserDAO udao = (ImplUserDAO) context.getBean("ImplUserDAO");
	//private ImplCardDAO cdao = (ImplCardDAO) context.getBean("ImplCardDAO");
	//public StringBuilder message;
	//Utility util = new Utility();
//	private User user = new User();
//	private Card card = new Card();
	public int threads = 0;
	boolean write = false;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// test
	public RegistrationController(){
		
	}
	
//	public void doGet(HttpServletRequest request, HttpServletResponse response)
//		    throws ServletException, IOException {
//				response.sendRedirect("index.jsp");
//	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException {
			threads += 1;
			ImplCardDAO cdao;
			ImplUserDAO udao;
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("Spring-Module.xml");
			cdao = (ImplCardDAO) context.getBean("ImplCardDAO");
			udao = (ImplUserDAO) context.getBean("ImplUserDAO");
		
			try {
				if (validation(request, cdao, udao)){
//					request.getSession().setAttribute("msg", "Successfull Registration");
//					request.getSession().setAttribute("msgstate", "alert-success");
					response.sendRedirect("index.jsp");
				} else {
//					request.getSession().setAttribute("msg", message.toString());
//					request.getSession().setAttribute("msgstate", "alert-danger");
					response.sendRedirect("index.jsp");
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			threads -= 1;
	}
	
	public boolean validation(HttpServletRequest request, ImplCardDAO cdao, ImplUserDAO udao) throws ParseException{
		//Obtain parameters
		Utility util = new Utility();
//		Card card = new Card();
//		User user = new User();
		Validation validation = new Validation();
		String username = request.getParameter("reg-username");
		String password = request.getParameter("reg-password");
		String confirmpassword = request.getParameter("reg-confirmpassword");
		String email = request.getParameter("reg-email");
		String name = request.getParameter("reg-name");
		String surname = request.getParameter("reg-surname");
		String dob = request.getParameter("reg-dob");
		String country = request.getParameter("reg-country");
		String address = request.getParameter("reg-address");
		String city = request.getParameter("reg-city");
		String postcode = request.getParameter("reg-pcode");
		String cardtype = request.getParameter("reg-card");
		String cardholder = request.getParameter("reg-cardholder");
		String cardno = request.getParameter("reg-cardno");
		String 	expdate = request.getParameter("reg-expdate");
		String ccv = request.getParameter("reg-ccv");
		String accounttype = request.getParameter("reg-acc");
		if(accounttype == null){
			accounttype = "off";
		}
//		System.out.println("Account Type: " +accounttype);
//		System.out.println("Password:" + password );
//		System.out.println("ConfirmPassword:" + confirmpassword);
		//validation
		StringBuilder message = new StringBuilder();
		if (!validation.validate_username(username,udao)){
			message.append("Username not unique.\n");
		}
		if (!validation.validate_password(password)){
			message.append("Password too weak. Must be greater than 8 characters.\n");
		}
		if (!(confirmpassword.equals(password))){
			message.append("Passwords do not match.\n");
		}
		if (!validation.validate_email(email)){
			message.append("Invalid e-mail address.\n");
		}
		if (!validation.validate_name(name)){
			message.append("Invalid name.\n");
		}
		if (!validation.validate_name(surname)){
			message.append("Invalid surname.\n");
		}
		if (!validation.validate_dob(dob)){
			message.append("invalid date of birth. Must be over 18 years of age.\n ");
		}
		if (!validation.validate_empty(country)){
			message.append("Country cannot be empty.\n");
		}
		if (!validation.validate_empty(address)){
			message.append("Address cannot be empty.\n");
		}
		if (!validation.validate_empty(city)){
			message.append("City cannot be empty.\n");
		}
		if (!validation.validate_empty(postcode)){
			message.append("Postcode cannot be empty.\n");
		}
		if (cardtype == "-1"){
			message.append("Please select Credit card");
		}
		if (!validation.validate_empty(cardholder)){
			message.append("Card holder name cannot be empty.\n");
		}
		if (!validation.validate_credit_card(cardno)){
			message.append("Invalid credit card number.\n");
		}
		if (!validation.validate_exp_date(expdate)){
			message.append("Expiry date must be in the future.");
		}
		if (!validation.validate_ccv(ccv)){
			message.append("");
		}
		
		//Card type
		String Ctype = "";
		if (cardtype.equals("1")) Ctype = "American Express";
		else if (cardtype.equals("2")) Ctype = "Mastercard";
		else if (cardtype.equals("3"))Ctype = "Visa";
		
		//Country
		String cntry = "";
		if (country.equals("1")) cntry = "United States";
		else if (country.equals("2")) cntry = "United Kingdom";
		else if (country.equals("3"))cntry = "Malta";
		else if (country.equals("4"))cntry = "Germany";
		else if (country.equals("5"))cntry = "France";
		
		//account type
		int acctype;
		if (accounttype.equals("on")) acctype = 1;
		else acctype = 0;
		
		if (message.toString().equals("")){
//			System.out.println(ccv +"   " + Integer.parseInt(ccv));
			//Create Card
			Card c =  new Card(0, Integer.parseInt(ccv), Long.parseLong(cardno), Ctype, cardholder, util.ExpDateToDb(expdate));
			//card = c;
			int id;
			id = cdao.create(c);
			//Create User
			User u = new User(0, acctype, id,
					username, password, email, name, surname, 
					util.DateToDb(dob), address, city,cntry, postcode);
				while (write){
					
				}
				write = true;
				int uid = udao.create(u);
				write = false;
				//user = u;
				request.getSession().setAttribute("msg", "Successfull Registration");
				request.getSession().setAttribute("msgstate", "alert-success");
				//response.sendRedirect("index.jsp");
		} else {
			request.getSession().setAttribute("msg", message.toString());
			request.getSession().setAttribute("msgstate", "alert-danger");
		}
		//System.out.println(message.toString());
		return false;
	}
}
