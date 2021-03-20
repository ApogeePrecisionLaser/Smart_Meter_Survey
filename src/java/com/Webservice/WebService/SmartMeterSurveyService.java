package com.Webservice.WebService;

import com.Bean.tableClasses.meterDetail_Bean;
import com.Webservice.Model.OHLevelEntryModel;
import com.Webservice.Model.SmartMeterSurveyServiceModel;
import com.connection.DBConnection.DBConnection;
import com.general.model.drive;
import com.general.model.spreadsheet;
import com.layer.modellLayer.MeterReadingModel;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
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
//import org.json.JSONArray;
//import org.json.simple.JSONObject;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONException;
import sun.misc.BASE64Decoder;

/**
 *
 * @author Shubham
 */
@Path("/SmartMeterSurvey")
public class SmartMeterSurveyService {

    @Context
    
    ServletContext serveletContext; 
   Connection connection = null;
    static Map<String, String> otpMap = new HashMap<String, String>();

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONObject Login(JSONObject jsonObj) throws Exception {
         JSONArray jsonArray = new JSONArray();
         JSONObject jsonObj1=new JSONObject();
        SmartMeterSurveyServiceModel webServiceModel = new SmartMeterSurveyServiceModel();
        try {

            webServiceModel.setConnection(DBConnection.getConnectionForUtf(serveletContext));
            jsonObj1=webServiceModel.isExits(jsonObj);
            System.out.println("inside SmartMeterSurveyService class login method");
        } catch (Exception ex) {
            System.out.println("ERROR : in Login() in SmartMeterSurveyWebservices : " + ex);
        }
        webServiceModel.closeConnection();
        return jsonObj1;
    }
    
     @POST
    @Path("/login2")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String Login2(String jsonObj) throws Exception {
        String str="";
        try {
            str="hey welcome";
            System.out.println("com.Webservice.WebService.SmartMeterSurveyService.Login2()");
        } catch (Exception ex) {
            System.out.println("ERROR : in Login() in SmartMeterSurveyWebservices : " + ex);
        }        
        return str;
    }

    @POST
    @Path("/registerUser")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String registerUser(JSONObject jsonObj) throws Exception {
        System.out.println("UserAppWebServices...");
        SmartMeterSurveyServiceModel webServiceModel = new SmartMeterSurveyServiceModel();
        try {
            webServiceModel.setConnection(DBConnection.getConnectionForUtf(serveletContext));
        } catch (Exception ex) {
            System.out.println("ERROR : in registerUser() in SmartMeterSurveyWebservices : " + ex);
        }
        int a = webServiceModel.registerUser(jsonObj);
        System.out.println("Data Retrived : " + jsonObj);
        webServiceModel.closeConnection();
//        if (a > 0) {
//            sendOTP(jsonObj.get("mobile_no").toString());
//            //return "success";
//        } //else
        //return "fail";
        return a + "";
    }

    @POST
    @Path("/forgetPassword")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String forgetPassword(String mobile_no) throws Exception {
        System.out.println("forgetPassword in UserAppWebServices...");
        SmartMeterSurveyServiceModel webServiceModel = new SmartMeterSurveyServiceModel();
        try {
            webServiceModel.setConnection(DBConnection.getConnectionForUtf(serveletContext));
        } catch (Exception ex) {
            System.out.println("ERROR : in forgetPassword() in UserAppWebServices : " + ex);
        }
        int a = webServiceModel.verifyMobileNo(mobile_no);
        System.out.println("Data Retrived : " + mobile_no);
        webServiceModel.closeConnection();
        if (a > 0) {
            sendOTP(mobile_no);
            return "success";
        } else {
            return "fail";
        }
    }

    @POST
    @Path("/resetPassword")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String resetPassword(JSONObject json) throws Exception {
        System.out.println("forgetPassword in UserAppWebServices...");
        SmartMeterSurveyServiceModel webServiceModel = new SmartMeterSurveyServiceModel();
        try {
            webServiceModel.setConnection(DBConnection.getConnectionForUtf(serveletContext));
        } catch (Exception ex) {
            System.out.println("ERROR : in forgetPassword() in UserAppWebServices : " + ex);
        }
        int a = webServiceModel.resetPassword(json);
        System.out.println("Data Retrived : " + json);
        webServiceModel.closeConnection();
        if (a > 0) {
            return "success";
        } else {
            return "fail";
        }
    }

