/*
 * Copyright (c) 2023.
 */

package SteeringCompanyAPIs;

import com.shaft.api.RequestBuilder.AuthenticationType;
import com.shaft.driver.SHAFT;
import com.shaft.tools.io.ExcelFileManager;
import io.restassured.response.Response;


public class Reservations_API {

    ExcelFileManager testDataReader = new ExcelFileManager("SteeringCompanyAPI_TestData/SteeringCompanyAPI_TestData.xlsx");
    String BaseURL = testDataReader.getCellData("API_Data","Steering_Base_URL","URL");

    String Reservations_Path = testDataReader.getCellData("API_Data","GetReservationsById","URL");
    Response Reservations_Response;
    SHAFT.API Reservations_api;

    public void Get_Valid_Reservations_by_id_Rq(String TokenValue,String ReservationsID) {
        Reservations_api = new SHAFT.API(BaseURL);
        Reservations_api.get(Reservations_Path+"/"+ReservationsID).
                setAuthentication("", "", AuthenticationType.NONE).
                setTargetStatusCode(200).
                addHeader("Authorization", "Bearer " + TokenValue).perform();
        Reservations_Response = Reservations_api.getResponse();
    }
    public void Get_Reservations_With_NotFound_by_id_Rq(String TokenValue,String ReservationsID) {
        Reservations_api = new SHAFT.API(BaseURL);
        Reservations_api.get(Reservations_Path+"/"+ReservationsID).
                setAuthentication("", "", AuthenticationType.NONE).
                setTargetStatusCode(404).
                addHeader("Authorization", "Bearer " + TokenValue).perform();
        Reservations_Response = Reservations_api.getResponse();
    }
    public void Get_Reservations_With_BadRequest_by_id_Rq(String TokenValue,String ReservationsID) {
        Reservations_api = new SHAFT.API(BaseURL);
        if(ReservationsID.contains(" ")) {
            ReservationsID = String.valueOf('\u2001');
        }
        Reservations_api.get(Reservations_Path+"/"+ReservationsID).
                setAuthentication("", "", AuthenticationType.NONE).
                setTargetStatusCode(400).
                addHeader("Authorization", "Bearer " + TokenValue).perform();
        Reservations_Response = Reservations_api.getResponse();
    }
    public void Get_Reservation_by_id_With_Missing_Token_Rq(String ReservationsID) {
        // String Lookup_cities_Path = "/lookups/cities";
        Reservations_api = new SHAFT.API(BaseURL);
        Reservations_api.get(Reservations_Path+"/"+ ReservationsID).
                setAuthentication("","", AuthenticationType.NONE).
                setTargetStatusCode(401).
                perform();
        //  addHeader("Authorization", "Bearer " ).perform();

        Reservations_Response = Reservations_api.getResponse();
    }
    public void Get_Reservation_by_id_With_InValid_Token_Rq(String TokenValue,String ReservationsID) {
        // String Lookup_cities_Path = "/lookups/cities";

        Reservations_api = new SHAFT.API(BaseURL);
        Reservations_api.get(Reservations_Path+"/"+ReservationsID).
                setAuthentication("","", AuthenticationType.NONE).
                setTargetStatusCode(401).
                addHeader("Authorization", "Bearer "+ TokenValue ).
                perform();

        Reservations_Response = Reservations_api.getResponse();
    }
    /////////////////////////////////////////////Status Code////////////////////////////////////
    public void Check_Valid_Reservations_status_Code_Response(){
        SHAFT.Validations.assertThat().number(Reservations_Response.getStatusCode()).isEqualTo(200).perform();
    }
    public void Check_Unauthorized_Reservations_status_Code_Response(){
        SHAFT.Validations.assertThat().number(Reservations_Response.getStatusCode()).isEqualTo(401).perform();
    }
    public void Check_Validation_NotFound_Reservations_status_Code_Response() {
        SHAFT.Validations.assertThat().number(Reservations_Response.getStatusCode()).isEqualTo(404).perform();
    }
    public void Check_Validation_BadRequest_Reservations_status_Code_Response() {
        SHAFT.Validations.assertThat().number(Reservations_Response.getStatusCode()).isEqualTo(400).perform();

    }
    ////////////////////////////////////////////////////////////////////////////////////////
    public void Check_Reservations_Response_Time() {
        SHAFT.Validations.verifyThat().number(Reservations_Response.getTime()).isGreaterThanOrEquals(1.1).perform();
        SHAFT.Validations.verifyThat().number(Reservations_Response.getTime()).isLessThanOrEquals(10000).perform();
    }
    ////////////////////////////////Content////////////////////////////////
    public void Check_Reservations_Content(String ExpectedResult) {
        //String Policy_ResponseBody = Policy_Response.getBody().asString();
        Reservations_api.assertThatResponse().body().contains(ExpectedResult).
                withCustomReportMessage("Check that content object contains : "+ExpectedResult).
                perform();
        // SHAFT.Validations.assertThat().object(Policy_ResponseBody.contains(ExpectedResult)).isTrue().perform();
        //SHAFT.Validations.assertThat().object(Policy_ResponseBody).contains(ExpectedResult).withCustomReportMessage("Check that content object contains"+ExpectedResult).perform();
    }
    public void Check_all_Reservations_Valid_Content() {
        String Reservations_ResponseBody = Reservations_Response.getBody().asString();
        SHAFT.Validations.assertThat().object(Reservations_ResponseBody).contains("vehicleTypes").perform();
        Reservations_api.assertThatResponse().extractedJsonValue("vehicleTypes").isNotNull().withCustomReportMessage("Check that vehicleTypes object is not null.").perform();
    }
    //////////////////////////////////////////////////Schema////////////////////////////
    public void Check_Reservations_by_id_Response_Valid_Schema() {
        Reservations_api.assertThatResponse().matchesSchema(testDataReader.getCellData("API_Data","GetReservationsById","Valid_Schema")).perform();
    }
    public void Check_all_Reservations_Response_Unauthorized_Schema() {
        Reservations_api.assertThatResponse().matchesSchema(testDataReader.getCellData("API_Data","UnAuthorized","URL")).perform();
    }
    public void Check_Reservations_Response_NotFound_Error_Schema() {
        Reservations_api.assertThatResponse().matchesSchema(testDataReader.getCellData("API_Data", "Not Found", "URL")).perform();
    }
    public void Check_Reservations_Response_BadRequest_Error_Schema() {
        Reservations_api.assertThatResponse().matchesSchema(testDataReader.getCellData("API_Data", "Bad Request", "URL")).perform();
    }
 
}