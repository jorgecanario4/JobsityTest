package com.challenge.test;

import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.challenge.pom.IndexPage;
import com.challenge.pom.NavigationHeader;
import com.challenge.pom.SearchPage;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.security.Timestamp;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * This class isolates the search tests
 * @author Jorge Canario
 *
 */
public class SearchTest {

	WebDriver driver;
	IndexPage indexPage;
	
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
		
	}
	

	/**
	 * This test validates all products listed in the index's popular section returns a result when searched on the searchbox
	 * 
	 * @param input the data that the test will use provided by 'existingProducts' method
	 * @author Jorge Canario
	 */
	@Test(dataProvider = "existingProducts", priority = 2)
	public void aSearchFor_ExistingProduct_ShowResult(String input) {
			indexPage.clearTypeAndSubmit(input, NavigationHeader.searchBoxLocator);
			SearchPage searchPage = new SearchPage(driver);
			searchPage.loadPage();
			Boolean success = searchPage.isResults();
			if(!success) searchPage.scrnCapture();
			assertTrue(success);

	}
	
	/**
	 * This is the method that prepares the existing data for the test. It provides the data the 'aSearchFor_ExistingProduct_ShowResult' test will use
	 * 
	 * It will gather all the items listed in the index's popular section and provide it as data to the 'aSearchFor_ExistingProduct_ShowResult' test
	 * 
	 * @return the object with all the data the 'aSearchFor_ExistingProduct_ShowResult' test will use
	 * 
	 */
	@DataProvider(name = "existingProducts")
	public Object [] existingProducts() {
		indexPage.visitPage();
		List<WebElement> existingItems = indexPage.getPopularItems();

		Object [] data = new Object [existingItems.size()];
		
		for(int i=0; i<data.length; i++) {
			data[i] = existingItems.get(i).getText().trim();
		}

		return data;
	}

	/**
	 * This test validates unexistent products doesn't return results in the searhbox
	 * 
	 * @param input the data that the test will use provided by 'unexistingProducts' method
	 * @author Jorge Canario
	 */
	@Test(dataProvider = "unexistingProducts", priority = 1 )
	public void aSearchFor_UnexistingProduct_ShowNoResult(String input) {
			indexPage.clearTypeAndSubmit(input, NavigationHeader.searchBoxLocator);
			SearchPage searchPage = new SearchPage(driver);
			searchPage.loadPage();
			Boolean success = searchPage.isResults();
			if(success) searchPage.scrnCapture();
			assertFalse(success);

	}
	
	/**
	 * This is the method that prepares the unexisting data for the test. It provides the data the 'aSearchFor_UnexistingProduct_ShowNoResult' test will use
	 * 
	 * @return the object with all the data the 'aSearchFor_UnexistingProduct_ShowNoResult' test will use
	 * 
	 */
	@DataProvider(name = "unexistingProducts")
	public Object [] unexistingProducts() {
		return new Object [] {"UNXPRODUCT#1TEST"};
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
