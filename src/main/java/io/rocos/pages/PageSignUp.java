package io.rocos.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.artos.framework.infra.TestContext;

public class PageSignUp {

	TestContext context;
	WebDriver driver;

	// Default Errors and Validation Errors
	public static final String DE_NoError = "";

	public static final String DE_FirstName = "Please enter First Name.";
	public static final String DE_LastName = "Please enter Last Name.";

	public static final String DE_Email = "Please enter Email.";
	public static final String VE_Email = "Please enter a valid email address.";

	public static final String DE_AccountName = "Please enter Account Name.";
	public static final String VE_AccountName = "Are you sure you’ve entered your name correctly?";

	public static final String DE_Password = "Please enter Password.";
	public static final String VE_Password = "Please choose a stronger password. Use 8 or more characters with a mix of letters, numbers & symbols";

	public static final String DE_ConfirmPassword = "Confirm your password.";

	public static final String DE_InvitationCode = "Invitation Code not valid";

	public PageSignUp(TestContext context) {
		this.context = context;
		this.driver = (WebDriver) context.getGlobalObject("DRIVER");
	}

	public void navigateToSignUp() {
		driver.navigate().to("https://portal.rocos.io/signup");
	}

	// ******************************************************************
	// Find Elements
	// ******************************************************************
	public WebElement findInputBoxFirstName() {
		return driver.findElement(By.xpath("//*[@id=\"mat-input-0\"][@data-placeholder=\"First Name\"]"));
	}

	public WebElement findInputBoxLastName() {
		return driver.findElement(By.xpath("//*[@id=\"mat-input-1\"][@data-placeholder=\"Last Name\"]"));
	}

	public WebElement findInputBoxEmail() {
		return driver.findElement(By.xpath("//*[@id=\"mat-input-2\"][@data-placeholder=\"Email\"]"));
	}

	public WebElement findInputBoxPassword() {
		return driver.findElement(By.xpath("//*[@id=\"mat-input-3\"][@data-placeholder=\"Password\"]"));
	}

	public WebElement findInputBoxConfirmPassword() {
		return driver.findElement(By.xpath("//*[@id=\"mat-input-4\"][@data-placeholder=\"Confirm Password\"]"));
	}

	public WebElement findInputBoxAccountName() {
		return driver.findElement(By.xpath("//*[@id=\"mat-input-5\"][@data-placeholder=\"Account Name\"]"));
	}

	public WebElement findInputBoxInvitationCode() {
		return driver.findElement(By.xpath("//*[@id=\"mat-input-6\"][@data-placeholder=\"Invitation Code\"]"));
	}

	public WebElement findButtonSignUp() {
		return driver.findElement(By.xpath("//button[@type='submit' and contains(., 'Sign Up')]"));
	}

	// ******************************************************************
	// Fill Input Box Methods
	// ******************************************************************
	public void fillSingUpForm(String firstName, String lastName, String email, String password, String confirmPassword,
			String accountName, String invitationCode) {

		fillFirstName(firstName);
		fillLastName(lastName);
		fillEmail(email);
		fillPassword(password);
		fillConfirmPassword(confirmPassword);
		fillAccountName(accountName);
		fillInvitationCode(invitationCode);

	}

	public void fillFirstName(String firstName) {
		WebElement weFirstName = findInputBoxFirstName();
		weFirstName.click();
		weFirstName.sendKeys(firstName);

		// Move cursor to next potential input-box for trigger
		findInputBoxLastName().click();
	}

	public void fillLastName(String lastName) {
		WebElement weLastName = findInputBoxLastName();
		weLastName.click();
		weLastName.sendKeys(lastName);

		// Move cursor to next potential input-box for trigger
		findInputBoxEmail().click();
	}

	public void fillEmail(String email) {
		WebElement weEmail = findInputBoxEmail();
		weEmail.click();
		weEmail.sendKeys(email);

		// Move cursor to next potential input-box for trigger
		findInputBoxAccountName().click();

	}

	public void fillAccountName(String accountName) {
		WebElement weAccountName = findInputBoxAccountName();
		weAccountName.click();
		weAccountName.sendKeys(accountName);

		// Move cursor to next potential input-box for trigger
		findInputBoxPassword().click();

	}

	public void fillPassword(String password) {
		WebElement wePassword = findInputBoxPassword();
		wePassword.click();
		wePassword.sendKeys(password);

		// Move cursor to next potential input-box for trigger
		findInputBoxConfirmPassword().click();
	}

	public void fillConfirmPassword(String confirmPassword) {
		WebElement weConfirmPassword = findInputBoxConfirmPassword();
		weConfirmPassword.click();
		weConfirmPassword.sendKeys(confirmPassword);

		// Move cursor to next potential input-box for trigger
		findInputBoxInvitationCode().click();
	}

	public void fillInvitationCode(String invitationCode) {
		WebElement weInvitationCode = findInputBoxInvitationCode();
		weInvitationCode.click();
		weInvitationCode.clear();
		weInvitationCode.sendKeys(invitationCode);
//		JavascriptExecutor jse = (JavascriptExecutor)driver;
//		jse.executeScript("arguments[0].value='"+invitationCode+"';", weInvitationCode);

		// Move cursor to next potential input-box for trigger
		findInputBoxFirstName().click();
	}

	// ******************************************************************
	// Press Button
	// ******************************************************************
	public void pressSignUpButton() {
		WebElement weButtonSignUp = findButtonSignUp();
		weButtonSignUp.click();
	}
	// ******************************************************************
	// Default Error Validation
	// ******************************************************************

	public boolean isErrorPresent(String expectedErrorString) {
		if (DE_NoError.contentEquals(expectedErrorString)) {
			return false;
		}
		return driver.getPageSource().contains(expectedErrorString);
	}
}
