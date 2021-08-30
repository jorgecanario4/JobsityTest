package com.challenge.pom;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CategoryPage extends Base {

	public CategoryPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
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

	
	public By getCategorizedElementsLocator() {
		return categorizedElementsLocator;
	}

	public By getItemLocator() {
		return itemNameLocator;
	}

	public List<WebElement> getCategorizedElements() {
		waitForPresenceOf(pageContainerLocator);
		WebElement page = findElement(pageContainerLocator);
		WebElement categorizedElementContainer = page.findElement(categorizedElementsLocator);
		scrollToElementBottom(categorizedElementContainer);
		return categorizedElementContainer.findElements(itemLocator);
	}

	public HashMap<String, Float> buyCategoryItem(Integer itemReference) {
		HashMap<String, Float> map = new HashMap<String, Float>();
		WebElement item = getCategorizedElements().get(itemReference);
		scrollToElementBottom(item);
		map.put(item.findElement(itemNameLocator).getText().trim(),Float.valueOf(findElement(itemPriceLocator).getAttribute("outerHTML").replaceAll("[^0-9.]", "")));
		moveToElement(item);
		waitForToBeClickabe(addToCartAJAXBtnLocator);
		moveToElementAndClick(addToCartAJAXBtnLocator);
		waitForPresenceOf(cartPopUpWindowLocator);
		moveToElementAndClick(continueShoppingBtnLocator);
		refreshPage();
		return map;

	}
	
	public void loadPage() {
		try {
			waitForPresenceOf(numberOfResultsLocator);
		} catch (TimeoutException e) {
			e.printStackTrace();
			//TODO log
		}
	}
	/*
	 * IN THE FUTURE: METHOD => move forward index page list: NOT ENOUGH ELEMENTS TO
	 * DO PAGINATION METHOD => move backward index page list: NOT ENOUGH ELEMENTS TO
	 * DO PAGINATION METHOD => move to index page list to section: NOT ENOUGH
	 * ELEMENTS TO DO PAGINATION
	 */
}
