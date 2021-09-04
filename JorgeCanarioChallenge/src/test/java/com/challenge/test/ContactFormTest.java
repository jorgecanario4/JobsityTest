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

public class ContactFormTest {
	WebDriver driver;
	ContactPage contactPage;

	@BeforeClass
	@Parameters({"URL","Browser"})
	public void beforeClass(String url, String browser) {
		contactPage = new ContactPage(driver);
		driver = contactPage.driverConnection(browser);
		driver.get(url);
	}

	@Test
	public void contactPage_ValidatesAllFields_CannotBeSubmittedBlank() {
		contactPage.visitPage();
		contactPage.scrollToForm();
		contactPage.submitContactForm();
		contactPage.loadPage();
		Boolean success = contactPage.isErrorOnSubmit();
		if(!success) contactPage.scrnCapture();
		assertTrue(success);	
		
	}
	

	@Test
	public void contactPage_ValidatesMessageField_CannotBeSubmittedBlank() {
		contactPage.visitPage();
		contactPage.scrollToForm();
		String dropdownSelection = contactPage.getSubjectHeadingDropdownOptions().get(1).getText();
		contactPage.selectSubjectHeading(dropdownSelection);
		contactPage.writeTextEmailField("asdf@asf.com");
		contactPage.submitContactForm();
		contactPage.loadPage();
		Boolean success = contactPage.isErrorOnSubmit();
		if(!success) contactPage.scrnCapture();
		assertTrue(success);	
		
	}

	@Test
	public void contactPage_ValidatesEmailField_CannotBeSubmittedBlank() {
		contactPage.visitPage();
		contactPage.scrollToForm();
		String dropdownSelection = contactPage.getSubjectHeadingDropdownOptions().get(1).getText();
		contactPage.selectSubjectHeading(dropdownSelection);
		contactPage.writeTextMessageField("Neque porro quisquam est qui dolorem ipsum quia dolor sit amet...");
		contactPage.submitContactForm();
		contactPage.loadPage();
		Boolean success = contactPage.isErrorOnSubmit();
		if(!success) contactPage.scrnCapture();
		assertTrue(success);
	}
	
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
		Boolean success = contactPage.isErrorOnSubmit();
		if(!success) contactPage.scrnCapture();
		assertTrue(success);
	}

	@Test
	public void contactPage_ValidatesSubjectField_CannotBeSubmittedBlank() {
		contactPage.visitPage();
		contactPage.scrollToForm();
		contactPage.writeTextMessageField("Neque porro quisquam est qui dolorem ipsum quia dolor sit amet...");
		contactPage.writeTextEmailField("asdf@pasf.com");
		contactPage.submitContactForm();
		contactPage.loadPage();
		Boolean success = contactPage.isErrorOnSubmit();
		if(!success) contactPage.scrnCapture();
		assertTrue(success);
	}

	@Test(priority =1)
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

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
