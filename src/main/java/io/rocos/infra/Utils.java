package io.rocos.infra;

import java.io.File;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.artos.framework.infra.TestContext;
import com.artos.utils.UtilsFile;

public class Utils {
	
	public static File takeSnapShot(TestContext context, String fileWithPath) throws Exception {

		// Convert web driver object to TakeScreenshot
		TakesScreenshot scrShot = ((TakesScreenshot) (WebDriver) context.getGlobalObject("DRIVER"));

		// Call getScreenshotAs method to create image file
		File srcFile = scrShot.getScreenshotAs(OutputType.FILE);

		// Move image file to new destination
		File destFile = new File(fileWithPath);

		// Copy file at destination
		UtilsFile.copyFile(srcFile, destFile, true);

		return destFile;
	}

}
