package com.challenge.pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class IndexPage extends Base {

	public IndexPage(WebDriver driver) {
		super(driver);
	}

	public static final String PAGE_URL = "http://automationpractice.com/";

	private static By popularSectionLocator = By.cssSelector("ul#homefeatured");
	private static By itemNameLocator = By.cssSelector("a.product-name");
	private static By addToCartAJAXBtnLocator = By.cssSelector("a.button.ajax_add_to_cart_button.btn.btn-default");
	private static By productDetailsAJAXBtnLocator = By.cssSelector("a.button.lnk_view.btn.btn-default");
	private static By pageContainerLocator = By.cssSelector("div.columns-container");
	
	public List<WebElement> getPopularItems() {
		waitForPresenceOf(pageContainerLocator);
		WebElement page = findElement(pageContainerLocator);
		WebElement popularItemsContainer = page.findElement(popularSectionLocator);
		return popularItemsContainer.findElements(itemNameLocator);
		
	}

	public By getAddToCartAJAXBtnLocator() {
		return addToCartAJAXBtnLocator;
	}

	public By getProductDetailsAJAXBtnLocator() {
		return productDetailsAJAXBtnLocator;
	}

	public void visitPage() {
		get(PAGE_URL);
	}
	
	public WebElement getPopularSection() {
		return findElement(popularSectionLocator);
	}

}
