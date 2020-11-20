/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Webservice.Model;

import java.sql.PreparedStatement;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Com7_2
 */
public class OHLevelEntryModel {

    private static Connection connection;
    static final long Fifteen_MINUTE_IN_MILLIS=900000;
    private HttpSession session;
    private ServletContext context;
    Date dt = new Date();
    SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String cut_dt1 = df1.format(dt);
    public String junctionRefreshFunction(byte[] dataToProcess, int firstStartDataPosition, boolean testRequestFromJunction) {
        String responseVal = null;
        String[] receivedPhase1status = null;
        String[] receivedPhase2status = null;
        String[] receivedPhase3status = null;
        String[] receivedStatus = null;

        double divisor = 100;

//        int firstStartDelimiter = dataToProcess[firstStartDataPosition - 4];
//        int secStartDelimiter = dataToProcess[firstStartDataPosition - 4];
//        int dataSize1 = dataToProcess[firstStartDataPosition - 3];
//        int dataSize2 = dataToProcess[firstStartDataPosition - 2];
//        int project_no = dataToProcess[firstStartDataPosition - 1];
        byte junctionID = dataToProcess[firstStartDataPosition];
//        int program_version_no = dataToProcess[firstStartDataPosition + 1];
//        int fileNo = dataToProcess[firstStartDataPosition + 2];
//        int functionNo = dataToProcess[firstStartDataPosition + 3];

        byte twoByteData[] = new byte[2];

        twoByteData[0] = dataToProcess[firstStartDataPosition + 5];
        twoByteData[1] = dataToProcess[firstStartDataPosition + 6];
//        byte_data(twoByteData[0], twoByteData[1], junctionID);
//        if (twoByteData[1] < 0) {
//            int temp = twoByteData[1];
//            int temp1 = 256 + temp;
//            // byte b = (byte)temp1;
//            byte c = (byte) (temp >> 8);
//            System.out.println(c);
//            System.out.println(twoByteData[1] & 0xFF);
        //       }
        long voltage1 = (new BigInteger(twoByteData).longValue());

//        twoByteData[0] = dataToProcess[firstStartDataPosition + 6];
//        twoByteData[1] = (byte) Math.abs(dataToProcess[firstStartDataPosition + 7]);
//        long voltage2 = new BigInteger(twoByteData).longValue();

//        twoByteData[0] = dataToProcess[firstStartDataPosition + 8];
//        twoByteData[1] = dataToProcess[firstStartDataPosition + 9];
//        long voltage3 = new BigInteger(twoByteData).longValue() / 100;
//
//        twoByteData[0] = dataToProcess[firstStartDataPosition + 10];
//        twoByteData[1] = dataToProcess[firstStartDataPosition + 11];
//        long current1 = new BigInteger(twoByteData).longValue() / 100;
//
//        twoByteData[0] = dataToProcess[firstStartDataPosition + 12];
//        twoByteData[1] = dataToProcess[firstStartDataPosition + 13];
//        long current2 = new BigInteger(twoByteData).longValue();
//
//        twoByteData[0] = dataToProcess[firstStartDataPosition + 14];
//        twoByteData[1] = dataToProcess[firstStartDataPosition + 15];
//        long current3 = new BigInteger(twoByteData).longValue() / 100;
//
//        twoByteData[0] = dataToProcess[firstStartDataPosition + 16];
//        twoByteData[1] = dataToProcess[firstStartDataPosition + 17];
//        long activePower1 = new BigInteger(twoByteData).longValue() / 100;
//
//        twoByteData[0] = dataToProcess[firstStartDataPosition + 18];
//        twoByteData[1] = dataToProcess[firstStartDataPosition + 19];
//        long activePower2 = new BigInteger(twoByteData).longValue();
//
//        twoByteData[0] = dataToProcess[firstStartDataPosition + 20];
//        twoByteData[1] = dataToProcess[firstStartDataPosition + 21];
//        long activePower3 = new BigInteger(twoByteData).longValue() / 100;
//
//        twoByteData[0] = dataToProcess[firstStartDataPosition + 22];
//        twoByteData[1] = dataToProcess[firstStartDataPosition + 23];
//        long connectedLoad = new BigInteger(twoByteData).longValue() / 100; // totalActivePower
//
//        twoByteData[0] = dataToProcess[firstStartDataPosition + 24];
//        twoByteData[1] = dataToProcess[firstStartDataPosition + 25];
//        long powerFactor = new BigInteger(twoByteData).longValue() / 100;
//
//        byte fourByteData[] = new byte[4];
//        fourByteData[0] = dataToProcess[firstStartDataPosition + 26];
//        fourByteData[1] = dataToProcess[firstStartDataPosition + 27];
//        fourByteData[2] = dataToProcess[firstStartDataPosition + 28];
//        fourByteData[3] = dataToProcess[firstStartDataPosition + 29];
//        long consumedUnit = (long) (new BigInteger(fourByteData).longValue() / divisor);
//
//        int activity = dataToProcess[firstStartDataPosition + 30];
//        int activity1 = 1;
//        int activity2 = 0;
//        int activity3 = 0;
//        int receivedActivity = activity;
//
//        int unsignedPhase1 = dataToProcess[firstStartDataPosition + 31] & 0xFF;
//        int unsignedPhase2 = dataToProcess[firstStartDataPosition + 32] & 0xFF;
//        int unsignedPhase3 = dataToProcess[firstStartDataPosition + 33] & 0xFF;
//
//        int unsignedStatusByte = dataToProcess[firstStartDataPosition + 34] & 0xFF;
//
//        String phase1Status = Integer.toBinaryString(unsignedPhase1);
//        String phase2Status = Integer.toBinaryString(unsignedPhase2);
//        String phase3Status = Integer.toBinaryString(unsignedPhase3);
//
//        String statusByte = Integer.toBinaryString(unsignedStatusByte);
//
//        int contactorOnStatus = dataToProcess[firstStartDataPosition + 35];
//
//        int juncOnTimeHr = dataToProcess[firstStartDataPosition + 36];
//        int juncOnTimeMin = dataToProcess[firstStartDataPosition + 37];
//        int juncOffTimeHr = dataToProcess[firstStartDataPosition + 38];
//        int juncOffTimeMin = dataToProcess[firstStartDataPosition + 39];
//
//        int juncHr = dataToProcess[firstStartDataPosition + 40];
//        int juncMin = dataToProcess[firstStartDataPosition + 41];
//        int juncDat = dataToProcess[firstStartDataPosition + 42];
//        int juncMonth = dataToProcess[firstStartDataPosition + 43];
//        int juncYear = dataToProcess[firstStartDataPosition + 44];
//
//        int crc = dataToProcess[firstStartDataPosition + 45];
//        int firstLastDelimiter = dataToProcess[firstStartDataPosition + 46];
//        int secLastDelimiter = dataToProcess[firstStartDataPosition + 46];
//
//        int appHr = 0, appMin = 0, appDat = 0, appMonth = 0, appYear = 0;
//        Calendar cald = Calendar.getInstance();
//        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String dateTime = sd.format(cald.getTime());
//
//        String appDate = dateTime.split(" ")[0];
//        String appTime = dateTime.split(" ")[1];
//
//        String time[] = appTime.split(":");
//        String dte[] = appDate.split("-");
//
//        appHr = Integer.parseInt(time[0]);
//        appMin = Integer.parseInt(time[1]);
//
//        appDat = Integer.parseInt(dte[2]);
//        appMonth = Integer.parseInt(dte[1]);
//        appYear = Integer.parseInt(dte[0].substring(2));

        /*---------------------------- code started to manage one minute difference in junction time and in application time  ------------------------------------------*/


        return responseVal;
    }

