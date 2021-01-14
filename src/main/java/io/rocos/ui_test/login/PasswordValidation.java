package io.rocos.ui_test.login;

import com.artos.annotation.DataProvider;
import com.artos.annotation.TestCase;
import com.artos.annotation.TestPlan;
import com.artos.annotation.Unit;
import com.artos.framework.Enums.TestStatus;
import com.artos.framework.infra.TestContext;
import com.artos.interfaces.TestExecutable;
import com.artos.utils.Guard;
import com.artos.utils.Transform;

import io.rocos.infra.Utils;
import io.rocos.pages.PageSignUp;

@TestPlan(preparedBy = "arpit", preparationDate = "14/01/2021")
@TestCase(sequence = 5)
public class PasswordValidation implements TestExecutable {

	@Unit(sequence = 1, dataprovider = "PASSWORD_COMBINATIONS")
	public void testUnit_1(TestContext context) throws Exception {
		// --------------------------------------------------------------------------------------------
		Transform transform = new Transform();
		// Navigate to SignUp page
		PageSignUp pSignUp = new PageSignUp(context);
		pSignUp.navigateToSignUp();
		context.setTestStatus(TestStatus.PASS,
				Utils.takeSnapShot(context, "./reporting/SignUpPage_" + transform.randLong() + ".png"),
				"Snapshot post first time page load");

		// No errors should be present upon first page load
		Guard.guardEquals(false, pSignUp.isErrorPresent(PageSignUp.DE_FirstName));

		// Enter First-Name using DataProvider and validate no errors
		String password = (String) context.getParameterisedObject1();
		String expectedError = (String) context.getParameterisedObject2();
		context.getLogger().info("Password : " + password + ", Expected Error : " + expectedError);
		pSignUp.fillPassword(password);
		context.setTestStatus(TestStatus.PASS,
				Utils.takeSnapShot(context, "./reporting/SignUpPagePostEntry_" + transform.randLong() + ".png"),
				"Snapshot post text entry");

		// No errors should be present after input
		if (expectedError.equals(PageSignUp.DE_NoError)) {
			Guard.guardEquals(false, pSignUp.isErrorPresent(expectedError));
		} else if (expectedError.equals(PageSignUp.VE_Password)) {
			// Due to string wrapping, we will compare using substring for each piece
			Guard.guardEquals(true, pSignUp.isErrorPresent(expectedError.substring(0, 48)));
		} else {
			Guard.guardEquals(true, pSignUp.isErrorPresent(expectedError));
		}

		Thread.sleep(1000);

		// --------------------------------------------------------------------------------------------
	}

	@DataProvider(name = "PASSWORD_COMBINATIONS")
	public Object[][] dataproviderMethod_1(TestContext context) {

		// @formatter:off
		Object[][] data = new Object[][]
			{
				{"", PageSignUp.DE_Password}, 					// Empty Input
				
				{" ", PageSignUp.VE_Password},					// Invalid password length
				{"1", PageSignUp.VE_Password},					// Invalid password length
				{"1A", PageSignUp.VE_Password},					// Invalid password length
				{"1A2", PageSignUp.VE_Password},				// Invalid password length
				{"1A2B", PageSignUp.VE_Password},				// Invalid password length
				{"1A2B@", PageSignUp.VE_Password},				// Invalid password length
				{"1A2B@a", PageSignUp.VE_Password},				// Invalid password length
				{"1A2B@ab", PageSignUp.VE_Password},			// Invalid password length
				
				{"@ABCDefgh", PageSignUp.VE_Password},			// Valid password length, missing digit
				{"@12345678", PageSignUp.VE_Password},			// Valid password length, missing char
				{"a1234ABCD", PageSignUp.VE_Password},			// Valid password length, missing special char
				{"a1234ABCD", PageSignUp.VE_Password},			// Valid password length, missing special char
				{"@1234abcd", PageSignUp.VE_Password},			// Valid password length, missing Capital char
				{"@1234ABCD", PageSignUp.VE_Password},			// Valid password length, missing small char
				
				{"@Abcd123", PageSignUp.DE_NoError},			// Valid Password - 8 char
				{"@Abc 123", PageSignUp.DE_NoError},			// Valid Password - 8 char with space
				{"@Abc*123", PageSignUp.DE_NoError},			// Valid Password - 8 char with space
				{"@ABCD1234efgh", PageSignUp.DE_NoError},		// Valid Password - longer than 8 char
				{"#ABCD1234efgh", PageSignUp.DE_NoError},		// Valid Password - longer than 8 char
				{"OPQR1234efgh~", PageSignUp.DE_NoError},			// Valid Password - longer than 8 char
				{"~!#$%^&@*()_+1234ABCDabcd", PageSignUp.DE_NoError},		// Valid Password
				{"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA@bcd123", PageSignUp.DE_NoError},		// Valid Long Password
				{"<h1>Header 1@ Injection</h1>", PageSignUp.DE_NoError},		// HTML header check validation
			
			};
		return data;
		// @formatter:on
	}
}
