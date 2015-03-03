package com.test.site.bt.controller;

import static org.junit.Assert.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.MockitoAnnotations.Mock;

import com.bettest.site.bt.controller.LoginController;
import com.bettest.site.bt.dao.ImplUserDAO;
import com.bettest.site.bt.model.User;


public class LoginControllerTest {
	
	private LoginController lc;

	private HttpServletRequest 	request;
	private HttpServletResponse response;
	private HttpSession 		session;
	private User				user;
	private ImplUserDAO			udao;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		request = Mockito.mock(HttpServletRequest.class);
		response = Mockito.mock(HttpServletResponse.class);
		session = Mockito.mock(HttpSession.class);
		user = new User();
		udao = Mockito.mock(ImplUserDAO.class);
		Mockito.doReturn(session).when(request).getSession();
		lc = new LoginController();
	}
	
	@After
	public void tearDown() throws Exception {
		request = null;
		response = null;
		session = null;
		lc = null;
		user = null;
		udao = null;
	}
	/*
	 * Testing for Empty Username and Password Input
	 */
	@Test
	public void testRedirectEmptyUsernameEmptyPassword() throws ServletException, IOException {
		//given
		Mockito.doReturn("").when(request).getParameter("bt_un");
		Mockito.doReturn("").when(request).getParameter("bt_pw");
		//when
		lc.doPost(request, response);
		//then
		Mockito.verify(response).sendRedirect("index.jsp");
	}
	
	@Test
	public void testRedirectEmptyUsernameFullName() throws ServletException, IOException {
		//given
		Mockito.doReturn("").when(request).getParameter("bt_un");
		Mockito.doReturn("something").when(request).getParameter("bt_pw");
		//when
		lc.doPost(request, response);
		//then
		Mockito.verify(response).sendRedirect("index.jsp");
	}
	
	@Test
	public void testRedirectFullUsernameEmptyName() throws ServletException, IOException {
		//given
		Mockito.doReturn("something").when(request).getParameter("bt_un");
		Mockito.doReturn("").when(request).getParameter("bt_pw");
		//when
		lc.doPost(request, response);
		//then
		Mockito.verify(response).sendRedirect("index.jsp");
	}
	
	@Test
	public void testRedirectNullUsernameNullPassword() throws ServletException, IOException {
		//given
		Mockito.doReturn(null).when(request).getParameter("bt_un");
		Mockito.doReturn(null).when(request).getParameter("bt_pw");
		
		//when
		lc.doPost(request, response);
		//then
		Mockito.verify(response).sendRedirect("index.jsp");
	}
	
	@Test
	public void testRedirectNullUsernameFullPassword() throws ServletException, IOException {
		//given
		Mockito.doReturn(null).when(request).getParameter("bt_un");
		Mockito.doReturn("something").when(request).getParameter("bt_pw");
		
		//when
		lc.doPost(request, response);
		//then
		Mockito.verify(response).sendRedirect("index.jsp");
	}
	
	@Test
	public void testRedirectFullUsernameNullPassword() throws ServletException, IOException {
		//given
		Mockito.doReturn("something").when(request).getParameter("bt_un");
		Mockito.doReturn(null).when(request).getParameter("bt_pw");
		
		//when
		lc.doPost(request, response);
		//then
		Mockito.verify(response).sendRedirect("index.jsp");
	}
	
	/*
	 * Testing Time Constraints
	 */
	
	@Test
	public void testTimeReturnedNotTimesetBadDetails() throws ServletException, IOException {
		//given
		Mockito.doReturn("someusername").when(request).getParameter("bt_un");
		Mockito.doReturn("somepassword").when(request).getParameter("bt_pw");
		Mockito.doReturn("something").when(session).getAttribute("someusername-blocked");
		
		//when
		lc.doPost(request, response);
		//then
		Mockito.verify(response).sendRedirect("index.jsp");
	}
	
	
	@Test
	public void testTimeReturnedIsNullBadDetails() throws ServletException, IOException {
		//given
		Mockito.doReturn("someusername").when(request).getParameter("bt_un");
		Mockito.doReturn("somepassword").when(request).getParameter("bt_pw");
		Mockito.doReturn(null).when(session).getAttribute("someusername-blocked");
		
		//when
		lc.doPost(request, response);
		//then
		Mockito.verify(response).sendRedirect("index.jsp");
	}
	
	@Test
	public void testTimeReturnedNotTimesetGoodDetails() throws ServletException, IOException {
		//given
		long n = System.currentTimeMillis();
		long b = n - 120000;
		Mockito.doReturn("someusername").when(request).getParameter("bt_un");
		Mockito.doReturn("somepassword").when(request).getParameter("bt_pw");
		Mockito.doReturn("" + b).when(session).getAttribute("someusername-blocked");
		Mockito.when(udao.readByLogin("someuser", "somepassword")).thenReturn(user);
		//when
		lc.doPost(request, response);
		//then
		Mockito.verify(response).sendRedirect("index.jsp");
	}

	/*
	 * Redirect Invalid Details.
	 */
	
	@Test
	public void testRedirectInvalidDetailsNullCounter() throws ServletException, IOException {
		//given
		Mockito.doReturn("someuser").when(request).getParameter("bt_un");
		Mockito.doReturn("somepassword").when(request).getParameter("bt_pw");
		Mockito.doReturn(null).when(request).getParameter("someuser");
		Mockito.when(udao.readByLogin("someuser", "somepassword")).thenReturn(null);
		//when
		lc.doPost(request, response);
		//then
		Mockito.verify(response).sendRedirect("index.jsp");
	}
	
	@Test
	public void testRedirectInvalidDetailsBlockedCounter() throws ServletException, IOException {
		//given
		Mockito.doReturn("someuser").when(request).getParameter("bt_un");
		Mockito.doReturn("somepassword").when(request).getParameter("bt_pw");
		Mockito.doReturn("Blocked").when(session).getAttribute("someuser");
		Mockito.when(udao.readByLogin("someuser", "somepassword")).thenReturn(null);
		//when
		lc.doPost(request, response);
		//then
		Mockito.verify(response).sendRedirect("index.jsp");
	}
	
	@Test
	public void testRedirectInvalidDetailsFirstCounter() throws ServletException, IOException {
		//given
		Mockito.doReturn("someuser").when(request).getParameter("bt_un");
		Mockito.doReturn("somepassword").when(request).getParameter("bt_pw");
		Mockito.doReturn("1").when(session).getAttribute("someuser");
		Mockito.when(udao.readByLogin("someuser", "somepassword")).thenReturn(null);
		//when
		lc.doPost(request, response);
		//then
		Mockito.verify(response).sendRedirect("index.jsp");
	}
	
	@Test
	public void testRedirectInvalidDetailsSecondCounter() throws ServletException, IOException {
		//given
		Mockito.doReturn("someuser").when(request).getParameter("bt_un");
		Mockito.doReturn("somepassword").when(request).getParameter("bt_pw");
		Mockito.doReturn("2").when(session).getAttribute("someuser");
		Mockito.when(udao.readByLogin("someuser", "somepassword")).thenReturn(null);
		//when
		lc.doPost(request, response);
		//then
		Mockito.verify(response).sendRedirect("index.jsp");
	}
	
	@Test
	public void testRedirectInvalidDetailsThirdCounter() throws ServletException, IOException {
		//given
		Mockito.doReturn("someuser").when(request).getParameter("bt_un");
		Mockito.doReturn("somepassword").when(request).getParameter("bt_pw");
		Mockito.doReturn("3").when(session).getAttribute("someuser");
		Mockito.when(udao.readByLogin("someuser", "somepassword")).thenReturn(null);
		//when
		lc.doPost(request, response);
		//then
		Mockito.verify(response).sendRedirect("index.jsp");
	}
	
	@Test
	public void testRedirectInvalidDetailsFourthCounter() throws ServletException, IOException {
		//given
		Mockito.doReturn("someuser").when(request).getParameter("bt_un");
		Mockito.doReturn("somepassword").when(request).getParameter("bt_pw");
		Mockito.doReturn("4").when(session).getAttribute("someuser");
		Mockito.when(udao.readByLogin("someuser", "somepassword")).thenReturn(null);
		//when
		lc.doPost(request, response);
		//then
		Mockito.verify(response).sendRedirect("index.jsp");
	}
	
	@Test
	public void testRedirectTimeset() throws ServletException, IOException {
		//given
		Mockito.doReturn("someuser").when(request).getParameter("bt_un");
		Mockito.doReturn("somepassword").when(request).getParameter("bt_pw");
		Mockito.doReturn("timeset").when(request).getParameter("someuser-blocked");
		Mockito.when(udao.readByLogin("someuser", "somepassword")).thenReturn(null);
		//when
		lc.doPost(request, response);
		//then
		Mockito.verify(response).sendRedirect("index.jsp");
	}
	
	// Correct Details
	@Test
	public void testCorrectDetails() throws ServletException, IOException {
		//given
		Mockito.doReturn("jdoe21").when(request).getParameter("bt_un");
		Mockito.doReturn("somepassword").when(request).getParameter("bt_pw");
		Mockito.doReturn("timeset").when(request).getParameter("someuser-blocked");
		Mockito.when(udao.readByLogin("jdoe21", "somepassword")).thenReturn(user);
		//when
		lc.doPost(request, response);
		//then
		Mockito.verify(response).sendRedirect("home.jsp");
	}
}
