package gk.generic.functions;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.windows.WindowsDriver;


public class Reporting {
    private String processName;
    public String browserDrivers;
    public static WebDriver driver;

    public static ExtentHtmlReporter htmlReporter;
    public static ExtentReports extent;
    public static ExtentTest extentTest;
    public String resultsPath;



    public ExtentReports getExtent() {
        return extent;
    }

    public void setExtent(ExtentReports extentTest) {
        extent = extentTest;
    }

    public ExtentTest getExtentTest() {
        return extentTest;
    }

    public void setExtentTest(ExtentTest extentTest) {
        Reporting.extentTest = extentTest;
    }

    @BeforeTest
    public ExtentReports initializeExtentReports(String sReportName, String sAppend, UtilityFunctions utils)
    {

           sReportName = "Test";

            if (sAppend.toUpperCase().equals("TRUE")) {
                htmlReporter = new ExtentHtmlReporter(utils.getresultsPath() + "/"+ sReportName+".html");
                htmlReporter.setAppendExisting(true);}
            else {

                File file = new File(utils.getresultsPath());
                if (!file.exists()) {
                    if (file.mkdir()) {
                        System.out.println(" Report directory is created!");
                      } else {
                        System.out.println(" Failed to create Report directory!");
                    }
                }
                htmlReporter = new ExtentHtmlReporter(utils.getresultsPath() + "/" + sReportName + ".html");

            }

            extent = new ExtentReports();
            extent.attachReporter(htmlReporter);



        return extent;
    }



    /********************************************************************************************************************************************
     * Extent Reporting
     * @param
     * @throws Exception
     */

    public void ExtentLogPassDB(String sMessagePass, ExtentTest logger) throws Exception
    {
        logger.pass(sMessagePass);
    }

    public void ExtentLogInfoDB(String sMessagePass, ExtentTest logger) throws Exception
    {
        logger.info(sMessagePass);

    }

    public void ExtentLogPassFail( String sObject, String sMessagePass, String sMessageFail, Boolean Screenshot, String xmlpath, UtilityFunctions utils) throws Exception
    {

        if (utils.checkIfObjectIsDisplayed(sObject, xmlpath))
        {
            ExtentLogPass( sMessagePass, Screenshot, utils);
        }
        else
        {
            ExtentLogFail( sMessageFail, Screenshot,utils);
            System.out.println(sMessageFail);  //Added to have error in output log for build Server.
        }

    }

    public void ExtentLogPassFailConverse( String sObject, String sMessagePass, String sMessageFail, Boolean Screenshot, String xmlpath, UtilityFunctions utils) throws Exception
    {

        if (utils.checkIfObjectIsDisplayedConverse(sObject, xmlpath))
        {
            ExtentLogFail( sMessageFail, Screenshot,utils);
        }
        else
        {
            ExtentLogPass( sMessagePass, Screenshot, utils);
        }

    }

    public void ExtentLogPassFailWithText( String sObject, String sMessagePass, String sMessageFail, Boolean Screenshot, String xmlpath, UtilityFunctions utils, String sText) throws Exception
    {

        if (utils.checkIfObjectIsDisplayedWithText(sObject, xmlpath,sText))
        {

            ExtentLogPass( sMessagePass, Screenshot, utils);

        }
        else
        {
            ExtentLogFail( sMessageFail, Screenshot,utils);
        }

    }

    public void ExtentLogPassFailConverseWithText( String sObject, String sMessagePass, String sMessageFail, Boolean Screenshot, String xmlpath, UtilityFunctions utils, String sText) throws Exception
    {

        if (utils.checkIfObjectIsDisplayedWithText(sObject, xmlpath,sText))
        {
            ExtentLogFail( sMessageFail, Screenshot,utils);
        }
        else
        {
            ExtentLogPass( sMessagePass, Screenshot, utils);
        }

    }


    public void ExtentLogFailWarningConverse( String sObject, String sMessageFail, String sMessageWarning, Boolean Screenshot, String xmlpath, UtilityFunctions utils) throws Exception
    {

        if (utils.checkIfObjectIsDisplayedConverse(sObject, xmlpath))
        {
            ExtentLogWarning( sMessageWarning, Screenshot,utils);
        }
        else
        {
            ExtentLogFail( sMessageFail, Screenshot, utils);
        }

    }

