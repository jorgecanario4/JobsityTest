package com.challenge.pom;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SearchPage extends Base {

	public static final String PAGE_URL = "http://automationpractice.com/index.php?controller=search&orderby=position&orderway=desc&search_query=&submit_search=";

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
	
	public By getSearchResultsLocator() {
		return searchResultsLocator;
	}

	public By getItemLocator() {
		return itemLocator;
	}

	public List<WebElement> getSearchResultElements() {
		waitForPresenceOf(pageContainerLocator);
		WebElement page = findElement(pageContainerLocator);
		WebElement searchedItemsContainer = page.findElement(searchResultsLocator);
		return searchedItemsContainer.findElements(itemLocator);

	}
	
	public HashMap<String, Float> buyResultedItem(Integer itemReference) {
		HashMap<String, Float> map = new HashMap<String, Float>();
		WebElement item = getSearchResultElements().get(itemReference);
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

	public Boolean isResults() {
		try {
			return !findElement(noResultsWarningLocator).isDisplayed();
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return true;
		}
	}

	public Integer getResultAmount() {
		String textAmountOfResult = findElement(numberOfResultsLocator).getText().trim();
		Pattern pattern = Pattern.compile("^([1-9])+");
		Matcher matcher = pattern.matcher(textAmountOfResult);
		return Integer.valueOf(matcher.group(1));
	}

	public void visitPage() {
		get(PAGE_URL);
	}

	public void loadPage() {
		try {
			waitForPresenceOf(numberOfResultsLocator);
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}
	

}
