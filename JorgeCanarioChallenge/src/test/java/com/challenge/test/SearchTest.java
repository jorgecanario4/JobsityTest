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


public class SearchTest {

	WebDriver driver;
	IndexPage indexPage;
	
	@BeforeClass
	@Parameters({"URL","Browser"})
	public void beforeClass(String url, String browser) {
		indexPage = new IndexPage(driver);
		driver = indexPage.driverConnection(browser);
		driver.get(url);
		
	}
	

	@Test(dataProvider = "existingProducts", priority = 2)
	public void search_ExistingProduct_ShowResult(String input) {
			indexPage.clearTypeAndSubmit(input, NavigationHeader.searchBoxLocator);
			SearchPage searchPage = new SearchPage(driver);
			searchPage.loadPage();
			Boolean success = searchPage.isResults();
			if(!success) searchPage.scrnCapture();
			assertTrue(success);

	}
	
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

	
	@Test(dataProvider = "unexistingProducts", priority = 1 )
	public void search_unexistingProduct_ShowNoResult(String input) {
			indexPage.clearTypeAndSubmit(input, NavigationHeader.searchBoxLocator);
			SearchPage searchPage = new SearchPage(driver);
			searchPage.loadPage();
			Boolean success = searchPage.isResults();
			if(success) searchPage.scrnCapture();
			assertFalse(success);

	}
	
	@DataProvider(name = "unexistingProducts")
	public Object [] unexistingProducts() {
		return new Object [] {"UNXPRODUCT#1TEST"};
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}



}
