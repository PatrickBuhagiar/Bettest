package com.bettest.site.bt.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bettest.site.bt.dao.ImplUserDAO;
import com.bettest.site.bt.model.User;

public class LogoutController  extends HttpServlet {

	private static final long serialVersionUID = -1846147684737341484L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException {
		User currentUser = (User) request.getSession().getAttribute("logged-user");
		if(currentUser != null){
			request.getSession().removeAttribute("logged-user");
			request.getSession().removeAttribute("logged-user-bets");
			request.getSession().setAttribute("msg", "Thank you for visiting");
			request.getSession().setAttribute("msgstate", "alert-success");
			response.sendRedirect("index.jsp");
		} else {
			request.getSession().setAttribute("msg", "Something Funny Occured: You cannot log out, because you're not logged in!");
			request.getSession().setAttribute("msgstate", "alert-danger");
			response.sendRedirect("home.jsp");
		}
		
	}
}
