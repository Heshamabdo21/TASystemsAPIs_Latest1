/*
 * Copyright (c) 2023.
 */

package SteeringCompanyAPIs;

import com.shaft.api.RequestBuilder.AuthenticationType;
import com.shaft.api.RestActions;
import com.shaft.driver.SHAFT;
import com.shaft.tools.io.ExcelFileManager;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;


public class Commissions_API {
    ExcelFileManager testDataReader = new ExcelFileManager("SteeringCompanyAPI_TestData/SteeringCompanyAPI_TestData.xlsx");
    //ExtraExcelFun testDataReader2 = new ExtraExcelFun("SteeringCompanyAPI_TestData/SteeringCompanyAPI_TestData.xlsx");

    String BaseURL = testDataReader.getCellData("API_Data","Steering_Base_URL","URL");
    Response OTAs_Response;
    SHAFT.API OTAs_api;
    String OTAs_Path = testDataReader.getCellData("API_Data","GetOTAs","URL");
  /////////////////////////// Methods for Get All OTAs ///////////////////////
    public void Get_all_OTAs_Rq(String TokenValue) {
     //   String Lookup_policies_Path = "/policies";
    	 OTAs_api = new SHAFT.API(BaseURL); 
    	 OTAs_api.get(OTAs_Path).
    	setAuthentication("","", AuthenticationType.NONE).
        setTargetStatusCode(200).
    	addHeader("Authorization", "Bearer " + TokenValue).perform();     
         OTAs_Response = OTAs_api.getResponse();
    }
    public void GeT_all_OTAs_Path_by_parameter_Query_Rq(String TokenValue, String PageSize, String PageNumber) {
        //  String Lookup_policies_Path = "/policies";
        OTAs_api = new SHAFT.API(BaseURL);
        List<List<Object>> parameters = Arrays.asList(Arrays.asList("size", PageSize),
                Arrays.asList("page",PageNumber));
        OTAs_api.get(OTAs_Path).
                setAuthentication("", "", AuthenticationType.NONE).
                setTargetStatusCode(200).
                setParameters(parameters, RestActions.ParametersType.QUERY).
                addHeader("Authorization", "Bearer " + TokenValue).perform();
        OTAs_Response = OTAs_api.getResponse();

    }
    public void Get_InValid_all_OTAs_by_parameter_Query_Rq(String TokenValue, String PageSize, String PageNumber) {
        //  String Lookup_policies_Path = "/policies";
        OTAs_api = new SHAFT.API(BaseURL);
        List<List<Object>> parameters = Arrays.asList(Arrays.asList("size", PageSize),
                Arrays.asList("page",PageNumber));
        OTAs_api.get(OTAs_Path).
                setAuthentication("", "", AuthenticationType.NONE).
                setTargetStatusCode(422).
                setParameters(parameters, RestActions.ParametersType.QUERY).
                addHeader("Authorization", "Bearer " + TokenValue).perform();
        OTAs_Response = OTAs_api.getResponse();

    }
    public void GeT_all_OTAs_With_Missing_Token_Rq() {
        // String Lookup_cities_Path = "/lookups/cities";
        OTAs_api = new SHAFT.API(BaseURL);
        OTAs_api.get(OTAs_Path).
                setAuthentication("","", AuthenticationType.NONE).
                setTargetStatusCode(401).
                perform();
              //  addHeader("Authorization", "Bearer " ).perform();

        OTAs_Response = OTAs_api.getResponse();
    }
    public void GeT_all_OTAs_With_InValid_Token_Rq(String TokenValue) {
        // String Lookup_cities_Path = "/lookups/cities";

        OTAs_api = new SHAFT.API(BaseURL);
        OTAs_api.get(OTAs_Path).
                setAuthentication("","", AuthenticationType.NONE).
                setTargetStatusCode(401).
                addHeader("Authorization", "Bearer "+ TokenValue ).perform();

        OTAs_Response = OTAs_api.getResponse();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void Check_Valid_OTAs_status_Code_Response(){
        SHAFT.Validations.assertThat().number(OTAs_Response.getStatusCode()).isEqualTo(200).perform();
    }
    public void Check_Unauthorized_OTAs_status_Code_Response(){
        SHAFT.Validations.assertThat().number(OTAs_Response.getStatusCode()).isEqualTo(401).perform();
    }
    public void Check_Validation_Error_OTAs_status_Code_Response(){
        SHAFT.Validations.assertThat().number(OTAs_Response.getStatusCode()).isEqualTo(422).perform();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void Check_OTAs_Response_Time() {
        SHAFT.Validations.verifyThat().number(OTAs_Response.getTime()).isGreaterThanOrEquals(1.1).perform();
        SHAFT.Validations.verifyThat().number(OTAs_Response.getTime()).isLessThanOrEquals(10000).perform();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void Check_all_OTAs_Valid_Content() {
        String OTAs_ResponseBody = OTAs_Response.getBody().asString();
        SHAFT.Validations.assertThat().object(OTAs_ResponseBody).contains("content").perform();
        OTAs_api.assertThatResponse().extractedJsonValue("content").isNotNull().withCustomReportMessage("Check that content object is not null.").perform();
    }
    public void Check_Get_all_OTAs_Content(String ExpectedResult) {
        //String Commissions_ResponseBody = Commissions_Response.getBody().asString();
        OTAs_api.assertThatResponse().body().contains(ExpectedResult).
                withCustomReportMessage("Check that content object contains : "+ExpectedResult).
                perform();
        }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void Check_Get_all_OTAs_Response_Valid_Schema() {
        OTAs_api.assertThatResponse().matchesSchema(testDataReader.getCellData("API_Data","GetOTAs","Valid_Schema")).perform();
    }
    public void Check_Get_all_OTAs_Response_Unauthorized_Schema() {
        OTAs_api.assertThatResponse().matchesSchema(testDataReader.getCellData("API_Data","UnAuthorized","URL")).perform();
    }
    public void Check_Get_all_OTAs_Response_Validation_Error_Schema() {
        OTAs_api.assertThatResponse().matchesSchema(testDataReader.getCellData("API_Data","Validation Error","URL")).perform();
    }

    /////////////////////// Add  Commissions Methods ///////////////////
    String Commissions_Path = testDataReader.getCellData("API_Data","GetCommission","URL");
    public Response Commissions_Response;
    SHAFT.API Commissions_api;
    ////////////////////////////////Add Commissions/////////////////////////////////
    public void Add_Commissions_Rq(@NotNull String TokenValue, @NotNull Object[] data) {
        Commissions_api = new SHAFT.API(BaseURL);

        String Add_CommissionsBody ="{\n"+
                "    \"otaCode\": \""+data[2]+"\",\n" +
                "    \"addCommission\": \""+data[3]+"\"\n" +
                "}";
        Commissions_api.post(Commissions_Path).
                setRequestBody(Add_CommissionsBody).
                setTargetStatusCode(200).
                setContentType(ContentType.JSON).
                setAuthentication("","", AuthenticationType.NONE).
                addHeader("Authorization", "Bearer " + TokenValue).perform();
     /*   try {
            testDataReader2.AddExpectedResult("Commission_TestData", data[0].toString(), "ExpectedResult", "\"addCommission\":\""+data[3]+"\"");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
        Commissions_Response = Commissions_api.getResponse();
    }
    public void Add_Commissions_With_Missing_Token_Rq( @NotNull Object[] data) {
        Commissions_api = new SHAFT.API(BaseURL);
            String Add_CommissionsBody ="{\n"+
                    "    \"otaCode\": \""+data[2]+"\",\n" +
                    "    \"addCommission\": \""+data[3]+"\"\n" +
                    "}";
            Commissions_api.post(Commissions_Path).
                    setRequestBody(Add_CommissionsBody).
                    setTargetStatusCode(401).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).perform();
                   // addHeader("Authorization", "Bearer " + TokenValue).
        Commissions_Response = Commissions_api.getResponse();
    }
    public void Add_Commissions_With_InValid_Token_Rq(@NotNull String TokenValue, @NotNull Object[] data) {
        Commissions_api = new SHAFT.API(BaseURL);
            String Add_CommissionsBody ="{\n"+
                    "    \"otaCode\": \""+data[2]+"\",\n" +
                    "    \"addCommission\": \""+data[3]+"\"\n" +
                    "}";
            Commissions_api.post(Commissions_Path).
                    setRequestBody(Add_CommissionsBody).
                    setTargetStatusCode(401).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        Commissions_Response = Commissions_api.getResponse();
    }
    public void Add_Commissions_With_Invalid_Input_Rq(@NotNull String TokenValue, @NotNull Object[] data) {
       // Random random=new Random();
        //int x=random.nextInt(10000);
        Commissions_api = new SHAFT.API(BaseURL);
            String Add_CommissionsBody ="{\n"+
                    "    \"otaCode\": \""+data[2]+"\",\n" +
                    "    \"addCommission\": \""+data[3]+"\"\n" +
                    "}";
            Commissions_api.post(Commissions_Path).
                    setRequestBody(Add_CommissionsBody).
                    setTargetStatusCode(422).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();


        Commissions_Response = Commissions_api.getResponse();
     /*  try {
            testDataReader2.AddExpectedResult("Commission_TestData", data[0].toString(), "ExpectedResult",Commissions_Response.getBody().asString() );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
    }
    public void Add_Commissions_With_NotAccepted_Input_Rq(String TokenValue, Object[] data)  {
        // Random random=new Random();
        //int x=random.nextInt(10000);
        Commissions_api = new SHAFT.API(BaseURL);
            String Add_Cancel_CommissionsBody="{\n"+
                    "    \"otaCode\": \""+data[2]+"\",\n" +
                    "    \"addCommission\": \""+data[3]+"\"\n" +
                    "}";
            Commissions_api.post(Commissions_Path).
                    setRequestBody(Add_Cancel_CommissionsBody).
                    setTargetStatusCode(406).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        Commissions_Response = Commissions_api.getResponse();
      /*        try {
            testDataReader2.AddExpectedResult("Commission_TestData", data[0].toString(), "ExpectedResult",Commissions_Response.getBody().asString() );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
    }
    public void Add_Commissions_With_NotFound_Input_Rq(String TokenValue, Object[] data) {
        // Random random=new Random();
        //int x=random.nextInt(10000);
        Commissions_api = new SHAFT.API(BaseURL);
            String Add_CommissionsBody ="{\n"+
                    "    \"otaCode\": \""+data[2]+"\",\n" +
                    "    \"addCommission\": \""+data[3]+"\"\n" +
                    "}";
            Commissions_api.post(Commissions_Path).
                    setRequestBody(Add_CommissionsBody).
                    setTargetStatusCode(404).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        Commissions_Response = Commissions_api.getResponse();
          /*         try {
            testDataReader2.AddExpectedResult("Commission_TestData", data[0].toString(), "ExpectedResult",Commissions_Response.getBody().asString() );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
    }
    ////////////////////////////////GET Commission By ID/////////////////////////////////
    public void Get_Commissions_ById_Rq(@NotNull String TokenValue, @NotNull Object[] data) {
       // Random random=new Random();
       // int x=random.nextInt(10000);
        Commissions_api = new SHAFT.API(BaseURL);
        Commissions_api.get(Commissions_Path +"/"+data[2]).
        //          setRequestBody(GET_Cancel_CommissionsBody).
                    setTargetStatusCode(200).
                    setContentType(ContentType.ANY).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();

        Commissions_Response = Commissions_api.getResponse();
         /*                 try {
            testDataReader2.AddExpectedResult("Commission_TestData", data[0].toString(), "ExpectedResult",Commissions_Response.getBody().asString() );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
    }
    public void Get_Commissions_ById_With_Missing_Token_Rq( @NotNull Object[] data) {
        //Random random=new Random();
        //int x=random.nextInt(10000);
        Commissions_api = new SHAFT.API(BaseURL);
            Commissions_api.get(Commissions_Path +"/"+data[2]).
                    //          setRequestBody(GET_Cancel_CommissionsBody).
                            setTargetStatusCode(401).
                    setContentType(ContentType.ANY).
                    setAuthentication("","", AuthenticationType.NONE).perform();
              //      addHeader("Authorization", "Bearer " + TokenValue).perform();
        Commissions_Response = Commissions_api.getResponse();
    }
    public void Get_Commissions_ById_With_InValid_Token_Rq(@NotNull String TokenValue, @NotNull Object[] data) {
     //   Random random=new Random();
     //   int x=random.nextInt(10000);
        Commissions_api = new SHAFT.API(BaseURL);
            Commissions_api.get(Commissions_Path +"/"+data[2]).
                    //          setRequestBody(GET_Cancel_CommissionsBody).
                            setTargetStatusCode(401).
                    setContentType(ContentType.ANY).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        Commissions_Response = Commissions_api.getResponse();
    }
    public void Get_Commissions_ById_With_NotFound_Input_Rq(@NotNull String TokenValue, @NotNull Object[] data) {
       // Random random=new Random();
        //int x=random.nextInt(10000);
        Commissions_api = new SHAFT.API(BaseURL);
        if(data[2].toString().contains(" ")) {
            data[2]='\u2001';
        }

            Commissions_api.get(Commissions_Path +"/"+data[2]).
                    //          setRequestBody(GET_Cancel_CommissionsBody).
                            setTargetStatusCode(404).
                    setContentType(ContentType.ANY).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        Commissions_Response = Commissions_api.getResponse();
          /*                 try {
            testDataReader2.AddExpectedResult("Commission_TestData", data[0].toString(), "ExpectedResult",Commissions_Response.getBody().asString() );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/

    }
    public void Get_Commissions_ById_With_BadRequest_Input_Rq(@NotNull String TokenValue, @NotNull Object[] data) {
       // Random random=new Random();
       // int x=random.nextInt(10000);
        Commissions_api = new SHAFT.API(BaseURL);
            if(data[2].toString().contains(" ")) {
                data[2]='\u2001';
            }
            Commissions_api.get(Commissions_Path +"/"+ data[2]).
        //          setRequestBody(GET_Cancel_CommissionsBody).
                    setTargetStatusCode(400).
                    setContentType(ContentType.ANY).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).
                    perform();
        Commissions_Response = Commissions_api.getResponse();
    }
    /////////////////////////////////Get All Commissions////////////////////////////////////////////
    public void Get_all_Commissions_Rq(String TokenValue) {
        //   String Lookup_policies_Path = "/policies";
        Commissions_api = new SHAFT.API(BaseURL);
        Commissions_api.get(Commissions_Path).
                setAuthentication("","", AuthenticationType.NONE).
                setTargetStatusCode(200).
                addHeader("Authorization", "Bearer " + TokenValue).perform();
        Commissions_Response = Commissions_api.getResponse();
    }
    public void Get_Commissions_by_parameter_Query_Rq(String TokenValue, String PageSize, String PageNumber) {
        //  String Lookup_policies_Path = "/policies";
        Commissions_api = new SHAFT.API(BaseURL);
        List<List<Object>> parameters = Arrays.asList(Arrays.asList("size", PageSize),
                Arrays.asList("page",PageNumber));
        Commissions_api.get(Commissions_Path).
                setAuthentication("", "", AuthenticationType.NONE).
                setTargetStatusCode(200).
                setParameters(parameters, RestActions.ParametersType.QUERY).
                addHeader("Authorization", "Bearer " + TokenValue).perform();
        Commissions_Response = Commissions_api.getResponse();

    }
    public void Get_InValid_Commissions_by_parameter_Query_Rq(String TokenValue, String PageSize, String PageNumber) {
        //  String Lookup_policies_Path = "/policies";
        Commissions_api = new SHAFT.API(BaseURL);
        List<List<Object>> parameters = Arrays.asList(Arrays.asList("size", PageSize),
                Arrays.asList("page",PageNumber));
        Commissions_api.get(Commissions_Path).
                setAuthentication("", "", AuthenticationType.NONE).
                setTargetStatusCode(422).
                setParameters(parameters, RestActions.ParametersType.QUERY).
                addHeader("Authorization", "Bearer " + TokenValue).perform();
        Commissions_Response = Commissions_api.getResponse();

    }
    public void Get_all_Commissions_With_Missing_Token_Rq() {
        // String Lookup_cities_Path = "/lookups/cities";
        Commissions_api = new SHAFT.API(BaseURL);
        Commissions_api.get(Commissions_Path).
                setAuthentication("","", AuthenticationType.NONE).
                setTargetStatusCode(401).
                perform();
        //  addHeader("Authorization", "Bearer " ).perform();

        Commissions_Response = Commissions_api.getResponse();
    }
    public void Get_all_Commissions_With_InValid_Token_Rq(String TokenValue) {
        // String Lookup_cities_Path = "/lookups/cities";

        Commissions_api = new SHAFT.API(BaseURL);
        Commissions_api.get(Commissions_Path).
                setAuthentication("","", AuthenticationType.NONE).
                setTargetStatusCode(401).
                addHeader("Authorization", "Bearer "+ TokenValue ).perform();

        Commissions_Response = Commissions_api.getResponse();
    }
    ///////////////////Status Code////////////////////////////
    public void Check_Valid_Commissions_status_Code_Response(){
        SHAFT.Validations.assertThat().number(Commissions_Response.getStatusCode()).isEqualTo(200).perform();
    }
    public void Check_Validation_Error_Commissions_status_Code_Response(){
        SHAFT.Validations.assertThat().number(Commissions_Response.getStatusCode()).isEqualTo(422).perform();
    }
    public void Check_Unauthorized_Commissions_status_Code_Response(){
        SHAFT.Validations.assertThat().number(Commissions_Response.getStatusCode()).isEqualTo(401).perform();
    }
    public void Check_Validation_NotAccepted_Commissions_status_Code_Response() {
        SHAFT.Validations.assertThat().number(Commissions_Response.getStatusCode()).isEqualTo(406).perform();

    }
    public void Check_Validation_NotFound_Commissions_status_Code_Response() {
        SHAFT.Validations.assertThat().number(Commissions_Response.getStatusCode()).isEqualTo(404).perform();

    }
    public void Check_Validation_BadRequest_Commissions_status_Code_Response() {
        SHAFT.Validations.assertThat().number(Commissions_Response.getStatusCode()).isEqualTo(400).perform();

    }
    /////////////////////////////Schema//////////////////////////////////////
    public void Check_Add_Commissions_Response_Valid_Schema() {
                    Commissions_api.assertThatResponse().matchesSchema(testDataReader.getCellData("API_Data", "AddCommission", "Valid_Schema")).perform();
    }
    public void Check_Get_all_Commissions_Response_Valid_Schema() {
        Commissions_api.assertThatResponse().matchesSchema(testDataReader.getCellData("API_Data", "GetCommission", "Valid_Schema")).perform();
    }
    public void Check_Get_Commissions_ByID_Response_Valid_Schema() {
        Commissions_api.assertThatResponse().matchesSchema(testDataReader.getCellData("API_Data", "GetCommissionById", "Valid_Schema")).perform();
    }
    public void Check_Commissions_Response_Time() {
        SHAFT.Validations.verifyThat().number(Commissions_Response.getTime()).isGreaterThanOrEquals(1.1).perform();
        SHAFT.Validations.verifyThat().number(Commissions_Response.getTime()).isLessThanOrEquals(30000).perform();
    }
    public void Check_Commissions_Response_Unauthorized_Schema() {
        Commissions_api.assertThatResponse().matchesSchema(testDataReader.getCellData("API_Data","UnAuthorized","URL")).perform();
    }
    public void Check_Commissions_Response_Validation_Error_Schema() {
        Commissions_api.assertThatResponse().matchesSchema(testDataReader.getCellData("API_Data","Validation Error","URL")).perform();
    }
    public void Check_Commissions_Response_NotAccepted_Error_Schema() {
        Commissions_api.assertThatResponse().matchesSchema(testDataReader.getCellData("API_Data","Not Accepted","URL")).perform();
    }
    public void Check_Commissions_Response_NotFound_Error_Schema() {
        Commissions_api.assertThatResponse().matchesSchema(testDataReader.getCellData("API_Data", "Not Found", "URL")).perform();
    }
    public void Check_Commissions_Response_BadRequest_Error_Schema() {
        Commissions_api.assertThatResponse().matchesSchema(testDataReader.getCellData("API_Data", "Bad Request", "URL")).perform();
    }

    //////////////////////////////content/////////////////////////////////////
    public void Check_Commissions_Content(String ExpectedResult) {
        //String Commissions_ResponseBody = Commissions_Response.getBody().asString();
        Commissions_api.assertThatResponse().body().contains(ExpectedResult).
               withCustomReportMessage("Check that content object contains : "+ExpectedResult).
                perform();
       // SHAFT.Validations.assertThat().object(Commissions_ResponseBody.contains(ExpectedResult)).isTrue().perform();
        //SHAFT.Validations.assertThat().object(Commissions_ResponseBody).contains(ExpectedResult).withCustomReportMessage("Check that content object contains"+ExpectedResult).perform();
    }
    public void Check_Commissions_Valid_Content() {
        String Commissions_api_ResponseBody = Commissions_Response.getBody().asString();
        SHAFT.Validations.assertThat().object(Commissions_api_ResponseBody).contains("otaCode").perform();
        Commissions_api.assertThatResponse().extractedJsonValue("otaCode").isNotNull().withCustomReportMessage("Check that otaCode object is not null.").perform();
    }
    public void Check_all_Commissions_Valid_Content() {
        String Commissions_api_ResponseBody = Commissions_Response.getBody().asString();
        SHAFT.Validations.assertThat().object(Commissions_api_ResponseBody).contains("content").perform();
        Commissions_api.assertThatResponse().extractedJsonValue("content").isNotNull().withCustomReportMessage("Check that content object is not null.").perform();
    }

}