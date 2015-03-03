package com.testmodel.site;

import java.util.Random;
import java.util.Vector;

import static org.junit.Assert.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.bettest.site.bt.model.User;
import com.selenium.site.bt.page.BetForm;
import com.selenium.site.bt.page.RegisterForm;
import com.selenium.site.bt.page.LoginForm;

import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.AllRoundTester;
import nz.ac.waikato.modeljunit.FsmModel;
import nz.ac.waikato.modeljunit.GreedyTester;
import nz.ac.waikato.modeljunit.LookaheadTester;
import nz.ac.waikato.modeljunit.RandomTester;
import nz.ac.waikato.modeljunit.Tester;
import nz.ac.waikato.modeljunit.VerboseListener;

public class PerformanceTest implements FsmModel, Runnable {
	private WebDriver browser;
	private int userNumber = 0;
	private RegisterForm rf;
	private LoginForm lf;
	private BetForm bf;
	private User u;
	private Random r;
	private int betCount;
	
	private Vector<Long> responseTime;

	public PerformanceTest(Vector<Long> responseTime) {
		this.responseTime = responseTime;
	}

	public void run() {
		this.before();
		Tester t = new GreedyTester(this);
		t.addListener(new VerboseListener());
		t.generate(5);
		t.buildGraph();
		this.after();
	}
	
	public void before(){
		this.browser = new FirefoxDriver();
		rf = new RegisterForm(browser);
		lf = new LoginForm(browser);
		bf = new BetForm(browser);
		r = new Random();
//		
		betCount = 0;
	}
	
	public void after(){
		browser.quit();
		browser = null;
		rf = null;
		lf = null;
		bf = null;
		u = null;
		r = null;
	}
	
	public long recTime(){
		return System.currentTimeMillis();
	}
	
	
	public States getState() {
		States toReturn;
		if(rf.findElementByName("open_login")){
			// We are in the index page.
			// Available States: INDEX, INDEX_SUCCESS, INDEX_FAIL, REGISTER, LOGIN.
			if(u == null && rf.elementContains("RegisterModal", "in")){
				toReturn = States.REGISTER;
			} else if(u == null && !rf.elementContains("RegisterModal", "in") && rf.findElementByName("msgfail")){
				toReturn = States.INDEX_FAIL;
			} else if(u != null && !rf.elementContains("RegisterModal", "in") && rf.findElementByName("msgsuccess")){
				toReturn = States.INDEX_SUCCESS;
			} else if(rf.elementContains("LoginModal", "in")){
				toReturn = States.LOGIN;
			} else {
				toReturn = States.INDEX;
			}
		} else {
			//We are in the home page.
			if(rf.findElementByName("msgsuccess") && !rf.elementContains("BetModal", "in")){
				toReturn = States.HOME_SUCCESS;
			} else if(rf.findElementByName("msgfail") && !rf.elementContains("BetModal", "in")){
				toReturn = States.HOME_FAIL;
			} else if(rf.elementContains("BetModal", "in")){
				toReturn = States.BET;
			} else {
				toReturn = States.HOME_SUCCESS;
			}
		}
		return toReturn;
	}
	
	public double genProbability(){
		return (Math.round(Math.random() * 100.0) / 100.0) ;
	}
	
	public String genUsername(){
		Random r = new Random();
		return r.nextInt(100) + "user" + r.nextInt(100);
	}
	
	/* Guards */
	
	public boolean openBetGuard(){
		if ((getState().equals(States.HOME_SUCCESS) || getState().equals(States.HOME_FAIL)) & betCount ==0){
			return true;
		} else return false;
	}
	
	public boolean openLoginGuard(){
		return getState().equals(States.INDEX_SUCCESS);
	}
	
	public boolean doGoodLoginGuard(){
		double p = genProbability();
//		System.out.println("doGoodLogin: " + p);
		if(p > 0.25 && getState().equals(States.LOGIN)){
			return true;
		} else {
			return false;
		}
	}
	
	public boolean doBadLoginGuard(){
		double p = genProbability();
//		System.out.println("doBadLogin: " + p);
		if(p < 0.25 && getState().equals(States.LOGIN)){
			return true;
		} else {
			return false;
		}
	}
	
