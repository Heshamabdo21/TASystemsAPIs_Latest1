/*
 * Copyright (c) 2023.
 */

package SteeringCompanyAPIs;

import com.shaft.api.RequestBuilder.AuthenticationType;
import com.shaft.api.RestActions;
import com.shaft.driver.SHAFT;
import com.shaft.tools.io.ExcelFileManager;
import io.restassured.response.Response;

import java.util.Arrays;
import java.util.List;


public class Lookups_API {

    ExcelFileManager testDataReader = new ExcelFileManager("SteeringCompanyAPI_TestData/SteeringCompanyAPI_TestData.xlsx");

    //  String BaseURL = "https://api-demo.np.transporticonline.com/steeringcompanies/v1" ;
    String BaseURL = testDataReader.getCellData("API_Data", "Steering_Base_URL", "URL");
    String Lookup_cities_Path = testDataReader.getCellData("API_Data", "cities", "URL");
    String Lookup_hotels_Path = testDataReader.getCellData("API_Data", "hotels", "URL");
    String Lookup_routes_Path = testDataReader.getCellData("API_Data", "routes", "URL");
    String Lookup_sectors_Path = testDataReader.getCellData("API_Data", "sectors", "URL");
    String Lookup_terminals_Path = testDataReader.getCellData("API_Data", "terminals", "URL");
    String Lookup_ports_Path = testDataReader.getCellData("API_Data", "ports", "URL");
    String Lookup_vehicleTypes_Path = testDataReader.getCellData("API_Data", "vehicleTypes", "URL");
    String Lookup_vehicleCategories_Path = testDataReader.getCellData("API_Data", "vehicleCategories", "URL");

    //////////////////////1////////////////////////////////////////////////
    Response Lookups_Response;
    SHAFT.API Lookups_api;

