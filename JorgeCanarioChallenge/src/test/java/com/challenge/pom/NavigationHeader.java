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
		return findElements(categoriesLocator);
		
	}
	
	public void navigateToWomenCategorySection() {
		scrollToNavigationHeader();
		WebElement category = getCategoriesElements().get(0);
		click(category);
		
	}
	
	public void scrollToNavigationHeader() {
		scrollToTop();
	}
	
	public Integer getCartItemsQuantity() {
		scrollToNavigationHeader();
		waitForPresenceOf(shoppingCartWidgetLocator);
		WebElement cartWidget = findElement(shoppingCartWidgetLocator);
		WebElement cartItemsQuantity = cartWidget.findElement(cartItemsQuantityLocator);
		return (cartItemsQuantity.getText() == "")? 0 : Integer.valueOf(cartItemsQuantity.getText());
	}
	
	public List<WebElement> getCartItems() {
		scrollToNavigationHeader();
		moveToElement(shoppingCartWidgetLocator);
		return findElements(cartItemsLocator);
	}
	
	public HashMap<String, Float> getCartItemsMap() {
		HashMap<String, Float> map = new HashMap<String, Float>();
		List<WebElement> cartItems = getCartItems();
		for(WebElement item : cartItems) {
			map.put(item.findElement(cartItemNameLocator).getAttribute("title").trim() , Float.valueOf(item.findElement(cartItemPriceLocator).getAttribute("outerHTML").replaceAll("[^0-9.]", "")));
		}
		return map;
	}
	
	public HashMap<String, Float> deleteItemFromCartWidget(Integer itemReference) {
		HashMap<String, Float> map = new HashMap<String, Float>();
		List<WebElement> cartItems = getCartItems();
		
		WebElement item = cartItems.get(itemReference);
		map.put(item.findElement(cartItemNameLocator).getAttribute("title").trim() , Float.valueOf(item.findElement(cartItemPriceLocator).getAttribute("outerHTML").replaceAll("[^0-9.]", "")));
		moveToElementAndClick(cartItemRemoveBtnLocator);
		refreshPage();
		return map;
		
	}
	
	public Boolean checkOutFromCartWidget() {
		Boolean feedback = false;
		
		if(getCartItemsQuantity()>0) {
			moveToElement(shoppingCartWidgetLocator);
			WebElement shoppingCartWidgetSummary = findElement(shoppingCartWidgetSummaryLocator);
			waitForPresenceOf(checkOutShoppingWidgetBtnLocator);
			shoppingCartWidgetSummary.findElement(checkOutShoppingWidgetBtnLocator).click();
			feedback = true;
	 	}
		return feedback;
	}

	
}
