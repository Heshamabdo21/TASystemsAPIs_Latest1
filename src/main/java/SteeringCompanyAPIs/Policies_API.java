/*
 * Copyright (c) 2023.
 */

package SteeringCompanyAPIs;

import com.shaft.driver.SHAFT;
import com.shaft.tools.io.ExcelFileManager;
import com.shaft.api.*;
import com.shaft.api.RequestBuilder.AuthenticationType;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import Utils.ExtraExcelFun;


public class Policies_API {
    ExcelFileManager testDataReader = new ExcelFileManager("SteeringCompanyAPI_TestData/SteeringCompanyAPI_TestData.xlsx");
    ExtraExcelFun testDataReader2 = new ExtraExcelFun("SteeringCompanyAPI_TestData/SteeringCompanyAPI_TestData.xlsx");

    String BaseURL = testDataReader.getCellData("API_Data","Steering_Base_URL","URL");
    Response All_Policies_Response;
    SHAFT.API All_Policies_api;
   // String All_Policies_Path = "/policies";
    String All_Policies_Path = testDataReader.getCellData("API_Data","GetAllPolicies","URL");
  /////////////////////////// Methods for Get All Policies ///////////////////////
    public void GET_All_Policies_Rq(String TokenValue) {
     //   String Lookup_policies_Path = "/policies";
    	 All_Policies_api = new SHAFT.API(BaseURL); 
    	 All_Policies_api.get(All_Policies_Path).
    	setAuthentication("","", AuthenticationType.NONE).
    	addHeader("Authorization", "Bearer " + TokenValue).perform();     
         All_Policies_Response = All_Policies_api.getResponse();
    }
    public void GET_All_Policies_Path_by_parameter_Query_Rq(String TokenValue, String PageSize, String PageNumber) {
        //  String Lookup_policies_Path = "/policies";
        All_Policies_api = new SHAFT.API(BaseURL);
        List<List<Object>> parameters = Arrays.asList(Arrays.asList("size", PageSize),
                Arrays.asList("page",PageNumber));
        All_Policies_api.get(All_Policies_Path).
                setAuthentication("", "", AuthenticationType.NONE).
                setParameters(parameters, RestActions.ParametersType.QUERY).
                addHeader("Authorization", "Bearer " + TokenValue).perform();
        All_Policies_Response = All_Policies_api.getResponse();

    }
    public void GET_All_Policies_With_Missing_Token_Rq() {
        // String Lookup_cities_Path = "/lookups/cities";
        All_Policies_api = new SHAFT.API(BaseURL);
        All_Policies_api.get(All_Policies_Path).
                setAuthentication("","", AuthenticationType.NONE).
                setTargetStatusCode(401).
                perform();
              //  addHeader("Authorization", "Bearer " ).perform();

        All_Policies_Response = All_Policies_api.getResponse();
    }
    public void GET_All_Policies_With_InValid_Token_Rq(String TokenValue) {
        // String Lookup_cities_Path = "/lookups/cities";

        All_Policies_api = new SHAFT.API(BaseURL);
        All_Policies_api.get(All_Policies_Path).
                setAuthentication("","", AuthenticationType.NONE).
                setTargetStatusCode(401).
                addHeader("Authorization", "Bearer "+ TokenValue ).perform();

        All_Policies_Response = All_Policies_api.getResponse();
    }
    public void Check_Valid_policies_status_Code_Response(){
        SHAFT.Validations.assertThat().number(All_Policies_Response.getStatusCode()).isEqualTo(200).perform();
    }
    public void Check_Unauthorized_Policies_status_Code_Response(){
        SHAFT.Validations.assertThat().number(All_Policies_Response.getStatusCode()).isEqualTo(401).perform();
    }
    public void Check_policies_Response_Time() {
        SHAFT.Validations.verifyThat().number(All_Policies_Response.getTime()).isGreaterThanOrEquals(1.1).perform();
        SHAFT.Validations.verifyThat().number(All_Policies_Response.getTime()).isLessThanOrEquals(10000).perform();
    }
    public void Check_All_policies_Valid_Content() {
        String Lookups_policies_ResponseBody = All_Policies_Response.getBody().asString();
        SHAFT.Validations.assertThat().object(Lookups_policies_ResponseBody).contains("cancellationPolicies").perform();
        All_Policies_api.assertThatResponse().extractedJsonValue("cancellationPolicies").isNotNull().withCustomReportMessage("Check that cancellationPolicies object is not null.").perform();
    }
    public void Check_All_policies_Response_Valid_Schema() {
        All_Policies_api.assertThatResponse().matchesSchema(testDataReader.getCellData("API_Data","GetAllPolicies","Valid_Schema")).perform();
    }
    public void Check_All_policies_Response_Unauthorized_Schema() {
        All_Policies_api.assertThatResponse().matchesSchema(testDataReader.getCellData("API_Data","UnAuthorized","URL")).perform();
    }

    /////////////////////// Add  Policy Methods ///////////////////
    String Cancel_Policy_Path = testDataReader.getCellData("API_Data","AddCancellationPolicies","URL");
    String Tax_Policy_Path = testDataReader.getCellData("API_Data","AddTaxPolicies","URL");
    String Payment_Policy_Path = testDataReader.getCellData("API_Data","AddPaymentPolicies","URL");
    String General_Policy_Path = testDataReader.getCellData("API_Data","AddGeneralPolicies","URL");
    String Usage_Policy_Path = testDataReader.getCellData("API_Data","AddUsagePolicies","URL");

