package com.challenge.pom;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

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

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;


/**
 * This is a base wrapper class that wraps selenium's main actions into a single class for easy access and segmentation of the code. It also protects main code from
 * deprecation of methods, classes, variable name change, etc. Having a wrapper class centralizes problems related to the framework, thus, final test method won't suffer any change at all
 * from even if at some point in future we decide to use another framework different from selenium
 * 
 * @author Jorge Canario
 *
 */
public class Base {

	private WebDriver driver;

	/** Is a variable used to classify certain logged information as actions */
	public static final int ACTION = 1;
	/** Is a variable used to classify certain logged information as debug. Difference between actions and debug: 
	  debug are steps to complete an action, while an action involves several steps */
	public static final int DEBUG = 2;
	/** Is a variable used to classify certain logged information as alert. Logs clasified as ALERT informs that certain step failed because of some situation*/
	public static final int ALERT = 3;
	/** Is a variable used to classify certain logged information as actions. Informs about the status of the latest opened action (LIFO). For logs labeled as FAILURE, latest action couldn't be completed because of some reason */
	public static final int FAILURE = 4;
	/** Is a variable used to classify certain logged information as actions. Informs about the status of the latest opened action (LIFO). For logs labeled as SUCCESS, latest action was completed successfully */
	public static final int SUCCESS = 5;

	/**
	 * Base constructor class to instanciate.
	 * 
	 * @param driver refers to the driver that will be performing actions. This class perform actions over a instance of a browser, that instance of the browser can be thought as the driver.
	 * @author Jorge Canario
	 */
	public Base(WebDriver driver) {
		this.driver = driver;
	}

	/**
	 * 
	 * This methods setups the correct connection for the driver to operate. Unless called from outside the class the driver can't control nothing.
	 * 
	 * @param browser 	This variable informs about the browser that is going to run the test. Based on the browser name, we select the proper driver for the connection
	 * @return driver	The driver that is now connected to the correct browser driver
	 * @author Jorge Canario
	 */
	public WebDriver driverConnection(String browser) {
		
		String opSys = System.getProperty("os.name").toLowerCase();
		Boolean isMac = opSys.indexOf("mac") >= 0;
		Boolean isWindows = opSys.indexOf("window") >= 0;
		Boolean isLinux = opSys.indexOf("nix") >= 0 || opSys.indexOf("nux") >= 0 || opSys.indexOf("aix") > 0;
			
		switch (browser) {
		case "Firefox":
			if(isMac) {
				System.setProperty("webdriver.gecko.driver", FileSystems.getDefault().getPath("src", "test","resources","BrowserDriver", "MacOS_BrowsersDrivers", "geckodriver").toAbsolutePath().toString());
			} else if (isWindows) {
				System.setProperty("webdriver.gecko.driver", FileSystems.getDefault().getPath("src", "test","resources","BrowserDriver", "Windows_BrowsersDrivers", "geckodriver.exe").toAbsolutePath().toString());
			} else if(isLinux) {
				System.setProperty("webdriver.gecko.driver", FileSystems.getDefault().getPath("src", "test","resources","BrowserDriver", "Linux_BrowsersDrivers", "geckodriver").toAbsolutePath().toString());
			} else {
				System.out.println("Your Operative System is not supported by the Application!!!");
			}
			driver = new FirefoxDriver();
			break;
		case "Safari":
			if(!isMac) 
				report("Your Operative System is not supported by the Application!!!", FAILURE);
			driver = new SafariDriver();
			break;
		case "IE 11":
			if(!isWindows) 
				report("Your Operative System is not supported by the Application!!!", FAILURE);
			System.setProperty("webdriver.ie.driver", FileSystems.getDefault().getPath("src", "test","resources","BrowserDriver", "Windows_BrowsersDrivers", "Windows8.1-KB2990999-x86.msu").toAbsolutePath().toString());
			driver = new InternetExplorerDriver();
			break;
		case "Chrome":
			if(isMac) {
				System.setProperty("webdriver.chrome.driver", FileSystems.getDefault().getPath("src", "test","resources","BrowserDriver", "MacOS_BrowsersDrivers", "chromedriver").toAbsolutePath().toString());	
			} else if (isWindows) {
				System.setProperty("webdriver.chrome.driver", FileSystems.getDefault().getPath("src", "test","resources","BrowserDriver", "Windows_BrowsersDrivers", "chromedriver.exe").toAbsolutePath().toString());
			} else if(isLinux) {
				System.setProperty("webdriver.chrome.driver", FileSystems.getDefault().getPath("src", "test","resources","BrowserDriver", "Linux_BrowsersDrivers", "chromedriver").toAbsolutePath().toString());
			} else {
				System.out.println("Your Operative System is not supported by the Application!!!");
			}
			driver = new ChromeDriver();
			break;

		}
		

		driver.manage().window().maximize();
		return driver;
	}

	
	/**
	 * Wrapper method to find an element based on the locator of the element
	 * @param locator is a By class element that specifies the location in the HTML of the element to find
	 * @return an instance of the WebElement that was found using the locator
	 * @author Jorge Canario
	 */
	public WebElement findElement(By locator) {
		return driver.findElement(locator);
	}

