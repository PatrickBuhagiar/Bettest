package com.selenium.site.bt.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginForm extends GeneralFunctions implements FormFunctions{
	
	String username;
	String password;
	
	public LoginForm(){
		super();
	}
	
	public LoginForm(WebDriver b) {		
		super(b);
	}
	
	public void setDetails(String u, String p){
		this.username = u;
		this.password = p;
	}
	
	public void GoTo() {
		browser.get("http://localhost:8080/bt/index.jsp");
		browser.findElement(By.id("open_login")).click();
	}

	public void FillForm() {
		if(username != null && password != null){
			super.FillField(username , "log-the-username");
			super.FillField(password, "log-password");
		} else {
			super.FillField("jakefree", "log-the-username");
			super.FillField("somepassword", "log-password");
		}
	}
}
