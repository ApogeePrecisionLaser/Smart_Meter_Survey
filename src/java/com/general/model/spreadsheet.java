/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.model;

import com.Bean.tableClasses.meterDetail_Bean;
import com.Webservice.Model.SmartMeterSurveyServiceModel;
import com.connection.DBConnection.DBConnection;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetResponse;
import com.google.api.services.sheets.v4.model.Request;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.SpreadsheetProperties;
import com.google.api.services.sheets.v4.model.UpdateSpreadsheetPropertiesRequest;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/*
 * BEFORE RUNNING:
 * ---------------
 * 1. If not already done, enable the Google Sheets API
 *    and check the quota for your project at
 *    https://console.developers.google.com/apis/api/sheets
 * 2. Install the Java client library on Maven or Gradle. Check installation
 *    instructions at https://github.com/google/google-api-java-client.
 *    On other build systems, you can add the jar files to your project from
 *    https://developers.google.com/resources/api-libraries/download/sheets/v4/java
 */
/**
 *
 * @author Manpreet Kaur
 */
public class spreadsheet {

    /** Application name. */
    private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
            System.getProperty("user.home"), ".credentials/sheets.googleapis.com-java-quickstart");
    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;
    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;
    /** Global instance of the scopes required by this spreadsheet. */
    private static final List<String> SCOPES = Arrays.asList(SheetsScopes.DRIVE);

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize() throws IOException {
        // Load client secrets
    //    File file = new File("E:\\Netebeans Project\\Smart_Meter_survey\\src\\java\\com\\general\\model\\client_secret.json");
//        File file = new File("E:\\NetBeans Project\\UserDetail\\src\\java\\com\\general\\model\\client_secret.json");
       java.io.File file = new java.io.File("C:\\google_json_file\\client_secret.json");
        InputStream in = new FileInputStream(file);

        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES).setDataStoreFactory(DATA_STORE_FACTORY).setAccessType("offline").build();
        Credential credential = new AuthorizationCodeInstalledApp(
                flow, new LocalServerReceiver()).authorize("user");
        System.out.println("Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    /**
     * Build and return an authorized Sheets API client service.
     * @return an authorized Sheets API client service
     * @throws IOException
     */
    public static Sheets createSheetsService() throws IOException, GeneralSecurityException {
        // Authorize using one of the following scopes:
        //   "https://www.googleapis.com/auth/drive"
        //   "https://www.googleapis.com/auth/spreadsheets"

        //GoogleCredential credential = authorize();
        Credential credential = authorize();

        return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential) // .setApplicationName("Google-SheetsSample/0.1")
                .setApplicationName(APPLICATION_NAME).build();
    }

    public static void uploadGoogleSheet(List list, List headerList, int dataListSize, int headerListSize, String ExcelReportName, String sheetRange, Connection connection) throws IOException, GeneralSecurityException, NoSuchFieldException {

        Spreadsheet requestBody = new Spreadsheet();
        Sheets sheetsService = createSheetsService();
                
         // creating the spreadsheet
        Sheets.Spreadsheets.Create request = sheetsService.spreadsheets().create(requestBody);
        Spreadsheet response = request.execute();
        System.out.println(response);

        // Changing the spreadsheet's title.
        String title = response.getProperties().getTitle();
        System.out.println("title = " + title);
        String spreadsheetId = response.getSpreadsheetId();
        System.out.println("spreadsheetId = " + spreadsheetId);
        title = ExcelReportName;
                List<Request> requests = new ArrayList<Request>();
                requests.add(new Request()
                        .setUpdateSpreadsheetProperties(new UpdateSpreadsheetPropertiesRequest()
                        .setProperties(new SpreadsheetProperties()
                        .setTitle(title))
                        .setFields("title")));

        BatchUpdateSpreadsheetRequest body =
        new BatchUpdateSpreadsheetRequest().setRequests(requests);
        BatchUpdateSpreadsheetResponse response1 =
        sheetsService.spreadsheets().batchUpdate(spreadsheetId, body).execute();

        // write values into the spreadsheet
        String valueInputOption=  "USER_ENTERED";
        List<List<Object>> listOfListOfStrings = new ArrayList<List<Object>>();
        // populate spreadsheet
        List<Object> listOfStrings = new ArrayList<Object>(); // one inner list for first headers row
        for(int i=0; i<headerListSize; i++){
            listOfStrings.add(headerList.get(i));
        }
        listOfListOfStrings.add(listOfStrings);

        Iterator itr = list.iterator();
        meterDetail_Bean sb = new meterDetail_Bean();
        int i = 0;
        while (itr.hasNext() && i < list.size()) {

            sb = (meterDetail_Bean) list.get(i);
            String meter_installation_date = sb.getDate_time1();
            if(meter_installation_date==null || meter_installation_date.isEmpty()){
                meter_installation_date = "";
            }
            String meter_no = sb.getMeter_no();
            if(meter_no==null || meter_no.isEmpty()){
                meter_no = "";
            }
            String survey_meter_no = sb.getSurvey_meter_no();
            if(survey_meter_no==null || survey_meter_no.isEmpty()){
                survey_meter_no = "";
            }
            String meter_start_reading = sb.getMeter_reading1();
            if(meter_start_reading==null || meter_start_reading.isEmpty()){
                meter_start_reading = "";
            }
            String name = sb.getKey_person_name();
            if(name==null || name.isEmpty()){
                name = "";
            }
            String address = sb.getAddress();
            if(address==null || address.isEmpty()){
                address = "";
            }
            String mobile = sb.getMobile_no();
            if(mobile==null || mobile.isEmpty()){
                mobile = "";
            }
            String occupants = String.valueOf(sb.getNumber_of_occupants());
            if(occupants==null || occupants.isEmpty()){
                occupants = "";
            }
            String date_time = sb.getDate_time();
            if(date_time==null || date_time.isEmpty()){
                date_time = "";
            }
            String meter_reading = sb.getMeter_reading();
            if(meter_reading==null || meter_reading.isEmpty()){
                meter_reading = "";
            }
            String remark = sb.getRemark();
            if(remark==null || remark.isEmpty()){
                remark = "";
            }
            double latitude = sb.getLatitude();
            double longitude = sb.getLongitude();
            String gps_link = "http://www.google.com/maps/place/" + latitude + "," + longitude;
            String image = sb.getImage_name();      

             SmartMeterSurveyServiceModel sm = new SmartMeterSurveyServiceModel();
                String image_id=sm.getFileIdFromDatabase(image);
        
            if(image_id.equals("")){
                image_id = "0B0a0S9mad9qOeW5OOEhQaWlvUlk";
            }
            String image_link = "https://drive.google.com/file/d/" + image_id + "/view";
            listOfStrings = new ArrayList<Object>(); // and another list for data row

            listOfStrings.add(i + 1);
            listOfStrings.add(meter_installation_date);
            listOfStrings.add(meter_no);
            listOfStrings.add(survey_meter_no);
            listOfStrings.add(meter_start_reading);
            listOfStrings.add(name);
            listOfStrings.add(address);
            listOfStrings.add(mobile);
            listOfStrings.add(occupants);
            listOfStrings.add(date_time);
            listOfStrings.add(meter_reading);
            listOfStrings.add(remark);
            listOfStrings.add(gps_link);
            listOfStrings.add(image_link);
            listOfListOfStrings.add(listOfStrings);
            i++;

        }

        ValueRange requestBody1 = new ValueRange();
        requestBody1.setRange(sheetRange);
        requestBody1.setMajorDimension("ROWS");
        requestBody1.setValues(listOfListOfStrings);
        Sheets.Spreadsheets.Values.Update updateRequest =
        sheetsService.spreadsheets().values().update(spreadsheetId, sheetRange, requestBody1);
        updateRequest.setValueInputOption(valueInputOption);
        UpdateValuesResponse updateResponse = updateRequest.execute();
        System.out.println(updateResponse);

        //Sharing Spreadsheet with client[s]
        share.shareSpreadsheet(spreadsheetId, connection);

    }
    

  // Apend value on sheet...