	/**
	 * Wrapper method to find the elements based on the locator of the elements. When using this
	 * methods user is hoping to find several elements that share the same locator
	 * 
	 * @param locator is a By class element that specifies the location in the HTML of the elements to find. 
	 * @return a List of WebElement whose HTML corresponds to the By locator specified as input
	 * @author Jorge Canario
	 */
	public List<WebElement> findElements(By locator) {
		return driver.findElements(locator);
	}

	/**
	 * Wrapper method used to find the text inside a WebElement
	 * 
	 * @param element the element whose text we want to find
	 * @return a String of text corresponding to the text in the WebElement
	 * @author Jorge Canario
	 */
	public String getText(WebElement element) {
		return element.getText();
	}

	/**
	 * Overridden wrapper method used to find the text inside a locator
	 * 
	 * @param locator the locator specifying the location of the element whose text is to find
	 * @return a String of text corresponding to the text in the WebElement
	 * @author Jorge Canario
	 */
	public String getText(By locator) {
		return driver.findElement(locator).getText();
	}

	/**
	 * Method tries to find an inputtext element in the provided locator, clear (in case pending text was left in it) and writes
	 * the specified text
	 * 
	 * @param inputText the text that want to be typed into the inputText field
	 * @param locator the inputText where we want to type the text
	 * @author Jorge Canario
	 */
	public void clearAndSendKeys(String inputText, By locator) {
		WebElement element = driver.findElement(locator);
		element.clear();
		element.sendKeys(inputText);
	}
	
	/**
	 * Method tries to find an inputtext element in the provided locator, clear (in case pending text was left in it) and writes
	 * the specified text and submit the entry
	 * 
	 * @param inputText the text that want to be typed and submitted into the inputText field
	 * @param locator the inputText where we want to type the text
	 * @author Jorge Canario
	 */
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

	/**
	 * Wrapper method to type text into a input text. This class finds the element and then writes in it.
	 * @param inputText refers to the locator of where we want to type the input text
	 * @param locator refers to the text that wants to be typed into the inputText
	 * @author Jorge Canario
	 */
	public void sendKeys(String inputText, By locator) {
		driver.findElement(locator).sendKeys(inputText);
	}

	/**
	 * Wrapper class method to click the element found by the provided locator
	 * 
	 * @param locator the location of element that wants to be clicked
	 * @author Jorge Canario
	 */
	public void click(By locator) {
		driver.findElement(locator).click();
	}

	/**
	 * Overridden wrapper class method to click the element provided
	 * 
	 * @param element  refers to the element that wants to be clicked
	 * @author Jorge Canario
	 */
	public void click(WebElement element) {
		element.click();
	}

	/**
	 * Wrapper method to verify certain element has loaded correctly in the HTML. 
	 * 
	 * @param locator refers to the location in the HTML of the element that we are checking its visibility
	 * @return A boolean value confirming if it was possible to find the requested element
	 * @author Jorge Canario
	 */
	public Boolean isDisplayed(By locator) {
		try {
			return driver.findElement(locator).isDisplayed();
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}
	}

	/**
	 * Wrapper class used to navigate to URLs
	 * 
	 * @param url refers to the website (URL), we want to visit
	 * @author Jorge Canario
	 */
	public void get(String url) {
		driver.get(url);
	}

	/**
	 * Wrapper class used to hover an element located in the provided locator 
	 * 
	 * @param locator the location in the HTML of the element that wants to be hovered
	 * @author Jorge Canario
	 */
	public void moveToElement(By locator) {
		Actions action = new Actions(driver);
		action.moveToElement(findElement(locator)).perform();
	}

	/**
	 * Overridden wrapper class used to hover an element 
	 * 
	 * @param element refers to the element that wants to be hovered
	 * @author Jorge Canario
	 */
	public void moveToElement(WebElement element) {
		Actions action = new Actions(driver);
		action.moveToElement(element).perform();
	}

	/**
	 * Methods hover the element before clicking it
	 * 
	 * @param locator the location in the HTML of the element that wants to be hovered and clicked
	 * @author Jorge Canario
	 */
	public void moveToElementAndClick(By locator) {
		Actions action = new Actions(driver);
		action.moveToElement(findElement(locator)).click().build().perform();
	}

	/**
	 * Methods allow to zoom out the page. For this it uses the combination of CTRL + "-" shortcut
	 * @author Jorge Canario
	 */
	public void zoomOut() {
		Actions action = new Actions(driver);
		action.sendKeys(Keys.chord(Keys.CONTROL, Keys.SUBTRACT));
	}

