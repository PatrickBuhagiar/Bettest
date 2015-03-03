package com.selenium.site.bt.page;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class GeneralFunctions {
	protected WebDriver browser;
	
	public GeneralFunctions(){
		
	}
	public GeneralFunctions(WebDriver b){
		browser = b;
		
	}
	
	public void FillField(String keys, String id){
		//browser.findElement(By.className(id)).click();
		new WebDriverWait(browser, 10).until(ExpectedConditions.elementToBeClickable(By.id(id))).sendKeys(keys);
	}
	
	public void Submit(String id){
		//browser.findElement(By.className(id)).click();
		new WebDriverWait(browser, 10).until(ExpectedConditions.elementToBeClickable(By.id(id))).submit();
		//new WebDriverWait(browser, 10).until(ExpectedConditions.elementToBeClickable()).submit();
	}
	
	public boolean findElementByName(String id){
		
		try {
			if(browser.findElement(By.id(id)) != null)
					return true;
			else return false;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean elementContains(String id, String Class){
		try {
			this.findElementById(id).getAttribute("class").contains(Class);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public WebElement findElementById(String id){
		try {
			return new WebDriverWait(browser, 1).until(ExpectedConditions.elementToBeClickable(By.id(id)));
		} catch (Exception e) {
			return null;
		}
	}
	
	public WebElement findElementByClass(String id){
		return new WebDriverWait(browser, 10).until(ExpectedConditions.elementToBeClickable(By.className(id)));
	}
	
	public List<WebElement> findElementsByName(String id){
		List<WebElement> elements = browser.findElements(By.id(id));
		return elements;
	}
	
	public List<WebElement> findElementsByClass(String Class){
		List<WebElement> elements = browser.findElements(By.className(Class));
		return elements;
	}
	
	public String Url(){
		return browser.getCurrentUrl();
	}
	
	public void clear(String id){
		//browser.findElement(By.id(id)).clear();
		
		WebElement toClear = browser.findElement(By.id(id));
		toClear.sendKeys(Keys.CONTROL + "a");
		toClear.sendKeys(Keys.DELETE);
	}

	public void SelectField(int index, String id) {
		Select country = new Select(new WebDriverWait(browser, 10).until(ExpectedConditions.elementToBeClickable(By.id(id))));
		country.selectByIndex(index);
	}
	
}