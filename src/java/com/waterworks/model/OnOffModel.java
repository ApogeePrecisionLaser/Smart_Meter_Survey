/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.waterworks.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import com.waterworks.tableClasses.OnOff;
import java.math.BigInteger;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import jxl.write.DateTime;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Administrator
 */
public class OnOffModel {

    private static Connection connection;
    private String driver, url, user, password;
    private String message, messageBGColor;
    private String driverClass;
    private String connectionString;
    private String db_username;
    private String db_password;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";
    DecimalFormat df = new DecimalFormat("0.00");
      ArrayList<OnOff> pdflist = new ArrayList<OnOff>();
    String diffdatetime="";
    int levelvalue=0;
    long totallevel=0;
    String finaldatetime="";
    long leveldifference=0;
    long diff=0;
    String totalTime="";
    long totalday=0;
    long totalhour=0;
    long totalmin=0;
    long totalsec=0;
    String totalleakage="";
     String totalstable="";
 String totalsupply="";
  String totaldistribution="";
  long totalleakagevalue=0;
     long totalstablevalue=0;
 long totalsupplyvalue=0;
  long totaldistributionvalue=0;
  
  
  
  
    public void setConnection() {
        try {
            Class.forName(driver);
            // connection = DriverManager.getConnection(connectionString+"?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", db_username, db_password);
            connection = (Connection) DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println("WattageTypeModel setConnection() Error: " + e);
        }
    }

    public int getTotalRowsInTable(String searchDateFrom, String searchDateTo,String searchOverheadtankName,String type) {
        if (searchDateFrom != null && !searchDateFrom.isEmpty()) {
            String[] sdate_array = searchDateFrom.split("-");
            searchDateFrom = sdate_array[2] + "-" + sdate_array[1] + "-" + sdate_array[0];
        }
        if (searchDateTo != null && !searchDateTo.isEmpty()) {
            String[] sdate_array = searchDateTo.split("-");
            searchDateTo = sdate_array[2] + "-" + sdate_array[1] + "-" + sdate_array[0];
        }
        String query = " select count(distribution_id) from watertreatmentplant AS wtp,overheadtank AS oht,"
                + " ohlevel as ol,distribution as d"
                + " where oht.watertreatmentplant_id = wtp.watertreatmentplant_id"
                + " and ol.overheadtank_id = oht.overheadtank_id and ol.ohlevel_id=d.ohlevel_id "
            //    + " And IF('" + searchDateFrom + "'='', ol.date_time LIKE '%%',ol.date_time >='" + searchDateFrom + "') "
             //   + " And IF('" + searchDateTo + "'='', ol.date_time LIKE '%%',date_format(ol.date_time, '%Y-%m-%d') <='" + searchDateTo + "') "
                + " And IF('" + searchOverheadtankName + "' = '',oht.name LIKE '%%', oht.name='" + searchOverheadtankName + "')"
                + " And IF('" + type + "' = '',d.type LIKE '%%', d.type='" + type + "')";
         if(searchDateFrom!=""){
        query=query+"and ol.date_time='"+searchDateFrom+"'";
        }
         if(searchDateFrom!=""){
        query+=" and ol.date_time='"+searchDateFrom+"'";
        }
        int noOfRows = 0;
        try {
            PreparedStatement presta = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rs = presta.executeQuery();
            rs.next();
            noOfRows = rs.getInt(1);
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows - onoffmodel : " + e);

        }
        System.out.println("No of Rows in Table for search is****....." + noOfRows);
        return noOfRows;
    }

