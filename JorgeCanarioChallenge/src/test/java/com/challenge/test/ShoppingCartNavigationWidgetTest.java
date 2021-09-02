package com.challenge.test;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.AssertJUnit;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.challenge.pom.CategoryPage;
import com.challenge.pom.IndexPage;
import com.challenge.pom.NavigationHeader;
import com.challenge.pom.SearchPage;
import com.challenge.pom.ShoppingCartPage;

import org.testng.annotations.BeforeClass;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.security.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;

public class ShoppingCartNavigationWidgetTest {

	WebDriver driver;
	IndexPage indexPage;
	Map<String,Float> cart;
	
	@BeforeClass
	@Parameters({"URL","Browser"})
	public void beforeClass(String url, String browser) {
		indexPage = new IndexPage(driver);
		driver = indexPage.driverConnection(browser);
		driver.get(url);
		cart = new HashMap<String, Float>();

	}
	
	public void addItemToCart(HashMap<String, Float> itemsToAdd) {
		itemsToAdd.forEach(
			    (key, value) -> cart.merge( key, value, (v1, v2) -> v1+v2)
		);
	}
	
	public void removeItemsFromCart(HashMap<String, Float> itemsToRemove) {
		itemsToRemove.forEach(
			    (key, value) -> cart.merge( key, value, (v1, v2) -> Float.compare(v1, v2) == 0 ? null: v1 - v2)
		);
	}
	
	@Test(priority=5 ,enabled = true)
	public void shoppingCartWidgetCheckOutBtn_RedirectToShoppingCartPage_Successfully() {
		NavigationHeader navbar = new NavigationHeader(driver);
		Boolean success = navbar.checkOutFromCartWidget();
		assertTrue(success);
		if(!success) navbar.scrnCapture();
		success = driver.getCurrentUrl().equals(ShoppingCartPage.PAGE_URL);
		if(!success) navbar.scrnCapture();
		assertTrue(success);
		ShoppingCartPage shoppingCartPage = new ShoppingCartPage(driver);
		success = shoppingCartPage.isShoppingCartFormPresent();
		if(!success) shoppingCartPage.scrnCapture();
		assertTrue(success);
		
	}
	
	@Test(priority=4 ,enabled = true)
	public void shoppingCartWidgetItems_CanBeDeleted_Successfully() {
		NavigationHeader navbar = new NavigationHeader(driver);
		Integer initialCartItemsQuantity = navbar.getCartItemsQuantity();
		removeItemsFromCart(navbar.deleteItemFromCartWidget(0));
		Integer afterAddCartItemsQuantity = navbar.getCartItemsQuantity();
		
		Boolean success = (initialCartItemsQuantity-1)== afterAddCartItemsQuantity;
		if(!success) navbar.scrnCapture();
		assertTrue(success);
		
	}
	
	@Test(priority=3 ,enabled = true)
	public void shoppingCartWidget_Contains_AddedItems() {
		NavigationHeader navbar = new NavigationHeader(driver);
		navbar.refreshPage();
		
		Boolean success = navbar.getCartItemsMap().equals(cart);
		if(!success) navbar.scrnCapture();
		assertTrue(success);
		
	}
	
	

	@Test(priority=2 ,enabled = true)
	public void itemsSearched_CanBeAddedToCart_Successfully() {
		String searchItem = "Blouse";
		Integer addToCartSearchResult = 0;
		indexPage.visitPage();
		NavigationHeader navbar = new NavigationHeader(driver);
		Integer initialCartItemsQuantity = navbar.getCartItemsQuantity();
		indexPage.clearTypeAndSubmit(searchItem, NavigationHeader.searchBoxLocator);
		SearchPage searchPage = new SearchPage(driver);
		searchPage.loadPage();
		addItemToCart(searchPage.buyResultedItem(addToCartSearchResult));
		Integer afterAddCartItemsQuantity = navbar.getCartItemsQuantity();
		
		Boolean success = (initialCartItemsQuantity+1)== afterAddCartItemsQuantity;
		if(!success) searchPage.scrnCapture();
		assertTrue(success);

	}

	@Test(priority =1, enabled = true)
	public void itemsFromCategorySection_CanBeAddedToCart_Successfully() {
		Integer addToCartCategoryResult = 0;
		NavigationHeader navbar = new NavigationHeader(driver);
		Integer initialCartItemsQuantity = navbar.getCartItemsQuantity();
		navbar.navigateToWomenCategorySection();
		CategoryPage categoryPage = new CategoryPage(driver);
		categoryPage.loadPage();
		addItemToCart(categoryPage.buyCategoryItem(addToCartCategoryResult));
		Integer afterAddCartItemsQuantity = navbar.getCartItemsQuantity();
		
		Boolean success = (initialCartItemsQuantity+1) == afterAddCartItemsQuantity;
		if(!success) categoryPage.scrnCapture();
		assertTrue(success);	

	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
