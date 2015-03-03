package com.test.site.bt.util;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bettest.site.bt.dao.ImplUserDAO;
import com.bettest.site.bt.model.User;
import com.bettest.site.bt.util.Utility;
import com.bettest.site.bt.util.Validation;

public class ValidationTest {
	Utility u;
	Validation v;
	User user;
	ClassPathXmlApplicationContext context;
	ImplUserDAO udao;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		u = new Utility();
		v = new Validation();
		user = new User();
		context = Mockito.mock(ClassPathXmlApplicationContext.class);
		udao = Mockito.mock(ImplUserDAO.class);
	}

	@After
	public void tearDown() throws Exception {
		u = null;
		v = null;
		user = null;
		context = null;
		udao = null;
	}

	//Validate_name
	@Test
	public void validate_nameTest_spaces(){
		assertTrue(v.validate_name("Patrick James"));
	}
	
	@Test
	public void validate_nameTest_alpha(){
		assertTrue(v.validate_name("Jake"));
	}
	
	@Test
	public void validate_nameTest_nonalpha(){
		assertFalse(v.validate_name("Patrick&Jake"));
	}
	
	@Test
	public void validate_nameTest_numeric(){
		assertFalse(v.validate_name("123"));
	}
	
	//Validate_Password	
	@Test
	public void validate_passwordTest_short(){
		assertFalse(v.validate_password("passwor"));
	}
	
	@Test
	public void validate_passwordTest_long(){
		assertTrue(v.validate_password("password"));
	}
	
	//Validate empty
	@Test
	public void validate_emptyTest_empty(){
		assertFalse(v.validate_empty(""));
	}
	
	@Test
	public void validate_emptyTest_nonempty(){
		assertTrue(v.validate_empty("!empty"));
	}
	
	@Test
	public void validate_emptyTest_null(){
		assertFalse(v.validate_empty(null));
	}
	
	//validate_integer
	@Test
	public void validate_integerTest_int(){
		assertTrue(v.validate_integer("12"));
	}
	
	@Test
	public void validate_integerTest_notint(){
		assertFalse(v.validate_integer("s12"));
	}
	
	@Test
	public void validate_integerTest_float(){
		assertFalse(v.validate_integer("12.2"));
	}
	
	//validate_longInt
	@Test
	public void validate_longIntTest_long(){
		assertTrue(v.validate_longInt("123123123123123"));
	}
	
	@Test
	public void validate_longIntTest_notlong(){
		assertFalse(v.validate_longInt("123123123123s131"));
	}
	
	//validate_decimal
	@Test
	public void validate_decimalTest_decimal(){
		assertTrue(v.validate_decimal("1000.21"));
	}
	
	@Test
	public void validate_decimalTest_notdecimal(){
		assertFalse(v.validate_decimal("f1222.2"));
	}
	
	//validate_credit_card
	@Test
	public void validate_credit_cardTest_Mastercard(){
		assertTrue(v.validate_credit_card("5500005555555559"));
	}
	
	@Test
	public void validate_credit_cardTest_AmericanExpress(){
		assertTrue(v.validate_credit_card("371449635398431"));
	}
	
	@Test
	public void validate_credit_cardTest_Visa(){
		assertTrue(v.validate_credit_card("4539863918734925"));
	}
	
	@Test
	public void validate_credit_cardTest_DinersClub(){
		assertFalse(v.validate_credit_card("36438936438936"));
	}
	
	//validate_email
	@Test
	public void validate_emailTest_good(){
		assertTrue(v.validate_email("patrickbhgr@gmail.com"));
	}
	
	@Test 
	public void validate_emailTest_bad(){
		assertFalse(v.validate_email("patrickbhgrgmail.com"));
	}
	
	//validate_date
	@Test
	public void validate_dateTest(){
		assertTrue(v.validate_date("12/12/1994"));
	}
	
	@Test
	public void validate_dateTest_notdate(){
		assertFalse(v.validate_date("12/2"));
	}
	
	@Test
	public void validate_dateTest_wrongFormat(){
		assertFalse(v.validate_date("12.12.2011"));
	}
	
	@Test
	public void validate_dateTest_invalidMonthday(){
		assertFalse(v.validate_date("33/14/2000"));
	}
	
	@Test
	public void validate_dateTest_null(){
		assertFalse(v.validate_date(null));
	}
	
	//validate_dob
	@Test
	public void validate_dobTest_under18(){
		assertFalse(v.validate_dob("12/12/2000"));
	}
	
	@Test
	public void validate_dobTest_over18(){
		assertTrue(v.validate_dob("12/12/1994"));
	}
	
	@Test
	public void validate_dobTest_notDate(){
		assertFalse(v.validate_dob("12122012"));
	}
	
	@Test
	public void validate_dobTest_null(){
		assertFalse(v.validate_dob(null));
	}
	//validate_username
	@Test
	public void validate_usernameTest_unique(){
		Mockito.when(udao.find_username("jk_dll")).thenReturn(null);
		assertTrue(v.validate_username("jk_dll",udao));
	}
	
	@Test
	public void validate_usernameTest_null(){
		Mockito.when(udao.find_username(null)).thenReturn(null);
		assertFalse(v.validate_username(null,udao));
	}
	
	@Test
	public void validate_usernameTest_Notunique(){
		Mockito.doReturn(user).when(udao).find_username("jk_dll");
		boolean k = v.validate_username("jk_dll",udao);
		assertFalse(k);
	}
	
	//validate_ccv
	@Test
	public void valdiate_ccvTest_large(){
		assertFalse(v.validate_ccv("1234"));
	}
	
	@Test
	public void valdiate_ccvTest_small(){
		assertFalse(v.validate_ccv("14"));
	}
	
	@Test
	public void valdiate_ccvTest_good(){
		assertTrue(v.validate_ccv("123"));
	}
	
	@Test
	public void valdiate_ccvTest_nan(){
		assertFalse(v.validate_ccv("1f4"));
	}
	
	//validate_exp_date
	@Test
	public void validate_exp_dateTest_past(){
		assertFalse(v.validate_exp_date("12/10"));
	}
	
	@Test
	public void validate_exp_dateTest_future(){
		assertTrue(v.validate_exp_date("12/20"));
	}
	
	@Test
	public void validate_exp_dateTest_null(){
		assertFalse(v.validate_exp_date(null));
	}
	
	@Test
	public void validate_exp_dateTest_notdate(){
		assertFalse(v.validate_exp_date("121009"));
	}
	
}
