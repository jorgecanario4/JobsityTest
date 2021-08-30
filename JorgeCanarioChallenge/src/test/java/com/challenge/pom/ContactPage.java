package com.challenge.pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class ContactPage extends Base {

	public static final String PAGE_URL="http://automationpractice.com/index.php?controller=contact";
	
	private static By contactFormLocator = By.cssSelector("form.contact-form-box");
	private static By subjectHeadingDropdownListLocator = By.cssSelector("select#id_contact");
	private static By emailInputTextLocator = By.cssSelector("input#email");
	private static By orderInputTextLocator = By.cssSelector("input#id_order");
	private static By attachmentInputLocator = By.cssSelector("input#fileUpload");
	private static By submitBtnLocator = By.cssSelector("button#submitMessage");
	private static By messageTextAreaLocator = By.cssSelector("textarea#message");
	private static By pageContainerLocator = By.cssSelector("div.columns-container");
	private static By alertSubmitErrorLocator = By.cssSelector("div.alert.alert-danger");
	private static By alertSubmitSuccessLocator = By.cssSelector("p.alert.alert-success");

	public ContactPage(WebDriver driver) {
		super(driver);
	}
	
	
	public List<WebElement> getSubjectHeadingDropdownOptions(){
		WebElement subjectHeadingDropdownList = findElement(subjectHeadingDropdownListLocator);
		return subjectHeadingDropdownList.findElements(By.tagName("option"));	
	}
	
	public String selectSubjectHeading(String input) {
		WebElement subjectHeadingDropdownList = findElement(subjectHeadingDropdownListLocator);
		Select subjectHeadingSelection = new Select(subjectHeadingDropdownList);
		subjectHeadingSelection.selectByVisibleText(input);
		return getText(subjectHeadingSelection.getFirstSelectedOption());
		
	}
	
	public void selectFileForAttachmentInput(String inputFile) {
		WebElement attachmentInput = findElement(attachmentInputLocator);
		attachmentInput.sendKeys(inputFile);
	}

	public void submitContactForm() {
		findElement(submitBtnLocator).click();
	}

	public void scrollToForm() {
		WebElement page = findElement(pageContainerLocator);
		WebElement form = page.findElement(contactFormLocator);
		scrollToElementBottom(form);
	}
	
	public void loadPage() {
		By containerWithAllElementsLocator = By.cssSelector("div#center_column");
		try {
			waitForPresenceOf(containerWithAllElementsLocator);
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}
	
	public void writeTextMessageField(String input) {
		findElement(messageTextAreaLocator).sendKeys(input);
	}
	
	public void writeTextEmailField(String input) {
		findElement(emailInputTextLocator).sendKeys(input);
	}
	
	public void writeTextOrderField(String input) {
		findElement(orderInputTextLocator).sendKeys(input);
	}
	
	public Boolean isErrorOnSubmit() {
		waitForPresenceOf(alertSubmitErrorLocator);
		WebElement page = findElement(pageContainerLocator);
		return page.findElement(alertSubmitErrorLocator).isDisplayed();
	}
	
	public Boolean isSuccessOnSubmit() {
		waitForPresenceOf(alertSubmitSuccessLocator);
		WebElement page = findElement(pageContainerLocator);
		return page.findElement(alertSubmitSuccessLocator).isDisplayed();
	}
	
	public void visitPage() {
		get(PAGE_URL);
	}
}
