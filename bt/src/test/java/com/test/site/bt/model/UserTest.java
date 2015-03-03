package com.test.site.bt.model;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bettest.site.bt.model.User;

public class UserTest {
	User theUser;
	SimpleDateFormat sdf;
	@Before
	public void setUp() throws Exception {
		sdf = new SimpleDateFormat("yyyy-MM-dd");
		theUser = new User(1,1,2,"John","somepassword","john@john.com",
				"John","Doe",sdf.parse("1984-10-10")
				,"SomeAddress","New York","USA", "NY123");
	}

	@After
	public void tearDown() throws Exception {
		sdf = null;
		theUser = null;
	}

	@Test
	public void testUserFullConstructor() {
		assertNotEquals(theUser,null);
	}

	@Test
	public void testUser() {
		theUser = new User();
		assertNotEquals(theUser,null);
	}

	@Test
	public void testToString() {
		assertEquals("Username=John, password= somepassword",theUser.toString());
	}

	@Test
	public void testGetUser_id() {
		assertEquals(1,theUser.getUser_id());
	}

	@Test
	public void testSetUser_id() {
		theUser.setUser_id(2);
		assertNotEquals(1,theUser.getUser_id());
	}

	@Test
	public void testGetUser_type_id() {
		assertEquals(1,theUser.getUser_type_id());
	}

	@Test
	public void testSetUser_type_id() {
		theUser.setUser_type_id(0);
		assertNotEquals(1,theUser.getUser_type_id());
	}

	@Test
	public void testGetUser_card_id() {
		assertEquals(2,theUser.getUser_card_id());
	}

	@Test
	public void testSetUser_card_id() {
		theUser.setUser_card_id(1);
		assertNotEquals(2,theUser.getUser_card_id());
	}

	@Test
	public void testGetUser_username() {
		assertEquals("John", theUser.getUser_username());
	}

	@Test
	public void testSetUser_username() {
		theUser.setUser_username("John2323");
		assertNotEquals("John", theUser.getUser_username());
	}

	@Test
	public void testGetUser_password() {
		assertEquals("somepassword", theUser.getUser_password());
	}

	@Test
	public void testSetUser_password() {
		theUser.setUser_password("someotherpassword");
		assertNotEquals("somepassword", theUser.getUser_password());
	}

	@Test
	public void testGetUser_name() {
		assertEquals("John", theUser.getUser_name());
	}

	@Test
	public void testSetUser_name() {
		theUser.setUser_name("Jeremy");
		assertNotEquals("John", theUser.getUser_name());
	}

	@Test
	public void testGetUser_surname() {
		assertEquals("Doe", theUser.getUser_surname());
	}

	@Test
	public void testSetUser_surname() {
		theUser.setUser_surname("O'Doe");
		assertNotEquals("Doe", theUser.getUser_surname());
	}

	@Test
	public void testGetUser_address() {
		assertEquals("SomeAddress", theUser.getUser_address());
	}

	@Test
	public void testSetUser_address() {
		theUser.setUser_address("SomeOtherAddress");
		assertNotEquals("SomeAddress", theUser.getUser_address());
	}

	@Test
	public void testGetUser_city() {
		assertEquals("New York", theUser.getUser_city());
	}

	@Test
	public void testSetUser_city() {
		theUser.setUser_city("Boston");
		assertNotEquals("New York", theUser.getUser_city());
	}

	@Test
	public void testGetUser_country() {
		assertEquals("USA", theUser.getUser_country());
	}

	@Test
	public void testSetUser_country() {
		theUser.setUser_country("Malta");
		assertNotEquals("USA", theUser.getUser_country());
	}

	@Test
	public void testGetUser_postcode() {
		assertEquals("NY123", theUser.getUser_postcode());
	}

	@Test
	public void testSetUser_postcode() {
		theUser.setUser_postcode("NYC234");
		assertNotEquals("NY123", theUser.getUser_postcode());
	}

	@Test
	public void testGetUser_email() {
		assertEquals("john@john.com", theUser.getUser_email());
	}

	@Test
	public void testSetUser_email() {
		theUser.setUser_email("n@n.com");
		assertNotEquals("john@john.com", theUser.getUser_email());
	}

	@Test
	public void testGetUser_dob() throws ParseException {
		assertEquals(sdf.parse("1984-10-10"),theUser.getUser_dob());
	}

	@Test
	public void testSetUser_dob() throws ParseException {
		theUser.setUser_dob(sdf.parse("1984-10-11"));
		assertNotEquals(sdf.parse("1984-10-10"),theUser.getUser_dob());
	}

}
