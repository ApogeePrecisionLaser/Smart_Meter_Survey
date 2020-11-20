/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.waterworks.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import com.waterworks.tableClasses.LevelRangeBean;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Shobha
 */
public class LevelRangeModel {
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
    DecimalFormat df = new DecimalFormat("0.00");
    public int totalLeakage=0;
    public int totalStable=0;
    public int totalSupply=0;
    public int totalDistribution=0;

    public int totalLeakageTime=0;
    public int totalStableTime=0;
    public int totalSupplyTime=0;
    public int totalDistributionTime=0;

    public void setConnection() {
        try {
            Class.forName(driver);
            // connection = DriverManager.getConnection(connectionString+"?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", db_username, db_password);
            connection = (Connection) DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println("WattageTypeModel setConnection() Error: " + e);
        }
    }

    public int getTotalRowsInTable(String searchDateFrom,String searchTimeFrom,String searchDateTo,String searchTimeTo,String searchOverheadtankName) {
        if (searchDateFrom != null && !searchDateFrom.isEmpty()) {
            String[] sdate_array = searchDateFrom.split("-");
            searchDateFrom = sdate_array[2] + "-" + sdate_array[1] + "-" + sdate_array[0];

            searchDateFrom=searchDateFrom+" "+searchTimeFrom;
        }
        if (searchDateTo != null && !searchDateTo.isEmpty()) {
            String[] sdate_array = searchDateTo.split("-");
            searchDateTo = sdate_array[2] + "-" + sdate_array[1] + "-" + sdate_array[0];

            searchDateTo=searchDateTo+" "+searchTimeTo;
        }


//        String query = " select count(distribution_id) from watertreatmentplant AS wtp,overheadtank AS oht,"
//                + " ohlevel as ol,distribution as d"
//                + " where oht.watertreatmentplant_id = wtp.watertreatmentplant_id"
//                + " and ol.overheadtank_id = oht.overheadtank_id and ol.ohlevel_id=d.ohlevel_id "
//                + " And IF('" + searchDateFrom + "'='', ol.date_time LIKE '%%',ol.date_time >='" + searchDateFrom + "') "
//                + " And IF('" + searchDateTo + "'='', ol.date_time LIKE '%%',date_format(ol.date_time, '%Y-%m-%d') <='" + searchDateTo + "') "
//                + " And IF('" + searchOverheadtankName + "' = '',oht.name LIKE '%%', oht.name='" + searchOverheadtankName + "')";

//        String query = " select count(*) "
//                      +" from watertreatmentplant wtp,overheadtank oht,temp_distribution td "
//                      +" where td.overheadtank_id=oht.overheadtank_id "
//                      +" and oht.watertreatmentplant_id=wtp.watertreatmentplant_id "
//                      +" order by td.created_date desc"
//                      + " And IF('" + searchDateFrom + "'='', td.created_date LIKE '%%',td.created_date >='" + searchDateFrom + "') "
//                + " And IF('" + searchDateTo + "'='', td.created_date LIKE '%%',date_format(td.created_date, '%Y-%m-%d') <='" + searchDateTo + "') "
//                + " And IF('" + searchOverheadtankName + "' = '',oht.name LIKE '%%', oht.name='" + searchOverheadtankName + "')";

        String query = " select count(*) "
                      +" from watertreatmentplant wtp,overheadtank oht,temp_distribution td "
                      +" where td.overheadtank_id=oht.overheadtank_id "
                      +" and oht.watertreatmentplant_id=wtp.watertreatmentplant_id "
                      + " And IF('" + searchDateFrom + "'='', td.created_date LIKE '%%',td.created_date >='" + searchDateFrom + "') "
                      + " And IF('" + searchDateTo + "'='', td.created_date LIKE '%%',td.created_date <='" + searchDateTo + "') "
                      + " And IF('" + searchOverheadtankName + "' = '',oht.name LIKE '%%', oht.name='" + searchOverheadtankName + "')";

        int noOfRows = 0;
        try {
            PreparedStatement presta = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rs = presta.executeQuery();
            rs.next();
            noOfRows = rs.getInt(1);
        } catch (Exception e) {
            System.out.println("Error inside getTotalRowsInTable - onoffmodel : " + e);

        }
        System.out.println("No of Rows in Table for search is****....." + noOfRows);
        return noOfRows;
    }

