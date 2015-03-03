package com.selenium.site.bt.page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegisterForm extends GeneralFunctions implements FormFunctions{

	public RegisterForm(WebDriver b) {
		super(b);
		// TODO Auto-generated constructor stub
	}

	public void FillForm() {
		super.FillField("LisaSimmm" , "reg-username");
		super.FillField("Lisaknows" , "reg-password");
		super.FillField("Lisaknows" , "reg-confirm-password");
		super.FillField("lisa@gmail.com" , "reg-email");
		super.FillField("Lisa" , "reg-name");
		super.FillField("Simmons" , "reg-surname");
		super.FillField("27/07/1993" , "reg-dob");
		super.SelectField(2, "reg-country");
		super.FillField("11, Private Drive" , "reg-address");
		super.FillField("London" , "reg-city");
		super.FillField("LND001" , "reg-postcode");
		super.SelectField(2, "reg-card");
		super.FillField("LISA SIMMONS" , "reg-card-holder-id");
		super.FillField("5184182275041609" , "reg-card-number");
		super.FillField("10/19" , "reg-exp-date");
		super.FillField("123" , "reg-ccv");
	}
	
	public void FillFormModel(String username, String card, boolean type){
		super.FillField(username , "reg-username");
		super.FillField("Lisaknows" , "reg-password");
		super.FillField("Lisaknows" , "reg-confirm-password");
		super.FillField("lisa@gmail.com" , "reg-email");
		super.FillField("Lisa" , "reg-name");
		super.FillField("Simmons" , "reg-surname");
		super.FillField("27/07/1993" , "reg-dob");
		super.SelectField(2, "reg-country");
		super.FillField("11, Private Drive" , "reg-address");
		super.FillField("London" , "reg-city");
		super.FillField("LND001" , "reg-postcode");
		super.SelectField(2, "reg-card");
		super.FillField("LISA SIMMONS" , "reg-card-holder-id");
		super.FillField(card , "reg-card-number");
		super.FillField("10/19" , "reg-exp-date");
		super.FillField("123" , "reg-ccv");
		if (type){
			super.findElementById("reg-Acc").click();
		}
	}

	public void GoTo() {
		// TODO Auto-generated method stub
		browser.get("http://localhost:8080/bt/");
		browser.findElement(By.cssSelector("a[href='register']")).click();
	}
	
	public boolean RegisterSuccess(){
			
			return true;
	}
	
	public String ReturnErrorMessage(String ID){
        JavascriptExecutor js = (JavascriptExecutor) browser;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("var x = $(\'#"+ID+"\');");
        stringBuilder.append("x.blur().mouseover()");
        js.executeScript(stringBuilder.toString());
        String y = this.findElementByClass("tipsy-inner").getText();
		//String x =this.findElementById(ID).getText();
		//System.out.println("EEERRR " + ID + "    " + x);
		System.out.println("EEERRR " + ID + "    " + y);
		return y;
	}

}