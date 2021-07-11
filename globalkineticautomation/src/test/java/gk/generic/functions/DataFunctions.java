package gk.generic.functions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.Writer;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class DataFunctions {
    private String sWebURL;
    private String sPassword;
    private String sUsername;
    private String sbrowserDrivers;
    private Connection connect;
    private java.sql.Connection conn = null;
    private Sheet sheet;
    private Workbook workbook;

    private String sDataType;
    private String sReportName;
    private String sAppendReport;


    int col,Column_Count,Row_Count;
    int colnNum=0;
    int fillonum = 1;

    ArrayList<String> lines = new ArrayList<String>();

    public String getWebURL() { return sWebURL; }

    public String getWebPassword() { return sPassword; }
    public String getWebUserName() { return sUsername; }

    public String getDataType()
    {
        return sDataType;
    }
    public String getReportName() { return sReportName; }
    public String getAppendReport() { return sAppendReport; }

    /*****************************************************************************
     Function Name: 	GetEnvironmentVariables
     Description:	gets environment variables from the config json file

     * @param
     ******************************************************************************/
    public void GetEnvironmentVariables() throws IOException, ParseException
    {
        File f1=null;
        FileReader fr=null;

        JSONParser parser = new JSONParser();
        try {
            f1 = new File("conf/Environment.json");
            fr = new FileReader(f1);
            Object obj = parser.parse(fr);
            JSONObject jsonObject = (JSONObject) obj;
            //System.out.print(jsonObject);
            //String[] env=new String[10];

            sWebURL = (String) jsonObject.get("weburl");
            sUsername = (String) jsonObject.get("webusername");
            sPassword = (String) jsonObject.get("webpassword");
            sDataType = (String) jsonObject.get("datatype");
            sReportName = (String) jsonObject.get("reportname");
            sAppendReport = (String) jsonObject.get("appendreport");

        } finally {
            try{
                fr.close();

            }catch(IOException ioe)

            {
                ioe.printStackTrace();
            }
        }
    }

    //Read and Write data from/To a TextFile
    public String ReadTextFile(String filePath)throws IOException
    {
        BufferedReader input = null;
        FileReader file = new FileReader(filePath);
        input = new BufferedReader(file);
        String value = input.readLine();
        input.close();
        return value;
    }

    public void WriteTextFile(String filePath, String outputData)throws IOException
    {
        Writer output = null;
        File file = new File(filePath);
        output = new BufferedWriter(new FileWriter(file));

        output.write(outputData);

        output.close();
    }

    public Sheet ReadExcel(String FILE_NAME, String strSheetName) throws IOException
    {
        FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
        workbook = new XSSFWorkbook(excelFile);

        sheet = workbook.getSheet(strSheetName);
        return sheet;
    }

    public String getCellData(String strColumn, int iRow, Sheet sheet, Recordset record,ResultSet resultset, String Type ) throws Exception{
        String sValue = null;
        switch (Type.toUpperCase())
        {

            case "EXCEL":
                Row row = sheet.getRow(0);
                for (int i =0;i<columnCount(sheet);i++)
                {
                    if (row.getCell(i).getStringCellValue().trim().equals(strColumn))
                    {
                        Row raw = sheet.getRow(iRow);
                        Cell cell = raw.getCell(i);
                        DataFormatter formatter = new DataFormatter();
                        sValue = formatter.formatCellValue(cell);
                        break;
                    }
                }
                break;


        }
        return sValue;
    }


    public int rowCount(Sheet sheet, Recordset record, ResultSet resultset, String Type) throws Exception{
        int count = 0;
        switch (Type.toUpperCase())
        {
            case "EXCEL":
                count = sheet.getPhysicalNumberOfRows();
                break;
            case "FILLO":

                count = record.getCount();
                break;
            case "SQLSERVER":
                int i = 0;
                while (resultset.next())
                {
                    i++;
                }
                count = i;
        }
        return count;
    }
    public int columnCount(Sheet sheet) throws Exception{
        return sheet.getRow(0).getLastCellNum();
    }

    public void writeData(String[] sColumn,int Row,String[] sData,String filepath,  String Type, String sQuery) throws IOException, InvalidFormatException, FilloException, SQLException
    {
        switch (Type.toUpperCase())
        {
            case "EXCEL":
                int CoulmnNo = 0 ;
                FileInputStream file = new FileInputStream(filepath);
                Workbook wb = WorkbookFactory.create(file);
                sheet = wb.getSheetAt(0);

                Cell cell = null;

                Row row = sheet.getRow(0);

                for (int c = 0;c<sColumn.length; c++)
                {
                    for( int i=0; i<row.getLastCellNum();i++)
                    {
                        if(row.getCell(i).getStringCellValue().trim().equals(sColumn[c]))
                        {
                            CoulmnNo=i;
                            Row raw = sheet.getRow(Row);
                            cell = raw.createCell(CoulmnNo);
                            cell.setCellValue(sData[c]);

                            cell.setCellValue(sData[c]);
                            break;
                        }
                    }
                }
                FileOutputStream fileOut = new FileOutputStream(filepath);
                wb.write(fileOut);
                fileOut.close();

                break;
                   default:
                throw new IllegalStateException("Unexpected value: " + Type.toUpperCase());
        }
    }

    public String updateData(String[] ColumnValue, String[] SearchValue,String xmlPath) throws IOException
    {
        String sBody = null;
        FileInputStream inputStream = new FileInputStream(xmlPath);
        try {

            sBody = IOUtils.toString(inputStream, "UFT-8");
            for(int i=0; i<SearchValue.length; i++)
            {
                if (sBody.contains(SearchValue[i]))
                {
                    sBody = sBody.replace(SearchValue[i], ColumnValue[i]);
                }
            }
        } finally {
            inputStream.close();
        }
        return sBody;
    }

    public String ReadData(String xmlPath) throws IOException
    {
        String sBody = null;
        FileInputStream inputStream = new FileInputStream(xmlPath);
        try {

            sBody = IOUtils.toString(inputStream, "UFT-8");
        } finally {
            inputStream.close();
        }
        return sBody;
    }

    public Recordset ConnectFillo(String path, String Query) throws FilloException
    {
        Fillo fillo=new Fillo();

        Recordset record;

        connect=fillo.getConnection(path);
        record=connect.executeQuery(Query);

		       return record;
    }

    public ResultSet ConnectAndQuerySQLServer(String sDBURL, String sUserName,String  sPassword,  String sQuery)
    {
        ResultSet rs = null;

        try
        {
            String dbURL = sDBURL;
            String user = sUserName;
            String pass = sPassword;
            conn = DriverManager.getConnection(dbURL, user, pass);
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sQuery);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rs;
    }

    public String xmlParser(String outputData, String tagName, String fieldName) throws SAXException, IOException, ParserConfigurationException {

        String element = null;
        DocumentBuilderFactory dbFactory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder =
                dbFactory.newDocumentBuilder();
        InputSource is = new InputSource(new
                StringReader(outputData));
        org.w3c.dom.Document doc = dBuilder.parse(is);

        doc.getDocumentElement().normalize();
        System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
        NodeList nList = doc.getElementsByTagName(tagName);
        System.out.println("----------------------------");

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            System.out.println("\nCurrent Element :" + nNode.getNodeName());
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                element = eElement.getElementsByTagName(fieldName).item(0).getTextContent();
            } // end if
        } // end for loop
        return element;
    } // end function

    public long generateRandom()
    {
        return (long)(Math.random()*100000 + 3333300000L);
    }

    public String ReadDataJson(String xmlPath) throws IOException, ParseException
    {
        File f1=null;
        FileReader fr=null;

        JSONParser parser = new JSONParser();
        try {
            f1 = new File(xmlPath);
            fr = new FileReader(f1);
            Object obj = parser.parse(fr);
            JSONObject jsonObject = (JSONObject) obj;

            String sBody = jsonObject.toString();
            return sBody;
        } finally {
            try{
                lines.clear();
                fr.close();

                //out.close();


            }catch(IOException ioe)

            {
                ioe.printStackTrace();
            }
        }

    }


    public String validateData(String[] data,String outputData, int icount, String sPath, String format, String sRunStatus, Reporting repo) throws Exception {
        String sOutput = null;
        //String[] sData3 = null;
        if (format.equals("JSON")) {

            for(int v = 0; v < data.length; v++) {
                if (data[v].contains("NotNull")) {
                    String [] dataHold =data[v].split("_");
                    String [] hold =outputData.split(",");
                    for (int l = 0; l < hold.length;l++) {
                        if(hold[l].contains(dataHold[0])) {
                            String[]dataCheckNotNulll = hold[l].split(":");
                            if(dataCheckNotNulll[1].trim().isEmpty()||dataCheckNotNulll[1].trim().equals(null) ) {
                                if (sOutput== null) {

                                    sOutput =  data[v];
                                }else{
                                    sOutput = sOutput + "," + data[v];
                                } // end if
                                //else
                            } // end if


                        } // end if
                    } // end for loop

                }

                else if(outputData.contains(data[v]) ==false) {
//								ValidationResults
                    if (sOutput == null) {
                        sOutput =  data[v];
                    }else
                    {
                        sOutput = sOutput + "," +data[v];
                    }



                }

            }  // end for loop
        } else {

            for (int n = 1; n < data.length;n++) {
                String [] dataValue =data[n].split("_");

                String sData =xmlParser(outputData, data[0], dataValue[0]);

                if(sData.equals(dataValue[1]) == false) {
                    if (sOutput == null){
                        sOutput =dataValue[0];
                    }else
                    {
                        sOutput =sOutput + "," + dataValue[0];
                    } // end if else
                }
            }
        }
        String[]sColumn={"ValidationStatus","ValidationResults"};

        if(sOutput ==null) {


            sRunStatus = sRunStatus+ " Passed";
            String[]sData3={"Passed","Validation Successful"};

            writeData(sColumn, icount, sData3, sPath,"Excel",null);
        }
        else
        {
            sRunStatus = sRunStatus+ " Failed";

            String[]sData3={"Failed", "Validation failed for "+ sOutput};
            writeData(sColumn, icount, sData3, sPath, "Excel", null);

        }
        return sRunStatus;

    }
}
