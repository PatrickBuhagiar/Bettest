package com.bettest.site.bt.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bettest.site.bt.dao.ImplBetDAO;
import com.bettest.site.bt.dao.ImplCardDAO;
import com.bettest.site.bt.dao.ImplUserDAO;
import com.bettest.site.bt.model.Bet;
import com.bettest.site.bt.model.Card;
import com.bettest.site.bt.model.User;

public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 3720733758637196504L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException {
		ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Module.xml");
		ImplUserDAO udao = (ImplUserDAO) context.getBean("ImplUserDAO");
		String username = request.getParameter("bt_un");
		String password = request.getParameter("bt_pw");
		User k = udao.readByLogin(username,password);
		String attUser = (String) request.getSession().getAttribute(username);
		String timeUser = "" + request.getSession().getAttribute(username+"-blocked");
		if((username == "" && password == "") || (username == null && password == null)){
			request.getSession().setAttribute("msg", "Username or Password is empty.");
			request.getSession().setAttribute("msgstate", "alert-danger");
			response.sendRedirect("index.jsp");
		} else {
			// Check if Timer Exists first.
			if(k == null){
				if(attUser == null){
					request.getSession().setAttribute("msg", "Login Details Incorrect.");
					request.getSession().setAttribute("msgstate", "alert-danger");
					System.out.println("First Login Failed for User " + username);
					request.getSession().setAttribute(username, "1");
				} else if(attUser.equals("Blocked")){
					request.getSession().setAttribute("msg", "Login Details Incorrect - You have been blocked for 5 minutes");
					request.getSession().setAttribute("msgstate", "alert-danger");
					System.out.println("User : " + username + " is blocked from logging in");
				} else {
					int attNumber = Integer.parseInt(attUser);
					attNumber++;
					request.getSession().setAttribute("msg", "Login Details Incorrect.");
					request.getSession().setAttribute("msgstate", "alert-danger");
					if(attNumber >= 3){
						request.getSession().setAttribute("msg", "Login Details Incorrect - You have been blocked for 5 minutes");
						request.getSession().setAttribute("msgstate", "alert-danger");
						System.out.println(username + " Has Been Blocked");
						request.getSession().setAttribute(username, "Blocked");
						request.getSession().setAttribute(username+"-blocked", System.currentTimeMillis());
					} else {
						request.getSession().setAttribute(username, "" + attNumber);
					}
				}
				response.sendRedirect("index.jsp");
			} else {
				ImplBetDAO bdao = (ImplBetDAO) context.getBean("ImplBetDAO");
				List<Bet> betList = bdao.readByUserId(k.getUser_id());
				request.getSession().setAttribute("logged-user", k);
				request.getSession().setAttribute("logged-user-bets", betList);
				request.getSession().setAttribute("msg", "Welcome to the Betting Site! Please place your bet.");
				request.getSession().setAttribute("msgstate", "alert-success");
				response.sendRedirect("home.jsp");
			}
		}
	}
}
