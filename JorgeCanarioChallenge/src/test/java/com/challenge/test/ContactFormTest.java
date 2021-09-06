package com.challenge.test;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.challenge.pom.ContactPage;

import org.testng.annotations.BeforeClass;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class isolates the contact form tests
 * @author Jorge Canario
 *
 */
public class ContactFormTest {
	WebDriver driver;
	ContactPage contactPage;

	/**
	 * This method runs before the execution of the class and setups everything for correct execution. In this case prepares the connection of the drivers of the browser
	 * @param url	the URL of the page that wants to be tested
	 * @param browser the browser that will be used for the test
	 * @author Jorge Canario
	 */
	@BeforeClass
	@Parameters({"URL","Browser"})
	public void beforeClass(String url, String browser) {
		contactPage = new ContactPage(driver);
		driver = contactPage.driverConnection(browser);
		driver.get(url);
	}

	/**
	 * This test validates the contact form doesn't accept to send a message with no content
	 * @author Jorge Canario
	 */
	@Test
	public void contactPage_ValidatesAllFields_CannotBeSubmittedBlank() {
		contactPage.visitPage();
		contactPage.scrollToForm();
		contactPage.submitContactForm();
		contactPage.loadPage();
		Boolean success = contactPage.isErrorOnSubmit() && contactPage.verifyAlertBannerContent(new ArrayList<String>(
			    Arrays.asList("There is 3 error", "Invalid email address.", "The message cannot be blank.", "Please select a subject from the list provided.")));
		if(!success) contactPage.scrnCapture();
		assertTrue(success);	
		
	}
	

	/**
	 * This test validates the contact form doesn't accept to send a message with blank message field
	 * @author Jorge Canario
	 */
	@Test
	public void contactPage_ValidatesMessageField_CannotBeSubmittedBlank() {
		contactPage.visitPage();
		contactPage.scrollToForm();
		String dropdownSelection = contactPage.getSubjectHeadingDropdownOptions().get(1).getText();
		contactPage.selectSubjectHeading(dropdownSelection);
		contactPage.writeTextEmailField("asdf@asf.com");
		contactPage.submitContactForm();
		contactPage.loadPage();
		Boolean success = contactPage.isErrorOnSubmit() && contactPage.verifyAlertBannerContent(new ArrayList<String>(
			    Arrays.asList("There is 1 error", "The message cannot be blank.")));
		if(!success) contactPage.scrnCapture();
		assertTrue(success);	
		
	}

	/**
	 * This test validates the contact form doesn't accept to send a message with blank email field
	 * @author Jorge Canario
	 */
	@Test
	public void contactPage_ValidatesEmailField_CannotBeSubmittedBlank() {
		contactPage.visitPage();
		contactPage.scrollToForm();
		String dropdownSelection = contactPage.getSubjectHeadingDropdownOptions().get(1).getText();
		contactPage.selectSubjectHeading(dropdownSelection);
		contactPage.writeTextMessageField("Neque porro quisquam est qui dolorem ipsum quia dolor sit amet...");
		contactPage.submitContactForm();
		contactPage.loadPage();
		Boolean success = contactPage.isErrorOnSubmit() && contactPage.verifyAlertBannerContent(new ArrayList<String>(
			    Arrays.asList("There is 1 error", "Invalid email address.")));
		if(!success) contactPage.scrnCapture();
		assertTrue(success);
	}
	
	/**
	 * This methods validates that email must meet the certain criteria before it can send the message to the contact page
	 * @author Jorge Canario
	 */
	@Test
	public void contactPage_ValidatesEmailField_FollowCorrectFormatBeforeSubmit() {
		contactPage.visitPage();
		contactPage.scrollToForm();
		String dropdownSelection = contactPage.getSubjectHeadingDropdownOptions().get(1).getText();
		contactPage.selectSubjectHeading(dropdownSelection);
		contactPage.writeTextMessageField("Neque porro quisquam est qui dolorem ipsum quia dolor sit amet...");
		contactPage.writeTextEmailField("asdf/p[asf.com");
		contactPage.submitContactForm();
		contactPage.loadPage();
		Boolean success = contactPage.isErrorOnSubmit() && contactPage.verifyAlertBannerContent(new ArrayList<String>(
			    Arrays.asList("There is 1 error", "Invalid email address.")));
		if(!success) contactPage.scrnCapture();
		assertTrue(success);
	}

	/**
	 * This test validates the contact form doesn't accept to send a message with blank subject field
	 * @author Jorge Canario
	 */
	@Test
	public void contactPage_ValidatesSubjectField_CannotBeSubmittedBlank() {
		contactPage.visitPage();
		contactPage.scrollToForm();
		contactPage.writeTextMessageField("Neque porro quisquam est qui dolorem ipsum quia dolor sit amet...");
		contactPage.writeTextEmailField("asdf@pasf.com");
		contactPage.submitContactForm();
		contactPage.loadPage();
		Boolean success = contactPage.isErrorOnSubmit() && contactPage.verifyAlertBannerContent(new ArrayList<String>(
			    Arrays.asList("There is 1 error", "Please select a subject from the list provided.")));
		if(!success) contactPage.scrnCapture();
		assertTrue(success);
	}

	/**
	 * This test validates the contact form accepts to send message with all mandatory fields filled in
	 * @author Jorge Canario
	 */
	@Test
	public void contactPage_AllowMessagesWithAttachment_ToBeSubmittedSuccessfully() {
		contactPage.visitPage();
		contactPage.scrollToForm();
		contactPage.writeTextMessageField("Neque porro quisquam est qui dolorem ipsum quia dolor sit amet...");
		contactPage.writeTextEmailField("asdf@asf.com");
		String dropdownSelection = contactPage.getSubjectHeadingDropdownOptions().get(1).getText();
		contactPage.selectSubjectHeading(dropdownSelection);
		contactPage.selectFileForAttachmentInput(System.getProperty("user.dir")+"/src/test/resources/TestingFiles/UploadTestFile.txt");
		contactPage.submitContactForm();
		contactPage.loadPage();
		Boolean success = contactPage.isSuccessOnSubmit();
		if(!success) contactPage.scrnCapture();
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
