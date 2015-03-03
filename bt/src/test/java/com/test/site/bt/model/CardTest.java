package com.test.site.bt.model;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bettest.site.bt.model.Bet;
import com.bettest.site.bt.model.Card;

public class CardTest {
	Card theCard;
	SimpleDateFormat sdf;
	@SuppressWarnings("deprecation")
	@Before
	public void setUp() throws Exception {
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		theCard = new Card(1,255,453295,"Visa","John", sdf.parse("02/10/2014"));
	}

	@After
	public void tearDown() throws Exception {
		theCard = null;
	}

	@Test
	public void testCardFullConstructor() {
		assertNotEquals(theCard,null);
	}

	@Test
	public void testCard() {
		theCard = new Card();
		assertNotEquals(theCard,null);
	}

	@Test
	public void testGetCard_id() {
		assertEquals(1,theCard.getCard_id());
	}

	@Test
	public void testSetCard_id() {
		theCard.setCard_id(2);
		assertNotEquals(1,theCard.getCard_id());
	}

	@Test
	public void testGetCard_cvv() {
		assertEquals(255,theCard.getCard_cvv());
	}

	@Test
	public void testSetCard_cvv() {
		theCard.setCard_cvv(256);
		assertNotEquals(255,theCard.getCard_cvv());
	}

	@Test
	public void testGetCard_number() {
		assertEquals(453295, theCard.getCard_number());
	}

	@Test
	public void testSetCard_number() {
		theCard.setCard_number(453297);
		assertNotEquals(453295, theCard.getCard_number());
	}

	@Test
	public void testGetCard_type_name() {
		assertEquals("Visa", theCard.getCard_type_name());
	}

	@Test
	public void testSetCard_type_name() {
		theCard.setCard_type_name("Mastercard");
		assertNotEquals("Visa", theCard.getCard_type_name());
	}

	@Test
	public void testGetCard_holder_name() {
		assertEquals("John", theCard.getCard_holder_name());
	}

	@Test
	public void testSetCard_holder_name() {
		theCard.setCard_holder_name("Jeremy");
		assertNotEquals("John", theCard.getCard_holder_name());
	}

	@Test
	public void testGetCard_exp_date() throws ParseException {
		assertEquals("2014-00-02", theCard.getCard_exp_date());
	}

	@Test
	public void testSetCard_exp_date() throws ParseException {
		theCard.setCard_exp_date(sdf.parse("03/10/2014"));
		assertNotEquals("2014-00-02", theCard.getCard_exp_date());
	}

}
