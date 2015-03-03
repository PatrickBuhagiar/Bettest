package com.test.site.bt.controller;

import static org.junit.Assert.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.RandomUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bettest.site.bt.controller.LoginController;
import com.bettest.site.bt.controller.RegistrationController;
import com.bettest.site.bt.dao.ImplCardDAO;
import com.bettest.site.bt.dao.ImplUserDAO;
import com.bettest.site.bt.model.Card;
import com.bettest.site.bt.model.User;
import com.bettest.site.bt.util.Validation;

/*
 * IMPORTANT
 * Refering to the known problems section in the report, altering with registration controller 
 * has made these unit tests fail. The unit tests in the documentation refer to before implementing
 * the model. 
 * 
 */


public class RegistrationControllerTest extends Mockito {
	
	private RegistrationController rc;
	// test
	private HttpServletRequest 	request;
	private HttpServletResponse response;
	private HttpSession 		session;
	private Card				card;
	private ImplCardDAO			cdao;
	private User 				user;
	private ImplUserDAO			udao;
	private Validation 			validator;
	private RandomUtils 		random;
	private String				ranuserName;
	
	@Before 
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		request = Mockito.mock(HttpServletRequest.class);
		response = Mockito.mock(HttpServletResponse.class);
		session = Mockito.mock(HttpSession.class);
		cdao = Mockito.mock(ImplCardDAO.class);
		Mockito.doReturn(session).when(request).getSession();
		card = Mockito.mock(Card.class);
		rc = Mockito.spy(new RegistrationController());
		user = Mockito.mock(User.class);
		udao = Mockito.mock(ImplUserDAO.class);
		validator = Mockito.mock(Validation.class);
		random = new RandomUtils();
		ranuserName = "username" + random.nextInt(100, 5000);
		Mockito.doReturn(ranuserName).when(request).getParameter("reg-username");
		Mockito.doReturn("password").when(request).getParameter("reg-password");
		Mockito.doReturn("password").when(request).getParameter("reg-confirmpassword");
		Mockito.doReturn("e@gmail.com").when(request).getParameter("reg-email");
		Mockito.doReturn("Patrick").when(request).getParameter("reg-name");
		Mockito.doReturn("Buhagiar").when(request).getParameter("reg-surname");
		Mockito.doReturn("12/12/1990").when(request).getParameter("reg-dob");
		Mockito.doReturn("1").when(request).getParameter("reg-country");
		Mockito.doReturn("t5b 11 Somewhere").when(request).getParameter("reg-address");
		Mockito.doReturn("Sliema").when(request).getParameter("reg-city");
		Mockito.doReturn("SLM001").when(request).getParameter("reg-pcode");
		Mockito.doReturn("1").when(request).getParameter("reg-card");
		Mockito.doReturn("PATRICK").when(request).getParameter("reg-cardholder");
		Mockito.doReturn("4532952436613849").when(request).getParameter("reg-cardno");
		Mockito.doReturn("12/20").when(request).getParameter("reg-expdate");
		Mockito.doReturn("333").when(request).getParameter("reg-ccv");
		Mockito.doReturn("on").when(request).getParameter("reg-acc");
	}	

	@After
	public void tearDown() throws Exception {
		request = null;
		response = null;
		session = null;
		cdao = null;
		card = null;
		rc = null;
		user = null;
		udao = null;
		validator = null;
	}
	
