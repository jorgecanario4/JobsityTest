package com.challenge.pom;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * This class extends the Base wrapper class and serves the same purpose, having this class it won't be necessary to modify the test in case something happens with 
 * current way to perform action.
 * 
 * Also, this class main purpose is to wrap all possible actions that can be performed in the Navigation header section of page
 * 
 * @author Jorge Canario
 *
 */
public class NavigationHeader extends Base {

	/**
	 * Constructor class to instantiate.
	 * 
	 * @param driver refers to the driver that will be performing actions. This class perform actions over a instance of a browser, that instance of the browser can be thought as the driver.
	 * @author Jorge Canario
	 */
	public NavigationHeader(WebDriver driver) {
		super(driver);
	}


	/** Is the locator that identifies the search box element. Since the navigation bar is present in most pages it makes sense to make the locator accessible everywhere, thus is declared public */
	public static By searchBoxLocator = By.id("search_query_top");
	/** Is the locator that identifies the shopping cart widget element. Since the navigation bar is present in most pages it makes sense to make the locator accessible everywhere, thus is declared public */
	public static By shoppingCartWidgetLocator = By.cssSelector("a[title=\"View my shopping cart\"]");
	/** Is the locator that identifies the set categories' button element. Since the navigation bar is present in most pages it makes sense to make the locator accessible everywhere, thus is declared public */
	public static By categoriesLocator = By.cssSelector("a.sf-with-ul");
	/** Is the locator that identifies the home logo element that when clicked takes you back to index page. Since the navigation bar is present in most pages it makes sense to make the locator accessible everywhere, thus is declared public */
	public static By homeLogoLocator = By.cssSelector("a[title=\"My Store\"]");
	/** Is the locator that identifies the 'contact us' button element. Since the navigation bar is present in most pages it makes sense to make the locator accessible everywhere, thus is declared public */
	public static By contactUsBtnLocator = By.cssSelector("div#contact-link > a[title=\"Contact Us\"]");
	/** Is the locator that identifies the 'sign in' button element. Since the navigation bar is present in most pages it makes sense to make the locator accessible everywhere, thus is declared public */
	public static By signInBtnLocator = By.cssSelector("a.login");
	/** Is the locator that identifies the 'sign out' button element, only present in HTML when user's logged in. Since the navigation bar is present in most pages it makes sense to make the locator accessible everywhere, thus is declared public */
	public static By signOutBtnLocator = By.cssSelector("a.logout");
	/** Is the locator that identifies the 'account' button element, only present in HTML when user's logged in. Since the navigation bar is present in most pages it makes sense to make the locator accessible everywhere, thus is declared public */
	public static By accountDetailsBtnLocator = By.cssSelector("a.account");
	/** Is the locator that identifies the text with the phone information. Since the navigation bar is present in most pages it makes sense to make the locator accessible everywhere, thus is declared public */
	public static By shopPhoneLocator = By.cssSelector("span.shop-phone");
	/** Is the locator that identifies the text with the count of the amount of elements in the shopping cart widget. Since the navigation bar is present in most pages it makes sense to make the locator accessible everywhere, thus is declared public */
	public static By cartItemsQuantityLocator = By.cssSelector("span.ajax_cart_quantity");
	/** Is the locator that identifies the shopping cart widget element. Since the navigation bar is present in most pages it makes sense to make the locator accessible everywhere, thus is declared public */
	public static By cartItemsLocator= By.cssSelector("div.cart-info");
	/** Is the locator that identifies the name of the elements in the shopping cart widget, only visible after hovering over the shopping cart widget. Since the navigation bar is present in most pages it makes sense to make the locator accessible everywhere, thus is declared public */
	public static By cartItemNameLocator= By.cssSelector("div.product-name > a");
	/** Is the locator that identifies the price of the elements in the shopping cart widget, only visible after hovering over the shopping cart widget. Since the navigation bar is present in most pages it makes sense to make the locator accessible everywhere, thus is declared public */
	public static By cartItemPriceLocator= By.cssSelector("span.price");
	/** Is the locator that identifies the 'x' buttons of the elements in the shopping cart widget, only visible after hovering over the shopping cart widget. Since the navigation bar is present in most pages it makes sense to make the locator accessible everywhere, thus is declared public */
	public static By cartItemRemoveBtnLocator= By.cssSelector("a.ajax_cart_block_remove_link");
	/** Is the locator that identifies the 'check out' button in the shopping cart widget, only visible after hovering over the shopping cart widget. Since the navigation bar is present in most pages it makes sense to make the locator accessible everywhere, thus is declared public */
	public static By checkOutShoppingWidgetBtnLocator = By.cssSelector("#button_order_cart");
	/** Is the locator that identifies the content of the shopping cart widget, only visible after hovering over the shopping cart widget. Since the navigation bar is present in most pages it makes sense to make the locator accessible everywhere, thus is declared public */
	public static By shoppingCartWidgetSummaryLocator = By.cssSelector("div.cart_block_list");
			
	/**
	 *  This method returns a list of the category buttons in the navigation header as WebElements
	 * @return a list with all the buttons in the category set shown in the navigation bar
	 * @author Jorge Canario
	 */
	public List<WebElement> getCategoriesElements(){
		report("Trying to get all category pages link elements", ACTION);
		List<WebElement> output = findElements(categoriesLocator);
		report(" Handing over all category pages link elements", SUCCESS);
		return output;
		
	}
	
