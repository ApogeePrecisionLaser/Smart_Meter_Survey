/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.Webservice.WebService;


import com.Webservice.Model.OHLevelEntryModel;
import com.connection.DBConnection.DBConnection;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

//import org.json.JSONObject;



/**
 *
 * @author Navitus1
 */

@Path("/SmartMeter")
public class OHLevelEntryWebService {

    @Context
    ServletContext servletContext;
    Connection connection = null;
    @POST
    @Path("/smartmeterdata")
    @Produces(MediaType.MULTIPART_FORM_DATA)//http://192.168.1.15:8080/Smart_Meter_survey/resources/SmartMeter/smartmeterdata
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public byte[] insertEnergyMeterData(@Context HttpServletRequest requestContext,byte[] receivedBytes) {
        HttpSession session  = requestContext.getSession();
        byte[] response = null;
        String result = "Sorry!! something went wrong. ";
        System.out.println("data at " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()) + ": "+ requestContext.getRemoteAddr());
        for (int i = 0; i < receivedBytes.length; i++) {
            System.out.print(" " + receivedBytes[i]);
        }
        String changeCommand = session.getAttribute("sp14") == null?"":session.getAttribute("sp14").toString();
        servletContext.getInitParameter("driverClass");
        System.out.println("");
        OHLevelEntryModel oHLevelEntryModel = new OHLevelEntryModel();
        if (receivedBytes != null && receivedBytes.length!=0)  {
            try {
                oHLevelEntryModel.setConnection(DBConnection.getConnectionForUtf(servletContext));
                oHLevelEntryModel.setContext(servletContext);
                oHLevelEntryModel.setSession(session);
                String responseVal = null;
//                if (receivedBytes.length == 35)
//                    responseVal = wsEnergyMeterModel.junctionRefreshFunctionOne(receivedBytes,5,false);
//                else if (receivedBytes.length == 17)
//                    responseVal = wsEnergyMeterModel.junctionRefreshFunctionNine(receivedBytes,5,false);
//                else
                    responseVal = oHLevelEntryModel.junctionRefreshFunction(receivedBytes,5,false);
                     System.out.println("Server Side Response--- : " + responseVal);
                if (responseVal != null && !responseVal.isEmpty()) {
                   response =  oHLevelEntryModel.sendResponse(responseVal);
                   if(response != null && response.length > 0){
                       result = "Successful!!";
                   }
                }
            } catch (Exception ex) {
                System.out.println("Exception in insertEnergyMeterData"+ ex);
            }finally{
                oHLevelEntryModel.closeConnection();
            }
        }
        System.out.println("result : " + result);
        return response;
    }
 
    @POST
    @Path("/smartmeterdisplay")
    @Produces(MediaType.MULTIPART_FORM_DATA)//http://192.168.1.15:8080/Smart_Meter_survey/resources/SmartMeter/smartmeterdisplay
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public byte[] displayData(@Context HttpServletRequest requestContext,byte[] receivedBytes) {
        HttpSession session  = requestContext.getSession();
        byte[] response = null;
        String result = "Sorry!! something went wrong. ";
      //  System.out.println("data at " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()) + ": "+ requestContext.getRemoteAddr());
        for (int i = 0; i < receivedBytes.length; i++) {
            System.out.print("  " + receivedBytes[i]);
        }
     //   String changeCommand = session.getAttribute("sp14") == null?"":session.getAttribute("sp14").toString();
        servletContext.getInitParameter("driverClass");
      //  System.out.println("");   
        OHLevelEntryModel oHLevelEntryModel = new OHLevelEntryModel();
        if (receivedBytes != null && receivedBytes.length!=0)  {
            try {             
                oHLevelEntryModel.setConnection(DBConnection.getConnectionForUtf(servletContext));
                oHLevelEntryModel.setContext(servletContext);
                oHLevelEntryModel.setSession(session);
                String responseVal = null;
//                if (receivedBytes.length == 35)
//                    responseVal = wsEnergyMeterModel.junctionRefreshFunctionOne(receivedBytes,5,false);
//                else if (receivedBytes.length == 17)
//                    responseVal = wsEnergyMeterModel.junctionRefreshFunctionNine(receivedBytes,5,false);
//                else

               // System.out.println("junction refresh");
                    response = oHLevelEntryModel.junctionRefreshFunction1(receivedBytes,5,false);
                           System.out.print(" Server Side Response bytes" );           
                     for (int i = 0; i < response.length; i++) {
                         if(response[i]== 0){
                         response[i]=127;
                         }
           // System.out.print(" respone-------------  " + response[i]);
        }
                     
               // if (responseVal != null && !responseVal.isEmpty()) {
                //   response =  responseVal;                                             // oHLevelEntryModel.sendResponse(responseVal);
                   if(response != null && response.length > 0){
                       result = "Successful!!";
                       
               //    }
                }
            } catch (Exception ex) {
                System.out.println("Exception in insertEnergyMeterData"+ ex);
            }finally{
                oHLevelEntryModel.closeConnection();
            }
        }
     //   System.out.println("result : " + response.toString());
        return response;
    }

    @POST
    @Path("/all_tank_status")
    @Produces(MediaType.APPLICATION_JSON)//http://192.168.1.15:8080/Smart_Meter_survey/resources/SmartMeter/smartmeterdisplay
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONObject sendLiveTankData(String dummy) {
        JSONObject obj1 = new JSONObject();    
        JSONArray arrayObj = new JSONArray();
        OHLevelEntryModel oHLevelEntryModel = new OHLevelEntryModel();
        
        try{
            oHLevelEntryModel.setConnection(DBConnection.getConnectionForUtf(servletContext));
            oHLevelEntryModel.setContext(servletContext);
            
            arrayObj = oHLevelEntryModel.getAllOverHeadTankData();
            
            obj1.put("Data", arrayObj);   
        }catch(Exception e){     
        }
        //System.out.println("request come from jabalpur"+ test);
        return obj1;  
    }
    
    @POST
    @Path("/tank_status_info")
    @Produces(MediaType.APPLICATION_JSON)//http://192.168.1.15:8080/Smart_Meter_survey/resources/SmartMeter/smartmeterdisplay
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONObject sendTankDataInfo(String test) {
        String latlon[] = test.split(",");
        String lat = latlon[0];
        String lon = latlon[1];
        JSONObject obj1 = new JSONObject();    
        JSONArray arrayObj = new JSONArray();
        OHLevelEntryModel oHLevelEntryModel = new OHLevelEntryModel();
        
        try{
            oHLevelEntryModel.setConnection(DBConnection.getConnectionForUtf(servletContext));
            oHLevelEntryModel.setContext(servletContext);
            
            arrayObj = oHLevelEntryModel.getOverHeadTankDataInfo(lat,lon);
            
            obj1.put("Data", arrayObj);   
        }catch(Exception e){     
        }
        //System.out.println("request come from jabalpur"+ test);
        return obj1;  
    }











    @POST
    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON)//http://192.168.1.15:8080/Smart_Meter_survey/resources/SmartMeter/smartmeterdisplay
    @Consumes(MediaType.APPLICATION_JSON)
    public String d(String test) {

        System.out.println("request come from jabalpur"+ test);

        return "success";
    }
}
