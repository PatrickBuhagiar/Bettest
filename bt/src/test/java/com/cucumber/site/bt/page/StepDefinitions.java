package com.cucumber.site.bt.page;

import java.net.UnknownHostException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.bettest.site.bt.dao.ImplUserDAO;
import com.selenium.site.bt.page.BetForm;
import com.selenium.site.bt.page.LoginForm;
import com.selenium.site.bt.page.RegisterForm;

import org.openqa.selenium.WebElement;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.junit.Assert.*;

public class StepDefinitions {
		
	WebDriver browser;
	LoginForm login;
	RegisterForm register;
	BetForm bet;
	ImplUserDAO udao;
	ClassPathXmlApplicationContext context;
	
	@Before
	public void init() {
		//System.setProperty("webdriver.chrome.driver", "chromedriver.exe"); 
		browser = new FirefoxDriver();
		context = new ClassPathXmlApplicationContext("Spring-Module.xml");
		udao = (ImplUserDAO) context.getBean("ImplUserDAO");
		//bet = new BetForm(browser);
		//register = new RegisterForm(browser);
	}
	
	@After
	public void tearDown() throws UnknownHostException {
		//ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Module.xml");
		//ImplUserDAO udao =(ImplUserDAO) context.getBean("ImplUserDAO");
		bet = null;
		udao = null;
		login = null;
		browser.quit();
		
	}

	@Given("^I am a user trying to register$")
	public void trying_to_register() throws Throwable {
		udao.flushUser("LisaSimmm",true);
		register = new RegisterForm(browser);
		register.GoTo();	
	}
	

	@When("^I register providing correct information$")
	public void i_register_providing_correct_information() throws Throwable {
		register.FillForm();
		register.Submit("submit_register");
	}
	
	@Then("^I should be told that the registration was successful$")
	public void Successful_registration() throws Throwable {
		//assertFalse(register.findElementByName("msgsuccess"));
		assertTrue(register.findElementByName("msgsuccess"));
	}
	
	@When("I fill in a form with correct data and I change the \"(.*?)\" field to have incorrect input")
	public void wrong_input_register(String a) throws Throwable {
		String ID = "reg-"+a;
		register.FillForm();
		register.clear(ID);
		register.FillField("1!2", ID);	
	}
	
	
	@Then("^I  should  be  told \"([^\"]*)\"  since  the  data  in  \"([^\"]*)\"  is incorrect$")
	public void I_should_be_told_since_the_data_in_is_incorrect(String a, String b) throws Throwable {
		String ID = "reg-"+b;
		String message = register.ReturnErrorMessage(ID);
		assertEquals(message, a);
	}
	
	@Given("^I am a user who has not yet logged on$")
	public void am_a_user_who_has_not_yet_logged_on() throws Throwable {
		browser.get("http://localhost:8080/bt/index.jsp");
	}
	
	@Given("^I am a user with a free account$")
	public void am_user_with_free_account() throws Throwable {
		udao.flushUser("jakefree", false);
		login = new LoginForm(browser);
		browser.get("http://localhost:8080/bt/");
		if(browser.findElements(By.id("btn_it")).isEmpty()){
			login.GoTo();
			login.setDetails("jakefree", "somepassword");
			login.FillForm();
			login.Submit("submit_login");
		}
	}
	
	@Given("^I am a user with a premium account$")
	public void am_user_with_premium_account() throws Throwable {
		udao.flushUser("jakepremium", false);
		login = new LoginForm(browser);
		browser.get("http://localhost:8080/bt/");
		if(browser.findElements(By.id("btn_it")).isEmpty()){
			login.GoTo();
			login.setDetails("jakepremium", "somepassword");
			login.FillForm();
			login.Submit("submit_login");
		}
	}
	
	@When("^I try to place a \"(.*?)\" bet of (\\d+) euros$")
	public void i_try_to_place_a_bet_of_euros(String somerisk, int somebet) throws Throwable {
		if(somerisk.equals("low")) { somerisk = "1"; }
		else if(somerisk.equals("medium")) { somerisk = "2"; }
		else if(somerisk.equals("high")) { somerisk = "3"; }
		System.out.println("amount " + somebet + "risk " + somerisk);
		bet = new BetForm(browser);
		bet.setBetDetails("" + somebet, somerisk);
		bet.GoTo();
		bet.FillForm();
		bet.Submit("bet-placebet");
	}
	
	@When("^I try to access the betting screen$")
	public void i_try_to_access_the_betting_screen() throws Throwable {
		browser.get("http://localhost:8080/bt/home.jsp");
	}
	
	@When("^I try to place a bet of 5000 euros$")
	public void i_try_to_place_a_bet_of_5000_euros() throws Throwable {
		bet = new BetForm(browser);
		bet.setBetDetails("5000", "1");
		bet.GoTo();
		bet.FillForm();
		bet.Submit("bet-placebet");
	}
	
	@When("^I try to place a bet of 1 euros$")
	public void i_try_to_place_a_bet_of_1_euros() throws Throwable {
		bet = new BetForm(browser);
		bet.setBetDetails("1", "1");
		bet.GoTo();
		bet.FillForm();
		bet.Submit("bet-placebet");
	}
	
	@When("^I try to place a bet of 5 euros$")
	public void i_try_to_place_a_bet_of_5_euros() throws Throwable {
		bet = new BetForm(browser);
		bet.setBetDetails("5.00", "1");
		bet.GoTo();
		bet.FillForm();
		bet.Submit("bet-placebet");
	}
	
	@Then("^I should be \"(.*?)\" to bet$")
	public void i_should_be_to_bet(String prompt) throws Throwable {
		bet = new BetForm(browser);
		assertTrue(bet.findElementByName(prompt));
	}
	
	@Then("^I should be told the bet was successfully placed$")
	public void i_should_be_told_the_bet_was_successfully_placed() throws Throwable {
		bet = new BetForm(browser);
		//assertTrue(bet.findElementByName("msgsuccess"));
		assertTrue(bet.findElementById("msgsuccess").getText().contains("Your Bet Has Been placed, thank you!"));
		//bet.Submit("log-logout");
	}
	
	@Then("^I should be told that I have reached the maximum cumulative betting amount$")
	public void i_should_be_told_that_I_have_reached_the_maximum_cuml_betting_amount() throws Throwable {
		bet = new BetForm(browser);
		//assertTrue(bet.findElementByName("msgfail"));
		assertTrue(bet.findElementById("msgfail").getText().contains("ERROR: Premium Users can only bet up to 5000 euros in their lifetime"));
		//bet.Submit("log-logout");
	}
	
	@Then("^I should be told that I have reached the maximum number of bets$")
	public void i_should_be_told_that_i_have_reached_the_maxmium_number_of_bets() throws Throwable {
		bet = new BetForm(browser);
		//assertTrue(bet.findElementByName("msgfail"));
		assertTrue(bet.findElementById("msgfail").getText().contains("ERROR: Free Users can only place up to 3 bets"));
		//bet.Submit("log-logout");
	}
	
	@Then("^I should be refused access$")
	public void i_should_be_refused_access() throws Throwable {
		bet = new BetForm(browser);	
		assertTrue(bet.Url().contains("http://localhost:8080/bt/index.jsp"));
	}
	
}