	/**
	 * This methods clicks the 'Women' category button
	 * @author Jorge Canario
	 */
	public void navigateToWomenCategorySection() {
		report("Trying to get navigate to 'Women' category", ACTION);
		scrollToNavigationHeader();
		WebElement category = getCategoriesElements().get(0);
		report(" Getting the first link element of categories in navigation bar: Women", DEBUG);
		report(" Click on link: Women", SUCCESS);
		click(category);
		
	}
	
	/**
	 * This method scrolls to the top of the page where the navigation bar should be visible
	 * @author Jorge Canario
	 */
	public void scrollToNavigationHeader() {
		report("Scrolling to navigation bar", DEBUG);
		scrollToTop();
	}
	
	/**
	 * This method uses the cart item quantity info in the shopping cart widget to return the amount of items in the shopping cart widget
	 * @return an integer with the amount of items in the shopping cart widget
	 * @author Jorge Canario
	 */
	public Integer getCartItemsQuantity() {
		report("Trying to get the amount of elements in shopping cart widget", ACTION);
		scrollToNavigationHeader();
		report(" Awating for 'Shopping Cart Widget' to be present", DEBUG);
		waitForPresenceOf(shoppingCartWidgetLocator);
		report(" Getting shopping cart widget element", DEBUG);
		WebElement cartWidget = findElement(shoppingCartWidgetLocator);
		report(" Getting the amount of elements in shopping cart widget", DEBUG);
		WebElement cartItemsQuantity = cartWidget.findElement(cartItemsQuantityLocator);
		Integer output = (cartItemsQuantity.getText() == "")? 0 : Integer.valueOf(cartItemsQuantity.getText());
		report(" Handing over amount of elements in shopping cart widget. Current quantity:"+ output, SUCCESS);
		return output;
	}
	
	/**
	 * This method returns a list with all the items in the shopping cart widget as WebElements
	 * @return a list with all the items in the shopping cart widget as WebElements
	 * @author Jorge Canario
	 */
	public List<WebElement> getCartItems() {
		report("Trying to get items/products elements inside the shopping cart widget ", ACTION);
		scrollToNavigationHeader();
		report(" Moving mouse over the shopping cart widget", DEBUG);
		moveToElement(shoppingCartWidgetLocator);
		report(" Getting list of items/products inside the shopping cart widget", DEBUG);
		List<WebElement> output = findElements(cartItemsLocator);
		report(" Handing over items/products elements inside the shopping cart widget", SUCCESS);
		return output;
	}
	
	/**
	 * This method returns a map with the element's name as key and the price as value based on the items found in shopping cart widget
	 * @return a map with the element's name as key and the price as value of the items in the shopping cart widget
	 * @author Jorge Canario
	 */
	public HashMap<String, Float> getCartItemsMap() {
		report("Trying to get a map with items/products' name and prices inside the shopping cart widget ", ACTION);
		HashMap<String, Float> map = new HashMap<String, Float>();
		List<WebElement> cartItems = getCartItems();
		for(WebElement item : cartItems) {
			map.put(item.findElement(cartItemNameLocator).getAttribute("title").trim() , Float.valueOf(item.findElement(cartItemPriceLocator).getAttribute("outerHTML").replaceAll("[^0-9.]", "")));
		}
		report(" Handing over a map with items/products' name and prices inside the shopping cart widget", SUCCESS);
		return map;
	}
	
	/**
	 * This method deletes an item from the shopping cart widget based on the ordinal number of when it was found
	 * @param itemReference the ordinal number of the item to delete
	 * @return a map with the deleted element's name as key and price as value
	 * @author Jorge Canario
	 */
	public HashMap<String, Float> deleteItemFromCartWidget(Integer itemReference) {
		report("Trying to delete item form shopping widget cart", ACTION);
		
		HashMap<String, Float> map = new HashMap<String, Float>();
		List<WebElement> cartItems = getCartItems();
		
		report(" From the items/products inside the shopping cart widget, getting element nummber:", DEBUG);
		WebElement item = cartItems.get(itemReference);
		map.put(item.findElement(cartItemNameLocator).getAttribute("title").trim() , Float.valueOf(item.findElement(cartItemPriceLocator).getAttribute("outerHTML").replaceAll("[^0-9.]", "")));
		report(" Moving mouse over 'close' button and click", DEBUG);
		moveToElementAndClick(cartItemRemoveBtnLocator);
		report(" Refreshing Category Page (F5)", DEBUG);
		waitForToBeClickabe(shoppingCartWidgetLocator);
		refreshPage();
		report(" Handing over a map with the deleted item/product's name and price", SUCCESS);
		return map;
		
	}
	
	/**
	 * This method clicks in the check out button of the shopping cart widget
	 * @return a confirmation of the action. If action was completed successfully returns true, if not false
	 * @author Jorge Canario
	 */
	public Boolean checkOutFromCartWidget() {
		report("Trying to check out current items/products from the cart", ACTION);
		Boolean feedback = false;
		
		if(getCartItemsQuantity()>0) {
			report(" Amount of items/products more than zero", DEBUG);
			report(" Moving mouse over the shopping cart widget", DEBUG);
			moveToElement(shoppingCartWidgetLocator);
			report(" Getting shopping cart widget section with buttons of action", DEBUG);
			WebElement shoppingCartWidgetSummary = findElement(shoppingCartWidgetSummaryLocator);
			report(" Awaiting for the 'Check Out' button to be present", DEBUG);
			waitForToBeClickabe(checkOutShoppingWidgetBtnLocator);
			report(" Getting 'Check Out' button element and click", DEBUG);
			shoppingCartWidgetSummary.findElement(checkOutShoppingWidgetBtnLocator).click();
			feedback = true;
	 	}
		report(" Handing over result of the action:"+ feedback, SUCCESS);
		return feedback;
	}

	
}
