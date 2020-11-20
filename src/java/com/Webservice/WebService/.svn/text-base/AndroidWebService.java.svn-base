package com.Webservice.WebService;

import com.Webservice.Model.SmartMeterSurveyServiceModel;
import com.connection.DBConnection.DBConnection;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import sun.misc.BASE64Decoder;

/**
 *
 * @author Shubham
 */
@Path("/AndroidWebService")
public class AndroidWebService {

    @Context
    ServletContext serveletContext;
    Connection connection = null;
    static Map<String, String> otpMap = new HashMap<String, String>();
//
    @POST
    @Path("/nearestOH")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONObject nearestOH(String lat_long) throws Exception
    {
        String user_id, password;
        String latitude = lat_long.split("_")[0];
        String longtitude = lat_long.split("_")[1];
        SmartMeterSurveyServiceModel webServiceModel = new SmartMeterSurveyServiceModel();
        try {
            webServiceModel.setConnection(DBConnection.getConnectionForUtf(serveletContext));
        } catch (Exception ex) {
            System.out.println("ERROR : in Login() in AppointmentWebservices : " + ex);
        }
        String data = webServiceModel.isExits(latitude, longtitude);
        JSONObject jsonObj1=new JSONObject(data);
        webServiceModel.closeConnection();
        return jsonObj1;
    }

//    @POST
//    @Path("/verify_mobileno")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public String VerifyMoileNo(String mobile_no) throws Exception {
//        JSONObject obj = new JSONObject();
//        Response res = null;
//        String reply = "";
//
//        SmartMeterSurveyServiceModel webServiceModel = new SmartMeterSurveyServiceModel();
//        try {
//            webServiceModel.setConnection(DBConnection.getConnectionForUtf(serveletContext));
//        } catch (Exception ex) {
//            System.out.println("ERROR : in Login() in AppointmentWebservices : " + ex);
//        }
//        int affected = webServiceModel.getPatientId(mobile_no);
//        webServiceModel.closeConnection();
//        if (affected > 0) {
//            return "success";
//        } else {
//            return "error";
//        }
//    }

    @POST
    @Path("/sendOTP")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String sendOTP(String mobile_no) throws Exception
    {
        String otp = "";
//        String mobile_no = "";
        System.out.println("UserAppWebServices...");
        SmartMeterSurveyServiceModel webServiceModel = new SmartMeterSurveyServiceModel();
        try {
            webServiceModel.setConnection(DBConnection.getConnectionForUtf(serveletContext));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
//      int num= webServiceModel.insertPatientRecord(jsonObject);
//        mobile_no = jsonObject.get("mobile_no").toString();
        otp = webServiceModel.random(6);
        otpMap.put(mobile_no, otp);
        webServiceModel.sendSmsToAssignedFor(mobile_no, "OTP:- " + otp);
        System.out.println("Data Retrived : " + mobile_no);
        webServiceModel.closeConnection();
//       if(num>0)
        return "success";
//       else
//       return "error";
    }

    @POST
    @Path("/reSendOTP")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String reSendOTP(String mobile_no) throws Exception
    {
        String otp = "";
        System.out.println("PocServiceConnectionServices...");
        SmartMeterSurveyServiceModel webServiceModel = new SmartMeterSurveyServiceModel();
        otp = otpMap.get(mobile_no);
        if (otp != null) {
            webServiceModel.sendSmsToAssignedFor(mobile_no, "OTP:- " + otp);
        } else {
            otp = "fail";
        }
        System.out.println("Data Retrived : " + mobile_no);
        webServiceModel.closeConnection();
        if (!otp.equals("fail")) {
            return "success";
        } else {
            return "error";
        }
    }

    @POST
    @Path("/verifyOTP")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String verifyOTP(String mobile_no_otp) throws Exception
    {
        String otp = "";
        String status = "";
        JSONObject jsonObj = new JSONObject();
        System.out.println("PocServiceConnectionServices...");
        String mobile_no = mobile_no_otp.split("_")[0];
        otp = mobile_no_otp.split("_")[1];
        SmartMeterSurveyServiceModel webServiceModel = new SmartMeterSurveyServiceModel();
        try
        {
            webServiceModel.setConnection(DBConnection.getConnectionForUtf(serveletContext));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (otp.equals(otpMap.get(mobile_no)))
        {
            otpMap.remove(mobile_no);
            webServiceModel.closeConnection();
            status= "success";
        }
        return status;
    }
}
