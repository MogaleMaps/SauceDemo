package gk.gui.tests;
//tester

import gk.generic.functions.commonBase;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.net.MalformedURLException;

public class saucedemoE2E_tests extends commonBase {

    //Declaration
    @BeforeClass
    public void beforeMethod() throws MalformedURLException, InterruptedException {
        try
        {
            setup("/TestData/GUI/SauceDemo.xlsx");
        }
        catch(Exception e)
        {
            System.out.println("Unable to initialize the Data File");
        }
    }

    @SuppressWarnings("deprecation")
    @Test
    public void saucedemoE2E_tests() {
        try {
            String sReportName = new String();
            int iFailCount = 0;
            initialiseFunctions();
            sReportName = "Global Kinetic - UI Tests" + utils.getCurrentTimeStamp();
            utils.setresultsPath("TestReports/" + sReportName);
            repo.initializeExtentReports(sReportName,"FALSE",utils);
            iRow = data.rowCount(sheet, record, resultset, data.getDataType());

            String sFunctionName = null;
            for (int i = 1 ;i<iRow;i++)
            {
                sFunctionName = null;
                if (data.getCellData("Run",i,sheet, null,null, sDataType).toUpperCase().equals("YES")) {
                    repo.setExtentTest(repo.getExtent().createTest(data.getCellData("Scenario",i,sheet, null,null, sDataType)));
                    try
                    {
                        //Open Browser
                        sFunctionName = "InitializeBrowser";
                        InitializeBrowser(i);

                        //Step 1 - Navigate to URL
                        sFunctionName = "NavigateToURL";
                        mm.NavigateToURL(sheet, i, sDataType);

                        //Step 2 - Login to Sauce Demo
                        sFunctionName = "Login to Sauce Demo";
                        mm.LoginToSauceDemo(sheet, i, sDataType);

                        //Step 3 - Select Product
                        mm.ViewProduct();

                        //Step 4 - Clicks on Add to Cart
                        sFunctionName = "Add Product to Cart";
                        mm.AddToCart();

                        //Step 5 - Checkout
                        sFunctionName="Check Out Item(s)";
                        mm.CheckOut();

                        // Step 6 - Checkout Completed Successfully
                        sFunctionName="Check Out Complete";
                        mm.CheckOutComplete();

                        //Step 13 - Add to Cart from Home Page
                        sFunctionName = "Navigate to About Us";
                        mm.NavigateToAboutUs();

                        //Close Browser
                        sFunctionName = "Collapse";
                        Collapse();
 }
                    catch (Exception e) {
                        iFailCount++;
                        repo.getExtentTest().fail(sFunctionName + ": " + e);
                        System.out.print(sFunctionName + ": " + e.getMessage());
                        Collapse();
                        continue;
                    }
                }
                     // LoginWith Problem User
             else
             if (data.getCellData("Run",i,sheet, null,null, sDataType).toUpperCase().equals("YES")) {
                 repo.setExtentTest(repo.getExtent().createTest(data.getCellData("Scenario",i,sheet, null,null, sDataType)));
                 try
                 {
                     //Open Browser
                     sFunctionName = "InitializeBrowser";
                     InitializeBrowser(i);

                     //Step 1 - Navigate to URL
                     sFunctionName = "NavigateToURL";
                     mm.NavigateToURL(sheet, i, sDataType);

                     //Step 2 - Test locked out user
                     sFunctionName = "Login with Problem User";
                     mm.LoginWithProblemUser(sheet, i, sDataType);
                     mm.ViewProduct();

                     //Step 3 - Clicks on Add to Cart
                     sFunctionName = "Add Product to Cart";
                     mm.AddToCart();

                     //Step 4 - Checkout
                     sFunctionName="Check Out Item(s)";
                     mm.CheckOut();
                     //Close Browser
                     sFunctionName = "Collapse";
                     Collapse();
                 }
                 catch (Exception e) {
                     iFailCount++;
                     repo.getExtentTest().fail(sFunctionName + ": " + e);
                     System.out.print(sFunctionName + ": " + e.getMessage());
                     Collapse();
                     continue;
                 }
             }

            }


        }
        catch(Exception e)
        {
            repo.getExtentTest().fail(e);
            System.out.print(e.getMessage());
        }
    }
    @AfterMethod
    public void afterMethod() throws Exception {
        repo.getExtent().flush();
        Collapse();
    }
}
