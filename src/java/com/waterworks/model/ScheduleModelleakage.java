package com.waterworks.model;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Connection;

import java.io.IOException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;


import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Administrator
 */
public class ScheduleModelleakage extends TimerTask {
  String waterlvlnext="";
    private ServletContext ctx;
    private static Connection connection;
    Date dt = new Date();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String cut_dt = df.format(dt);
static final long Fifteen_MINUTE_IN_MILLIS=900000;
    private HttpSession session;
    private ServletContext context;
    Date dt1 = new Date();
    SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String cut_dt1 = df2.format(dt1);
    public void setCtx(ServletContext ctx) {
        this.ctx = ctx;
    }

    public void run() {
        try {
            System.out.println(" leakage run method is running");      
         //   List list = getOverheadtankId();
       insertDistributionData();    
        } catch (Exception ex) {
            System.out.println(" run() Error: " + ex);
        }
    }
public List getOverheadtankDeviceIdById(String device) {
        List list = new ArrayList();
        String query1 = "select remark from overheadtank where remark!=''";
        try {
            ResultSet rs1 = connection.prepareStatement(query1).executeQuery();
            while (rs1.next()) {
                String overheadtank_id = rs1.getString("remark");
                list.add(overheadtank_id);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return list;
    }
    public List getOverheadtankDeviceId() {
        List list = new ArrayList();
        String query1 = "select remark from overheadtank where remark!='' and type='yes'";
        try {
            ResultSet rs1 = connection.prepareStatement(query1).executeQuery();
            while (rs1.next()) {
                String overheadtank_id = rs1.getString("remark");
                list.add(overheadtank_id);
            } 
        } catch (Exception e) {
            System.out.println(e);
        } 
                     return list;
    }
 public int getWaterStatusId( String device_id) {
        int vehicle_id = 0;
         Connection con=null;
        String query = "select device_status_id from device_status where active='Y' and device_id='"+device_id+"'";
        try {
           Class.forName("com.mysql.jdbc.Driver");
             con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mqtt_server","root","root");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                vehicle_id = rs.getInt("device_status_id");
            }     
          
        } catch (Exception e) {
            System.out.println(e);
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ScheduleModelleakage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return vehicle_id;
    }
 
 public long getShedulartime() {
        long vehicle_id = 0;
         Connection con=null;
        String query = "SELECT time_in_seconds FROM schedular_time where Schedular_Name='leakage_schedular' ";
        try {
          ResultSet rs = connection.prepareStatement(query).executeQuery();
           
            while (rs.next()) {
                vehicle_id = rs.getLong("time_in_seconds");   
            }     
          
        } catch (Exception e) {
            System.out.println(e);
        } 
        return vehicle_id;
    }
 public String getOverheadTankType(String deviceid) {
         String typ="";
        String query = "select type FROM overheadtank where remark='"+deviceid+"'";
        try {
           Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/smart_meter_survey","root","root");
            Statement stmt = con.createStatement();
            ResultSet rset = stmt.executeQuery(query);
            if (rset.next()) {
                 typ= rset.getString(1);
            } else {
                return "";
            }
        } catch (Exception e) {
            System.out.println("Error in getOverHeadTank - OHLevelModel - " + e);
           // message = "Something going wrong";
          //  messageBGColor = "red";
            return "";
        }
        return typ;
    }
  public String getOverheadTankid1(String deviceid) {
         String typ="";
        String query = "select overheadtank_id FROM overheadtank where remark='"+deviceid+"'";
        try {
           Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/smart_meter_survey","root","root");
            Statement stmt = con.createStatement();
            ResultSet rset = stmt.executeQuery(query);
            if (rset.next()) {
                 typ= rset.getString(1);
            } else {
                return "";
            }
        } catch (Exception e) {
            System.out.println("Error in getOverHeadTank - OHLevelModel - " + e);
          //  message = "Something going wrong";
          //  messageBGColor = "red";
            return "";
        }
        return typ;
    }
   public int getOverHeadTankHeight(String deviceid,String type) {
        String overid=getOverheadTankid1(deviceid);
        String query = "select oht.height from overheadtank_height as oht,overheadtank as ot\n" +
"where oht.overheadtank_id=ot.overheadtank_id and ot.type='"+type+"' and oht.overheadtank_id='"+overid+"'";
        try {
          Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/smart_meter_survey","root","root");
            Statement stmt = con.createStatement();
            ResultSet rset = stmt.executeQuery(query);
            if (rset.next()) {
                return rset.getInt(1);
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println("Error in getOverHeadTank - OHLevelModel - " + e);
           // message = "Something going wrong";
          //  messageBGColor = "red";
            return 0;
        }
    }
    public List<String> getWaterLevel(String id) {
               List<String> li=new ArrayList<>();
               int device_status_id = getWaterStatusId(id);
        DateTimeFormatter formatter11 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
 
       
        LocalDateTime datetimenew = LocalDateTime.parse(cut_dt1,formatter11);
 //datetimenew=datetimenew.minusMinutes(3);
        datetimenew=datetimenew.minusMinutes(1);
  String type1=getOverheadTankType(id);
        int a=getOverHeadTankHeight(id,type1);
        String aftersubtraction=datetimenew.format(formatter11);
              // int device_status_id = 5449;
       String status="";
       String temp = "";
       String intensity = "";      
       String datetime="";   
       String relay_status = "";
       int water_id = 0;
       String relay_status1 = "";
       String magnetic_sensor_value="";
        Connection con=null;
        String query = "select wd.water_level from water_data wd where wd.device_status_id='" + device_status_id + "'and wd.created_date >'"+aftersubtraction+"' and water_level not in('9999') order by date_time  desc";
        try {
                ResultSet rs = connection.prepareStatement(query).executeQuery();
//            Class.forName("com.mysql.jdbc.Driver");   
//           con=DriverManager.getConnection("jdbc:mysql://localhost:3306/smart_meter_survey","root","root");
//            Statement stmt = con.createStatement();   
//            ResultSet rs = stmt.executeQuery(query);   
            while (rs.next()) {
                status = rs.getString("water_level");
                int b=0;
                   if(Integer.parseInt(status)>a){
          b= Integer.parseInt(status)-a;
        }else{
      b=a-Integer.parseInt(status);
        }
    
//      System.out.println("waterlvlnext--------"+waterlvlnext);
//        System.out.println("a -----------"+a);
                     status=Integer.toString(b);
//                     int diff=0;
//                        if(!"".equals(waterlvlnext)){
//                            if(Integer.parseInt(waterlvlnext) >=Integer.parseInt(status)){
//                                diff=Integer.parseInt(waterlvlnext) -Integer.parseInt(status);
//                            }else{
//                           diff= Integer.parseInt(status) -Integer.parseInt(waterlvlnext);
//                            }
//                        
//                        if(diff<=500){
//                         waterlvlnext=status;
//                        }else{
//                        status=waterlvlnext;
//                        }
//                        }else{
//                        waterlvlnext=status;
//                        }
						
	  int a1=Integer.parseInt(status)/10;
                 status=String.valueOf(a1);
                li.add(status);
            }
          //  con.close();
        } catch (Exception e) {
            System.out.println(e);
        } 
        return li;
    }
   public void insertDistributionData() throws ParseException {
           List<String> li=new ArrayList<>();
         DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
     Date dateobj = new Date();
     //   System.out.println(df.format(dateobj));
        String datetime = df.format(dateobj);
        
        
        
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
Date date = sdf.parse(datetime);
Calendar cal = Calendar.getInstance();
cal.setTime(date); 
long date4 = cal.getTimeInMillis();

long final_date = date4+Fifteen_MINUTE_IN_MILLIS;
        
         Date revdate = new Date(final_date);
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 //   System.out.println(formatter.format(revdate)); 
     String dateTime_test = formatter.format(revdate);     
   
        int ohlevel_id = 0;
        List deviceli=getOverheadtankDeviceId();   
        
        for(int i=0;i<deviceli.size();i++){        
        
        String deviceid=deviceli.get(i).toString();
      String overid=getOverheadTankid1(deviceid);
      
            li = getWaterLevel(deviceid);
      if(li.size()>0){
            double sumavg=0;
              int sum = 0;
  
            for(int j=0; j<li.size();j++){
            sumavg=sumavg+Double.parseDouble(li.get(j));
            }
            double avg=0.0;    
            if(sumavg!=0){
             avg=sumavg/li.size();
            }
          //  System.out.println(avg);    
            
        int junction_id=Integer.parseInt(overid);
        
        String type = "";
        int temp_distribution_id_last=0;
        String created_date = "";
        String current_dateTime="";
        int previous_temp_dist_leveltype_id=0;
        int level = 0, temp_level = 0, on_off_value=0,time_diff=0,time=0;
      //  int voltage1=Integer.parseInt(waterleveltemp);
        int voltage1=(int)avg;     
        String query = "INSERT INTO ohlevel (level_a,level_b,overheadtank_id,remark,step,level1,level2,level3,level4,date_time) "
                + " VALUES (?,?,?,?,?,?,?,?,?,?)";
        String query9 = "INSERT INTO temp_ohlevel (level_a,level_b,overheadtank_id,remark,step,level1,level2,level3,level4) "
                + " VALUES (?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            PreparedStatement ps9 = (PreparedStatement) connection.prepareStatement(query9);
            ps.setString(1, "0");
            ps.setString(2, "0");
            ps.setInt(3, junction_id);
            
            ps.setInt(4, voltage1);
              ps.setString(5, "0");
            ps.setString(6, "0");
               ps.setString(7, "0");
            ps.setString(8, "0");
             ps.setString(9, "0");
              ps.setString(10, datetime);

        //    --------------------------------- for temp_ohlevel      
            ps9.setString(1, "0");
            ps9.setString(2, "0");
            ps9.setInt(3, junction_id);
            
            ps9.setInt(4, voltage1);
              ps9.setString(5, "0");
            ps9.setString(6, "0");
               ps9.setString(7, "0");    
            ps9.setString(8, "0");
             ps9.setString(9, "0");
          //    ps9.setString(10, datetime);
             ps9.executeUpdate();

          //    ---------------------------------
            if (voltage1 > 0 && voltage1 < 1000) {       
                //context.setAttribute("overflow_" + junction_id, "0");
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
                      if (Double.isNaN(currentlevelRatio)) {
    currentlevelRatio=0;
}
                        if(currentlevelRatio < db_level_ratio && currentlevelRatio > 0.005){   
                            /////////////////////////get level_type for  Leakage////////////////
                            String query11="Select level_type_id,type_name from level_type where type_name='Leakage'";
                             getConnection();
                            ResultSet rs64 = connection.prepareStatement(query11).executeQuery();
                            if (rs64.next()) {
                            levelType = rs64.getInt("level_type_id");
                            levelTypeName=rs64.getString("type_name");
                        }
                        }
                         if(currentlevelRatio >= 0 && currentlevelRatio <= 0.001){
                            /////////////////////////get level_type for  Stable ////////////////
                              String query11="Select level_type_id,type_name from level_type where type_name='Stable'";
                           getConnection();
                              ResultSet rs62 = connection.prepareStatement(query11).executeQuery();
                            if (rs62.next()) {
                            levelType = rs62.getInt("level_type_id");
                            levelTypeName=rs62.getString("type_name");
                        }
                            }
                         if(currentlevelRatio < 0 ){
                            /////////////////////////get level_type for Supply////////////////
                             String query11="Select level_type_id,type_name from level_type where type_name='Supply'";
                          getConnection();
                             ResultSet rs63 = connection.prepareStatement(query11).executeQuery();
                            if (rs63.next()) {
                            levelType = rs63.getInt("level_type_id");
                            levelTypeName=rs63.getString("type_name");
                        }
                            }
                         if(currentlevelRatio >  db_level_ratio){
                            /////////////////////////get level_type for Distribution ////////////////
                             String query11="Select level_type_id,type_name from level_type where type_name='Distribution'";
                             getConnection();
                           ResultSet rs61 = connection.prepareStatement(query11).executeQuery();
                            if (rs61.next()) {
                            levelType = rs61.getInt("level_type_id");
                            levelTypeName=rs61.getString("type_name");
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
                                    getConnection();
                                  ResultSet rs65 = connection.prepareStatement(query15).executeQuery();
                                  if (rs65.next()) {
                                   time_range = rs65.getInt(1);
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
                        getConnection();
                        PreparedStatement ps12 = (PreparedStatement) connection.prepareStatement(query12);
                        ResultSet rs12 = ps12.executeQuery();
                        while(rs12.next()){
                            last_distribution_id=rs12.getInt("distribution_id");
                            last_type=rs12.getString("type");
                            last_date=rs12.getString("created_date");
                            last_waterLevel=rs12.getInt("waterLevel");
                            last_dist_level_type_id=rs12.getInt("level_type_id");
                        //    System.out.println("Last distribution level_type_id= "+last_dist_level_type_id);
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
        }
        }
        return;
    

   }
    public static Connection getConnection() {
        return connection;
    }

    public static void setConnection(Connection connection) {
        ScheduleModelleakage.connection = connection;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println(" closeConnection() Error: " + e);
        }
    }
}
