package gk.generic.functions;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import java.util.List;


import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;

import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

import org.apache.poi.ss.usermodel.Sheet;


public class UtilityFunctions {

    private String processName;
    public String browserDrivers;
    public String resultsPath;

    public static WebDriver driver;

    public static ExtentHtmlReporter htmlReporter;
    public static ExtentReports extent;
    public static ExtentTest extentTest;
    public static Reporting repo;
    private int thinkTime = 1000;


    private String sDefaultPath;

    public WebDriver getWebdriver()
    {
        return driver;
    }

    public void setWebDriver(WebDriver DriverTest) { driver = DriverTest; }

    public WebDriver initializeWedriver(String sdriverName, String strURL, DesiredCapabilities capabilities)
    {
        try
        {
            sDefaultPath = System.getProperty("user.dir");
            sDefaultPath = sDefaultPath.replace("batch", "");

            switch (sdriverName.toUpperCase())
            {
                case "CHROME":

                       System.setProperty("webdriver.chrome.driver", sDefaultPath+"/drivers/chromedriver91.exe");
                        ChromeOptions options = new ChromeOptions();
                       //options.addArguments("--headless");
                       options.addArguments("--window-size=1920,1080");
                        driver = new ChromeDriver(options);
                        break;

                case "FIREFOX":

                    System.setProperty("webdriver.firefox.marionette","drivers/geckodriver.exe");
                    driver =  new FirefoxDriver();
                    break;

             }
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        }catch(Exception e)
        {
            System.out.print(e.getMessage());
        }
        return driver;
    }

    public void navigate( String baseUrl)
    {

        driver.get(baseUrl);
    }



    /*****************************************************************************
     Function Name: 	checkIfObjectIsDisplayed
     Description:	Checks if an object is displayed using either an xpath, ID or a Name
     ******************************************************************************/
    public boolean checkIfObjectIsDisplayedShort( String property, String path)
    {
        boolean exists = false;
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        try
        {
            //get object properties from the xml file repository
            String[] element = xmlParser(path, property);
            switch (element[0].toUpperCase())
            {
                case "XPATH":
                    exists = driver.findElement(By.xpath(element[1])).isDisplayed() == true;
                    break;

                case "ID":
                    exists = driver.findElement(By.id(element[1])).isDisplayed() == true;
                    break;

                case "NAME":
                    exists = driver.findElement(By.name(element[1])).isDisplayed() == true;
                    break;
                case "LINKTEXT":
                    exists = driver.findElement(By.linkText(element[1])).isDisplayed() == true;
                    break;
            }
        }
        catch(Exception e)
        {
            exists=false;
        }
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        return exists;


    }

    /*****************************************************************************
     Function Name: 	checkIfObjectIsDisplayed
     Description:	Checks if an object is displayed using either an xpath, ID or a Name
     ******************************************************************************/
    public boolean checkIfObjectIsDisplayedConverse(String property, String path)
    {
        String id;
        boolean exists = true;
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        try
        {
            //get object properties from the xml file repository
            String[] element = xmlParser(path, property);
            switch (element[0].toUpperCase())
            {
                case "XPATH":
                    exists = driver.findElement(By.xpath(element[1])).isDisplayed()  == true;
                    break;

                case "ID":
                    exists = driver.findElement(By.id(element[1])).isDisplayed() == true;
                    break;

                case "NAME":
                    exists = driver.findElement(By.name(element[1])).isDisplayed() == true;
                    break;
                case "LINKTEXT":
                    exists = driver.findElement(By.linkText(element[1])).isDisplayed() == true;
                    break;

            }
        }
        catch(Exception e)
        {

            exists=false;
        }
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        return exists;

    }

    /*****************************************************************************
     Function Name: 	checkIfObjectIsDisplayed
     Description:	Checks if an object is displayed using either an xpath, ID or a Name
     ******************************************************************************/
    public boolean checkIfObjectIsDisplayedConverseShort(String property, String path)
    {
        boolean exists = true;
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        try
        {
            //get object properties from the xml file repository
            String[] element = xmlParser(path, property);
            switch (element[0].toUpperCase())
            {
                case "XPATH":
                    exists = driver.findElement(By.xpath(element[1])).isDisplayed() == false;
                    break;

                case "ID":
                    exists = driver.findElement(By.id(element[1])).isDisplayed() == false;
                    break;

                case "NAME":
                    exists = driver.findElement(By.name(element[1])).isDisplayed() == false;
                    break;
                case "LINKTEXT":
                    exists = driver.findElement(By.linkText(element[1])).isDisplayed() == false;
                    break;
            }
        }
        catch(Exception e)
        {
            exists=true;
        }
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        return exists;

    }

    /*****************************************************************************
     Function Name:    checkIfObjectIsDisplayedWithText
     Description:  Checks if an object is displayed using either an xpath, ID or a Name
     ******************************************************************************/
    public boolean checkIfObjectIsDisplayedWithText( String property, String path, String sText)
    {
        boolean exists = false;

        try
        {
            //get object properties from the xml file repository
            String[] element = xmlParser(path, property);
            element[1] = element[1] + "[text()='" +sText+ "']";
            switch (element[0].toUpperCase())
            {
                case "XPATH":
                    exists = driver.findElement(By.xpath(element[1])).isDisplayed() == true;
                    break;

                case "ID":
                    exists = driver.findElement(By.id(element[1])).isDisplayed() == true;
                    break;

                case "NAME":
                    exists = driver.findElement(By.name(element[1])).isDisplayed() == true;
                    break;
                case "LINKTEXT":
                    exists = driver.findElement(By.linkText(element[1])).isDisplayed() == true;
                    break;
            }
        }
        catch(Exception e)
        {

            exists=false;
        }
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        return exists;
    }

