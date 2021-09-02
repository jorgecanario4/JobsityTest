package com.challenge.pom;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

public class Base {

	private WebDriver driver;

	public Base(WebDriver driver) {
		this.driver = driver;
	}

	public WebDriver driverConnection(String browser) {
		switch (browser) {
		case "Firefox":
			System.setProperty("webdriver.gecko.driver", "src/test/resources/BrowserDriver/geckodriver");
			driver = new FirefoxDriver();
			break;
		case "Safari":
			driver = new SafariDriver();
			break;
		case "IE 11":
			System.setProperty("webdriver.ie.driver", "src/test/resources/BrowserDriver/Windows8.1-KB2990999-x86.msu");
			driver = new InternetExplorerDriver();
			break;
		case "Chrome":
			System.setProperty("webdriver.chrome.driver", "./src/test/resources/BrowserDriver/chromedriver");
			driver = new ChromeDriver();
			break;

		}

		driver.manage().window().maximize();
		return driver;
	}

	/*
	 * Wrapper methods section:
	 */
	public WebElement findElement(By locator) {
		return driver.findElement(locator);
	}

	public List<WebElement> findElements(By locator) {
		return driver.findElements(locator);
	}

	public String getText(WebElement element) {
		return element.getText();
	}

	public String getText(By locator) {
		return driver.findElement(locator).getText();
	}

	public void clearAndSendKeys(String inputText, By locator) {
		WebElement element = driver.findElement(locator);
		element.clear();
		element.sendKeys(inputText);
	}

	public void clearTypeAndSubmit(String inputText, By locator) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.elementToBeClickable(locator));
			WebElement element = driver.findElement(locator);
			element.clear();
			element.sendKeys(inputText);
			element.submit();
		} catch (org.openqa.selenium.NoSuchElementException e) {
			e.printStackTrace();
		} catch (TimeoutException e2) {
			e2.printStackTrace();
		}
	}

	public void sendKeys(String inputText, By locator) {
		driver.findElement(locator).sendKeys(inputText);
	}

	public void click(By locator) {
		driver.findElement(locator).click();
	}

	public void click(WebElement element) {
		element.click();
	}

	public Boolean isDisplayed(By locator) {
		try {
			return driver.findElement(locator).isDisplayed();
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}
	}

	public void get(String url) {
		driver.get(url);
	}

	public void moveToElement(By locator) {
		Actions action = new Actions(driver);
		action.moveToElement(findElement(locator)).perform();
	}

	public void moveToElement(WebElement element) {
		Actions action = new Actions(driver);
		action.moveToElement(element).perform();
	}

	public void moveToElementAndClick(By locator) {
		Actions action = new Actions(driver);
		action.moveToElement(findElement(locator)).click().build().perform();
	}

	public void zoomOut() {
		Actions action = new Actions(driver);
		action.sendKeys(Keys.chord(Keys.CONTROL, Keys.SUBTRACT));
	}

	public void zoomIn() {
		Actions action = new Actions(driver);
		action.sendKeys(Keys.chord(Keys.CONTROL, Keys.ADD));
	}

	public void zoomNormal() {
		Actions action = new Actions(driver);
		action.sendKeys(Keys.chord(Keys.CONTROL, "0"));
	}

	public void waitForPresenceOf(By locator) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}
	
	public void waitForToBeClickabe(By locator) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	public void scrollToTop() {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.scrollTo(0,0)");
	}

	public void scrollToBottom() {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	public void scrollToDown() {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.scrollBy(0, 200)");
	}

	public void scrollToElementTop(WebElement element) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public void scrollToElementBottom(WebElement element) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].scrollIntoView(false);", element);
	}

	public void refreshPage() {
		driver.navigate().refresh();
	}
	
	public void scrnCapture() {
		File scrnshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String fileName = driver.getTitle().replaceAll(" ", "_") +"--"+getDate() ;
		File scrnshotName = new File("./"+ fileName +".png");
		try {
			FileUtils.copyFile(scrnshot,scrnshotName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Reporter.log("<br><img src='"+System.getProperty("user.dir")+"/"+fileName+".png' height='720' width='1000'><br>");
		
	}
	
	public String getDate() {
		DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd-hhmmssS");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public void report(String input) {
		Reporter.log(input);
	}
}
