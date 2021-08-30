package com.challenge.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ShoppingCartPage extends Base{

	public ShoppingCartPage(WebDriver driver) {
		super(driver);
	}
	
	public static final String PAGE_URL = "http://automationpractice.com/index.php?controller=order";
	
	private static By shoppingCartFormLocator = By.cssSelector("div#center_column");
	private static By pageContainerLocator = By.cssSelector("div.columns-container");
	
	public WebElement getShoppingCartForm() {
		return findElement(pageContainerLocator).findElement(shoppingCartFormLocator);
	}

	public Boolean isShoppingCartFormPresent() {
		try {
			return getShoppingCartForm().isDisplayed();
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}
	}
}
