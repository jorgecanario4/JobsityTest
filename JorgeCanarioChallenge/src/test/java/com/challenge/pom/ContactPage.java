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
		report(" Getting 'Subject Heading' dropdown element", DEBUG);
		WebElement subjectHeadingDropdownList = findElement(subjectHeadingDropdownListLocator);
		report(" Getting list with all 'Subject Heading's options element", DEBUG);
		List<WebElement> output = subjectHeadingDropdownList.findElements(By.tagName("option"));	
		report(" Handing over all 'Subject Heading's options element", DEBUG);
		return output;
	}
	
	public String selectSubjectHeading(String input) {
		report(" Getting 'Subject Heading' dropdown element", DEBUG);
		WebElement subjectHeadingDropdownList = findElement(subjectHeadingDropdownListLocator);
		report(" Selecting from 'Subject Heading' option number:", DEBUG);
		Select subjectHeadingSelection = new Select(subjectHeadingDropdownList);
		subjectHeadingSelection.selectByVisibleText(input);
		String output = getText(subjectHeadingSelection.getFirstSelectedOption());
		report(" Handing over the text of the selected option. Text is: " + output, DEBUG);
		return output;
		
	}
	
	public void selectFileForAttachmentInput(String inputFile) {
		report(" Getting the 'Attachment input' element ", DEBUG);
		WebElement attachmentInput = findElement(attachmentInputLocator);
		report(" Uploading into 'Attachment input' element: \n"+ inputFile, DEBUG);
		attachmentInput.sendKeys(inputFile);
	}

	public void submitContactForm() {
		report(" Clicking 'Submit' Button", DEBUG);
		findElement(submitBtnLocator).click();
	}

	public void scrollToForm() {
		report(" Getting Contact page element from the Contact page's container.", DEBUG);
		WebElement page = findElement(pageContainerLocator);
		report(" Getting Contact's form from the Contact page", DEBUG);
		WebElement form = page.findElement(contactFormLocator);
		report(" Scrolling until Contact's form becomes visible", DEBUG);
		scrollToElementBottom(form);
	}
	
	public void loadPage() {
		report(" Awaiting for the container with all elements of contact page to present", DEBUG);
		By containerWithAllElementsLocator = By.cssSelector("div#center_column");
		try {
			waitForPresenceOf(containerWithAllElementsLocator);
		} catch (TimeoutException e) {
			report(" Container with page's element wasn't present after the wait", ALERT);
			e.printStackTrace();
		}
	}
	
	public void writeTextMessageField(String input) {
		report(" Writing in 'Message' inputText: " + input, DEBUG);
		findElement(messageTextAreaLocator).sendKeys(input);
	}
	
	public void writeTextEmailField(String input) {
		report(" Writing in 'Email' inputText: " + input, DEBUG);
		findElement(emailInputTextLocator).sendKeys(input);
	}
	
	public void writeTextOrderField(String input) {
		report(" Writing in 'Order' inputText: " + input, DEBUG);
		findElement(orderInputTextLocator).sendKeys(input);
	}
	
	public Boolean isErrorOnSubmit() {
		report("Verifying if banner with unsuccessful message is displayed", ACTION);
		report(" Checking if the banner with unsuccessful message is present", DEBUG);
		waitForPresenceOf(alertSubmitErrorLocator);
		report(" Getting Contact page element from the Contact page's container.", DEBUG);
		WebElement page = findElement(pageContainerLocator);
		report(" Checking if banner with unsuccessful message is an element of the page and is displayed", DEBUG);
		Boolean output = page.findElement(alertSubmitErrorLocator).isDisplayed();
		report(" Handing over result of the check. Result: " + output, SUCCESS);
		return output;
	}
	
	public Boolean isSuccessOnSubmit() {
		report("Verifying if banner with successful message is displayed", ACTION);
		report(" Checking if the banner with successful message is present", DEBUG);
		waitForPresenceOf(alertSubmitSuccessLocator);
		report(" Getting Contact page element from the Contact page's container.", DEBUG);
		WebElement page = findElement(pageContainerLocator);
		report(" Checking if banner with successful message is an element of the page and is displayed", DEBUG);
		Boolean output = page.findElement(alertSubmitSuccessLocator).isDisplayed();
		report(" Handing over result of the check. Result: " + output, SUCCESS);
		return output;
	}
	
	public void visitPage() {
		report(" Navigating diretcly to Contact page: "+ PAGE_URL, DEBUG);
		get(PAGE_URL);
	}
}
