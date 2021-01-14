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
@TestCase(sequence = 1)
public class FirstNameValidation implements TestExecutable {

	@Unit(sequence = 1, dataprovider = "FIRST_NAME_COMBINATIONS")
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
		String firstName = (String) context.getParameterisedObject1();
		String expectedError = (String) context.getParameterisedObject2();
		context.getLogger().info("FirstName : " + firstName + ", Expected Error : " + expectedError);
		pSignUp.fillFirstName(firstName);
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

	@DataProvider(name = "FIRST_NAME_COMBINATIONS")
	public Object[][] dataproviderMethod_1(TestContext context) {

		// @formatter:off
		Object[][] data = new Object[][]
			{
				{"", PageSignUp.DE_FirstName}, 					// Empty Input
				
				{" ", PageSignUp.DE_NoError},					// Input starting with Space
				{"1ABC", PageSignUp.DE_NoError},				// Input starting with numerics
				{"ABC", PageSignUp.DE_NoError},					// Input starting with Capital Alpha
				{"aBC", PageSignUp.DE_NoError},					// Input starting with small Alpha
				{"Abc Test", PageSignUp.DE_NoError},			// Input with spaces
				{"~!#@$%^&*()_+-=", PageSignUp.DE_NoError},		// Input with special char
				{"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", PageSignUp.DE_NoError},		// Input with 100 char
				{"<h1>Header 1 Injection</h1>", PageSignUp.DE_NoError},		// HTML header check validation
			
			};
		return data;
		// @formatter:on
	}
}
