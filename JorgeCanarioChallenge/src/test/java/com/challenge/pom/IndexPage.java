package com.challenge.pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * This class extends the Base wrapper class and serves the same purpose, having this class it won't be necessary to modify the test in case something happens with 
 * current way to perform action.
 * 
 * Also, this class main purpose is to wrap all possible actions that can be performed in the index page
 * 
 * @author Jorge Canario
 *
 */
public class IndexPage extends Base {

	/**
	 * Constructor class to instantiate.
	 * 
	 * @param driver refers to the driver that will be performing actions. This class perform actions over a instance of a browser, that instance of the browser can be thought as the driver.
	 * @author Jorge Canario
	 */
	public IndexPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * Is the direct URL of the page: {@value}
	 */
	public static final String PAGE_URL = "http://automationpractice.com/";

	private static By popularSectionLocator = By.cssSelector("ul#homefeatured");
	private static By itemNameLocator = By.cssSelector("a.product-name");
	private static By addToCartAJAXBtnLocator = By.cssSelector("a.button.ajax_add_to_cart_button.btn.btn-default");
	private static By productDetailsAJAXBtnLocator = By.cssSelector("a.button.lnk_view.btn.btn-default");
	private static By pageContainerLocator = By.cssSelector("div.columns-container");
	
	/**
	 * 
	 * @return a list with all the webelements of the items in the popular section on the index page
	 * @author Jorge Canario
	 */
	public List<WebElement> getPopularItems() {
		report("Trying to get a list of of items/products in popular section", ACTION);
		report(" Awaiting for the Index page container to load", DEBUG);
		waitForPresenceOf(pageContainerLocator);
		report(" Getting the page container", DEBUG);
		WebElement page = findElement(pageContainerLocator);
		report(" Getting the container with resulting items/products from popular section", DEBUG);
		WebElement popularItemsContainer = page.findElement(popularSectionLocator);
		report(" Getting a list of items/product in popular section", DEBUG);
		List<WebElement> output = popularItemsContainer.findElements(itemNameLocator);
		report(" Handing over a list of items/products in popular section", SUCCESS);
		return output;
	}

	/**
	 * Returns the locator of the 'Add To Cart' button that appears after hovering a purchasable item
	 * @return a locator of the 'Add To Cart' Button that appears after the item is hovered
	 * @author Jorge Canario
	 */
	public By getAddToCartAJAXBtnLocator() {
		return addToCartAJAXBtnLocator;
	}

	/**
	 * Returns the locator of the 'More Details' button that appears after hovering a purchasable item
	 * @return a locator of the 'More Details' Button that appears after the item is hovered
	 * @author Jorge Canario
	 */
	public By getProductDetailsAJAXBtnLocator() {
		return productDetailsAJAXBtnLocator;
	}

	/**
	 * This methods navigate to index page directly by changing the URL on the browser
	 * @author Jorge Canario
	 */
	public void visitPage() {
		report(" Navigating diretcly to Index page: "+ PAGE_URL, DEBUG);
		get(PAGE_URL);
	}
	/**
	 * Return a a WebElement of the container of the popular section on the index page
	 * @return a WebElement of the container of the popular section on the index page
	 * @author Jorge Canario
	 */
	public WebElement getPopularSection() {
		return findElement(popularSectionLocator);
	}

}
