/*
 * Copyright (c) 2023.
 */

package SteeringCompanyAPI_TestCases;

import SteeringCompanyAPIs.Token_API;
import com.shaft.tools.io.ExcelFileManager;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Test_Token_Cases {
    
    ExcelFileManager testDataReader ;
    String UserName,Password,InValidUserName,InValidPassword;

     @BeforeClass
     public void Setup_data() {
     testDataReader = new ExcelFileManager("SteeringCompanyAPI_TestData/SteeringCompanyAPI_TestData.xlsx");
    // KEYCLOAK_HOST = "https://auth-demo.np.transporticonline.com" ;
     UserName=testDataReader.getCellData("TokenAPI_TestData","UserName","Data1");
     Password=testDataReader.getCellData("TokenAPI_TestData","Password","Data1");
     InValidUserName=testDataReader.getCellData("TokenAPI_TestData","UserName","Data2");
     InValidPassword=testDataReader.getCellData("TokenAPI_TestData","Password","Data2");  
     }
    
    @Test(description = "TC001 - Perform Post Token API with valid user name and password and return with token")
    public void Valid_Token_RQ_TC() {  
    	Token_API Token_TC=new Token_API();
    	Token_TC.POST_Valid_TOKEN_Rq(UserName,Password);
    	Token_TC.Check_Token_Valid_status_Code_Response();
    	Token_TC.Check_Token_Response_Valid_content();
    	Token_TC.Check_Token_Response_Valid_Schema();
    	Token_TC.Check_Token_Response_Time();
    	String Token =Token_TC.Get_Valid_Access_Token();
    	Token_TC.CheckTokenExpiration(Token);
    	Token_TC.CheckTokenISS(Token,"\"https://auth-demo.np.transporticonline.com/auth/realms/tic\"");
        Token_TC.CheckTokentcId(Token,"\"319957\"");
        Token_TC.CheckTokenpreferred_username(Token,"\"tokhi\"");
    }
    
    @Test(description = "TC002 - Perform Post Token API with Invalid user name and password")
    public void InValid_Token_RQ_TC() {  
        Token_API Token_TC2=new Token_API();
        Token_TC2.POST_Unauthorized_TOKEN_Rq(InValidUserName,InValidPassword);
        Token_TC2.Check_Token_Unauthorized_status_Code_Response();
        Token_TC2.Check_Token_Response_Unauthorized_content();
        Token_TC2.Check_Token_Response_Unauthorized_Schema();
        Token_TC2.Check_Token_Response_Time();
    }
    
    @Test(description = "TC003 - Perform Post Token API with empty request body")
    public void Bad_Token_RQ_TC() {  
        Token_API Token_TC3=new Token_API();
        Token_TC3.POST_InValid_TOKEN_Rq();
        Token_TC3.Check_Token_Bad_status_Code_Response();
        Token_TC3.Check_Token_Response_Bad_content();
        Token_TC3.Check_Token_Response_Bad_Schema();
        Token_TC3.Check_Token_Response_Time();
    }
    
 
}