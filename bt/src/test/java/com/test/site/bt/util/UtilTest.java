/**
 * 
 */
package com.test.site.bt.util;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bettest.site.bt.dao.ImplBetDAO;
import com.bettest.site.bt.dao.ImplUserDAO;
import com.bettest.site.bt.model.Bet;
import com.bettest.site.bt.model.User;
import com.bettest.site.bt.util.Utility;

public class UtilTest {
	private Utility u; 
	private ImplBetDAO 	bdao;
	private User 	userToTest;
	private ArrayList<Bet> 	betList;
	private Bet mockedBet;
	private Utility spyUtility;
	private List<Bet> spyList;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		bdao = Mockito.mock(ImplBetDAO.class);
		userToTest = Mockito.mock(User.class);
		mockedBet = Mockito.mock(Bet.class);
		betList = Mockito.mock(ArrayList.class);
		spyList = Mockito.spy(new ArrayList<Bet>());
		spyUtility = Mockito.spy(new Utility());
		u = new Utility();
	}

	@After
	public void tearDown() throws Exception {
	}

	//DateToDb
	@Test
	public void DateToDbTest_goodFormat(){
		assertEquals("Wed Dec 12 00:00:00 CET 2012", u.DateToDb("12/12/2012").toString());
	}
	
	@Test
	public void DateToDbTest_badFormat(){
		assertEquals(null, u.DateToDb("12.12.2012"));
	}
	
	//DbToDate
	@Test
	public void DbToDateTest_goodFormat(){
		assertEquals("Wed Dec 12 00:00:00 CET 2012", u.DbToDate("2012-12-12").toString());
	}
	
	@Test
	public void DbToDateTest_badFormat(){
		assertEquals(null, u.DbToDate("12/12/2012"));
	}
	
	//ExpDateToDb
	@Test
	public void ExpDateToDbTest_goodFormat(){
		assertEquals("Sat Dec 01 00:00:00 CET 2012", u.ExpDateToDb("12/12").toString());
	}
	
	@Test
	public void ExpDateToDbTest_badFormat(){
		assertEquals(null, u.ExpDateToDb("12.12"));
	}

	
	@Test
	public void getBetsNullUser(){
		Mockito.doReturn(9999).when(userToTest).getUser_id();
		Mockito.doReturn(null).when(bdao).readByUserId(9999);
		String out = u.getBets(userToTest);
		assertTrue(out.contains("Your Have No Bets to View"));
	}
	
	@Test
	public void getBetsEmptyList(){
		Mockito.doReturn(0).when(betList).size();
		Mockito.doReturn(9999).when(userToTest).getUser_id();
		Mockito.doReturn(betList).when(bdao).readByUserId(9999);
		Mockito.doReturn(0).when(betList).size();
		String out = u.getBets(userToTest);
		assertTrue(out.contains("Your Have No Bets to View"));
	}
	
	@Test
	public void getBestListNull(){
		Mockito.doReturn(1).when(userToTest).getUser_id();
		Mockito.doReturn(null).when(bdao).readByUserId(1);
		String out = u.getBets(userToTest);
		System.out.println(out);
		assertTrue(out.contains("Your Have No Bets to View"));
	}
	
	@Test
	public void getBetNullParam(){
		Mockito.doReturn(5).when(betList).size();
		assertTrue(u.getBets(null).contains("Your Have No Bets to View"));
	}
	
	@Test
	public void getBetNullParamEmptyList(){
		Mockito.doReturn(0).when(betList).size();
		assertTrue(u.getBets(null).contains("Your Have No Bets to View"));
	}
	
	@Test
	public void getBestNullList(){
		List<Bet> someList = new ArrayList<Bet>();
		someList.add(new Bet());
		Mockito.doReturn(null).when(bdao).readByUserId(1);
		assertTrue(u.getBets(userToTest).contains("Your Have No Bets to View"));
	}
	
	@Test
	public void getBetListExistsNullUser(){
		ArrayList<Bet> mocked = Mockito.mock(ArrayList.class);
		Mockito.doReturn(1).when(mocked).size();
		Mockito.doReturn(mocked).when(bdao).readByUserId(1);
		assertTrue(u.getBets(userToTest).contains("Your Have No Bets to View"));
	}
	
	@Test
	public void getBetListExists(){
		Mockito.doReturn("Your Previously Placed Bets").when(spyUtility).getBets(userToTest);
		assertTrue(spyUtility.getBets(userToTest).contains("Your Previously Placed Bets"));
	} 
}
