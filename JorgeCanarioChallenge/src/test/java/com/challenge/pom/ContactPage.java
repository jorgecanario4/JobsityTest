package com.challenge.pom;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * This class extends the Base wrapper class and serves the same purpose, having
 * this class it won't be necessary to modify the test in case something happens
 * with current way to perform action.
 * 
 * Also, this class main purpose is to wrap all possible actions that can be
 * performed in the contact page
 * 
 * @author Jorge Canario
 *
 */
public class ContactPage extends Base {

	/**
	 * Is the direct URL of the page: {@value}
	 */
	public static final String PAGE_URL = "http://automationpractice.com/index.php?controller=contact";

	private static By contactFormLocator = By.cssSelector("form.contact-form-box");
	private static By subjectHeadingDropdownListLocator = By.cssSelector("select#id_contact");
	private static By emailInputTextLocator = By.cssSelector("input#email");
	private static By orderInputTextLocator = By.cssSelector("input#id_order");
	private static By attachmentInputLocator = By.cssSelector("input#fileUpload");
	private static By submitBtnLocator = By.cssSelector("button#submitMessage");
	private static By messageTextAreaLocator = By.cssSelector("textarea#message");
	private static By pageContainerLocator = By.cssSelector("div.columns-container");
	private static By alertSubmitErrorBannerLocator = By.cssSelector("div.alert.alert-danger");
	private static By alertSubmitSuccessBannerLocator = By.cssSelector("p.alert.alert-success");

	/**
	 * Constructor class to instantiate.
	 * 
	 * @param driver refers to the driver that will be performing actions. This
	 *               class perform actions over a instance of a browser, that
	 *               instance of the browser can be thought as the driver.
	 * @author Jorge Canario
	 */
	public ContactPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * This method returns all the options that the "Subject Heading"'s dropdown
	 * list has
	 * 
	 * @return a list of web elements for each of the options the dropdown list has
	 * @author Jorge Canario
	 */
	public List<WebElement> getSubjectHeadingDropdownOptions() {
		report(" Getting 'Subject Heading' dropdown element", DEBUG);
		WebElement subjectHeadingDropdownList = findElement(subjectHeadingDropdownListLocator);
		report(" Getting list with all 'Subject Heading's options element", DEBUG);
		List<WebElement> output = subjectHeadingDropdownList.findElements(By.tagName("option"));
		report(" Handing over all 'Subject Heading's options element", DEBUG);
		return output;
	}

	/**
	 * This method selects the option, by its ordinal number of appearance, to
	 * select for "Subject Heading"
	 * 
	 * @param input the ordinal number option to select
	 * @return a Strign with the option selected
	 * @author Jorge Canario
	 */
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

	/**
	 * This methods allow to upload a file into the attachment input of the contact
	 * page form
	 * 
	 * @param inputFile a String specifying the absolute location of the file to be
	 *                  uploaded
	 * @author Jorge Canario
	 */
	public void selectFileForAttachmentInput(String inputFile) {
		report(" Getting the 'Attachment input' element ", DEBUG);
		WebElement attachmentInput = findElement(attachmentInputLocator);
		report(" Uploading into 'Attachment input' element: \n" + inputFile, DEBUG);
		attachmentInput.sendKeys(inputFile);
	}

	/**
	 * This method clicks on the 'Send' button
	 * 
	 * @author Jorge Canario
	 */
	public void submitContactForm() {
		report(" Clicking 'Submit' Button", DEBUG);
		findElement(submitBtnLocator).click();
	}

	/**
	 * This method scroll to a point where the whole form is visible, allowing
	 * driver to perform action cause on it as if page loaded correctly, elements
	 * would be visible
	 * 
	 * @author Jorge Canario
	 */
	public void scrollToForm() {
		report(" Getting Contact page element from the Contact page's container.", DEBUG);
		WebElement page = findElement(pageContainerLocator);
		report(" Getting Contact's form from the Contact page", DEBUG);
		WebElement form = page.findElement(contactFormLocator);
		report(" Scrolling until Contact's form becomes visible", DEBUG);
		scrollToElementBottom(form);
	}

	/**
	 * This method makes the driver wait until the container with all elements (not
	 * includind footer and navigation bar) loads correctly
	 * 
	 * @author Jorge Canario
	 */
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

	/**
	 * This method writes the text provided in the "Message" inputText on the
	 * contact page's form
	 * 
	 * @param input the text to write in the inputText
	 * @author Jorge Canario
	 */
	public void writeTextMessageField(String input) {
		report(" Writing in 'Message' inputText: " + input, DEBUG);
		findElement(messageTextAreaLocator).sendKeys(input);
	}

