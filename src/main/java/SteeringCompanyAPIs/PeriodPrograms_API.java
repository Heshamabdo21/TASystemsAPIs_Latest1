/*
 * Copyright (c) 2023.
 */

package SteeringCompanyAPIs;

import Utils.ExtraExcelFun;
import com.shaft.api.RequestBuilder.AuthenticationType;
import com.shaft.api.RestActions;
import com.shaft.driver.SHAFT;
import com.shaft.tools.io.ExcelFileManager;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class PeriodPrograms_API {
    ExcelFileManager testDataReader = new ExcelFileManager("SteeringCompanyAPI_TestData/SteeringCompanyAPI_TestData.xlsx");
    ExtraExcelFun testDataReader2 = new ExtraExcelFun("SteeringCompanyAPI_TestData/SteeringCompanyAPI_TestData.xlsx");

    String BaseURL = testDataReader.getCellData("API_Data","Steering_Base_URL","URL");
    Response All_PeriodPrograms_Response;
    SHAFT.API All_PeriodPrograms_api;
   // String All_PeriodPrograms_Path = "/PeriodPrograms";
    String All_PeriodPrograms_Path = testDataReader.getCellData("API_Data","GetAllPeriodPrograms","URL");
  /////////////////////////// Methods for Get All PeriodPrograms ///////////////////////
    public void GET_All_PeriodPrograms_Rq(String TokenValue) {
     //   String Lookup_PeriodPrograms_Path = "/PeriodPrograms";
        All_PeriodPrograms_api = new SHAFT.API(BaseURL);
        All_PeriodPrograms_api.get(All_PeriodPrograms_Path).
                setAuthentication("","", AuthenticationType.NONE).
                addHeader("Authorization", "Bearer " + TokenValue).perform();
         All_PeriodPrograms_Response = All_PeriodPrograms_api.getResponse();
    }
    public void GET_All_PeriodPrograms_Path_by_parameter_Query_Rq(String TokenValue, String PageSize, String PageNumber) {
        //  String Lookup_PeriodPrograms_Path = "/PeriodPrograms";
        All_PeriodPrograms_api = new SHAFT.API(BaseURL);
        List<List<Object>> parameters = Arrays.asList(Arrays.asList("size", PageSize),
                Arrays.asList("page",PageNumber));
        All_PeriodPrograms_api.get(All_PeriodPrograms_Path).
                setAuthentication("", "", AuthenticationType.NONE).
                setParameters(parameters, RestActions.ParametersType.QUERY).
                addHeader("Authorization", "Bearer " + TokenValue).perform();
        All_PeriodPrograms_Response = All_PeriodPrograms_api.getResponse();

    }
    public void GET_All_PeriodPrograms_ValidationError_by_parameter_Query_Rq(String TokenValue, String PageSize, String PageNumber) {
        //  String Lookup_PeriodPrograms_Path = "/PeriodPrograms";
        All_PeriodPrograms_api = new SHAFT.API(BaseURL);
        List<List<Object>> parameters = Arrays.asList(Arrays.asList("size", PageSize),
                Arrays.asList("page",PageNumber));
        All_PeriodPrograms_api.get(All_PeriodPrograms_Path).
                setTargetStatusCode(422).
                setAuthentication("", "", AuthenticationType.NONE).
                setParameters(parameters, RestActions.ParametersType.QUERY).
                addHeader("Authorization", "Bearer " + TokenValue).perform();
        All_PeriodPrograms_Response = All_PeriodPrograms_api.getResponse();

    }
    public void GET_All_PeriodPrograms_With_Missing_Token_Rq() {
        // String Lookup_cities_Path = "/lookups/cities";
        All_PeriodPrograms_api = new SHAFT.API(BaseURL);
        All_PeriodPrograms_api.get(All_PeriodPrograms_Path).
                setAuthentication("","", AuthenticationType.NONE).
                setTargetStatusCode(401).
                perform();
              //  addHeader("Authorization", "Bearer " ).perform();

        All_PeriodPrograms_Response = All_PeriodPrograms_api.getResponse();
    }
    public void GET_All_PeriodPrograms_With_InValid_Token_Rq(String TokenValue) {
        // String Lookup_cities_Path = "/lookups/cities";

        All_PeriodPrograms_api = new SHAFT.API(BaseURL);
        All_PeriodPrograms_api.get(All_PeriodPrograms_Path).
                setAuthentication("","", AuthenticationType.NONE).
                setTargetStatusCode(401).
                addHeader("Authorization", "Bearer "+ TokenValue ).perform();

        All_PeriodPrograms_Response = All_PeriodPrograms_api.getResponse();
    }
    //////////////////////////////////////////////////////////////////////////////////////
    public void Check_Valid_All_PeriodPrograms_status_Code_Response(){
        SHAFT.Validations.assertThat().number(All_PeriodPrograms_Response.getStatusCode()).isEqualTo(200).perform();
    }
    public void Check_Unauthorized_All_PeriodPrograms_status_Code_Response(){
        SHAFT.Validations.assertThat().number(All_PeriodPrograms_Response.getStatusCode()).isEqualTo(401).perform();
    }
    public void Check_ValidationError_All_PeriodPrograms_status_Code_Response(){
        SHAFT.Validations.assertThat().number(All_PeriodPrograms_Response.getStatusCode()).isEqualTo(422).perform();
    }

    //////////////////////////////////////////////////////////////////////////////////////
    public void Check_PeriodPrograms_Response_Time() {
        SHAFT.Validations.verifyThat().number(All_PeriodPrograms_Response.getTime()).isGreaterThanOrEquals(1.1).perform();
        SHAFT.Validations.verifyThat().number(All_PeriodPrograms_Response.getTime()).isLessThanOrEquals(30000).perform();
    }
    public void Check_All_PeriodPrograms_Valid_Content() {
        String Lookups_PeriodPrograms_ResponseBody = All_PeriodPrograms_Response.getBody().asString();
        SHAFT.Validations.assertThat().object(Lookups_PeriodPrograms_ResponseBody).contains("content").perform();
        All_PeriodPrograms_api.assertThatResponse().extractedJsonValue("content").isNotNull().withCustomReportMessage("Check that content object is not null.").perform();
    }
    public void Check_All_PeriodPrograms_Content(String ExpectedResult) {
        All_PeriodPrograms_api.assertThatResponse().body().contains(ExpectedResult).
                withCustomReportMessage("Check that content object contains : "+ExpectedResult).
                perform();
    }
    //////////////////////////////////////////////////////////////////////////////////////
    public void Check_All_PeriodPrograms_Response_Valid_Schema() {
        All_PeriodPrograms_api.assertThatResponse().matchesSchema(testDataReader.getCellData("API_Data","GetAllPeriodPrograms","Valid_Schema")).perform();
    }
    //////////////////////////////////////////////////////////////////////////////////////
    public void Check_All_PeriodPrograms_Response_Unauthorized_Schema() {
        All_PeriodPrograms_api.assertThatResponse().matchesSchema(testDataReader.getCellData("API_Data","UnAuthorized","URL")).perform();
    }
    public void Check_All_PeriodPrograms_Response_ValidationError_Schema() {
        All_PeriodPrograms_api.assertThatResponse().matchesSchema(testDataReader.getCellData("API_Data","Validation Error","URL")).perform();
    }


    /////////////////////// Add  PeriodProgram Methods ///////////////////
    String PeriodProgram_Path = testDataReader.getCellData("API_Data","AddPeriodPrograms","URL");
    public Response PeriodProgram_Response;
    SHAFT.API PeriodProgram_api;
    ////////////////////////////////Add PeriodProgram/////////////////////////////////
    public void Add_PeriodProgram_Rq(@NotNull String TokenValue, @NotNull Object[] data) {
        PeriodProgram_api = new SHAFT.API(BaseURL);
        String Add_PeriodProgramBody;
        if(data[1].toString().contains("WithPolicies")){
         Add_PeriodProgramBody="{\n"+
                "    \"creationPeriodId\": "+data[2]+",\n" +
                "    \"templateId\": "+data[3]+",\n" +
                "    \"periodMaxQuotaPerPeriod\": "+data[4]+",\n" +
                "    \"minimumModelYear\": "+data[5]+",\n" +
                "    \"maximumModelYear\": "+data[6]+",\n" +
                "    \"minimumSeat\": "+data[7]+",\n" +
                "    \"maximumSeat\": "+data[8]+",\n" +
                "    \"vehiclePricePer\": "+data[9]+",\n" +
                "    \"isActive\": "+data[10]+",\n" +
                "    \"policies\": [\n" +
                "        {\n" +
                "            \"type\": \""+data[11]+"\",\n" +
                "            \"id\": "+data[12]+"\n" +
                "        },\n" +
                "        {\n" +
                "            \"type\": \""+data[13]+"\",\n" +
                "            \"id\": "+data[14]+"\n" +
                "        },\n" +
                "        {\n" +
                "            \"type\": \""+data[15]+"\",\n" +
                "            \"id\": "+data[16]+"\n" +
                "        },\n" +
                "        {\n" +
                "            \"type\": \""+data[17]+"\",\n" +
                "            \"id\": "+data[18]+"\n" +
                "        },\n" +
                "        {\n" +
                "            \"type\": \""+data[19]+"\",\n" +
                "            \"id\": "+data[20]+"\n" +
                "        }]"+
                "}";}
        else
        {Add_PeriodProgramBody="{\n"+
                "    \"creationPeriodId\": "+data[2]+",\n" +
                "    \"templateId\": "+data[3]+",\n" +
                "    \"periodMaxQuotaPerPeriod\": "+data[4]+",\n" +
                "    \"minimumModelYear\": "+data[5]+",\n" +
                "    \"maximumModelYear\": "+data[6]+",\n" +
                "    \"minimumSeat\": "+data[7]+",\n" +
                "    \"maximumSeat\": "+data[8]+",\n" +
                "    \"vehiclePricePer\": "+data[9]+",\n" +
                "    \"isActive\": "+data[10]+",\n" +
                "    \"policies\": [] \n"+
                "}";
        }
        PeriodProgram_api.post(PeriodProgram_Path).
                setRequestBody(Add_PeriodProgramBody).
                setTargetStatusCode(201).
                setContentType(ContentType.JSON).
                setAuthentication("","", AuthenticationType.NONE).
                addHeader("Authorization", "Bearer " + TokenValue).perform();
        PeriodProgram_Response = PeriodProgram_api.getResponse();
        int PeriodProgramID;
        try {
            JSONObject ResponseJsonObject = new JSONObject (PeriodProgram_Response.getBody().asString());
            PeriodProgramID =ResponseJsonObject.getInt("id");

        } catch (JSONException e) {
             PeriodProgramID=0;
            throw new RuntimeException(e);
        }


        try {
            testDataReader2.AddExpectedResult("PeriodProgram_TestData", data[0].toString(), "ExpectedResult", "\"creationPeriodId\":"+data[2]+"");
            testDataReader2.AddExpectedResult("PeriodProgram_TestData", data[0].toString(), "PeriodProgramID", String.valueOf(PeriodProgramID));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
/*
    public void Add_PeriodProgram_Without_Policies_Rq(@NotNull String TokenValue, @NotNull Object[] data) {
        PeriodProgram_api = new SHAFT.API(BaseURL);
        String Add_PeriodProgramBody="{\n"+
                "    \"creationPeriodId\": "+data[2]+",\n" +
                "    \"templateId\": "+data[3]+",\n" +
                "    \"periodMaxQuotaPerPeriod\": "+data[4]+",\n" +
                "    \"minimumModelYear\": "+data[5]+",\n" +
                "    \"maximumModelYear\": "+data[6]+",\n" +
                "    \"minimumSeat\": "+data[7]+",\n" +
                "    \"maximumSeat\": "+data[8]+",\n" +
                "    \"vehiclePricePer\": "+data[9]+",\n" +
                "    \"isActive\": "+data[10]+",\n" +
                "    \"policies\": [] \n"+
                "}";

        PeriodProgram_api.post(PeriodProgram_Path).
                setRequestBody(Add_PeriodProgramBody).
                setTargetStatusCode(201).
                setContentType(ContentType.JSON).
                setAuthentication("","", AuthenticationType.NONE).
                addHeader("Authorization", "Bearer " + TokenValue).perform();
      */
/*  try {
            testDataReader2.AddExpectedResult("PeriodProgram_TestData", data[0].toString(), "ExpectedResult", "\"nameArabic\":\""+data[2]+"\"");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*//*
        PeriodProgram_Response = PeriodProgram_api.getResponse();
    }
*/
    public void Add_PeriodProgram_With_Missing_Token_Rq( @NotNull Object[] data) {
        PeriodProgram_api = new SHAFT.API(BaseURL);
        String Add_PeriodProgramBody;
            if(data[1].toString().contains("WithPolicies")){
                Add_PeriodProgramBody="{\n"+
                        "    \"creationPeriodId\": "+data[2]+",\n" +
                        "    \"templateId\": "+data[3]+",\n" +
                        "    \"periodMaxQuotaPerPeriod\": "+data[4]+",\n" +
                        "    \"minimumModelYear\": "+data[5]+",\n" +
                        "    \"maximumModelYear\": "+data[6]+",\n" +
                        "    \"minimumSeat\": "+data[7]+",\n" +
                        "    \"maximumSeat\": "+data[8]+",\n" +
                        "    \"vehiclePricePer\": "+data[9]+",\n" +
                        "    \"isActive\": "+data[10]+",\n" +
                        "    \"policies\": [\n" +
                        "        {\n" +
                        "            \"type\": \""+data[11]+"\",\n" +
                        "            \"id\": "+data[12]+"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"type\": \""+data[13]+"\",\n" +
                        "            \"id\": "+data[14]+"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"type\": \""+data[15]+"\",\n" +
                        "            \"id\": "+data[16]+"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"type\": \""+data[17]+"\",\n" +
                        "            \"id\": "+data[18]+"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"type\": \""+data[19]+"\",\n" +
                        "            \"id\": "+data[20]+"\n" +
                        "        }]"+
                        "}";}
            else
            {Add_PeriodProgramBody="{\n"+
                    "    \"creationPeriodId\": "+data[2]+",\n" +
                    "    \"templateId\": "+data[3]+",\n" +
                    "    \"periodMaxQuotaPerPeriod\": "+data[4]+",\n" +
                    "    \"minimumModelYear\": "+data[5]+",\n" +
                    "    \"maximumModelYear\": "+data[6]+",\n" +
                    "    \"minimumSeat\": "+data[7]+",\n" +
                    "    \"maximumSeat\": "+data[8]+",\n" +
                    "    \"vehiclePricePer\": "+data[9]+",\n" +
                    "    \"isActive\": "+data[10]+",\n" +
                    "    \"policies\": [] \n"+
                    "}";
            }
            PeriodProgram_api.post(PeriodProgram_Path).
                    setRequestBody(Add_PeriodProgramBody).
                    setTargetStatusCode(401).
                    setContentType(ContentType.JSON).
                    setAuthentication("", "", AuthenticationType.NONE).perform();
            // addHeader("Authorization", "Bearer " + TokenValue).
        
        PeriodProgram_Response = PeriodProgram_api.getResponse();
    }
    public void Add_PeriodProgram_With_InValid_Token_Rq(@NotNull String TokenValue, @NotNull Object[] data) {
        PeriodProgram_api = new SHAFT.API(BaseURL);
        String Add_PeriodProgramBody;
        if(data[1].toString().contains("WithPolicies")){
            Add_PeriodProgramBody="{\n"+
                    "    \"creationPeriodId\": "+data[2]+",\n" +
                    "    \"templateId\": "+data[3]+",\n" +
                    "    \"periodMaxQuotaPerPeriod\": "+data[4]+",\n" +
                    "    \"minimumModelYear\": "+data[5]+",\n" +
                    "    \"maximumModelYear\": "+data[6]+",\n" +
                    "    \"minimumSeat\": "+data[7]+",\n" +
                    "    \"maximumSeat\": "+data[8]+",\n" +
                    "    \"vehiclePricePer\": "+data[9]+",\n" +
                    "    \"isActive\": "+data[10]+",\n" +
                    "    \"policies\": [\n" +
                    "        {\n" +
                    "            \"type\": \""+data[11]+"\",\n" +
                    "            \"id\": "+data[12]+"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"type\": \""+data[13]+"\",\n" +
                    "            \"id\": "+data[14]+"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"type\": \""+data[15]+"\",\n" +
                    "            \"id\": "+data[16]+"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"type\": \""+data[17]+"\",\n" +
                    "            \"id\": "+data[18]+"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"type\": \""+data[19]+"\",\n" +
                    "            \"id\": "+data[20]+"\n" +
                    "        }]"+
                    "}";}
        else
        {Add_PeriodProgramBody="{\n"+
                "    \"creationPeriodId\": "+data[2]+",\n" +
                "    \"templateId\": "+data[3]+",\n" +
                "    \"periodMaxQuotaPerPeriod\": "+data[4]+",\n" +
                "    \"minimumModelYear\": "+data[5]+",\n" +
                "    \"maximumModelYear\": "+data[6]+",\n" +
                "    \"minimumSeat\": "+data[7]+",\n" +
                "    \"maximumSeat\": "+data[8]+",\n" +
                "    \"vehiclePricePer\": "+data[9]+",\n" +
                "    \"isActive\": "+data[10]+",\n" +
                "    \"policies\": [] \n"+
                "}";
        }
            PeriodProgram_api.post(PeriodProgram_Path).
                    setRequestBody(Add_PeriodProgramBody).
                    setTargetStatusCode(401).
                    setContentType(ContentType.JSON).
                    setAuthentication("", "", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        PeriodProgram_Response = PeriodProgram_api.getResponse();
    }
    public void Add_PeriodProgram_With_Invalid_Input_Rq(@NotNull String TokenValue, @NotNull Object[] data) {
        PeriodProgram_api = new SHAFT.API(BaseURL);
        String Add_PeriodProgramBody;
        if(data[1].toString().contains("WithPolicies")){
            Add_PeriodProgramBody="{\n"+
                    "    \"creationPeriodId\": "+data[2]+",\n" +
                    "    \"templateId\": "+data[3]+",\n" +
                    "    \"periodMaxQuotaPerPeriod\": "+data[4]+",\n" +
                    "    \"minimumModelYear\": "+data[5]+",\n" +
                    "    \"maximumModelYear\": "+data[6]+",\n" +
                    "    \"minimumSeat\": "+data[7]+",\n" +
                    "    \"maximumSeat\": "+data[8]+",\n" +
                    "    \"vehiclePricePer\": "+data[9]+",\n" +
                    "    \"isActive\": "+data[10]+",\n" +
                    "    \"policies\": [\n" +
                    "        {\n" +
                    "            \"type\": \""+data[11]+"\",\n" +
                    "            \"id\": "+data[12]+"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"type\": \""+data[13]+"\",\n" +
                    "            \"id\": "+data[14]+"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"type\": \""+data[15]+"\",\n" +
                    "            \"id\": "+data[16]+"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"type\": \""+data[17]+"\",\n" +
                    "            \"id\": "+data[18]+"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"type\": \""+data[19]+"\",\n" +
                    "            \"id\": "+data[20]+"\n" +
                    "        }]"+
                    "}";}
        else
        {Add_PeriodProgramBody="{\n"+
                "    \"creationPeriodId\": "+data[2]+",\n" +
                "    \"templateId\": "+data[3]+",\n" +
                "    \"periodMaxQuotaPerPeriod\": "+data[4]+",\n" +
                "    \"minimumModelYear\": "+data[5]+",\n" +
                "    \"maximumModelYear\": "+data[6]+",\n" +
                "    \"minimumSeat\": "+data[7]+",\n" +
                "    \"maximumSeat\": "+data[8]+",\n" +
                "    \"vehiclePricePer\": "+data[9]+",\n" +
                "    \"isActive\": "+data[10]+",\n" +
                "    \"policies\": [] \n"+
                "}";
        }
            PeriodProgram_api.post(PeriodProgram_Path).
                    setRequestBody(Add_PeriodProgramBody).
                    setTargetStatusCode(422).
                    setContentType(ContentType.JSON).
                    setAuthentication("", "", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();

        PeriodProgram_Response = PeriodProgram_api.getResponse();
//        try {
//            testDataReader2.AddExpectedResult("PeriodProgram_TestData", data[0].toString(), "ExpectedResult",PeriodProgram_Response.asString() );
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }
    public void Add_PeriodProgram_With_NotAccepted_Input_Rq(String TokenValue, Object[] data)  {
        PeriodProgram_api = new SHAFT.API(BaseURL);
        String Add_PeriodProgramBody;
        if(data[1].toString().contains("WithPolicies")){
            Add_PeriodProgramBody="{\n"+
                    "    \"creationPeriodId\": "+data[2]+",\n" +
                    "    \"templateId\": "+data[3]+",\n" +
                    "    \"periodMaxQuotaPerPeriod\": "+data[4]+",\n" +
                    "    \"minimumModelYear\": "+data[5]+",\n" +
                    "    \"maximumModelYear\": "+data[6]+",\n" +
                    "    \"minimumSeat\": "+data[7]+",\n" +
                    "    \"maximumSeat\": "+data[8]+",\n" +
                    "    \"vehiclePricePer\": "+data[9]+",\n" +
                    "    \"isActive\": "+data[10]+",\n" +
                    "    \"policies\": [\n" +
                    "        {\n" +
                    "            \"type\": \""+data[11]+"\",\n" +
                    "            \"id\": "+data[12]+"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"type\": \""+data[13]+"\",\n" +
                    "            \"id\": "+data[14]+"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"type\": \""+data[15]+"\",\n" +
                    "            \"id\": "+data[16]+"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"type\": \""+data[17]+"\",\n" +
                    "            \"id\": "+data[18]+"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"type\": \""+data[19]+"\",\n" +
                    "            \"id\": "+data[20]+"\n" +
                    "        }]"+
                    "}";}
        else
        {Add_PeriodProgramBody="{\n"+
                "    \"creationPeriodId\": "+data[2]+",\n" +
                "    \"templateId\": "+data[3]+",\n" +
                "    \"periodMaxQuotaPerPeriod\": "+data[4]+",\n" +
                "    \"minimumModelYear\": "+data[5]+",\n" +
                "    \"maximumModelYear\": "+data[6]+",\n" +
                "    \"minimumSeat\": "+data[7]+",\n" +
                "    \"maximumSeat\": "+data[8]+",\n" +
                "    \"vehiclePricePer\": "+data[9]+",\n" +
                "    \"isActive\": "+data[10]+",\n" +
                "    \"policies\": [] \n"+
                "}";
        }
            PeriodProgram_api.post(PeriodProgram_Path).
                    setRequestBody(Add_PeriodProgramBody).
                    setTargetStatusCode(406).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        PeriodProgram_Response = PeriodProgram_api.getResponse();
    }
    public void Add_PeriodProgram_With_NotFound_Input_Rq(String TokenValue, Object[] data) {
        PeriodProgram_api = new SHAFT.API(BaseURL);
            String Add_PeriodProgramBody;
            if(data[1].toString().contains("WithPolicies")){
                Add_PeriodProgramBody="{\n"+
                        "    \"creationPeriodId\": "+data[2]+",\n" +
                        "    \"templateId\": "+data[3]+",\n" +
                        "    \"periodMaxQuotaPerPeriod\": "+data[4]+",\n" +
                        "    \"minimumModelYear\": "+data[5]+",\n" +
                        "    \"maximumModelYear\": "+data[6]+",\n" +
                        "    \"minimumSeat\": "+data[7]+",\n" +
                        "    \"maximumSeat\": "+data[8]+",\n" +
                        "    \"vehiclePricePer\": "+data[9]+",\n" +
                        "    \"isActive\": "+data[10]+",\n" +
                        "    \"policies\": [\n" +
                        "        {\n" +
                        "            \"type\": \""+data[11]+"\",\n" +
                        "            \"id\": "+data[12]+"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"type\": \""+data[13]+"\",\n" +
                        "            \"id\": "+data[14]+"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"type\": \""+data[15]+"\",\n" +
                        "            \"id\": "+data[16]+"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"type\": \""+data[17]+"\",\n" +
                        "            \"id\": "+data[18]+"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"type\": \""+data[19]+"\",\n" +
                        "            \"id\": "+data[20]+"\n" +
                        "        }]"+
                        "}";}
            else
            {Add_PeriodProgramBody="{\n"+
                    "    \"creationPeriodId\": "+data[2]+",\n" +
                    "    \"templateId\": "+data[3]+",\n" +
                    "    \"periodMaxQuotaPerPeriod\": "+data[4]+",\n" +
                    "    \"minimumModelYear\": "+data[5]+",\n" +
                    "    \"maximumModelYear\": "+data[6]+",\n" +
                    "    \"minimumSeat\": "+data[7]+",\n" +
                    "    \"maximumSeat\": "+data[8]+",\n" +
                    "    \"vehiclePricePer\": "+data[9]+",\n" +
                    "    \"isActive\": "+data[10]+",\n" +
                    "    \"policies\": [] \n"+
                    "}";
            }
            PeriodProgram_api.post(PeriodProgram_Path).
                    setRequestBody(Add_PeriodProgramBody).
                    setTargetStatusCode(404).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        PeriodProgram_Response = PeriodProgram_api.getResponse();
    }

        ////////////////////////////////Update PeriodProgram/////////////////////////////////
    public void Update_PeriodProgram_Rq(@NotNull String TokenValue,  @NotNull Object[] data)  {
        PeriodProgram_api = new SHAFT.API(BaseURL);
            String Update_PeriodProgramBody="{\n"+
                    "    \"creationPeriodId\": "+data[2]+",\n" +
                    "    \"templateId\": "+data[3]+",\n" +
                    "    \"periodMaxQuotaPerPeriod\": "+data[4]+",\n" +
                    "    \"minimumModelYear\": "+data[5]+",\n" +
                    "    \"maximumModelYear\": "+data[6]+",\n" +
                    "    \"minimumSeat\": "+data[7]+",\n" +
                    "    \"maximumSeat\": "+data[8]+",\n" +
                    "    \"vehiclePricePer\": " + data[9] + ",\n" +
                    "    \"isActive\": " + data[10] + ",\n" +
                    "    \"policies\": [] \n" +
                    "}";
        PeriodProgram_api.put(PeriodProgram_Path + "/" + data[22]).
                setRequestBody(Update_PeriodProgramBody).
                setTargetStatusCode(200).
                setContentType(ContentType.JSON).
                setAuthentication("", "", AuthenticationType.NONE).
                addHeader("Authorization", "Bearer " + TokenValue).perform();
        try {
            testDataReader2.AddExpectedResult("PeriodProgram_TestData", data[0].toString(), "ExpectedResult", "\"creationPeriodId\":" + data[2] + "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PeriodProgram_Response = PeriodProgram_api.getResponse();
    }
    public void Update_PeriodProgram_With_Missing_Token_Rq( @NotNull Object[] data) {
        PeriodProgram_api = new SHAFT.API(BaseURL);
            String Update_PeriodProgramBody="{\n"+
                    "    \"creationPeriodId\": "+data[2]+",\n" +
                    "    \"templateId\": "+data[3]+",\n" +
                    "    \"periodMaxQuotaPerPeriod\": "+data[4]+",\n" +
                    "    \"minimumModelYear\": "+data[5]+",\n" +
                    "    \"maximumModelYear\": "+data[6]+",\n" +
                    "    \"minimumSeat\": "+data[7]+",\n" +
                    "    \"maximumSeat\": "+data[8]+",\n" +
                    "    \"vehiclePricePer\": "+data[9]+",\n" +
                    "    \"isActive\": "+data[10]+",\n" +
                    "    \"policies\": [] \n"+
                    "}";;
            PeriodProgram_api.put(PeriodProgram_Path+"/"+data[22]).
                    setRequestBody(Update_PeriodProgramBody).
                    setTargetStatusCode(401).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).perform();
                //    addHeader("Authorization", "Bearer " + TokenValue).perform();

        PeriodProgram_Response = PeriodProgram_api.getResponse();
    }
    public void Update_PeriodProgram_With_InValid_Token_Rq(@NotNull String TokenValue,  @NotNull Object[] data) {
        PeriodProgram_api = new SHAFT.API(BaseURL);

            String Update_PeriodProgramBody="{\n"+
                    "    \"creationPeriodId\": "+data[2]+",\n" +
                    "    \"templateId\": "+data[3]+",\n" +
                    "    \"periodMaxQuotaPerPeriod\": "+data[4]+",\n" +
                    "    \"minimumModelYear\": "+data[5]+",\n" +
                    "    \"maximumModelYear\": "+data[6]+",\n" +
                    "    \"minimumSeat\": "+data[7]+",\n" +
                    "    \"maximumSeat\": "+data[8]+",\n" +
                    "    \"vehiclePricePer\": "+data[9]+",\n" +
                    "    \"isActive\": "+data[10]+",\n" +
                    "    \"policies\": [] \n"+
                    "}";;
            PeriodProgram_api.put(PeriodProgram_Path+"/"+data[22]).
                    setRequestBody(Update_PeriodProgramBody).
                    setTargetStatusCode(401).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();

        PeriodProgram_Response = PeriodProgram_api.getResponse();
    }
    public void Update_PeriodProgram_With_Invalid_Input_Rq(@NotNull String TokenValue, @NotNull Object[] data) {
        PeriodProgram_api = new SHAFT.API(BaseURL);

            String Update_PeriodProgramBody="{\n"+
                    "    \"creationPeriodId\": "+data[2]+",\n" +
                    "    \"templateId\": "+data[3]+",\n" +
                    "    \"periodMaxQuotaPerPeriod\": "+data[4]+",\n" +
                    "    \"minimumModelYear\": "+data[5]+",\n" +
                    "    \"maximumModelYear\": "+data[6]+",\n" +
                    "    \"minimumSeat\": "+data[7]+",\n" +
                    "    \"maximumSeat\": "+data[8]+",\n" +
                    "    \"vehiclePricePer\": "+data[9]+",\n" +
                    "    \"isActive\": "+data[10]+",\n" +
                    "    \"policies\": [] \n"+
                    "}";;
            PeriodProgram_api.put(PeriodProgram_Path+"/"+data[22]).
                    setRequestBody(Update_PeriodProgramBody).
                    setTargetStatusCode(422).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();

        PeriodProgram_Response = PeriodProgram_api.getResponse();
    }
    public void Update_PeriodProgram_With_NotAccepted_Input_Rq(String TokenValue, Object[] data)  {
        PeriodProgram_api = new SHAFT.API(BaseURL);

            String Update_PeriodProgramBody="{\n"+
                    "    \"creationPeriodId\": "+data[2]+",\n" +
                    "    \"templateId\": "+data[3]+",\n" +
                    "    \"periodMaxQuotaPerPeriod\": "+data[4]+",\n" +
                    "    \"minimumModelYear\": "+data[5]+",\n" +
                    "    \"maximumModelYear\": "+data[6]+",\n" +
                    "    \"minimumSeat\": "+data[7]+",\n" +
                    "    \"maximumSeat\": "+data[8]+",\n" +
                    "    \"vehiclePricePer\": "+data[9]+",\n" +
                    "    \"isActive\": "+data[10]+",\n" +
                    "    \"policies\": [] \n"+
                    "}";;
            PeriodProgram_api.put(PeriodProgram_Path+"/"+data[22]).
                    setRequestBody(Update_PeriodProgramBody).
                    setTargetStatusCode(406).
                    setContentType(ContentType.JSON).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();

        PeriodProgram_Response = PeriodProgram_api.getResponse();
    }
    public void Update_PeriodProgram_With_NotFound_Input_Rq(String TokenValue, Object[] data) {
        PeriodProgram_api = new SHAFT.API(BaseURL);

            String Update_PeriodProgramBody = "{\n"+
                    "    \"creationPeriodId\": "+data[2]+",\n" +
                    "    \"templateId\": "+data[3]+",\n" +
                    "    \"periodMaxQuotaPerPeriod\": "+data[4]+",\n" +
                    "    \"minimumModelYear\": "+data[5]+",\n" +
                    "    \"maximumModelYear\": "+data[6]+",\n" +
                    "    \"minimumSeat\": "+data[7]+",\n" +
                    "    \"maximumSeat\": "+data[8]+",\n" +
                    "    \"vehiclePricePer\": "+data[9]+",\n" +
                    "    \"isActive\": "+data[10]+",\n" +
                    "    \"policies\": [] \n"+
                    "}";;
            PeriodProgram_api.put(PeriodProgram_Path + "/" + data[22]).
                    setRequestBody(Update_PeriodProgramBody).
                    setTargetStatusCode(404).
                    setContentType(ContentType.JSON).
                    setAuthentication("", "", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
            PeriodProgram_Response = PeriodProgram_api.getResponse();

    }
    ////////////////////////////////GET PeriodProgram/////////////////////////////////
    public void Get_PeriodProgram_Rq(@NotNull String TokenValue, @NotNull Object[] data) {
        PeriodProgram_api = new SHAFT.API(BaseURL);

            PeriodProgram_api.get(PeriodProgram_Path+"/"+data[22]).
                    //          setRequestBody(GET_Cancel_PeriodProgramBody).
                            setTargetStatusCode(200).
                    setContentType(ContentType.ANY).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
/*
        try {
            testDataReader2.AddExpectedResult("PeriodProgram_TestData", data[0].toString(), "ExpectedResult", "\"id\":"+data[data.length-2]+"");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
*/
        PeriodProgram_Response = PeriodProgram_api.getResponse();
    }
    public void Get_PeriodProgram_With_Missing_Token_Rq( @NotNull Object[] data) {
        PeriodProgram_api = new SHAFT.API(BaseURL);
/*            String GET_Cancel_PeriodProgramBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+"\",\n" +
                    "    \"chargeUnit\": \""+data[6]+"\",\n" +
                    "    \"deadline\": \""+data[7]+"\",\n" +
                    "    \"chargeType\": \""+data[8]+"\",\n" +
                    "    \"chargeValue\": \""+data[9]+"\",\n" +
                    "    \"id\": \""+data[10]+"\"\n"+
                    "}";*/
            PeriodProgram_api.get(PeriodProgram_Path+"/"+data[22]).
                    //          setRequestBody(GET_Cancel_PeriodProgramBody).
                            setTargetStatusCode(401).
                    setContentType(ContentType.ANY).
                    setAuthentication("","", AuthenticationType.NONE).perform();
              //      addHeader("Authorization", "Bearer " + TokenValue).perform();
        PeriodProgram_Response = PeriodProgram_api.getResponse();
    }
    public void Get_PeriodProgram_With_InValid_Token_Rq(@NotNull String TokenValue, @NotNull Object[] data) {
        PeriodProgram_api = new SHAFT.API(BaseURL);
/*            String GET_Cancel_PeriodProgramBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+"\",\n" +
                    "    \"chargeUnit\": \""+data[6]+"\",\n" +
                    "    \"deadline\": \""+data[7]+"\",\n" +
                    "    \"chargeType\": \""+data[8]+"\",\n" +
                    "    \"chargeValue\": \""+data[9]+"\",\n" +
                    "    \"id\": \""+data[10]+"\"\n"+
                    "}";*/
            PeriodProgram_api.get(PeriodProgram_Path+"/"+data[22]).
                    //          setRequestBody(GET_Cancel_PeriodProgramBody).
                            setTargetStatusCode(401).
                    setContentType(ContentType.ANY).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        PeriodProgram_Response = PeriodProgram_api.getResponse();
    }
    public void Get_PeriodProgram_With_NotFound_Input_Rq(@NotNull String TokenValue, @NotNull Object[] data) {
        PeriodProgram_api = new SHAFT.API(BaseURL);
/*            String GET_Cancel_PeriodProgramBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+"\",\n" +
                    "    \"chargeUnit\": \""+data[6]+"\",\n" +
                    "    \"deadline\": \""+data[7]+"\",\n" +
                    "    \"chargeType\": \""+data[8]+"\",\n" +
                    "    \"chargeValue\": \""+data[9]+"\",\n" +
                    "    \"id\": \""+data[10]+"\"\n"+
                    "}";*/
            PeriodProgram_api.get(PeriodProgram_Path+"/"+data[22]).
                    //          setRequestBody(GET_Cancel_PeriodProgramBody).
                            setTargetStatusCode(404).
                    setContentType(ContentType.ANY).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).perform();
        PeriodProgram_Response = PeriodProgram_api.getResponse();
    }
    public void Get_PeriodProgram_With_BadRequest_Input_Rq(@NotNull String TokenValue, @NotNull Object[] data) {
        PeriodProgram_api = new SHAFT.API(BaseURL);
            /*            String GET_Cancel_PeriodProgramBody="{\n"+
                    "    \"nameArabic\": \""+data[2]+"\",\n" +
                    "    \"nameEnglish\": \""+data[3]+"\",\n" +
                    "    \"descriptionArabic\": \""+data[4]+"\",\n" +
                    "    \"descriptionEnglish\": \""+data[5]+"\",\n" +
                    "    \"chargeUnit\": \""+data[6]+"\",\n" +
                    "    \"deadline\": \""+data[7]+"\",\n" +
                    "    \"chargeType\": \""+data[8]+"\",\n" +
                    "    \"chargeValue\": \""+data[9]+"\",\n" +
                    "    \"id\": \""+data[10]+"\"\n"+
                    "}";*/
            if(data[22].toString().contains(" ")) {
                data[22]='\u2001';
            }

            PeriodProgram_api.get(PeriodProgram_Path+"/"+ data[22]).
        //          setRequestBody(GET_Cancel_PeriodProgramBody).
                    setTargetStatusCode(400).
                    setContentType(ContentType.ANY).
                    setAuthentication("","", AuthenticationType.NONE).
                    addHeader("Authorization", "Bearer " + TokenValue).
                    perform();
        PeriodProgram_Response = PeriodProgram_api.getResponse();
    }
    ///////////////////Status Code////////////////////////////
    public void Check_Valid_Add_PeriodPrograms_status_Code_Response(){
        SHAFT.Validations.assertThat().number(PeriodProgram_Response.getStatusCode()).isEqualTo(201).perform();
    }
    public void Check_Valid_Update_PeriodPrograms_status_Code_Response(){
        SHAFT.Validations.assertThat().number(PeriodProgram_Response.getStatusCode()).isEqualTo(200).perform();
    }
    public void Check_Valid_Get_PeriodPrograms_status_Code_Response(){
        SHAFT.Validations.assertThat().number(PeriodProgram_Response.getStatusCode()).isEqualTo(200).perform();
    }
    public void Check_Validation_Error_PeriodProgram_status_Code_Response(){
        SHAFT.Validations.assertThat().number(PeriodProgram_Response.getStatusCode()).isEqualTo(422).perform();
    }
    public void Check_Unauthorized_PeriodProgram_status_Code_Response(){
        SHAFT.Validations.assertThat().number(PeriodProgram_Response.getStatusCode()).isEqualTo(401).perform();
    }
    public void Check_Validation_NotAccepted_PeriodProgram_status_Code_Response() {
        SHAFT.Validations.assertThat().number(PeriodProgram_Response.getStatusCode()).isEqualTo(406).perform();

    }
    public void Check_Validation_NotFound_PeriodProgram_status_Code_Response() {
        SHAFT.Validations.assertThat().number(PeriodProgram_Response.getStatusCode()).isEqualTo(404).perform();

    }
    public void Check_Validation_BadRequest_PeriodProgram_status_Code_Response() {
        SHAFT.Validations.assertThat().number(PeriodProgram_Response.getStatusCode()).isEqualTo(400).perform();

    }
    /////////////////////////////Schema//////////////////////////////////////
    public void Check_PeriodProgram_Response_Valid_Schema(String PeriodProgramType) {
        if(PeriodProgramType.contains("WithPolicies"))
        {
            PeriodProgram_api.assertThatResponse().matchesSchema(testDataReader.getCellData("API_Data", "AddPeriodProgramsWithPolicies", "Valid_Schema")).perform();
        }
        else
        {
            PeriodProgram_api.assertThatResponse().matchesSchema(testDataReader.getCellData("API_Data", "AddPeriodPrograms", "Valid_Schema")).perform();

        }
        }
    public void Check_PeriodProgram_Response_Time() {
        SHAFT.Validations.verifyThat().number(PeriodProgram_Response.getTime()).isGreaterThanOrEquals(1.1).perform();
        SHAFT.Validations.verifyThat().number(PeriodProgram_Response.getTime()).isLessThanOrEquals(30000).perform();
    }
    public void Check_PeriodProgram_Response_Unauthorized_Schema() {
        PeriodProgram_api.assertThatResponse().matchesSchema(testDataReader.getCellData("API_Data","UnAuthorized","URL")).perform();
    }
    public void Check_PeriodProgram_Response_Validation_Error_Schema() {
        PeriodProgram_api.assertThatResponse().matchesSchema(testDataReader.getCellData("API_Data","Validation Error","URL")).perform();
    }
    public void Check_PeriodProgram_Response_NotAccepted_Error_Schema() {
        PeriodProgram_api.assertThatResponse().matchesSchema(testDataReader.getCellData("API_Data","Not Accepted","URL")).perform();
    }
    public void Check_PeriodProgram_Response_NotFound_Error_Schema() {
        PeriodProgram_api.assertThatResponse().matchesSchema(testDataReader.getCellData("API_Data", "Not Found", "URL")).perform();
    }
    public void Check_PeriodProgram_Response_BadRequest_Error_Schema() {
        PeriodProgram_api.assertThatResponse().matchesSchema(testDataReader.getCellData("API_Data", "Bad Request", "URL")).perform();
    }
    //////////////////////////////content/////////////////////////////////////
    public void Check_PeriodProgram_Content(String ExpectedResult) {
        PeriodProgram_api.assertThatResponse().body().contains(ExpectedResult).
               withCustomReportMessage("Check that content object contains : "+ExpectedResult).
                perform();
    }
    public void Check_PeriodPrograms_Valid_Content() {
        String PeriodPrograms_ResponseBody = PeriodProgram_Response.getBody().asString();
        SHAFT.Validations.assertThat().object(PeriodPrograms_ResponseBody).contains("id").perform();
        PeriodProgram_api.assertThatResponse().extractedJsonValue("id").isNotNull().withCustomReportMessage("Check that id object is not null.").perform();
    }
}