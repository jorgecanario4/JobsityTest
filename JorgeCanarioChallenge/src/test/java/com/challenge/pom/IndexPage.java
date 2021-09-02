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

	public By getAddToCartAJAXBtnLocator() {
		return addToCartAJAXBtnLocator;
	}

	public By getProductDetailsAJAXBtnLocator() {
		return productDetailsAJAXBtnLocator;
	}

	public void visitPage() {
		String featureName = "<URL>";
		report(featureName + " Navigating diretcly to Index page: "+ PAGE_URL, DEBUG);
		get(PAGE_URL);
	}
	
	public WebElement getPopularSection() {
		return findElement(popularSectionLocator);
	}

}