	/**
	 * Methods allow to zoom in the page. For this it uses the combination of CTRL + "+" shortcut
	 * @author Jorge Canario
	 */
	public void zoomIn() {
		Actions action = new Actions(driver);
		action.sendKeys(Keys.chord(Keys.CONTROL, Keys.ADD));
	}

	/**
	 * Methods allow to set the zoom to normal i.e. 100% . For this it uses the combination of CTRL + 0 shortcut
	 * @author Jorge Canario
	 */
	public void zoomNormal() {
		Actions action = new Actions(driver);
		action.sendKeys(Keys.chord(Keys.CONTROL, "0"));
	}

	/**
	 * Wait for certain element to be present in the webdriver. i.e. to be visible in the screen.
	 * 
	 * @param locator the location of the element that we're waiting
	 * @author Jorge Canario
	 */
	public void waitForPresenceOf(By locator) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}

	/**
	 * Wait for certain element to be clickable in the webdriver i.e. not only visible but to be able to respond to clicks event.
	 * 
	 * @param locator the location of the element that we're waiting
	 * @author Jorge Canario
	 */
	public void waitForToBeClickabe(By locator) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	/**
	 * Scroll current page until it reaches the top of the page. 
	 * @author Jorge Canario
	 */
	public void scrollToTop() {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.scrollTo(0,0)");
	}

	/**
	 * Scroll current page until it reaches the bottom of the page
	 * @author Jorge Canario
	 */
	public void scrollToBottom() {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	/**
	 * Scroll current page a little bit down
	 * @author Jorge Canario
	 */
	public void scrollToDown() {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.scrollBy(0, 200)");
	}

	/**
	 * Scroll until the top the element is visible in the page
	 * @param element the element whose top we are looking for
	 * @author Jorge Canario
	 */
	public void scrollToElementTop(WebElement element) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	/**
	 * Scroll until the bottom of the element is visible in the page
	 * @param element the element whose bottom we are looking for
	 * @author Jorge Canario
	 */
	public void scrollToElementBottom(WebElement element) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].scrollIntoView(false);", element);
	}
	
	/**
	 * Refreshes the page. F5. Normally used when certain action need the page to refresh to show results
	 * @author Jorge Canario
	 */
	public void refreshPage() {
		driver.navigate().refresh();
	}

	/**
	 * Take a screenshot of the current page, at the position it is. Screenshot will show what is visible at the moment when it was called.
	 * Screenshots are taken and saved into the "emailablereport" file
	 * @author Jorge Canario
	 */
	public void scrnCapture() {
		report(" Taking screenshot", DEBUG);
		File scrnshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String fileName = driver.getTitle().replaceAll(" ", "_") + "--" + getDate();
		File scrnshotName = FileSystems.getDefault().getPath(fileName + ".png").toAbsolutePath().toFile();
		try {
			FileUtils.copyFile(scrnshot, scrnshotName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Reporter.log("<br><img src='" + scrnshotName.toString() + "' height='420' width='720'><br>");
		

	}

	/**
	 * A class to calculate the current date and return it
	 * @return a String with the date in the following format: yy-MM-dd-hhmmssS
	 * @author Jorge Canario
	 */
	public String getDate() {
		DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd-hhmmssS");
		Date date = new Date();
		return dateFormat.format(date);
	}

	/**
	 * Method used to do log with correct formatting in the report generated by the automation
	 * 
	 * @param input	refers to the text that will be logged
	 * @param notificationType refers to the classification of the log: DEBUG, ALERT, ACTION, FAILURE, SUCCESS
	 * @author Jorge Canario
	 */
	public void report(String input, int notificationType) {
		String notificationHeader = "";

		switch (notificationType) {
		case DEBUG: notificationHeader = "DEBUG  : "; break;
		case ALERT: notificationHeader = "ALERT  : "; break;
		case ACTION: notificationHeader = "ACTION  : "; break;
		case FAILURE: notificationHeader = "FAILURE : "; break;
		case SUCCESS: notificationHeader = "SUCCESS: "; break;
		}
		Reporter.log("[" + getDate() + "] "+ notificationHeader + input);
	}
	
	/**
	 * This method takes a full DOM with viewport more than 1200px of the page and save it in the project directory 
	 * as 'pixelPerfectviewport1200pxCmpImg.jpg'
	 * 
	 * @author Jorge Canario
	 */
	public void takeFullDOMScrnshot() {
		Screenshot screenshot=new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1200)).takeScreenshot(driver);
        try {
            ImageIO.write(screenshot.getImage(),"JPG", FileSystems.getDefault().getPath("pixelPerfectviewport1200pxCmpImg.jpg").toAbsolutePath().toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
