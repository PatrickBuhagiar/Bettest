package com.test.site.bt.controller;

import java.io.IOException;

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

import com.bettest.site.bt.controller.LogoutController;
import com.bettest.site.bt.model.User;

public class LogoutControllerTest {
	private LogoutController lc;
	private HttpServletRequest 	request;
	private HttpServletResponse response;
	private HttpSession 		session;

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
		Mockito.doReturn(session).when(request).getSession();
		lc = new LogoutController();
	}

	@After
	public void tearDown() throws Exception {
		request = null;
		response = null;
		session = null;
		lc = null;
	}
	@Test
	public void testCurrentUserNull() throws ServletException, IOException {
		//given
		Mockito.doReturn(null).when(session).getAttribute("logged-user");
		//when
		lc.doPost(request, response);
		//then
		Mockito.verify(response).sendRedirect("home.jsp");
	}
	@Test
	public void testCurrentUserNotNull() throws ServletException, IOException {
		//given
		Mockito.doReturn(new User()).when(session).getAttribute("logged-user");
		//when
		lc.doPost(request, response);
		//then
		Mockito.verify(response).sendRedirect("index.jsp");
	}
	/*
	@Test
	public void test() {
		fail("Not yet implemented");
	}
	*/
}
