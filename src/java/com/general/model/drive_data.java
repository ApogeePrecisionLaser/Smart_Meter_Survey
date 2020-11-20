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
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Manpreet
 */

public class drive_data {
 private static Connection connection;
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
  //      java.io.File file = new java.io.File("E:\\Netebeans Project\\Smart_Meter_survey\\src\\java\\com\\general\\model\\client_secret.json");
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
        return new Drive.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
   
  /*  public static String getDriveFileId(String image_name) throws IOException {

        Drive service = getDriveService();

        String fileId = "";
        String pageToken = null;
        do {
            FileList result = service.files().list().setQ("mimeType='image/jpeg'").setSpaces("drive").setFields("nextPageToken, files(id, name)").setPageToken(pageToken).execute();
            for (File file : result.getFiles()) {
//                System.out.printf("Found file: %s (%s)\n",
//                        file.getId() , file.getName());
                if ((file.getName()).equalsIgnoreCase(image_name)) {
                    fileId = file.getId();
                    System.out.println("fileId = " + fileId);
                }
            }
            pageToken = result.getNextPageToken();
        } while (pageToken != null);

        return fileId;

    }  */

    public static void main(String arg[]) throws IOException {
//         getDriveFolderId("993", "IMG_20170325_142440.jpg");
        setConnection();
        List<String> list = new ArrayList<String>();
        list = getFolderFileList();
        getDriveFolderId(list);
    }
    public static void setConnection() {
        try {
            System.out.println("hii");
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/smart_meter_survey?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", "jpss_2", "jpss_1277");
        } catch (Exception e) {
            System.out.println("ReadMailModel setConnection() Error: " + e);
        }
    }
        public static List<String> getFolderFileList() {
        List list = new ArrayList();
        String query = "select key_person_id,image_name,general_image_details_id from general_image_details;";

        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next()) {
                String folder_name = rs.getString("key_person_id");
                String image_name = rs.getString("image_name");
                String general_image_details_id = rs.getString("general_image_details_id");
                String temp = folder_name + "&" + image_name + "&" + general_image_details_id;
                list.add(temp);
            }
        } catch (Exception e) {
            System.out.println("Error:DownloadZipFilesModel in getFolderFileList() : " + e);
        }

        return list;
    }
         public static int insertfile(int general_image_details_id, String file_id) {
        int rowsAffected = 0;
        String query = "INSERT INTO drivefile (general_image_details_id,file_id) "
                + " VALUES (?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, general_image_details_id);
            ps.setString(2, file_id);

            rowsAffected = ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("ERROR inside drive_data insertfile - " + e);
        }
        return rowsAffected;
    }

    public static int insertfolder(String folder_id, String image_uploaded_for_id, int general_image_details_id) {
        int rowsAffected = 0;
        String query = "INSERT INTO folder (drive_folder_id, image_uploaded_for_id, general_image_details_id) "
                + " VALUES (?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, folder_id);
            ps.setString(2, image_uploaded_for_id);
            ps.setInt(3, general_image_details_id);

            rowsAffected = ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                rowsAffected = rs.getInt(1);
            }

        } catch (Exception e) {
            System.out.println("ERROR inside drive_data insertfolder - " + e);
        }
        return rowsAffected;
    }

    public static void getDriveFolderId(List<String> Folderlist) throws IOException {
        Drive service = getDriveService();
        String pageToken = null;
        FileList result = null;
        do {
             result = service.files().list()
                    .setSpaces("drive")
                    .setFields("nextPageToken, files(id, name)")
                    .setPageToken(pageToken)
                    .execute();

//            System.out.printf("Found result: %s (%s)\n", result);
           insertDriveFile(Folderlist, result);
           insertDriveFolder(Folderlist, result);

            pageToken = result.getNextPageToken();
        } while (pageToken != null);
        
    }
    
    public static void insertDriveFile(List<String> Folderlist, FileList result) throws IOException {

        for (int i = 0; i < Folderlist.size(); i++) {
            String listValue = Folderlist.get(i);
            String folder_name = listValue.split("&")[0];
            String image_name = listValue.split("&")[1];
            int general_image_details_id = Integer.parseInt(listValue.split("&")[2]);
            String fileId = "";
            String folderId = "";
            //     for (File file : result.getFiles()) {
            //   if ((file.getName()).equalsIgnoreCase(folder_name)) {
            //     folderId = file.getId();
            //     System.out.println("folderId = " + folderId);

            for (File file1 : result.getFiles()) {
                if ((file1.getName()).equalsIgnoreCase(image_name)) {
                    fileId = file1.getId();
                    System.out.println("fileId = " + fileId);
                    insertfile(general_image_details_id, fileId);
                }

                //    int folder_id = insertfolder(folderId, "6");

            }
            //   }
            //  }

        }
    }

    public static void insertDriveFolder(List<String> Folderlist, FileList result) throws IOException {

        for (int i = 0; i < Folderlist.size(); i++) {
            String listValue = Folderlist.get(i);
            String folder_name = listValue.split("&")[0];
            String image_name = listValue.split("&")[1];
            int general_image_details_id = Integer.parseInt(listValue.split("&")[2]);
            String fileId = "";
            String folderId = "";
            for (File file : result.getFiles()) {
                if ((file.getName()).equalsIgnoreCase(folder_name)) {
                    folderId = file.getId();
                    System.out.println("folderId = " + folderId);

                    insertfolder(folderId, "6", general_image_details_id);

                }
            }

        }
    }
    
}
