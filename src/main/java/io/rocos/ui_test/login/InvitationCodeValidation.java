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
@TestCase(sequence = 6)
public class InvitationCodeValidation implements TestExecutable {

	@Unit(sequence = 1, dataprovider = "INVITATION_CODE_COMBINATIONS")
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
		String invitationCode = (String) context.getParameterisedObject1();
		String expectedError = (String) context.getParameterisedObject2();
		context.getLogger().info("InvitationCode : " + invitationCode + ", Expected Error : " + expectedError);
		
		pSignUp.fillFirstName("TestName");
		pSignUp.fillLastName("TestLastName");
		pSignUp.fillEmail("Test@rocos.io");
		pSignUp.fillAccountName("TestAccount");
		pSignUp.fillPassword("ABC@123@bcd");
		pSignUp.fillConfirmPassword("ABC@123@bcd");
		
		pSignUp.fillInvitationCode(invitationCode);
		
		pSignUp.pressSignUpButton();
		// Allow some time to process
		Thread.sleep(2000);
		
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

	@DataProvider(name = "INVITATION_CODE_COMBINATIONS")
	public Object[][] dataproviderMethod_1(TestContext context) {

		// @formatter:off
		Object[][] data = new Object[][]
			{
				{"", PageSignUp.DE_NoError}, 					
				{" ", "Request failed with status code 400"},					
				{"1", PageSignUp.DE_InvitationCode},					
				{"1A", PageSignUp.DE_InvitationCode},					
				{"1A2", PageSignUp.DE_InvitationCode},				
				{"1A2B", PageSignUp.DE_InvitationCode},				
				{"1A2B@", PageSignUp.DE_InvitationCode},				
				{"1A2B@a", PageSignUp.DE_InvitationCode},				
				{"1A2B@ab", PageSignUp.DE_InvitationCode},			
				
				{"@ABCDefgh", PageSignUp.DE_InvitationCode},			
				{"@12345678", PageSignUp.DE_InvitationCode},			
				{"a1234ABCD", PageSignUp.DE_InvitationCode},			
				{"a1234ABCD", PageSignUp.DE_InvitationCode},			
				{"@1234abcd", PageSignUp.DE_InvitationCode},			
				{"@1234ABCD", PageSignUp.DE_InvitationCode},	
				
				{"@Abcd123", PageSignUp.DE_InvitationCode},			
				{"@Abc 123", PageSignUp.DE_InvitationCode},			
				{"@Abc*123", PageSignUp.DE_InvitationCode},			
				{"@ABCD1234efgh", PageSignUp.DE_InvitationCode},		
				{"#ABCD1234efgh", PageSignUp.DE_InvitationCode},		
				{"ROCOS2021", PageSignUp.DE_InvitationCode},			
				{"~!#$%^&@*()_+1234ABCDabcd", PageSignUp.DE_InvitationCode},		
				{"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA@bcd123", PageSignUp.DE_InvitationCode},		
				{"<h1>Header 1@ Injection</h1>", PageSignUp.DE_InvitationCode},		// HTML header check validation
			
			};
		return data;
		// @formatter:on
	}
}
