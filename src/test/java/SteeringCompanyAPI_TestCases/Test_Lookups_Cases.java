/*
 * Copyright (c) 2023.
 */

package SteeringCompanyAPI_TestCases;

import SteeringCompanyAPIs.Lookups_API;
import SteeringCompanyAPIs.Token_API;
import Utils.ExtraExcelFun;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


@SuppressWarnings("TestDataSupplier")
public class Test_Lookups_Cases {

    ExtraExcelFun testDataReader;
    String UserName;
    String Password;

    @BeforeClass
     public void Setup_data() {
     testDataReader = new ExtraExcelFun("SteeringCompanyAPI_TestData/SteeringCompanyAPI_TestData.xlsx");
     UserName=testDataReader.getCellData("TokenAPI_TestData","UserName","Data1");
     Password=testDataReader.getCellData("TokenAPI_TestData","Password","Data1");
       }
    /////////////////////// Test Case for Valid Get all Lookups //////////////////////////////////////////
    @DataProvider(name = "Valid_GET_all_Lookups_Data")
    public Object[][] Valid_GET_all_Lookups_Data(){
        int dataRowsNumber = testDataReader.CountRowsHasSpecificText("Lookups_TestData","Get_All_Valid_");
        Object[][] data =new Object[dataRowsNumber][ 3];
        for (int i=0;i<dataRowsNumber;i++)
        {
            data[i][0]= testDataReader.getCellData("Lookups_TestData","Get_All_Valid_"+(i+1),"TC_Type");
            data[i][1]= testDataReader.getCellData("Lookups_TestData","Get_All_Valid_"+(i+1),"APIName");
            data[i][2]= testDataReader.getCellData("Lookups_TestData","Get_All_Valid_"+(i+1),"ExpectedResult");

        }
        return data;
    }
@Test(description = "TC001 - Perform Get all Lookups API with valid user name and password"
        ,dataProvider = "Valid_GET_all_Lookups_Data")
@Story("Geting Valid Lookups")
@Severity(SeverityLevel.CRITICAL)
    public void Valid_GET_all_Lookups_Rq_TC(Object[] data) {
    	Token_API Token_TC=new Token_API();
    	Token_TC.POST_Valid_TOKEN_Rq(UserName,Password);
        Token_TC.Check_Token_Valid_status_Code_Response();
    	String Token =Token_TC.Get_Valid_Access_Token();
    	
    	Lookups_API Lookups_TC=new Lookups_API();
    	Lookups_TC.GET_Valid_all_Lookups_Rq(Token,data[1].toString());
    	Lookups_TC.Check_Valid_Lookups_status_Code_Response();
    	Lookups_TC.Check_Valid_Lookups_Response_Time();
    	Lookups_TC.Check_Lookups_Valid_Content();
    	Lookups_TC.Check_Lookups_Response_Valid_Schema(data[1].toString());
    	    }
    /////////////////////// Test Case for Valid Get all Lookups with Pagenation //////////////////////////////////////////
    @DataProvider(name = "Valid_GET_Pagenation_Lookups_Data")
    public Object[][] Valid_GET_Pagenation_Lookups_Data(){
        int dataRowsNumber = testDataReader.CountRowsHasSpecificText("Lookups_TestData","Get_Valid_Pagenation_");
        Object[][] data =new Object[dataRowsNumber][ 5];
        for (int i=0;i<dataRowsNumber;i++)
        {
            data[i][0]= testDataReader.getCellData("Lookups_TestData","Get_Valid_Pagenation_"+(i+1),"TC_Type");
            data[i][1]= testDataReader.getCellData("Lookups_TestData","Get_Valid_Pagenation_"+(i+1),"APIName");
            data[i][2]= testDataReader.getCellData("Lookups_TestData","Get_Valid_Pagenation_"+(i+1),"PageSize");
            data[i][3]= testDataReader.getCellData("Lookups_TestData","Get_Valid_Pagenation_"+(i+1),"PageNumber");
            data[i][4]= testDataReader.getCellData("Lookups_TestData","Get_Valid_Pagenation_"+(i+1),"ExpectedResult");

        }
        return data;
    }
    @Test(description = "TC002 - Perform Get all Lookups API by parameters Qry with valid user name and password and return with token"
    ,dataProvider="Valid_GET_Pagenation_Lookups_Data")
    @Story("Geting Valid Lookups")
    @Severity(SeverityLevel.CRITICAL)
    public void Valid_GET_all_cities_Lookups_by_Qry_Rq_TC(Object[] data) {
    Token_API Token_TC=new Token_API();
    Token_TC.POST_Valid_TOKEN_Rq(UserName,Password);
    Token_TC.Check_Token_Valid_status_Code_Response();
    String Token =Token_TC.Get_Valid_Access_Token();
    
    Lookups_API Lookups_TC=new Lookups_API();
    Lookups_TC.GET_Valid_all_Lookups_by_parameter_Query_Rq(Token,data[1].toString(),data[2].toString(),data[3].toString());
    Lookups_TC.Check_Valid_Lookups_status_Code_Response();
    Lookups_TC.Check_Valid_Lookups_Response_Time();
    Lookups_TC.Check_Lookups_Valid_Content();
    Lookups_TC.Check_Lookups_Response_Valid_Schema(data[1].toString());
    
}
    /////////////////////////////////////Invalid token for Get Lookups///////////////////////////////////////////////////////////
    @Test(description = "TC003 - Perform Get all Lookups API with missing Token"
            ,dataProvider = "Valid_GET_all_Lookups_Data")
    @Story("Geting InValid Lookups")
    @Severity(SeverityLevel.NORMAL)
    public void InValid_GET_all_Lookups_With_Missing_Token_TC(Object[] data) {
        Token_API Token_TC=new Token_API();
        Token_TC.POST_Valid_TOKEN_Rq(UserName,Password);
        Token_TC.Check_Token_Valid_status_Code_Response();
        String Token =Token_TC.Get_Valid_Access_Token();

        Lookups_API Lookups_TC=new Lookups_API();
        Lookups_TC.GET_all_Lookups_With_Missing_Token_Rq(data[1].toString());
        Lookups_TC.Check_Lookups_status_Code_Unauthorized_Response();
        Lookups_TC.Check_Valid_Lookups_Response_Time();
        Lookups_TC.Check_Lookups_Response_Unauthorized_Schema();
    }

    @Test(description = "TC004 - Perform Get all Lookups API with invalid Token"
            ,dataProvider = "Valid_GET_all_Lookups_Data")
    @Story("Geting InValid Lookups")
    @Severity(SeverityLevel.NORMAL)
    public void InValid_GET_all_Lookups_With_Invalid_Token_TC(Object[] data) {
        Token_API Token_TC=new Token_API();
        Token_TC.POST_Valid_TOKEN_Rq(UserName,Password);
        Token_TC.Check_Token_Valid_status_Code_Response();
        String Token =Token_TC.Get_Valid_Access_Token();

        Lookups_API Lookups_TC=new Lookups_API();
        Lookups_TC.GET_all_Lookups_With_InValid_Token_Rq("123",data[1].toString());
        Lookups_TC.Check_Lookups_status_Code_Unauthorized_Response();
        Lookups_TC.Check_Valid_Lookups_Response_Time();
     //   Lookups_TC.Check_Lookups_Response_Unauthorized_Schema();
    }
}