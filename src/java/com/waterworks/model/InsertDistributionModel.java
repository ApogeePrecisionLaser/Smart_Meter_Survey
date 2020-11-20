/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.waterworks.model;

//import com.mysql.jdbc.Connection;
////import com.mysql.jdbc.PreparedStatement;
//import java.sql.DriverManager;
////import java.sql.ResultSet;
//import java.sql.ResultSet;
//import javax.servlet.ServletContext;


import java.sql.PreparedStatement;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
/**
 *
 * @author Shobha
 */
public class InsertDistributionModel {
    
    private Connection connection;
    private String driver, url, user, password;
    private String message, messageBGColor;
    private String driverClass;
    private String connectionString;
    private String db_username;
    private String db_password;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";
    //private ServletContext context;
    public void setConnection() {
        try {
            Class.forName(driver);
            // connection = DriverManager.getConnection(connectionString+"?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", db_username, db_password);
            connection = (Connection) DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println("WattageTypeModel setConnection() Error: " + e);
        }
    }


    public void insertInToDistribution(){
      
        int ohlevel_id = 0;
        String type = "";
        String created_date = "";
        String current_dateTime="";
        int previous_temp_dist_leveltype_id=0;
        int level = 0, temp_level = 0, on_off_value=0,time_diff=0,time=0;
         int count=0;
//        String query = "INSERT INTO ohlevel (level_a,level_b,overheadtank_id,remark,step,level1,level2,level3,level4) "
//                + " VALUES (?,?,?,?,?,?,?,?,?)";
//        String query9 = "INSERT INTO temp_ohlevel (level_a,level_b,overheadtank_id,remark,step,level1,level2,level3,level4) "
//                + " VALUES (?,?,?,?,?,?,?,?,?)";
        try {
//            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
//            PreparedStatement ps9 = (PreparedStatement) connection.prepareStatement(query9);
//            ps.setByte(1, data[0]);
//            ps.setByte(2, data[1]);
//            ps.setInt(3, junction_id);
//            twoByteData[0] = data[0];
//            twoByteData[1] = data[1];
//            long voltage1 = (new BigInteger(twoByteData).longValue());
//            ps.setString(4, ("" + voltage1).trim());
//            ps.setByte(5, data[2]);
//            ps.setByte(6, data[3]);
//            ps.setByte(7, data[4]);
//            ps.setByte(8, data[5]);
//            ps.setByte(9, data[6]);
//
//        //    --------------------------------- for temp_ohlevel
//            ps9.setByte(1, data[0]);
//            ps9.setByte(2, data[1]);
//            ps9.setInt(3, junction_id);
//            ps9.setString(4, ("" + voltage1).trim());
//            ps9.setByte(5, data[2]);
//            ps9.setByte(6, data[3]);
//            ps9.setByte(7, data[4]);
//            ps9.setByte(8, data[5]);
//            ps9.setByte(9, data[6]);
//             ps9.executeUpdate();

        String allOhlevelRecordQuery="select ohlevel_id,overheadtank_id,date_time,remark "
                                     +" from smart_meter_survey.ohlevel o "
                                     +" where o.ohlevel_id > 110908 "
                                     +" limit 6000 ";

                   PreparedStatement pst =  (PreparedStatement) connection.prepareStatement(allOhlevelRecordQuery);
                   ResultSet rset = pst.executeQuery();
                   while(rset.next()){
                       int voltage1=Integer.parseInt(rset.getString("remark"));
                       int junction_id=rset.getInt("overheadtank_id");
                        ohlevel_id=rset.getInt("ohlevel_id");
                        current_dateTime=rset.getString("date_time");
                        count=ohlevel_id;


                        try{
             String query14=" update ohlevel ohl set ohl.status='Yes' "
                           +" where ohl.ohlevel_id= "+count;
             PreparedStatement ps5 = (PreparedStatement) connection.prepareStatement(query14);
             ps5.executeUpdate();
             System.out.println("count value="+count);
             ps5.close();

         }catch(Exception e){
             System.out.println("exception while updating ohlevel status column "+e);
         }
          //    ---------------------------------
            if (voltage1 > 0 && voltage1 < 1000) {
               // context.setAttribute("overflow_" + junction_id, "0");
//                ps.executeUpdate();
//                ResultSet rs = ps.getGeneratedKeys();
//                if (rs.next()) {
//                    ohlevel_id = rs.getInt(1);
//                }
                ///////////////////////get Date Time of latest inserted value in ohlevel table start////////
//                String queryDate=" select date_time from smart_meter_survey.ohlevel ohl "
//                                 +" where ohl.ohlevel_id="+ohlevel_id
//                                 +" and ohl.overheadtank_id="+junction_id;
//
//                 ResultSet rs13 = connection.prepareStatement(queryDate).executeQuery();
//                              if (rs13.next()) {
//                            current_dateTime = rs13.getString("date_time");
//                        }

                ///////////////////////get Date Time of latest inserted value in ohlevel table end////////
                if (ohlevel_id > 0) {
                    //  String query1 = "select remark from ohlevel where ohlevel_id=( select max(ohlevel_id) from ohlevel "
                    //          + " where ohlevel_id not in (select max(ohlevel_id) from ohlevel where overheadtank_id=" + junction_id + ") and overheadtank_id=" + junction_id + " )";
                    String query1 = "select * from "
                                   +" (select * from ohlevel where overheadtank_id="+junction_id
                                   +" and  ohlevel_id <= "+ohlevel_id
                                   +" order by ohlevel_id desc limit 2 ) a "
                                   +" order by ohlevel_id limit 1 ";
                    try {
                        ResultSet rs1 = connection.prepareStatement(query1).executeQuery();
                        if (rs1.next()) {
                            level = rs1.getInt("remark");
                        }
                        rs1.close();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    //String query4 = "select type,level,created_date from temp_distribution where overheadtank_id='" + junction_id + "'  order by temp_distribution_id desc";
                    String query4=" select lt.level_type_id,type,level,td.created_date  "
                                 +" from temp_distribution1 td,level_type lt "
                                 +" where overheadtank_id="+junction_id
                                 +" and td.level_type_id=lt.level_type_id "
                                 +" order by temp_distribution_id desc LIMIT 1 ";
                    try {
                        ResultSet rs4 = connection.prepareStatement(query4).executeQuery();
                        if (rs4.next()) {
                            type = rs4.getString("type");
                            created_date = rs4.getString("created_date");
                            temp_level = rs4.getInt("level");
                            previous_temp_dist_leveltype_id=rs4.getInt("level_type_id");
                        }
                        rs4.close();
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
                        rs5.close();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
//                    String query8 = "SELECT TIMESTAMPDIFF(MINUTE,'" + cut_dt1 + "','" + created_date + "' ) ";
//                    String query8 = "SELECT TIMESTAMPDIFF(MINUTE,'" + created_date + "','" + cut_dt1 + "' ) ";
//                    try {
//
//                        ResultSet rs6 = connection.prepareStatement(query8).executeQuery();
//                        if (rs6.next()) {
//                            time_diff = rs6.getInt(1);
//                        }
//                    } catch (Exception e) {
//                        System.out.println(e);
//                    }
                    //////////////////////////////for lekage start////////////////////
                    int previousThirdLevel=0;
                    String previousThirdLevelDate_Time="";
                    int newTimeDifference=0;
                    int levelType=0;
                    String levelTypeName="";
                    String lekageQuery="select ohlevel_id,overheadtank_id,date_time,remark from (select ohlevel_id,overheadtank_id,date_time,remark "
                                       +" from ohlevel ohl "
                                       +" where ohl.overheadtank_id="+junction_id
                                       +" and ohlevel_id < "+ohlevel_id
                                       +" order by ohlevel_id DESC limit 3) a "
                                       +" order by ohlevel_id limit 1 ";
                    try{
                    ResultSet rs5 = connection.prepareStatement(lekageQuery).executeQuery();
                    while(rs5.next()){
                    previousThirdLevel=rs5.getInt("remark");
                    previousThirdLevelDate_Time=rs5.getString("date_time");

                        }
                    rs5.close();
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
                        rs2.close();
////////////////////////////calculate time difference start/////////////
                     String query10="SELECT TIMESTAMPDIFF(MINUTE,'" + previousThirdLevelDate_Time + "','" + current_dateTime + "' ) ";
                        try {

                        ResultSet rs6 = connection.prepareStatement(query10).executeQuery();
                        if (rs6.next()) {
                            newTimeDifference = rs6.getInt(1);

                        }
                        rs6.close();
                    } catch (Exception e) {
                        System.out.println(e);
                    }

////////////////////////////calculate time difference start/////////////
                     if(newTimeDifference == 0){
                         newTimeDifference=1;
                     }

                        double currentlevelRatio = (7 / Double.parseDouble((-3)+".0"));
                        if(currentlevelRatio < db_level_ratio && currentlevelRatio > 0.005){
                            /////////////////////////get level_type for  Leakage////////////////
                            String query11="Select level_type_id,type_name from level_type where type_name='Leakage'";
                            ResultSet rs6 = connection.prepareStatement(query11).executeQuery();
                            if (rs6.next()) {
                            levelType = rs6.getInt("level_type_id");
                            levelTypeName=rs6.getString("type_name");
                        }
                            rs6.close();
                        }
                         if(currentlevelRatio >= 0 && currentlevelRatio <= 0.005){
                            /////////////////////////get level_type for  Stable ////////////////
                              String query11="Select level_type_id,type_name from level_type where type_name='Stable'";
                           ResultSet rs6 = connection.prepareStatement(query11).executeQuery();
                            if (rs6.next()) {
                            levelType = rs6.getInt("level_type_id");
                            levelTypeName=rs6.getString("type_name");
                        }
                           rs6.close();
                            }
                         if(currentlevelRatio < 0 ){
                            /////////////////////////get level_type for Supply////////////////
                             String query11="Select level_type_id,type_name from level_type where type_name='Supply'";
                           ResultSet rs6 = connection.prepareStatement(query11).executeQuery();
                            if (rs6.next()) {
                            levelType = rs6.getInt("level_type_id");
                            levelTypeName=rs6.getString("type_name");
                        }
                           rs6.close();
                            }
                         if(currentlevelRatio >=  db_level_ratio){
                            /////////////////////////get level_type for Distribution ////////////////
                             String query11="Select level_type_id,type_name from level_type where type_name='Distribution'";
                           ResultSet rs6 = connection.prepareStatement(query11).executeQuery();
                            if (rs6.next()) {
                            levelType = rs6.getInt("level_type_id");
                            levelTypeName=rs6.getString("type_name");
                        }
                           rs6.close();
                            }


                        if(levelType == 0){
                            System.out.println("------------------------------levelTypeId="+levelType);
                        }

                           ////////////////////////insert into temp_distribution/////////////////////
                        int temp_dist_id=0;
                            if(levelType != previous_temp_dist_leveltype_id){
                         String query3 = "insert into temp_distribution1 (overheadtank_id,level,type,created_date,level_type_id) values(?,?,?,?,?)";
                        PreparedStatement ps3 = (PreparedStatement) connection.prepareStatement(query3);
                        ps3.setInt(1, junction_id);
                        ps3.setInt(2, level);
                        ps3.setString(3,levelTypeName);
                        ps3.setString(4,current_dateTime);
                        ps3.setInt(5,levelType);
                        ps3.executeUpdate();
                        ResultSet rs = ps3.getGeneratedKeys();
                            if (rs.next()) {
                              temp_dist_id = rs.getInt(1);
                                 }
                        rs.close();
                        System.out.println("Record successfully inserted in temp_distribution and type="+levelTypeName);
                        ////////////////////////////insert into distribution ////////////////////
                        int last_dist_level_type_id=0;
                        String query12="select d.distribution_id,oht.overheadtank_id,d.type,d.created_date,d.level_type_id "
                                       +" from overheadtank oht,ohlevel ohl,distribution1 d "
                                       +" where ohl.ohlevel_id=d.ohlevel_id "
                                       +" and ohl.overheadtank_id=oht.overheadtank_id "
                                       +" and oht.overheadtank_id="+junction_id
                                       +" order by d.distribution_id desc Limit 1 ";
                        PreparedStatement ps12 = (PreparedStatement) connection.prepareStatement(query12);
                        ResultSet rs12 = ps12.executeQuery();
                        while(rs12.next()){
                            last_dist_level_type_id=rs12.getInt("level_type_id");
                            System.out.println("Last distribution1 level_type_id= "+last_dist_level_type_id);
                        }
                        rs12.close();
                        ///////////////////////////calculate time difference/////////////////////
                        String current_temp_dist_Date="";
                        String query13=" select created_date "
                                   +" from temp_distribution1 td "
                                   +" where td.overheadtank_id= "+junction_id
                                   +" and td.temp_distribution_id= "+temp_dist_id;
                        PreparedStatement ps13 = (PreparedStatement) connection.prepareStatement(query13);
                        ResultSet rs13 = ps13.executeQuery();
                        while(rs13.next()){
                            current_temp_dist_Date=rs13.getString("created_date");
                            System.out.println("Last distribution level_type_id= "+last_dist_level_type_id);
                        }
                        rs13.close();

                        String query8 = "SELECT TIMESTAMPDIFF(MINUTE,'" + created_date + "','" + current_temp_dist_Date + "' ) ";
                    try {

                        ResultSet rs6 = connection.prepareStatement(query8).executeQuery();
                        if (rs6.next()) {
                            time_diff = rs6.getInt(1);
                        }
                        rs.close();
                    } catch (Exception e) {
                        System.out.println(e);
                    }



                         if (((voltage1 - temp_level) > on_off_value) && (time<time_diff) && (last_dist_level_type_id != levelType)) {
                            String query5 = "insert into distribution1 (ohlevel_id,type,created_date,level_type_id) values(?,?,?,?)";
                            PreparedStatement ps5 = (PreparedStatement) connection.prepareStatement(query5);
                            ps5.setInt(1, ohlevel_id);
                            ps5.setString(2,levelTypeName);
                             ps5.setString(3,current_dateTime);
                            ps5.setInt(4,levelType);
                            ps5.executeUpdate();
                        }

                        if (((temp_level - voltage1) > on_off_value) && (time<time_diff) && (last_dist_level_type_id != levelType)) {
                            String query5 = "insert into distribution1 (ohlevel_id,type,created_date,level_type_id) values(?,?,?,?)";
                            PreparedStatement ps5 = (PreparedStatement) connection.prepareStatement(query5);
                            ps5.setInt(1, ohlevel_id);
                            ps5.setString(2,levelTypeName);
                             ps5.setString(3,current_dateTime);
                            ps5.setInt(4,levelType);
                            ps5.executeUpdate();
                        }

                        System.gc();
                            }

                       // }
                    }catch(Exception e){
                         System.out.println("Error in OHLevelEntryModel byte_data method..."+e);
                    }

                }
            }
            else{
                //context.setAttribute("overflow_" + junction_id, "1");
                System.out.println("level value is greater than 1000");
            }}///////////////////////closing of ResultSet/////////////////
                   rset.close();

        } catch (Exception e) {
            System.out.println("insertRecord ERROR inside SmartMeterSurveyServiceModel - " + e);
        }

        //return;

    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
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

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageBGColor() {
        return messageBGColor;
    }

    public void setMessageBGColor(String messageBGColor) {
        this.messageBGColor = messageBGColor;
    }

    public String getMsgBgColor() {
        return msgBgColor;
    }

    public void setMsgBgColor(String msgBgColor) {
        this.msgBgColor = msgBgColor;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

   

}