	public boolean doLogOutGuard(){
		double p = genProbability();
		//50% chance to logout if a bet was placed
		if(p < 0.5 && betCount > 0 && ((getState().equals(States.HOME_FAIL) || getState().equals(States.HOME_SUCCESS)))){
			return true;
		} else {
			return false;
		}
	}
	
	public boolean openRegisterGuard(){
		return getState().equals(States.INDEX);
	}
	
	public boolean doRegisterGuard(){
		return getState().equals(States.REGISTER);
	}
	
	
	public boolean dofillBetGuard(){
		double p = genProbability();
		//100% chance to place a bet of none made
		if(betCount == 0 && getState().equals(States.BET)){
			return true;
		} else if(p >= 0.5 && getState().equals(States.BET) && betCount > 0){
			//50% chance to place bet after betting
			return true;
		} else {
			return false;
		}
	}
	
	/* Actions */
	@Action
	public void dofillBet(){
		long start = recTime();
		Random randomBetAmount = new Random();
		String toBet, toRisk;
		if(u.getUser_type_id() == 0){
			toBet = "" + (randomBetAmount.nextFloat() * 5 + 1);
			toRisk = "" + 1;
		} else {
			toBet = "" + (randomBetAmount.nextFloat() * 1900 + 100);
			toRisk = "" + (randomBetAmount.nextInt(3) + 1);
		}
		bf.setBetDetails(toBet, toRisk);
		bf.FillForm();
		bf.Submit("bet-placebet");
		betCount++;
		assertTrue(browser.getCurrentUrl().contains("home"));
		responseTime.add(recTime()-start);
	}
		
	@Action
	public void openLogin(){
		long start = recTime();
		lf.GoTo();
		assertTrue(rf.elementContains("LoginModal", "in"));
		responseTime.add(recTime() - start);
	}
	
	@Action
	public void openBet(){
		long start = recTime();
		bf.GoTo();
		assertTrue(rf.elementContains("BetModal", "in"));
		responseTime.add(recTime() - start);
	}
	
	@Action
	public void doGoodLogin(){
		long start = recTime();
		lf.setDetails(u.getUser_username(), u.getUser_password());
		lf.FillForm();
		lf.Submit("submit_login");
		assertTrue(rf.findElementByName("msgsuccess"));
		responseTime.add(recTime() - start);
	}
	
	@Action
	public void doBadLogin(){
		long start = recTime();
		lf.setDetails(u.getUser_password(), u.getUser_password() + " ");
		lf.FillForm();
		lf.Submit("submit_login");
		assertTrue(rf.findElementByName("msgfail"));
		responseTime.add(recTime() - start);
	}
	
	@Action
	public void doRegister() throws InterruptedException{
		long start = recTime();
		String un = genUsername();
//		System.out.println("Username: " + un);
		String card = "5165586416321162";
		u = new User();
		u.setUser_username(un);
		u.setUser_password("Lisaknows");
		double p = genProbability();
//		System.out.println("Probability Account Type" + p);
		if(p > 0.75){
			rf.FillFormModel(un, card, false);
			u.setUser_type_id(0);
		} else {
			rf.FillFormModel(un, card, true);
			u.setUser_type_id(1);
		}
//		System.out.println("Attempting Registration: " + u.getUser_username() + "," + u.getUser_password());
		Thread.sleep(5000);
		rf.Submit("submit_register");
		assertTrue(rf.findElementByName("msgsuccess"));
		responseTime.add(recTime() - start);
	}
	
	@Action
	public void openRegister(){
		long start = recTime();
		rf.GoTo();
		assertTrue(rf.elementContains("RegisterModal", "in"));
		responseTime.add(recTime() - start);
	}

	@Action
	public void doLogOut(){
		long start = recTime();
		betCount = 0;
		rf.Submit("log-logout");
		assertTrue(browser.getCurrentUrl().contains("index"));
		responseTime.add(recTime() - start);
	}
	
	public void reset(boolean testing) {
		long start = recTime();
//		System.out.println("Reset()");
		if (browser.getCurrentUrl().equals("http://localhost:8080/bt/home.jsp")){
			rf.Submit("log-logout");
		}
		u = null;
		browser.get("http://localhost:8080/bt/");
		responseTime.add(recTime() - start);
	}
}