    @POST
    @Path("/sendOTP")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String sendOTP(String mobile_no) throws Exception {
        String otp = "";
        System.out.println("UserAppWebServices...");
        SmartMeterSurveyServiceModel webServiceModel = new SmartMeterSurveyServiceModel();
        System.out.println("Data Retrived : " + mobile_no);
        int a = webServiceModel.verifyMobileNo(mobile_no);
        if (a == 0) {
            otp = webServiceModel.random(6);
            otpMap.put(mobile_no, otp);
            webServiceModel.sendSmsToAssignedFor(mobile_no, "OTP:- " + otp);
            return otp;
        } else {
            return "-1";
        }
    }

    @POST
    @Path("/reSendOTP")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String reSendOTP(String mobile_no) throws Exception {
        String otp = "";
        System.out.println("UserAppWebServices...");
        SmartMeterSurveyServiceModel webServiceModel = new SmartMeterSurveyServiceModel();
        otp = otpMap.get(mobile_no);
        if (otp != null) {
            webServiceModel.sendSmsToAssignedFor(mobile_no, "OTP:- " + otp);
        } else {
            otp = "-1";
        }
        System.out.println("Data Retrived : " + mobile_no);
        return otp;
    }

    @POST
    @Path("/verifyOTP")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String verifyOTP(String mobile_no_otp) throws Exception {
        String otp = "";
        JSONObject jsonObj = new JSONObject();
        System.out.println("SmartMeterSurveyServices...");
        String mobile_no = "";
        String[] array = mobile_no_otp.split("_");
        if (array.length == 2) {
            mobile_no = array[0];
            otp = array[1];
        } else {
            System.out.println("SmartMeterSurveyServices...verifyOTP..." + mobile_no_otp);
        }
        if (otp.equals(otpMap.get(mobile_no))) {
            otpMap.remove(mobile_no);
            return "success";
        } else {
            return "fail";
        }
    }

    @POST
    @Path("/meterDetail")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONObject meterDetail() throws Exception {
        JSONObject json = new JSONObject();
        SmartMeterSurveyServiceModel webServiceModel = new SmartMeterSurveyServiceModel();
        try {
            webServiceModel.setConnection(DBConnection.getConnectionForUtf(serveletContext));
        } catch (Exception ex) {
            System.out.println("ERROR : in meterDetail() in SmartMeterSurveyWebservices : " + ex);
        }
        json.put("MeterDetailData", webServiceModel.getMeterDetilData());
        json.put("ReasonData", webServiceModel.getReasons());
        webServiceModel.closeConnection();
        return json;
    }

    @POST
    @Path("/meterReading")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String meterReading(JSONObject json) throws Exception {
        SmartMeterSurveyServiceModel webServiceModel = new SmartMeterSurveyServiceModel();
        try {
            webServiceModel.setConnection(DBConnection.getConnectionForUtf(serveletContext));
        } catch (Exception ex) {
            System.out.println("ERROR : in meterReading() in SmartMeterSurveyWebservices : " + ex);
        }
        String meter_no = json.get("meter_no").toString();
        String meter_reading = json.get("meter_reading") == null ? "0" : json.get("meter_reading").toString();
        String no_of_occupant = json.get("no_of_occupant").toString();
        String date_time = json.get("date_time").toString();
        String latitude = json.get("latitude").toString();
        String longitude = json.get("longitude").toString();
        String reason_id = json.get("reason_id") == null ? "" : json.get("reason_id").toString();
        int id = webServiceModel.insertRecord(meter_no, meter_reading, date_time, latitude, longitude, no_of_occupant, reason_id);
        int affected = 0;
        if (id > 0) {
            affected = id;
        }
      JSONObject jsn = new JSONObject(json.toString());
        JSONArray jsonArraay = jsn.getJSONArray("image");
        int size = jsonArraay.length();
        for (int i = 0; i < size; i++) {
           JSONObject jsonObject = jsonArraay.getJSONObject(i);
            byte[] fileAsBytes = new BASE64Decoder().decodeBuffer(jsonObject.getString("byte_arr"));
            String fileName = jsonObject.getString("imgname");
            String path = "C:/ssadvt_repository/SmartMeterSurvey/MeterReading/" + id;
            makeDirectory(path);
            String file = path + "/" + fileName;
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(fileAsBytes);
            outputStream.close();
            int affect = webServiceModel.insertImageRecord(fileName, id, 3);
            drive d = new drive();
            d.drive(fileName, file, affect, affected + "");
            MeterReadingModel mr=new MeterReadingModel();
            mr.setConnection(webServiceModel.getConnection());
           List<meterDetail_Bean> list= mr.ShowData2(id);
           List<meterDetail_Bean> list1= mr.ShowData(-1,-1,"","","","");
           int dataListSize2 = list1.size();
           int dataListSize1 = list.size()+2;
            String sheetRange1 = "Sheet1!A1:N" + dataListSize1;
            System.out.println(list+"--"+dataListSize1+"--"+sheetRange1);
           spreadsheet.uploadGoogleSheetAppendValue(list, dataListSize1, sheetRange1,dataListSize2);
        }
        webServiceModel.closeConnection();
        if (affected > 0) {
            return "success";
        } else {
            return "fail";
        }
    }

