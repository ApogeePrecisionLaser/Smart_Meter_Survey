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
import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;

import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.*;
import com.google.api.services.drive.Drive;

import com.smartMeterSurvey.general.model.DownloadZipFilesModel;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Com7_2
 */
public class share {

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
    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/drive-java-quickstart
     */
    private static final List<String> SCOPES = Arrays.asList(DriveScopes.DRIVE_FILE);

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

//        java.io.File file = new java.io.File("E:\\NetBeans Project\\UserDetail\\src\\java\\com\\general\\model\\client_secret.json");
    //    java.io.File file = new java.io.File("E:\\Manpreet.Workspace\\UserDetail\\src\\java\\com\\general\\model\\client_secret.json");
    //   java.io.File file = new java.io.File("E:\\Netebeans Project\\Smart_Meter_survey\\src\\java\\com\\general\\model\\client_secret.json");
        java.io.File file = new java.io.File("C:\\google_json_file\\client_secret.json");

        java.io.FileInputStream in = new java.io.FileInputStream(file);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(DATA_STORE_FACTORY)
                .setAccessType("offline")
                .build();

        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver())
                .authorize("user");

        System.out.println("Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    /**
     * Build and return an authorized Drive client service.
     * @return an authorized Drive client service
     * @throws IOException
     */
    public static Drive getDriveService() throws IOException {
        Credential credential = authorize();
        return new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public static void shareSpreadsheet(String spreadsheetId, Connection connection) throws IOException {
        Drive service = getDriveService();

        JsonBatchCallback<Permission> callback = new JsonBatchCallback<Permission>() {

            @Override
            public void onFailure(GoogleJsonError e, HttpHeaders responseHeaders) throws IOException {
                // Handle error
                System.err.println(e.getMessage());
            }

            @Override
            public void onSuccess(Permission permission, HttpHeaders responseHeaders) throws IOException {
                System.out.println("Permission ID: " + permission.getId());
            }
        };

        BatchRequest batch = service.batch();

        DownloadZipFilesModel psm = new DownloadZipFilesModel();
        psm.setConnection(connection);
        List<String> dataList = psm.getClientData();
        Iterator<String> itr = dataList.iterator();
        while (itr.hasNext()) {
            String client_email = itr.next();
            Permission userPermission = new Permission()
                    .setType("user")
                    .setRole("writer")
                    .setEmailAddress(client_email);
            service.permissions().create(spreadsheetId, userPermission)
                    .setFields("id")
                    .queue(batch, callback);

        }

        batch.execute();

//       FileList result = service.files().list().setPageSize(10).setFields("nextPageToken, files(id, name)").execute();

    }

}
