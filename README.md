# FridaySalesFunnel

### Project Description:
This project illustrates Selenium framework with TestNG to validate scenarios on SalesFunnel(Friday).
Implements positive scenario which reads data from excel file and executes till it reaches DOB page along with few negative scenarios.

Pure Java project that uses maven as build tool to manage dependencies and execution. TestNG has been used to handle parameterization,taking screenshots for failed tests and showcase report. It also depicts basic reporting using 'extentreports'.
JaCoCo plugin is used to represent code coverage.

### Pre-Requisite:
*Project have been run on windows 10 platform with JDK 1.8. Please ensure JDK 1.8(64 bit) is installed and configure jdk 1.8 if compilation fails.*

### Project/Test Details & ThoughtProcess:
Selenium(3.5.3) is used to automate SalesFunnel flow which was expected to be run for 3 Different car brands and each brand with 3 variants, making it 9 scenarios. For which data is stored in excel file as below and fetched using fillo api. Data then passed as inputs to testng test using dataprovider annotation which helps to execute scenario for all 9 inputs.
 
_Please note: SupportingDetails(Column K) denotes values for below fields._**
 
* If car is insured or registered(Das Auto ist schon versichert ?)
* If owner (Wird das Auto auf dich zugelassen?)
* If used or new (Das Auto ist...)

### Test Scenarios:
1.	CarLookupPositive : Test to execute on SalesFunnel as described above.
2.	InvalidRegDate : Passes hard-coded invalid date to validate if correct message is shown for eg, if date is in past.
... InvalidHSNTSN : Validates if incorrect HSN/TSN results in proper error message asking user to enter correct details.
... Under18DOB : As Friday doesn’t allow under 18 driver to be registered, thi test verifies if such date throws relevant error message.

### Package Information:
 
* _org.com.friday.carlookup.helper_ : Helper class similar to page class in POM model, which has WebElements and page functions(methods) which collectively forms 1 logical flow to go through registration process.
* _org.com.friday.carlookup.listeners_ : Listener class to add extentreport reporting.
* _org.com.friday.carlookup.utils_ : Utility classes to initialize browser instance, read properties file,dataprovider class and wrapper methods to carryout user actions on page.
* _org.com.friday.carlookup.scripts_ : Classes with testng tests as described above.
* _src/test/resources_ : Contains inputdata sheet and properties file that stores salesfunnel url.

### Steps to build/Run:
Run as Maven Build

clean install -Dbrowsername=chrome -Dgroups=positive

Valid values are :
*	Browser : chrome,firefox
*	Groups : positive,negative,regression


### Reporting:
Upon successful project execution reports can be viewed as below:
1.	Target/surefire-reports/emailable-report.html
2.	Target/surefire-reports/extent.html
 

3.	Target/jacoco-report/index.html
 

### Jars Used:
* Selenium
* TestNG
* Maven
* Webdrivermanager
* Fillo
* extentreports