    public void ExtentLogPass( String sMessagePass, Boolean Screenshot, UtilityFunctions utils) throws Exception
    {


        if (Screenshot)
        {
            String fileName = takeScreenShotNew("ExtentLogPass", utils);
            getExtentTest().pass(sMessagePass, MediaEntityBuilder.createScreenCaptureFromPath(fileName).build());
            extentTest.addScreenCaptureFromPath(fileName, sMessagePass);
            extent.flush();
        }
        else
        {
            getExtentTest().pass(sMessagePass);
        }
    }


    public void ExtentLogPass2( String sMessagePass, Boolean Screenshot, UtilityFunctions utils, WindowsDriver strWebDriver) throws Exception
    {


        if (Screenshot)
        {
            String fileName=takeScreenShot2("ExtentLogPass", utils,  strWebDriver);
            getExtentTest().pass(sMessagePass, MediaEntityBuilder.createScreenCaptureFromPath(fileName).build());

        }
        else
        {
            getExtentTest().pass(sMessagePass);
        }
    }

    public void ExtentLogFail2( String sMessageFail, Boolean Screenshot, UtilityFunctions utils, WindowsDriver strWebDriver) throws Exception
    {


        if (Screenshot)
        {
            String fileName=takeScreenShot2("ExtentLogPass", utils,  strWebDriver);
            getExtentTest().fail(sMessageFail, MediaEntityBuilder.createScreenCaptureFromPath(fileName).build());

        }
        else
        {
            getExtentTest().fail(sMessageFail);
        }
    }


    public void ExtentLogFail( String sMessageFail, Boolean Screenshot, UtilityFunctions utils) throws Exception
    {


        if (Screenshot)
        {
            String fileName = takeScreenShotNew("ExtentLogFail", utils);
            getExtentTest().fail(sMessageFail, MediaEntityBuilder.createScreenCaptureFromPath(fileName).build());
            extentTest.addScreenCaptureFromPath(fileName, sMessageFail);
            extent.flush();

        }
        else
        {
            getExtentTest().fail(sMessageFail);
        }
    }


    public void ExtentLogWarning( String sMessageWarning, Boolean Screenshot, UtilityFunctions utils) throws Exception
    {


        if (Screenshot)
        {
            String fileName=takeScreenShot("ExtentLogFail", utils);
            getExtentTest().warning(sMessageWarning, MediaEntityBuilder.createScreenCaptureFromPath(fileName).build());

        }
        else
        {

            getExtentTest().warning(sMessageWarning);
        }
    }

    public void ExtentLogInfo( String sMessageInfo, Boolean Screenshot, UtilityFunctions utils) throws Exception
    {


        if (Screenshot)
        {
            String fileName=takeScreenShot("ExtentLogFail", utils);
            getExtentTest().info(sMessageInfo, MediaEntityBuilder.createScreenCaptureFromPath(fileName).build());

        }
        else
        {
            getExtentTest().info(sMessageInfo);

        }
    }


    public String takeScreenShot2(String FileName, UtilityFunctions utils, WindowsDriver strWebDriver) throws Exception {
        String fileName="Empty";

        try
        {
            String sDefaultPath = System.getProperty("user.dir");
            sDefaultPath = sDefaultPath.replace("batch", "");
            File scrFile = ((TakesScreenshot)strWebDriver).getScreenshotAs(OutputType.FILE);

            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            fileName =sDefaultPath+"/screenshots/"+FileName+timeStamp+".png";

            // try {
            FileUtils.copyFile(scrFile, new File(fileName));
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        return fileName;
    }


    public String takeScreenShot(String FileName, UtilityFunctions utils) throws Exception {
        String fileName="Empty";

        try
        {
            String sDefaultPath = System.getProperty("user.dir");
            sDefaultPath = sDefaultPath.replace("batch", "");
            File scrFile = ((TakesScreenshot)utils.getWebdriver()).getScreenshotAs(OutputType.FILE);

            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            fileName =sDefaultPath + "/screenshot/"+timeStamp+".png";

            // try {
            FileUtils.copyFile(scrFile, new File(fileName));
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        return fileName;
    }

    public String takeScreenShotNew(String FileName, UtilityFunctions utils) throws Exception {
        String fileName="Empty";
        String fileName2="NULL";

        try
        {

            File scrFile = ((TakesScreenshot)utils.getWebdriver()).getScreenshotAs(OutputType.FILE);

            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            fileName = utils.getresultsPath() + "/" + timeStamp+".png";
            fileName2 = "Z"+ timeStamp+".png";

            FileUtils.copyFile(scrFile, new File(fileName));
           // extentTest.
        } catch (Exception e1) {

            e1.printStackTrace();
        }

        return fileName;
    }

}
