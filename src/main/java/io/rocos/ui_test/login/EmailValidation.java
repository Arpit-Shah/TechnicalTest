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
@TestCase(sequence = 3)
public class EmailValidation implements TestExecutable {

	@Unit(sequence = 1, dataprovider = "EMAIL_COMBINATIONS")
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

		// Enter Last-Name using DataProvider and validate no errors
		String email = (String) context.getParameterisedObject1();
		String expectedError = (String) context.getParameterisedObject2();
		context.getLogger().info("Email : " + email + ", Expected Error : " + expectedError);
		pSignUp.fillEmail(email);
		context.setTestStatus(TestStatus.PASS,
				Utils.takeSnapShot(context, "./reporting/SignUpPagePostEntry_" + transform.randLong() + ".png"),
				"Snapshot post text entry");

		// No errors should be present after input
		if (expectedError.equals(PageSignUp.DE_NoError)) {
			Guard.guardEquals(false, pSignUp.isErrorPresent(expectedError));
		} else {
			Guard.guardEquals(true, pSignUp.isErrorPresent(expectedError));
		}

		Thread.sleep(1000);
		// --------------------------------------------------------------------------------------------
	}

	@DataProvider(name = "EMAIL_COMBINATIONS")
	public Object[][] dataproviderMethod_1(TestContext context) {

		// @formatter:off
		Object[][] data = new Object[][]
			{
				{"", PageSignUp.DE_Email}, 									// Empty Input
				
			
				{"test@test.com", PageSignUp.DE_NoError},					// Good Path - Simple name
				{"test@test.name.com", PageSignUp.DE_NoError},				// Good Path - dotted domain string
				{"test.test@test.name.com", PageSignUp.DE_NoError},			// Good Path - dotted name string
				{"test@test.io", PageSignUp.DE_NoError},					// Good Path - different sub-domain
				{"123@test.io", PageSignUp.DE_NoError},						// Good Path - numericName
				{"123~!#$%^&*_-=+@test.io", PageSignUp.DE_NoError},			// Good Path - special char
				{"ABCDEFGHIJKLMNOPQRSTUVWXYZ@123test.io", PageSignUp.DE_NoError},			// Good Path - long name
				{"test@test", PageSignUp.DE_NoError},						// Missing domain end
				
				{" ", PageSignUp.VE_Email},									// Input starting with Space
				{"@test.com", PageSignUp.VE_Email},							// No name
				{"aBC", PageSignUp.VE_Email},								// No @ sign
				{"<h1>Header 1 Injection</h1>", PageSignUp.VE_Email},		// HTML header check validation
				{"( )@test.io", PageSignUp.VE_Email},						// Bad Path - special char
			
			};
		return data;
		// @formatter:on
	}
}
