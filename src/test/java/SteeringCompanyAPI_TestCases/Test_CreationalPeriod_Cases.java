/*
 * Copyright (c) 2023.
 */

package SteeringCompanyAPI_TestCases;

import SteeringCompanyAPIs.CreationalPeriods_API;
import SteeringCompanyAPIs.Token_API;
import Utils.ExtraExcelFun;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@SuppressWarnings("ALL")
public class Test_CreationalPeriod_Cases {
    ExtraExcelFun testDataReader2;

    String UserName,Password;
    @BeforeClass
    ///////// Read Data for Token API ///////////////////////////////
    public void Setup_data() {
        // testDataReader = new ExcelFileManager("SteeringCompanyAPI_TestData/SteeringCompanyAPI_TestData.xlsx");
        testDataReader2 = new ExtraExcelFun("SteeringCompanyAPI_TestData/SteeringCompanyAPI_TestData.xlsx");
        UserName=testDataReader2.getCellData("TokenAPI_TestData","UserName","Data1");
        Password=testDataReader2.getCellData("TokenAPI_TestData","Password","Data1");
    }
    //////////////////Test Cases for Get All Active CreationalPeriod API ////////////////////////////
    @Test(description = "TC001  -CreationalPeriod-  Perform Get all CreationalPeriod API with valid user name and password")
    @Story("Retrieving All Active CreationalPeriod")
    @Severity(SeverityLevel.CRITICAL)
    public void Get_Valid_all_CreationalPeriod_Rq_TC() {
        Token_API Token_TC=new Token_API();
        Token_TC.POST_Valid_TOKEN_Rq(UserName,Password);
        Token_TC.Check_Token_Valid_status_Code_Response();
        String Token =Token_TC.Get_Valid_Access_Token();

        CreationalPeriods_API GetAllCreationalPeriod_TC=new CreationalPeriods_API();
        GetAllCreationalPeriod_TC.Get_Valid_all_CreationalPeriods_Rq(Token);
        GetAllCreationalPeriod_TC.Check_Valid_CreationalPeriods_status_Code_Response();
        GetAllCreationalPeriod_TC.Check_CreationalPeriods_Response_Time();
        GetAllCreationalPeriod_TC.Check_all_CreationalPeriods_Valid_Content();
        GetAllCreationalPeriod_TC.Check_all_CreationalPeriods_Response_Valid_Schema();
    }
    //////////////////Test Cases for Get All Active CreationalPeriod API with Pagination ////////////////
    @DataProvider(name = "Valid_Get_CreationalPeriod_Pagination")
    public Object[][] Get_Valid_CreationalPeriod_Pagination(){
        int dataRowsNumber = testDataReader2.CountRowsHasSpecificText("CreationalPeriod_TestData","Get_Valid_Creational_Pagenation_");
        Object[][] data =new Object[dataRowsNumber][ 5];
        for (int i=0;i<dataRowsNumber;i++)
        {
            data[i][0]= testDataReader2.getCellData("CreationalPeriod_TestData","Get_Valid_Creational_Pagenation_"+(i+1),"TC_Type");
            data[i][1]= testDataReader2.getCellData("CreationalPeriod_TestData","Get_Valid_Creational_Pagenation_"+(i+1),"APIName");
            data[i][2]= testDataReader2.getCellData("CreationalPeriod_TestData","Get_Valid_Creational_Pagenation_"+(i+1),"PageSize");
            data[i][3]= testDataReader2.getCellData("CreationalPeriod_TestData","Get_Valid_Creational_Pagenation_"+(i+1),"PageNumber");
            data[i][4]= testDataReader2.getCellData("CreationalPeriod_TestData","Get_Valid_Creational_Pagenation_"+(i+1),"ExpectedResult");

        }
        return data;
    }
    @Test(description = "TC002  -CreationalPeriod-   Perform Get all CreationalPeriod API with pagination with valid user name and password"
            ,dataProvider = "Valid_Get_CreationalPeriod_Pagination")
    @Story("Retrieving All Active CreationalPeriod with Pagination")
    @Severity(SeverityLevel.CRITICAL)
    public void Get_Valid_all_CreationalPeriod_by_Qry_Rq_TC(Object[] data) {
        Token_API Token_TC=new Token_API();
        Token_TC.POST_Valid_TOKEN_Rq(UserName,Password);
        Token_TC.Check_Token_Valid_status_Code_Response();
        String Token =Token_TC.Get_Valid_Access_Token();

        CreationalPeriods_API GetAllCreationalPeriod_TC=new CreationalPeriods_API();
        GetAllCreationalPeriod_TC.Get_Valid_all_CreationalPeriods_by_parameter_Query_Rq(Token,data[2].toString(),data[3].toString());
        GetAllCreationalPeriod_TC.Check_Valid_CreationalPeriods_status_Code_Response();
        GetAllCreationalPeriod_TC.Check_CreationalPeriods_Response_Time();
        GetAllCreationalPeriod_TC.Check_all_CreationalPeriods_Valid_Content();
        GetAllCreationalPeriod_TC.Check_all_CreationalPeriods_Response_Valid_Schema();
    }
    ///////////////Test cases for Get Creational Period for Valid ID///////////////////////////////
    @DataProvider(name = "Get_Valid_CreationalPeriod_ByID")
    public Object[][] Get_Valid_CreationalPeriod_ByID(){
        int dataRowsNumber = testDataReader2.CountRowsHasSpecificText("CreationalPeriod_TestData","Get_Valid_Creational_ByID_");
        Object[][] data =new Object[dataRowsNumber][ 4];
        for (int i=0;i<dataRowsNumber;i++)
        {
            data[i][0]= testDataReader2.getCellData("CreationalPeriod_TestData","Get_Valid_Creational_ByID_"+(i+1),"TC_Type");
            data[i][1]= testDataReader2.getCellData("CreationalPeriod_TestData","Get_Valid_Creational_ByID_"+(i+1),"APIName");
            data[i][2]= testDataReader2.getCellData("CreationalPeriod_TestData","Get_Valid_Creational_ByID_"+(i+1),"CreationalPeriodID");
            data[i][3]= testDataReader2.getCellData("CreationalPeriod_TestData","Get_Valid_Creational_ByID_"+(i+1),"ExpectedResult");

        }
        return data;
    }
    @Test(description = "TC003  -CreationalPeriod-   Perform Get CreationalPeriod API by ID"
            ,dataProvider = "Get_Valid_CreationalPeriod_ByID")
    @Story("Retrieving CreationalPeriod by ID")
    @Severity(SeverityLevel.CRITICAL)
    public void Get_Valid_CreationalPeriod_ByID_Rq_TC(Object[] data) {
        Token_API Token_TC=new Token_API();
        Token_TC.POST_Valid_TOKEN_Rq(UserName,Password);
        Token_TC.Check_Token_Valid_status_Code_Response();
        String Token =Token_TC.Get_Valid_Access_Token();

        CreationalPeriods_API GetAllCreationalPeriod_TC=new CreationalPeriods_API();
        GetAllCreationalPeriod_TC.Get_Valid_CreationalPeriods_by_id_Rq(Token,data[2].toString());
        GetAllCreationalPeriod_TC.Check_Valid_CreationalPeriods_status_Code_Response();
        GetAllCreationalPeriod_TC.Check_CreationalPeriods_Response_Time();
        GetAllCreationalPeriod_TC.Check_CreationalPeriods_Content(data[3].toString());
        GetAllCreationalPeriod_TC.Check_CreationalPeriods_by_id_Response_Valid_Schema();
    }
    ///////////////Test cases for Get Creational Period for NotFound ID///////////////////////////////
    @DataProvider(name = "Get_CreationalPeriod_With_NotFound_ByID")
    public Object[][] Get_CreationalPeriod_With_NotFound_ByID(){
        int dataRowsNumber = testDataReader2.CountRowsHasSpecificText("CreationalPeriod_TestData","Get_NotFound_Creational_ByID_");
        Object[][] data =new Object[dataRowsNumber][ 4];
        for (int i=0;i<dataRowsNumber;i++)
        {
            data[i][0]= testDataReader2.getCellData("CreationalPeriod_TestData","Get_NotFound_Creational_ByID_"+(i+1),"TC_Type");
            data[i][1]= testDataReader2.getCellData("CreationalPeriod_TestData","Get_NotFound_Creational_ByID_"+(i+1),"APIName");
            data[i][2]= testDataReader2.getCellData("CreationalPeriod_TestData","Get_NotFound_Creational_ByID_"+(i+1),"CreationalPeriodID");
            data[i][3]= testDataReader2.getCellData("CreationalPeriod_TestData","Get_NotFound_Creational_ByID_"+(i+1),"ExpectedResult");

        }
        return data;
    }
    @Test(description = "TC004  -CreationalPeriod-   Perform Get CreationalPeriod API With NotFound by ID"
            ,dataProvider = "Get_CreationalPeriod_With_NotFound_ByID")
    @Story("Retrieving CreationalPeriod by ID With Not Found Response")
    @Severity(SeverityLevel.CRITICAL)
    public void Get_CreationalPeriod_With_NotFound_ByID_Rq_TC(Object[] data) {
        Token_API Token_TC=new Token_API();
        Token_TC.POST_Valid_TOKEN_Rq(UserName,Password);
        Token_TC.Check_Token_Valid_status_Code_Response();
        String Token =Token_TC.Get_Valid_Access_Token();

        CreationalPeriods_API GetAllCreationalPeriod_TC=new CreationalPeriods_API();
        GetAllCreationalPeriod_TC.Get_CreationalPeriods_With_NotFound_by_id_Rq(Token,data[2].toString());
        GetAllCreationalPeriod_TC.Check_Validation_NotFound_CreationalPeriods_status_Code_Response();
        GetAllCreationalPeriod_TC.Check_CreationalPeriods_Response_Time();
        GetAllCreationalPeriod_TC.Check_CreationalPeriods_Content(data[3].toString());
        GetAllCreationalPeriod_TC.Check_CreationalPeriods_Response_NotFound_Error_Schema();
    }
    ///////////////Test cases for Get Creational Period for BadRequest ID///////////////////////////////
    @DataProvider(name = "Get_CreationalPeriod_With_BadRequest_ByID")
    public Object[][] Get_CreationalPeriod_With_BadRequest_ByID(){
        int dataRowsNumber = testDataReader2.CountRowsHasSpecificText("CreationalPeriod_TestData","Get_BadRequest_Creational_ByID_");
        Object[][] data =new Object[dataRowsNumber][ 4];
        for (int i=0;i<dataRowsNumber;i++)
        {
            data[i][0]= testDataReader2.getCellData("CreationalPeriod_TestData","Get_BadRequest_Creational_ByID_"+(i+1),"TC_Type");
            data[i][1]= testDataReader2.getCellData("CreationalPeriod_TestData","Get_BadRequest_Creational_ByID_"+(i+1),"APIName");
            data[i][2]= testDataReader2.getCellData("CreationalPeriod_TestData","Get_BadRequest_Creational_ByID_"+(i+1),"CreationalPeriodID");
            data[i][3]= testDataReader2.getCellData("CreationalPeriod_TestData","Get_BadRequest_Creational_ByID_"+(i+1),"ExpectedResult");

        }
        return data;
    }
    @Test(description = "TC005  -CreationalPeriod-   Perform Get CreationalPeriod API With BadRequest by ID"
            ,dataProvider = "Get_CreationalPeriod_With_BadRequest_ByID")
    @Story("Retrieving CreationalPeriod by ID With BadRequest Response")
    @Severity(SeverityLevel.CRITICAL)
    public void Get_CreationalPeriod_With_BadRequest_ByID_Rq_TC(Object[] data) {
        Token_API Token_TC=new Token_API();
        Token_TC.POST_Valid_TOKEN_Rq(UserName,Password);
        Token_TC.Check_Token_Valid_status_Code_Response();
        String Token =Token_TC.Get_Valid_Access_Token();

        CreationalPeriods_API GetAllCreationalPeriod_TC=new CreationalPeriods_API();
        GetAllCreationalPeriod_TC.Get_CreationalPeriods_With_BadRequest_by_id_Rq(Token,data[2].toString());
        GetAllCreationalPeriod_TC.Check_Validation_BadRequest_CreationalPeriods_status_Code_Response();
        GetAllCreationalPeriod_TC.Check_CreationalPeriods_Response_Time();
        GetAllCreationalPeriod_TC.Check_CreationalPeriods_Content(data[3].toString());
        GetAllCreationalPeriod_TC.Check_CreationalPeriods_Response_BadRequest_Error_Schema();
    }
    ///////////////Test cases for Get Invalid Creational Period Pagenation//////////////////////////////
    @DataProvider(name = "Get_InValid_Creational_Pagenation")
    public Object[][] Get_InValid_Creational_Pagenation(){
        int dataRowsNumber = testDataReader2.CountRowsHasSpecificText("CreationalPeriod_TestData","Get_InValid_Creational_Pagenation_");
        Object[][] data =new Object[dataRowsNumber][ 5];
        for (int i=0;i<dataRowsNumber;i++)
        {
            data[i][0]= testDataReader2.getCellData("CreationalPeriod_TestData","Get_InValid_Creational_Pagenation_"+(i+1),"TC_Type");
            data[i][1]= testDataReader2.getCellData("CreationalPeriod_TestData","Get_InValid_Creational_Pagenation_"+(i+1),"APIName");
            data[i][2]= testDataReader2.getCellData("CreationalPeriod_TestData","Get_InValid_Creational_Pagenation_"+(i+1),"PageSize");
            data[i][3]= testDataReader2.getCellData("CreationalPeriod_TestData","Get_InValid_Creational_Pagenation_"+(i+1),"PageNumber");
            data[i][4]= testDataReader2.getCellData("CreationalPeriod_TestData","Get_InValid_Creational_Pagenation_"+(i+1),"ExpectedResult");

        }
        return data;
    }
    @Test(description = "TC006  -CreationalPeriod-   Perform Get all CreationalPeriod API With invalid Pagenation"
            ,dataProvider = "Get_InValid_Creational_Pagenation")
    @Story("Retrieving all CreationalPeriod  With InValid Pagenation")
    @Severity(SeverityLevel.CRITICAL)
    public void Get_InValid_Creational_Pagenation_Rq_TC(Object[] data) {
        Token_API Token_TC=new Token_API();
        Token_TC.POST_Valid_TOKEN_Rq(UserName,Password);
        Token_TC.Check_Token_Valid_status_Code_Response();
        String Token =Token_TC.Get_Valid_Access_Token();

        CreationalPeriods_API GetAllCreationalPeriod_TC=new CreationalPeriods_API();
        GetAllCreationalPeriod_TC.Get_InValid_all_CreationalPeriods_by_parameter_Query_Rq(Token,data[2].toString(),data[3].toString());
        GetAllCreationalPeriod_TC.Check_Validation_Error_CreationalPeriods_status_Code_Response();
        GetAllCreationalPeriod_TC.Check_CreationalPeriods_Response_Time();
        GetAllCreationalPeriod_TC.Check_CreationalPeriods_Content(data[4].toString());
        GetAllCreationalPeriod_TC.Check_CreationalPeriods_Response_Validation_Error_Schema();
    }
    ///////////////Test cases for missing /invalid/expired Token/////////////////////////////////////////
    @Test(description = "TC007  -CreationalPeriod-   Perform Get all CreationalPeriod API with missing Token")
    @Story("Retrieving All Active CreationalPeriod with missing Token")
    @Severity(SeverityLevel.MINOR)
    public void Get_Invalid_all_CreationalPeriod_with_missing_Token_TC() {
        Token_API Token_TC=new Token_API();
        Token_TC.POST_Valid_TOKEN_Rq(UserName,Password);
        Token_TC.Check_Token_Valid_status_Code_Response();

        CreationalPeriods_API GetAllCreationalPeriod_TC=new CreationalPeriods_API();
        GetAllCreationalPeriod_TC.Get_all_CreationalPeriods_With_Missing_Token_Rq();
        GetAllCreationalPeriod_TC.Check_Unauthorized_CreationalPeriods_status_Code_Response();
        GetAllCreationalPeriod_TC.Check_CreationalPeriods_Response_Time();
        GetAllCreationalPeriod_TC.Check_all_CreationalPeriods_Response_Unauthorized_Schema();
    }
    @Test(description = "TC008  -CreationalPeriod-   Perform Get CreationalPeriod by ID with missing Token"
            ,dataProvider = "Get_Valid_CreationalPeriod_ByID")
    @Story("Retrieving CreationalPeriod By ID with missing Token")
    @Severity(SeverityLevel.MINOR)
    public void Get_Invalid_CreationalPeriod_ByID_with_missing_Token_TC(Object[] data) {
        Token_API Token_TC=new Token_API();
        Token_TC.POST_Valid_TOKEN_Rq(UserName,Password);
        Token_TC.Check_Token_Valid_status_Code_Response();

        CreationalPeriods_API GetAllCreationalPeriod_TC=new CreationalPeriods_API();
        GetAllCreationalPeriod_TC.Get_CreationalPeriods_by_id_With_Missing_Token_Rq(data[2].toString());
        GetAllCreationalPeriod_TC.Check_Unauthorized_CreationalPeriods_status_Code_Response();
        GetAllCreationalPeriod_TC.Check_CreationalPeriods_Response_Time();
        GetAllCreationalPeriod_TC.Check_all_CreationalPeriods_Response_Unauthorized_Schema();
    }
    @Test(description = "TC009 -CreationalPeriod-  Perform Get all CreationalPeriod API with invalid or expired Token")
    @Story("Retrieving All Active CreationalPeriod with invalid/expired Token")
    @Severity(SeverityLevel.MINOR)
    public void Get_Invalid_all_CreationalPeriod_with_invalid_Token_TC() {
        Token_API Token_TC=new Token_API();
        Token_TC.POST_Valid_TOKEN_Rq(UserName,Password);
        Token_TC.Check_Token_Valid_status_Code_Response();
        CreationalPeriods_API GetAllCreationalPeriod_TC=new CreationalPeriods_API();
        GetAllCreationalPeriod_TC.Get_all_CreationalPeriods_With_InValid_Token_Rq("123");
        GetAllCreationalPeriod_TC.Check_Unauthorized_CreationalPeriods_status_Code_Response();
        GetAllCreationalPeriod_TC.Check_CreationalPeriods_Response_Time();
        //GetAllCreationalPeriod_TC.Check_All_CreationalPeriod_Response_Unauthorized_Schema(); // There is no response content
    }
    @Test(description = "TC010 -CreationalPeriod-  Perform Get CreationalPeriod API by ID with invalid or expired Token"
            ,dataProvider = "Get_Valid_CreationalPeriod_ByID")
    @Story("Retrieving CreationalPeriod by ID with invalid/expired Token")
    @Severity(SeverityLevel.MINOR)
    public void Get_Invalid_CreationalPeriod_ByID_with_invalid_Token_TC(Object[] data) {
        Token_API Token_TC=new Token_API();
        Token_TC.POST_Valid_TOKEN_Rq(UserName,Password);
        Token_TC.Check_Token_Valid_status_Code_Response();
        CreationalPeriods_API GetAllCreationalPeriod_TC=new CreationalPeriods_API();
        GetAllCreationalPeriod_TC.Get_CreationalPeriods_by_id_With_InValid_Token_Rq("123",data[2].toString());
        GetAllCreationalPeriod_TC.Check_Unauthorized_CreationalPeriods_status_Code_Response();
        GetAllCreationalPeriod_TC.Check_CreationalPeriods_Response_Time();
        //GetAllCreationalPeriod_TC.Check_All_CreationalPeriod_Response_Unauthorized_Schema(); // There is no response content
    }
}
