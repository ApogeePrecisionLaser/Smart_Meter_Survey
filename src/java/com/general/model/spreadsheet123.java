/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.model;

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
import com.google.api.services.sheets.v4.Sheets.Spreadsheets.BatchUpdate;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.AddSheetRequest;
import com.google.api.services.sheets.v4.model.BatchGetValuesResponse;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetResponse;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesResponse;
import com.google.api.services.sheets.v4.model.Request;
import com.google.api.services.sheets.v4.model.Sheet;
import com.google.api.services.sheets.v4.model.SheetProperties;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.SpreadsheetProperties;
import com.google.api.services.sheets.v4.model.UpdateCellsRequest;
import com.google.api.services.sheets.v4.model.UpdateSheetPropertiesRequest;
import com.google.api.services.sheets.v4.model.UpdateSpreadsheetPropertiesRequest;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.AbstractList;
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

public class spreadsheet123 {

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
        // Load client secrets.
        File file = new File("C:\\google_json_file\\client_secret.json");
        InputStream in = new FileInputStream(file);

        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(DATA_STORE_FACTORY)
                .setAccessType("offline")
                .build();
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

        return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                // .setApplicationName("Google-SheetsSample/0.1")
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

  public static void main(String args[]) throws IOException, GeneralSecurityException {
       
//    Sheets sheetsService = createSheetsService();
//
//      // The spreadsheet to request.
//    String spreadsheetId = "1rHHbyRDf37yvBO2rv8iULPAGF8QqCsdhgqJq2q1pJ-M";
//
//    // The ranges to retrieve from the spreadsheet.
//    List<String> ranges = new ArrayList<String>(); // TODO: Update placeholder value.
//
//    // True if grid data should be returned.
//    // This parameter is ignored if a field mask was set in the request.
//    boolean includeGridData = true; // TODO: Update placeholder value.
//
//    Sheets.Spreadsheets.Get request = sheetsService.spreadsheets().get(spreadsheetId);
//    request.setRanges(ranges);
//    request.setIncludeGridData(includeGridData);
//
//    Spreadsheet response = request.execute();
//    System.out.println(response);
//    Integer sheetId = response.getSheets().get(0).getProperties().getSheetId();
//    System.out.println("sheetId = " + sheetId);
//    String sheetName = response.getSheets().get(0).getProperties().getTitle();
//    System.out.println("sheetName = " + sheetName);


        /*
        // creating the spreadsheet
        Spreadsheet requestBody = new Spreadsheet();
        Sheets.Spreadsheets.Create request = sheetsService.spreadsheets().create(requestBody);
        Spreadsheet response = request.execute();
        System.out.println(response);

        // Changing the spreadsheet's title.
        String title = response.getProperties().getTitle();
        System.out.println("title = " + title);
        String spreadsheetId = response.getSpreadsheetId();
        System.out.println("spreadsheetId = " + spreadsheetId);

        List<Sheet> sheets = response.getSheets();
        System.out.println("spreadsheet sheets = " + sheets);
        Sheet get = sheets.get(0);
        int sheetId = get.getProperties().getSheetId();
        String sheetTitle = get.getProperties().getTitle();
        System.out.println("sheetId = " + sheetId + " and sheetTitle = " + sheetTitle);

        List<Request> requests = new ArrayList<Request>();
        requests.add(new Request()
        .setUpdateSpreadsheetProperties(new UpdateSpreadsheetPropertiesRequest()
        .setProperties(new SpreadsheetProperties()
        .setTitle("My First spreadSheet")
        // .setDefaultFormat("backgroundColor":{"blue":1.0,"green":1.0,"red":1.0})
        ).setFields("title")));
        requests.add(new Request()
        .setUpdateSheetProperties(new UpdateSheetPropertiesRequest()
        .setProperties(new SheetProperties()
        .setTitle("My First Sheet")
        // .setTabColor("green":1.0)
        ).setFields("title")));
        requests.add(new Request()
        .setAddSheet(new AddSheetRequest()
        .setProperties(new SheetProperties()
        .setTitle("My Second Sheet")
        // .setTabColor("green":1.0)
        )));
        //                requests.add(new Request()
        //                        .setUpdateCells(new UpdateCellsRequest()
        //                        .setRange('My First Sheet'!A1)
        //                        ));

        BatchUpdateSpreadsheetRequest sheetbody =
        new BatchUpdateSpreadsheetRequest().setRequests(requests);
        BatchUpdateSpreadsheetResponse sheetresponse =
        sheetsService.spreadsheets().batchUpdate(spreadsheetId, sheetbody).execute();
        System.out.println("batchUpdate sheetresponse = " + sheetresponse);

        // write values into the spreadsheet
        String range = "My First Sheet!A1:B3";
        String valueInputOption=  "USER_ENTERED";
        List<List<Object>> listOfListOfStrings = new ArrayList<List<Object>>();
        // populate
        List<Object> listOfStrings = new ArrayList<Object>(); // one inner list
        listOfStrings.add("Item");
        listOfStrings.add("Cost");
        listOfListOfStrings.add(listOfStrings);
        listOfStrings = new ArrayList<Object>(); // and another one
        listOfStrings.add("one");
        listOfStrings.add("10");
        listOfListOfStrings.add(listOfStrings);
        listOfStrings = new ArrayList<Object>(); // and another one
        listOfStrings.add("two");
        listOfStrings.add("20");
        listOfListOfStrings.add(listOfStrings);
        //        String spreadsheetId = "1WvMzi_CTkTBNMEhYQYtbVYfgV-bqASUSQtm8GrSbmLc";
        //        String range = "My First Sheet!B4";
        //        String valueInputOption=  "USER_ENTERED";
        //        List<List<Object>> listOfListOfStrings = new ArrayList<List<Object>>();
        //        List<Object> listOfStrings = new ArrayList<Object>();
        //        listOfStrings.add("=SUM(B1:B3)");
        //        listOfListOfStrings.add(listOfStrings);
        ValueRange requestBody1 = new ValueRange();
        requestBody1.setRange(range);
        requestBody1.setMajorDimension("ROWS");
        requestBody1.setValues(listOfListOfStrings);
        Sheets.Spreadsheets.Values.Update updateRequest =
        sheetsService.spreadsheets().values().update(spreadsheetId, range, requestBody1);
        updateRequest.setValueInputOption(valueInputOption);
        UpdateValuesResponse updateResponse = updateRequest.execute();
        System.out.println(updateResponse);
         */
     /*   Sheets.Spreadsheets.Create request = sheetsService.spreadsheets().create(requestBody);
        Spreadsheet response = request.execute();
        System.out.println(response);


        // Changing the spreadsheet's title.
        String title = response.getProperties().getTitle();
        System.out.println("title = " + title);
        String spreadsheetId = response.getSpreadsheetId();
        System.out.println("spreadsheetId = " + spreadsheetId);

        List<Sheet> sheets = response.getSheets();
        System.out.println("spreadsheet sheets = " + sheets);
        Sheet get = sheets.get(0);
        int sheetId = get.getProperties().getSheetId();
        String sheetTitle = get.getProperties().getTitle();
        System.out.println("sheetId = " + sheetId + " and sheetTitle = " + sheetTitle); 
              
                List<Request> requests = new ArrayList<Request>();
                requests.add(new Request()
                        .setUpdateSpreadsheetProperties(new UpdateSpreadsheetPropertiesRequest()
                        .setProperties(new SpreadsheetProperties()
                        .setTitle("My First spreadSheet")
                       // .setDefaultFormat("backgroundColor":{"blue":1.0,"green":1.0,"red":1.0})
                        ).setFields("title")));

                requests.add(new Request()
                        .setUpdateSheetProperties(new UpdateSheetPropertiesRequest()
                        .setProperties(new SheetProperties()
                        .setTitle("My First Sheet")
                       // .setTabColor("green":1.0)
                        ).setFields("title"))); 

               requests.add(new Request()
                        .setAddSheet(new AddSheetRequest()
                        .setProperties(new SheetProperties()
                        .setTitle("My Second Sheet")
                       // .setTabColor("green":1.0)
                        ))); 

//                requests.add(new Request()
//                        .setUpdateCells(new UpdateCellsRequest()
//                        .setRange('My First Sheet'!A1)
//                        ));
                
        BatchUpdateSpreadsheetRequest sheetbody =
        new BatchUpdateSpreadsheetRequest().setRequests(requests);
        BatchUpdateSpreadsheetResponse sheetresponse =
        sheetsService.spreadsheets().batchUpdate(spreadsheetId, sheetbody).execute();
        System.out.println("batchUpdate sheetresponse = " + sheetresponse);

       
        // write values into the spreadsheet
        String range = "My First Sheet!A1:B3";
        String valueInputOption=  "USER_ENTERED";
        List<List<Object>> listOfListOfStrings = new ArrayList<List<Object>>();
        // populate
        List<Object> listOfStrings = new ArrayList<Object>(); // one inner list
        listOfStrings.add("Item");
        listOfStrings.add("Cost");
        listOfListOfStrings.add(listOfStrings);

        listOfStrings = new ArrayList<Object>(); // and another one
        listOfStrings.add("one");
        listOfStrings.add("10");
        listOfListOfStrings.add(listOfStrings);

        listOfStrings = new ArrayList<Object>(); // and another one
        listOfStrings.add("two");
        listOfStrings.add("20");
        listOfListOfStrings.add(listOfStrings); 

//        String spreadsheetId = "1WvMzi_CTkTBNMEhYQYtbVYfgV-bqASUSQtm8GrSbmLc";
//        String range = "My First Sheet!B4";
//        String valueInputOption=  "USER_ENTERED";
//        List<List<Object>> listOfListOfStrings = new ArrayList<List<Object>>();
//        List<Object> listOfStrings = new ArrayList<Object>();
//        listOfStrings.add("=SUM(B1:B3)");
//        listOfListOfStrings.add(listOfStrings);

        ValueRange requestBody1 = new ValueRange();
        requestBody1.setRange(range);
        requestBody1.setMajorDimension("ROWS");
        requestBody1.setValues(listOfListOfStrings);
        Sheets.Spreadsheets.Values.Update updateRequest =
        sheetsService.spreadsheets().values().update(spreadsheetId, range, requestBody1);
        updateRequest.setValueInputOption(valueInputOption);
        UpdateValuesResponse updateResponse = updateRequest.execute();
        System.out.println(updateResponse);
        */


    // Read values from the spreadsheet
   // String spreadsheetId = "1rHHbyRDf37yvBO2rv8iULPAGF8QqCsdhgqJq2q1pJ-M";
//    String range = "Class Data!A2:E";
//    String spreadsheetId = "1bJXH2XwWgWyLVZzr-jAtiztR-1QVgjpKx5KCsmMx3x0";
//    String range = "Sheet1!A1:B2";
   // String ss = SpreadsheetApp.getMaxRows();
       // List<String> response3 = sheetsService.spreadsheets().values().batchGet(spreadsheetId).getRanges();
       //  List<String>  range1= sheetsService.spreadsheets().get(spreadsheetId).getRanges();
       // System.out.println("Range = " + range1);
//        ValueRange response3 = sheetsService.spreadsheets().values()
//    .get(spreadsheetId, range)
//    .execute();
//    List<List<Object>> values = response3.getValues();
//    if (values == null || values.size() == 0) {
//       System.out.println("No data found.");
//    } else {
//      // System.out.println("Name, Major");
//    for (List row : values) {
//    // Print columns A and E, which correspond to indices 0 and 4.
//       System.out.printf("%s, %s\n", row.get(0), row.get(1));
//      }
//    }

        // Read values from the client spreadsheet
//        String spreadsheetId = "1rHHbyRDf37yvBO2rv8iULPAGF8QqCsdhgqJq2q1pJ-M";
//        String range = "WAGE_2!A1:D3891";
//
//        ValueRange response3 = sheetsService.spreadsheets().values().get(spreadsheetId, range).execute();
//        List<List<Object>> values = response3.getValues();
//        if (values == null || values.isEmpty()) {
//            System.out.println("No data found in spreadsheet");
//        } else {
//        for (List row : values.subList(1,values.size() )) {
//            System.out.println("List = " + row);
//        // Print columns A to D, which correspond to indices 0 to 3.
//         String A=(String) row.get(0);
//         String B=(String) row.get(1);
//         String C=(String) row.get(2);
//         String D=(String) row.get(3);
//         System.out.printf("%s, %s, %s, %s\n", A,B,C,D);
//
//        }
//
//
//        }

         //Renaming Auto Spreadsheet
//        List<Request> requestsLast = new ArrayList<Request>();
//        requestsLast.add(new Request()
//                        .setUpdateSheetProperties(new UpdateSheetPropertiesRequest()
//                        .setProperties(new SheetProperties()
//                        .setTitle("WAGE_2")
//                        .setSheetId(1348908106)
//                       // .setTabColor("green":1.0)
//                        ).setFields("title")));
//
//        BatchUpdateSpreadsheetRequest bodyLast =
//        new BatchUpdateSpreadsheetRequest().setRequests(requestsLast);
//        BatchUpdateSpreadsheetResponse responseLast =
//        sheetsService.spreadsheets().batchUpdate("1rHHbyRDf37yvBO2rv8iULPAGF8QqCsdhgqJq2q1pJ-M", bodyLast).execute();


    }



        
}