    public byte[] sendResponse(String response)
            throws Exception {
        byte[] finalBytes = null;
        if (response != null && !response.isEmpty()) {

            String[] b1 = response.split(" ");
            // byte[] bytes = new byte[b1.length];
            finalBytes = new byte[b1.length + 3];
            int[] k = new int[b1.length];
            for (int j = 0; j < b1.length; j++) {
                k[j] = Integer.parseInt(b1[j]);
                //   bytes[j] = ((byte) k[j]);
            }
            finalBytes = getFinalBytes(k);

        }
        System.out.println("sent response: " + response);
        return finalBytes;
    }

    public byte[] getFinalBytes(int[] k) {
        int length = k.length;

        int dSize = 0;
        int dSum = 0;

        byte dataSize[] = new byte[2];
        byte[] finalBytes = new byte[length + 3];
        try {

            for (int i = 4; i < (length - 2); i++) {
                dSum += k[i];
                dSize++;
            }

            dataSize[0] = (byte) ((dSize >> 8) & 0xFF); //>> 8 discards the lowest 8 bits by moving all bits 8 places to the right.
            dataSize[1] = (byte) (dSize & 0xFF); //& 0xFF masks the lowest eight bits.

            byte lowByte = (byte) (dSum & 0xFF); //get lowest 8-bit
            byte crcByte = (byte) (0xFF - lowByte); // subtract lowest 8-bit from 255

            for (int i = 0, j = 0; j < finalBytes.length; j++) {
                if (j == 4) {
                    finalBytes[j] = dataSize[0];
                } else if (j == 5) {
                    finalBytes[j] = dataSize[1];
                } else if (j == finalBytes.length - 3) {
                    finalBytes[j] = crcByte;
                } else {
                    finalBytes[j] = ((byte) k[i]);
                    i++;
                }
            }

        } catch (Exception e) {
            System.out.println("Exception in EnergyMeateWebServiceModel getFinalBytes: " + e);
        }
        return finalBytes;
    }

