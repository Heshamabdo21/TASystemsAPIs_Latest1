/*
 * Copyright (c) 2023.
 */

package SteeringCompanyAPI_TestCases;

import SteeringCompanyAPIs.PeriodProgramTemplates_API;
import SteeringCompanyAPIs.Token_API;
import Utils.ExtraExcelFun;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@SuppressWarnings("ALL")
public class Test_PeriodProgramTemplate_Cases {
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
    //////////////////Test Cases for Get All Active PeriodProgramTemplate API ////////////////////////////
    @Test(description = "TC001  -PeriodProgramTemplate-  Perform Get all PeriodProgramTemplate API with valid user name and password")
    @Story("Retrieving All Active PeriodProgramTemplate")
    @Severity(SeverityLevel.CRITICAL)
    public void Get_Valid_all_PeriodProgramTemplate_Rq_TC() {
        Token_API Token_TC=new Token_API();
        Token_TC.POST_Valid_TOKEN_Rq(UserName,Password);
        Token_TC.Check_Token_Valid_status_Code_Response();
        String Token =Token_TC.Get_Valid_Access_Token();

        PeriodProgramTemplates_API GetAllPeriodProgramTemplate_TC=new PeriodProgramTemplates_API();
        GetAllPeriodProgramTemplate_TC.Get_Valid_all_PeriodProgramTemplates_Rq(Token);
        GetAllPeriodProgramTemplate_TC.Check_Valid_PeriodProgramTemplates_status_Code_Response();
        GetAllPeriodProgramTemplate_TC.Check_PeriodProgramTemplates_Response_Time();
        GetAllPeriodProgramTemplate_TC.Check_all_PeriodProgramTemplates_Valid_Content();
        GetAllPeriodProgramTemplate_TC.Check_all_PeriodProgramTemplates_Response_Valid_Schema();
    }
    //////////////////Test Cases for Get All Active PeriodProgramTemplate API with Pagination ////////////////
    @DataProvider(name = "Valid_Get_PeriodProgramTemplate_Pagination")
    public Object[][] Get_Valid_PeriodProgramTemplate_Pagination(){
        int dataRowsNumber = testDataReader2.CountRowsHasSpecificText("PeriodProgramTemplate_TestData","Get_Valid_PeriodProgramTemplate_Pagenation_");
        Object[][] data =new Object[dataRowsNumber][ 5];
        for (int i=0;i<dataRowsNumber;i++)
        {
            data[i][0]= testDataReader2.getCellData("PeriodProgramTemplate_TestData","Get_Valid_PeriodProgramTemplate_Pagenation_"+(i+1),"TC_Type");
            data[i][1]= testDataReader2.getCellData("PeriodProgramTemplate_TestData","Get_Valid_PeriodProgramTemplate_Pagenation_"+(i+1),"APIName");
            data[i][2]= testDataReader2.getCellData("PeriodProgramTemplate_TestData","Get_Valid_PeriodProgramTemplate_Pagenation_"+(i+1),"PageSize");
            data[i][3]= testDataReader2.getCellData("PeriodProgramTemplate_TestData","Get_Valid_PeriodProgramTemplate_Pagenation_"+(i+1),"PageNumber");
            data[i][4]= testDataReader2.getCellData("PeriodProgramTemplate_TestData","Get_Valid_PeriodProgramTemplate_Pagenation_"+(i+1),"ExpectedResult");

        }
        return data;
    }
    @Test(description = "TC002  -PeriodProgramTemplate-   Perform Get all PeriodProgramTemplate API with pagination with valid user name and password"
            ,dataProvider = "Valid_Get_PeriodProgramTemplate_Pagination")
    @Story("Retrieving All Active PeriodProgramTemplate with Pagination")
    @Severity(SeverityLevel.CRITICAL)
    public void Get_Valid_all_PeriodProgramTemplate_by_Qry_Rq_TC(Object[] data) {
        Token_API Token_TC=new Token_API();
        Token_TC.POST_Valid_TOKEN_Rq(UserName,Password);
        Token_TC.Check_Token_Valid_status_Code_Response();
        String Token =Token_TC.Get_Valid_Access_Token();

        PeriodProgramTemplates_API GetAllPeriodProgramTemplate_TC=new PeriodProgramTemplates_API();
        GetAllPeriodProgramTemplate_TC.Get_Valid_all_PeriodProgramTemplates_by_parameter_Query_Rq(Token,data[2].toString(),data[3].toString());
        GetAllPeriodProgramTemplate_TC.Check_Valid_PeriodProgramTemplates_status_Code_Response();
        GetAllPeriodProgramTemplate_TC.Check_PeriodProgramTemplates_Response_Time();
        GetAllPeriodProgramTemplate_TC.Check_all_PeriodProgramTemplates_Valid_Content();
        GetAllPeriodProgramTemplate_TC.Check_all_PeriodProgramTemplates_Response_Valid_Schema();
    }
   /////////////////Test Cases for Valid  Get Period Program Template by ID///////////////////////////////////
    @DataProvider(name = "Get_Valid_PeriodProgramTemplate_ByID")
    public Object[][] Get_Valid_PeriodProgramTemplate_ByID(){
        int dataRowsNumber = testDataReader2.CountRowsHasSpecificText("PeriodProgramTemplate_TestData","Get_Valid_PeriodProgramTemplate_ByID_");
        Object[][] data =new Object[dataRowsNumber][ 4];
        for (int i=0;i<dataRowsNumber;i++)
        {
            data[i][0]= testDataReader2.getCellData("PeriodProgramTemplate_TestData","Get_Valid_PeriodProgramTemplate_ByID_"+(i+1),"TC_Type");
            data[i][1]= testDataReader2.getCellData("PeriodProgramTemplate_TestData","Get_Valid_PeriodProgramTemplate_ByID_"+(i+1),"APIName");
            data[i][2]= testDataReader2.getCellData("PeriodProgramTemplate_TestData","Get_Valid_PeriodProgramTemplate_ByID_"+(i+1),"PeriodProgramTemplateID");
            data[i][3]= testDataReader2.getCellData("PeriodProgramTemplate_TestData","Get_Valid_PeriodProgramTemplate_ByID_"+(i+1),"ExpectedResult");

        }
        return data;
    }
    @Test(description = "TC003  -PeriodProgramTemplate-   Perform Get PeriodProgramTemplate API by ID"
            ,dataProvider = "Get_Valid_PeriodProgramTemplate_ByID")
    @Story("Retrieving PeriodProgramTemplate by ID")
    @Severity(SeverityLevel.CRITICAL)
    public void Get_Valid_PeriodProgramTemplate_ByID_Rq_TC(Object[] data) {
        Token_API Token_TC=new Token_API();
        Token_TC.POST_Valid_TOKEN_Rq(UserName,Password);
        Token_TC.Check_Token_Valid_status_Code_Response();
        String Token =Token_TC.Get_Valid_Access_Token();

        PeriodProgramTemplates_API GetAllPeriodProgramTemplate_TC=new PeriodProgramTemplates_API();
        GetAllPeriodProgramTemplate_TC.Get_Valid_PeriodProgramTemplates_by_id_Rq(Token,data[2].toString());
        GetAllPeriodProgramTemplate_TC.Check_Valid_PeriodProgramTemplates_status_Code_Response();
        GetAllPeriodProgramTemplate_TC.Check_PeriodProgramTemplates_Response_Time();
        GetAllPeriodProgramTemplate_TC.Check_PeriodProgramTemplates_Content(data[3].toString());
        GetAllPeriodProgramTemplate_TC.Check_PeriodProgramTemplates_by_id_Response_Valid_Schema();
    }
    ////////////////Test Cases for  Get Period Program Template for Not Found ID////////////////////////////////
    @DataProvider(name = "Get_PeriodProgramTemplate_With_NotFound_ByID")
    public Object[][] Get_PeriodProgramTemplate_With_NotFound_ByID(){
        int dataRowsNumber = testDataReader2.CountRowsHasSpecificText("PeriodProgramTemplate_TestData","Get_NotFound_PeriodProgramTemplate_ByID_");
        Object[][] data =new Object[dataRowsNumber][ 4];
        for (int i=0;i<dataRowsNumber;i++)
        {
            data[i][0]= testDataReader2.getCellData("PeriodProgramTemplate_TestData","Get_NotFound_PeriodProgramTemplate_ByID_"+(i+1),"TC_Type");
            data[i][1]= testDataReader2.getCellData("PeriodProgramTemplate_TestData","Get_NotFound_PeriodProgramTemplate_ByID_"+(i+1),"APIName");
            data[i][2]= testDataReader2.getCellData("PeriodProgramTemplate_TestData","Get_NotFound_PeriodProgramTemplate_ByID_"+(i+1),"PeriodProgramTemplateID");
            data[i][3]= testDataReader2.getCellData("PeriodProgramTemplate_TestData","Get_NotFound_PeriodProgramTemplate_ByID_"+(i+1),"ExpectedResult");

        }
        return data;
    }
    @Test(description = "TC004  -PeriodProgramTemplate-   Perform Get PeriodProgramTemplate API With NotFound by ID"
            ,dataProvider = "Get_PeriodProgramTemplate_With_NotFound_ByID")
    @Story("Retrieving PeriodProgramTemplate by ID With Not Found Response")
    @Severity(SeverityLevel.CRITICAL)
    public void Get_PeriodProgramTemplate_With_NotFound_ByID_Rq_TC(Object[] data) {
        Token_API Token_TC=new Token_API();
        Token_TC.POST_Valid_TOKEN_Rq(UserName,Password);
        Token_TC.Check_Token_Valid_status_Code_Response();
        String Token =Token_TC.Get_Valid_Access_Token();

        PeriodProgramTemplates_API GetAllPeriodProgramTemplate_TC=new PeriodProgramTemplates_API();
        GetAllPeriodProgramTemplate_TC.Get_PeriodProgramTemplates_With_NotFound_by_id_Rq(Token,data[2].toString());
        GetAllPeriodProgramTemplate_TC.Check_Validation_NotFound_PeriodProgramTemplates_status_Code_Response();
        GetAllPeriodProgramTemplate_TC.Check_PeriodProgramTemplates_Response_Time();
        GetAllPeriodProgramTemplate_TC.Check_PeriodProgramTemplates_Content(data[3].toString());
        GetAllPeriodProgramTemplate_TC.Check_PeriodProgramTemplates_Response_NotFound_Error_Schema();
    }
    /////////////////Test Cases for  Get Period Program Template for BadRequest ID/////////////////////////////////////////////////////////////////////
    @DataProvider(name = "Get_PeriodProgramTemplate_With_BadRequest_ByID")
    public Object[][] Get_PeriodProgramTemplate_With_BadRequest_ByID(){
        int dataRowsNumber = testDataReader2.CountRowsHasSpecificText("PeriodProgramTemplate_TestData","Get_BadRequest_PeriodProgramTemplate_ByID_");
        Object[][] data =new Object[dataRowsNumber][ 4];
        for (int i=0;i<dataRowsNumber;i++)
        {
            data[i][0]= testDataReader2.getCellData("PeriodProgramTemplate_TestData","Get_BadRequest_PeriodProgramTemplate_ByID_"+(i+1),"TC_Type");
            data[i][1]= testDataReader2.getCellData("PeriodProgramTemplate_TestData","Get_BadRequest_PeriodProgramTemplate_ByID_"+(i+1),"APIName");
            data[i][2]= testDataReader2.getCellData("PeriodProgramTemplate_TestData","Get_BadRequest_PeriodProgramTemplate_ByID_"+(i+1),"PeriodProgramTemplateID");
            data[i][3]= testDataReader2.getCellData("PeriodProgramTemplate_TestData","Get_BadRequest_PeriodProgramTemplate_ByID_"+(i+1),"ExpectedResult");

        }
        return data;
    }
    @Test(description = "TC005  -PeriodProgramTemplate-   Perform Get PeriodProgramTemplate API With BadRequest by ID"
            ,dataProvider = "Get_PeriodProgramTemplate_With_BadRequest_ByID")
    @Story("Retrieving PeriodProgramTemplate by ID With BadRequest Response")
    @Severity(SeverityLevel.CRITICAL)
    public void Get_PeriodProgramTemplate_With_BadRequest_ByID_Rq_TC(Object[] data) {
        Token_API Token_TC=new Token_API();
        Token_TC.POST_Valid_TOKEN_Rq(UserName,Password);
        Token_TC.Check_Token_Valid_status_Code_Response();
        String Token =Token_TC.Get_Valid_Access_Token();

        PeriodProgramTemplates_API GetAllPeriodProgramTemplate_TC=new PeriodProgramTemplates_API();
        GetAllPeriodProgramTemplate_TC.Get_PeriodProgramTemplates_With_BadRequest_by_id_Rq(Token,data[2].toString());
        GetAllPeriodProgramTemplate_TC.Check_Validation_BadRequest_PeriodProgramTemplates_status_Code_Response();
        GetAllPeriodProgramTemplate_TC.Check_PeriodProgramTemplates_Response_Time();
        GetAllPeriodProgramTemplate_TC.Check_PeriodProgramTemplates_Content(data[3].toString());
        GetAllPeriodProgramTemplate_TC.Check_PeriodProgramTemplates_Response_BadRequest_Error_Schema();
    }
    ///////////////Test cases for missing /invalid/expired Token/////////////////////////////////////////
    @Test(description = "TC006  -PeriodProgramTemplate-   Perform Get all PeriodProgramTemplate API with missing Token")
    @Story("Retrieving All Active PeriodProgramTemplate with missing Token")
    @Severity(SeverityLevel.MINOR)
    public void Get_Invalid_all_PeriodProgramTemplate_with_missing_Token_TC() {
        Token_API Token_TC=new Token_API();
        Token_TC.POST_Valid_TOKEN_Rq(UserName,Password);
        Token_TC.Check_Token_Valid_status_Code_Response();

        PeriodProgramTemplates_API GetAllPeriodProgramTemplate_TC=new PeriodProgramTemplates_API();
        GetAllPeriodProgramTemplate_TC.Get_all_PeriodProgramTemplates_With_Missing_Token_Rq();
        GetAllPeriodProgramTemplate_TC.Check_Unauthorized_PeriodProgramTemplates_status_Code_Response();
        GetAllPeriodProgramTemplate_TC.Check_PeriodProgramTemplates_Response_Time();
        GetAllPeriodProgramTemplate_TC.Check_all_PeriodProgramTemplates_Response_Unauthorized_Schema();
    }
    @Test(description = "TC007  -PeriodProgramTemplate-   Perform Get PeriodProgramTemplate by ID with missing Token"
            ,dataProvider = "Get_Valid_PeriodProgramTemplate_ByID")
    @Story("Retrieving PeriodProgramTemplate By ID with missing Token")
    @Severity(SeverityLevel.MINOR)
    public void Get_Invalid_PeriodProgramTemplate_ByID_with_missing_Token_TC(Object [] data) {
        Token_API Token_TC=new Token_API();
        Token_TC.POST_Valid_TOKEN_Rq(UserName,Password);
        Token_TC.Check_Token_Valid_status_Code_Response();

        PeriodProgramTemplates_API GetAllPeriodProgramTemplate_TC=new PeriodProgramTemplates_API();
        GetAllPeriodProgramTemplate_TC.Get_PeriodProgramTemplates_by_id_With_Missing_Token_Rq(data[2].toString());
        GetAllPeriodProgramTemplate_TC.Check_Unauthorized_PeriodProgramTemplates_status_Code_Response();
        GetAllPeriodProgramTemplate_TC.Check_PeriodProgramTemplates_Response_Time();
        GetAllPeriodProgramTemplate_TC.Check_all_PeriodProgramTemplates_Response_Unauthorized_Schema();
    }
    @Test(description = "TC008 -PeriodProgramTemplate-  Perform Get all PeriodProgramTemplate API with invalid or expired Token")
    @Story("Retrieving All Active PeriodProgramTemplate with invalid/expired Token")
    @Severity(SeverityLevel.MINOR)
    public void Get_Invalid_all_PeriodProgramTemplate_with_invalid_Token_TC() {
        Token_API Token_TC=new Token_API();
        Token_TC.POST_Valid_TOKEN_Rq(UserName,Password);
        Token_TC.Check_Token_Valid_status_Code_Response();
        PeriodProgramTemplates_API GetAllPeriodProgramTemplate_TC=new PeriodProgramTemplates_API();
        GetAllPeriodProgramTemplate_TC.Get_all_PeriodProgramTemplates_With_InValid_Token_Rq("123");
        GetAllPeriodProgramTemplate_TC.Check_Unauthorized_PeriodProgramTemplates_status_Code_Response();
        GetAllPeriodProgramTemplate_TC.Check_PeriodProgramTemplates_Response_Time();
        //GetAllPeriodProgramTemplate_TC.Check_All_PeriodProgramTemplate_Response_Unauthorized_Schema(); // There is no response content
    }
    @Test(description = "TC009 -PeriodProgramTemplate-  Perform PeriodProgramTemplate API by ID with invalid or expired Token"
            ,dataProvider = "Get_Valid_PeriodProgramTemplate_ByID")
    @Story("Retrieving PeriodProgramTemplate by ID with invalid/expired Token")
    @Severity(SeverityLevel.MINOR)
    public void Get_Invalid_PeriodProgramTemplate_ByID_with_invalid_Token_TC(Object [] data) {
        Token_API Token_TC=new Token_API();
        Token_TC.POST_Valid_TOKEN_Rq(UserName,Password);
        Token_TC.Check_Token_Valid_status_Code_Response();
        PeriodProgramTemplates_API GetAllPeriodProgramTemplate_TC=new PeriodProgramTemplates_API();
        GetAllPeriodProgramTemplate_TC.Get_PeriodProgramTemplates_by_id_With_InValid_Token_Rq("123",data[2].toString());
        GetAllPeriodProgramTemplate_TC.Check_Unauthorized_PeriodProgramTemplates_status_Code_Response();
        GetAllPeriodProgramTemplate_TC.Check_PeriodProgramTemplates_Response_Time();
        //GetAllPeriodProgramTemplate_TC.Check_All_PeriodProgramTemplate_Response_Unauthorized_Schema(); // There is no response content
    }
    ///////////////Test cases for InValid PeriodProgramTemplate Pagenation/////////////////////////////////////////
    @DataProvider(name = "Get_InValid_PeriodProgramTemplate_Pagenation")
    public Object[][] Get_InValid_PeriodProgramTemplate_Pagenation(){
        int dataRowsNumber = testDataReader2.CountRowsHasSpecificText("PeriodProgramTemplate_TestData","Get_InValid_PeriodProgramTemplate_Pagenation_");
        Object[][] data =new Object[dataRowsNumber][ 5];
        for (int i=0;i<dataRowsNumber;i++)
        {
            data[i][0]= testDataReader2.getCellData("PeriodProgramTemplate_TestData","Get_InValid_PeriodProgramTemplate_Pagenation_"+(i+1),"TC_Type");
            data[i][1]= testDataReader2.getCellData("PeriodProgramTemplate_TestData","Get_InValid_PeriodProgramTemplate_Pagenation_"+(i+1),"APIName");
            data[i][2]= testDataReader2.getCellData("PeriodProgramTemplate_TestData","Get_InValid_PeriodProgramTemplate_Pagenation_"+(i+1),"PageSize");
            data[i][3]= testDataReader2.getCellData("PeriodProgramTemplate_TestData","Get_InValid_PeriodProgramTemplate_Pagenation_"+(i+1),"PageNumber");
            data[i][4]= testDataReader2.getCellData("PeriodProgramTemplate_TestData","Get_InValid_PeriodProgramTemplate_Pagenation_"+(i+1),"ExpectedResult");

        }
        return data;
    }
    @Test(description = "TC010  -PeriodProgramTemplate-   Perform Get all PeriodProgramTemplate API With invalid Pagenation"
            ,dataProvider = "Get_InValid_PeriodProgramTemplate_Pagenation")
    @Story("Retrieving all PeriodProgramTemplate  With InValid Pagenation")
    @Severity(SeverityLevel.CRITICAL)
    public void Get_InValid_PeriodProgramTemplate_Pagenation_Rq_TC(Object[] data) {
        Token_API Token_TC=new Token_API();
        Token_TC.POST_Valid_TOKEN_Rq(UserName,Password);
        Token_TC.Check_Token_Valid_status_Code_Response();
        String Token =Token_TC.Get_Valid_Access_Token();

        PeriodProgramTemplates_API GetAllPeriodProgramTemplate_TC=new PeriodProgramTemplates_API();
        GetAllPeriodProgramTemplate_TC.Get_InValid_all_PeriodProgramTemplates_by_parameter_Query_Rq(Token,data[2].toString(),data[3].toString());
        GetAllPeriodProgramTemplate_TC.Check_Validation_Error_PeriodProgramTemplates_status_Code_Response();
        GetAllPeriodProgramTemplate_TC.Check_PeriodProgramTemplates_Response_Time();
        GetAllPeriodProgramTemplate_TC.Check_PeriodProgramTemplates_Content(data[4].toString());
        GetAllPeriodProgramTemplate_TC.Check_PeriodProgramTemplates_Response_Validation_Error_Schema();
    }
}