    public boolean makeDirectory(String dirPathName) {
        boolean result = false;
        File directory = new File(dirPathName);
        if (!directory.exists()) {
            result = directory.mkdir();
        }
        return result;
    }

    @POST
    @Path("/getHistory")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONObject getHistory(String meter_no) throws Exception {
        SmartMeterSurveyServiceModel webServiceModel = new SmartMeterSurveyServiceModel();
        JSONObject jsonObj = new JSONObject();
        try {
            webServiceModel.setConnection(DBConnection.getConnectionForUtf(serveletContext));
            jsonObj.put("data", webServiceModel.getHistory(meter_no));
        } catch (Exception ex) {
            System.out.println("ERROR : in Login() in SmartMeterSurveyWebservices : " + ex);
        }
        webServiceModel.closeConnection();
        return jsonObj;

//        ;
//
//
//;
//
//;
    }

    @POST
    @Path("/getDataTNPT")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONObject getTreeAndNode() throws Exception {
        SmartMeterSurveyServiceModel webServiceModel = new SmartMeterSurveyServiceModel();
        JSONObject jsonObj = new JSONObject();
        try {
            webServiceModel.setConnection(DBConnection.getConnectionForUtf(serveletContext));
            jsonObj.put("pipe_typeData", webServiceModel.getDataPipeType());
            jsonObj.put("nodeData", webServiceModel.getDataNode());
            jsonObj.put("treeData", webServiceModel.getDataTree());
            jsonObj.put("type_of_bend", webServiceModel.getDataTree());
        } catch (Exception ex) {
            System.out.println("ERROR : in Login() in SmartMeterSurveyWebservices : " + ex);
        }
        webServiceModel.closeConnection();
        return jsonObj;

//        ;
//
//
//;
//
//;
    }

    @POST
    @Path("/watertpn")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONObject getwatertpn() throws Exception {
        SmartMeterSurveyServiceModel webServiceModel = new SmartMeterSurveyServiceModel();
        JSONObject jsonObj = new JSONObject();
        try {
            webServiceModel.setConnection(DBConnection.getConnectionForUtf(serveletContext));
            jsonObj.put("data", webServiceModel.getwatertpn());

        } catch (Exception ex) {
            System.out.println("ERROR : in Login() in SmartMeterSurveyWebservices : " + ex);
        }
        webServiceModel.closeConnection();
        return jsonObj;

//        ;
//
//
//;
//
//;
    }

    @POST
    @Path("/tank")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONObject gettank() throws Exception {
        SmartMeterSurveyServiceModel webServiceModel = new SmartMeterSurveyServiceModel();
        JSONObject jsonObj = new JSONObject();
        try {
            webServiceModel.setConnection(DBConnection.getConnectionForUtf(serveletContext));
            jsonObj.put("data", webServiceModel.gettank());

        } catch (Exception ex) {
            System.out.println("ERROR : in Login() in SmartMeterSurveyWebservices : " + ex);
        }
        webServiceModel.closeConnection();
        return jsonObj;

//        ;
//
//
//;
//
//;
    }

    @POST
    @Path("/tanklevel")       
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONObject gettanklevel() throws Exception {
        SmartMeterSurveyServiceModel webServiceModel = new SmartMeterSurveyServiceModel();
        JSONObject jsonObj = new JSONObject();
        try {
            webServiceModel.setConnection(DBConnection.getConnectionForUtf(serveletContext));
            jsonObj.put("data", webServiceModel.gettanklevel());
            jsonObj.put("sumpwell_data", webServiceModel.getOht_sumpwell());
            jsonObj.put("meter_readings", webServiceModel.getMeterReading());

        } catch (Exception ex) { 
            System.out.println("ERROR : in Login() in SmartMeterSurveyWebservices : " + ex);
        }
        webServiceModel.closeConnection();
        return jsonObj;
    }

