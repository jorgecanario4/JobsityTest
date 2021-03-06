package com.challenge.test;

import org.testng.annotations.Test;

import com.applitools.eyes.images.Target;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.images.Eyes;

import com.challenge.pom.ContactPage;
import com.challenge.pom.IndexPage;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;

import javax.imageio.ImageIO;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;

/**
 * This class isolates all the test related to the visuals of the webpage
 * 
 * @author Jorge Canario
 *
 */
public class WebPageVisualTest {
	WebDriver driver;
	IndexPage indexPage;

	/**
	 * This method runs before the execution of the class and setups everything for
	 * correct execution. In this case prepares the connection of the drivers of the
	 * browser
	 * 
	 * @param url     the URL of the page that wants to be tested
	 * @param browser the browser that will be used for the test
	 * @author Jorge Canario
	 */
	@BeforeClass
	@Parameters({ "URL", "Browser" })
	public void beforeClass(String url, String browser) {
		indexPage = new IndexPage(driver);
		driver = indexPage.driverConnection(browser);
		driver.get(url);
	}
	
	/**
	 * This test ensures that page matches exactly with the reference .png provided as design. It ensures page meet pixel perfect.
	 * @author Jorge Canario
	 */
	@Test
	public void indexPage_IsAPixelPerfect_DesignedPage() {
		  indexPage.takeFullDOMScrnshot();
          
		  indexPage.report("To see difference, if any, visit: https://eyes.applitools.com/app/test-results/00000251771339192780?accountId=tx5voHA9AkKs_CIHBl8Iew~~&display=details&top=00000251771339192780%283%29", IndexPage.DEBUG);
		  
          Eyes eyes = new Eyes();
          eyes.setApiKey("AA9zfpqPaiRw2JMvgCLHi3bRbxo34mL98Rh9fioF1gdc110");
          
          try {
              eyes.open("Jobsity Challenge - Item #2", "Jobsity Challenge - Pixel Perfect Validation", new RectangleSize(1200, 600));
//              BufferedImage img = ImageIO.read(FileSystems.getDefault().getPath("src", "test","resources","References","indexPagePixelPerfectBaseline.jpg").toAbsolutePath().toFile());
              BufferedImage img = ImageIO.read(FileSystems.getDefault().getPath("pixelPerfectviewport1200pxCmpImg.jpg").toAbsolutePath().toFile());
              eyes.check("Image buffer", Target.image(img));
              eyes.close();
          } catch(IOException ex){
              System.out.println(ex);
          } finally {
              eyes.abortIfNotClosed();
          }  
                  
	}

	/**
	 * This method run after the execution of the class and prepares everything to
	 * get back to normal. In this case close connection of the drivers of the
	 * browser
	 * 
	 * @author Jorge Canario
	 */
	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
