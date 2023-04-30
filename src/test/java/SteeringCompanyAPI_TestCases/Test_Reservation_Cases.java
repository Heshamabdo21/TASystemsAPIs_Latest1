/*
 * Copyright (c) 2023.
 */

package SteeringCompanyAPI_TestCases;

import SteeringCompanyAPIs.Reservations_API;
import SteeringCompanyAPIs.Token_API;
import Utils.ExtraExcelFun;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@SuppressWarnings("ALL")
public class Test_Reservation_Cases {
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

    ///////////////Test cases for Get Reservation for Valid ID///////////////////////////////
    @DataProvider(name = "Get_Valid_Reservation_ByID")
    public Object[][] Get_Valid_Reservation_ByID(){
        int dataRowsNumber = testDataReader2.CountRowsHasSpecificText("Reservation_TestData","Get_Valid_Reservation_ByID_");
        Object[][] data =new Object[dataRowsNumber][ 4];
        for (int i=0;i<dataRowsNumber;i++)
        {
            data[i][0]= testDataReader2.getCellData("Reservation_TestData","Get_Valid_Reservation_ByID_"+(i+1),"TC_Type");
            data[i][1]= testDataReader2.getCellData("Reservation_TestData","Get_Valid_Reservation_ByID_"+(i+1),"APIName");
            data[i][2]= testDataReader2.getCellData("Reservation_TestData","Get_Valid_Reservation_ByID_"+(i+1),"ReservationID");
            data[i][3]= testDataReader2.getCellData("Reservation_TestData","Get_Valid_Reservation_ByID_"+(i+1),"ExpectedResult");

        }
        return data;
    }
    @Test(description = "TC001  -Reservation-   Perform Get Reservation API by ID"
            ,dataProvider = "Get_Valid_Reservation_ByID")
    @Story("Retrieving Reservation by ID")
    @Severity(SeverityLevel.CRITICAL)
    public void Get_Valid_Reservation_ByID_Rq_TC(Object[] data) {
        Token_API Token_TC=new Token_API();
        Token_TC.POST_Valid_TOKEN_Rq(UserName,Password);
        Token_TC.Check_Token_Valid_status_Code_Response();
        String Token =Token_TC.Get_Valid_Access_Token();

        Reservations_API GetAllReservation_TC=new Reservations_API();
        GetAllReservation_TC.Get_Valid_Reservations_by_id_Rq(Token,data[2].toString());
        GetAllReservation_TC.Check_Valid_Reservations_status_Code_Response();
        GetAllReservation_TC.Check_Reservations_Response_Time();
        GetAllReservation_TC.Check_Reservations_Content(data[3].toString());
        GetAllReservation_TC.Check_Reservations_by_id_Response_Valid_Schema();
    }
    ///////////////Test cases for Get Reservation Period for NotFound ID///////////////////////////////
    @DataProvider(name = "Get_Reservation_With_NotFound_ByID")
    public Object[][] Get_Reservation_With_NotFound_ByID(){
        int dataRowsNumber = testDataReader2.CountRowsHasSpecificText("Reservation_TestData","Get_NotFound_Reservation_ByID_");
        Object[][] data =new Object[dataRowsNumber][ 4];
        for (int i=0;i<dataRowsNumber;i++)
        {
            data[i][0]= testDataReader2.getCellData("Reservation_TestData","Get_NotFound_Reservation_ByID_"+(i+1),"TC_Type");
            data[i][1]= testDataReader2.getCellData("Reservation_TestData","Get_NotFound_Reservation_ByID_"+(i+1),"APIName");
            data[i][2]= testDataReader2.getCellData("Reservation_TestData","Get_NotFound_Reservation_ByID_"+(i+1),"ReservationID");
            data[i][3]= testDataReader2.getCellData("Reservation_TestData","Get_NotFound_Reservation_ByID_"+(i+1),"ExpectedResult");

        }
        return data;
    }
    @Test(description = "TC002  -Reservation-   Perform Get Reservation API With NotFound by ID"
            ,dataProvider = "Get_Reservation_With_NotFound_ByID")
    @Story("Retrieving Reservation by ID With Not Found Response")
    @Severity(SeverityLevel.CRITICAL)
    public void Get_Reservation_With_NotFound_ByID_Rq_TC(Object[] data) {
        Token_API Token_TC=new Token_API();
        Token_TC.POST_Valid_TOKEN_Rq(UserName,Password);
        Token_TC.Check_Token_Valid_status_Code_Response();
        String Token =Token_TC.Get_Valid_Access_Token();

        Reservations_API GetAllReservation_TC=new Reservations_API();
        GetAllReservation_TC.Get_Reservations_With_NotFound_by_id_Rq(Token,data[2].toString());
        GetAllReservation_TC.Check_Validation_NotFound_Reservations_status_Code_Response();
        GetAllReservation_TC.Check_Reservations_Response_Time();
        GetAllReservation_TC.Check_Reservations_Content(data[3].toString());
        GetAllReservation_TC.Check_Reservations_Response_NotFound_Error_Schema();
    }
    ///////////////Test cases for Get Reservation Period for BadRequest ID///////////////////////////////
    @DataProvider(name = "Get_Reservation_With_BadRequest_ByID")
    public Object[][] Get_Reservation_With_BadRequest_ByID(){
        int dataRowsNumber = testDataReader2.CountRowsHasSpecificText("Reservation_TestData","Get_BadRequest_Reservation_ByID_");
        Object[][] data =new Object[dataRowsNumber][ 4];
        for (int i=0;i<dataRowsNumber;i++)
        {
            data[i][0]= testDataReader2.getCellData("Reservation_TestData","Get_BadRequest_Reservation_ByID_"+(i+1),"TC_Type");
            data[i][1]= testDataReader2.getCellData("Reservation_TestData","Get_BadRequest_Reservation_ByID_"+(i+1),"APIName");
            data[i][2]= testDataReader2.getCellData("Reservation_TestData","Get_BadRequest_Reservation_ByID_"+(i+1),"ReservationID");
            data[i][3]= testDataReader2.getCellData("Reservation_TestData","Get_BadRequest_Reservation_ByID_"+(i+1),"ExpectedResult");

        }
        return data;
    }
    @Test(description = "TC003  -Reservation-   Perform Get Reservation API With BadRequest by ID"
            ,dataProvider = "Get_Reservation_With_BadRequest_ByID")
    @Story("Retrieving Reservation by ID With BadRequest Response")
    @Severity(SeverityLevel.CRITICAL)
    public void Get_Reservation_With_BadRequest_ByID_Rq_TC(Object[] data) {
        Token_API Token_TC=new Token_API();
        Token_TC.POST_Valid_TOKEN_Rq(UserName,Password);
        Token_TC.Check_Token_Valid_status_Code_Response();
        String Token =Token_TC.Get_Valid_Access_Token();

        Reservations_API GetAllReservation_TC=new Reservations_API();
        GetAllReservation_TC.Get_Reservations_With_BadRequest_by_id_Rq(Token,data[2].toString());
        GetAllReservation_TC.Check_Validation_BadRequest_Reservations_status_Code_Response();
        GetAllReservation_TC.Check_Reservations_Response_Time();
        GetAllReservation_TC.Check_Reservations_Content(data[3].toString());
        GetAllReservation_TC.Check_Reservations_Response_BadRequest_Error_Schema();
    }