public static void uploadGoogleSheetAppendValue(List list, int dataListSize, String sheetRange,int dataListSize2) throws IOException, GeneralSecurityException, NoSuchFieldException {

      //  Spreadsheet requestBody = new Spreadsheet();
        Sheets sheetsService = createSheetsService();
        String spreadsheetId="1dUUQjQ4JywvSctOn7xqUvoCfeQHTKjnJPkN8fGhHLU4";
        
        // write values into the spreadsheet
        String valueInputOption=  "USER_ENTERED";
        List<List<Object>> listOfListOfStrings = new ArrayList<List<Object>>();
        // populate spreadsheet
        List<Object> listOfStrings = new ArrayList<Object>();

        Iterator itr = list.iterator();
        meterDetail_Bean sb = new meterDetail_Bean();
        int i = 0;
        while (itr.hasNext() && i < list.size()) {

            sb = (meterDetail_Bean) list.get(i);
            String meter_installation_date = sb.getDate_time1();
            if(meter_installation_date==null || meter_installation_date.isEmpty()){
                meter_installation_date = "";
            }
            String meter_no = sb.getMeter_no();
            if(meter_no==null || meter_no.isEmpty()){
                meter_no = "";
            }
            String survey_meter_no = sb.getSurvey_meter_no();
            if(survey_meter_no==null || survey_meter_no.isEmpty()){
                survey_meter_no = "";
            }
            String meter_start_reading = sb.getMeter_reading1();
            if(meter_start_reading==null || meter_start_reading.isEmpty()){
                meter_start_reading = "";
            }
            String name = sb.getKey_person_name();
            if(name==null || name.isEmpty()){
                name = "";
            }
            String address = sb.getAddress();
            if(address==null || address.isEmpty()){
                address = "";
            }
            String mobile = sb.getMobile_no();
            if(mobile==null || mobile.isEmpty()){
                mobile = "";
            }
            String occupants = String.valueOf(sb.getNumber_of_occupants());
            if(occupants==null || occupants.isEmpty()){
                occupants = "";
            }
            String date_time = sb.getDate_time();
            if(date_time==null || date_time.isEmpty()){
                date_time = "";
            }
            String meter_reading = sb.getMeter_reading();
            if(meter_reading==null || meter_reading.isEmpty()){
                meter_reading = "";
            }
            String remark = sb.getRemark();
            if(remark==null || remark.isEmpty()){
                remark = "";
            }
            double latitude = sb.getLatitude();
            double longitude = sb.getLongitude();
            String gps_link = "http://www.google.com/maps/place/" + latitude + "," + longitude;
            String image = sb.getImage_name();

             SmartMeterSurveyServiceModel sm = new SmartMeterSurveyServiceModel();
                String image_id=sm.getFileIdFromDatabase(image);

            if(image_id.equals("")){
                image_id = "0B0a0S9mad9qOeW5OOEhQaWlvUlk";
            }
           String image_link = "https://drive.google.com/file/d/" + image_id + "/view";
            listOfStrings = new ArrayList<Object>(); // and another list for data row

            listOfStrings.add(dataListSize2);
            listOfStrings.add(meter_installation_date);
            listOfStrings.add(meter_no);
            listOfStrings.add(survey_meter_no);
            listOfStrings.add(meter_start_reading);
            listOfStrings.add(name);
            listOfStrings.add(address);
            listOfStrings.add(mobile);
            listOfStrings.add(occupants);
            listOfStrings.add(date_time);
            listOfStrings.add(meter_reading);
            listOfStrings.add(remark);
            listOfStrings.add(gps_link);
            listOfStrings.add(image_link);
            listOfListOfStrings.add(listOfStrings);
            i++;

        }

        ValueRange requestBody1 = new ValueRange();
        requestBody1.setRange(sheetRange);
        requestBody1.setMajorDimension("ROWS");
        requestBody1.setValues(listOfListOfStrings);
        Sheets.Spreadsheets.Values.Append request =
        sheetsService.spreadsheets().values().append(spreadsheetId, sheetRange, requestBody1);
        request.setValueInputOption(valueInputOption);
       // request.setInsertDataOption(listOfListOfStrings);

       AppendValuesResponse response = request.execute();

    // TODO: Change code below to process the `response` object:
      System.out.println(response);

    }
}

    

