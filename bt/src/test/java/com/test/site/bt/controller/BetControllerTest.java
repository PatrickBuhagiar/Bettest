package com.test.site.bt.controller;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

import com.bettest.site.bt.controller.BetController;
import com.bettest.site.bt.dao.ImplBetDAO;
import com.bettest.site.bt.model.Bet;
import com.bettest.site.bt.model.User;


public class BetControllerTest extends Mockito{
	private BetController bc;
	private HttpServletRequest 	request;
	private HttpServletResponse response;
	private HttpSession 		session;
	private User				user;
	private Bet					bet;
	private ImplBetDAO			bdao;
	private List<Bet>			betList;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		bdao = Mockito.mock(ImplBetDAO.class);
		request = Mockito.mock(HttpServletRequest.class);
		response = Mockito.mock(HttpServletResponse.class);
		session = Mockito.mock(HttpSession.class);
		user = Mockito.mock(User.class);
		bet = Mockito.mock(Bet.class);
		betList = Mockito.spy(new ArrayList<Bet>());
		bc = Mockito.spy(new BetController());
		
		doReturn(session).when(request).getSession();
	}
	
	//...
	@After
	public void tearDown() throws Exception {
		bdao = null;
		request = null;
		response = null;
		session = null;
		user = null;
		bet = null;
		betList = null;
		bc = Mockito.spy(new BetController());
	}

	@Test
	public void testDoGet() throws ServletException, IOException{
		Mockito.doReturn(1).when(user).getUser_id();
		Mockito.doReturn(user).when(session).getAttribute("logged-user");
		Mockito.doReturn(betList).when(bdao).readByUserId(1);
		bc.doGet(request,response);
		 Mockito.verify(session).setAttribute("logged-user-bets", new ArrayList());
	}
	
	@Test
	public void testNullCurrentUser() throws ServletException, IOException{
			Mockito.doReturn(null).when(session).getAttribute("logged-user");
		 bc.doPost(request,response);
		 
		 Mockito.verify(response).sendRedirect("index.jsp");
	}
	
	@Test
	public void testNullAmount() throws ServletException, IOException{
		Mockito.doReturn(user).when(session).getAttribute("logged-user");
		Mockito.doReturn(null).when(request).getParameter("bet-amount");
		Mockito.doReturn("1").when(request).getParameter("bet-risk");
		bc.doPost(request,response);
		
		Mockito.verify(response).sendRedirect("home.jsp");
	}
	
	@Test
	public void testNullRisk() throws ServletException, IOException{
		Mockito.doReturn(user).when(session).getAttribute("logged-user");
		Mockito.doReturn("12.5").when(request).getParameter("bet-amount");
		Mockito.doReturn(null).when(request).getParameter("bet-risk");
		bc.doPost(request,response);
		
		Mockito.verify(response).sendRedirect("home.jsp");
	}
	
	@Test
	public void testNotDecimalAmount() throws ServletException, IOException{
		Mockito.doReturn(user).when(session).getAttribute("logged-user");
		Mockito.doReturn("1a").when(request).getParameter("bet-amount");
		Mockito.doReturn("1").when(request).getParameter("bet-risk");
		bc.doPost(request,response);
		
		Mockito.verify(response).sendRedirect("home.jsp");
	}
	
	@Test
	public void testFalseResult() throws ServletException, IOException{
		Mockito.doReturn(user).when(session).getAttribute("logged-user");
		Mockito.doReturn("100.50").when(request).getParameter("bet-amount");
		Mockito.doReturn("1").when(request).getParameter("bet-risk");
		Mockito.doReturn(false).when((bc)).betConstraints(request, user,bet);
		
		bc.doPost(request,response);
		
		Mockito.verify(response).sendRedirect("home.jsp");
	}
	@Test
	public void testTrueResult() throws ServletException, IOException{
		Mockito.doReturn(user).when(session).getAttribute("logged-user");
		Mockito.doReturn("100.50").when(request).getParameter("bet-amount");
		Mockito.doReturn("1").when(request).getParameter("bet-risk");
		Mockito.doReturn(true).when((bc)).betConstraints(request, user,bet);
		Mockito.doReturn(1).when(bdao).create(bet);
		
		bc.doPost(request,response);
		
		Mockito.verify(response).sendRedirect("home.jsp");
	}
	
	@Test
	public void testFreeBetConstraintsGood() throws ServletException, IOException{
		Mockito.doReturn(1).when(bdao).create(bet);
		
		Mockito.doReturn(0).when(user).getUser_type_id();
		Mockito.doReturn(1).when(user).getUser_id();
		
		Mockito.doReturn((float) 4.0).when(bet).getBet_amount();
		
		Mockito.doReturn((long) 3).when(bdao).getCountByUserId(1);
		Mockito.doReturn((float) 5).when(bdao).getUserTotalBetted(1);
		Mockito.doReturn(1).when(bet).getBet_risk_level();
		
		assertTrue(bc.betConstraints(request,user,bet));		
	}
	
	@Test
	public void testFreeBetConstraintsBad() throws ServletException, IOException{
		Mockito.doReturn(1).when(bdao).create(bet);
		
		Mockito.doReturn(0).when(user).getUser_type_id();
		Mockito.doReturn(1).when(user).getUser_id();
		
		Mockito.doReturn((float) 6.0).when(bet).getBet_amount();
		
		Mockito.doReturn((long) 3).when(bdao).getCountByUserId(1);
		Mockito.doReturn((float) 9).when(bdao).getUserTotalBetted(1);
		Mockito.doReturn(2).when(bet).getBet_risk_level();
		
		assertFalse(bc.betConstraints(request,user,bet));		
	}
	
	@Test
	public void testFreeBetConstraintsBadCountRisk() throws ServletException, IOException{
		Mockito.doReturn(1).when(bdao).create(bet);
		
		Mockito.doReturn(0).when(user).getUser_type_id();
		Mockito.doReturn(1).when(user).getUser_id();
		
		Mockito.doReturn((float) 5.0).when(bet).getBet_amount();
		
		Mockito.doReturn((long) 5).when(bdao).getCountByUserId(1);
		Mockito.doReturn((float) 5).when(bdao).getUserTotalBetted(1);
		Mockito.doReturn(4).when(bet).getBet_risk_level();
		
		assertFalse(bc.betConstraints(request,user,bet));		
	}
	
	
	@Test
	public void testFreeBetConstraintsBadAmount() throws ServletException, IOException{
		Mockito.doReturn(1).when(bdao).create(bet);
		
		Mockito.doReturn(0).when(user).getUser_type_id();
		Mockito.doReturn(1).when(user).getUser_id();
		
		Mockito.doReturn((float) 6.0).when(bet).getBet_amount();
		
		Mockito.doReturn((long) 3).when(bdao).getCountByUserId(1);
		Mockito.doReturn((float) 4).when(bdao).getUserTotalBetted(1);
		Mockito.doReturn(1).when(bet).getBet_risk_level();
		
		assertFalse(bc.betConstraints(request,user,bet));		
	}
	
	@Test
	public void testFreeBetConstraintsBadCount() throws ServletException, IOException{
		Mockito.doReturn(1).when(bdao).create(bet);
		
		Mockito.doReturn(0).when(user).getUser_type_id();
		Mockito.doReturn(1).when(user).getUser_id();
		
		Mockito.doReturn((float) 4.0).when(bet).getBet_amount();
		
		Mockito.doReturn((long) 7).when(bdao).getCountByUserId(1);
		Mockito.doReturn((float) 5).when(bdao).getUserTotalBetted(1);
		Mockito.doReturn(1).when(bet).getBet_risk_level();
		
		assertTrue(bc.betConstraints(request,user,bet));		
	}
	
	@Test
	public void testFreeBetConstraintsBadRisk() throws ServletException, IOException{
		Mockito.doReturn(1).when(bdao).create(bet);
		
		Mockito.doReturn(0).when(user).getUser_type_id();
		Mockito.doReturn(2).when(user).getUser_id();
		
		Mockito.doReturn((float) 4.0).when(bet).getBet_amount();
		
		Mockito.doReturn((long) 3).when(bdao).getCountByUserId(1);
		Mockito.doReturn((float) 5).when(bdao).getUserTotalBetted(1);
		Mockito.doReturn(1).when(bet).getBet_risk_level();
		
		assertTrue(bc.betConstraints(request,user,bet));		
	}
	
	@Test
	public void testPremiumBetConstraintsGood() throws ServletException, IOException{
		Mockito.doReturn(1).when(bdao).create(bet);
		
		Mockito.doReturn(1).when(user).getUser_type_id();
		Mockito.doReturn(1).when(user).getUser_id();
		
		Mockito.doReturn((float) 4.0).when(bet).getBet_amount();
		
		Mockito.doReturn((long) 3).when(bdao).getCountByUserId(1);
		Mockito.doReturn((float) 5.0).when(bdao).getUserTotalBetted(1);
		Mockito.doReturn(1).when(bet).getBet_risk_level();
		
		assertTrue(bc.betConstraints(request,user,bet));		
	}
	
	@Test
	public void testErrorStringFreeUser() throws ServletException, IOException{
		//builErrorString(int userId, int userType, long betCount, float betTotal, float betAmount, boolean isLow)
		String r = bc.builErrorString(0, 0, 5, (float) 5, (float) 5, false);
		assertTrue(r.contains("ERROR: Free Users can only"));	
	}
	
	@Test
	public void testErrorStringFreeUserCount() throws ServletException, IOException{
		//builErrorString(int userId, int userType, long betCount, float betTotal, float betAmount, boolean isLow)
		String r = bc.builErrorString(0, 0, 5, (float) 5, (float) 5, false);
		assertTrue(r.contains("place up to 3 bets"));	
	}
	
	@Test
	public void testErrorStringPremiumUser() throws ServletException, IOException{
		//builErrorString(int userId, int userType, long betCount, float betTotal, float betAmount, boolean isLow)
		String r = bc.builErrorString(0, 1, 5, (float) 5, (float) 5, false);
		assertTrue(r.contains("ERROR: Premium Users can only"));	
	}
	
	@Test
	public void testErrorStringPremiumUserAmount() throws ServletException, IOException{
		//builErrorString(int userId, int userType, long betCount, float betTotal, float betAmount, boolean isLow)
		String r = bc.builErrorString(0, 1, 5, (float) 5, (float) 5001, false);
		assertTrue(r.contains("ERROR: Premium Users can only"));	
	}
	@Test
	public void testErrorStringPremiumUserAmountEqual() throws ServletException, IOException{
		//builErrorString(int userId, int userType, long betCount, float betTotal, float betAmount, boolean isLow)
		String r = bc.builErrorString(0, 1, 5, (float) 5, (float) 5000, false);
		assertTrue(r.contains("ERROR: Premium Users can only"));	
	}
	@Test
	public void testErrorStringOtherUserId() throws ServletException, IOException{
		//builErrorString(int userId, int userType, long betCount, float betTotal, float betAmount, boolean isLow)
		String r = bc.builErrorString(0, 3, 5, (float) 5, (float) 5000, false);
		assertFalse(r.contains("ERROR: Premium Users can only"));	
	}
}