    public ArrayList<OnOff> getAllRecords(int lowerLimit, int noOfRowsToDisplay, String searchDateFrom, String searchDateTo,String searchOverheadtankName,String type) {
        ArrayList<OnOff> list = new ArrayList<OnOff>();
        levelvalue=0;
        totallevel=0;
        diff=0;
        Date d1 = null;
		Date d2 = null;
        if (searchDateFrom != null && !searchDateFrom.isEmpty()) {
            String[] sdate_array = searchDateFrom.split("-");
            searchDateFrom = sdate_array[2] + "-" + sdate_array[1] + "-" + sdate_array[0];
        }
        if (searchDateTo != null && !searchDateTo.isEmpty()) {
            String[] sdate_array = searchDateTo.split("-");
            searchDateTo = sdate_array[2] + "-" + sdate_array[1] + "-" + sdate_array[0];
        }
        byte[] twoByteData = new byte[2];
        String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        if (lowerLimit == -1) {     
            addQuery = "";
        }
        //  int overheadtank_id = getOverHeadTankid(Integer.parseInt(oh_level));
        String query = " select wtp.name as name1,oht.name,ol.remark,ol.ohlevel_id,d.type,ol.date_time,level3,level4,capacity_height from watertreatmentplant AS wtp,overheadtank AS oht,"
                + " ohlevel as ol,distribution as d"
                + " where oht.watertreatmentplant_id = wtp.watertreatmentplant_id"
                + " and ol.overheadtank_id = oht.overheadtank_id and ol.ohlevel_id=d.ohlevel_id "
             //   + " And IF('" + searchDateFrom + "'='', ol.date_time LIKE '%%',ol.date_time >='" + searchDateFrom + "') "
            //    + " And IF('" + searchDateTo + "'='', ol.date_time LIKE '%%',date_format(ol.date_time, '%Y-%m-%d') <='" + searchDateTo + "') "
                + " And IF('" + searchOverheadtankName + "' = '',oht.name LIKE '%%', oht.name='" + searchOverheadtankName + "') "
                   + " And IF('" + type + "' = '',d.type LIKE '%%', d.type='" + type + "') ";
        if(!"".equals(searchDateFrom)){
        query=query+"and ol.date_time='"+searchDateFrom+"'";
        }
         if(!"".equals(searchDateFrom)){
        query+=" and ol.date_time='"+searchDateFrom+"'";
        }
              query+= "order by ol.date_time desc ";           
                  query+=" LIMIT "+lowerLimit+", "+noOfRowsToDisplay;
               
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                OnOff ohLevelBean = new OnOff();
                ohLevelBean.setWaterTreatmentPlantName(rset.getString(1));
                ohLevelBean.setOverHeadTankName(rset.getString(2));
                ohLevelBean.setOhLevelId(rset.getInt("ohlevel_id"));
                ohLevelBean.setType(rset.getString("type"));
                String datetime1 = rset.getString("date_time");
                
                 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
 
       
 
      //  System.out.println("Before subtraction of minutes from date: "+currentTime);
 
        LocalDateTime datetime = LocalDateTime.parse(datetime1,formatter);
 
        datetime=datetime.minusMinutes(6);
 
        String aftersubtraction=datetime.format(formatter);
        
        String values[] = aftersubtraction.split(" ");
        
        String value1 = values[0];
        String values2 = values[1];
        String values3[] = values2.split(":");
        String values4 = values3[0];
        String values5 = values3[1];
        
        String finaltime = value1+" "+values4+":"+values5;
                
                String remark = getRemark(finaltime);
                ohLevelBean.setDateTime(aftersubtraction);
                ohLevelBean.setLevel(remark);
                
                finaldatetime = getFinaldatetime(aftersubtraction,type);
                ohLevelBean.setFinaldatetime(finaldatetime);
                
              String finallevel = getRemark(finaltime);
                
             
                 leveldifference = Integer.parseInt(remark) - (levelvalue);
               ohLevelBean.setLeveldifference(Math.abs(leveldifference));
                
          if(!diffdatetime.equals("")) 
          {
              	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
              
        d1 =  format.parse(diffdatetime);
			d2 =  format.parse(aftersubtraction);

			//in milliseconds
			 diff = d1.getTime() - d2.getTime();
 
//			long diffSeconds = diff / 1000 % 60;
//			long diffMinutes = diff / (60 * 1000) % 60;
//			long diffHours = diff / (60 * 60 * 1000) % 24;
//			long diffDays = diff / (24 * 60 * 60 * 1000);
//
//			System.out.print(diffDays + " days, ");
//			System.out.print(diffHours + " hours, ");
//			System.out.print(diffMinutes + " minutes, ");
//			System.out.print(diffSeconds + " seconds.");
//                        
//                        if(diffSeconds>0 && diffHours==0 || diffMinutes>0 )
//                        {
//                            String colname="Difference in Seconds";
//                        long dtdifference = diffSeconds;
//                        
//                        ohLevelBean.setColname(colname);
//                        ohLevelBean.setDtdifference(dtdifference);
//                        }
//                         if (diffSeconds>0 && diffMinutes>0 && diffHours==0)
//                        {
//                              String colname = " Difference in minutes";
//                              long dtdifference = diffMinutes;
//                              
//                               ohLevelBean.setColname(colname);
//                        ohLevelBean.setDtdifference(dtdifference);
//                        }
//                        else
//                        {
//                          String colname = " Difference in hours";
//                          long dtdifference= diffHours;
//                          
//                           ohLevelBean.setColname(colname);
//                        ohLevelBean.setDtdifference(dtdifference);
//                        }
          }
                
                totallevel=totallevel+leveldifference;
                ohLevelBean.setTotallevel(Math.abs(totallevel));
                twoByteData[0] = rset.getByte("level3");
                twoByteData[1] = rset.getByte("level4");
                long voltage1 = (new BigInteger(twoByteData).longValue());
                ohLevelBean.setValue_of_34(("" + voltage1).trim());
                ohLevelBean.setLevel_of_34("" + df.format((voltage1 / (rset.getDouble("capacity_height") * 1000)) * 100));
                list.add(ohLevelBean);
                
                   
                levelvalue = Integer.parseInt(remark);
                
                diffdatetime = aftersubtraction;
                
            }
        } catch (Exception e) {
            System.out.println("Error in getAllRecrod -- OnOFF Model : " + e);

        }
        return list;
    }

    
    
    
     public ArrayList<OnOff> ShowPDF(int lowerLimit, int noOfRowsToDisplay, String searchDateFrom, String searchDateTo,String searchOverheadtankName,String type) {
        ArrayList<OnOff> list = new ArrayList<OnOff>();
       
         String finallevel = "";
        levelvalue=0;
        totallevel=0;
        diff=0;
         leveldifference=0;
         totalTime="";
           totalday=0;
     totalhour=0;
     totalmin=0;
       totalsec=0;

        Date d1 = null;
		Date d2 = null;
        if (searchDateFrom != null && !searchDateFrom.isEmpty()) {
            String[] sdate_array = searchDateFrom.split("-");
            searchDateFrom = sdate_array[2] + "-" + sdate_array[1] + "-" + sdate_array[0];
        }
        if (searchDateTo != null && !searchDateTo.isEmpty()) {
            String[] sdate_array = searchDateTo.split("-");
            searchDateTo = sdate_array[2] + "-" + sdate_array[1] + "-" + sdate_array[0];
        }
        byte[] twoByteData = new byte[2];
        String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        if (lowerLimit == -1) {
            addQuery = "";
        }
        //  int overheadtank_id = getOverHeadTankid(Integer.parseInt(oh_level));
        String query = " select wtp.name as name1,oht.name,ol.remark,ol.ohlevel_id,d.type,ol.date_time,level3,level4,capacity_height from watertreatmentplant AS wtp,overheadtank AS oht,"
                + " ohlevel as ol,distribution as d"
                + " where oht.watertreatmentplant_id = wtp.watertreatmentplant_id"
                + " and ol.overheadtank_id = oht.overheadtank_id and ol.ohlevel_id=d.ohlevel_id "
              //  + " And IF('" + searchDateFrom + "'='', ol.date_time LIKE '%%',ol.date_time >='" + searchDateFrom + "') "
              //  + " And IF('" + searchDateTo + "'='', ol.date_time LIKE '%%',date_format(ol.date_time, '%Y-%m-%d') <='" + searchDateTo + "') "
                + " And IF('" + searchOverheadtankName + "' = '',oht.name LIKE '%%', oht.name='" + searchOverheadtankName + "') "
                   + " And IF('" + type + "' = '',d.type LIKE '%%', d.type='" + type + "') ";
         if(!"".equals(searchDateFrom)){
        query=query+"and ol.date_time>='"+searchDateFrom+"'";
        }
         if(!"".equals(searchDateFrom)){
        query+=" and ol.date_time<='"+searchDateFrom+"'";
        }
              query+= "order by ol.date_time desc " + addQuery;  
           //     + " order by ol.date_time asc "
              //  + addQuery;
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                OnOff ohLevelBean = new OnOff();
                ohLevelBean.setWaterTreatmentPlantName(rset.getString(1));
                ohLevelBean.setOverHeadTankName(rset.getString(2));
                ohLevelBean.setOhLevelId(rset.getInt("ohlevel_id"));
                ohLevelBean.setType(rset.getString("type"));
                String datetime1 = rset.getString("date_time");
                
                 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
 
       
 
      //  System.out.println("Before subtraction of minutes from date: "+currentTime);
 
        LocalDateTime datetime = LocalDateTime.parse(datetime1,formatter);
 
        datetime=datetime.minusMinutes(6);
 
        String aftersubtraction=datetime.format(formatter);
        
        String values[] = aftersubtraction.split(" ");
        
        String value1 = values[0];
        String values2 = values[1];
        String values3[] = values2.split(":");
        String values4 = values3[0];
        String values5 = values3[1];
        
        String finaltime = value1+" "+values4+":"+values5;
                
                String remark = getRemark(finaltime);
                ohLevelBean.setDateTime(aftersubtraction);
                ohLevelBean.setLevel(remark);
                
                finaldatetime = getFinaldatetime(datetime1,type);
                ohLevelBean.setFinaldatetime(finaldatetime);
                
                if (!finaldatetime.equals(""))
                {
                  String finalvalues[] = finaldatetime.split(" ");
        
        String finalvalue1 = finalvalues[0];
        String finalvalues2 = finalvalues[1];
        String finalvalues3[] = finalvalues2.split(":");
        String finalvalues4 = finalvalues3[0];
        String finalvalues5 = finalvalues3[1];
        
        String finaltime1 = finalvalue1+" "+finalvalues4+":"+finalvalues5;
                
                
                
               finallevel = getFinalLevel(finaltime1,type);
                ohLevelBean.setColname(finallevel);
                }
                
                 leveldifference = Integer.parseInt(remark) - Integer.parseInt(finallevel);
               ohLevelBean.setLeveldifference(Math.abs(leveldifference));
                
               
          
              	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
              
        d1 =  format.parse(aftersubtraction);
			d2 =  format.parse(finaldatetime);

			//in milliseconds
			 diff = d2.getTime() - d1.getTime();

                         
//                         Date result = new Date(diff); 
//                         
//                         String diffdt = format.format(result);
//                         ohLevelBean.setDtdifference(diffdt);
//                         
//                         
                         Calendar cal = Calendar.getInstance();
       cal.setTimeInMillis(diff);
//       System.out.println("Milliseconds to Date using Calendar:"
//               + df.format(cal.getTime()));
//     



                         
			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);

			System.out.print(diffDays + " days, ");
			System.out.print(diffHours + " hours, ");
			System.out.print(diffMinutes + " minutes, ");
			System.out.print(diffSeconds + " seconds.");
                        
      
                        
                       
                        String findatetimev = diffDays+":"+diffHours+":"+diffMinutes+":"+diffSeconds;
                        ohLevelBean.setDtdifference(findatetimev);
                        
                          totalday=totalday+diffDays;
                         totalhour=totalhour+diffHours;
                          totalmin=totalmin+diffMinutes;
                          totalsec=totalsec+diffSeconds;

                        
                        totalTime = totalday+":"+totalhour+":"+totalmin+":"+totalsec;
                        ohLevelBean.setTotalTime(totalTime);
