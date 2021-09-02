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
		report("Trying to get shopping cart table element", ACTION);
		report(" Getting shopping cart table element", DEBUG);
		WebElement output = findElement(pageContainerLocator).findElement(shoppingCartFormLocator);
		report(" Handing over shopping cart table element", SUCCESS);
		return output;
	}

	public Boolean isShoppingCartFormPresent() {
		report("Verifying that shopping cart table is visible", ACTION);
		try {
			report(" Shopping cart table element was not displayed", SUCCESS);
			Boolean output = getShoppingCartForm().isDisplayed();
			return output;
		} catch (org.openqa.selenium.NoSuchElementException e) {
			report(" Shopping cart table element was not displayed", ALERT);
			return false;
		}
	}
}
