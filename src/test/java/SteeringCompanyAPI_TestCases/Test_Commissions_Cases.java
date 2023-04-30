/*
 * Copyright (c) 2023.
 */

package SteeringCompanyAPI_TestCases;

import SteeringCompanyAPIs.Commissions_API;
import SteeringCompanyAPIs.Token_API;
import Utils.ExtraExcelFun;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.jetbrains.annotations.NotNull;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@SuppressWarnings("ALL")
public class Test_Commissions_Cases {

    ExtraExcelFun testDataReader ;
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

     //////////////////Test Cases for Get All Active Commissions API ////////////////////////////

    @Test(description = "TC001  -Commission- Perform Get all Commissions API with valid user name and password")
    @Story("Retrieving All Active Commission")
    @Severity(SeverityLevel.CRITICAL)
    public void Valid_GET_all_Commissions_Rq_TC() {
         Token_API Token_TC=new Token_API();
         Token_TC.POST_Valid_TOKEN_Rq(UserName,Password);
         Token_TC.Check_Token_Valid_status_Code_Response();
         String Token =Token_TC.Get_Valid_Access_Token();

    Commissions_API GetAllCommissions_TC=new Commissions_API();
         GetAllCommissions_TC.Get_all_Commissions_Rq(Token);
         GetAllCommissions_TC.Check_Valid_Commissions_status_Code_Response();
         GetAllCommissions_TC.Check_Commissions_Response_Time();
         GetAllCommissions_TC.Check_all_Commissions_Valid_Content();
         GetAllCommissions_TC.Check_Get_all_Commissions_Response_Valid_Schema();
     }

    @DataProvider (name = "Valid_data_Get_all_Commissions")
    public Object[][] Valid_data_Get_all_Commissions(){
        int dataRowsNumber = testDataReader2.CountRowsHasSpecificText("Commission_TestData","Get_AllCommission_Valid_Pagenation_");
        Object[][] data =new Object[dataRowsNumber][ 5];
        for (int i=0;i<dataRowsNumber;i++)
        {
            data[i][0]= testDataReader2.getCellData("Commission_TestData","Get_AllCommission_Valid_Pagenation_"+(i+1),"TC_Type");
            data[i][1]= testDataReader2.getCellData("Commission_TestData","Get_AllCommission_Valid_Pagenation_"+(i+1),"APIName");
            data[i][2]= testDataReader2.getCellData("Commission_TestData","Get_AllCommission_Valid_Pagenation_"+(i+1),"PageSize");
            data[i][3]= testDataReader2.getCellData("Commission_TestData","Get_AllCommission_Valid_Pagenation_"+(i+1),"PageNumber");
            data[i][4]= testDataReader2.getCellData("Commission_TestData","Get_AllCommission_Valid_Pagenation_"+(i+1),"ExpectedResult");

        }
        return data;
    }
    @Test(description = "TC002  -Commission- Perform Get all Commissions API with pagination with valid user name and password"
    ,dataProvider = "Valid_data_Get_all_Commissions")
    @Story("Retrieving All Active Commission with Pagination")
    @Severity(SeverityLevel.CRITICAL)
    public void Valid_GET_all_Commissions_by_Qry_Rq_TC(Object[] data) {
        Token_API Token_TC=new Token_API();
        Token_TC.POST_Valid_TOKEN_Rq(UserName,Password);
        Token_TC.Check_Token_Valid_status_Code_Response();
        String Token =Token_TC.Get_Valid_Access_Token();

   Commissions_API GetAllCommissions_TC=new Commissions_API();
        GetAllCommissions_TC.Get_Commissions_by_parameter_Query_Rq(Token,data[2].toString(),data[3].toString());
        GetAllCommissions_TC.Check_Valid_Commissions_status_Code_Response();
        GetAllCommissions_TC.Check_Commissions_Response_Time();
        GetAllCommissions_TC.Check_all_Commissions_Valid_Content();
        GetAllCommissions_TC.Check_Get_all_Commissions_Response_Valid_Schema();
    }

    @Test(description = "TC003  -Commission- Perform Get all Commissions API with missing Token")
    @Story("Retrieving All Active Commission with missing Token")
    @Severity(SeverityLevel.MINOR)
    public void Invalid_GET_all_Commissions_with_missing_Token_TC() {
        Token_API Token_TC=new Token_API();
        Token_TC.POST_Valid_TOKEN_Rq(UserName,Password);
        Token_TC.Check_Token_Valid_status_Code_Response();
        //String Token =Token_TC.Get_Valid_Access_Token();

   Commissions_API GetAllCommissions_TC=new Commissions_API();
        GetAllCommissions_TC.Get_all_Commissions_With_Missing_Token_Rq();
        GetAllCommissions_TC.Check_Unauthorized_Commissions_status_Code_Response();
        GetAllCommissions_TC.Check_Commissions_Response_Time();
        GetAllCommissions_TC.Check_Commissions_Response_Unauthorized_Schema();
    }