//                        
//                        if(diffSeconds>0 && diffHours==0 || diffMinutes>0 )
//                        {
//                            String colname="Difference in Seconds";
//                        long dtdifference = diffSeconds;
//                        
//                        ohLevelBean.setColname(colname);
//                        ohLevelBean.setDtdifference(dtdifference);
//                        }
//                         if (diffSeconds>0 && diffMinutes>0 && diffHours==0)
//                        {
//                              String colname = " Difference in minutes";
//                              long dtdifference = diffMinutes;
//                              
//                               ohLevelBean.setColname(colname);
//                        ohLevelBean.setDtdifference(dtdifference);
//                        }
//                        else
//                        {
//                          String colname = " Difference in hours";
//                          long dtdifference= diffHours;
//                          
//                           ohLevelBean.setColname(colname);
//                        ohLevelBean.setDtdifference(dtdifference);
//                        }
          
                
                totallevel=totallevel+leveldifference;
                ohLevelBean.setTotallevel(Math.abs(totallevel));
                twoByteData[0] = rset.getByte("level3");
                twoByteData[1] = rset.getByte("level4");
                long voltage1 = (new BigInteger(twoByteData).longValue());
                ohLevelBean.setValue_of_34(("" + voltage1).trim());
                ohLevelBean.setLevel_of_34("" + df.format((voltage1 / (rset.getDouble("capacity_height") * 1000)) * 100));
                pdflist.add(ohLevelBean);
                
                   
                levelvalue = Integer.parseInt(remark);
                
                diffdatetime = aftersubtraction;
                
            }
        } catch (Exception e) {
            System.out.println("Error in getAllRecrod -- OnOFF Model : " + e);

        }
        return pdflist;
    }

    
      public ArrayList<OnOff> ShowAllPDF(int lowerLimit, int noOfRowsToDisplay, String searchDateFrom, String searchDateTo,String searchOverheadtankName,String type) {
        ArrayList<OnOff> list = new ArrayList<OnOff>();
       
         String finallevel = "";
        levelvalue=0;
        totallevel=0;
        diff=0;
         leveldifference=0;
         totalTime="";
           totalday=0;
     totalhour=0;
     totalmin=0;
       totalsec=0;

        Date d1 = null;
		Date d2 = null;
        if (searchDateFrom != null && !searchDateFrom.isEmpty()) {
            String[] sdate_array = searchDateFrom.split("-");
            searchDateFrom = sdate_array[2] + "-" + sdate_array[1] + "-" + sdate_array[0];
        }
        if (searchDateTo != null && !searchDateTo.isEmpty()) {
            String[] sdate_array = searchDateTo.split("-");
            searchDateTo = sdate_array[2] + "-" + sdate_array[1] + "-" + sdate_array[0];
        }
        byte[] twoByteData = new byte[2];
        String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        if (lowerLimit == -1) {
            addQuery = "";
        }
        //  int overheadtank_id = getOverHeadTankid(Integer.parseInt(oh_level));
        String query = " select wtp.name as name1,oht.name,ol.remark,ol.ohlevel_id,d.type,ol.date_time,level3,level4,capacity_height from watertreatmentplant AS wtp,overheadtank AS oht,"
                + " ohlevel as ol,distribution as d"
                + " where oht.watertreatmentplant_id = wtp.watertreatmentplant_id"
                + " and ol.overheadtank_id = oht.overheadtank_id and ol.ohlevel_id=d.ohlevel_id "
               // + " And IF('" + searchDateFrom + "'='', ol.date_time LIKE '%%',ol.date_time >='" + searchDateFrom + "') "
              //  + " And IF('" + searchDateTo + "'='', ol.date_time LIKE '%%',date_format(ol.date_time, '%Y-%m-%d') <='" + searchDateTo + "') "
                + " And IF('" + searchOverheadtankName + "' = '',oht.name LIKE '%%', oht.name='" + searchOverheadtankName + "') "
                   + " And IF('" + type + "' = '',d.type LIKE '%%', d.type='" + type + "') ";
         if(!"".equals(searchDateFrom)){
        query=query+"and ol.date_time>='"+searchDateFrom+"'";
        }
         if(!"".equals(searchDateFrom)){
        query+=" and ol.date_time<='"+searchDateFrom+"'";
        }
              query+= "order by ol.date_time desc " + addQuery;  
              //  + " order by ol.date_time asc "
               
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                OnOff ohLevelBean = new OnOff();
                ohLevelBean.setWaterTreatmentPlantName(rset.getString(1));
                ohLevelBean.setOverHeadTankName(rset.getString(2));
                ohLevelBean.setOhLevelId(rset.getInt("ohlevel_id"));
                ohLevelBean.setType(rset.getString("type"));
                String datetime1 = rset.getString("date_time");
                
                 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
 
       
 
      //  System.out.println("Before subtraction of minutes from date: "+currentTime);
 
        LocalDateTime datetime = LocalDateTime.parse(datetime1,formatter);
 
        datetime=datetime.minusMinutes(6);
 
        String aftersubtraction=datetime.format(formatter);
        
        String values[] = aftersubtraction.split(" ");
        
        String value1 = values[0];
        String values2 = values[1];
        String values3[] = values2.split(":");
        String values4 = values3[0];
        String values5 = values3[1];
        
        String finaltime = value1+" "+values4+":"+values5;
                
                String remark = getRemark(finaltime);
                ohLevelBean.setDateTime(aftersubtraction);
                ohLevelBean.setLevel(remark);
                
                finaldatetime = getFinaldatetime(datetime1,type);
                ohLevelBean.setFinaldatetime(finaldatetime);
                
                if (!finaldatetime.equals(""))
                {
                  String finalvalues[] = finaldatetime.split(" ");
        
        String finalvalue1 = finalvalues[0];
        String finalvalues2 = finalvalues[1];
        String finalvalues3[] = finalvalues2.split(":");
        String finalvalues4 = finalvalues3[0];
        String finalvalues5 = finalvalues3[1];
        
        String finaltime1 = finalvalue1+" "+finalvalues4+":"+finalvalues5;
                
                
                
               finallevel = getFinalLevel(finaltime1,type);
                ohLevelBean.setColname(finallevel);
                }
                
                 leveldifference = Integer.parseInt(remark) - Integer.parseInt(finallevel);
               ohLevelBean.setLeveldifference(Math.abs(leveldifference));
                
               
          
              	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
              
        d1 =  format.parse(aftersubtraction);
			d2 =  format.parse(finaldatetime);

			//in milliseconds
			 diff = d2.getTime() - d1.getTime();

                         
//                         Date result = new Date(diff); 
//                         
//                         String diffdt = format.format(result);
//                         ohLevelBean.setDtdifference(diffdt);
//                         
//                         
                         Calendar cal = Calendar.getInstance();
       cal.setTimeInMillis(diff);
//       System.out.println("Milliseconds to Date using Calendar:"
//               + df.format(cal.getTime()));
//     



                         
			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);

			System.out.print(diffDays + " days, ");
			System.out.print(diffHours + " hours, ");
			System.out.print(diffMinutes + " minutes, ");
			System.out.print(diffSeconds + " seconds.");
                        
      
                        
                       
                        String findatetimev = diffDays+":"+diffHours+":"+diffMinutes+":"+diffSeconds;
                        ohLevelBean.setDtdifference(findatetimev);
                        
                          totalday=totalday+diffDays;
                         totalhour=totalhour+diffHours;
                          totalmin=totalmin+diffMinutes;
                          totalsec=totalsec+diffSeconds;

                        
                        totalTime = totalday+":"+totalhour+":"+totalmin+":"+totalsec;
                        ohLevelBean.setTotalTime(totalTime);

                        
                        
                        
                
                totallevel=totallevel+leveldifference;
                ohLevelBean.setTotallevel(Math.abs(totallevel));
                twoByteData[0] = rset.getByte("level3");
                twoByteData[1] = rset.getByte("level4");
                long voltage1 = (new BigInteger(twoByteData).longValue());
                ohLevelBean.setValue_of_34(("" + voltage1).trim());
                ohLevelBean.setLevel_of_34("" + df.format((voltage1 / (rset.getDouble("capacity_height") * 1000)) * 100));
                pdflist.add(ohLevelBean);
                
                   
                levelvalue = Integer.parseInt(remark);
                
                diffdatetime = aftersubtraction;
                
                
                if(type.equals("Leakage"))
                        {
                         totalleakage = totalday+":"+totalhour+":"+totalmin+":"+totalsec;
                        ohLevelBean.setTotalleakagetime(totalleakage);
                       totalleakagevalue=totallevel;
                        ohLevelBean.setTotalleakagevalue(totalleakagevalue);
                        }
                  if(type.equals("Stable"))
                        {
                         totalstable = totalday+":"+totalhour+":"+totalmin+":"+totalsec;
                        ohLevelBean.setTotalstabletime(totalstable);
                        totalstablevalue = totallevel;
                       ohLevelBean.setTotalstablevalue(totalstablevalue);
                        
                        }
                 if(type.equals("Supply"))
                        {
                         totalsupply = totalday+":"+totalhour+":"+totalmin+":"+totalsec;
                        ohLevelBean.setTotalsupplytime(totalsupply);
                        totalsupplyvalue = totallevel;
                        ohLevelBean.setTotalsupplyvalue(totalsupplyvalue);
                       
                        }
                  if(type.equals("Distribution"))
                        {
                         totaldistribution = totalday+":"+totalhour+":"+totalmin+":"+totalsec;
                        ohLevelBean.setTotaldistributiontime(totaldistribution);
                        totaldistributionvalue =totallevel; 
                       ohLevelBean.setTotaldistributionvalue(Math.abs(totaldistributionvalue));
                       
                       ohLevelBean.setTotalleakagetime(totalleakage);
                        ohLevelBean.setTotalleakagevalue(Math.abs(totalleakagevalue));
                        
                          ohLevelBean.setTotalstabletime(totalstable);
                           ohLevelBean.setTotalstablevalue(Math.abs(totalstablevalue));
                           
                           ohLevelBean.setTotalsupplytime(totalsupply);
                             ohLevelBean.setTotalsupplyvalue(Math.abs(totalsupplyvalue));
                        }
                
            }
        } catch (Exception e) {
            System.out.println("Error in getAllRecrod -- OnOFF Model : " + e);

        }
        return pdflist;
    }

    
      
     public String getRemark(String q){

        String name="";
        String query =" select remark from ohlevel where date_time>'" + q +"' limit 1";
        try {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            int count = 0;
            
            while (rs.next()) {    // move cursor from BOR to valid record.
                 name = rs.getString("remark");
                 count++;
            }
            if (count == 0) {
               System.out.println("No value");
            }
        } catch (Exception e) {
            System.out.println("getP_name ERROR inside PersonModel" + e);
        }
        return name;

    }
     
      public String getFinaldatetime(String q,String type){

        String name="";
 String query = " select wtp.name as name1,oht.name,ol.remark,ol.ohlevel_id,d.type,ol.date_time,level3,level4,capacity_height from watertreatmentplant AS wtp,overheadtank AS oht,"
                + " ohlevel as ol,distribution as d"
                + " where oht.watertreatmentplant_id = wtp.watertreatmentplant_id"
                + " and ol.overheadtank_id = oht.overheadtank_id and ol.ohlevel_id=d.ohlevel_id ";
               // + " And IF('" + q + "'='', ol.date_time LIKE '%%',ol.date_time >'" + q + "') ";
               if(!q.equals("")){
                   query+=" and ol.date_time>'"+q+"'";
               }
//                   + " And IF('" + type + "' = '',d.type LIKE '%%', d.type='" + type + "') "
                query+= " order by ol.date_time asc limit 1";              
        try {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            int count = 0;
            
            while (rs.next()) {    // move cursor from BOR to valid record.
                 String type_name = rs.getString("type");
                 if(!type_name.equals(type))
                         {
                 name = rs.getString("date_time");
                         }
                   DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                   name = rs.getString("date_time");
                 
                    LocalDateTime datetime = LocalDateTime.parse(name,formatter);
 
        datetime=datetime.minusMinutes(6);
 
        String aftersubtraction=datetime.format(formatter);
        
        name = aftersubtraction;
                 count++;
            }
            if (count == 0) {
               System.out.println("No value");
            }
        } catch (Exception e) {
            System.out.println("getP_name ERROR inside PersonModel" + e);
        }
        return name;

    }
      
        public String getFinalLevel(String q,String type){

//             DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
//                 
//                 
//                    LocalDateTime datetime = LocalDateTime.parse(q,formatter);
// 
//        datetime=datetime.plusMinutes(6);
// 
//        String aftersubtraction=datetime.format(formatter);
//        
//        q = aftersubtraction;
//            
        String name="";
 String query = "select remark from ohlevel where date_time>'" + q +"' limit 1 ";
        try {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            int count = 0;
            
            while (rs.next()) {    // move cursor from BOR to valid record.
                 name = rs.getString("remark");
                 
                 count++;
            }
            if (count == 0) {
               System.out.println("No value");
            }
        } catch (Exception e) {
            System.out.println("getP_name ERROR inside PersonModel" + e);
        }
        return name;

    }
      

      public List<String> getTypeName(String q){

        List<String> li = new ArrayList<String>();
        String query = " select d.type_name "
                 +" from level_type as d ";
                
        try {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            int count = 0;
            q = q.trim();
            while (rs.next()) {    // move cursor from BOR to valid record.
                String name = rs.getString("type_name");
                if (name.toUpperCase().startsWith(q.toUpperCase())) {
                    li.add(name);
                 
                    count++;
                }
            }
            li.add("All");
            if (count == 0) {
                li.add("No such Status exists");
            }
        } catch (Exception e) {
            System.out.println("getP_name ERROR inside PersonModel" + e);
        }
        return li;

    }

      
      
      
         public static String getTypeNameId(int id){
     String name="";
        int count = 0;
        String query = " select d.type_name "
                 +" from level_type as d where level_type_id="+id;
                
        try {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
           
            
            while (rs.next()) {    // move cursor from BOR to valid record.
                 name = rs.getString("type_name");
              
                 
                    count++;
                }
            
           
            if (count == 0) {
               System.out.println("No such Status exists");
            }
        } catch (Exception e) {
            System.out.println("getP_name ERROR inside PersonModel" + e);
        }
        return name;

    }

      
    
    
    
    
     public List<String> getOverheadTankName(String q){

        List<String> li = new ArrayList<String>();
        String query = " select oht.name "
                 +" from watertreatmentplant AS wtp,overheadtank AS oht,ohlevel as ol,distribution as d "
                 +" where oht.watertreatmentplant_id = wtp.watertreatmentplant_id "
                 +" and ol.overheadtank_id = oht.overheadtank_id and ol.ohlevel_id=d.ohlevel_id "
                 +" group by oht.name ";
        try {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            int count = 0;
            q = q.trim();
            while (rs.next()) {    // move cursor from BOR to valid record.
                String name = rs.getString("name");
                if (name.toUpperCase().startsWith(q.toUpperCase())) {
                    li.add(name);
                    count++;
                }
            }
            if (count == 0) {
                li.add("No such Status exists");
            }
        } catch (Exception e) {
            System.out.println("getP_name ERROR inside PersonModel" + e);
        }
        return li;

    }


    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("Error in closeConnection -- OHLevelModel : " + e);
        }
    }

    public void setConnection(Connection con) {
        try {

            connection = con;
        } catch (Exception e) {
            System.out.println("PaymentStatusModel setConnection() Error: " + e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriver() {
        return driver;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageBGColor() {
        return messageBGColor;
    }

    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public String getDb_password() {
        return db_password;
    }

    public void setDb_password(String db_password) {
        this.db_password = db_password;
    }

    public String getDb_username() {
        return db_username;
    }

    public void setDb_username(String db_username) {
        this.db_username = db_username;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getMsgBgColor() {
        return msgBgColor;
    }

    public void setMsgBgColor(String msgBgColor) {
        this.msgBgColor = msgBgColor;
    }
}