    public void GET_Valid_all_Lookups_Rq(String TokenValue, String LookupsType) {
        Lookups_api = new SHAFT.API(BaseURL);
        switch (LookupsType) {
            case "cities" ->
                    Lookups_api.get(Lookup_cities_Path)
                            .setAuthentication("", "", AuthenticationType.NONE)
                            .setTargetStatusCode(200).addHeader("Authorization", "Bearer " + TokenValue).perform();
            case "routes" ->
                    Lookups_api.get(Lookup_routes_Path)
                            .setAuthentication("", "", AuthenticationType.NONE)
                            .setTargetStatusCode(200).addHeader("Authorization", "Bearer " + TokenValue).perform();
            case "hotels" ->
                    Lookups_api.get(Lookup_hotels_Path)
                            .setAuthentication("", "", AuthenticationType.NONE)
                            .setTargetStatusCode(200).addHeader("Authorization", "Bearer " + TokenValue).perform();
            case "sectors" ->
                    Lookups_api.get(Lookup_sectors_Path)
                            .setAuthentication("", "", AuthenticationType.NONE)
                            .setTargetStatusCode(200).addHeader("Authorization", "Bearer " + TokenValue).perform();
            case "terminals" ->
                    Lookups_api.get(Lookup_terminals_Path)
                            .setAuthentication("", "", AuthenticationType.NONE)
                            .setTargetStatusCode(200).addHeader("Authorization", "Bearer " + TokenValue).perform();
            case "ports" ->
                    Lookups_api.get(Lookup_ports_Path)
                            .setAuthentication("", "", AuthenticationType.NONE)
                            .setTargetStatusCode(200).addHeader("Authorization", "Bearer " + TokenValue).perform();
            case "vehicleTypes" ->
                    Lookups_api.get(Lookup_vehicleTypes_Path)
                            .setAuthentication("", "", AuthenticationType.NONE)
                            .setTargetStatusCode(200).addHeader("Authorization", "Bearer " + TokenValue).perform();
            case "vehicleCategories" ->
                    Lookups_api.get(Lookup_vehicleCategories_Path)
                            .setAuthentication("", "", AuthenticationType.NONE)
                            .setTargetStatusCode(200).addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        Lookups_Response = Lookups_api.getResponse();
    }

    public void GET_Valid_all_Lookups_by_parameter_Query_Rq(String TokenValue, String LookupsType,String PageSize, String PageNumber) {
        Lookups_api = new SHAFT.API(BaseURL);
        List<List<Object>> parameters = Arrays.asList(Arrays.asList("size", PageSize), Arrays.asList("page", PageNumber));
        switch (LookupsType) {
            case "cities" ->
                    Lookups_api.get(Lookup_cities_Path)
                            .setAuthentication("", "", AuthenticationType.NONE)
                            .setTargetStatusCode(200).setParameters(parameters, RestActions.ParametersType.QUERY)
                            .addHeader("Authorization", "Bearer " + TokenValue).perform();
            case "routes" ->
                    Lookups_api.get(Lookup_routes_Path)
                            .setAuthentication("", "", AuthenticationType.NONE)
                            .setTargetStatusCode(200).setParameters(parameters, RestActions.ParametersType.QUERY)
                            .addHeader("Authorization", "Bearer " + TokenValue).perform();
            case "hotels" ->
                    Lookups_api.get(Lookup_hotels_Path)
                            .setAuthentication("", "", AuthenticationType.NONE)
                            .setTargetStatusCode(200).setParameters(parameters, RestActions.ParametersType.QUERY)
                            .addHeader("Authorization", "Bearer " + TokenValue).perform();
            case "sectors" ->
                    Lookups_api.get(Lookup_sectors_Path)
                            .setAuthentication("", "", AuthenticationType.NONE)
                            .setTargetStatusCode(200).setParameters(parameters, RestActions.ParametersType.QUERY)
                            .addHeader("Authorization", "Bearer " + TokenValue).perform();
            case "terminals" ->
                    Lookups_api.get(Lookup_terminals_Path)
                            .setAuthentication("", "", AuthenticationType.NONE)
                            .setTargetStatusCode(200).setParameters(parameters, RestActions.ParametersType.QUERY)
                            .addHeader("Authorization", "Bearer " + TokenValue).perform();
            case "ports" ->
                    Lookups_api.get(Lookup_ports_Path)
                            .setAuthentication("", "", AuthenticationType.NONE)
                            .setTargetStatusCode(200).setParameters(parameters, RestActions.ParametersType.QUERY)
                            .addHeader("Authorization", "Bearer " + TokenValue).perform();
            case "vehicleTypes" ->
                    Lookups_api.get(Lookup_vehicleTypes_Path)
                            .setAuthentication("", "", AuthenticationType.NONE)
                            .setTargetStatusCode(200).setParameters(parameters, RestActions.ParametersType.QUERY)
                            .addHeader("Authorization", "Bearer " + TokenValue).perform();
            case "vehicleCategories" ->
                    Lookups_api.get(Lookup_vehicleCategories_Path)
                            .setAuthentication("", "", AuthenticationType.NONE)
                            .setTargetStatusCode(200).setParameters(parameters, RestActions.ParametersType.QUERY)
                            .addHeader("Authorization", "Bearer " + TokenValue).perform();
        }

//        Lookups_api.get(Lookup_cities_Path)
//                .setAuthentication("", "", AuthenticationType.NONE)
//                .setTargetStatusCode(200).setParameters(parameters, RestActions.ParametersType.QUERY)
//                .addHeader("Authorization", "Bearer " + TokenValue).perform();


        Lookups_Response = Lookups_api.getResponse();
    }
    public void GET_all_Lookups_With_Missing_Token_Rq(String LookupsType) {
        Lookups_api = new SHAFT.API(BaseURL);
        switch (LookupsType) {
            case "cities" ->
                    Lookups_api.get(Lookup_cities_Path)
                            .setAuthentication("", "", AuthenticationType.NONE)
                            .setTargetStatusCode(401)
                           // .addHeader("Authorization", "Bearer ")
                            .perform();
            case "routes" ->
                    Lookups_api.get(Lookup_routes_Path)
                            .setAuthentication("", "", AuthenticationType.NONE)
                            .setTargetStatusCode(401)
                           // .addHeader("Authorization", "Bearer ")
                            .perform();
            case "hotels" ->
                    Lookups_api.get(Lookup_hotels_Path)
                            .setAuthentication("", "", AuthenticationType.NONE)
                            .setTargetStatusCode(401)
                           // .addHeader("Authorization", "Bearer ")
                            .perform();
            case "sectors" ->
                    Lookups_api.get(Lookup_sectors_Path)
                            .setAuthentication("", "", AuthenticationType.NONE)
                            .setTargetStatusCode(401)
                           // .addHeader("Authorization", "Bearer ")
                            .perform();
            case "terminals" ->
                    Lookups_api.get(Lookup_terminals_Path)
                            .setAuthentication("", "", AuthenticationType.NONE)
                            .setTargetStatusCode(401)
                           // .addHeader("Authorization", "Bearer ")
                            .perform();
            case "ports" ->
                    Lookups_api.get(Lookup_ports_Path)
                            .setAuthentication("", "", AuthenticationType.NONE)
                            .setTargetStatusCode(401)
                           // .addHeader("Authorization", "Bearer ")
                            .perform();
            case "vehicleTypes" ->
                    Lookups_api.get(Lookup_vehicleTypes_Path)
                            .setAuthentication("", "", AuthenticationType.NONE)
                            .setTargetStatusCode(401)
                           // .addHeader("Authorization", "Bearer ")
                            .perform();
            case "vehicleCategories" ->
                    Lookups_api.get(Lookup_vehicleCategories_Path)
                            .setAuthentication("", "", AuthenticationType.NONE)
                            .setTargetStatusCode(401)
                           // .addHeader("Authorization", "Bearer ")
                            .perform();
        }
        Lookups_Response = Lookups_api.getResponse();
    }
    public void GET_all_Lookups_With_InValid_Token_Rq(String TokenValue, String LookupsType) {
        // String Lookup_cities_Path = "/lookups/cities";

        Lookups_api = new SHAFT.API(BaseURL);
        switch (LookupsType) {
            case "cities" ->
                    Lookups_api.get(Lookup_cities_Path)
                            .setAuthentication("", "", AuthenticationType.NONE)
                            .setTargetStatusCode(401).addHeader("Authorization", "Bearer " + TokenValue).perform();
            case "routes" ->
                    Lookups_api.get(Lookup_routes_Path)
                            .setAuthentication("", "", AuthenticationType.NONE)
                            .setTargetStatusCode(401).addHeader("Authorization", "Bearer " + TokenValue).perform();
            case "hotels" ->
                    Lookups_api.get(Lookup_hotels_Path)
                            .setAuthentication("", "", AuthenticationType.NONE)
                            .setTargetStatusCode(401).addHeader("Authorization", "Bearer " + TokenValue).perform();
            case "sectors" ->
                    Lookups_api.get(Lookup_sectors_Path)
                            .setAuthentication("", "", AuthenticationType.NONE)
                            .setTargetStatusCode(401).addHeader("Authorization", "Bearer " + TokenValue).perform();
            case "terminals" ->
                    Lookups_api.get(Lookup_terminals_Path)
                            .setAuthentication("", "", AuthenticationType.NONE)
                            .setTargetStatusCode(401).addHeader("Authorization", "Bearer " + TokenValue).perform();
            case "ports" ->
                    Lookups_api.get(Lookup_ports_Path)
                            .setAuthentication("", "", AuthenticationType.NONE)
                            .setTargetStatusCode(401).addHeader("Authorization", "Bearer " + TokenValue).perform();
            case "vehicleTypes" ->
                    Lookups_api.get(Lookup_vehicleTypes_Path)
                            .setAuthentication("", "", AuthenticationType.NONE)
                            .setTargetStatusCode(401).addHeader("Authorization", "Bearer " + TokenValue).perform();
            case "vehicleCategories" ->
                    Lookups_api.get(Lookup_vehicleCategories_Path)
                            .setAuthentication("", "", AuthenticationType.NONE)
                            .setTargetStatusCode(401).addHeader("Authorization", "Bearer " + TokenValue).perform();
        }

        Lookups_Response = Lookups_api.getResponse();
    }
    public void Check_Valid_Lookups_status_Code_Response() {
        SHAFT.Validations.assertThat().number(Lookups_Response.getStatusCode()).isEqualTo(200).perform();
    }
    public void Check_Lookups_status_Code_Unauthorized_Response() {
        SHAFT.Validations.assertThat().number(Lookups_Response.getStatusCode()).isEqualTo(401).perform();
    }
    public void Check_Valid_Lookups_Response_Time() {
        SHAFT.Validations.verifyThat().number(Lookups_Response.getTime()).isGreaterThanOrEquals(1.1).perform();
        SHAFT.Validations.verifyThat().number(Lookups_Response.getTime()).isLessThanOrEquals(10000).perform();
    }
    public void Check_Lookups_Valid_Content() {
        String Lookups_ResponseBody = Lookups_Response.getBody().asString();
        SHAFT.Validations.assertThat().object(Lookups_ResponseBody).contains("content").perform();
        Lookups_api.assertThatResponse().extractedJsonValue("content").isNotNull().withCustomReportMessage("Check that content object is not null.").perform();
    }
    public void Check_Lookups_Response_Valid_Schema(String LookupsType) {
        switch (LookupsType) {
            case "cities" ->
                    Lookups_api.assertThatResponse().matchesSchema(testDataReader.getCellData("API_Data", "cities", "Valid_Schema")).perform();

            case "routes" ->
                    Lookups_api.assertThatResponse().matchesSchema(testDataReader.getCellData("API_Data", "routes", "Valid_Schema")).perform();

            case "hotels" ->
                    Lookups_api.assertThatResponse().matchesSchema(testDataReader.getCellData("API_Data", "hotels", "Valid_Schema")).perform();

            case "sectors" ->
                    Lookups_api.assertThatResponse().matchesSchema(testDataReader.getCellData("API_Data", "sectors", "Valid_Schema")).perform();

            case "terminals" ->
                    Lookups_api.assertThatResponse().matchesSchema(testDataReader.getCellData("API_Data", "terminals", "Valid_Schema")).perform();

            case "ports" ->
                    Lookups_api.assertThatResponse().matchesSchema(testDataReader.getCellData("API_Data", "ports", "Valid_Schema")).perform();

            case "vehicleTypes" ->
                    Lookups_api.assertThatResponse().matchesSchema(testDataReader.getCellData("API_Data", "vehicleTypes", "Valid_Schema")).perform();

            case "vehicleCategories" ->
                    Lookups_api.assertThatResponse().matchesSchema(testDataReader.getCellData("API_Data", "vehicleCategories", "Valid_Schema")).perform();

        }

    }
    public void Check_Lookups_Response_Unauthorized_Schema() {
        Lookups_api.assertThatResponse().matchesSchema(testDataReader.getCellData("API_Data", "UnAuthorized", "URL")).perform();
    }


}