package org.com.friday.carlookup.scripts;

import java.io.IOException;

import org.com.friday.carlookup.helper.CarInsuranceHelper;
import org.com.friday.carlookup.utils.DriverFactory;
import org.com.friday.carlookup.utils.FileUtil;
import org.com.friday.carlookup.utils.InputDataProvider;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class NegativeScenario extends DriverFactory{
	
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
	 * Negative scenario to test if website throws correct error message
	 * for incorrect registration date.
	 */
	@Test(groups = {"negative"})
	public void Negative_InvalidRegDate() {
		
		try {
			Reporter.log("Inside Negative_InvalidRegDate method");
			CarInsuranceHelper carInsuranceHelper= new CarInsuranceHelper(getDriver());
			getDriver().get(FileUtil.readPropertyFile().getProperty("url"));
			carInsuranceHelper.clickOnLandingbanner();
			String actualMsg = carInsuranceHelper.enterRegistrationDate("11111111");
			
			Reporter.log("Expected Message:::: Hups! Dieses Datum liegt in der Vergangenheit. Bitte überprüfe deine Eingabe.");
			Reporter.log("Actual Message:::: "+actualMsg+"");
			Assert.assertEquals(actualMsg, "Hups! Dieses Datum liegt in der Vergangenheit. Bitte überprüfe deine Eingabe.");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Negative scenario to test if website throws correct error message
	 * for incorrect HSN/TSN value.
	 */
	@Test(groups = {"negative"})
	public void Negative_InvalidHSNTSN() {
		
		try {
			Reporter.log("Inside Negative_InvalidHSNTSN method");
			CarInsuranceHelper carInsuranceHelper= new CarInsuranceHelper(getDriver());
			getDriver().get(FileUtil.readPropertyFile().getProperty("url"));
			carInsuranceHelper.clickOnLandingbanner();
			carInsuranceHelper.selectPreCondition("Y");
			carInsuranceHelper.selectRegisteredOwner("Y", "Y");
			String actualMsg = carInsuranceHelper.enterHSNTSN("032020", "0009", "888");
			
			Reporter.log("Expected Message:::: Wir konnten kein passendes Fahrzeug ermitteln. Bitte gib nur PKW ein - keine Roller, Motorräder, LKW, Wohnmobile oder Anhänger.");
			Reporter.log("Actual Message:::: "+actualMsg+"");
			Assert.assertEquals(actualMsg, "Wir konnten kein passendes Fahrzeug ermitteln. Bitte gib nur PKW ein - keine Roller, Motorräder, LKW, Wohnmobile oder Anhänger.");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Negative scenario to test if website throws correct error message
	 * for below 18 Date of Birth.
	 */
	@Test(groups = {"negative"},dataProvider="data-provider",dataProviderClass=InputDataProvider.class)
	public void Negative_Under18DOB(String scenario,String make,String model,String bodyType,String fuelType,
			String enginePower,String engine,String HSNTSN,String firstRegistrationMonth,String firstRegistrationYear,String supportingDetails) {
		
		try {
			Reporter.log("Inside Negative_Under18DOB method");
			String[] insuranceDetails = supportingDetails.split("\\|");
			
			CarInsuranceHelper carInsuranceHelper= new CarInsuranceHelper(getDriver());
			getDriver().get(FileUtil.readPropertyFile().getProperty("url"));
			carInsuranceHelper.clickOnLandingbanner();
			
			carInsuranceHelper.selectPreCondition(insuranceDetails[0]);
			carInsuranceHelper.selectRegisteredOwner(insuranceDetails[1], insuranceDetails[2]);
			
			carInsuranceHelper.selectVehicle(make, model,bodyType, fuelType, enginePower, HSNTSN, firstRegistrationMonth+firstRegistrationYear);
			String actualMsg = carInsuranceHelper.validateAgeCriteria("02012018");
			
			Reporter.log("Expected Message:::: Wir können keine Personen versichern, die jünger als 18 Jahre sind.");
			Reporter.log("Actual Message:::: "+actualMsg+"");
			Assert.assertEquals(actualMsg, "Wir können keine Personen versichern, die jünger als 18 Jahre sind.");
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