    @Test(description = "TC004 -Commission- Perform Get all Commissions API with invalid or expired Token")
    @Story("Retrieving All Active Commission with invalid/expired Token")
    @Severity(SeverityLevel.MINOR)
    public void Invalid_GET_all_Commissions_with_invalid_Token_TC() {
         Token_API Token_TC=new Token_API();
         Token_TC.POST_Valid_TOKEN_Rq(UserName,Password);
         Token_TC.Check_Token_Valid_status_Code_Response();
         //String Token =Token_TC.Get_Valid_Access_Token();
   Commissions_API GetAllCommissions_TC=new Commissions_API();
        GetAllCommissions_TC.Get_all_Commissions_With_InValid_Token_Rq("123");
        GetAllCommissions_TC.Check_Unauthorized_Commissions_status_Code_Response();
        GetAllCommissions_TC.Check_Commissions_Response_Time();
        //GetAllCommissions_TC.Check_Commissions_Response_Unauthorized_Schema(); // There is no response content
    }

    /////////////////////// Test Case for Add Commissions //////////////////////////////////////////
    @DataProvider (name = "Valid_data_add_Commissions")
    public Object[][] Valid_Add_Commissions(){
        int dataRowsNumber = testDataReader2.CountRowsHasSpecificText("Commission_TestData","Add_Commission_Valid_");
        Object[][] data =new Object[dataRowsNumber][ 5];
        for (int i=0;i<dataRowsNumber;i++)
        {
            data[i][0]= testDataReader2.getCellData("Commission_TestData","Add_Commission_Valid_"+(i+1),"TC_Type");
            data[i][1]= testDataReader2.getCellData("Commission_TestData","Add_Commission_Valid_"+(i+1),"APIName");
            data[i][2]= testDataReader2.getCellData("Commission_TestData","Add_Commission_Valid_"+(i+1),"otaCode");
            data[i][3]= testDataReader2.getCellData("Commission_TestData","Add_Commission_Valid_"+(i+1),"addCommission");
            data[i][4]= testDataReader2.getCellData("Commission_TestData","Add_Commission_Valid_"+(i+1),"ExpectedResult");

        }
        return data;
    }

