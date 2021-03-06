package com.challenge.pom;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * This class extends the Base wrapper class and serves the same purpose, having this class it won't be necessary to modify the test in case something happens with 
 * current way to perform action.
 * 
 * Also, this class main purpose is to wrap all possible actions that can be performed in the category page
 * 
 * @author Jorge Canario
 *
 */
public class CategoryPage extends Base {

	/**
	 * Constructor class to instanciate.
	 * 
	 * @param driver refers to the driver that will be performing actions. This class perform actions over a instance of a browser, that instance of the browser can be thought as the driver.
	 * @author Jorge Canario
	 */
	public CategoryPage(WebDriver driver) {
		super(driver);
	}
	
	private static By categorizedElementsLocator = By.cssSelector("ul.product_list.grid.row");
	private static By itemLocator = By.cssSelector("div.product-container");
	private static By numberOfResultsLocator = By.cssSelector("span.heading-counter");
	private static By itemNameLocator = By.cssSelector("a.product-name");
	private static By itemPriceLocator = By.cssSelector("span.price.product-price");
	private static By addToCartAJAXBtnLocator = By.cssSelector("a.button.ajax_add_to_cart_button.btn.btn-default");
	private static By pageContainerLocator = By.cssSelector("div.columns-container");
	private static By continueShoppingBtnLocator = By.cssSelector("span.continue.btn.btn-default.button.exclusive-medium");
	private static By cartPopUpWindowLocator = By.cssSelector("div#layer_cart[style*=\"display: block\"]");  //It will only be visible when it pops up

	/**
	 * Gives the location of the container with the categorized elements
	 * 
	 * @return locator with the location of the element in the HTML. Instance of By class
	 * @author Jorge Canario
	 */
	public By getCategorizedElementsLocator() {
		return categorizedElementsLocator;
	}

	/**
	 * Gives the location used to identify items in the page
	 * @return locator with the location of the element(s) in the HTML. Instance of By class
	 * @author Jorge Canario
	 */
	public By getItemLocator() {
		return itemNameLocator;
	}

	/**
	 * Gives a list with all the items in the this category present(it will scroll to find them all, in case of pagination, 
	 * this method would only take all elements shown in this page, to get following pages tester should move there) in the page at the moment.
	 * @return All WebElements
	 * @author Jorge Canario
	 */
	public List<WebElement> getCategorizedElements() {
		report("Trying to get all elements listed in this category", ACTION);
		report(" Awaiting for the Category page container to load", DEBUG);
		waitForPresenceOf(pageContainerLocator);
		report(" Getting Category page element from the Category page's container.", DEBUG);
		WebElement page = findElement(pageContainerLocator);
		report(" Getting the container with resulting items/products from this categorization out of page's elements", DEBUG);
		WebElement categorizedElementContainer = page.findElement(categorizedElementsLocator);
		report(" Scrolling page until container with resulting items/products becomes visible", DEBUG);
		scrollToElementBottom(categorizedElementContainer);
		report(" Handing over a list with all the items/products from the container", SUCCESS);
		return categorizedElementContainer.findElements(itemLocator);
	}

	/**
	 * From the elements listed as result of this categorization, this methods adds to cart the referenced (by number) in the input
	 * 
	 * @param itemReference refers to the ordinal number of the element that will be purchased
	 * @return a map with the name and the price of the element to identify it posteriori
	 * @author Jorge Canario
	 */
	public HashMap<String, Float> buyCategoryItem(Integer itemReference) {
		report("Trying to buy from category section item/product number: "+itemReference, ACTION);
		HashMap<String, Float> map = new HashMap<String, Float>();
		WebElement item = getCategorizedElements().get(itemReference);
		report(" Getting one of the items on the list and scrolling until it becomes visible", DEBUG);
		scrollToElementBottom(item);
		report(" Saving into a map the combination of item's name and price", DEBUG);
		map.put(item.findElement(itemNameLocator).getText().trim(),Float.valueOf(findElement(itemPriceLocator).getAttribute("outerHTML").replaceAll("[^0-9.]", "")));
		report(" Moving mouse over the item ", DEBUG);
		moveToElement(item);
		report(" Waiting the the Javascript code to make the 'Add To Cart' button to be clickable", DEBUG);
		waitForToBeClickabe(addToCartAJAXBtnLocator);
		report(" Moving mouse over the 'Add To Cart' button and click on it", DEBUG);
		moveToElementAndClick(addToCartAJAXBtnLocator);
		report(" Awaiting for the easy checkout pop-up window that's shown after some item is added to cart", DEBUG);
		waitForPresenceOf(cartPopUpWindowLocator);
		report(" Move to the button 'Continue Shopping' and click", DEBUG);
		moveToElementAndClick(continueShoppingBtnLocator);
		report(" Refreshing Category Page (F5)", DEBUG);
		refreshPage();
		report(" Handing over item's name and price combination", SUCCESS);
		return map;

	}
	
	/**
	 * This method makes the driver wait until the text that contains the number of results has loaded correctly
	 * @author Jorge Canario
	 */
	public void loadPage() {
		report(" Waiting for the element counter to be present", DEBUG);
		try {
			waitForPresenceOf(numberOfResultsLocator);
		} catch (TimeoutException e) {
			report(" Element counter is not visible after the wait", ALERT);
			e.printStackTrace();
		}
	}

}
