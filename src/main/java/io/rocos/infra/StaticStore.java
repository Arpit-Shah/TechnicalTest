package io.rocos.infra;

public class StaticStore {

	public static String FRAMEWORK_DRIVER;

	public static boolean isFireFox() {
		System.out.println(FRAMEWORK_DRIVER);
		return FRAMEWORK_DRIVER.equals("firefox") ? true : false;
	}

	public static boolean isChrome() {
		return FRAMEWORK_DRIVER.equals("chrome") ? true : false;
	}

}
