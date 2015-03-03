package com.bettest.site.bt.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bettest.site.bt.dao.ImplBetDAO;
import com.bettest.site.bt.dao.ImplCardDAO;
import com.bettest.site.bt.dao.ImplUserDAO;
import com.bettest.site.bt.model.Bet;
import com.bettest.site.bt.model.Card;
import com.bettest.site.bt.model.User;
import com.bettest.site.bt.util.Utility;
import com.bettest.site.bt.util.Validation;


public class BetController extends HttpServlet {
	 
	private static final long serialVersionUID = 1L;
	private Validation validation;
	private ApplicationContext context; 
	private StringBuilder message= new StringBuilder();
	private ImplBetDAO bdao;
	private Utility util;
	
	public BetController() {
		context = new ClassPathXmlApplicationContext("Spring-Module.xml");
		util = new Utility();
		validation = new Validation();
		bdao = (ImplBetDAO) context.getBean("ImplBetDAO");
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException {
		User currentUser = (User) request.getSession().getAttribute("logged-user");
		List<Bet> betList = bdao.readByUserId(currentUser.getUser_id());
		request.getSession().setAttribute("logged-user-bets", betList);
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException {
		
		User currentUser = (User) request.getSession().getAttribute("logged-user");
		ImplBetDAO sbdao = (ImplBetDAO) context.getBean("ImplBetDAO");
		if(currentUser == null){
			// Not Logged in.
			request.getSession().setAttribute("msg", "Please Login.");
			request.getSession().setAttribute("msgstate", "alert-danger");
			response.sendRedirect("index.jsp");
		} else {
			// We can Proceed.
			String amount = request.getParameter("bet-amount");
			String risk = request.getParameter("bet-risk");
			System.out.println("a " + amount + " r " + risk );
			if(amount != null && risk != null && validation.validate_decimal(amount)){
				// The Bet Is Valid, Proceed to add.
				Bet tempBet = new Bet();
				tempBet.setBet_amount(Float.parseFloat(amount));
				tempBet.setBet_id(-1);
				tempBet.setBet_risk_level(Integer.parseInt(risk));
				Date now = new Date();
				tempBet.setBet_timestamp(new Timestamp(now.getTime()));
				tempBet.setBet_user_id(currentUser.getUser_id());
				boolean result = betConstraints(request, currentUser, tempBet);
				
				if(result){
					bdao.create(tempBet);
				}
				List<Bet> thelist = bdao.readByUserId(currentUser.getUser_id());
				request.setAttribute("logged-user-bets", thelist);
				response.sendRedirect("home.jsp");
			} else {
				request.getSession().setAttribute("msg", "Risk or Amount are empty, Please Try again");
				request.getSession().setAttribute("msgstate", "alert-danger");
				List<Bet> thelist = bdao.readByUserId(currentUser.getUser_id());
				request.setAttribute("logged-user-bets", thelist);
				response.sendRedirect("home.jsp");
			}
		}
	}
	
	public boolean betConstraints(HttpServletRequest request, User toConstrain, Bet toPlace){
		int utype = toConstrain.getUser_type_id();
		int uid = toConstrain.getUser_id();
		float betAmount = toPlace.getBet_amount();
		ImplBetDAO bdao = (ImplBetDAO) context.getBean("ImplBetDAO");
		long betCount = bdao.getCountByUserId(uid);
		float betCuml = bdao.getUserTotalBetted(uid);
		boolean isLow = (toPlace.getBet_risk_level() == 1);
		
		System.out.println("betammount: " + betAmount);
		if (utype == 0 && betCount < 3 && betAmount <= 5 && isLow){
			// Valid Free Bet by Free User
			request.getSession().setAttribute("msg", "Your Bet Has Been placed, thank you!");
			request.getSession().setAttribute("msgstate", "alert-success");
			return true;
		} else if(utype == 1 && (betCuml + betAmount) <= 5000){
			// Valid Premium Bet by Free User.
			request.getSession().setAttribute("msg", "Your Bet Has Been placed, thank you!");
			request.getSession().setAttribute("msgstate", "alert-success");
			return true;
		} else {
			String m = builErrorString(uid,utype,betCount,betCuml,betAmount,isLow);
			request.getSession().setAttribute("msg", m);
			request.getSession().setAttribute("msgstate", "alert-danger");
			return false;
		}
	}
	//...
	public String builErrorString(int userId, int userType, long betCount, float betTotal, float betAmount, boolean isLow){
		String out = "";
		ArrayList<String> errorList = new ArrayList<String>();
		if(userType == 0){
			out += "ERROR: Free Users can only";
			if(betCount >= 3){
				errorList.add(" place up to 3 bets");
			}
			if(betAmount > 5.00){
				errorList.add(" bet up to 5 euros at once");
			}
			if(!isLow){
				errorList.add(" place low risk bets");
			}
		} else if (userType == 1){
			out += "ERROR: Premium Users can only";
			if((betAmount + betTotal) > 5000.00){
				errorList.add(" bet up to 5000 euros in their lifetime");
			}
		}
		for(int i = 0; i <= errorList.size() - 1; i++){
			out += errorList.get(i);
			int next = i + 1;
			if(next <= errorList.size() - 1){
				out += ",";
			}
		}
		System.out.println("Returning Error String: " + out);
		return out;
	}
}