    @POST
    @Path("/tanklevelhistory")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONObject gettanklevel(String overheadtank_id) throws Exception {
        SmartMeterSurveyServiceModel webServiceModel = new SmartMeterSurveyServiceModel();
        JSONObject jsonObj = new JSONObject();
        try {
            webServiceModel.setConnection(DBConnection.getConnectionForUtf(serveletContext));
            jsonObj.put("data", webServiceModel.gettanklevelHistory(overheadtank_id));

        } catch (Exception ex) {
            System.out.println("ERROR : in Login() in SmartMeterSurveyWebservices : " + ex);
        }
        webServiceModel.closeConnection();
        return jsonObj;
    }

    @POST
    @Path("/min_max_level")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONObject getMinMaxlevel(String overheadtank_id) throws Exception {
        SmartMeterSurveyServiceModel webServiceModel = new SmartMeterSurveyServiceModel();
        JSONObject jsonObj = new JSONObject();
        try {
            webServiceModel.setConnection(DBConnection.getConnectionForUtf(serveletContext));
            jsonObj.put("data", webServiceModel.getMinMaxLevel(overheadtank_id));

        } catch (Exception ex) {
            System.out.println("ERROR : in Login() in SmartMeterSurveyWebservices : " + ex);
        }
        webServiceModel.closeConnection();
        return jsonObj;
    }

    @POST
    @Path("OnOfflevel")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONObject getOnOfflevel() throws Exception {
        SmartMeterSurveyServiceModel webServiceModel = new SmartMeterSurveyServiceModel();
        JSONObject jsonObj = new JSONObject();
        try {
            webServiceModel.setConnection(DBConnection.getConnectionForUtf(serveletContext));
            jsonObj.put("data", webServiceModel.getOnOffLevel());
            //jsonObj.put("sumpwell_data", webServiceModel.getOht_sumpwell());
            

        } catch (Exception ex) {
            System.out.println("ERROR : in Login() in SmartMeterSurveyWebservices : " + ex);
        }
        webServiceModel.closeConnection();
        return jsonObj;
    }
//   @POST
//   @Path("/cityList")
//   @Produces(MediaType.APPLICATION_JSON)
//   @Consumes(MediaType.APPLICATION_JSON)
//   public JSONObject cityList()throws Exception{
//        JSONObject jsonObj =new JSONObject();
//      SmartMeterSurveyServiceModel webServiceModel = new SmartMeterSurveyServiceModel();
//       try{
//           webServiceModel.setConnection(DBConnection.getConnectionForUtf(serveletContext));
//       }catch(Exception ex){
//           System.out.println("ERROR : in vehicleDetail() in RideWebservices : " + ex);
//       }
//     JSONArray jsonArray=webServiceModel.getCityList();
//     jsonObj.put("city", jsonArray);
//        webServiceModel.closeConnection();
//      return jsonObj;
//}
//http://45.114.142.35:8080/Smart_Meter_survey/resources/SmartMeterSurvey/sendSMS
    @POST
    @Path("/sendSMS")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String sendSMS(JSONObject json) throws Exception {
        String otp = "success";
        System.out.println("check SMS...");
        SmartMeterSurveyServiceModel webServiceModel = new SmartMeterSurveyServiceModel();
        String mobile_no = json.get("MobileNo").toString();
        String message = json.get("Message").toString();

          System.out.println("mobile_no");
          System.out.println(message);
        webServiceModel.sendSmsToAssignedFor(mobile_no, "OTP:- " + otp);
        return otp;

    }
    
//    @POST
//    @Path("/all_tank_status")
//    @Produces(MediaType.APPLICATION_JSON)//http://192.168.1.15:8080/Smart_Meter_survey/resources/SmartMeter/smartmeterdisplay
//    @Consumes(MediaType.APPLICATION_JSON)
//    public JSONObject sendLiveTankData(String dummy) {
//        JSONObject obj1 = new JSONObject();    
//        org.json.simple.JSONArray arrayObj = new org.json.simple.JSONArray();
//        OHLevelEntryModel oHLevelEntryModel = new OHLevelEntryModel();
//        
//        try{
//            oHLevelEntryModel.setConnection(DBConnection.getConnectionForUtf(serveletContext));
//            oHLevelEntryModel.setContext(serveletContext);
//            
//            arrayObj = oHLevelEntryModel.getAllOverHeadTankData();
//            
//            obj1.put("tank_status", arrayObj);
////               PrintWriter out = response.getWriter();
////               out.print(obj1);
////             if(response != null && response.length > 0){
////                       result = "Successful!!";
////                   }
////                return obj1;     
//        }catch(Exception e){     
//        }
//        //System.out.println("request come from jabalpur"+ test);
//        return obj1;  
//    }




//    @POST
//    @Path("/test")
//    @Produces(MediaType.APPLICATION_JSON)//http://45.114.142.35:8080/Smart_Meter_survey/resources/SmartMeterSurvey/test
//    @Consumes(MediaType.APPLICATION_JSON)
//    public String d(String test) {
//
//        System.out.println("request come from jabalpur"+ test);
//
//        return "success";
//    }
    @POST
    @Path("/test")
    @Produces(MediaType.TEXT_PLAIN)//http://45.114.142.35:8080/Smart_Meter_survey/resources/SmartMeterSurvey/test
    @Consumes(MediaType.TEXT_PLAIN)
    public String d(String test) {

        System.out.println("request come from jabalpur"+ test);

        return "success";
    }
    