    public ArrayList<LevelRangeBean> getAllRecords(int lowerLimit, int noOfRowsToDisplay, String searchDateFrom,String searchTimeFrom,String searchDateTo,String searchTimeTo,String searchOverheadtankName) {
        ArrayList<LevelRangeBean> list = new ArrayList<LevelRangeBean>();
        LevelRangeBean levelRangeBean1 = new LevelRangeBean();
        String waterTreatmentPlantName="";

        if (searchDateFrom != null && !searchDateFrom.isEmpty()) {
            String[] sdate_array = searchDateFrom.split("-");
            searchDateFrom = sdate_array[2] + "-" + sdate_array[1] + "-" + sdate_array[0];

            searchDateFrom=searchDateFrom+" "+searchTimeFrom;
        }
        if (searchDateTo != null && !searchDateTo.isEmpty()) {
            String[] sdate_array = searchDateTo.split("-");
            searchDateTo = sdate_array[2] + "-" + sdate_array[1] + "-" + sdate_array[0];

            searchDateTo=searchDateTo+" "+searchTimeTo;
        }
        byte[] twoByteData = new byte[2];
        String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        if (lowerLimit == -1) {
            addQuery = "";
        }

        if(searchOverheadtankName != null && !searchOverheadtankName.isEmpty()){
            waterTreatmentPlantName = getWaterTreatmentPlantName(searchOverheadtankName);
        }


        String query="select td.temp_distribution_id,wtp.name as wtpName,oht.name as ohtName,td.level, "
                     +" td.type,td.created_date,td.level_range,td.time_range "
                     +" from watertreatmentplant wtp,overheadtank oht,temp_distribution td "
                     +" where td.overheadtank_id=oht.overheadtank_id "
                     +" and oht.watertreatmentplant_id=wtp.watertreatmentplant_id "
                     + " And IF('" + searchDateFrom + "'='', td.created_date LIKE '%%',td.created_date >='" + searchDateFrom + "') "
                     + " And IF('" + searchDateTo + "'='', td.created_date LIKE '%%',td.created_date <='" + searchDateTo + "') "
                     + " And IF('" + searchOverheadtankName + "' = '',oht.name LIKE '%%', oht.name='" + searchOverheadtankName + "') "
                     +" order by td.created_date desc "
                     + addQuery;

        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                LevelRangeBean levelRangeBean = new LevelRangeBean();
                levelRangeBean.setTemp_distribution_id(rset.getInt("temp_distribution_id"));
                levelRangeBean.setWtpName(rset.getString("wtpName"));
                levelRangeBean.setOhtName(rset.getString("ohtName"));
                levelRangeBean.setLevel(rset.getInt("level"));
                levelRangeBean.setType(rset.getString("type"));
                String type=rset.getString("type");
                levelRangeBean.setCreated_date(rset.getString("created_date"));
                levelRangeBean.setLevel_range(rset.getInt("level_range"));
                levelRangeBean.setTime_range(rset.getInt("time_range"));
                if(type.equals("Leakage")){
                  totalLeakage=totalLeakage+rset.getInt("level_range");
              //    levelRangeBean.setTotal_leakage(totalLeakage);
                  totalLeakageTime=totalLeakageTime+rset.getInt("time_range");
                //  levelRangeBean.setTotal_leakage_time(totalLeakageTime);
                }
                else if(type.equals("Stable"))
                {
                    totalStable=totalStable+rset.getInt("level_range");
               //     levelRangeBean.setTotal_stable(totalStable);
                    totalStableTime=totalStableTime+rset.getInt("time_range");
               //     levelRangeBean.setTotal_stable_time(totalStableTime);
                }
                else if(type.equals("Supply")){
                   totalSupply=totalSupply+rset.getInt("level_range");
                //   levelRangeBean.setTotal_supply(totalSupply);
                   totalSupplyTime=totalSupplyTime+rset.getInt("time_range");
                 //  levelRangeBean.setTotal_supply_time(totalSupplyTime);

                }
                else if(type.equals("Distribution")){
                     totalDistribution=totalDistribution+rset.getInt("level_range");
                    // levelRangeBean.setTotal_distribution(totalDistribution);
                     totalDistributionTime=totalDistributionTime+rset.getInt("time_range");
                   //  levelRangeBean.setTotal_distribution_time(totalDistributionTime);
                }
               else{
                    System.out.println("Not matched of any type(Leakage,Stable,Supply,Distribution) ");
                }

                list.add(levelRangeBean);

            }
            levelRangeBean1.setTotal_leakage(totalLeakage);
            levelRangeBean1.setTotal_leakage_time(totalLeakageTime);
            levelRangeBean1.setTotal_stable(totalStable);
            levelRangeBean1.setTotal_stable_time(totalStableTime);
            levelRangeBean1.setTotal_supply(totalSupply);
             levelRangeBean1.setTotal_supply_time(totalSupplyTime);
             levelRangeBean1.setTotal_distribution(totalDistribution);
             levelRangeBean1.setTotal_distribution_time(totalDistributionTime);
             levelRangeBean1.setOverheadTankName(searchOverheadtankName);
             levelRangeBean1.setWaterTreatmentPlantName(waterTreatmentPlantName);
             try{
             String splitDateTimeFrom[] = searchDateFrom.split(" ");
             String splitDateTimeTo[] = searchDateTo.split(" ");

             levelRangeBean1.setDateFrom(splitDateTimeFrom[0]);
             levelRangeBean1.setTimeFrom(splitDateTimeFrom[1]);
             levelRangeBean1.setDtaeTo(splitDateTimeTo[0]);
             levelRangeBean1.setTimeTo(splitDateTimeTo[1]);
            }catch(Exception e){
                System.out.println("Error while spliting searchDateTimeFromTo "+e);
            }