    @SuppressWarnings("TestDataSupplier")
    @Test(description = "TC005 -Commission- Perform Add Valid Commission",dataProvider = "Valid_data_add_Commissions")
    @Story("Adding Commission")
    @Severity(SeverityLevel.CRITICAL)
    public void Valid_Add_Commissions_TC(Object[] data) {
        Token_API Token_TC=new Token_API();
        Token_TC.POST_Valid_TOKEN_Rq(UserName,Password);
        Token_TC.Check_Token_Valid_status_Code_Response();
        String Token =Token_TC.Get_Valid_Access_Token();

   Commissions_API AddCommissions_TC=new Commissions_API();
        AddCommissions_TC.Add_Commissions_Rq(Token,data);
        AddCommissions_TC.Check_Valid_Commissions_status_Code_Response();
        AddCommissions_TC.Check_Commissions_Response_Time();
        AddCommissions_TC.Check_Commissions_Valid_Content();
       // testDataReader = new ExtraExcelFun("SteeringCompanyAPI_TestData/SteeringCompanyAPI_TestData.xlsx");
      //  String ExpectedResult=testDataReader.getCellData("Commission_TestData",data[0].toString(),"ExpectedResult");
        AddCommissions_TC.Check_Commissions_Content(data[data.length-1].toString());
        AddCommissions_TC.Check_Add_Commissions_Response_Valid_Schema();
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    @SuppressWarnings("TestDataSupplier")
    @Test(description = "TC006 -Commission- Perform Add Commission With Missing Token",dataProvider = "Valid_data_add_Commissions")
    @Story("Adding Commission")
    @Severity(SeverityLevel.NORMAL)
    public void InValid_Add_Commissions_With_Missing_Token_TC(Object[] data){
   Commissions_API AddCommissions_TC=new Commissions_API();
        AddCommissions_TC.Add_Commissions_With_Missing_Token_Rq(data);
        AddCommissions_TC.Check_Unauthorized_Commissions_status_Code_Response();
        AddCommissions_TC.Check_Commissions_Response_Time();
        AddCommissions_TC.Check_Commissions_Response_Unauthorized_Schema();
    }

    @SuppressWarnings("TestDataSupplier")
    @Test(description = "TC007 -Commission- Perform Add Commission With Invalid/Expired Token",dataProvider = "Valid_data_add_Commissions")
    @Story("Adding Commission")
    @Severity(SeverityLevel.NORMAL)
    public void InValid_Add_Commissions_With_Invalid_Token_TC(Object[] data){

   Commissions_API AddCommissions_TC=new Commissions_API();
        AddCommissions_TC.Add_Commissions_With_InValid_Token_Rq("123",data);
        AddCommissions_TC.Check_Unauthorized_Commissions_status_Code_Response();
        AddCommissions_TC.Check_Commissions_Response_Time();
    }
    
    /////////////////////// Test Case for Get Commissions //////////////////////////////////////////
    @DataProvider (name = "Valid_data_Get_Commissions")
    public Object[][] Valid_Get_Commissions(){
        int dataRowsNumber = testDataReader2.CountRowsHasSpecificText("Commission_TestData","Get_Commission_Valid_");
        Object[][] data =new Object[dataRowsNumber][ 5];
        for (int i=0;i<dataRowsNumber;i++)
        {
            data[i][0]= testDataReader2.getCellData("Commission_TestData","Get_Commission_Valid_"+(i+1),"TC_Type");
            data[i][1]= testDataReader2.getCellData("Commission_TestData","Get_Commission_Valid_"+(i+1),"APIName");
            data[i][2]= testDataReader2.getCellData("Commission_TestData","Get_Commission_Valid_"+(i+1),"otaCode");
            data[i][3]= testDataReader2.getCellData("Commission_TestData","Get_Commission_Valid_"+(i+1),"addCommission");
            data[i][4]= testDataReader2.getCellData("Commission_TestData","Get_Commission_Valid_"+(i+1),"ExpectedResult");

        }
        return data;
    }

    @SuppressWarnings("TestDataSupplier")
    @Test(description = "TC008 -Commission- Perform Get Valid Commission",dataProvider = "Valid_data_Get_Commissions")
    @Story("Retrieving Commission")
    @Severity(SeverityLevel.CRITICAL)
    public void Valid_Get_Commissions_TC(Object[] data){
        Token_API Token_TC=new Token_API();
        Token_TC.POST_Valid_TOKEN_Rq(UserName,Password);
        Token_TC.Check_Token_Valid_status_Code_Response();
        String Token =Token_TC.Get_Valid_Access_Token();

   Commissions_API GetCommissions_TC=new Commissions_API();
        GetCommissions_TC.Get_Commissions_ById_Rq(Token,data);
        GetCommissions_TC.Check_Valid_Commissions_status_Code_Response();
        GetCommissions_TC.Check_Commissions_Response_Time();
       // testDataReader = new ExtraExcelFun("SteeringCompanyAPI_TestData/SteeringCompanyAPI_TestData.xlsx");
       // String ExpectedResult=testDataReader.getCellData("Commission_TestData",data[0].toString(),"ExpectedResult");
        GetCommissions_TC.Check_Commissions_Content(data[data.length-1].toString());
        GetCommissions_TC.Check_Get_Commissions_ByID_Response_Valid_Schema();
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    @SuppressWarnings("TestDataSupplier")
    @Test(description = "TC009 -Commission- Perform Get Commission With Missing Token",dataProvider = "Valid_data_Get_Commissions")
    @Story("Retrieving Commission")
    @Severity(SeverityLevel.NORMAL)
    public void InValid_Get_Commissions_With_Missing_Token_TC(Object[] data){
   Commissions_API GetCommissions_TC=new Commissions_API();
        GetCommissions_TC.Get_Commissions_ById_With_Missing_Token_Rq(data);
        GetCommissions_TC.Check_Unauthorized_Commissions_status_Code_Response();
        GetCommissions_TC.Check_Commissions_Response_Time();
        // GetCommissions_TC.Check_All_Commissions_Valid_Content();
        GetCommissions_TC.Check_Commissions_Response_Unauthorized_Schema();
    }

    @SuppressWarnings("TestDataSupplier")
    @Test(description = "TC010 -Commission- Perform Get Commission With InValid/Expired Token",dataProvider = "Valid_data_Get_Commissions")
    @Story("Retrieving Commission")
    @Severity(SeverityLevel.NORMAL)
    public void InValid_Get_Commissions_With_InValid_Token_TC(Object[] data){
   Commissions_API GetCommissions_TC=new Commissions_API();
        GetCommissions_TC.Get_Commissions_ById_With_InValid_Token_Rq("123",data);
        GetCommissions_TC.Check_Unauthorized_Commissions_status_Code_Response();
        GetCommissions_TC.Check_Commissions_Response_Time();
    }
    
    ///////////////////////////////////////// Missing Keys Test Cases for Add Commission /////////////////////////////////////
    @DataProvider(name = "Missing_data_add_Commission")
    public Object[][] Missing_data_add_Commission() {
        int dataRowsNumber = testDataReader2.CountRowsHasSpecificText("Commission_TestData","Add_Commission_Missing_");
        Object[][] data =new Object[dataRowsNumber][ 5];
        for (int i=0;i<dataRowsNumber;i++)
        {
            data[i][0]= testDataReader2.getCellData("Commission_TestData","Add_Commission_Missing_"+(i+1),"TC_Type");
            data[i][1]= testDataReader2.getCellData("Commission_TestData","Add_Commission_Missing_"+(i+1),"APIName");
            data[i][2]= testDataReader2.getCellData("Commission_TestData","Add_Commission_Missing_"+(i+1),"otaCode");
            data[i][3]= testDataReader2.getCellData("Commission_TestData","Add_Commission_Missing_"+(i+1),"addCommission");
            data[i][4]= testDataReader2.getCellData("Commission_TestData","Add_Commission_Missing_"+(i+1),"ExpectedResult");

        }
        return data;
    }

    @SuppressWarnings("TestDataSupplier")
    @Test(description = "TC011 -Commission- Perform Add Commission with Missing Keys",dataProvider = "Missing_data_add_Commission")
    @Story("Adding Commission with Missing keys")
    @Severity(SeverityLevel.CRITICAL)
    public void Add_Commissions_With_Missing_Keys_TC(Object[] data){
        Token_API Token_TC=new Token_API();
        Token_TC.POST_Valid_TOKEN_Rq(UserName,Password);
        Token_TC.Check_Token_Valid_status_Code_Response();
        String Token =Token_TC.Get_Valid_Access_Token();

   Commissions_API Commissions_TC=new Commissions_API();
   Commissions_TC.Add_Commissions_With_Invalid_Input_Rq(Token,data);
   Commissions_TC.Check_Validation_Error_Commissions_status_Code_Response();
   Commissions_TC.Check_Commissions_Response_Time();
   Commissions_TC.Check_Commissions_Content(data[data.length-1].toString());
   Commissions_TC.Check_Commissions_Response_Validation_Error_Schema();
    }
    
    ///////////////////////////////////////// InValid Data Test Cases for Add Commission/////////////////////////////////////
    @DataProvider(name = "InValid_data_add_Commission")
    public Object[][] InValid_data_add_Commission() {
        int dataRowsNumber = testDataReader2.CountRowsHasSpecificText("Commission_TestData","Add_Commission_InValid_");
        Object[][] data =new Object[dataRowsNumber][ 5];
        for (int i=0;i<dataRowsNumber;i++)
        {
            data[i][0]= testDataReader2.getCellData("Commission_TestData","Add_Commission_InValid_"+(i+1),"TC_Type");
            data[i][1]= testDataReader2.getCellData("Commission_TestData","Add_Commission_InValid_"+(i+1),"APIName");
            data[i][2]= testDataReader2.getCellData("Commission_TestData","Add_Commission_InValid_"+(i+1),"otaCode");
            data[i][3]= testDataReader2.getCellData("Commission_TestData","Add_Commission_InValid_"+(i+1),"addCommission");
            data[i][4]= testDataReader2.getCellData("Commission_TestData","Add_Commission_InValid_"+(i+1),"ExpectedResult");

        }
        return data;
    }
    @SuppressWarnings("TestDataSupplier")
    @Test(description = "TC012 -Commission- Perform Add Commission with InValid data",dataProvider = "InValid_data_add_Commission")
    @Story("Adding Commission with InValid data")
    @Severity(SeverityLevel.CRITICAL)
    public void Add_Commissions_With_InValid_data_TC(Object[] data){
        Token_API Token_TC=new Token_API();
        Token_TC.POST_Valid_TOKEN_Rq(UserName,Password);
        Token_TC.Check_Token_Valid_status_Code_Response();
        String Token =Token_TC.Get_Valid_Access_Token();

   Commissions_API Commissions_TC=new Commissions_API();
   Commissions_TC.Add_Commissions_With_Invalid_Input_Rq(Token,data);
   Commissions_TC.Check_Validation_Error_Commissions_status_Code_Response();
   Commissions_TC.Check_Commissions_Response_Time();
   Commissions_TC.Check_Commissions_Content(data[data.length-1].toString());
   Commissions_TC.Check_Commissions_Response_Validation_Error_Schema();
    }
    
    ///////////////////////////////////////// Not Accepted Test Cases for Add Commission/////////////////////////////////////
    @DataProvider(name = "NotAccepted_data_add_Commission")
    public Object[][] NotAccepted_data_add_Commission() {
        int dataRowsNumber = testDataReader2.CountRowsHasSpecificText("Commission_TestData","Add_Commission_NotAccepted_");
        Object[][] data =new Object[dataRowsNumber][ 5];
        for (int i=0;i<dataRowsNumber;i++)
        {
            data[i][0]= testDataReader2.getCellData("Commission_TestData","Add_Commission_NotAccepted_"+(i+1),"TC_Type");
            data[i][1]= testDataReader2.getCellData("Commission_TestData","Add_Commission_NotAccepted_"+(i+1),"APIName");
            data[i][2]= testDataReader2.getCellData("Commission_TestData","Add_Commission_NotAccepted_"+(i+1),"otaCode");
            data[i][3]= testDataReader2.getCellData("Commission_TestData","Add_Commission_NotAccepted_"+(i+1),"addCommission");
            data[i][4]= testDataReader2.getCellData("Commission_TestData","Add_Commission_NotAccepted_"+(i+1),"ExpectedResult");

        }
        return data;
    }
    @SuppressWarnings("TestDataSupplier")
    @Test(description = "TC013 -Commission- Perform Add Commission with NotAccepted data",dataProvider = "NotAccepted_data_add_Commission")
    @Story("Adding Commission with NotAccepted data")
    @Severity(SeverityLevel.CRITICAL)
    public void Add_Commissions_With_NotAccepted_data_TC(Object[] data){
        Token_API Token_TC=new Token_API();
        Token_TC.POST_Valid_TOKEN_Rq(UserName,Password);
        Token_TC.Check_Token_Valid_status_Code_Response();
        String Token =Token_TC.Get_Valid_Access_Token();

   Commissions_API Commissions_TC=new Commissions_API();
   Commissions_TC.Add_Commissions_With_NotAccepted_Input_Rq(Token,data);
   Commissions_TC.Check_Validation_NotAccepted_Commissions_status_Code_Response();
   Commissions_TC.Check_Commissions_Response_Time();
   Commissions_TC.Check_Commissions_Content(data[data.length-1].toString());
   Commissions_TC.Check_Commissions_Response_NotAccepted_Error_Schema();
    }
    
    ///////////////////////////////////////// Not Accepted Test Cases for Add Commission/////////////////////////////////////
    @DataProvider(name = "NotFound_data_add_Commission")
    public Object[][] NotFound_data_add_Commission() {
        int dataRowsNumber = testDataReader2.CountRowsHasSpecificText("Commission_TestData","Add_Commission_NotFound_");
        Object[][] data =new Object[dataRowsNumber][ 5];
        for (int i=0;i<dataRowsNumber;i++)
        {
            data[i][0]= testDataReader2.getCellData("Commission_TestData","Add_Commission_NotFound_"+(i+1),"TC_Type");
            data[i][1]= testDataReader2.getCellData("Commission_TestData","Add_Commission_NotFound_"+(i+1),"APIName");
            data[i][2]= testDataReader2.getCellData("Commission_TestData","Add_Commission_NotFound_"+(i+1),"otaCode");
            data[i][3]= testDataReader2.getCellData("Commission_TestData","Add_Commission_NotFound_"+(i+1),"addCommission");
            data[i][4]= testDataReader2.getCellData("Commission_TestData","Add_Commission_NotFound_"+(i+1),"ExpectedResult");

        }
        return data;
    }
    @SuppressWarnings("TestDataSupplier")
    @Test(description = "TC014 -Commission- Perform Add Commission with NotFound data",dataProvider = "NotFound_data_add_Commission")
    @Story("Adding Commission with NotFound data")
    @Severity(SeverityLevel.CRITICAL)
    public void Add_Commissions_With_NotFound_data_TC(Object[] data){
        Token_API Token_TC=new Token_API();
        Token_TC.POST_Valid_TOKEN_Rq(UserName,Password);
        Token_TC.Check_Token_Valid_status_Code_Response();
        String Token =Token_TC.Get_Valid_Access_Token();

   Commissions_API Commissions_TC=new Commissions_API();
   Commissions_TC.Add_Commissions_With_NotFound_Input_Rq(Token,data);
   Commissions_TC.Check_Validation_NotFound_Commissions_status_Code_Response();
   Commissions_TC.Check_Commissions_Response_Time();
   Commissions_TC.Check_Commissions_Content(data[data.length-1].toString());
   Commissions_TC.Check_Commissions_Response_NotFound_Error_Schema();
    }


    ///////////////////////////////////////// Not Found data Test Cases for Get Commission /////////////////////////////////////
    @DataProvider(name = "NotFound_data_Get_Commission")
    public Object[][] NotFound_data_Get_Commission() {
        int dataRowsNumber = testDataReader2.CountRowsHasSpecificText("Commission_TestData","Get_Commission_NotFound_");
        Object[][] data =new Object[dataRowsNumber][ 5];
        for (int i=0;i<dataRowsNumber;i++)
        {
            data[i][0]= testDataReader2.getCellData("Commission_TestData","Get_Commission_NotFound_"+(i+1),"TC_Type");
            data[i][1]= testDataReader2.getCellData("Commission_TestData","Get_Commission_NotFound_"+(i+1),"APIName");
            data[i][2]= testDataReader2.getCellData("Commission_TestData","Get_Commission_NotFound_"+(i+1),"otaCode");
            data[i][3]= testDataReader2.getCellData("Commission_TestData","Get_Commission_NotFound_"+(i+1),"addCommission");
            data[i][4]= testDataReader2.getCellData("Commission_TestData","Get_Commission_NotFound_"+(i+1),"ExpectedResult");

        }
        return data;
    }
    @SuppressWarnings("TestDataSupplier")
    @Test(description = "TC015 -Commission- Perform Get Commission with NotFound data",dataProvider = "NotFound_data_Get_Commission")
    @Story("Retrieving Commission with NotFound data")
    @Severity(SeverityLevel.CRITICAL)
    public void Get_Commissions_With_NotFound_data_TC(Object[] data){
        Token_API Token_TC=new Token_API();
        Token_TC.POST_Valid_TOKEN_Rq(UserName,Password);
        Token_TC.Check_Token_Valid_status_Code_Response();
        String Token =Token_TC.Get_Valid_Access_Token();

   Commissions_API Commissions_TC=new Commissions_API();
   Commissions_TC.Get_Commissions_ById_With_NotFound_Input_Rq(Token,data);
   Commissions_TC.Check_Validation_NotFound_Commissions_status_Code_Response();
   Commissions_TC.Check_Commissions_Response_Time();
   Commissions_TC.Check_Commissions_Content(data[data.length-1].toString());
   Commissions_TC.Check_Commissions_Response_NotFound_Error_Schema();
    }

    //////////////////Test Cases for Get All Active Commissions API ////////////////////////////

    @Test(description = "TC016  -OTAs- Perform Get all OTAs API with valid user name and password")
    @Story("Retrieving All Active OTAs")
    @Severity(SeverityLevel.CRITICAL)
    public void Valid_GET_all_OTAs_Rq_TC() {
        Token_API Token_TC=new Token_API();
        Token_TC.POST_Valid_TOKEN_Rq(UserName,Password);
        Token_TC.Check_Token_Valid_status_Code_Response();
        String Token =Token_TC.Get_Valid_Access_Token();

   Commissions_API GetAllOTAs_TC=new Commissions_API();
        GetAllOTAs_TC.Get_all_OTAs_Rq(Token);
        GetAllOTAs_TC.Check_Valid_OTAs_status_Code_Response();
        GetAllOTAs_TC.Check_OTAs_Response_Time();
        GetAllOTAs_TC.Check_all_OTAs_Valid_Content();
        GetAllOTAs_TC.Check_Get_all_OTAs_Response_Valid_Schema();
    }

    @DataProvider (name = "Valid_data_Get_all_OTAs")
    public Object[][] Valid_data_Get_all_OTAs(){
        int dataRowsNumber = testDataReader2.CountRowsHasSpecificText("Commission_TestData","Get_AllOTA_Valid_Pagenation_");
        Object[][] data =new Object[dataRowsNumber][ 5];
        for (int i=0;i<dataRowsNumber;i++)
        {
            data[i][0]= testDataReader2.getCellData("Commission_TestData","Get_AllOTA_Valid_Pagenation_"+(i+1),"TC_Type");
            data[i][1]= testDataReader2.getCellData("Commission_TestData","Get_AllOTA_Valid_Pagenation_"+(i+1),"APIName");
            data[i][2]= testDataReader2.getCellData("Commission_TestData","Get_AllOTA_Valid_Pagenation_"+(i+1),"PageSize");
            data[i][3]= testDataReader2.getCellData("Commission_TestData","Get_AllOTA_Valid_Pagenation_"+(i+1),"PageNumber");
            data[i][4]= testDataReader2.getCellData("Commission_TestData","Get_AllOTA_Valid_Pagenation_"+(i+1),"ExpectedResult");

        }
        return data;
    }
    @Test(description = "TC017  -OTAs- Perform Get all OTAs API with pagination with valid user name and password"
            ,dataProvider = "Valid_data_Get_all_OTAs")
    @Story("Retrieving All Active OTAs with Pagination")
    @Severity(SeverityLevel.CRITICAL)
    public void Valid_GET_all_OTAs_by_Qry_Rq_TC(Object[] data) {
        Token_API Token_TC=new Token_API();
        Token_TC.POST_Valid_TOKEN_Rq(UserName,Password);
        Token_TC.Check_Token_Valid_status_Code_Response();
        String Token =Token_TC.Get_Valid_Access_Token();

   Commissions_API GetAllOTAs_TC=new Commissions_API();
        GetAllOTAs_TC.GeT_all_OTAs_Path_by_parameter_Query_Rq(Token,data[2].toString(),data[3].toString());
        GetAllOTAs_TC.Check_Valid_OTAs_status_Code_Response();
        GetAllOTAs_TC.Check_OTAs_Response_Time();
        GetAllOTAs_TC.Check_all_OTAs_Valid_Content();
        GetAllOTAs_TC.Check_Get_all_OTAs_Response_Valid_Schema();
    }

    @Test(description = "TC018  -OTAs- Perform Get all OTAs API with missing Token")
    @Story("Retrieving All Active OTAs with missing Token")
    @Severity(SeverityLevel.MINOR)
    public void Invalid_GET_all_OTAs_with_missing_Token_TC() {
        Token_API Token_TC=new Token_API();
        Token_TC.POST_Valid_TOKEN_Rq(UserName,Password);
        Token_TC.Check_Token_Valid_status_Code_Response();
        //String Token =Token_TC.Get_Valid_Access_Token();

   Commissions_API GetAllOTAs_TC=new Commissions_API();
        GetAllOTAs_TC.GeT_all_OTAs_With_Missing_Token_Rq();
        GetAllOTAs_TC.Check_Unauthorized_OTAs_status_Code_Response();
        GetAllOTAs_TC.Check_OTAs_Response_Time();
        GetAllOTAs_TC.Check_Get_all_OTAs_Response_Unauthorized_Schema();
    }

    @Test(description = "TC019 -OTAs- Perform Get all OTAs API with invalid or expired Token")
    @Story("Retrieving All Active OTAs with invalid/expired Token")
    @Severity(SeverityLevel.MINOR)
    public void Invalid_GET_all_OTAs_with_invalid_Token_TC() {
        Token_API Token_TC=new Token_API();
        Token_TC.POST_Valid_TOKEN_Rq(UserName,Password);
        Token_TC.Check_Token_Valid_status_Code_Response();
        //String Token =Token_TC.Get_Valid_Access_Token();
   Commissions_API GetAllOTAs_TC=new Commissions_API();
        GetAllOTAs_TC.GeT_all_OTAs_With_InValid_Token_Rq("123");
        GetAllOTAs_TC.Check_Unauthorized_OTAs_status_Code_Response();
        GetAllOTAs_TC.Check_OTAs_Response_Time();
        //GetAllOTAs_TC.Check_OTAs_Response_Unauthorized_Schema(); // There is no response content
    }
    ///////////////Test cases for InValid OTAs Pagenation/////////////////////////////////////////
    @DataProvider(name = "Get_InValid_OTAs_Pagenation")
    public Object[][] Get_InValid_OTAs_Pagenation(){
        int dataRowsNumber = testDataReader2.CountRowsHasSpecificText("Commission_TestData","Get_InValid_AllOTA_Pagenation_");
        Object[][] data =new Object[dataRowsNumber][ 5];
        for (int i=0;i<dataRowsNumber;i++)
        {
            data[i][0]= testDataReader2.getCellData("Commission_TestData","Get_InValid_AllOTA_Pagenation_"+(i+1),"TC_Type");
            data[i][1]= testDataReader2.getCellData("Commission_TestData","Get_InValid_AllOTA_Pagenation_"+(i+1),"APIName");
            data[i][2]= testDataReader2.getCellData("Commission_TestData","Get_InValid_AllOTA_Pagenation_"+(i+1),"PageSize");
            data[i][3]= testDataReader2.getCellData("Commission_TestData","Get_InValid_AllOTA_Pagenation_"+(i+1),"PageNumber");
            data[i][4]= testDataReader2.getCellData("Commission_TestData","Get_InValid_AllOTA_Pagenation_"+(i+1),"ExpectedResult");

        }
        return data;
    }
    @Test(description = "TC020  -OTAs-   Perform Get all OTAs API With invalid Pagenation"
            ,dataProvider = "Get_InValid_OTAs_Pagenation")
    @Story("Retrieving all OTAs  With InValid Pagenation")
    @Severity(SeverityLevel.CRITICAL)
    public void Get_InValid_OTAs_Pagenation_Rq_TC(Object @NotNull [] data) {
        Token_API Token_TC=new Token_API();
        Token_TC.POST_Valid_TOKEN_Rq(UserName,Password);
        Token_TC.Check_Token_Valid_status_Code_Response();
        String Token =Token_TC.Get_Valid_Access_Token();

   Commissions_API GetAllOTAs_TC=new Commissions_API();
        GetAllOTAs_TC.Get_InValid_all_OTAs_by_parameter_Query_Rq(Token,data[2].toString(),data[3].toString());
        GetAllOTAs_TC.Check_Validation_Error_OTAs_status_Code_Response();
        GetAllOTAs_TC.Check_OTAs_Response_Time();
        GetAllOTAs_TC.Check_Get_all_OTAs_Content(data[4].toString());
        GetAllOTAs_TC.Check_Get_all_OTAs_Response_Validation_Error_Schema();
    }
    ///////////////Test cases for InValid Commission Pagenation/////////////////////////////////////////
    @DataProvider(name = "Get_InValid_Commissions_Pagenation")
    public Object[][] Get_InValid_Commissions_Pagenation(){
        int dataRowsNumber = testDataReader2.CountRowsHasSpecificText("Commission_TestData","Get_InValid_AllCommission_Pagenation_");
        Object[][] data =new Object[dataRowsNumber][ 5];
        for (int i=0;i<dataRowsNumber;i++)
        {
            data[i][0]= testDataReader2.getCellData("Commission_TestData","Get_InValid_AllCommission_Pagenation_"+(i+1),"TC_Type");
            data[i][1]= testDataReader2.getCellData("Commission_TestData","Get_InValid_AllCommission_Pagenation_"+(i+1),"APIName");
            data[i][2]= testDataReader2.getCellData("Commission_TestData","Get_InValid_AllCommission_Pagenation_"+(i+1),"PageSize");
            data[i][3]= testDataReader2.getCellData("Commission_TestData","Get_InValid_AllCommission_Pagenation_"+(i+1),"PageNumber");
            data[i][4]= testDataReader2.getCellData("Commission_TestData","Get_InValid_AllCommission_Pagenation_"+(i+1),"ExpectedResult");

        }
        return data;
    }
    @Test(description = "TC020  -Commissions-   Perform Get all Commissions API With invalid Pagenation"
            ,dataProvider = "Get_InValid_Commissions_Pagenation")
    @Story("Retrieving all Commissions  With InValid Pagenation")
    @Severity(SeverityLevel.CRITICAL)
    public void Get_InValid_Commissions_Pagenation_Rq_TC(Object @NotNull [] data) {
        Token_API Token_TC=new Token_API();
        Token_TC.POST_Valid_TOKEN_Rq(UserName,Password);
        Token_TC.Check_Token_Valid_status_Code_Response();
        String Token =Token_TC.Get_Valid_Access_Token();

   Commissions_API GetAllCommissions_TC=new Commissions_API();
        GetAllCommissions_TC.Get_InValid_Commissions_by_parameter_Query_Rq(Token,data[2].toString(),data[3].toString());
        GetAllCommissions_TC.Check_Validation_Error_Commissions_status_Code_Response();
        GetAllCommissions_TC.Check_Commissions_Response_Time();
        GetAllCommissions_TC.Check_Commissions_Content(data[4].toString());
        GetAllCommissions_TC.Check_Commissions_Response_Validation_Error_Schema();
    }
}
