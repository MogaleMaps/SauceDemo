package gk.application.specific.functions.GUI;
import org.openqa.selenium.support.ui.Select;
import gk.generic.functions.DataFunctions;
import gk.generic.functions.Reporting;
import gk.generic.functions.UtilityFunctions;
import gk.generic.functions.commonBase;
import org.apache.commons.lang3.RandomStringUtils;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import org.apache.commons.text.RandomStringGenerator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.apache.poi.ss.usermodel.Sheet;
import org.testng.Assert;

import java.util.Random;
import java.util.*;
import java.text.*;

public class SauceDemo extends commonBase {

    //Think Time Variables
    private int thinkTime = 5000;
    private int thinkTimeShort = 1000;

    //Report message
    private String sReportMessage;

    //Username Credentials
    private String sUsername;
    private String sPassword;
    private String product;
    //Customer Details
    private String FirstName = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
    private String LastName =RandomStringUtils.randomAlphanumeric(5).toUpperCase();
    private String PostalCode="1724";



    public SauceDemo(DataFunctions data, UtilityFunctions utils, Reporting repo)
    {
        this.data = data;
        this.utils = utils;
        this.repo = repo;
    }

    /*****************************************************************************
     Function Name: NavigateToURL
     Description:	Functions uses the URL contained within the SourceDemo.xls spreadsheet and navigates to the URL in the selected browser
     * @throws Exception
     ******************************************************************************/
    public void NavigateToURL(Sheet sheet, int i, String dataType) throws Exception
    {
        // Navigates browser to URL
        utils.navigate(data.getCellData("URL",i,sheet, null,null, dataType));

        //Maximises the Browser window

        //Validates if object exists and Reports to Test.html
        Thread.sleep(thinkTime);
        sReportMessage = "Navigate to URL";
        repo.ExtentLogPassFail("textUsername", sReportMessage+" Successful", sReportMessage+" Unsuccessful", true,"src/test/java/gk/object/repository/GUI/Common.xml",utils);
    }

    /*****************************************************************************
     Function Name: Login Sauce Demo
     Description:	Logins into the Souce Demo

     * @throws Exception
     ******************************************************************************/
    public void LoginToSauceDemo(Sheet sheet, int i, String dataType) throws Exception
    {

        //Get Username from SauceDemo.xls and enters the text in the Username text field
        sUsername = data.getCellData("Username", i, sheet, null, null, dataType);
        utils.EnterText("textUsername", sUsername,"src/test/java/gk/object/repository/GUI/Common.xml");

        //Get Password from SauceDemo.xls and enters the text in the Password text field
        sPassword = data.getCellData("Password", i, sheet, null, null, dataType);
        utils.EnterText("textPassword", sPassword,"src/test/java/gk/object/repository/GUI/Common.xml");

        //Clicks on the Login button
        utils.ClickObject("buttonLogin","src/test/java/gk/object/repository/GUI/Common.xml");
        //Validates if object exists and Reports to Test.html
        Thread.sleep(thinkTime);
        sReportMessage = "Login";
        repo.ExtentLogPassFail("menu", sReportMessage+" Successful", sReportMessage+" Unsuccessful", true,"src/test/java/gk/object/repository/GUI/Common.xml",utils);
    }

    /*****************************************************************************
     Function Name: Log out SD
     Description:	Logs out of Sauce Demo
     * @throws Exception
     ******************************************************************************/
    public void LogoutSD() throws Exception
    {

        utils.ClickObject("menu","src/test/java/gk/object/repository/GUI/Common.xml");
        Thread.sleep(thinkTimeShort);
        utils.ClickObject("btnLogout","src/test/java/gk/object/repository/GUI/Common.xml");

        //Validates if object exists and Reports to Test.html
        Thread.sleep(3000);
        sReportMessage = "Logout";
        repo.ExtentLogPassFail("buttonLogin" ,sReportMessage+" Successful", sReportMessage+" Unsuccessful", true,"src/test/java/gk/object/repository/GUI/Common.xml",utils);
    }

    /*****************************************************************************
     Function Name: Click on Product
     Description:	View Product
     Date Created:	04/11/2019
     * @throws Exception
     ******************************************************************************/
    public void ViewProduct() throws Exception
    {
        //Clicks on On product displayed
        utils.ClickObject("product1","src/test/java/gk/object/repository/GUI/Common.xml");

        //Validates if object exists and Reports to Test.html
        Thread.sleep(thinkTime);
        sReportMessage = "View Product";
        repo.ExtentLogPassFail("btnAddToCart", sReportMessage+" Successful", sReportMessage+" Unsuccessful", true,"src/test/java/gk/object/repository/GUI/Common.xml",utils);
    }

    /*****************************************************************************
     Function Name: Add Product to Cart
    Description:	Clicks Add to Cart
            * @throws Exception
     ******************************************************************************/
    public void AddToCart() throws Exception
    {

        //Clicks on Add to cart button
        utils.ClickObject("btnAddToCart","src/test/java/gk/object/repository/GUI/Common.xml");

        //Validates if object exists and Reports to Test.html
        sReportMessage = "Click on Add to cart";

        //Validates if object exists and Reports to Test.html
        Thread.sleep(thinkTimeShort);
        repo.ExtentLogPassFail("btnRemoveFromCart", sReportMessage+" Successful", sReportMessage+" Unsuccessful", true,"src/test/java/gk/object/repository/GUI/Common.xml",utils);
    }


