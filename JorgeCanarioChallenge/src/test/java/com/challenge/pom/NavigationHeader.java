package com.challenge.pom;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class NavigationHeader extends Base {

	public NavigationHeader(WebDriver driver) {
		super(driver);
	}


	public static By searchBoxLocator = By.id("search_query_top");
	public static By shoppingCartWidgetLocator = By.cssSelector("a[title=\"View my shopping cart\"]");
	public static By categoriesLocator = By.cssSelector("a.sf-with-ul");
	public static By homeLogoLocator = By.cssSelector("a[title=\"My Store\"]");
	public static By contactUsBtnLocator = By.cssSelector("div#contact-link > a[title=\"Contact Us\"]");
	public static By signInBtnLocator = By.cssSelector("a.login");
	public static By signOutBtnLocator = By.cssSelector("a.logout");
	public static By accountDetailsBtnLocator = By.cssSelector("a.account");
	public static By shopPhoneLocator = By.cssSelector("span.shop-phone");
	public static By cartItemsQuantityLocator = By.cssSelector("span.ajax_cart_quantity");
	public static By cartItemsLocator= By.cssSelector("div.cart-info");
	public static By cartItemNameLocator= By.cssSelector("div.product-name > a");
	public static By cartItemPriceLocator= By.cssSelector("span.price");
	public static By cartItemRemoveBtnLocator= By.cssSelector("a.ajax_cart_block_remove_link");
	public static By checkOutShoppingWidgetBtnLocator = By.cssSelector("#button_order_cart");
	public static By shoppingCartWidgetSummaryLocator = By.cssSelector("div.cart_block_list");
			
	public List<WebElement> getCategoriesElements(){
		report("Trying to get all category pages link elements", ACTION);
		List<WebElement> output = findElements(categoriesLocator);
		report(" Handing over all category pages link elements", SUCCESS);
		return output;
		
	}
	
	public void navigateToWomenCategorySection() {
		report("Trying to get navigate to 'Women' category", ACTION);
		scrollToNavigationHeader();
		WebElement category = getCategoriesElements().get(0);
		report(" Getting the first link element of categories in navigation bar: Women", DEBUG);
		report(" Click on link: Women", SUCCESS);
		click(category);
		
	}
	
	public void scrollToNavigationHeader() {
		report("Scrolling to navigation bar", DEBUG);
		scrollToTop();
	}
	
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
		refreshPage();
		report(" Handing over a map with the deleted item/product's name and price", SUCCESS);
		return map;
		
	}
	
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
			waitForPresenceOf(checkOutShoppingWidgetBtnLocator);
			report(" Getting 'Check Out' button element and click", DEBUG);
			shoppingCartWidgetSummary.findElement(checkOutShoppingWidgetBtnLocator).click();
			feedback = true;
	 	}
		report(" Handing over result of the action:"+ feedback, SUCCESS);
		return feedback;
	}

	
}