    /*****************************************************************************
     Function Name: 	checkIfObjectIsDisplayed
     Description:	Checks if an object is displayed using either an xpath, ID or a Name
     ******************************************************************************/
    public boolean checkIfObjectIsDisplayed(String property, String path)
    {
        boolean exists = false;
        try
        {
            //get object properties from the xml file repository
            String[] element = xmlParser(path, property);
            switch (element[0].toUpperCase())
            {
                case "XPATH":
                    exists = driver.findElement(By.xpath(element[1])).isDisplayed() == true;
                    break;

                case "ID":
                    exists = driver.findElement(By.id(element[1])).isDisplayed() == true;
                    break;

                case "NAME":
                    exists = driver.findElement(By.name(element[1])).isDisplayed() == true;
                    break;
                case "LINKTEXT":
                    exists = driver.findElement(By.linkText(element[1])).isDisplayed() == true;
                    break;

            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            exists=false;
        }
        return exists;

    }


    /*****************************************************************end robot*************************************************************************************/

    public String getCurrentTimeStamp()

    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return sdf.format(timestamp);

    }

    public List<?> selectValueByXPath(String xpath) {
        return driver.findElements(By.xpath(xpath));
    }

    public String[] xmlParser(String xmlPath, String tagName) throws SAXException, IOException, ParserConfigurationException {
        String[] element2 = new String[2];
        File fXmlFile = new File(xmlPath);
        DocumentBuilderFactory dbFactory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder =
                dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);
        doc.getDocumentElement().normalize();

        NodeList nList = doc.getElementsByTagName(tagName);