      @POST
    @Path("/graph")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String graph(JSONObject json) throws Exception {
        SmartMeterSurveyServiceModel webServiceModel = new SmartMeterSurveyServiceModel();
        try {    
            webServiceModel.setConnection(DBConnection.getConnectionForUtf(serveletContext));
        } catch (Exception ex) {
            System.out.println("ERROR : in meterReading() in SmartMeterSurveyWebservices : " + ex);
        }
        String tank_Id = json.get("oid").toString();
     
        String date_time = json.get("date_time").toString();    
        date_time=date_time.replace("20", "");
        String overheadtankname = json.get("overheadtank_name").toString();
      
       String device_id=webServiceModel.getDeviceId(tank_Id);
      int id=0;
        int affected = 0;
        if (device_id!="") {
            affected = 1;
        }
    
       
        webServiceModel.closeConnection();
        if (affected > 0) {
            String url="120.138.10.251:8080/Smart_Meter_survey/MobileGraph?task=VIEW_GRAPH2&did='"+device_id+"'&oid='"+tank_Id+"'&ohname='"+overheadtankname+"'&searchDate='"+date_time+"'";
            String finalurl=url.replace("'", "");
           // finalurl=url.replace(" ", "");
             
            return finalurl;
        } else {
            return "fail";
        }
    }
    
      @POST
    @Path("/graph1")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String graph1(JSONObject json) throws Exception {
        SmartMeterSurveyServiceModel webServiceModel = new SmartMeterSurveyServiceModel();
        try {    
            webServiceModel.setConnection(DBConnection.getConnectionForUtf(serveletContext));
        } catch (Exception ex) {
            System.out.println("ERROR : in meterReading() in SmartMeterSurveyWebservices : " + ex);
        }
        String tank_Id = json.get("oid").toString();
        String energyid = json.get("energyid").toString();
       // String ohlevelid = json.get("ohlevelid").toString();
        String ohlevelid = "2846336";
     
        String date_time = json.get("date_time").toString();    
        date_time=date_time.replace("20", "");
        String overheadtankname = json.get("overheadtank_name").toString();
      
       String device_id=webServiceModel.getDeviceId(tank_Id);
      int id=0;
        int affected = 0;
        if (device_id!="") {
            affected = 1;
        }
    
     // http://120.138.10.251:8080/Smart_Meter_survey/CanvasJSController111?task=VIEW_GRAPH2&ohlevel_id=2845900&energy_id=2845761&did=D_20%20&oid=30%20&ohname=Marghatai%20Sumpwell
        webServiceModel.closeConnection();
        if (affected > 0) {
         //   String url="120.138.10.251:8080/Smart_Meter_survey/MobileGraph?task=VIEW_GRAPH2&did='"+device_id+"'&oid='"+tank_Id+"'&ohname='"+overheadtankname+"'&searchDate='"+date_time+"'";
            String url="http://120.138.10.251:8080/Smart_Meter_survey/CanvasJSController111?task=VIEW_GRAPH2&ohlevel_id='"+ohlevelid+"'&energy_id='"+energyid+"'&did=D_20&oid='"+tank_Id+"'&ohname='"+overheadtankname+"'&searchDate='"+date_time+"'";
            String finalurl=url.replace("'", "");
           // finalurl=url.replace(" ", "");
             
            return finalurl;
        } else {
            return "fail";
        }
    }
}