//	@Test
//	public void testEmptyMessage() throws ServletException, IOException, ParseException{
//		Mockito.doReturn(true).when((rc)).validation(request, cdao, udao);
//		rc.doPost(request, response);
//		assertEquals("", rc.message.toString());
//		Mockito.verify(response).sendRedirect("index.jsp");
//	}
	
	@Test
	public void testFullMessage() throws ServletException, IOException, ParseException{
		Mockito.doReturn(false).when((rc)).validation(request, cdao, udao);
		rc.doPost(request, response);
		Mockito.verify(response).sendRedirect("index.jsp");
	}
	
	
	@Test
	public void testValidateNullAccType() throws ServletException, IOException, ParseException{
		Mockito.doReturn(ranuserName).when(request).getParameter("reg-username");
		Mockito.doReturn("password").when(request).getParameter("reg-password");
		Mockito.doReturn("password").when(request).getParameter("reg-confirmpassword");
		Mockito.doReturn("e@gmail.com").when(request).getParameter("reg-email");
		Mockito.doReturn("Patrick").when(request).getParameter("reg-name");
		Mockito.doReturn("Buhagiar").when(request).getParameter("reg-surname");
		Mockito.doReturn("12/12/1990").when(request).getParameter("reg-dob");
		Mockito.doReturn("1").when(request).getParameter("reg-country");
		Mockito.doReturn("t5b 11 Somewhere").when(request).getParameter("reg-address");
		Mockito.doReturn("Sliema").when(request).getParameter("reg-city");
		Mockito.doReturn("SLM001").when(request).getParameter("reg-pcode");
		Mockito.doReturn("1").when(request).getParameter("reg-card");
		Mockito.doReturn("PATRICK").when(request).getParameter("reg-cardholder");
		Mockito.doReturn("4532952436613849").when(request).getParameter("reg-cardno");
		Mockito.doReturn("12/20").when(request).getParameter("reg-expdate");
		Mockito.doReturn("333").when(request).getParameter("reg-ccv");
		Mockito.doReturn(null).when(request).getParameter("reg-acc");
		Mockito.doReturn(1).when(cdao).create(card);
		Mockito.doReturn(1).when(udao).create(user);
		assertEquals(false,rc.validation(request, cdao, udao));
	}
	
	
	
	
	@Test
	public void testValidateGood() throws ServletException, IOException, ParseException{
		ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Module.xml");
		ImplUserDAO udaoFlush = (ImplUserDAO) context.getBean("ImplUserDAO");
		udaoFlush.flushUser(ranuserName, true);
		Mockito.when(udao.find_username(ranuserName)).thenReturn(null);
		Mockito.doReturn(null).when(udao).find_username(ranuserName);
		Mockito.doReturn(true).when(validator).validate_ccv("333");
		Mockito.doReturn(true).when(validator).validate_credit_card("4532952436613849");
		Mockito.doReturn(true).when(validator).validate_dob("12/12/1990");
		Mockito.doReturn(true).when(validator).validate_email("e@gmail.com");
		Mockito.doReturn(true).when(validator).validate_exp_date("12/20");
		Mockito.doReturn(true).when(validator).validate_name("Patrick");
		Mockito.doReturn(true).when(validator).validate_name("Buhagiar");
		Mockito.doReturn(true).when(validator).validate_password("password");
		//Mockito.when(validator.validate_username(ranuserName,udao)).thenReturn(true);
		Mockito.doReturn(true).when(validator).validate_username(ranuserName,udao);
		Mockito.doReturn(1).when(cdao).create(card);
		Mockito.doReturn(1).when(udao).create(user);
		udaoFlush.flushUser(ranuserName, true);
		assertEquals(true,rc.validation(request, cdao, udaoFlush));
	}
	
	
	@Test
	public void testValidateBadUsernameRet() throws ServletException, IOException, ParseException{
		ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Module.xml");
		ImplUserDAO udaoFlush = (ImplUserDAO) context.getBean("ImplUserDAO");
		udaoFlush.flushUser("username2", true);
		Mockito.when(udao.find_username("username2")).thenReturn(null);
		Mockito.doReturn(null).when(udao).find_username("username2");
		Mockito.doReturn(true).when(validator).validate_ccv("333");
		Mockito.doReturn(true).when(validator).validate_credit_card("4532952436613849");
		Mockito.doReturn(true).when(validator).validate_dob("12/12/1990");
		Mockito.doReturn(true).when(validator).validate_email("e@gmail.com");
		Mockito.doReturn(true).when(validator).validate_exp_date("12/20");
		Mockito.doReturn(true).when(validator).validate_name("Patrick");
		Mockito.doReturn(true).when(validator).validate_name("Buhagiar");
		Mockito.doReturn(true).when(validator).validate_password("password");
		//Mockito.when(validator.validate_username(ranuserName,udao)).thenReturn(true);
		Mockito.doReturn(true).when(validator).validate_username(ranuserName,udao);
		Mockito.doReturn(1).when(cdao).create(card);
		Mockito.doReturn(1).when(udao).create(user);
		udaoFlush.flushUser("username2", true);
		assertEquals(false,rc.validation(request, cdao, udao));
	}
	/*
	 * Mockito.doReturn(true).when(validator).validate_username(username,udao);
		Mockito.doReturn(true).when(validator).validate_password(password);
		Mockito.doReturn(true).when(validator).validate_email(email);
		Mockito.doReturn(true).when(validator).validate_name(name);
		Mockito.doReturn(true).when(validator).validate_name(surname);
		Mockito.doReturn(true).when(validator).validate_dob(dob);
		Mockito.doReturn(true).when(validator).validate_empty(country);
		Mockito.doReturn(true).when(validator).validate_empty(address);
		Mockito.doReturn(true).when(validator).validate_empty(city);
		Mockito.doReturn(true).when(validator).validate_empty(postcode);
		Mockito.doReturn(true).when(validator).validate_empty(cardholder);
		Mockito.doReturn(true).when(validator).validate_credit_card(cardno);
		Mockito.doReturn(true).when(validator).validate_exp_date(expdate);
		Mockito.doReturn(true).when(validator).validate_ccv(ccv);
 */
	@Test
	public void testValidateBadPasswordRet() throws ServletException, IOException, ParseException{
		ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Module.xml");
		ImplUserDAO udaoFlush = (ImplUserDAO) context.getBean("ImplUserDAO");
		udaoFlush.flushUser(ranuserName, true);
		Mockito.when(udao.find_username(ranuserName)).thenReturn(null);
		Mockito.doReturn(false).when(validator).validate_username(ranuserName,udao);
		Mockito.doReturn(true).when(validator).validate_password("password");
		Mockito.doReturn(true).when(validator).validate_email("e@gmail.com");
		Mockito.doReturn(true).when(validator).validate_name("Patrick");
		Mockito.doReturn(true).when(validator).validate_name("Buhagiar");
		Mockito.doReturn(true).when(validator).validate_dob("12/12/1990");
		Mockito.doReturn(true).when(validator).validate_empty("1");
		Mockito.doReturn(true).when(validator).validate_empty("t5b 11 Somewhere");
		Mockito.doReturn(true).when(validator).validate_empty("Sliema");
		Mockito.doReturn(true).when(validator).validate_empty("SLM001");
		Mockito.doReturn(true).when(validator).validate_empty("PATRICK");
		Mockito.doReturn(true).when(validator).validate_credit_card("4532952436613849");
		Mockito.doReturn(true).when(validator).validate_exp_date("12/20");
		Mockito.doReturn(true).when(validator).validate_ccv("333");
		
		
		Mockito.doReturn(1).when(cdao).create(card);
		Mockito.doReturn(1).when(udao).create(user);
		udaoFlush.flushUser("username2", true);
		assertEquals(false,rc.validation(request, cdao, udao));
	}
	@Test
	public void testValidateBadCVV() throws ServletException, IOException, ParseException{
		
		Mockito.doReturn(null).when(udao).find_username(ranuserName);
		Mockito.doReturn(false).when(validator).validate_ccv("333");
		Mockito.doReturn(true).when(validator).validate_credit_card("4532952436613849");
		Mockito.doReturn(true).when(validator).validate_dob("12/12/1990");
		Mockito.doReturn(true).when(validator).validate_email("e@gmail.com");
		Mockito.doReturn(true).when(validator).validate_exp_date("12/20");
		Mockito.doReturn(true).when(validator).validate_name("Patrick");
		Mockito.doReturn(true).when(validator).validate_name("Buhagiar");
		Mockito.doReturn(true).when(validator).validate_password("password");
		Mockito.doReturn(true).when(validator).validate_username(ranuserName,udao);
		
		Mockito.doReturn(1).when(cdao).create(card);
		Mockito.doReturn(1).when(udao).create(user);
		Mockito.doReturn(false).when(rc).validation(request, cdao, udao);
		//rc.validation(request, cdao, udao);
		verify(udao,never()).create(any(User.class));
		verify(cdao,never()).create(any(Card.class));
	}
	@Test
	public void testValidateBadCreditCard() throws ServletException, IOException, ParseException{
		
		Mockito.doReturn(null).when(udao).find_username(ranuserName);
		Mockito.doReturn(true).when(validator).validate_ccv("333");
		Mockito.doReturn(false).when(validator).validate_credit_card("4532952436613849");
		Mockito.doReturn(true).when(validator).validate_dob("12/12/1990");
		Mockito.doReturn(true).when(validator).validate_email("e@gmail.com");
		Mockito.doReturn(true).when(validator).validate_exp_date("12/20");
		Mockito.doReturn(true).when(validator).validate_name("Patrick");
		Mockito.doReturn(true).when(validator).validate_name("Buhagiar");
		Mockito.doReturn(true).when(validator).validate_password("password");
		Mockito.doReturn(true).when(validator).validate_username(ranuserName,udao);
		
		Mockito.doReturn(1).when(cdao).create(card);
		Mockito.doReturn(1).when(udao).create(user);
		Mockito.doReturn(false).when(rc).validation(request, cdao, udao);
		//rc.validation(request, cdao, udao);
		verify(udao,never()).create(any(User.class));
		verify(cdao,never()).create(any(Card.class));
	}
	@Test
	public void testValidateBadDoB() throws ServletException, IOException, ParseException{
		
		Mockito.doReturn(null).when(udao).find_username(ranuserName);
		Mockito.doReturn(true).when(validator).validate_ccv("333");
		Mockito.doReturn(true).when(validator).validate_credit_card("4532952436613849");
		Mockito.doReturn(false).when(validator).validate_dob("12/12/1990");
		Mockito.doReturn(true).when(validator).validate_email("e@gmail.com");
		Mockito.doReturn(true).when(validator).validate_exp_date("12/20");
		Mockito.doReturn(true).when(validator).validate_name("Patrick");
		Mockito.doReturn(true).when(validator).validate_name("Buhagiar");
		Mockito.doReturn(true).when(validator).validate_password("password");
		Mockito.doReturn(true).when(validator).validate_username(ranuserName,udao);
		
		Mockito.doReturn(1).when(cdao).create(card);
		Mockito.doReturn(1).when(udao).create(user);
		Mockito.doReturn(false).when(rc).validation(request, cdao, udao);
		//rc.validation(request, cdao, udao);
		verify(udao,never()).create(any(User.class));
		verify(cdao,never()).create(any(Card.class));
	}
	@Test
	public void testValidateBadEmail() throws ServletException, IOException, ParseException{
		
		Mockito.doReturn(null).when(udao).find_username(ranuserName);
		Mockito.doReturn(true).when(validator).validate_ccv("333");
		Mockito.doReturn(true).when(validator).validate_credit_card("4532952436613849");
		Mockito.doReturn(true).when(validator).validate_dob("12/12/1990");
		Mockito.doReturn(false).when(validator).validate_email("e@gmail.com");
		Mockito.doReturn(true).when(validator).validate_exp_date("12/20");
		Mockito.doReturn(true).when(validator).validate_name("Patrick");
		Mockito.doReturn(true).when(validator).validate_name("Buhagiar");
		Mockito.doReturn(true).when(validator).validate_password("password");
		Mockito.doReturn(true).when(validator).validate_username(ranuserName,udao);
		
		Mockito.doReturn(1).when(cdao).create(card);
		Mockito.doReturn(1).when(udao).create(user);
		Mockito.doReturn(false).when(rc).validation(request, cdao, udao);
		//rc.validation(request, cdao, udao);
		verify(udao,never()).create(any(User.class));
		verify(cdao,never()).create(any(Card.class));
	}
	@Test
	public void testValidateBadExpDate() throws ServletException, IOException, ParseException{
		
		Mockito.doReturn(null).when(udao).find_username(ranuserName);
		Mockito.doReturn(true).when(validator).validate_ccv("333");
		Mockito.doReturn(true).when(validator).validate_credit_card("4532952436613849");
		Mockito.doReturn(true).when(validator).validate_dob("12/12/1990");
		Mockito.doReturn(true).when(validator).validate_email("e@gmail.com");
		Mockito.doReturn(false).when(validator).validate_exp_date("12/20");
		Mockito.doReturn(true).when(validator).validate_name("Patrick");
		Mockito.doReturn(true).when(validator).validate_name("Buhagiar");
		Mockito.doReturn(true).when(validator).validate_password("password");
		Mockito.doReturn(true).when(validator).validate_username(ranuserName,udao);
		
		Mockito.doReturn(1).when(cdao).create(card);
		Mockito.doReturn(1).when(udao).create(user);
		Mockito.doReturn(false).when(rc).validation(request, cdao, udao);
		//rc.validation(request, cdao, udao);
		verify(udao,never()).create(any(User.class));
		verify(cdao,never()).create(any(Card.class));
	}
	@Test
	public void testValidateBadName() throws ServletException, IOException, ParseException{
		
		Mockito.doReturn(null).when(udao).find_username(ranuserName);
		Mockito.doReturn(true).when(validator).validate_ccv("333");
		Mockito.doReturn(true).when(validator).validate_credit_card("4532952436613849");
		Mockito.doReturn(true).when(validator).validate_dob("12/12/1990");
		Mockito.doReturn(true).when(validator).validate_email("e@gmail.com");
		Mockito.doReturn(true).when(validator).validate_exp_date("12/20");
		Mockito.doReturn(false).when(validator).validate_name("Patrick");
		Mockito.doReturn(true).when(validator).validate_name("Buhagiar");
		Mockito.doReturn(true).when(validator).validate_password("password");
		Mockito.doReturn(true).when(validator).validate_username(ranuserName,udao);
		
		Mockito.doReturn(1).when(cdao).create(card);
		Mockito.doReturn(1).when(udao).create(user);
		Mockito.doReturn(false).when(rc).validation(request, cdao, udao);
		//rc.validation(request, cdao, udao);
		verify(udao,never()).create(any(User.class));
		verify(cdao,never()).create(any(Card.class));
	}
	@Test
	public void testValidateBadSurname() throws ServletException, IOException, ParseException{
		
		Mockito.doReturn(null).when(udao).find_username(ranuserName);
		Mockito.doReturn(true).when(validator).validate_ccv("333");
		Mockito.doReturn(true).when(validator).validate_credit_card("4532952436613849");
		Mockito.doReturn(true).when(validator).validate_dob("12/12/1990");
		Mockito.doReturn(true).when(validator).validate_email("e@gmail.com");
		Mockito.doReturn(true).when(validator).validate_exp_date("12/20");
		Mockito.doReturn(true).when(validator).validate_name("Patrick");
		Mockito.doReturn(false).when(validator).validate_name("Buhagiar");
		Mockito.doReturn(true).when(validator).validate_password("password");
		Mockito.doReturn(true).when(validator).validate_username(ranuserName,udao);
		
		Mockito.doReturn(1).when(cdao).create(card);
		Mockito.doReturn(1).when(udao).create(user);
		Mockito.doReturn(false).when(rc).validation(request, cdao, udao);
		//rc.validation(request, cdao, udao);
		verify(udao,never()).create(any(User.class));
		verify(cdao,never()).create(any(Card.class));
	}
	@Test
	public void testValidateBadPassword() throws ServletException, IOException, ParseException{
		
		Mockito.doReturn(null).when(udao).find_username(ranuserName);
		Mockito.doReturn(true).when(validator).validate_ccv("333");
		Mockito.doReturn(true).when(validator).validate_credit_card("4532952436613849");
		Mockito.doReturn(true).when(validator).validate_dob("12/12/1990");
		Mockito.doReturn(true).when(validator).validate_email("e@gmail.com");
		Mockito.doReturn(true).when(validator).validate_exp_date("12/20");
		Mockito.doReturn(true).when(validator).validate_name("Patrick");
		Mockito.doReturn(true).when(validator).validate_name("Buhagiar");
		Mockito.doReturn(false).when(validator).validate_password("password");
		Mockito.doReturn(true).when(validator).validate_username(ranuserName,udao);
		
		Mockito.doReturn(1).when(cdao).create(card);
		Mockito.doReturn(1).when(udao).create(user);
		Mockito.doReturn(false).when(rc).validation(request, cdao, udao);
		//rc.validation(request, cdao, udao);
		verify(udao,never()).create(any(User.class));
		verify(cdao,never()).create(any(Card.class));
	}
	@Test
	public void testValidateBadUsername() throws ServletException, IOException, ParseException{
		
		Mockito.doReturn(null).when(udao).find_username(ranuserName);
		Mockito.doReturn(true).when(validator).validate_ccv("333");
		Mockito.doReturn(true).when(validator).validate_credit_card("4532952436613849");
		Mockito.doReturn(true).when(validator).validate_dob("12/12/1990");
		Mockito.doReturn(true).when(validator).validate_email("e@gmail.com");
		Mockito.doReturn(true).when(validator).validate_exp_date("12/20");
		Mockito.doReturn(true).when(validator).validate_name("Patrick");
		Mockito.doReturn(true).when(validator).validate_name("Buhagiar");
		Mockito.doReturn(true).when(validator).validate_password("password");
		Mockito.doReturn(false).when(validator).validate_username(ranuserName,udao);
		
		Mockito.doReturn(1).when(cdao).create(card);
		Mockito.doReturn(1).when(udao).create(user);
		Mockito.doReturn(false).when(rc).validation(request, cdao, udao);
		//rc.validation(request, cdao, udao);
		verify(udao,never()).create(any(User.class));
		verify(cdao,never()).create(any(Card.class));
	}
	@Test
	public void testValidateBadAll() throws ServletException, IOException, ParseException{
		
		Mockito.doReturn(null).when(udao).find_username(ranuserName);
		Mockito.doReturn(false).when(validator).validate_ccv("333");
		Mockito.doReturn(false).when(validator).validate_credit_card("4532952436613849");
		Mockito.doReturn(false).when(validator).validate_dob("12/12/1990");
		Mockito.doReturn(false).when(validator).validate_email("e@gmail.com");
		Mockito.doReturn(false).when(validator).validate_exp_date("12/20");
		Mockito.doReturn(false).when(validator).validate_name("Patrick");
		Mockito.doReturn(false).when(validator).validate_name("Buhagiar");
		Mockito.doReturn(false).when(validator).validate_password("password");
		Mockito.doReturn(false).when(validator).validate_username(ranuserName,udao);
		
		Mockito.doReturn(1).when(cdao).create(card);
		Mockito.doReturn(1).when(udao).create(user);
		Mockito.doReturn(false).when(rc).validation(request, cdao, udao);
		verify(udao,never()).create(any(User.class));
		verify(cdao,never()).create(any(Card.class));
		//rc.validation(request, cdao, udao);
		
	}
}
