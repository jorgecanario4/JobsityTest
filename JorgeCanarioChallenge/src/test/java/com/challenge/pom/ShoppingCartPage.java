package com.challenge.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * This class extends the Base wrapper class and serves the same purpose, having this class it won't be necessary to modify the test in case something happens with 
 * current way to perform action.
 * 
 * Also, this class main purpose is to wrap all possible actions that can be performed in the shopping cart page
 * 
 * @author Jorge Canario
 *
 */
public class ShoppingCartPage extends Base{

	/**
	 * Constructor class to instantiate.
	 * 
	 * @param driver refers to the driver that will be performing actions. This class perform actions over a instance of a browser, that instance of the browser can be thought as the driver.
	 * @author Jorge Canario
	 */
	public ShoppingCartPage(WebDriver driver) {
		super(driver);
	}
	
	/**
	 * Is the direct URL of the page: {@value}
	 */
	public static final String PAGE_URL = "http://automationpractice.com/index.php?controller=order";
	
	private static By shoppingCartFormLocator = By.cssSelector("div#center_column");
	private static By pageContainerLocator = By.cssSelector("div.columns-container");
	
	/**
	 * This method returns the webelement with shopping cart table that has all the added to cart items
	 * @return the shopping cart table with all the added to cart items
	 * @author Jorge Canario
	 */
	public WebElement getShoppingCartForm() {
		report("Trying to get shopping cart table element", ACTION);
		report(" Getting shopping cart table element", DEBUG);
		WebElement output = findElement(pageContainerLocator).findElement(shoppingCartFormLocator);
		report(" Handing over shopping cart table element", SUCCESS);
		return output;
	}

	/**
	 * This methods verifies that the shopping cart table is present in the page
	 * @return a confirmation if the shopping cart table is there or not. True if it is and false otherwise
	 * @author Jorge Canario
	 */
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
