package com.selenium.site.bt.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BetForm extends GeneralFunctions implements FormFunctions{
	
	String amount;
	String risklevel;
	
	public BetForm(WebDriver b) {
		super(b);
	}
//
	public void FillForm() {
		if(amount != null && risklevel != null){
			super.FillField(amount , "bet-amount");
			super.SelectField(Integer.parseInt(risklevel), "bet-risk");
		} else {
			super.FillField("5.00", "bet-amount");
			super.SelectField(1, "bet-risk");
		}
	}

	public void GoTo() {
		browser.get("http://localhost:8080/bt/");
		browser.findElement(By.id("bet_it")).click();
	}
	
	public void setBetDetails(String a, String r){
		this.amount = a;
		this.risklevel = r;
	}
}