        for (int temp = 0; temp < nList.getLength(); temp++) {

            Node nNode = nList.item(temp);


            if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                Element eElement = (Element) nNode;

                String element = eElement.getElementsByTagName("identifier").item(0).getTextContent();
                String element1 = eElement.getElementsByTagName("Element").item(0).getTextContent();
                element2[0] = element;
                element2[1] = element1;


            } // end if
        } // end for loop

        return element2;
    }

    /********************************************************************************************************************************************
     Selenium Area
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException */

    public void WaitForElement( String property, String path, int intWait) throws SAXException, IOException, ParserConfigurationException
    {
        String[] element = xmlParser(path, property);
        try
        {
            WebDriverWait wait = new WebDriverWait(driver,intWait);
        }
        catch(Exception e)
        {
            System.out.println("Element "+element[1]+ " NOT found.");
        }
    }

    /*****************************************************************************
     Function Name: 	waitforProperty
     Description:	wait for the property to appear using either xpath, ID, Name, linktext and CssSelector and maximum wait time

     ******************************************************************************/
    public void waitforProperty( String property, int sWait, String path) throws SAXException, IOException, ParserConfigurationException
    {
        WebDriverWait wait = new WebDriverWait(driver,sWait);
        //get object properties from the xml file repository
        String[] element = xmlParser(path, property);
        switch (element[0].toUpperCase())
        {
            case "XPATH":
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element[1])));
                break;

            case "ID":
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(element[1])));
                break;

            case "NAME":
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(element[1])));
                break;

            case "LINKTEXT":
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(element[1])));
                break;

            case "CSSSELECTOR":
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(element[1])));
                break;

        }
    }

    /*****************************************************************************
     Function Name: 	GetText
     Description:	get text from the application using either xpath, ID, Name, linktext and CssSelector
     ******************************************************************************/
    public String GetText( String property, String path) throws SAXException, IOException, ParserConfigurationException
    {
        String strTextToReturn = null;
        //get object properties from the xml file repository
        String[] element = xmlParser(path, property);
        switch (element[0].toUpperCase())
        {
            case "XPATH":
                strTextToReturn = driver.findElement(By.xpath(element[1])).getText();
                break;

            case "ID":
                strTextToReturn = driver.findElement(By.name(element[1])).getText();
                break;

            case "NAME":
                strTextToReturn = driver.findElement(By.name(element[1])).getText();
                break;

            case "LINKTEXT":
                strTextToReturn = driver.findElement(By.name(element[1])).getText();
                break;

            case "CSSSELECTOR":
                strTextToReturn = driver.findElement(By.cssSelector(element[1])).getText();
                break;

        }
        return strTextToReturn;

    }

    /*****************************************************************************
     Function Name: 	GetTextDynamicXpath
     Description:	get text from the application using either xpath, ID, Name, linktext and CssSelector
     ******************************************************************************/
    public String GetTextDynamicXpath( String property, String path) throws SAXException, IOException, ParserConfigurationException
    {
        String strTextToReturn = null;
        String id = null;
        //get object properties from the xml file repository
        String[] element = xmlParser(path, property);
        switch (element[0].toUpperCase())
        {
            case "XPATH":
                id = driver.findElement(By.xpath(element[1])).getAttribute("id");
                strTextToReturn = driver.findElement(By.id(id)).getText();
                break;
        }
        return strTextToReturn;

    }

    /*****************************************************************************
     Function Name: ClearTextDynamicXpath
     Description:	Clear text from the application using either xpath, ID, Name, linktext and CssSelector

     ******************************************************************************/
    public void ClearTextDynamicXpath( String property, String path, int iStart) throws SAXException, IOException, ParserConfigurationException
    {
        String id = null;
        //get object properties from the xml file repository
        String[] element = xmlParser(path, property);
        switch (element[0].toUpperCase())
        {
            case "XPATH":
                id = driver.findElement(By.xpath(element[1])).getAttribute("id");
                id = id.substring(iStart);
                driver.findElement(By.id(id)).clear();
                break;
        }
    }

    /*****************************************************************************
     Function Name: ClearText
     Description:	Clear text from the application using either xpath, ID, Name, linktext and CssSelector

     ******************************************************************************/
    public void ClearObject( String property, String path) throws SAXException, IOException, ParserConfigurationException
    {
        String id = null;
        //get object properties from the xml file repository
        String[] element = xmlParser(path, property);
        switch (element[0].toUpperCase())
        {
            case "XPATH":
                driver.findElement(By.xpath(element[1])).clear();
                break;

            case "ID":
                driver.findElement(By.id(element[1])).clear();
                break;

            case "NAME":
                driver.findElement(By.name(element[1])).clear();
                break;

            case "LINKTEXT":
                driver.findElement(By.linkText(element[1])).clear();
                break;

            case "CSSSELECTOR":
                driver.findElement(By.cssSelector(element[1])).clear();
                break;

        }
    }

    /*****************************************************************************
     Function Name: 	EnterText
     Description:	Enter text to the application using either xpath, ID, Name, linktext and CssSelector and maximum wait time

     ******************************************************************************/
    public void EnterText( String property, String sText,String path) throws SAXException, IOException, ParserConfigurationException
    {
        //get object properties from the xml file repository
        String[] element = xmlParser(path, property);
        switch (element[0].toUpperCase())
        {
            case "XPATH":
                driver.findElement(By.xpath(element[1])).sendKeys(sText);
                break;

            case "ID":
                driver.findElement(By.id(element[1])).sendKeys(sText);
                break;

            case "NAME":
                driver.findElement(By.name(element[1])).sendKeys(sText);
                break;

            case "LINKTEXT":
                driver.findElement(By.linkText(element[1])).sendKeys(sText);
                break;

            case "CSSSELECTOR":
                driver.findElement(By.cssSelector(element[1])).sendKeys(sText);
                break;
        }
    }

    /*****************************************************************************
     Function Name: 	EnterText
     Description:	Enter text to the application using either xpath, ID, Name, linktext and CssSelector and maximum wait time

     ******************************************************************************/
    public void EnterTextDynamicXpath( String property, String sText,String path, int iStart) throws SAXException, IOException, ParserConfigurationException
    {
        String id = null;
        //get object properties from the xml file repository
        String[] element = xmlParser(path, property);
        switch (element[0].toUpperCase())
        {
            case "XPATH":
                id = driver.findElement(By.xpath(element[1])).getAttribute("id");
                id = id.substring(iStart);
                driver.findElement(By.id(id)).sendKeys(sText);
                break;
        }
    }

    /*****************************************************************************
     Function Name: 	EnterTextAction
     Description:	Enter text to the application using either xpath, ID, Name, linktext and CssSelector and maximum wait time

     ******************************************************************************/
    public void EnterTextAction( String property, String sText,String path) throws SAXException, IOException, ParserConfigurationException
    {
        Actions action = new Actions(driver);
        //get object properties from the xml file repository
        String[] element = xmlParser(path, property);
        switch (element[0].toUpperCase())
        {
            case "XPATH":
                //driver.findElement(By.xpath(element[1])).sendKeys(sText);
                WebElement enterTxt0 = driver.findElement(By.xpath(element[1]));
                action.sendKeys(enterTxt0, sText).build().perform();
                break;

            case "ID":
                driver.findElement(By.id(element[1])).sendKeys(sText);
                break;

            case "NAME":
                driver.findElement(By.name(element[1])).sendKeys(sText);
                break;

            case "LINKTEXT":
                driver.findElement(By.linkText(element[1])).sendKeys(sText);
                break;

            case "CSSSELECTOR":
                driver.findElement(By.cssSelector(element[1])).sendKeys(sText);
                break;
        }
    }

    /*****************************************************************************
     Function Name: PressDownKey
     Description:	Press the Down Key the application using either xpath, ID and maximum wait time

     ******************************************************************************/
    public void PressDownKey( String property, String path) throws SAXException, IOException, ParserConfigurationException
    {
        //get object properties from the xml file repository
        String[] element = xmlParser(path, property);
        switch (element[0].toUpperCase())
        {
           case "XPATH":
                driver.findElement(By.xpath(element[1])).sendKeys(Keys.DOWN);
                break;

            case "ID":
                driver.findElement(By.id(element[1])).sendKeys(Keys.DOWN);
                break;
        }
    }

    /*****************************************************************************
     Function Name: PressUpKey
     Description:	Press the Up Key the application using either xpath, ID and maximum wait time

     ******************************************************************************/
    public void PressUpKey( String property, String path) throws SAXException, IOException, ParserConfigurationException
    {
        //get object properties from the xml file repository
        String[] element = xmlParser(path, property);
        switch (element[0].toUpperCase())
        {
            case "XPATH":
                driver.findElement(By.xpath(element[1])).sendKeys(Keys.ARROW_UP);
                break;

            case "ID":
                driver.findElement(By.id(element[1])).sendKeys(Keys.ARROW_UP);
                break;
        }
    }

    /*****************************************************************************
     Function Name: PressTabKey
     Description:	Press the Up Key the application using either xpath, ID and maximum wait time

     ******************************************************************************/
    public void PressTabKey( String property, String path) throws SAXException, IOException, ParserConfigurationException
    {
        //get object properties from the xml file repository
        String[] element = xmlParser(path, property);
        switch (element[0].toUpperCase())
        {
            case "XPATH":
                driver.findElement(By.xpath(element[1])).sendKeys(Keys.TAB);
                break;

            case "ID":
                driver.findElement(By.id(element[1])).sendKeys(Keys.TAB);
                break;
        }
    }

     /*****************************************************************************
     Function Name: PressEnterKey
     Description:	Press the Enter Key the application using either xpath, ID and maximum wait time

     ******************************************************************************/
    public void PressEnterKey( String property, String path) throws SAXException, IOException, ParserConfigurationException
    {
        //get object properties from the xml file repository
        String[] element = xmlParser(path, property);
        switch (element[0].toUpperCase())
        {
            case "XPATH":
                driver.findElement(By.xpath(element[1])).sendKeys(Keys.ENTER);
                break;

            case "ID":
                driver.findElement(By.id(element[1])).sendKeys(Keys.ENTER);
                break;
        }
    }

    /*****************************************************************************
     Function Name: PressPageDownKey
     Description:	Press Page Down Key
     Date Created:	03/12/2019
     ******************************************************************************/
    public void PressPageDownKey( String property, String path) throws SAXException, IOException, ParserConfigurationException
    {
        //get object properties from the xml file repository
        String[] element = xmlParser(path, property);
        switch (element[0].toUpperCase())
        {
            case "XPATH":
                driver.findElement(By.xpath(element[1])).sendKeys(Keys.PAGE_DOWN);
                break;

            case "ID":
                driver.findElement(By.id(element[1])).sendKeys(Keys.PAGE_DOWN);
                break;
        }
    }

    /*****************************************************************************
     Function Name: PressBackSpaceKey
     Description:	Press the Back Space Key the application using either xpath, ID and maximum wait time
     Date Created:	05/12/2019
     ******************************************************************************/
    public void PressBackSpaceKey( String property, String path) throws SAXException, IOException, ParserConfigurationException
    {
        //get object properties from the xml file repository
        String[] element = xmlParser(path, property);
        switch (element[0].toUpperCase())
        {
            case "XPATH":
                driver.findElement(By.xpath(element[1])).sendKeys(Keys.BACK_SPACE);
                break;

            case "ID":
                driver.findElement(By.id(element[1])).sendKeys(Keys.BACK_SPACE);
                break;
        }
    }

    /*****************************************************************************
     Function Name: SelectAll
     Description:	Press the Controll + Key the application using either xpath, ID and maximum wait time
     Date Created:	05/12/2019
     ******************************************************************************/
    public void SelectAll( String property, String path) throws SAXException, IOException, ParserConfigurationException
    {
        //get object properties from the xml file repository
        String[] element = xmlParser(path, property);
        switch (element[0].toUpperCase())
        {
            case "XPATH":
                driver.findElement(By.xpath(element[1])).sendKeys(Keys.CONTROL + "a");
                break;

            case "ID":
                driver.findElement(By.id(element[1])).sendKeys(Keys.CONTROL + "a");
                break;
        }
    }

    /*****************************************************************************
     Function Name: 	ClickObject
     Description:	click an object in an application using either xpath, ID, Name, linktext and CssSelector and maximum wait time

     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     ******************************************************************************/
    public void ClickObject( String property, String path) throws SAXException, IOException, ParserConfigurationException
    {
        //get object properties from the xml file repository
        String[] element = xmlParser(path, property);
        switch (element[0].toUpperCase())
        {
            case "XPATH":
                driver.findElement(By.xpath(element[1])).click();
                break;

            case "ID":
                driver.findElement(By.id(element[1])).click();
                break;

            case "NAME":
                driver.findElement(By.name(element[1])).click();
                break;

            case "LINKTEXT":
                driver.findElement(By.linkText(element[1])).click();
                break;

            case "CSSSELECTOR":
                driver.findElement(By.cssSelector(element[1])).click();
                break;

            case "CLASS":
                driver.findElement(By.className(element[1])).click();
                //driver.findElement(By.).click();
                break;
        }
    }


    /*****************************************************************************
     Function Name: 	ClickObject
     Description:	click an object in an application using either xpath, ID, Name, linktext and CssSelector and maximum wait time

     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     ******************************************************************************/
    public String ClickObjectDynamicXpath( String property, String path, int iStart) throws SAXException, IOException, ParserConfigurationException
    {
        String id = null;
        //get object properties from the xml file repository
        String[] element = xmlParser(path, property);
        switch (element[0].toUpperCase())
        {
            case "XPATH":
                id = driver.findElement(By.xpath(element[1])).getAttribute("id");
                id = id.substring(iStart);
                driver.findElement(By.id(id)).click();
                break;
        }

        return id;
    }

    /*****************************************************************************
     Function Name: 	ClickObject
     Description:	click an object in an application using either xpath, ID, Name, linktext and CssSelector and maximum wait time

     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     ******************************************************************************/
    public String getObjectDynamicXpath( String property, String path) throws SAXException, IOException, ParserConfigurationException
    {
        String id = null;
        //get object properties from the xml file repository
        String[] element = xmlParser(path, property);
        switch (element[0].toUpperCase())
        {
            case "XPATH":
                id = driver.findElement(By.xpath(element[1])).getAttribute("id");
                //id = id.substring(iStart);
                //driver.findElement(By.id(id)).click();
                break;
        }

        return id;
    }


    /*****************************************************************************
     Function Name: 	ClickObject
     Description:	click an object in an application using either xpath, ID, Name, linktext and CssSelector and maximum wait time

     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     ******************************************************************************/
    public void ClickObjectDynamicMultiXpath( String property, String property1, String path, int iStart) throws SAXException, IOException, ParserConfigurationException
    {
        String id = null;
        String sXpath = null;
        //get object properties from the xml file repository
        String[] element = xmlParser(path, property);
        String[] element1 = xmlParser(path, property1);
        switch (element[0].toUpperCase())
        {
            case "XPATH":
                //id = driver.findElement(By.xpath(element[1])).getAttribute("id");
                //id = driver.findElement(By.xpath("//*[contains(@name,'isTechnologyFeasible')][1]")).getAttribute("id");
                sXpath = element[1] + " and @" + element1[0] + "='" + element1[1] + "']";
                //id = driver.findElement(By.xpath("//*[contains(@name,'isTechnologyFeasible') and @value='No']")).getAttribute("id");
                id = driver.findElement(By.xpath(sXpath)).getAttribute("id");
                id = id.substring(iStart);
                driver.findElement(By.id(id)).click();
                break;
        }
    }

    /*****************************************************************************
     Function Name: 	ClickObjectUsingAction
     Description:	click on the application using action builder using either xpath, ID, Name, linktext and CssSelector and maximum wait time

     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     ******************************************************************************/
    public void ClickObjectUsingAction( String property, String path) throws SAXException, IOException, ParserConfigurationException
    {
        Actions action = new Actions(driver);
        //get object properties from the xml file repository
        String[] element = xmlParser(path, property);
        switch (element[0].toUpperCase())
        {
            case "XPATH":
                action.moveToElement(driver.findElement(By.name(element[1]))).click().build().perform();
                break;

            case "ID":
                action.moveToElement(driver.findElement(By.name(element[1]))).click().build().perform();
                break;

            case "NAME":
                action.moveToElement(driver.findElement(By.name(element[1]))).click().build().perform();
                break;

            case "LINKTEXT":
                action.moveToElement(driver.findElement(By.name(element[1]))).click().build().perform();
                break;

            case "CSSSELECTOR":
                action.moveToElement(driver.findElement(By.name(element[1]))).click().build().perform();
                break;

            case "CLASS":
                action.moveToElement(driver.findElement(By.className(element[1]))).click().build().perform();
                break;
        }

    }


    /*****************************************************************************
     Function Name: 	CheckBoxDynamicXpath
     Description:	click an object in an application using either xpath, ID, Name, linktext and CssSelector and maximum wait time

     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     ******************************************************************************/
    public void CheckBoxDynamicXpath( String property, String path, int iStart, String sValue) throws SAXException, IOException, ParserConfigurationException
    {
        String id = null;
        String sXpath = null;
        int iLength = 0;
        //get object properties from the xml file repository
        String[] element = xmlParser(path, property);
        switch (element[0].toUpperCase())
        {
            case "XPATH":
                sXpath = element[1].substring(0, element[1].length() - 1);
                sXpath = sXpath + " and @value='" + sValue + "']";
                id = driver.findElement(By.xpath(sXpath)).getAttribute("id");
                id = id.substring(iStart);
                driver.findElement(By.id(id)).click();
                break;
        }
    }


    public void HoverAndClickObject( String property1, String property2, String path) throws SAXException, IOException, ParserConfigurationException
    {
        //get object properties from the xml file repository
        Actions action = new Actions(driver);
        String[] element1 = xmlParser(path, property1);
        String[] element2 = xmlParser(path, property2);
        switch (element1[0].toUpperCase())
        {
            case "XPATH":
                driver.findElement(By.xpath(element1[1])).click();
                action.moveToElement(driver.findElement(By.xpath(element2[1]))).click().build().perform();
                break;

            case "ID":
                driver.findElement(By.id(element1[1])).click();
                break;

            case "NAME":
                driver.findElement(By.name(element1[1])).click();
                break;

            case "LINKTEXT":
                driver.findElement(By.linkText(element1[1])).click();
                break;

            case "CSSSELECTOR":
                driver.findElement(By.cssSelector(element1[1])).click();
                break;

        }


    }


    /*****************************************************************************
     Function Name: SelectDropdown
     Description:	Selects a dropdown in an application using either xpath, ID, Name, linktext and CssSelector and maximum wait time
     Date Created:	27/08/2019
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     ******************************************************************************/
    public void SelectDropdown( String property, String sText,String path) throws SAXException, IOException, ParserConfigurationException
    {
        String id = null;

        //get object properties from the xml file repository
        String[] element = xmlParser(path, property);
        switch (element[0].toUpperCase())
        {
            case "XPATH":
                Select dropdown = new Select(driver.findElement(By.xpath(element[1])));
                dropdown.selectByVisibleText(sText);
                break;

            case "ID":
                Select dropdown1 = new Select(driver.findElement(By.id(element[1])));
                dropdown1.selectByVisibleText(sText);
                break;

            case "CLASS":
                Select dropdown2 = new Select(driver.findElement(By.className(element[1])));
                dropdown2.selectByVisibleText(sText);
                break;
        }
    }


    /*****************************************************************************
     Function Name: SelectDropdownDynamicXpath
     Description:	Selects a dropdown in an application using either xpath, ID, Name, linktext and CssSelector and maximum wait time
     Date Created:	27/08/2019
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     ******************************************************************************/
    public void SelectDropdownDynamicXpath( String property, String sText,String path, int iStart) throws SAXException, IOException, ParserConfigurationException
    {
        String id = null;

        //get object properties from the xml file repository
        String[] element = xmlParser(path, property);
        switch (element[0].toUpperCase())
        {
            case "XPATH":
                id = driver.findElement(By.xpath(element[1])).getAttribute("id");
                id = id.substring(iStart);
                Select dropdown = new Select(driver.findElement(By.id(id)));
                dropdown.selectByVisibleText(sText);
                break;

//            case "ID":
//                Select dropdown1 = new Select(driver.findElement(By.id(element[1])));
//                dropdown1.selectByVisibleText(sText);
//                break;
//
//            case "CLASS":
//                Select dropdown2 = new Select(driver.findElement(By.className(element[1])));
//                dropdown2.selectByVisibleText(sText);
//                break;
        }
    }

    public void ScrollPage()
    {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollTo(0,document.body.scrollHeight);");
    }


    /*****************************************************************************
     Function Name: DoubleClickObjectUsingActionBuilder
     Description:	Double click on the application using action builder using either xpath, ID, Name, linktext and CssSelector and maximum wait time

     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     ******************************************************************************/
    public void DoubleClickObjectUsingActionBuilder( String property, String path) throws SAXException, IOException, ParserConfigurationException
    {

        Actions action = new Actions(driver);
        //get object properties from the xml file repository
        String[] element = xmlParser(path, property);

//        boolean sText;
//        //sText = driver.findElement(By.xpath("//*[text()='20']")).getAttribute("xpath");
//        //sText = driver.findElement(By.xpath("/html/body/div[5]/div/div/table/tbody/tr[3]/td/table/tbody/tr[4]/td[6]")).getAttribute("class");
//        sText = driver.findElement(By.xpath("//*[@class='day-cell today-cell selected-day-cell']")).isDisplayed() == true;
//        if (sText == true)
//        {
//            driver.findElement(By.xpath("//*[@class='day-cell today-cell selected-day-cell']")).click();
//        }

        switch (element[0].toUpperCase())
        {
            case "XPATH":
                //action.moveToElement(driver.findElement(By.name(element[1]))).doubleClick().build().perform();
                WebElement object0 = driver.findElement(By.xpath(element[1]));
                action.doubleClick(object0).perform();
                break;

            case "ID":
                action.moveToElement(driver.findElement(By.name(element[1]))).doubleClick().build().perform();
                break;

            case "NAME":
                action.moveToElement(driver.findElement(By.name(element[1]))).doubleClick().build().perform();
                break;

            case "LINKTEXT":
                action.moveToElement(driver.findElement(By.name(element[1]))).doubleClick().build().perform();
                break;

            case "CSSSELECTOR":
                action.moveToElement(driver.findElement(By.name(element[1]))).doubleClick().build().perform();
                break;

        }

    }

    /*****************************************************************************
     Function Name: RightClickObjectUsingActionBuilder
     Description:	Right click on the application using action builder using either xpath, ID, Name, linktext and CssSelector and maximum wait time

     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     ******************************************************************************/
    public void RightClickObjectUsingActionBuilder( String property, String path) throws SAXException, IOException, ParserConfigurationException
    {

        Actions action = new Actions(driver);
        //get object properties from the xml file repository
        String[] element = xmlParser(path, property);
        switch (element[0].toUpperCase())
        {
            case "XPATH":
                //action.moveToElement(driver.findElement(By.name(element[1]))).doubleClick().build().perform();
                //WebElement object0 = driver.findElement(By.name(element[1]));
                //action.contextClick(object0).perform();
                break;

            case "ID":
                action.moveToElement(driver.findElement(By.name(element[1]))).doubleClick().build().perform();
                break;

            case "NAME":
                action.moveToElement(driver.findElement(By.name(element[1]))).doubleClick().build().perform();
                break;

            case "LINKTEXT":
                action.moveToElement(driver.findElement(By.name(element[1]))).doubleClick().build().perform();
                break;

            case "CSSSELECTOR":
                action.moveToElement(driver.findElement(By.name(element[1]))).doubleClick().build().perform();
                break;

        }

    }

    /*****************************************************************************
     Function Name: CheckCheckBox
     Description:	Select Checkbox that is not selected
     Date Created:	14/11/2019
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     ******************************************************************************/
    public void CheckCheckBox( String property, String path) throws SAXException, IOException, ParserConfigurationException
    {

        Actions action = new Actions(driver);
        //get object properties from the xml file repository
        String[] element = xmlParser(path, property);
        switch (element[0].toUpperCase())
        {
            case "XPATH":
                if ( !driver.findElement(By.xpath(element[1])).isSelected() )
                {
                    driver.findElement(By.xpath(element[1])).click();
                }
                break;

            case "ID":
                if ( !driver.findElement(By.id(element[1])).isSelected() )
                {
                    driver.findElement(By.id(element[1])).click();
                }
                break;

            case "NAME":
                if ( !driver.findElement(By.name(element[1])).isSelected() )
                {
                    driver.findElement(By.name(element[1])).click();
                }
                break;

            case "LINKTEXT":
                if ( !driver.findElement(By.linkText(element[1])).isSelected() )
                {
                    driver.findElement(By.linkText(element[1])).click();
                }
                break;

            case "CSSSELECTOR":
                if ( !driver.findElement(By.cssSelector(element[1])).isSelected() )
                {
                    driver.findElement(By.cssSelector(element[1])).click();
                }
                break;

        }

    }
    /*****************************************************************************
     Function Name: UnCheckCheckBox
     Description:	UnCheck Checkbox that is Checked
     Date Created:	14/11/2019
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     ******************************************************************************/
    public void UnCheckCheckBox( String property, String path) throws SAXException, IOException, ParserConfigurationException
    {

        Actions action = new Actions(driver);
        //get object properties from the xml file repository
        String[] element = xmlParser(path, property);
        switch (element[0].toUpperCase())
        {
            case "XPATH":
                if ( driver.findElement(By.xpath(element[1])).isSelected() )
                {
                    driver.findElement(By.xpath(element[1])).click();
                }
                break;

            case "ID":
                if ( driver.findElement(By.id(element[1])).isSelected() )
                {
                    driver.findElement(By.id(element[1])).click();
                }
                break;

            case "NAME":
                if ( driver.findElement(By.name(element[1])).isSelected() )
                {
                    driver.findElement(By.name(element[1])).click();
                }
                break;

            case "LINKTEXT":
                if ( driver.findElement(By.linkText(element[1])).isSelected() )
                {
                    driver.findElement(By.linkText(element[1])).click();
                }
                break;

            case "CSSSELECTOR":
                if ( driver.findElement(By.cssSelector(element[1])).isSelected() )
                {
                    driver.findElement(By.cssSelector(element[1])).click();
                }
                break;

        }

    }

    public  void SendEmail(String sfrom, String sto, String sReportName) {
        // Recipient's email ID needs to be mentioned.
        String to = sto;

        // Sender's email ID needs to be mentioned
        String from = sfrom;

        final String username = "rishen.moodley1@gmail.com";//change accordingly
        final String password = "A@ravmoodley081216G";//change accordingly

        // Assuming you are sending email through relay.jangosmtp.net
        String host = "smtp.gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "25");

        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            // Set Subject: header field
            message.setSubject("Please see attached automation results");

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Now set the actual message
            messageBodyPart.setText("This is message body");

            // Create a multipar message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            String filename = "report/"+sReportName+".html";
            DataSource source = new FileDataSource(filename);

            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            message.setContent(multipart);

            // Send message
            Transport.send(message);

            System.out.println("Sent message successfully to: "+sto);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    // Function that matches input str with
    // given wildcard pattern
    public static boolean strmatch(String str, String pattern,
                            int n, int m)
    {
        // empty pattern can only match with
        // empty string
        if (m == 0)
            return (n == 0);

        // lookup table for storing results of
        // subproblems
        boolean[][] lookup = new boolean[n + 1][m + 1];

        // initailze lookup table to false
        for(int i = 0; i < n + 1; i++)
            Arrays.fill(lookup[i], false);


        // empty pattern can match with empty string
        lookup[0][0] = true;

        // Only '*' can match with empty string
        for (int j = 1; j <= m; j++)
            if (pattern.charAt(j - 1) == '*')
                lookup[0][j] = lookup[0][j - 1];

        // fill the table in bottom-up fashion
        for (int i = 1; i <= n; i++)
        {
            for (int j = 1; j <= m; j++)
            {
                // Two cases if we see a '*'
                // a) We ignore '*'' character and move
                //    to next  character in the pattern,
                //     i.e., '*' indicates an empty sequence.
                // b) '*' character matches with ith
                //     character in input
                if (pattern.charAt(j - 1) == '*')
                    lookup[i][j] = lookup[i][j - 1] ||
                            lookup[i - 1][j];

                    // Current characters are considered as
                    // matching in two cases
                    // (a) current character of pattern is '?'
                    // (b) characters actually match
                else if (pattern.charAt(j - 1) == '?' ||
                        str.charAt(i - 1) == pattern.charAt(j - 1))
                    lookup[i][j] = lookup[i - 1][j - 1];

                    // If characters don't match
                else lookup[i][j] = false;
            }
        }

        return lookup[n][m];
    }

    public void Maximise() throws Exception
    {
        //driver.manage().window().maximize();
    }

    /*****************************************************************************
     Function Name: 	SetText
     Description:	click an object in an application using either xpath, ID, Name, linktext and CssSelector and maximum wait time

     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     ******************************************************************************/
    public void SetTextWhenPopulated( String iColumn, int i,Sheet sheet, String property, String path, String dataType) throws Exception {
        //get object properties from the xml file repository
        DataFunctions idata = new DataFunctions();
        String iText;
        iText= idata.getCellData(iColumn, i, sheet, null, null, dataType);
           String[] element = xmlParser(path, property);
        if (iText.length() > 0)
        {
            switch (element[0].toUpperCase())
            {
                case "XPATH":
                    driver.findElement(By.xpath(element[1])).click();
                    driver.findElement(By.xpath(element[1])).clear();
                    driver.findElement(By.xpath(element[1])).sendKeys(iText);
                    driver.findElement(By.xpath(element[1])).sendKeys(Keys.TAB);
                    break;

                case "ID":
                    driver.findElement(By.id(element[1])).clear();
                    //driver.findElement(By.id(element[1])).click();
                    driver.findElement(By.id(element[1])).sendKeys(iText);
                    driver.findElement(By.xpath(element[1])).sendKeys(Keys.ENTER);
                    break;

                case "NAME":
                    driver.findElement(By.name(element[1])).clear();
                    //driver.findElement(By.name(element[1])).click();
                    driver.findElement(By.name(element[1])).sendKeys(iText);
                    driver.findElement(By.name(element[1])).sendKeys(Keys.ENTER);
                    break;

                case "LINKTEXT":
                    driver.findElement(By.linkText(element[1])).clear();
                    //driver.findElement(By.linkText(element[1])).click();
                    driver.findElement(By.linkText(element[1])).sendKeys(iText);
                    driver.findElement(By.linkText(element[1])).sendKeys(Keys.ENTER);
                    break;

                case "CSSSELECTOR":
                    driver.findElement(By.cssSelector(element[1])).clear();
                    //driver.findElement(By.cssSelector(element[1])).click();
                    driver.findElement(By.cssSelector(element[1])).sendKeys(iText);
                    driver.findElement(By.cssSelector(element[1])).sendKeys(Keys.ENTER);
                    break;

                case "CLASS":
                    driver.findElement(By.className(element[1])).clear();
                    driver.findElement(By.className(element[1])).click();
                    driver.findElement(By.className(element[1])).sendKeys(iText);
                    driver.findElement(By.className(element[1])).sendKeys(Keys.ENTER);
                    //driver.findElement(By.).click();
                    break;
            }
    }   }

    /*****************************************************************************
     Function Name: ScrollToElement
     Description:	ScrollToElement

     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     ******************************************************************************/
    public void ScrollBy1000() throws SAXException, IOException, ParserConfigurationException
    {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Actions action = new Actions(driver);
        //get object properties from the xml file repository
        js.executeScript("window.scrollBy(0,1000)");

    }
    /*****************************************************************************
     Function Name: PressDeleteKey
     Description:	Press Delete Key the application using either xpath, ID and maximum wait time
     Date Created:	06/12/2019
     ******************************************************************************/
    public void PressDeleteKey( String property, String path) throws SAXException, IOException, ParserConfigurationException
    {
        // get object properties from the xml file repository
        String[] element = xmlParser(path, property);
        switch (element[0].toUpperCase())
        {
            case "XPATH":
                driver.findElement(By.xpath(element[1])).sendKeys(Keys.DELETE);
                break;

            case "ID":
                driver.findElement(By.id(element[1])).sendKeys(Keys.DELETE);
                break;
        }
    }

    /*****************************************************************************
     Function Name: CreateDirectory
     Description:	Create Directory in File System
     Date Created:	21/02/2020
     ******************************************************************************/
    public void CreateDirectory(String path)
    {
        File file = new File(path);
        if (!file.exists()) {
            if (file.mkdir()) {
                System.out.println("Report directory is created!");
            } else {
                System.out.println("Failed to create Report directory!");
            }
        }
    }

    /*****************************************************************************
     Function Name: getresultsPath
     Description:	Get resultsPath value, used for results and screenshots
     Date Created:	24/02/2020
     ******************************************************************************/
    public String getresultsPath()
    {
        return resultsPath;
    }

    /*****************************************************************************
     Function Name: setresultsPath
     Description:	Set resultsPath value, used for results and screenshots
     Date Created:	21/02/2020
     ******************************************************************************/
    public void setresultsPath(String path)
    {
        resultsPath = path;
    }

    /*****************************************************************************
     Function Name: Copy  coptens
     Description:	Create Directory in File System
     Date Created:	21/02/2020
     ******************************************************************************/
    public void CopyDirectory(String sourcePath, String destinationPath) throws Exception
    {
        try
        {
            String command = "cmd.exe";
            Process process = Runtime.getRuntime().exec(command);

            command = "xcopy " + sourcePath + "/* " + destinationPath + " /-y /s /i /m";
            process = Runtime.getRuntime().exec(command);
        }
        catch(IOException e){}
    }

    public WebElement getWebElement(String property, String xpath) {
        return driver.findElement(By.xpath(xpath));
    }

    public void EnterDatePicker(String property, String path) throws Exception {
        String[] element = xmlParser(path, property);

        switch (element[0].toUpperCase())
        {
            case "XPATH":
                driver.findElement(By.xpath(element[1])).click();
                break;

            case "ID":
                driver.findElement(By.id(element[1])).click();
                break;
        }
    }

    public void SelectedDatePicker(String dateInputXpath, String yearMontText, String selectedDate, String nextDate, String dayXPath) throws Exception {

        // Click the data picker text input
        driver.findElement(By.xpath(dateInputXpath)).click();

        String[] date = selectedDate.split("-");
        String selectMonth = "";

        int year = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]);
        int day = Integer.parseInt(date[2]);


        Thread.sleep(3000);

        switch (month) {
            case 1:
                selectMonth = "JANUARY";
                break;
            case 2:
                selectMonth = "FEBRUARY";
                break;
            case 3:
                selectMonth = "MARCH";
                break;
            case 4:
                selectMonth = "APRIL";
                break;
            case 5:
                selectMonth = "MAY";
                break;
            case 6:
                selectMonth = "JUNE";
                break;
            case 7:
                selectMonth = "JULY";
                break;
            case 8:
                selectMonth ="AUGUST";
                break;
            case 9:
                selectMonth = "SEPTEMBER";
                break;
            case 10:
                selectMonth = "OCTOBER";
                break;
            case 11:
                selectMonth = "NOVEMBER";
                break;
            case 12:
                selectMonth = "DECEMBER";
                break;
        }

        // Thread
        Thread.sleep(1000);
        // get month and year
        String monthYear = selectMonth.toUpperCase() + " " + year;

        while (true) {
            String yearMonth = driver.findElement(By.xpath(yearMontText)).getText().toUpperCase();
               // System.out.println("picker date " + yearMonth);
            if (yearMonth.equals(monthYear)) {
                break;
            } else {
                driver.findElement(By.xpath(nextDate)).click();
            }
        }

      // driver.findElement(By.xpath(dayXPath + "[contains(text(),"+ day + ")]")).click();
       // declare web ele
      List<WebElement> allDates = driver.findElements(By.xpath(dayXPath));


        // JavascriptExecutor jse = (JavascriptExecutor)driver;

        for (WebElement webElement : allDates) {

            int webElementDay = Integer.parseInt(webElement.getText());
           // System.out.println("day " + webElementDay);
         if (webElementDay == day) {
            // Actions actions = new Actions(driver);
            // System.out.println("click me " + webElement.getText());
              webElement.click();
             // jse.executeScript("arguments[0].scrollIntoView()", webElement);
             // Thread.sleep(3000);

            // actions.moveToElement(webElement).click().perform();
           //  driver.findElement(By.xpath(dayXPath + "[contains(text(),"+ day + ")]")).submit();
            // actions.moveToElement(webElement);
            // actions.perform();
             break;
         }
       }
    }

    public void SelectedDropDownValue(String xpath, String listXpath) throws Exception {

        driver.findElement(By.xpath(xpath)).click();
        Thread.sleep(1000);
        List<WebElement> webElement = driver.findElements(By.xpath(listXpath));

        for (WebElement dropDownValue: webElement) {
            System.out.println(dropDownValue.getText());
        }
    }
}




