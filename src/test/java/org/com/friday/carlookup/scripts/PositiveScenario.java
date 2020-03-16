package org.com.friday.carlookup.scripts;

import java.io.IOException;

import org.com.friday.carlookup.helper.CarInsuranceHelper;
import org.com.friday.carlookup.utils.DriverFactory;
import org.com.friday.carlookup.utils.FileUtil;
import org.com.friday.carlookup.utils.InputDataProvider;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class PositiveScenario extends DriverFactory{
	
	/**
	 * This is called before every test to initialize browser based on
	 * browser parameter in testng.xml
	 * @param browser
	 */
	@Parameters({"browserName"})
	@BeforeMethod(alwaysRun = true)
	public void setup(String browser) {
		initializeWebDriver(browser);
	}
	
	/**
	 * Positive test that reads data from excel file & repeats execution
	 * for all scenarios mentioned in data sheet file.
	 * Uses CarInsuranceHelper object to call respective methods
	 * to complete the execution.
	 * @param Scenario
	 * @param Make
	 * @param Model
	 * @param BodyType
	 * @param FuelType
	 * @param EnginePower
	 * @param Engine
	 * @param HSNTSN
	 * @param FirstRegistrationMonth
	 * @param FirstRegistrationYear
	 * @param SupportingDetails
	 */
	@Test(groups = {"positive"},dataProvider="data-provider",dataProviderClass=InputDataProvider.class)
	public void CarLookupPositive(String Scenario,String Make,String Model,String BodyType,String FuelType,
			String EnginePower,String Engine,String HSNTSN,String FirstRegistrationMonth,String FirstRegistrationYear,String SupportingDetails) {
		try {
			
			String[] insuranceDetails = SupportingDetails.split("\\|");
			
			CarInsuranceHelper carInsuranceHelper= new CarInsuranceHelper(getDriver());
			getDriver().get(FileUtil.readPropertyFile().getProperty("url"));
			carInsuranceHelper.clickOnLandingbanner();
			
			carInsuranceHelper.selectPreCondition(insuranceDetails[0]);
			carInsuranceHelper.selectRegisteredOwner(insuranceDetails[1], insuranceDetails[2]);
			
			String DOBText = carInsuranceHelper.selectVehicle(Make, Model,BodyType, FuelType, EnginePower, HSNTSN, FirstRegistrationMonth+FirstRegistrationYear);
			
			Assert.assertEquals(DOBText, "Wann wurdest du geboren?");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This is called everytime post execution of test method
	 * Takes screenshot, if failed and closes browser instance.
	 */
	@AfterMethod(alwaysRun = true)
	public void tearDown(ITestResult result) {
		if(ITestResult.FAILURE==result.getStatus()) {
			screenShot(result,getDriver());
		}
		quitDriver();
	}

}