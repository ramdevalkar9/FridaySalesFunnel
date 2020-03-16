package org.com.friday.carlookup.utils;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;

import io.github.bonigarcia.wdm.WebDriverManager;


public class DriverFactory {

	public WebDriver driver = null;
	public static int maxPageLoadWait = 100;
	private static ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();
	
	/**
	 * Utility method to configure & initialize driver instance based on browserName
	 * passed from testng.xml file.
	 * @param browserName
	 * @return null
	 */
	public void initializeWebDriver(String browserName){
		DesiredCapabilities capability = new DesiredCapabilities();

		if(browserName.equalsIgnoreCase("CHROME")){
			WebDriverManager.chromedriver().setup();
			capability = DesiredCapabilities.chrome();

			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			chromePrefs.put("intl.accept_languages", "en-US");

			ChromeOptions options = new ChromeOptions();
			//options.addArguments("--lang=en-US");
			options.setExperimentalOption("prefs", chromePrefs);
			options.addArguments("--ignore-certificate-errors");
			options.addArguments("--test-type");
			options.addArguments("--disable-extensions");
			options.addArguments("start-maximized");
			options.addArguments("disable-infobars");
			options.addArguments("--disable-notifications");
			//options.addArguments("enable-automation");
			options.addArguments("--disable-popup-blocking");
			options.setExperimentalOption("useAutomationExtension", true);
			options.setExperimentalOption("excludeSwitches",Collections.singletonList("enable-automation"));
			/*capability.setCapability(ChromeOptions.CAPABILITY, options);
			capability.setCapability(CapabilityType.TAKES_SCREENSHOT, true);*/

			driver = new ChromeDriver(options);
		}else if(browserName.equalsIgnoreCase("FIREFOX")){
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		}

		DriverFactory.setWebDriver(driver);
		driver.manage().timeouts().pageLoadTimeout(maxPageLoadWait, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
	}

	public void quitDriver() {
		driver.close();
	}

	public static WebDriver getDriver() {
		return webDriver.get();
	}

	public static void setWebDriver(WebDriver driver) {
		webDriver.set(driver);
	}

	/**
	 * This method is used take screenshot for failed testcases
	 * using ITestResult object status for running method.
	 * Stores screenshots under Screenshots folder with timestamp.
	 * @param result
	 * @param driver
	 */
	public void screenShot(ITestResult result,WebDriver driver){
		try{
			TakesScreenshot screenshot=(TakesScreenshot)driver;
			File src=screenshot.getScreenshotAs(OutputType.FILE);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
			Timestamp ts = new Timestamp(System.currentTimeMillis());
			String timestamp = sdf.format(ts).toString();
			
			FileUtils.copyFile(src, new File(System.getProperty("user.dir")+"\\"+"Screenshots"+"\\"+result.getName()+"_"+timestamp+".jpg"));
			System.out.println("Successfully captured a screenshot");
		}catch (Exception e){
			System.out.println("Exception while taking screenshot "+e.getMessage());
		} 

	}

}