	/**
	 * This method writes the text provided in the "Email" inputText on the contact
	 * page's form
	 * 
	 * @param input text to be written in the "Email" inputText
	 * @author Jorge Canario
	 */
	public void writeTextEmailField(String input) {
		report(" Writing in 'Email' inputText: " + input, DEBUG);
		findElement(emailInputTextLocator).sendKeys(input);
	}

	/**
	 * This method writes the text provided in the "Order" inputText on the contact
	 * page's form
	 * 
	 * @param input text to be written in the "Order" inputText
	 * @author Jorge Canario
	 */
	public void writeTextOrderField(String input) {
		report(" Writing in 'Order' inputText: " + input, DEBUG);
		findElement(orderInputTextLocator).sendKeys(input);
	}

	/**
	 * This method confirms if the contact form generated an error after submitting
	 * the data
	 * 
	 * @return a boolean value confirming if the contact form generate an error with
	 *         the data submitted
	 * @author Jorge Canario
	 */
	public Boolean isErrorOnSubmit() {
		report("Verifying if banner with unsuccessful message is displayed", ACTION);
		report(" Checking if the banner with unsuccessful message is present", DEBUG);
		waitForPresenceOf(alertSubmitErrorBannerLocator);
		report(" Getting Contact page element from the Contact page's container.", DEBUG);
		WebElement page = findElement(pageContainerLocator);
		report(" Checking if banner with unsuccessful message is an element of the page and is displayed", DEBUG);
		Boolean output = page.findElement(alertSubmitErrorBannerLocator).isDisplayed();
		report(" Handing over result of the check. Result: " + output, SUCCESS);
		return output;
	}

	/**
	 * 
	 * This method confirms if the contact form was successfully submitted
	 * 
	 * @return a boolean value confirming if the contact form's data was submitted
	 *         successfully
	 * @author Jorge Canario
	 */
	public Boolean isSuccessOnSubmit() {
		report("Verifying if banner with successful message is displayed", ACTION);
		report(" Checking if the banner with successful message is present", DEBUG);
		waitForPresenceOf(alertSubmitSuccessBannerLocator);
		report(" Getting Contact page element from the Contact page's container.", DEBUG);
		WebElement page = findElement(pageContainerLocator);
		report(" Checking if banner with successful message is an element of the page and is displayed", DEBUG);
		Boolean output = page.findElement(alertSubmitSuccessBannerLocator).isDisplayed();
		report(" Handing over result of the check. Result: " + output, SUCCESS);
		return output;
	}

	/**
	 * This methods navigate to contact page directly by changing the URL on the
	 * browser
	 * 
	 * @author Jorge Canario
	 */
	public void visitPage() {
		report(" Navigating diretcly to Contact page: " + PAGE_URL, DEBUG);
		get(PAGE_URL);
	}

	/**
	 *  Verify that the content in the alert banner is the expected
	 * @return A confirmation about the correct text in the banner
	 * @param inputs is an ArrayList of the expected content of the banner where the first element in the list need to be the title of the banner
	 * @author Jorge Canario
	 */
	public Boolean verifyAlertBannerContent(ArrayList<String> inputs) {
		report("Verifying if banner content is the expected", ACTION);
		Boolean output = true;
		WebElement alertSubmitErrorBanner = findElement(pageContainerLocator)
				.findElement(alertSubmitErrorBannerLocator);

		String bannerTitle = alertSubmitErrorBanner.findElement(By.tagName("p")).getText().trim();
		
		if (bannerTitle.equalsIgnoreCase(inputs.get(0))) {
			report(" Banner title matches the expected "+ bannerTitle, DEBUG);
			inputs.remove(0);
			
			try {
				List<WebElement> bannerIssues = alertSubmitErrorBanner.findElements(By.tagName("li"));

				if (bannerIssues.size() == inputs.size()) {
					for (WebElement issue : bannerIssues) {
						for (int i = 0; i < inputs.size(); i++) {
							if(issue.getText().equalsIgnoreCase(inputs.get(i))) {
								report(" Banner issue matches with the expected :" + inputs.get(i), DEBUG);
								inputs.remove(i);
								break;
							}
								
						}
					}
					output = (inputs.isEmpty());
					if(!output) report(" Some expected issues were not found in the banner", ALERT);
				}
				else {
					report(" Amount of issues in the banner differ from the amount provided. Amount of issues in banner: " + bannerIssues, DEBUG);
					output = false;
				}
			} catch (TimeoutException e) {
				output = (inputs.isEmpty());
			}
		}
		else {
			report(" Banner title differs from the expected. Value of the page is: " + bannerTitle, DEBUG);
			output = false;
		}

		report(" Handing over result of the check. Banner text is match the expected: " + output, SUCCESS);
		return output;
	}
}