    ///////////////Test cases for missing /invalid/expired Token/////////////////////////////////////////
    @Test(description = "TC004  -Reservation-   Perform Get Reservation by ID with missing Token"
            ,dataProvider = "Get_Valid_Reservation_ByID")
    @Story("Retrieving Reservation By ID with missing Token")
    @Severity(SeverityLevel.MINOR)
    public void Get_Invalid_Reservation_ByID_with_missing_Token_TC(Object[] data) {
        Token_API Token_TC=new Token_API();
        Token_TC.POST_Valid_TOKEN_Rq(UserName,Password);
        Token_TC.Check_Token_Valid_status_Code_Response();

        Reservations_API GetAllReservation_TC=new Reservations_API();
        GetAllReservation_TC.Get_Reservation_by_id_With_Missing_Token_Rq(data[2].toString());
        GetAllReservation_TC.Check_Unauthorized_Reservations_status_Code_Response();
        GetAllReservation_TC.Check_Reservations_Response_Time();
        GetAllReservation_TC.Check_all_Reservations_Response_Unauthorized_Schema();
    }
    @Test(description = "TC005 -Reservation-  Perform Get Reservation API by ID with invalid or expired Token"
            ,dataProvider = "Get_Valid_Reservation_ByID")
    @Story("Retrieving Reservation by ID with invalid/expired Token")
    @Severity(SeverityLevel.MINOR)
    public void Get_Invalid_Reservation_ByID_with_invalid_Token_TC(Object[] data) {
        Token_API Token_TC=new Token_API();
        Token_TC.POST_Valid_TOKEN_Rq(UserName,Password);
        Token_TC.Check_Token_Valid_status_Code_Response();
        Reservations_API GetAllReservation_TC=new Reservations_API();
        GetAllReservation_TC.Get_Reservation_by_id_With_InValid_Token_Rq("123",data[2].toString());
        GetAllReservation_TC.Check_Unauthorized_Reservations_status_Code_Response();
        GetAllReservation_TC.Check_Reservations_Response_Time();
        //GetAllReservation_TC.Check_All_Reservation_Response_Unauthorized_Schema(); // There is no response content
    }
}