    public Response Policy_Response;
    SHAFT.API Policy_api;
    ////////////////////////////////Add Policy/////////////////////////////////
    public void Add_Policy_Rq(@NotNull String TokenValue, @NotNull Object[] data) {
        Random random=new Random();
        int x=random.nextInt(10000);
        Policy_api = new SHAFT.API(BaseURL);
        if(data[1].equals("Cancel"))
        {
        String Add_Cancel_PolicyBody="{\n"+
                "    \"nameArabic\": \""+data[2]+x+"\",\n" +
                "    \"nameEnglish\": \""+data[3]+x+"\",\n" +
                "    \"descriptionArabic\": \""+data[4]+x+"\",\n" +
                "    \"descriptionEnglish\": \""+data[5]+x+"\",\n" +
                "    \"chargeUnit\": \""+data[6]+"\",\n" +
                "    \"deadline\": \""+data[7]+"\",\n" +
                "    \"chargeType\": \""+data[8]+"\",\n" +
                "    \"chargeValue\": \""+data[9]+"\",\n" +
                "    \"id\": \""+data[10]+"\"\n"+
                "}";
        Policy_api.post(Cancel_Policy_Path).
                setRequestBody(Add_Cancel_PolicyBody).
                setTargetStatusCode(201).
                setContentType(ContentType.JSON).
                setAuthentication("","", AuthenticationType.NONE).
                addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("Payment"))
        {
            String Add_Payment_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+x+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+x+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+x+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+x+"\",\n" +
                    "    \"refundType\": \""+data[6]+"\",\n" +
                    "    \"cancellationPolicyId\": \""+data[7]+"\",\n" +
                    "    \"id\": \""+data[8]+"\"\n"+
                    "}";
            Policy_api.post(Payment_Policy_Path).
                    setRequestBody(Add_Payment_PolicyBody).
                    setTargetStatusCode(201).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("Tax"))
        {
            String Add_Tax_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+x+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+x+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+x+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+x+"\",\n" +
                    "    \"chargeType\": \""+data[6]+"\",\n" +
                    "    \"chargeValue\": \""+data[7]+"\",\n" +
                    "    \"id\": \""+data[8]+"\"\n"+
                    "}";
            Policy_api.post(Tax_Policy_Path).
                    setRequestBody(Add_Tax_PolicyBody).
                    setTargetStatusCode(201).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("Usage"))
        {
            String Add_Usage_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+x+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+x+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+x+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+x+"\",\n" +
                    "    \"id\": \""+data[6]+"\"\n"+
                    "}";
            Policy_api.post(Usage_Policy_Path).
                    setRequestBody(Add_Usage_PolicyBody).
                    setTargetStatusCode(201).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("General"))
        {
            String Add_General_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+x+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+x+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+x+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+x+"\",\n" +
                    "    \"id\": \""+data[6]+"\"\n"+
                    "}";
            Policy_api.post(General_Policy_Path).
                    setRequestBody(Add_General_PolicyBody).
                    setTargetStatusCode(201).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        try {
            testDataReader2.AddExpectedResult("Policy_TestData", data[0].toString(), "ExpectedResult", "\"nameArabic\":\""+data[2]+x+"\"");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Policy_Response = Policy_api.getResponse();
    }
    public void Add_Policy_With_Missing_Token_Rq( @NotNull Object[] data) {
        Random random=new Random();
        int x=random.nextInt(10000);
        Policy_api = new SHAFT.API(BaseURL);
        if(data[1].equals("Cancel"))
        {
            String Add_Cancel_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+x+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+x+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+x+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+x+"\",\n" +
                    "    \"chargeUnit\": \""+data[6]+"\",\n" +
                    "    \"deadline\": \""+data[7]+"\",\n" +
                    "    \"chargeType\": \""+data[8]+"\",\n" +
                    "    \"chargeValue\": \""+data[9]+"\",\n" +
                    "    \"id\": \""+data[10]+"\"\n"+
                    "}";
            Policy_api.post(Cancel_Policy_Path).
                    setRequestBody(Add_Cancel_PolicyBody).
                    setTargetStatusCode(401).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).perform();
                   // addHeader("Authorization", "Bearer " + TokenValue).
        }
        else if (data[1].equals("Payment"))
        {
            String Add_Payment_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+x+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+x+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+x+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+x+"\",\n" +
                    "    \"refundType\": \""+data[6]+"\",\n" +
                    "    \"cancellationPolicyId\": \""+data[7]+"\",\n" +
                    "    \"id\": \""+data[8]+"\"\n"+
                    "}";
            Policy_api.post(Payment_Policy_Path).
                    setRequestBody(Add_Payment_PolicyBody).
                    setTargetStatusCode(401).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).perform();
                  //  addHeader("Authorization", "Bearer " + TokenValue)
        }
        else if (data[1].equals("Tax"))
        {
            String Add_Tax_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+x+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+x+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+x+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+x+"\",\n" +
                    "    \"chargeType\": \""+data[6]+"\",\n" +
                    "    \"chargeValue\": \""+data[7]+"\",\n" +
                    "    \"id\": \""+data[8]+"\"\n"+
                    "}";
            Policy_api.post(Tax_Policy_Path).
                    setRequestBody(Add_Tax_PolicyBody).
                    setTargetStatusCode(401).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).perform();
                   // addHeader("Authorization", "Bearer " + TokenValue)
        }
        else if (data[1].equals("Usage"))
        {
            String Add_Usage_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+x+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+x+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+x+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+x+"\",\n" +
                    "    \"id\": \""+data[6]+"\"\n"+
                    "}";
            Policy_api.post(Usage_Policy_Path).
                    setRequestBody(Add_Usage_PolicyBody).
                    setTargetStatusCode(401).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).perform();
                  //  addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("General"))
        {
            String Add_General_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+x+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+x+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+x+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+x+"\",\n" +
                    "    \"id\": \""+data[6]+"\"\n"+
                    "}";
            Policy_api.post(General_Policy_Path).
                    setRequestBody(Add_General_PolicyBody).
                    setTargetStatusCode(401).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).perform();
               //     addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        Policy_Response = Policy_api.getResponse();
    }
    public void Add_Policy_With_InValid_Token_Rq(@NotNull String TokenValue, @NotNull Object[] data) {
        Random random=new Random();
        int x=random.nextInt(10000);
        Policy_api = new SHAFT.API(BaseURL);
        if(data[1].equals("Cancel"))
        {
            String Add_Cancel_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+x+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+x+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+x+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+x+"\",\n" +
                    "    \"chargeUnit\": \""+data[6]+"\",\n" +
                    "    \"deadline\": \""+data[7]+"\",\n" +
                    "    \"chargeType\": \""+data[8]+"\",\n" +
                    "    \"chargeValue\": \""+data[9]+"\",\n" +
                    "    \"id\": \""+data[10]+"\"\n"+
                    "}";
            Policy_api.post(Cancel_Policy_Path).
                    setRequestBody(Add_Cancel_PolicyBody).
                    setTargetStatusCode(401).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("Payment"))
        {
            String Add_Payment_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+x+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+x+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+x+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+x+"\",\n" +
                    "    \"refundType\": \""+data[6]+"\",\n" +
                    "    \"cancellationPolicyId\": \""+data[7]+"\",\n" +
                    "    \"id\": \""+data[8]+"\"\n"+
                    "}";
            Policy_api.post(Payment_Policy_Path).
                    setRequestBody(Add_Payment_PolicyBody).
                    setTargetStatusCode(401).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("Tax"))
        {
            String Add_Tax_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+x+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+x+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+x+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+x+"\",\n" +
                    "    \"chargeType\": \""+data[6]+"\",\n" +
                    "    \"chargeValue\": \""+data[7]+"\",\n" +
                    "    \"id\": \""+data[8]+"\"\n"+
                    "}";
            Policy_api.post(Tax_Policy_Path).
                    setRequestBody(Add_Tax_PolicyBody).
                    setTargetStatusCode(401).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("Usage"))
        {
            String Add_Usage_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+x+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+x+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+x+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+x+"\",\n" +
                    "    \"id\": \""+data[6]+"\"\n"+
                    "}";
            Policy_api.post(Usage_Policy_Path).
                    setRequestBody(Add_Usage_PolicyBody).
                    setTargetStatusCode(401).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("General"))
        {
            String Add_General_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+x+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+x+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+x+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+x+"\",\n" +
                    "    \"id\": \""+data[6]+"\"\n"+
                    "}";
            Policy_api.post(General_Policy_Path).
                    setRequestBody(Add_General_PolicyBody).
                    setTargetStatusCode(401).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        Policy_Response = Policy_api.getResponse();
    }
    public void Add_Policy_With_Invalid_Input_Rq(@NotNull String TokenValue, @NotNull Object[] data) {
       // Random random=new Random();
        //int x=random.nextInt(10000);
        Policy_api = new SHAFT.API(BaseURL);
        if(data[1].equals("Cancel"))
        {
            String Add_Cancel_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+"\",\n" +
                    "    \"chargeUnit\": \""+data[6]+"\",\n" +
                    "    \"deadline\": \""+data[7]+"\",\n" +
                    "    \"chargeType\": \""+data[8]+"\",\n" +
                    "    \"chargeValue\": \""+data[9]+"\",\n" +
                    "    \"id\": \""+data[10]+"\"\n"+
                    "}";
            Policy_api.post(Cancel_Policy_Path).
                    setRequestBody(Add_Cancel_PolicyBody).
                    setTargetStatusCode(422).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("Payment"))
        {
            String Add_Payment_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+"\",\n" +
                    "    \"refundType\": \""+data[6]+"\",\n" +
                    "    \"cancellationPolicyId\": \""+data[7]+"\",\n" +
                    "    \"id\": \""+data[8]+"\"\n"+
                    "}";
            Policy_api.post(Payment_Policy_Path).
                    setRequestBody(Add_Payment_PolicyBody).
                    setTargetStatusCode(422).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("Tax"))
        {
            String Add_Tax_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+"\",\n" +
                    "    \"chargeType\": \""+data[6]+"\",\n" +
                    "    \"chargeValue\": \""+data[7]+"\",\n" +
                    "    \"id\": \""+data[8]+"\"\n"+
                    "}";
            Policy_api.post(Tax_Policy_Path).
                    setRequestBody(Add_Tax_PolicyBody).
                    setTargetStatusCode(422).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("Usage"))
        {
            String Add_Usage_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+"\",\n" +
                    "    \"id\": \""+data[6]+"\"\n"+
                    "}";
            Policy_api.post(Usage_Policy_Path).
                    setRequestBody(Add_Usage_PolicyBody).
                    setTargetStatusCode(422).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("General"))
        {
            String Add_General_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+"\",\n" +
                    "    \"id\": \""+data[6]+"\"\n"+
                    "}";
            Policy_api.post(General_Policy_Path).
                    setRequestBody(Add_General_PolicyBody).
                    setTargetStatusCode(422).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        Policy_Response = Policy_api.getResponse();
    }
    public void Add_Policy_With_NotAccepted_Input_Rq(String TokenValue, Object[] data)  {
        // Random random=new Random();
        //int x=random.nextInt(10000);
        Policy_api = new SHAFT.API(BaseURL);
        if(data[1].equals("Cancel"))
        {
            String Add_Cancel_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+"\",\n" +
                    "    \"chargeUnit\": \""+data[6]+"\",\n" +
                    "    \"deadline\": \""+data[7]+"\",\n" +
                    "    \"chargeType\": \""+data[8]+"\",\n" +
                    "    \"chargeValue\": \""+data[9]+"\",\n" +
                    "    \"id\": \""+data[10]+"\"\n"+
                    "}";
            Policy_api.post(Cancel_Policy_Path).
                    setRequestBody(Add_Cancel_PolicyBody).
                    setTargetStatusCode(406).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("Payment"))
        {
            String Add_Payment_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+"\",\n" +
                    "    \"refundType\": \""+data[6]+"\",\n" +
                    "    \"cancellationPolicyId\": \""+data[7]+"\",\n" +
                    "    \"id\": \""+data[8]+"\"\n"+
                    "}";
            Policy_api.post(Payment_Policy_Path).
                    setRequestBody(Add_Payment_PolicyBody).
                    setTargetStatusCode(406).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("Tax"))
        {
            String Add_Tax_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+"\",\n" +
                    "    \"chargeType\": \""+data[6]+"\",\n" +
                    "    \"chargeValue\": \""+data[7]+"\",\n" +
                    "    \"id\": \""+data[8]+"\"\n"+
                    "}";
            Policy_api.post(Tax_Policy_Path).
                    setRequestBody(Add_Tax_PolicyBody).
                    setTargetStatusCode(406).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("Usage"))
        {
            String Add_Usage_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+"\",\n" +
                    "    \"id\": \""+data[6]+"\"\n"+
                    "}";
            Policy_api.post(Usage_Policy_Path).
                    setRequestBody(Add_Usage_PolicyBody).
                    setTargetStatusCode(406).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("General"))
        {
            String Add_General_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+"\",\n" +
                    "    \"id\": \""+data[6]+"\"\n"+
                    "}";
            Policy_api.post(General_Policy_Path).
                    setRequestBody(Add_General_PolicyBody).
                    setTargetStatusCode(406).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        Policy_Response = Policy_api.getResponse();
    }
    public void Add_Policy_With_NotFound_Input_Rq(String TokenValue, Object[] data) {
        // Random random=new Random();
        //int x=random.nextInt(10000);
        Policy_api = new SHAFT.API(BaseURL);
        if(data[1].equals("Cancel"))
        {
            String Add_Cancel_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+"\",\n" +
                    "    \"chargeUnit\": \""+data[6]+"\",\n" +
                    "    \"deadline\": \""+data[7]+"\",\n" +
                    "    \"chargeType\": \""+data[8]+"\",\n" +
                    "    \"chargeValue\": \""+data[9]+"\",\n" +
                    "    \"id\": \""+data[10]+"\"\n"+
                    "}";
            Policy_api.post(Cancel_Policy_Path).
                    setRequestBody(Add_Cancel_PolicyBody).
                    setTargetStatusCode(404).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("Payment"))
        {
            String Add_Payment_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+"\",\n" +
                    "    \"refundType\": \""+data[6]+"\",\n" +
                    "    \"cancellationPolicyId\": \""+data[7]+"\",\n" +
                    "    \"id\": \""+data[8]+"\"\n"+
                    "}";
            Policy_api.post(Payment_Policy_Path).
                    setRequestBody(Add_Payment_PolicyBody).
                    setTargetStatusCode(404).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("Tax"))
        {
            String Add_Tax_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+"\",\n" +
                    "    \"chargeType\": \""+data[6]+"\",\n" +
                    "    \"chargeValue\": \""+data[7]+"\",\n" +
                    "    \"id\": \""+data[8]+"\"\n"+
                    "}";
            Policy_api.post(Tax_Policy_Path).
                    setRequestBody(Add_Tax_PolicyBody).
                    setTargetStatusCode(404).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("Usage"))
        {
            String Add_Usage_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+"\",\n" +
                    "    \"id\": \""+data[6]+"\"\n"+
                    "}";
            Policy_api.post(Usage_Policy_Path).
                    setRequestBody(Add_Usage_PolicyBody).
                    setTargetStatusCode(404).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("General"))
        {
            String Add_General_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+"\",\n" +
                    "    \"id\": \""+data[6]+"\"\n"+
                    "}";
            Policy_api.post(General_Policy_Path).
                    setRequestBody(Add_General_PolicyBody).
                    setTargetStatusCode(404).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        Policy_Response = Policy_api.getResponse();
    }

        ////////////////////////////////Update Policy/////////////////////////////////
    public void Update_Policy_Rq(@NotNull String TokenValue,  @NotNull Object[] data)  {
        Random random=new Random();
        int x=random.nextInt(10000);
        Policy_api = new SHAFT.API(BaseURL);
        if(data[1].equals("Cancel"))
        {
            String Update_Cancel_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+x+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+x+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+x+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+x+"\",\n" +
                    "    \"chargeUnit\": \""+data[6]+"\",\n" +
                    "    \"deadline\": \""+data[7]+"\",\n" +
                    "    \"chargeType\": \""+data[8]+"\",\n" +
                    "    \"chargeValue\": \""+data[9]+"\",\n" +
                    "    \"-id\": \""+data[10]+"\"\n"+
                    "}";
            Policy_api.put(Cancel_Policy_Path+"/"+data[10]).
                    setRequestBody(Update_Cancel_PolicyBody).
                    setTargetStatusCode(200).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("Payment"))
        {
            String Update_Payment_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+x+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+x+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+x+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+x+"\",\n" +
                    "    \"refundType\": \""+data[6]+"\",\n" +
                    "    \"cancellationPolicyId\": \""+data[7]+"\",\n" +
                    "    \"-id\": \""+data[8]+"\"\n"+
                    "}";
            Policy_api.put(Payment_Policy_Path+"/"+data[8]).
                    setRequestBody(Update_Payment_PolicyBody).
                    setTargetStatusCode(200).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("Tax"))
        {
            String Update_Tax_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+x+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+x+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+x+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+x+"\",\n" +
                    "    \"chargeType\": \""+data[6]+"\",\n" +
                    "    \"chargeValue\": \""+data[7]+"\",\n" +
                    "    \"-id\": \""+data[8]+"\"\n"+
                    "}";
            Policy_api.put(Tax_Policy_Path+"/"+data[8]).
                    setRequestBody(Update_Tax_PolicyBody).
                    setTargetStatusCode(200).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("Usage"))
        {
            String Update_Usage_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+x+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+x+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+x+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+x+"\",\n" +
                    "    \"-id\": \""+data[6]+"\"\n"+
                    "}";
            Policy_api.put(Usage_Policy_Path+"/"+data[6]).
                    setRequestBody(Update_Usage_PolicyBody).
                    setTargetStatusCode(200).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("General"))
        {
            String Update_General_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+x+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+x+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+x+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+x+"\",\n" +
                    "    \"-id\": \""+data[6]+"\"\n"+
                    "}";
            Policy_api.put(General_Policy_Path+"/"+data[6]).
                    setRequestBody(Update_General_PolicyBody).
                    setTargetStatusCode(200).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        try {
            testDataReader2.AddExpectedResult("Policy_TestData", data[0].toString(), "ExpectedResult", "\"nameArabic\":\""+data[2]+x+"\"");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Policy_Response = Policy_api.getResponse();
    }
    public void Update_Policy_With_Missing_Token_Rq( @NotNull Object[] data) {
        Random random=new Random();
        int x=random.nextInt(10000);
        Policy_api = new SHAFT.API(BaseURL);
        if(data[1].equals("Cancel"))
        {
            String Update_Cancel_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+x+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+x+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+x+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+x+"\",\n" +
                    "    \"chargeUnit\": \""+data[6]+"\",\n" +
                    "    \"deadline\": \""+data[7]+"\",\n" +
                    "    \"chargeType\": \""+data[8]+"\",\n" +
                    "    \"chargeValue\": \""+data[9]+"\",\n" +
                    "    \"-id\": \""+data[10]+"\"\n"+
                    "}";
            Policy_api.put(Cancel_Policy_Path+"/"+data[10]).
                    setRequestBody(Update_Cancel_PolicyBody).
                    setTargetStatusCode(401).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).perform();
                //    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("Payment"))
        {
            String Update_Payment_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+x+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+x+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+x+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+x+"\",\n" +
                    "    \"refundType\": \""+data[6]+"\",\n" +
                    "    \"cancellationPolicyId\": \""+data[7]+"\",\n" +
                    "    \"-id\": \""+data[8]+"\"\n"+
                    "}";
            Policy_api.put(Payment_Policy_Path+"/"+data[8]).
                    setRequestBody(Update_Payment_PolicyBody).
                    setTargetStatusCode(401).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).perform();
                 //   addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("Tax"))
        {
            String Update_Tax_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+x+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+x+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+x+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+x+"\",\n" +
                    "    \"chargeType\": \""+data[6]+"\",\n" +
                    "    \"chargeValue\": \""+data[7]+"\",\n" +
                    "    \"-id\": \""+data[8]+"\"\n"+
                    "}";
            Policy_api.put(Tax_Policy_Path+"/"+data[8]).
                    setRequestBody(Update_Tax_PolicyBody).
                    setTargetStatusCode(401).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).perform();
                   // addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("Usage"))
        {
            String Update_Usage_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+x+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+x+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+x+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+x+"\",\n" +
                    "    \"-id\": \""+data[6]+"\"\n"+
                    "}";
            Policy_api.put(Usage_Policy_Path+"/"+data[6]).
                    setRequestBody(Update_Usage_PolicyBody).
                    setTargetStatusCode(401).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).perform();
                    //addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("General"))
        {
            String Update_General_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+x+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+x+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+x+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+x+"\",\n" +
                    "    \"-id\": \""+data[6]+"\"\n"+
                    "}";
            Policy_api.put(General_Policy_Path+"/"+data[6]).
                    setRequestBody(Update_General_PolicyBody).
                    setTargetStatusCode(401).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).perform();
                  //  addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        Policy_Response = Policy_api.getResponse();
    }
    public void Update_Policy_With_InValid_Token_Rq(@NotNull String TokenValue,  @NotNull Object[] data) {
        Random random=new Random();
        int x=random.nextInt(10000);
        Policy_api = new SHAFT.API(BaseURL);
        if(data[1].equals("Cancel"))
        {
            String Update_Cancel_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+x+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+x+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+x+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+x+"\",\n" +
                    "    \"chargeUnit\": \""+data[6]+"\",\n" +
                    "    \"deadline\": \""+data[7]+"\",\n" +
                    "    \"chargeType\": \""+data[8]+"\",\n" +
                    "    \"chargeValue\": \""+data[9]+"\",\n" +
                    "    \"-id\": \""+data[10]+"\"\n"+
                    "}";
            Policy_api.put(Cancel_Policy_Path+"/"+data[10]).
                    setRequestBody(Update_Cancel_PolicyBody).
                    setTargetStatusCode(401).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("Payment"))
        {
            String Update_Payment_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+x+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+x+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+x+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+x+"\",\n" +
                    "    \"refundType\": \""+data[6]+"\",\n" +
                    "    \"cancellationPolicyId\": \""+data[7]+"\",\n" +
                    "    \"-id\": \""+data[8]+"\"\n"+
                    "}";
            Policy_api.put(Payment_Policy_Path+"/"+data[8]).
                    setRequestBody(Update_Payment_PolicyBody).
                    setTargetStatusCode(401).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("Tax"))
        {
            String Update_Tax_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+x+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+x+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+x+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+x+"\",\n" +
                    "    \"chargeType\": \""+data[6]+"\",\n" +
                    "    \"chargeValue\": \""+data[7]+"\",\n" +
                    "    \"-id\": \""+data[8]+"\"\n"+
                    "}";
            Policy_api.put(Tax_Policy_Path+"/"+data[8]).
                    setRequestBody(Update_Tax_PolicyBody).
                    setTargetStatusCode(401).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("Usage"))
        {
            String Update_Usage_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+x+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+x+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+x+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+x+"\",\n" +
                    "    \"-id\": \""+data[6]+"\"\n"+
                    "}";
            Policy_api.put(Usage_Policy_Path+"/"+data[6]).
                    setRequestBody(Update_Usage_PolicyBody).
                    setTargetStatusCode(401).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("General"))
        {
            String Update_General_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+x+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+x+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+x+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+x+"\",\n" +
                    "    \"-id\": \""+data[6]+"\"\n"+
                    "}";
            Policy_api.put(General_Policy_Path+"/"+data[6]).
                    setRequestBody(Update_General_PolicyBody).
                    setTargetStatusCode(401).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        Policy_Response = Policy_api.getResponse();
    }
    public void Update_Policy_With_Invalid_Input_Rq(@NotNull String TokenValue, @NotNull Object[] data) {
        //Random random=new Random();
        //int x=random.nextInt(10000);
        Policy_api = new SHAFT.API(BaseURL);
        if(data[1].equals("Cancel"))
        {
            String Update_Cancel_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+"\",\n" +
                    "    \"chargeUnit\": \""+data[6]+"\",\n" +
                    "    \"deadline\": \""+data[7]+"\",\n" +
                    "    \"chargeType\": \""+data[8]+"\",\n" +
                    "    \"chargeValue\": \""+data[9]+"\",\n" +
                    "    \"-id\": \""+data[10]+"\"\n"+
                    "}";
            Policy_api.put(Cancel_Policy_Path+"/"+data[10]).
                    setRequestBody(Update_Cancel_PolicyBody).
                    setTargetStatusCode(422).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("Payment"))
        {
            String Update_Payment_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+"\",\n" +
                    "    \"refundType\": \""+data[6]+"\",\n" +
                    "    \"cancellationPolicyId\": \""+data[7]+"\",\n" +
                    "    \"-id\": \""+data[8]+"\"\n"+
                    "}";
            Policy_api.put(Payment_Policy_Path+"/"+data[8]).
                    setRequestBody(Update_Payment_PolicyBody).
                    setTargetStatusCode(422).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("Tax"))
        {
            String Update_Tax_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+"\",\n" +
                    "    \"chargeType\": \""+data[6]+"\",\n" +
                    "    \"chargeValue\": \""+data[7]+"\",\n" +
                    "    \"-id\": \""+data[8]+"\"\n"+
                    "}";
            Policy_api.put(Tax_Policy_Path+"/"+data[8]).
                    setRequestBody(Update_Tax_PolicyBody).
                    setTargetStatusCode(422).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("Usage"))
        {
            String Update_Usage_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+"\",\n" +
                    "    \"-id\": \""+data[6]+"\"\n"+
                    "}";
            Policy_api.put(Usage_Policy_Path+"/"+data[6]).
                    setRequestBody(Update_Usage_PolicyBody).
                    setTargetStatusCode(422).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("General"))
        {
            String Update_General_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+"\",\n" +
                    "    \"-id\": \""+data[6]+"\"\n"+
                    "}";
            Policy_api.put(General_Policy_Path+"/"+data[6]).
                    setRequestBody(Update_General_PolicyBody).
                    setTargetStatusCode(422).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        Policy_Response = Policy_api.getResponse();
    }
    public void Update_Policy_With_NotAccepted_Input_Rq(String TokenValue, Object[] data)  {
        //Random random=new Random();
        //int x=random.nextInt(10000);
        Policy_api = new SHAFT.API(BaseURL);
        if(data[1].equals("Cancel"))
        {
            String Update_Cancel_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+"\",\n" +
                    "    \"chargeUnit\": \""+data[6]+"\",\n" +
                    "    \"deadline\": \""+data[7]+"\",\n" +
                    "    \"chargeType\": \""+data[8]+"\",\n" +
                    "    \"chargeValue\": \""+data[9]+"\",\n" +
                    "    \"-id\": \""+data[10]+"\"\n"+
                    "}";
            Policy_api.put(Cancel_Policy_Path+"/"+data[10]).
                    setRequestBody(Update_Cancel_PolicyBody).
                    setTargetStatusCode(406).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("Payment"))
        {
            String Update_Payment_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+"\",\n" +
                    "    \"refundType\": \""+data[6]+"\",\n" +
                    "    \"cancellationPolicyId\": \""+data[7]+"\",\n" +
                    "    \"-id\": \""+data[8]+"\"\n"+
                    "}";
            Policy_api.put(Payment_Policy_Path+"/"+data[8]).
                    setRequestBody(Update_Payment_PolicyBody).
                    setTargetStatusCode(406).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("Tax"))
        {
            String Update_Tax_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+"\",\n" +
                    "    \"chargeType\": \""+data[6]+"\",\n" +
                    "    \"chargeValue\": \""+data[7]+"\",\n" +
                    "    \"-id\": \""+data[8]+"\"\n"+
                    "}";
            Policy_api.put(Tax_Policy_Path+"/"+data[8]).
                    setRequestBody(Update_Tax_PolicyBody).
                    setTargetStatusCode(406).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("Usage"))
        {
            String Update_Usage_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+"\",\n" +
                    "    \"-id\": \""+data[6]+"\"\n"+
                    "}";
            Policy_api.put(Usage_Policy_Path+"/"+data[6]).
                    setRequestBody(Update_Usage_PolicyBody).
                    setTargetStatusCode(406).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("General"))
        {
            String Update_General_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+"\",\n" +
                    "    \"-id\": \""+data[6]+"\"\n"+
                    "}";
            Policy_api.put(General_Policy_Path+"/"+data[6]).
                    setRequestBody(Update_General_PolicyBody).
                    setTargetStatusCode(406).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        Policy_Response = Policy_api.getResponse();
    }
    public void Update_Policy_With_NotFound_Input_Rq(String TokenValue, Object[] data) {
        //Random random=new Random();
        //int x=random.nextInt(10000);
        Policy_api = new SHAFT.API(BaseURL);
        if(data[1].equals("Cancel"))
        {
            String Update_Cancel_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+"\",\n" +
                    "    \"chargeUnit\": \""+data[6]+"\",\n" +
                    "    \"deadline\": \""+data[7]+"\",\n" +
                    "    \"chargeType\": \""+data[8]+"\",\n" +
                    "    \"chargeValue\": \""+data[9]+"\",\n" +
                    "    \"-id\": \""+data[10]+"\"\n"+
                    "}";
            Policy_api.put(Cancel_Policy_Path+"/"+data[10]).
                    setRequestBody(Update_Cancel_PolicyBody).
                    setTargetStatusCode(404).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("Payment"))
        {
            String Update_Payment_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+"\",\n" +
                    "    \"refundType\": \""+data[6]+"\",\n" +
                    "    \"cancellationPolicyId\": \""+data[7]+"\",\n" +
                    "    \"-id\": \""+data[8]+"\"\n"+
                    "}";
            Policy_api.put(Payment_Policy_Path+"/"+data[8]).
                    setRequestBody(Update_Payment_PolicyBody).
                    setTargetStatusCode(404).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("Tax"))
        {
            String Update_Tax_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+"\",\n" +
                    "    \"chargeType\": \""+data[6]+"\",\n" +
                    "    \"chargeValue\": \""+data[7]+"\",\n" +
                    "    \"-id\": \""+data[8]+"\"\n"+
                    "}";
            Policy_api.put(Tax_Policy_Path+"/"+data[8]).
                    setRequestBody(Update_Tax_PolicyBody).
                    setTargetStatusCode(404).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("Usage"))
        {
            String Update_Usage_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+"\",\n" +
                    "    \"-id\": \""+data[6]+"\"\n"+
                    "}";
            Policy_api.put(Usage_Policy_Path+"/"+data[6]).
                    setRequestBody(Update_Usage_PolicyBody).
                    setTargetStatusCode(404).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("General"))
        {
            String Update_General_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+"\",\n" +
                    "    \"-id\": \""+data[6]+"\"\n"+
                    "}";
            Policy_api.put(General_Policy_Path+"/"+data[6]).
                    setRequestBody(Update_General_PolicyBody).
                    setTargetStatusCode(404).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        Policy_Response = Policy_api.getResponse();
    }
    
    ////////////////////////////////GET Policy/////////////////////////////////
    public void Get_Policy_Rq(@NotNull String TokenValue, @NotNull Object[] data) {
       // Random random=new Random();
       // int x=random.nextInt(10000);
        Policy_api = new SHAFT.API(BaseURL);
        if(data[1].equals("Cancel"))
        {
/*            String GET_Cancel_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+x+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+x+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+x+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+x+"\",\n" +
                    "    \"chargeUnit\": \""+data[6]+"\",\n" +
                    "    \"deadline\": \""+data[7]+"\",\n" +
                    "    \"chargeType\": \""+data[8]+"\",\n" +
                    "    \"chargeValue\": \""+data[9]+"\",\n" +
                    "    \"id\": \""+data[10]+"\"\n"+
                    "}";*/
            Policy_api.get(Cancel_Policy_Path+"/"+data[10]).
                    //          setRequestBody(GET_Cancel_PolicyBody).
                            setTargetStatusCode(200).
                    setContentType(ContentType.ANY).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("Payment"))
        {
/*            String GET_Payment_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+x+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+x+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+x+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+x+"\",\n" +
                    "    \"refundType\": \""+data[6]+"\",\n" +
                    "    \"cancellationPolicyId\": \""+data[7]+"\",\n" +
                    "    \"id\": \""+data[8]+"\"\n"+
                    "}";*/
            Policy_api.get(Payment_Policy_Path+"/"+data[8]).
                    //         setRequestBody(GET_Payment_PolicyBody).
                            setTargetStatusCode(200).
                    setContentType(ContentType.ANY).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("Tax"))
        {
/*            String GET_Tax_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+x+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+x+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+x+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+x+"\",\n" +
                    "    \"chargeType\": \""+data[6]+"\",\n" +
                    "    \"chargeValue\": \""+data[7]+"\",\n" +
                    "    \"id\": \""+data[8]+"\"\n"+
                    "}";*/
            Policy_api.get(Tax_Policy_Path+"/"+data[8]).
                    //     setRequestBody(GET_Tax_PolicyBody).
                            setTargetStatusCode(200).
                    setContentType(ContentType.ANY).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("Usage"))
        {
/*            String GET_Usage_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+x+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+x+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+x+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+x+"\",\n" +
                    "    \"id\": \""+data[6]+"\"\n"+
                    "}";*/
            Policy_api.get(Usage_Policy_Path+"/"+data[6]).
                    //          setRequestBody(GET_Usage_PolicyBody).
                            setTargetStatusCode(200).
                    setContentType(ContentType.ANY).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("General"))
        {
 /*           String GET_General_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+x+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+x+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+x+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+x+"\",\n" +
                    "    \"id\": \""+data[6]+"\"\n"+
                    "}";*/
            Policy_api.get(General_Policy_Path+"/"+data[6]).
                    //         setRequestBody(GET_General_PolicyBody).
                            setTargetStatusCode(200).
                    setContentType(ContentType.ANY).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        try {
            testDataReader2.AddExpectedResult("Policy_TestData", data[0].toString(), "ExpectedResult", "\"id\":"+data[data.length-2]+"");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Policy_Response = Policy_api.getResponse();
    }

    public void Get_Policy_With_Missing_Token_Rq( @NotNull Object[] data) {
        //Random random=new Random();
        //int x=random.nextInt(10000);
        Policy_api = new SHAFT.API(BaseURL);
        if(data[1].equals("Cancel"))
        {
/*            String GET_Cancel_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+x+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+x+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+x+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+x+"\",\n" +
                    "    \"chargeUnit\": \""+data[6]+"\",\n" +
                    "    \"deadline\": \""+data[7]+"\",\n" +
                    "    \"chargeType\": \""+data[8]+"\",\n" +
                    "    \"chargeValue\": \""+data[9]+"\",\n" +
                    "    \"id\": \""+data[10]+"\"\n"+
                    "}";*/
            Policy_api.get(Cancel_Policy_Path+"/"+data[10]).
                    //          setRequestBody(GET_Cancel_PolicyBody).
                            setTargetStatusCode(401).
                    setContentType(ContentType.ANY).
                    setAuthentication("","", AuthenticationType.NONE).perform();
              //      addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("Payment"))
        {
/*            String GET_Payment_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+x+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+x+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+x+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+x+"\",\n" +
                    "    \"refundType\": \""+data[6]+"\",\n" +
                    "    \"cancellationPolicyId\": \""+data[7]+"\",\n" +
                    "    \"id\": \""+data[8]+"\"\n"+
                    "}";*/
            Policy_api.get(Payment_Policy_Path+"/"+data[8]).
                    //         setRequestBody(GET_Payment_PolicyBody).
                            setTargetStatusCode(401).
                    setContentType(ContentType.ANY).
                    setAuthentication("","", AuthenticationType.NONE).perform();
                  //  addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("Tax"))
        {
/*            String GET_Tax_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+x+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+x+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+x+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+x+"\",\n" +
                    "    \"chargeType\": \""+data[6]+"\",\n" +
                    "    \"chargeValue\": \""+data[7]+"\",\n" +
                    "    \"id\": \""+data[8]+"\"\n"+
                    "}";*/
            Policy_api.get(Tax_Policy_Path+"/"+data[8]).
                    //     setRequestBody(GET_Tax_PolicyBody).
                            setTargetStatusCode(401).
                    setContentType(ContentType.ANY).
                    setAuthentication("","", AuthenticationType.NONE).perform();
                //    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("Usage"))
        {
/*            String GET_Usage_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+x+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+x+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+x+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+x+"\",\n" +
                    "    \"id\": \""+data[6]+"\"\n"+
                    "}";*/
            Policy_api.get(Usage_Policy_Path+"/"+data[6]).
                    //          setRequestBody(GET_Usage_PolicyBody).
                            setTargetStatusCode(401).
                    setContentType(ContentType.ANY).
                    setAuthentication("","", AuthenticationType.NONE).perform();
                  //  addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("General"))
        {
 /*           String GET_General_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+x+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+x+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+x+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+x+"\",\n" +
                    "    \"id\": \""+data[6]+"\"\n"+
                    "}";*/
            Policy_api.get(General_Policy_Path+"/"+data[6]).
                    //         setRequestBody(GET_General_PolicyBody).
                            setTargetStatusCode(401).
                    setContentType(ContentType.ANY).
                    setAuthentication("","", AuthenticationType.NONE).perform();
              //      addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        Policy_Response = Policy_api.getResponse();
    }

    public void Get_Policy_With_InValid_Token_Rq(@NotNull String TokenValue, @NotNull Object[] data) {
     //   Random random=new Random();
     //   int x=random.nextInt(10000);
        Policy_api = new SHAFT.API(BaseURL);
        if(data[1].equals("Cancel"))
        {
/*            String GET_Cancel_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+x+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+x+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+x+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+x+"\",\n" +
                    "    \"chargeUnit\": \""+data[6]+"\",\n" +
                    "    \"deadline\": \""+data[7]+"\",\n" +
                    "    \"chargeType\": \""+data[8]+"\",\n" +
                    "    \"chargeValue\": \""+data[9]+"\",\n" +
                    "    \"id\": \""+data[10]+"\"\n"+
                    "}";*/
            Policy_api.get(Cancel_Policy_Path+"/"+data[10]).
                    //          setRequestBody(GET_Cancel_PolicyBody).
                            setTargetStatusCode(401).
                    setContentType(ContentType.ANY).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("Payment"))
        {
/*            String GET_Payment_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+x+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+x+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+x+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+x+"\",\n" +
                    "    \"refundType\": \""+data[6]+"\",\n" +
                    "    \"cancellationPolicyId\": \""+data[7]+"\",\n" +
                    "    \"id\": \""+data[8]+"\"\n"+
                    "}";*/
            Policy_api.get(Payment_Policy_Path+"/"+data[8]).
                    //         setRequestBody(GET_Payment_PolicyBody).
                            setTargetStatusCode(401).
                    setContentType(ContentType.ANY).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("Tax"))
        {
/*            String GET_Tax_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+x+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+x+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+x+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+x+"\",\n" +
                    "    \"chargeType\": \""+data[6]+"\",\n" +
                    "    \"chargeValue\": \""+data[7]+"\",\n" +
                    "    \"id\": \""+data[8]+"\"\n"+
                    "}";*/
            Policy_api.get(Tax_Policy_Path+"/"+data[8]).
                    //     setRequestBody(GET_Tax_PolicyBody).
                            setTargetStatusCode(401).
                    setContentType(ContentType.ANY).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("Usage"))
        {
/*            String GET_Usage_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+x+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+x+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+x+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+x+"\",\n" +
                    "    \"id\": \""+data[6]+"\"\n"+
                    "}";*/
            Policy_api.get(Usage_Policy_Path+"/"+data[6]).
                    //          setRequestBody(GET_Usage_PolicyBody).
                            setTargetStatusCode(401).
                    setContentType(ContentType.ANY).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("General"))
        {
 /*           String GET_General_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+x+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+x+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+x+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+x+"\",\n" +
                    "    \"id\": \""+data[6]+"\"\n"+
                    "}";*/
            Policy_api.get(General_Policy_Path+"/"+data[6]).
                    //         setRequestBody(GET_General_PolicyBody).
                            setTargetStatusCode(401).
                    setContentType(ContentType.ANY).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        Policy_Response = Policy_api.getResponse();
    }

    public void Get_Policy_With_NotFound_Input_Rq(@NotNull String TokenValue, @NotNull Object[] data) {
       // Random random=new Random();
        //int x=random.nextInt(10000);
        Policy_api = new SHAFT.API(BaseURL);
        if(data[1].equals("Cancel"))
        {
/*            String GET_Cancel_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+x+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+x+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+x+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+x+"\",\n" +
                    "    \"chargeUnit\": \""+data[6]+"\",\n" +
                    "    \"deadline\": \""+data[7]+"\",\n" +
                    "    \"chargeType\": \""+data[8]+"\",\n" +
                    "    \"chargeValue\": \""+data[9]+"\",\n" +
                    "    \"id\": \""+data[10]+"\"\n"+
                    "}";*/
            Policy_api.get(Cancel_Policy_Path+"/"+data[10]).
                    //          setRequestBody(GET_Cancel_PolicyBody).
                            setTargetStatusCode(404).
                    setContentType(ContentType.ANY).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("Payment"))
        {
/*            String GET_Payment_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+x+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+x+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+x+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+x+"\",\n" +
                    "    \"refundType\": \""+data[6]+"\",\n" +
                    "    \"cancellationPolicyId\": \""+data[7]+"\",\n" +
                    "    \"id\": \""+data[8]+"\"\n"+
                    "}";*/
            Policy_api.get(Payment_Policy_Path+"/"+data[8]).
                    //         setRequestBody(GET_Payment_PolicyBody).
                            setTargetStatusCode(404).
                    setContentType(ContentType.ANY).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("Tax"))
        {
/*            String GET_Tax_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+x+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+x+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+x+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+x+"\",\n" +
                    "    \"chargeType\": \""+data[6]+"\",\n" +
                    "    \"chargeValue\": \""+data[7]+"\",\n" +
                    "    \"id\": \""+data[8]+"\"\n"+
                    "}";*/
            Policy_api.get(Tax_Policy_Path+"/"+data[8]).
                    //     setRequestBody(GET_Tax_PolicyBody).
                            setTargetStatusCode(404).
                    setContentType(ContentType.ANY).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("Usage"))
        {
/*            String GET_Usage_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+x+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+x+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+x+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+x+"\",\n" +
                    "    \"id\": \""+data[6]+"\"\n"+
                    "}";*/
            Policy_api.get(Usage_Policy_Path+"/"+data[6]).
                    //          setRequestBody(GET_Usage_PolicyBody).
                            setTargetStatusCode(404).
                    setContentType(ContentType.ANY).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("General"))
        {
 /*           String GET_General_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+x+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+x+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+x+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+x+"\",\n" +
                    "    \"id\": \""+data[6]+"\"\n"+
                    "}";*/
            Policy_api.get(General_Policy_Path+"/"+data[6]).
                    //         setRequestBody(GET_General_PolicyBody).
                            setTargetStatusCode(404).
                    setContentType(ContentType.ANY).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        Policy_Response = Policy_api.getResponse();
    }

    public void Get_Policy_With_BadRequest_Input_Rq(@NotNull String TokenValue, @NotNull Object[] data) {
       // Random random=new Random();
       // int x=random.nextInt(10000);
       // if (data[10].toString().)
        Policy_api = new SHAFT.API(BaseURL);
        if(data[1].equals("Cancel"))
        {
            /*            String GET_Cancel_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+x+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+x+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+x+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+x+"\",\n" +
                    "    \"chargeUnit\": \""+data[6]+"\",\n" +
                    "    \"deadline\": \""+data[7]+"\",\n" +
                    "    \"chargeType\": \""+data[8]+"\",\n" +
                    "    \"chargeValue\": \""+data[9]+"\",\n" +
                    "    \"id\": \""+data[10]+"\"\n"+
                    "}";*/
            if(data[10].toString().contains(" ")) {
                data[10]='\u2001';
            }

            Policy_api.get(Cancel_Policy_Path+"/"+ data[10]).
        //          setRequestBody(GET_Cancel_PolicyBody).
                    setTargetStatusCode(400).
                    setContentType(ContentType.ANY).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).
                    perform();
        }
        else if (data[1].equals("Payment"))
        {
         /*            String GET_Payment_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+x+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+x+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+x+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+x+"\",\n" +
                    "    \"refundType\": \""+data[6]+"\",\n" +
                    "    \"cancellationPolicyId\": \""+data[7]+"\",\n" +
                    "    \"id\": \""+data[8]+"\"\n"+
                    "}";*/
            if(data[8].toString().contains(" ")) {
                data[8]='\u2001';
            }

            Policy_api.get(Payment_Policy_Path+"/"+ data[8]).
         //         setRequestBody(GET_Payment_PolicyBody).
                    setTargetStatusCode(400).
                    setContentType(ContentType.ANY).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("Tax"))
        {
            /*            String GET_Tax_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+x+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+x+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+x+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+x+"\",\n" +
                    "    \"chargeType\": \""+data[6]+"\",\n" +
                    "    \"chargeValue\": \""+data[7]+"\",\n" +
                    "    \"id\": \""+data[8]+"\"\n"+
                    "}";*/
            if(data[8].toString().contains(" ")) {
                data[8]='\u2001';
            }

            Policy_api.get(Tax_Policy_Path+"/"+ data[8]).
             //     setRequestBody(GET_Tax_PolicyBody).
                    setTargetStatusCode(400).
                    setContentType(ContentType.ANY).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("Usage"))
        {
            /*            String GET_Usage_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+x+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+x+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+x+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+x+"\",\n" +
                    "    \"id\": \""+data[6]+"\"\n"+
                    "}";*/
            if(data[6].toString().contains(" ")) {
                data[6]='\u2001';
            }
            Policy_api.get(Usage_Policy_Path+"/"+ data[6]).
        //          setRequestBody(GET_Usage_PolicyBody).
                    setTargetStatusCode(400).
                    setContentType(ContentType.ANY).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        else if (data[1].equals("General"))
        {
            /*           String GET_General_PolicyBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+x+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+x+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+x+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+x+"\",\n" +
                    "    \"id\": \""+data[6]+"\"\n"+
                    "}";*/
            if(data[6].toString().contains(" ")) {
                data[6]='\u2001';
            }

            Policy_api.get(General_Policy_Path+"/"+ data[6]).
         //         setRequestBody(GET_General_PolicyBody).
                    setTargetStatusCode(400).
                    setContentType(ContentType.ANY).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        }
        Policy_Response = Policy_api.getResponse();
    }

    ///////////////////Status Code////////////////////////////
    public void Check_Valid_Add_policies_status_Code_Response(){
        SHAFT.Validations.assertThat().number(Policy_Response.getStatusCode()).isEqualTo(201).perform();
    }
    public void Check_Validation_Error_policy_status_Code_Response(){
        SHAFT.Validations.assertThat().number(Policy_Response.getStatusCode()).isEqualTo(422).perform();
    }

    public void Check_Unauthorized_policy_status_Code_Response(){
        SHAFT.Validations.assertThat().number(Policy_Response.getStatusCode()).isEqualTo(401).perform();
    }

    public void Check_Valid_Update_policies_status_Code_Response(){
        SHAFT.Validations.assertThat().number(Policy_Response.getStatusCode()).isEqualTo(200).perform();
    }

    public void Check_Valid_Get_policies_status_Code_Response(){
        SHAFT.Validations.assertThat().number(Policy_Response.getStatusCode()).isEqualTo(200).perform();
    }
    public void Check_Validation_NotAccepted_policy_status_Code_Response() {
        SHAFT.Validations.assertThat().number(Policy_Response.getStatusCode()).isEqualTo(406).perform();

    }
    public void Check_Validation_NotFound_policy_status_Code_Response() {
        SHAFT.Validations.assertThat().number(Policy_Response.getStatusCode()).isEqualTo(404).perform();

    }
    public void Check_Validation_BadRequest_policy_status_Code_Response() {
        SHAFT.Validations.assertThat().number(Policy_Response.getStatusCode()).isEqualTo(400).perform();

    }


    /////////////////////////////Schema//////////////////////////////////////
    public void Check_Policy_Response_Valid_Schema(@NotNull String PolicyType) {
        switch (PolicyType) {
            case "Cancel" ->
                    Policy_api.assertThatResponse().matchesSchema(testDataReader.getCellData("API_Data", "AddCancellationPolicies", "Valid_Schema")).perform();
            case "Payment" ->
                    Policy_api.assertThatResponse().matchesSchema(testDataReader.getCellData("API_Data", "AddPaymentPolicies", "Valid_Schema")).perform();
            case "Tax" ->
                    Policy_api.assertThatResponse().matchesSchema(testDataReader.getCellData("API_Data", "AddTaxPolicies", "Valid_Schema")).perform();
            case "Usage" ->
                    Policy_api.assertThatResponse().matchesSchema(testDataReader.getCellData("API_Data", "AddUsagePolicies", "Valid_Schema")).perform();
            case "General" ->
                    Policy_api.assertThatResponse().matchesSchema(testDataReader.getCellData("API_Data", "AddGeneralPolicies", "Valid_Schema")).perform();
        }

    }

    public void Check_Policy_Response_Time() {
        SHAFT.Validations.verifyThat().number(Policy_Response.getTime()).isGreaterThanOrEquals(1.1).perform();
        SHAFT.Validations.verifyThat().number(Policy_Response.getTime()).isLessThanOrEquals(30000).perform();
    }

    public void Check_policy_Response_Unauthorized_Schema() {
        Policy_api.assertThatResponse().matchesSchema(testDataReader.getCellData("API_Data","UnAuthorized","URL")).perform();
    }

    public void Check_policy_Response_Validation_Error_Schema() {
        Policy_api.assertThatResponse().matchesSchema(testDataReader.getCellData("API_Data","Validation Error","URL")).perform();
    }

    public void Check_policy_Response_NotAccepted_Error_Schema() {
        Policy_api.assertThatResponse().matchesSchema(testDataReader.getCellData("API_Data","Not Accepted","URL")).perform();
    }

    public void Check_policy_Response_NotFound_Error_Schema() {
        Policy_api.assertThatResponse().matchesSchema(testDataReader.getCellData("API_Data", "Not Found", "URL")).perform();
    }
    public void Check_policy_Response_BadRequest_Error_Schema() {
        Policy_api.assertThatResponse().matchesSchema(testDataReader.getCellData("API_Data", "Bad Request", "URL")).perform();
    }


    //////////////////////////////content/////////////////////////////////////
    public void Check_policy_Content(String ExpectedResult) {
        //String Policy_ResponseBody = Policy_Response.getBody().asString();
        Policy_api.assertThatResponse().body().contains(ExpectedResult).
               withCustomReportMessage("Check that content object contains : "+ExpectedResult).
                perform();
       // SHAFT.Validations.assertThat().object(Policy_ResponseBody.contains(ExpectedResult)).isTrue().perform();
        //SHAFT.Validations.assertThat().object(Policy_ResponseBody).contains(ExpectedResult).withCustomReportMessage("Check that content object contains"+ExpectedResult).perform();
    }
    
}