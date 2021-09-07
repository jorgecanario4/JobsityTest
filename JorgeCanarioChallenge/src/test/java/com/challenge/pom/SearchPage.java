package com.challenge.pom;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * This class extends the Base wrapper class and serves the same purpose, having this class it won't be necessary to modify the test in case something happens with 
 * current way to perform action.
 * 
 * Also, this class main purpose is to wrap all possible actions that can be performed in the search page
 * 
 * @author Jorge Canario
 *
 */
public class SearchPage extends Base {

	/**
	 * Is the direct URL of the page: {@value}
	 */
	public static final String PAGE_URL = "http://automationpractice.com/index.php?controller=search&orderby=position&orderway=desc&search_query=&submit_search=";
	
	/**
	 * Constructor class to instantiate.
	 * 
	 * @param driver refers to the driver that will be performing actions. This class perform actions over a instance of a browser, that instance of the browser can be thought as the driver.
	 * @author Jorge Canario
	 */
	public SearchPage(WebDriver driver) {
		super(driver);
	}

	private static By searchResultsLocator = By.cssSelector("ul.product_list.grid.row");
	private static By itemLocator = By.cssSelector("div.product-container");
	private static By noResultsWarningLocator = By.cssSelector("p.alert.alert-warning");
	private static By numberOfResultsLocator = By.cssSelector("span.heading-counter");
	private static By itemNameLocator = By.cssSelector("a.product-name");
	private static By itemPriceLocator = By.cssSelector("span.price.product-price");
	private static By addToCartAJAXBtnLocator = By.cssSelector("a.button.ajax_add_to_cart_button.btn.btn-default");
	private static By pageContainerLocator = By.cssSelector("div.columns-container");
	private static By cartPopUpWindowLocator = By.cssSelector("div#layer_cart[style*=\"display: block\"]");  //It will only be visible when it pops up
	private static By continueShoppingBtnLocator = By.cssSelector("span.continue.btn.btn-default.button.exclusive-medium");
	
	/**
	 * Returns the locator of the container with all the search result
	 * @return the locator of the container with all the search result
	 * @author Jorge Canario
	 */
	public By getSearchResultsLocator() {
		return searchResultsLocator;
	}

	/**
	 * Returns the locator that locates items in the search result
	 * @return the locator of the items in the search result
	 * @author Jorge Canario
	 */
	public By getItemLocator() {
		return itemLocator;
	}

	/**
	 * This method gets a list of webelements with all the resulted items 
	 * @return a list of webelements with all the items that showed up as result of the search
	 * @author Jorge Canario
	 */
	public List<WebElement> getSearchResultElements() {
		report("Trying to get all elements listed for this search", ACTION);
		report(" Awaiting for the Search page container to load", DEBUG);
		waitForPresenceOf(pageContainerLocator);
		report(" Getting Search page element from the Search page's container.", DEBUG);
		WebElement page = findElement(pageContainerLocator);
		report(" Getting the container with resulting items/products from this search", DEBUG);
		WebElement searchedItemsContainer = page.findElement(searchResultsLocator);
		report(" Getting all elements listed for this search", DEBUG);
		List<WebElement> output = searchedItemsContainer.findElements(itemLocator);
		report(" Handing over all elements listed for this search", SUCCESS);
		return output;

	}
	
	/**
	 * This methods add to cart an item from the resulted items of the search based on the ordinal number of appearance
	 * @param itemReference the ordinal number of the element we want to add to cart
	 * @return a map with the item's name as key and price as value of the added to cart item
	 */
	public HashMap<String, Float> buyResultedItem(Integer itemReference) {
		report("Trying to buy from category section item/product number: "+itemReference, ACTION);
		HashMap<String, Float> map = new HashMap<String, Float>();
		WebElement item = getSearchResultElements().get(itemReference);
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
		report(" Handing over bought item's name and price combination", SUCCESS);
		return map;

	}

	/**
	 * This method confirms if the search has results or not
	 * @return a boolean confirming if the search returned some value or not. True if some value, false if not
	 * @author Jorge Canario
	 */
	public Boolean isResults() {
		report("Verifying if search returned some item/product", ACTION);
		report(" Checking if 'No Result Notification Banner' is displayed", DEBUG);
		try {
			report(" 'No Result Notification Banner' was NOT displayed", SUCCESS);
			return !findElement(noResultsWarningLocator).isDisplayed();
		} catch (NoSuchElementException e) {
			return true;
		}
	}

	/**
	 * This method returns the amount of values returned by the page as result of the search
	 * @return an integer with the amount of values returned by the page as result of the search
	 * @author Jorge Canario
	 */
	public Integer getResultAmount() {
		report("Getting the amount of item/products returned by the search", ACTION);
		report(" Getting search result counter text", DEBUG);
		String textAmountOfResult = findElement(numberOfResultsLocator).getAttribute("outerHTML").replaceAll("(<span class=\"heading-counter\">)|(</span>)", "").trim().replaceAll("[^\\d]+(\\.*\\S)+", "");
		report(" Handing over the amount of item/products returned by the search", SUCCESS);
		return Integer.valueOf(textAmountOfResult);
	}

	/**
	 * This methods navigate to search page directly by changing the URL on the browser
	 * @author Jorge Canario
	 */
	public void visitPage() {
		report(" Navigating diretcly to Search page: "+ PAGE_URL, DEBUG);
		get(PAGE_URL);
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