    /*****************************************************************************
     Function Name: Checkout
     Description:	CheckOut
     * @throws Exception
     ******************************************************************************/
    public void CheckOut() throws Exception
    {

        //Clicks on the Trolley
        utils.ClickObject("btnTrolley","src/test/java/gk/object/repository/GUI/Common.xml");
        Thread.sleep(thinkTimeShort);

        String URL = UtilityFunctions.driver.getCurrentUrl();
        Assert.assertEquals(URL, "https://www.saucedemo.com/cart.html" );

        utils.ClickObject("btnCheckOut","src/test/java/gk/object/repository/GUI/Common.xml");
        //Validates if object exists and Reports to Test.html
        Thread.sleep(3000);

        sReportMessage = "Check Out Item(s)";
        //Enter Customer Details

        utils.EnterText("txtFirstName",FirstName,"src/test/java/gk/object/repository/GUI/Common.xml");
        utils.EnterText("txtLastName",LastName,"src/test/java/gk/object/repository/GUI/Common.xml");
        utils.EnterText("txtZipCode",PostalCode,"src/test/java/gk/object/repository/GUI/Common.xml");

        Thread.sleep(thinkTimeShort);
        utils.ClickObject("btnContinue","src/test/java/gk/object/repository/GUI/Common.xml");

        //  //Validates if object exists and Reports to Test.html
       Thread.sleep(thinkTimeShort);
       repo.ExtentLogPassFail("btnFinish", sReportMessage+" Successful", sReportMessage+" Unsuccessful", true,"src/test/java/gk/object/repository/GUI/Common.xml",utils);
    }
    public void CheckOutComplete() throws Exception
    {

        //Clicks on the Trolley
        utils.ClickObject("btnFinish","src/test/java/gk/object/repository/GUI/Common.xml");

        //Validates if object exists and Reports to Test.html
        Thread.sleep(thinkTimeShort);

        sReportMessage = "Checked Out Item";


        //  //Validates if object exists and Reports to Test.html
        Thread.sleep(thinkTimeShort);
        repo.ExtentLogPassFail("btnBackHome", sReportMessage+" Successful", sReportMessage+" Unsuccessful", true,"src/test/java/gk/object/repository/GUI/Common.xml",utils);
        Thread.sleep(thinkTimeShort);
        utils.ClickObject("btnBackHome","src/test/java/gk/object/repository/GUI/Common.xml");
    }
    /*****************************************************************************
     Function Name: Add Product(s) to Cart from Home Page
     Description:	Navigate to the About us page
     * @throws Exception

     ******************************************************************************/
    public void NavigateToAboutUs() throws Exception
    {

        //Clicks on menu slider
        Thread.sleep(3000);
        utils.ClickObject("menu","src/test/java/gk/object/repository/GUI/Common.xml");
        utils.ClickObject("btnAboutUs","src/test/java/gk/object/repository/GUI/Common.xml");

        //Validates if object exists and Reports to Test.html
        sReportMessage = "Click on About Us";

        //Validates if object exists and Reports to Test.html
        Thread.sleep(5000);
        String URL = UtilityFunctions.driver.getCurrentUrl();
        Assert.assertEquals(URL, "https://saucelabs.com/" );
        Boolean verifyTitle = UtilityFunctions.driver.getTitle().equalsIgnoreCase("Cross Browser Testing, Selenium Testing, Mobile Testing | Sauce Labs");
        assertTrue(verifyTitle);
        repo.ExtentLogPassFail("btnTryForFree", sReportMessage+" Successful", sReportMessage+" Unsuccessful", true,"src/test/java/gk/object/repository/GUI/Common.xml",utils);
    }

    public void LockedOutUser(Sheet sheet, int i, String dataType) throws Exception
    {

        //Get Username from SauceDemo.xls and enters the text in the Username text field
        sUsername = data.getCellData("Username", i, sheet, null, null, dataType);
        utils.EnterText("textUsername", sUsername,"src/test/java/gk/object/repository/GUI/Common.xml");

        //Get Password from SauceDemo.xls and enters the text in the Password text field
        sPassword = data.getCellData("Password", i, sheet, null, null, dataType);
        utils.EnterText("textPassword", sPassword,"src/test/java/gk/object/repository/GUI/Common.xml");

        //Clicks on the Login button
        utils.ClickObject("buttonLogin","src/test/java/gk/object/repository/GUI/Common.xml");
        //Validates if object exists and Reports to Test.html
        Thread.sleep(thinkTime);
        sReportMessage = "Login Failed Message Displayed";
        repo.ExtentLogPassFail("buttonLogin", sReportMessage+" Successful", sReportMessage+" Unsuccessful", true,"src/test/java/gk/object/repository/GUI/Common.xml",utils);
    }

    public void LoginWithProblemUser(Sheet sheet, int i, String dataType) throws Exception
    {

        //Get Username from SauceDemo.xls and enters the text in the Username text field
        sUsername = data.getCellData("Username", i, sheet, null, null, dataType);
        utils.EnterText("textUsername", sUsername,"src/test/java/gk/object/repository/GUI/Common.xml");

        //Get Password from SauceDemo.xls and enters the text in the Password text field
        sPassword = data.getCellData("Password", i, sheet, null, null, dataType);
        utils.EnterText("textPassword", sPassword,"src/test/java/gk/object/repository/GUI/Common.xml");

        //Clicks on the Login button
        utils.ClickObject("buttonLogin","src/test/java/gk/object/repository/GUI/Common.xml");
        //Validates if object exists and Reports to Test.html
        Thread.sleep(thinkTime);
        sReportMessage = "Login with Problem User";
        repo.ExtentLogPassFail("menu", sReportMessage+" Successful", sReportMessage+" Unsuccessful", true,"src/test/java/gk/object/repository/GUI/Common.xml",utils);
    }
}