    public int insertfolder(String level, String overheadtank_id, String level_datetime) {
        int rowsAffected = 0;
        String query = "INSERT INTO ohlevel (overheadtank_id,level,level_datetime) "
                + " VALUES (?,?,?)";
        try {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
            ps.setString(1, overheadtank_id);
            ps.setString(2, level);
            ps.setString(3, level_datetime);
            rowsAffected = ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("insertRecord ERROR inside SmartMeterSurveyServiceModel - " + e);
        }
        return rowsAffected;
    }

    public void byte_data(byte[] data, byte junction_id) throws ParseException {
        byte[] twoByteData = new byte[2];
        int ohlevel_id = 0;
        
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
     Date dateobj = new Date();
        System.out.println(df.format(dateobj));
        String datetime = df.format(dateobj);
        
        
        
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
Date date = sdf.parse(datetime);
Calendar cal = Calendar.getInstance();
cal.setTime(date); 
long date4 = cal.getTimeInMillis();

long final_date = date4+Fifteen_MINUTE_IN_MILLIS;
        
         Date revdate = new Date(final_date);
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    System.out.println(formatter.format(revdate)); 
     String dateTime_test = formatter.format(revdate); 
   
        
        
        
        String type = "";
        int temp_distribution_id_last=0;
        String created_date = "";
        String current_dateTime="";
        int previous_temp_dist_leveltype_id=0;
        int level = 0, temp_level = 0, on_off_value=0,time_diff=0,time=0;
        String query = "INSERT INTO ohlevel (level_a,level_b,overheadtank_id,remark,step,level1,level2,level3,level4,date_time) "
                + " VALUES (?,?,?,?,?,?,?,?,?,?)";
        String query9 = "INSERT INTO temp_ohlevel (level_a,level_b,overheadtank_id,remark,step,level1,level2,level3,level4) "
                + " VALUES (?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
            PreparedStatement ps9 = (PreparedStatement) connection.prepareStatement(query9);
            ps.setByte(1, data[0]);
            ps.setByte(2, data[1]);
            ps.setInt(3, junction_id);
            twoByteData[0] = data[0];
            twoByteData[1] = data[1];
            long voltage1 = (new BigInteger(twoByteData).longValue());
            ps.setString(4, ("" + voltage1).trim());
            ps.setByte(5, data[2]);
            ps.setByte(6, data[3]);
            ps.setByte(7, data[4]);
            ps.setByte(8, data[5]);
            ps.setByte(9, data[6]);
              ps.setString(10, datetime);

        //    --------------------------------- for temp_ohlevel
            ps9.setByte(1, data[0]);
            ps9.setByte(2, data[1]);
            ps9.setInt(3, junction_id);
            ps9.setString(4, ("" + voltage1).trim());
            ps9.setByte(5, data[2]);
            ps9.setByte(6, data[3]);
            ps9.setByte(7, data[4]);
            ps9.setByte(8, data[5]);
            ps9.setByte(9, data[6]);
             ps9.executeUpdate();

          //    ---------------------------------
            if (voltage1 > 0 && voltage1 < 1000) {
                context.setAttribute("overflow_" + junction_id, "0");
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    ohlevel_id = rs.getInt(1);
                }
                ///////////////////////get Date Time of latest inserted value in ohlevel table start////////
                String queryDate=" select date_time from smart_meter_survey.ohlevel ohl "
                                 +" where ohl.ohlevel_id="+ohlevel_id
                                 +" and ohl.overheadtank_id="+junction_id;

                 ResultSet rs13 = connection.prepareStatement(queryDate).executeQuery();
                              if (rs13.next()) {
                            current_dateTime = rs13.getString("date_time");
                        }

                ///////////////////////get Date Time of latest inserted value in ohlevel table end////////
                if (ohlevel_id > 0) {
                    //  String query1 = "select remark from ohlevel where ohlevel_id=( select max(ohlevel_id) from ohlevel "
                    //          + " where ohlevel_id not in (select max(ohlevel_id) from ohlevel where overheadtank_id=" + junction_id + ") and overheadtank_id=" + junction_id + " )";
                    String query1 = "select * from  (select * from ohlevel where overheadtank_id='" + junction_id + "' order by ohlevel_id desc limit 2 ) a order by ohlevel_id limit 1";
                    try {
                        ResultSet rs1 = connection.prepareStatement(query1).executeQuery();
                        if (rs1.next()) {
                            level = rs1.getInt("remark");
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    //String query4 = "select type,level,created_date from temp_distribution where overheadtank_id='" + junction_id + "'  order by temp_distribution_id desc";
                    String query4=" select td.temp_distribution_id,lt.level_type_id,type,level,td.created_date  "
                                 +" from temp_distribution td,level_type lt "
                                 +" where overheadtank_id="+junction_id
                                 +" and td.level_type_id=lt.level_type_id "
                                 +" order by temp_distribution_id desc LIMIT 1 ";
                    try {
                        ResultSet rs4 = connection.prepareStatement(query4).executeQuery();
                        if (rs4.next()) {
                            temp_distribution_id_last=rs4.getInt("temp_distribution_id");
                            type = rs4.getString("type");
                            created_date = rs4.getString("created_date");
                            temp_level = rs4.getInt("level");
                            previous_temp_dist_leveltype_id=rs4.getInt("level_type_id");
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    String query7 = "SELECT value,time FROM on_off_value where name='On' ";
                    try {
                     
                        ResultSet rs5 = connection.prepareStatement(query7).executeQuery();
                        if (rs5.next()) {          
                            on_off_value = rs5.getInt("value");
                            time = rs5.getInt("time");
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
//                    String query8 = "SELECT TIMESTAMPDIFF(MINUTE,'" + cut_dt1 + "','" + created_date + "' ) ";
                    String query8 = "SELECT TIMESTAMPDIFF(MINUTE,'" + created_date + "','" + cut_dt1 + "' ) ";
                    try {

                        ResultSet rs6 = connection.prepareStatement(query8).executeQuery();
                        if (rs6.next()) {
                            time_diff = rs6.getInt(1);
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    //////////////////////////////for lekage start////////////////////
                    int previousThirdLevel=0;
                    String previousThirdLevelDate_Time="";
                    int newTimeDifference=0;
                    int levelType=0;
                    String levelTypeName="";
                    String lekageQuery="select ohlevel_id,overheadtank_id,date_time,remark from (select ohlevel_id,overheadtank_id,date_time,remark "
                                       +" from ohlevel ohl "
                                       +" where ohl.overheadtank_id="+junction_id
                                       +" order by ohlevel_id DESC limit 3) a "
                                       +" order by ohlevel_id limit 1 ";
                    try{
                    ResultSet rs5 = connection.prepareStatement(lekageQuery).executeQuery();
                    while(rs5.next()){
                    previousThirdLevel=rs5.getInt("remark");
                    previousThirdLevelDate_Time=rs5.getString("date_time");

                        }
                    //if (previousThirdLevel > Integer.parseInt((""+voltage1))) {
                        ///////////find water level difference////////////////////
                        int levelDifference=previousThirdLevel-(Integer.parseInt(""+voltage1));
                        //double time_diff1=0.0;
                        double db_level_ratio=0.0;
                        String lekageQuery1="select level_ratio,time_diff "
                                           +" from smart_meter_survey.leakage_value";
                        ResultSet rs2 = connection.prepareStatement(lekageQuery1).executeQuery();
                    while(rs2.next()){
                    db_level_ratio=rs2.getDouble("level_ratio");//0.5
                     //time_diff1=rs2.getDouble("time_diff");//9.0
                        }
////////////////////////////calculate time difference start/////////////
                     String query10="SELECT TIMESTAMPDIFF(MINUTE,'" + previousThirdLevelDate_Time + "','" + current_dateTime + "' ) ";
                        try {

                        ResultSet rs6 = connection.prepareStatement(query10).executeQuery();
                        if (rs6.next()) {
                            newTimeDifference = rs6.getInt(1);
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }

////////////////////////////calculate time difference start/////////////

                        double currentlevelRatio = (levelDifference / Double.parseDouble(newTimeDifference+".0"));
                        if(currentlevelRatio < db_level_ratio && currentlevelRatio > 0.005){   
                            /////////////////////////get level_type for  Leakage////////////////
                            String query11="Select level_type_id,type_name from level_type where type_name='Leakage'";
                            ResultSet rs6 = connection.prepareStatement(query11).executeQuery();
                            if (rs6.next()) {
                            levelType = rs6.getInt("level_type_id");
                            levelTypeName=rs6.getString("type_name");
                        }
                        }
                         if(currentlevelRatio >= 0 && currentlevelRatio <= 0.001){
                            /////////////////////////get level_type for  Stable ////////////////
                              String query11="Select level_type_id,type_name from level_type where type_name='Stable'";
                           ResultSet rs6 = connection.prepareStatement(query11).executeQuery();
                            if (rs6.next()) {
                            levelType = rs6.getInt("level_type_id");
                            levelTypeName=rs6.getString("type_name");
                        }
                            }
                         if(currentlevelRatio < 0 ){
                            /////////////////////////get level_type for Supply////////////////
                             String query11="Select level_type_id,type_name from level_type where type_name='Supply'";
                           ResultSet rs6 = connection.prepareStatement(query11).executeQuery();
                            if (rs6.next()) {
                            levelType = rs6.getInt("level_type_id");
                            levelTypeName=rs6.getString("type_name");
                        }
                            }
                         if(currentlevelRatio >  db_level_ratio){
                            /////////////////////////get level_type for Distribution ////////////////
                             String query11="Select level_type_id,type_name from level_type where type_name='Distribution'";
                           ResultSet rs6 = connection.prepareStatement(query11).executeQuery();
                            if (rs6.next()) {
                            levelType = rs6.getInt("level_type_id");
                            levelTypeName=rs6.getString("type_name");
                        }
                            }

                            ////////for Leakage start/////////////////////////////////////////
                            if(levelType != previous_temp_dist_leveltype_id){
                        /////////code for update level_range level_time in temp_distribution table start///////

                                if(voltage1 > temp_level){
                                    double level_range=voltage1-temp_level;
                                    int time_range=0;
                                    String query15="SELECT TIMESTAMPDIFF(MINUTE,'" + created_date + "','" + current_dateTime + "' ) ";
                                try {
                                  ResultSet rs6 = connection.prepareStatement(query15).executeQuery();
                                  if (rs6.next()) {
                                   time_range = rs6.getInt(1);
                                    }
                                   } catch (Exception e) {
                                     System.out.println("Error in OHLevelEntryModel byte_data() time_range query "+e);
                                     }

                                    String updateQuery="update  temp_distribution set level_range=? , time_range=? "
                                                +" where temp_distribution_id=? ";
                             try{
                             PreparedStatement ps16 = (PreparedStatement) connection.prepareStatement(updateQuery);
                             ps16.setInt(1, (int)level_range);
                             ps16.setInt(2, time_range);
                             ps16.setInt(3, temp_distribution_id_last);
                             int updateStatus=ps16.executeUpdate();
                             }catch(Exception e){
                                 System.out.println("Error in OHLevelEntryModel byte_data() level_range,time_range update "+e);
                             }
                                }
                               if(voltage1 < temp_level){
                                    double level_range=temp_level-voltage1;

                                    int time_range=0;
                                    String query15="SELECT TIMESTAMPDIFF(MINUTE,'" + created_date + "','" + current_dateTime + "' ) ";
                                try {
                                  ResultSet rs6 = connection.prepareStatement(query15).executeQuery();
                                  if (rs6.next()) {
                                   time_range = rs6.getInt(1);
                                    }
                                   } catch (Exception e) {
                                     System.out.println("Error in OHLevelEntryModel byte_data() time_range query "+e);
                                     }

                                    String updateQuery="update  temp_distribution set level_range=? , time_range=? "
                                                +" where temp_distribution_id=? ";
                             try{
                             PreparedStatement ps16 = (PreparedStatement) connection.prepareStatement(updateQuery);
                             ps16.setInt(1, (int)level_range);
                             ps16.setInt(2, time_range);
                             ps16.setInt(3, temp_distribution_id_last);
                             int updateStatus=ps16.executeUpdate();
                             }catch(Exception e){
                                 System.out.println("Error in OHLevelEntryModel byte_data() level_range,time_range update "+e);
                             }

                                }
                                if(voltage1 == temp_level){
                                    double level_range=0;

                                    int time_range=0;
                                    String query15="SELECT TIMESTAMPDIFF(MINUTE,'" + created_date + "','" + current_dateTime + "' ) ";
                                try {
                                  ResultSet rs6 = connection.prepareStatement(query15).executeQuery();
                                  if (rs6.next()) {
                                   time_range = rs6.getInt(1);
                                    }
                                   } catch (Exception e) {
                                     System.out.println("Error in OHLevelEntryModel byte_data() time_range query "+e);
                                     }

                                    String updateQuery="update  temp_distribution set level_range=? , time_range=? "
                                                +" where temp_distribution_id=? ";
                             try{
                             PreparedStatement ps16 = (PreparedStatement) connection.prepareStatement(updateQuery);
                             ps16.setInt(1, (int)level_range);
                             ps16.setInt(2, time_range);
                             ps16.setInt(3, temp_distribution_id_last);
                             int updateStatus=ps16.executeUpdate();
                             }catch(Exception e){
                                 System.out.println("Error in OHLevelEntryModel byte_data() level_range,time_range update "+e);
                             }

                                }


                        /////////code for update level_range,level_time in temp_distribution table end///////
                         String query3 = "insert into temp_distribution (overheadtank_id,level,type,level_type_id) values(?,?,?,?)";
                        PreparedStatement ps3 = (PreparedStatement) connection.prepareStatement(query3);
                        ps3.setInt(1, junction_id);
                        ps3.setInt(2, level);
                        ps3.setString(3,levelTypeName);
                        ps3.setInt(4,levelType);
                        ps3.executeUpdate();
                        System.out.println("Record successfully inserted in temp_distribution and type="+levelTypeName);
                        ////////////////////////////insert into distribution ////////////////////
                        int last_dist_level_type_id=0;
                        String last_type="";
                        String last_date="";
                        int last_waterLevel=0;
                        int last_distribution_id=0;
                        String query12="select d.distribution_id,oht.overheadtank_id,d.type,d.created_date,d.level_type_id,ohl.remark as waterLevel "
                                       +" from overheadtank oht,ohlevel ohl,distribution d "
                                       +" where ohl.ohlevel_id=d.ohlevel_id "
                                       +" and ohl.overheadtank_id=oht.overheadtank_id "
                                       +" and oht.overheadtank_id="+junction_id
                                       +" order by d.distribution_id desc Limit 1 ";
                        PreparedStatement ps12 = (PreparedStatement) connection.prepareStatement(query12);
                        ResultSet rs12 = ps12.executeQuery();
                        while(rs12.next()){
                            last_distribution_id=rs12.getInt("distribution_id");
                            last_type=rs12.getString("type");
                            last_date=rs12.getString("created_date");
                            last_waterLevel=rs12.getInt("waterLevel");
                            last_dist_level_type_id=rs12.getInt("level_type_id");
                            System.out.println("Last distribution level_type_id= "+last_dist_level_type_id);
                        }

                         if (((voltage1 - temp_level) > on_off_value) && (time<time_diff) && (last_dist_level_type_id != levelType)) {

                             /////////////////////////////////calculate level_range and time_range start////////////
                             int level_difference=0;
                             int time_difference=0;
                             if(last_type.equals("Leakage") || last_type.equals("Supply") || last_type.equals("Distribution") || last_type.equals("Stable")){
                                 level_difference=(int)voltage1-last_waterLevel;

                                 String query15="SELECT TIMESTAMPDIFF(MINUTE,'" + last_date + "','" + current_dateTime + "' ) ";
                                try {
                                  ResultSet rs6 = connection.prepareStatement(query15).executeQuery();
                                  if (rs6.next()) {
                                   time_difference = rs6.getInt(1);
                                    }
                                   } catch (Exception e) {
                                     System.out.println("Error in OHLevelEntryModel byte_data() time_range query "+e);
                                     }
                                 }
                             ///////////////////////update distribution last row/////////////
                             String updateQuery="update  distribution set level_range=? , time_range=? "
                                                +" where distribution_id=? ";
                             try{
                             PreparedStatement ps16 = (PreparedStatement) connection.prepareStatement(updateQuery);
                             ps16.setInt(1, level_difference);
                             ps16.setInt(2, time_difference);
                             ps16.setInt(3, last_distribution_id);
                             int updateStatus=ps16.executeUpdate();
                             }catch(Exception e){
                                 System.out.println("Error in OHLevelEntryModel byte_data() level_range,time_range update "+e);
                             }
                             //////////////////////////////////////////////////////////////
                             /////////////////////////////////calculate level_range and time_range end////////////

                            String query5 = "insert into distribution (ohlevel_id,type,level_type_id) values(?,?,?)";
                            PreparedStatement ps5 = (PreparedStatement) connection.prepareStatement(query5);
                            ps5.setInt(1, ohlevel_id);
                            ps5.setString(2,levelTypeName);
                            ps5.setInt(3,levelType);
                            ps5.executeUpdate();
                        }

                        if (((temp_level - voltage1) > on_off_value) && (time<time_diff) && (last_dist_level_type_id != levelType)) {

                            /////////////////////////////////calculate level_range and time_range start////////////
                             int level_difference=0;
                             int time_difference=0;
                             if(last_type.equals("Leakage") || last_type.equals("Supply") || last_type.equals("Distribution") || last_type.equals("Stable")){
                                 level_difference=last_waterLevel-(int)voltage1;

                                 String query15="SELECT TIMESTAMPDIFF(MINUTE,'" + last_date + "','" + current_dateTime + "' ) ";
                                try {
                                  ResultSet rs6 = connection.prepareStatement(query15).executeQuery();
                                  if (rs6.next()) {
                                   time_difference = rs6.getInt(1);
                                    }
                                   } catch (Exception e) {
                                     System.out.println("Error in OHLevelEntryModel byte_data() time_range query "+e);
                                     }
                                 }
                             ///////////////////////update distribution last row/////////////
                             String updateQuery="update  distribution set level_range=? , time_range=? "
                                                +" where distribution_id=? ";
                             try{
                             PreparedStatement ps16 = (PreparedStatement) connection.prepareStatement(updateQuery);
                             ps16.setInt(1, level_difference);
                             ps16.setInt(2, time_difference);
                             ps16.setInt(3, last_distribution_id);
                             int updateStatus=ps16.executeUpdate();
                             }catch(Exception e){
                                 System.out.println("Error in OHLevelEntryModel byte_data() level_range,time_range update "+e);
                             }
                             //////////////////////////////////////////////////////////////
                             /////////////////////////////////calculate level_range and time_range end////////////



                            String query5 = "insert into distribution (ohlevel_id,type,level_type_id) values(?,?,?)";
                            PreparedStatement ps5 = (PreparedStatement) connection.prepareStatement(query5);
                            ps5.setInt(1, ohlevel_id);
                            ps5.setString(2,levelTypeName);
                            ps5.setInt(3,levelType);
                            ps5.executeUpdate();
                        }

                            }                            
                            
                   
                    }catch(Exception e){
                         System.out.println("Error in OHLevelEntryModel byte_data method..."+e);
                    }


                }
            } else {
                context.setAttribute("overflow_" + junction_id, "1");
                System.out.println("level value is greater than 1000");
            }

        } catch (Exception e) {
            System.out.println("insertRecord ERROR inside SmartMeterSurveyServiceModel - " + e);
        }
        return;
    }

    public byte[] getData(byte junction_id) {
        byte[] response = new byte[7];
        String query = "select level_a,level_b,step,level1,level2,level3,level4 from ohlevel where overheadtank_id='" + junction_id + "' order by ohlevel_id  desc";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();

            if (rset.next()) {
                byte a = rset.getByte("level_a");
                response[0] = a;
                byte b = rset.getByte("level_b");
                response[1] = b;
                byte c = rset.getByte("step");
                response[2] = c;
                byte d = rset.getByte("level1");
                response[3] = d;
                byte e = rset.getByte("level2");
                response[4] = e;
                byte f = rset.getByte("level3");
                response[5] = f;
                byte g = rset.getByte("level4");
                response[6] = g;

            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return response;
    }

    public byte[] junctionRefreshFunction1(byte[] dataToProcess, int firstStartDataPosition, boolean testRequestFromJunction) throws ParseException {
        byte junctionID = dataToProcess[firstStartDataPosition];
        int type_of_data = dataToProcess[firstStartDataPosition + 4];

        if (type_of_data == 1) {
            byte twoByteData[] = new byte[7];
            twoByteData[0] = dataToProcess[firstStartDataPosition + 5];
            twoByteData[1] = dataToProcess[firstStartDataPosition + 6];
            twoByteData[2] = dataToProcess[firstStartDataPosition + 7];
            twoByteData[3] = dataToProcess[firstStartDataPosition + 8];
            twoByteData[4] = dataToProcess[firstStartDataPosition + 9];
            twoByteData[5] = dataToProcess[firstStartDataPosition + 10];
            twoByteData[6] = dataToProcess[firstStartDataPosition + 11];
            if (twoByteData[3] != 1) {
                byte_data(twoByteData, junctionID);



            }
            String command_value = (String) context.getAttribute("" + junctionID);
            if (command_value == null) {
                command_value = "" + 0;
            }
            Byte command = (byte) Integer.parseInt(command_value);
            dataToProcess[13] = command;
            System.out.println("type_of_data is 1");
            return dataToProcess;
        } else if (type_of_data == 9) {
            byte twoByteDisplayData[] = new byte[7];
            twoByteDisplayData = getData(junctionID);
            byte data1 = twoByteDisplayData[0];
            byte data2 = twoByteDisplayData[1];
            byte data3 = twoByteDisplayData[2];
            byte data4 = twoByteDisplayData[3];
            byte data5 = twoByteDisplayData[4];
            byte data6 = twoByteDisplayData[5];
            byte data7 = twoByteDisplayData[6];
            byte feedback = dataToProcess[firstStartDataPosition + 7];
            if (feedback == 123) {
                context.setAttribute("feedback_"+junctionID,0);
            } else if (feedback == 122) {
                context.setAttribute("feedback_"+junctionID,1);
            }

            String overflow = (String) context.getAttribute("overflow_" + junctionID);
            String user_value = (String) context.getAttribute(("" + junctionID).trim());
            // for overflow 0 is within range and user_value 0 is off.
            if (user_value == null) {
                user_value = ("" + 0).trim();
            }
                if (overflow == null) {
                overflow = "" + 0;
            }
            System.out.println("-----------------" + user_value+"---"+overflow);
            System.out.println(data1);
            System.out.println(data2);
            System.out.println(data3);
            System.out.println(data4);
            System.out.println(data5);
            System.out.println(data6);
            System.out.println(data7);
            if ((overflow.equals("1")) && user_value.equals("0")) {
                dataToProcess[firstStartDataPosition + 7] = 123;
            } else if ((overflow.equals("1")) && user_value.equals("1")) {
                dataToProcess[firstStartDataPosition + 7] = 122;
            } else if ((overflow.equals("0")) && user_value.equals("0")) {
                dataToProcess[firstStartDataPosition + 7] = 121;
            } else if ((overflow.equals("0")) && user_value.equals("1")) {
                dataToProcess[firstStartDataPosition + 7] = 120;
            } else {
                dataToProcess[firstStartDataPosition + 7] = data3;
            }
            dataToProcess[firstStartDataPosition + 5] = data1;
            dataToProcess[firstStartDataPosition + 6] = data2;
            //   dataToProcess[firstStartDataPosition + 7] = data3;
            System.out.println(data3+"=="+data4);
            dataToProcess[firstStartDataPosition + 8] = data4;  //level2
            dataToProcess[firstStartDataPosition + 9] = data3;
            dataToProcess[firstStartDataPosition + 10] = data6;
            dataToProcess[firstStartDataPosition + 11] = data7;
            System.out.println("type_of_data is 9.");
            return dataToProcess;
        } else {
            System.out.println("type_of_data not match. it will be 1 or 9 otherwise same data will return" + dataToProcess);
            return dataToProcess;
        }
    }

//         public static void setConnection1() {
//        try {
//            System.out.println("hii");
//            Class.forName("com.mysql.jdbc.Driver");
//            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/smart_meter_survey?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", "jpss_2", "jpss_1277");
//        } catch (Exception e) {
//            System.out.println("ReadMailModel setConnection() Error: " + e);
//        }
//    }
    
    
    public JSONArray getAllOverHeadTankData() {
               
        //JSONObject obj = new JSONObject();
        JSONArray arrayObj = new JSONArray();
        //Map map = new HashMap();
        String query = "select overheadtank_id,name,latitude,longitude\n" +
                       "from overheadtank oht";
        try {
            
            PreparedStatement pst = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rset = pst.executeQuery();

            if (rset.next()) {
                JSONObject jsonObj = new JSONObject();
                String tank_id=rset.getString("overheadtank_id");
                int tank_id1=Integer.parseInt(tank_id);
                jsonObj.put("common_id",tank_id1);
                jsonObj.put("common_name",rset.getString("name"));
                jsonObj.put("latitude",rset.getString("latitude"));
                jsonObj.put("longitude",rset.getString("longitude"));
                
                String status=checkLiveStatus(tank_id);
                jsonObj.put("active",status);                
                arrayObj.add(jsonObj);               
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return arrayObj;
    }
    
    public JSONArray getOverHeadTankDataInfo(String lati, String longitude) {
               
        //JSONObject obj = new JSONObject();
        JSONArray arrayObj = new JSONArray();
        //Map map = new HashMap();
        String query = "select overheadtank_id,name,latitude,longitude\n" +
                       "from overheadtank oht where latitude="+lati+" and longitude="+longitude+"";
        try {
            
            PreparedStatement pst = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rset = pst.executeQuery();

            if (rset.next()) {
                JSONObject jsonObj = new JSONObject();                
                jsonObj.put("first",rset.getString("name"));
                jsonObj.put("second",rset.getString("latitude"));
                jsonObj.put("third",rset.getString("longitude"));                                             
                arrayObj.add(jsonObj);               
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return arrayObj;
    }
    
    public String checkLiveStatus(String tank_id) {
        Date dt = new Date();
        //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String cut_dt = df1.format(dt);
        String status="";
        String date_time = "";
        int time_difference=0;
        
        String query1="select date_time\n" +
                      "from ohlevel ohl\n" +
                      "where ohl.overheadtank_id=?\n" +
                      "order by date_time desc\n" +
                      "Limit 1";
//        String query2 = "SELECT TIMESTAMPDIFF(MINUTE,'2019-03-03 15:15:14','2019-03-03 15:15:14' )";
          String query2 = "SELECT TIMESTAMPDIFF(MINUTE,'"+date_time+"','"+cut_dt+"' )";
        try {
            PreparedStatement pst1 = (PreparedStatement) connection.prepareStatement(query1);
            pst1.setInt(1, Integer.parseInt(tank_id));
            ResultSet rset1 = pst1.executeQuery();
            if (rset1.next()) {
                
                date_time = rset1.getString("date_time");
            }
            
            PreparedStatement pst2 = (PreparedStatement) connection.prepareStatement(query2);
            ResultSet rset2 = pst2.executeQuery();
            if (rset2.next()) {
                
                time_difference = rset2.getInt(1);
            }
            if(time_difference < 10 & !date_time.equals("")){
                status = "YES";
            }
            if(time_difference > 10 | date_time.equals(""))
            {
                status="NO";
            }
          
        } catch (Exception e) {
            System.out.println(e);
        }
        return status;
    }
    
    
    
    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println(" closeConnection() Error: " + e);
        }
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

    public void setContext(ServletContext ctx) {
        this.context = ctx;
    }
}
