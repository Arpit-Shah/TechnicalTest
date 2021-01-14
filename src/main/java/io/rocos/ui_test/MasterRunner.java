package io.rocos.ui_test;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.artos.annotation.AfterTestSuite;
import com.artos.annotation.BeforeTestSuite;
import com.artos.framework.infra.Runner;
import com.artos.framework.infra.TestContext;
import com.artos.utils.PropertiesFileReader;

import io.rocos.infra.StaticStore;

public class MasterRunner {

	public static void main(String[] args) throws Exception {
		Runner runner = new Runner(MasterRunner.class);
		runner.run(args);
	}

	@BeforeTestSuite
	public void beforeTestSuite(TestContext context) {
		try {
			PropertiesFileReader fileReader = new PropertiesFileReader(new File("./conf/framework_config.properties"));
			StaticStore.FRAMEWORK_DRIVER = fileReader.getValue("framework.driver").trim();
		} catch (Exception e) {
			e.printStackTrace();
			context.getLogger().debug(e);
		}

		WebDriver driver;

		if (StaticStore.isFireFox()) {
			System.setProperty("webdriver.gecko.driver", "./assets/driver/geckodriver_64bit.exe");
			driver = new FirefoxDriver();
		} else {
			System.setProperty("webdriver.chrome.driver", "./assets/driver/chromedriverwin32.exe");
			driver = new ChromeDriver();
		}

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		context.setGlobalObject("DRIVER", driver);

		System.err.println("Driver Initiated");
	}

	@AfterTestSuite
	public void tearDown(TestContext context) {
		WebDriver driver = (WebDriver) context.getGlobalObject("DRIVER");
		driver.quit();
	}

}
