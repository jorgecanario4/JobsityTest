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
	public void pageValidates_AllFormsFields_CannotBeBlank() {
		contactPage.visitPage();
		contactPage.scrollToForm();
		contactPage.submitContactForm();
		contactPage.loadPage();
		assertTrue(contactPage.isErrorOnSubmit());	
		
	}
	

	@Test
	public void pageValidates_FormFieldMessage_CannotBeBlank() {
		contactPage.visitPage();
		contactPage.scrollToForm();
		String dropdownSelection = contactPage.getSubjectHeadingDropdownOptions().get(1).getText();
		contactPage.selectSubjectHeading(dropdownSelection);
		contactPage.writeTextEmailField("asdf@asf.com");
		contactPage.submitContactForm();
		contactPage.loadPage();
		assertTrue(contactPage.isErrorOnSubmit());	
		
	}

	@Test
	public void pageValidates_FormFieldEmail_CannotBeBlank() {
		contactPage.visitPage();
		contactPage.scrollToForm();
		String dropdownSelection = contactPage.getSubjectHeadingDropdownOptions().get(1).getText();
		contactPage.selectSubjectHeading(dropdownSelection);
		contactPage.writeTextMessageField("Neque porro quisquam est qui dolorem ipsum quia dolor sit amet...");
		contactPage.writeTextEmailField("asdf/p[asf.com");
		contactPage.submitContactForm();
		contactPage.loadPage();
		assertTrue(contactPage.isErrorOnSubmit());	
	}
	
	@Test
	public void pageValidates_FormFieldEmail_MustFollowRightFormat() {
		contactPage.visitPage();
		contactPage.scrollToForm();
		String dropdownSelection = contactPage.getSubjectHeadingDropdownOptions().get(1).getText();
		contactPage.selectSubjectHeading(dropdownSelection);
		contactPage.writeTextMessageField("Neque porro quisquam est qui dolorem ipsum quia dolor sit amet...");
		contactPage.submitContactForm();
		contactPage.loadPage();
		assertTrue(contactPage.isErrorOnSubmit());	
	}

	@Test
	public void pageValidates_FormFieldSubject_CannotBeBlank() {
		contactPage.visitPage();
		contactPage.scrollToForm();
		contactPage.writeTextMessageField("Neque porro quisquam est qui dolorem ipsum quia dolor sit amet...");
		contactPage.writeTextEmailField("asdf/p[asf.com");
		contactPage.submitContactForm();
		contactPage.loadPage();
		assertTrue(contactPage.isErrorOnSubmit());	
	}

	@Test(priority =1)
	public void messageAttachment_CanBeSentInForm_Successfully() {
		contactPage.visitPage();
		contactPage.scrollToForm();
		contactPage.writeTextMessageField("Neque porro quisquam est qui dolorem ipsum quia dolor sit amet...");
		contactPage.writeTextEmailField("asdf@asf.com");
		String dropdownSelection = contactPage.getSubjectHeadingDropdownOptions().get(1).getText();
		contactPage.selectSubjectHeading(dropdownSelection);
		contactPage.selectFileForAttachmentInput(System.getProperty("user.dir")+"/src/test/resources/TestingFiles/UploadTestFile.txt");
		contactPage.submitContactForm();
		contactPage.loadPage();
		assertTrue(contactPage.isSuccessOnSubmit());	
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