            list.add(levelRangeBean1);
        } catch (Exception e) {
            System.out.println("Error in getAllRecords -- OnOFF Model : " + e);

        }
        return list;
    }



    public ArrayList<LevelRangeBean> getAllRecordsLevelRangeCount(int lowerLimit, int noOfRowsToDisplay, String searchDateFrom,String searchTimeFrom,String searchDateTo,String searchTimeTo,String searchOverheadtankName) {
        ArrayList<LevelRangeBean> list = new ArrayList<LevelRangeBean>();

        totalLeakage=0;
        totalStable=0;
        totalSupply=0;
        totalDistribution=0;

        totalLeakageTime=0;
        totalStableTime=0;
        totalSupplyTime=0;
        totalDistributionTime=0;

        if (searchDateFrom != null && !searchDateFrom.isEmpty()) {
            String[] sdate_array = searchDateFrom.split("-");
            searchDateFrom = sdate_array[2] + "-" + sdate_array[1] + "-" + sdate_array[0];

            searchDateFrom=searchDateFrom+" "+searchTimeFrom;
        }
        if (searchDateTo != null && !searchDateTo.isEmpty()) {
            String[] sdate_array = searchDateTo.split("-");
            searchDateTo = sdate_array[2] + "-" + sdate_array[1] + "-" + sdate_array[0];

            searchDateTo=searchDateTo+" "+searchTimeTo;
        }
        byte[] twoByteData = new byte[2];
        String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        if (lowerLimit == -1) {
            addQuery = "";
        }


        

//        String query="select td.temp_distribution_id,wtp.name as wtpName,oht.name as ohtName,td.level, "
//                     +" td.type,td.created_date,td.level_range,td.time_range "
//                     +" from watertreatmentplant wtp,overheadtank oht,temp_distribution td "
//                     +" where td.overheadtank_id=oht.overheadtank_id "
//                     +" and oht.watertreatmentplant_id=wtp.watertreatmentplant_id "
//                     + " And IF('" + searchDateFrom + "'='', td.created_date LIKE '%%',td.created_date >='" + searchDateFrom + "') "
//                + " And IF('" + searchDateTo + "'='', td.created_date LIKE '%%',date_format(td.created_date, '%Y-%m-%d') <='" + searchDateTo + "') "
//                + " And IF('" + searchOverheadtankName + "' = '',oht.name LIKE '%%', oht.name='" + searchOverheadtankName + "') "
//                     +" order by td.created_date desc ";
//
        String query="select td.temp_distribution_id,wtp.name as wtpName,oht.name as ohtName,td.level, "
                     +" td.type,td.created_date,td.level_range,td.time_range "
                     +" from watertreatmentplant wtp,overheadtank oht,temp_distribution td "
                     +" where td.overheadtank_id=oht.overheadtank_id "
                     +" and oht.watertreatmentplant_id=wtp.watertreatmentplant_id "
                     + " And IF('" + searchDateFrom + "'='', td.created_date LIKE '%%',td.created_date >='" + searchDateFrom + "') "
                     + " And IF('" + searchDateTo + "'='', td.created_date LIKE '%%',td.created_date <='" + searchDateTo + "') "
                     + " And IF('" + searchOverheadtankName + "' = '',oht.name LIKE '%%', oht.name='" + searchOverheadtankName + "') "
                     +" order by td.created_date desc ";


        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                LevelRangeBean levelRangeBean = new LevelRangeBean();
                levelRangeBean.setTemp_distribution_id(rset.getInt("temp_distribution_id"));
                levelRangeBean.setWtpName(rset.getString("wtpName"));
                levelRangeBean.setOhtName(rset.getString("ohtName"));
                levelRangeBean.setLevel(rset.getInt("level"));
                levelRangeBean.setType(rset.getString("type"));
                String type=rset.getString("type");
                levelRangeBean.setCreated_date(rset.getString("created_date"));
                levelRangeBean.setLevel_range(rset.getInt("level_range"));
                levelRangeBean.setTime_range(rset.getInt("time_range"));
                if(type.equals("Leakage")){
                  totalLeakage=totalLeakage+rset.getInt("level_range");
                  totalLeakageTime=totalLeakageTime+rset.getInt("time_range");
                }
                else if(type.equals("Stable"))
                {
                    totalStable=totalStable+rset.getInt("level_range");
                    totalStableTime=totalStableTime+rset.getInt("time_range");
                }
                else if(type.equals("Supply")){
                   totalSupply=totalSupply+rset.getInt("level_range");
                   totalSupplyTime=totalSupplyTime+rset.getInt("time_range");

                }
                else if(type.equals("Distribution")){
                     totalDistribution=totalDistribution+rset.getInt("level_range");
                     totalDistributionTime=totalDistributionTime+rset.getInt("time_range");
                }
               else{
                    System.out.println("Not matched of any type(Leakage,Stable,Supply,Distribution) ");
                }

                list.add(levelRangeBean);
            }


        } catch (Exception e) {
            System.out.println("Error in getAllRecordsLevelRangeCount -- OnOFF Model : " + e);

        }
        return list;
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


     public String getWaterTreatmentPlantName(String overheadTankname){

        String waterTreatmentPlantName ="";
        String query = " select wtp.name "
                       +" from overheadtank oht,watertreatmentplant wtp "
                       +" where oht.watertreatmentplant_id=wtp.watertreatmentplant_id "
                       +" and oht.name='"+overheadTankname+"' ";
        try {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {    // move cursor from BOR to valid record.
                waterTreatmentPlantName = rs.getString("name");

            }

        } catch (Exception e) {
            System.out.println("ERROR inside getWaterTreatmentPlantName()" + e);
        }
        return waterTreatmentPlantName;

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
