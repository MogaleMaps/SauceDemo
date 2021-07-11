# SauceDemo
UI End to End tests using Selenium for Global Kinetic Assessment

#Author
Johnson Mogale Mapaela
Language : Java/Selenium
IDE: IntelliJ
P.S Please download all Maven Packages before running. 

#Running the Test
1. Test will run using Maven
2. Test uses Chrome Driver version 91+
3. Test Will execute in headless mode , configure in the Utility Functions Class
4. All modules/methods/classes are coupled with Developer notes to in order to understand what the respective class/module does.

#Test Report
1. A Test Report Directory will be created in the source folder upon executing the tests.
2. Extent Reports Used. 

#Test Approach
1. Reading and Capturing Data:
	1.1 Used a .xlsx file located in the TestData directory to store test cases to be executed. (Created 2 Test Cases , One that passes and One that Fails)

2. Used xpath as a locator for web-elements, Ideally , locating elements using id is recommended , I used xpath as an experiment using the new xpath locator tool. 

3. Created for generic functions , ie , classes I will always call in order to reduce repeating code in each of my test classes. 
	3.1 UtilityFunctions  --> Contains all webdriver config settings, path(s) and functions to check if web-elements are visible.
	3.2 commonBase class --> Contains data specific actions, i.e Config for reading excel document that contains test scenarions 
	3.3 Reporting class --> Contains reporting specific actions and config , i.e how the report is generated , what is logged , screenshots, date-time stamps etc.
	3.4 DataFunctions class  --> This reads environment variables from a Json file (Solution was found on stackOverFlow and Yet to read more on this approach)

4. Tests
	4.1 (Positive Test) Login with authorised user , View shopping item , Add to cart , checkout and navigate to About us page
	4.2 (Negative Test) Login with user credentials that expose an issue to the webpage , Try adding item to cart , checkout (This test exposes that there may be a defect in the webpage as the test fails).
	4.3 Covered the following
		4.3.1  Click , Send Texts, Navigate 
		4.3.2  Assertion of url , Page elements being visible
		4.3.3 Dynamic Variable(s) when capturing user informaion (Minimise hard-coding)
		4.3.4 Clear and easy to locate classes using a folder structure that is easy to read

5. Web-Element Locator
	5.1 Created an XML file that contains all web-elements for sauceLabs that will be used in the Test(s). File name, Common.xml 

Extra Notes

* Open to new ways of writing tests( Please recommended/advise/suggest)
* Issues encountered: Screenshots for test are being taken however not shown in Extent Test Report (Investigating)


