package gk.generic.functions;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.codoid.products.fillo.Recordset;

import gk.application.specific.functions.GUI.*;

import org.apache.poi.ss.usermodel.Sheet;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class commonBase {

    protected UtilityFunctions utils = new UtilityFunctions();
    protected DataFunctions data = new DataFunctions();
    protected Reporting repo = new Reporting();
    protected SauceDemo mm;


    public static String sDefaultPath;
    protected String sDataType;
    protected Sheet sheet;
    protected Sheet sheetData;
    protected int iRow;
    protected int iRowData;
    protected Recordset record;
    protected ResultSet resultset;

    public void setup(String Location) throws Exception {
        try
        {
            sDefaultPath = System.getProperty("user.dir");
            sDefaultPath = sDefaultPath.replace("batch", "");
            data.GetEnvironmentVariables();
            sDataType = data.getDataType();
            repo.setExtent(repo.initializeExtentReports(data.getReportName(), data.getAppendReport(), utils));

            switch (data.getDataType().toUpperCase())
            {
                case "EXCEL": sheet = data.ReadExcel(sDefaultPath+Location,"Sheet1");
                    iRow = data.rowCount(sheet, record, resultset, sDataType)-1;
                    break;

            }

        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void setupData(String Location, String sheetName) throws Exception {
        try
        {
            switch (data.getDataType().toUpperCase())
            {
                case "EXCEL": sheetData = data.ReadExcel(sDefaultPath+Location,sheetName);
                    iRowData = data.rowCount(sheetData, record, resultset, sDataType)-1;
                    break;
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

       protected void initialiseFunctions() {
        mm = new SauceDemo (data, utils, repo);

    }


    public void Collapse() throws Exception
    {
        if (utils.getWebdriver()!= null)
            utils.getWebdriver().quit();
    }

    public void InitializeBrowser(int i) throws Exception
    {
        utils.setWebDriver(utils.initializeWedriver(data.getCellData("Browser",i,sheet,null,null,sDataType), null,  null));
        System.out.println();
    }

}
