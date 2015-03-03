package com.test.site.bt.model;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bettest.site.bt.model.Bet;

public class BetTest {
	Bet theBet;
	
	@Before
	public void setUp() throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = dateFormat.parse("02/11/2014");
		theBet = new Bet(1, 2, 1, 5, new Timestamp(date.getTime()));
	}

	@After
	public void tearDown() throws Exception {
		theBet = null;
	}

	@Test
	public void testBet() {
		theBet = new Bet();
		assertNotEquals(theBet,null);
	}

	@Test
	public void testBetFullConstructor() {
		assertNotEquals(theBet, null);
	}

	@Test
	public void testGetBet_id() {
		assertEquals(1,theBet.getBet_id());
	}

	@Test
	public void testSetBet_id() {
		theBet.setBet_id(2);
		assertNotEquals(1,theBet.getBet_id());
	}

	@Test
	public void testGetBet_user_id() {
		assertEquals(2,theBet.getBet_user_id());
	}

	@Test
	public void testSetBet_user_id() {
		theBet.setBet_user_id(1);
		assertNotEquals(2,theBet.getBet_user_id());
	}

	@Test
	public void testGetBet_risk_level() {
		assertEquals(1,theBet.getBet_risk_level());
	}

	@Test
	public void testSetBet_risk_level() {
		theBet.setBet_risk_level(3);
		assertNotEquals(1,theBet.getBet_risk_level());
	}

	@Test
	public void testGetBet_amount() {
		assertEquals((float) 5,theBet.getBet_amount(), 0.001);
	}

	@Test
	public void testSetBet_amount() {
		theBet.setBet_amount(6);
		assertNotEquals(5,theBet.getBet_amount(), 0.001);
	}

	@Test
	public void testGetBet_timestamp() {
		assertEquals("2014-11-02 00:00:00.0",theBet.getBet_timestamp().toString());
	}

	@Test
	public void testSetBet_timestamp() {
		theBet.setBet_timestamp(new Timestamp(System.currentTimeMillis()));
		assertNotEquals("2014-10-02 00:00:00.0",theBet.getBet_timestamp().toString());		
	}
}
