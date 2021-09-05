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

/**
 * This class isolates the shopping cart widget tests
 * @author Jorge Canario
 *
 */
public class ShoppingCartNavigationWidgetTest {

	WebDriver driver;
	IndexPage indexPage;
	Map<String,Float> validatingCart;
	
	/**
	 * This method runs before the execution of the class and setups everything for correct execution. In this case prepares the connection of the drivers of the browser
	 * @param url	the URL of the page that wants to be tested
	 * @param browser the browser that will be used for the test
	 * @author Jorge Canario
	 */
	@BeforeClass
	@Parameters({"URL","Browser"})
	public void beforeClass(String url, String browser) {
		indexPage = new IndexPage(driver);
		driver = indexPage.driverConnection(browser);
		driver.get(url);
		validatingCart = new HashMap<String, Float>();

	}
	/**
	 * This method adds the added to cart item into the validation cart kept to validate the actual page cart
	 * @param itemsToAdd a map with the item's name as key and the price as value of the added item
	 * @author Jorge Canario
	 */
	public void addItemToValidatingCart(HashMap<String, Float> itemsToAdd) {
		itemsToAdd.forEach(
			    (key, value) -> validatingCart.merge( key, value, (v1, v2) -> v1+v2)
		);
	}
	
	/**
	 * This method removes the added to cart item into the validation cart kept to validate the actual page cart
	 * @param itemsToRemove a map with the item's name as key and the price as value of the removed item
	 * @author Jorge Canario
	 */
	public void removeItemsFromValidatingCart(HashMap<String, Float> itemsToRemove) {
		itemsToRemove.forEach(
			    (key, value) -> validatingCart.merge( key, value, (v1, v2) -> Float.compare(v1, v2) == 0 ? null: v1 - v2)
		);
	}
	
	/**
	 * This test needs to be run after some item is in the shopping cart widget. This test ensures that 'Check Out' button successfully
	 * redirects to shopping cart page
	 * @author Jorge Canario
	 */
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
	
	/**
	 * This test needs to be run after some item is in the shopping cart widget. This test ensures that items in shopping cart widget 
	 * can be deleted and disappear from the list.
	 * @author Jorge Canario
	 */
	@Test(priority=4 ,enabled = true)
	public void shoppingCartWidgetItems_CanBeDeleted_Successfully() {
		NavigationHeader navbar = new NavigationHeader(driver);
		Integer initialCartItemsQuantity = navbar.getCartItemsQuantity();
		removeItemsFromValidatingCart(navbar.deleteItemFromCartWidget(0));
		Integer afterAddCartItemsQuantity = navbar.getCartItemsQuantity();
		
		Boolean success = (initialCartItemsQuantity-1)== afterAddCartItemsQuantity;
		if(!success) navbar.scrnCapture();
		assertTrue(success);
		
	}
	
	/**
	 * This test needs to be run after some item is in the shopping cart widget. This method ensures that shopping cart widget reflects 
	 * the correct amount of items in added to shopping cart
	 * @author Jorge Canario
	 */
	@Test(priority=3 ,enabled = true)
	public void shoppingCartWidget_Contains_AddedItems() {
		NavigationHeader navbar = new NavigationHeader(driver);
		navbar.refreshPage();
		
		Boolean success = navbar.getCartItemsMap().equals(validatingCart);
		if(!success) navbar.scrnCapture();
		assertTrue(success);
		
	}
	
	/**
	 * This test ensures that items resulting from a search can be added to cart
	 * @author Jorge Canario
	 */
	@Test(priority=2 ,enabled = true)
	public void SearchedItems_CanBeAddedToCart_Successfully() {
		String searchItem = "Blouse";
		Integer searchResult = 0;
		indexPage.visitPage();
		NavigationHeader navbar = new NavigationHeader(driver);
		Integer initialCartItemsQuantity = navbar.getCartItemsQuantity();
		indexPage.clearTypeAndSubmit(searchItem, NavigationHeader.searchBoxLocator);
		SearchPage searchPage = new SearchPage(driver);
		searchPage.loadPage();
		addItemToValidatingCart(searchPage.buyResultedItem(searchResult));
		Integer afterAddCartItemsQuantity = navbar.getCartItemsQuantity();
		
		Boolean success = (initialCartItemsQuantity+1)== afterAddCartItemsQuantity;
		if(!success) searchPage.scrnCapture();
		assertTrue(success);

	}

	/**
	 * This test ensures that items resulting from categorization can be added to cart
	 * @author Jorge Canario
	 */
	@Test(priority =1, enabled = true)
	public void itemsFromCategorySection_CanBeAddedToCart_Successfully() {
		Integer categoryResult = 0;
		NavigationHeader navbar = new NavigationHeader(driver);
		Integer initialCartItemsQuantity = navbar.getCartItemsQuantity();
		navbar.navigateToWomenCategorySection();
		CategoryPage categoryPage = new CategoryPage(driver);
		categoryPage.loadPage();
		addItemToValidatingCart(categoryPage.buyCategoryItem(categoryResult));
		Integer afterAddCartItemsQuantity = navbar.getCartItemsQuantity();
		
		Boolean success = (initialCartItemsQuantity+1) == afterAddCartItemsQuantity;
		if(!success) categoryPage.scrnCapture();
		assertTrue(success);	

	}

	/**
	 * This method run after the execution of the class and prepares everything to get back to normal. In this case close connection of the drivers of the browser
	 * @author Jorge Canario
	 */
	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
