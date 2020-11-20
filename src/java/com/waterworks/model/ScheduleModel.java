package com.waterworks.model;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Connection;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimerTask;

import javax.servlet.ServletContext;

/**
 *
 * @author Administrator
 */
public class ScheduleModel extends TimerTask {

    private ServletContext ctx;
    private static Connection connection;
    Date dt = new Date();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String cut_dt = df.format(dt);

    public void setCtx(ServletContext ctx) {
        this.ctx = ctx;
    }

    public void run() {
        try {
            System.out.println("run method is running");
            List list = getOverheadtankId();
            checkOnOff(list);
        } catch (Exception ex) {
            System.out.println(" run() Error: " + ex);
        }
    }

    public List getOverheadtankId() {
        List list = new ArrayList();
        String query1 = "select overheadtank_id from ohlevel group by overheadtank_id ";
        try {
            ResultSet rs1 = connection.prepareStatement(query1).executeQuery();
            while (rs1.next()) {
                String overheadtank_id = rs1.getString("overheadtank_id");
                list.add(overheadtank_id);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return list;
    }

    public void checkOnOff(List list) {
        int value = 0;
        String query4 = "select value from type_of_error where type_of_error_id=1";
        try {
            ResultSet rs4 = connection.prepareStatement(query4).executeQuery();
            if (rs4.next()) {
                value = rs4.getInt("value");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        Iterator<String> iter = list.iterator();
        while (iter.hasNext()) {
            String temp1 = "";
            String data = iter.next();
            String query = " select distribution_id,d.created_date,d.type,o.mor_on_time,o.mor_off_time,o.eve_on_time,o.eve_off_time from "
                    + " distribution as d,ohlevel as ol,overheadtank as o"
                    + " where d.ohlevel_id=ol.ohlevel_id and ol.overheadtank_id='" + data + "' and o.overheadtank_id=ol.overheadtank_id"
                    + " AND IF('" + cut_dt + "'='', d.created_date LIKE '%%',d.created_date >='" + cut_dt + "') ";
            try {
                ResultSet rs1 = connection.prepareStatement(query).executeQuery();
                while (rs1.next()) {
                    int distribution_id = rs1.getInt("distribution_id");
                    int error_id = checkErrorLogEntry(distribution_id);
                    if (error_id == 0) {
                        String type = rs1.getString("type");
                        String created_date = rs1.getString("created_date");
                        String mor_on_time = rs1.getString("mor_on_time");
                        String mor_off_time = rs1.getString("mor_off_time");
                        String eve_on_time = rs1.getString("eve_on_time");
                        String eve_off_time = rs1.getString("eve_off_time");
                        String query2 = "";
                        if (Integer.parseInt(created_date.split(" ")[1].split(":")[0]) < 10 && type.equals("On")) {
                            temp1 = "Morning";
                            query2 = "SELECT TIMESTAMPDIFF(MINUTE,'" + cut_dt + " " + mor_on_time + "','" + created_date + "' ) ";
                        } else if (Integer.parseInt(created_date.split(" ")[1].split(":")[0]) < 10 && type.equals("Off")) {
                            temp1 = "Morning";
                            query2 = "SELECT TIMESTAMPDIFF(MINUTE,'" + cut_dt + " " + mor_off_time + "','" + created_date + "' ) ";
                        } else if (Integer.parseInt(created_date.split(" ")[1].split(":")[0]) > 10 && type.equals("On")) {
                            temp1 = "Evening";
                            query2 = "SELECT TIMESTAMPDIFF(MINUTE,'" + cut_dt + " " + eve_on_time + "','" + created_date + "' ) ";
                        } else if (Integer.parseInt(created_date.split(" ")[1].split(":")[0]) > 10 && type.equals("Off")) {
                            temp1 = "Evening";
                            query2 = "SELECT TIMESTAMPDIFF(MINUTE,'" + cut_dt + " " + eve_off_time + "','" + created_date + "' ) ";
                        }
                        try {
                            ResultSet rs2 = connection.prepareStatement(query2).executeQuery();
                            if (rs2.next()) {
                                int time_diff = rs2.getInt(1);
                                String temp = "";
                                if (time_diff > 0) {
                                    temp = "After";
                                } else {
                                    temp = "Before";
                                }
                                if (Math.abs(time_diff) > value) {
                                    int error_log_id = 0;
                                    String query3 = "insert into error_log (overheadtank_id,type_of_error_id,status_id,error_value,remark,distribution_id) values(?,?,?,?,?,?)";
                                    PreparedStatement ps1 = (PreparedStatement) connection.prepareStatement(query3);
                                    ps1.setInt(1, Integer.parseInt(data));
                                    ps1.setInt(2, 1);
                                    ps1.setInt(3, 1);
                                    ps1.setString(4, "" + Math.abs(time_diff));
                                    String message = "Set " + type + " in the " + temp1 + " " + temp + " time";
                                    ps1.setString(5, message);
                                    ps1.setInt(6, distribution_id);
                                    ps1.executeUpdate();
                                    ResultSet rs = ps1.getGeneratedKeys();
                                    if (rs.next()) {
                                        error_log_id = rs.getInt(1);
                                    }
                                    if (error_log_id > 0) {
//                                        String query5 = "select kp.mobile_no1,overheadtank_keyperson_id from error_log el,status s,type_of_error tof,designation d,key_person kp,"
//                                                + " overheadtank_keyperson ok,overheadtank o where "
//                                                + " el.type_of_error_id=tof.type_of_error_id and el.status_id=s.status_id and tof.designation_id=d.designation_id and"
//                                                + " kp.designation_id=d.designation_id and ok.key_person_id=kp.key_person_id and ok.overheadtank_id=o.overheadtank_id"
//                                                + " and o.overheadtank_id=" + Integer.parseInt(data) + " and error_log_id=" + error_log_id;

//                                         String query5 = "select kp.mobile_no1,overheadtank_keyperson_id from error_log el,status s,type_of_error tof,designation d,key_person kp,"
//                                                + " overheadtank_keyperson ok,overheadtank o,node1 n where "
//                                                + " el.type_of_error_id=tof.type_of_error_id and el.status_id=s.status_id and tof.node_id=n.node_id and"
//                                                + " kp.designation_id=d.designation_id and ok.key_person_id=kp.key_person_id and ok.overheadtank_id=o.overheadtank_id"
//                                                + " and o.overheadtank_id=" + Integer.parseInt(data) + " and error_log_id=" + error_log_id;
                                        int node_id=getNode_id(error_log_id);//call getNode_id()


//                                         String query7="select kp.mobile_no1,kp.key_person_name,ohtkp.overheadtank_keyperson_id "
//                                                 +" from error_log el,type_of_error toe,overheadtank_keyperson ohtkp,key_person kp,overheadtank o "
//                                                 +" where el.overheadtank_id=o.overheadtank_id "
//                                                 +" and ohtkp.overheadtank_id=o.overheadtank_id "
//                                                 +" and ohtkp.key_person_id=kp.key_person_id "
//                                                 +" and el.error_log_id="+error_log_id
//                                                 +" and o.overheadtank_id="+Integer.parseInt(data)
//                                                 +" group by kp.key_person_name";

                                         String query7="select kp.mobile_no1,kp.key_person_name,ohtkp.overheadtank_keyperson_id "
                                                       +" from error_log el,type_of_error toe,overheadtank_keyperson ohtkp,key_person kp,overheadtank o "
                                                       +" where ohtkp.key_person_id=kp.key_person_id "
                                                       +" and ohtkp.overheadtank_id="+Integer.parseInt(data)
                                                       +" and ohtkp.node_id="+node_id
                                                       +" group by kp.key_person_name ";


                                        try {
                                            ResultSet rs5 = connection.prepareStatement(query7).executeQuery();
                                            while (rs5.next()) {
//                                                int node_id=getNode_id(error_log_id);//call getNode_id()
                                                String mobile_no = rs5.getString("mobile_no1");
                                                String key_person_name=rs5.getString("key_person_name");
                                                int overheadtank_keyperson_id = rs5.getInt("overheadtank_keyperson_id");
                                                sendSmsToAssignedFor(mobile_no, message+"--"+key_person_name);
                                                String query6 = "insert into error_log_msg (overheadtank_key_person_id,error_log_id,date_time,received,node_id) values(?,?,?,?,?)";
                                                PreparedStatement ps6 = (PreparedStatement) connection.prepareStatement(query6);
                                                ps6.setInt(1, overheadtank_keyperson_id);
                                                ps6.setInt(2, error_log_id);
                                                ps6.setString(3, df1.format(dt));
                                                ps6.setString(4, "Send");
                                                ps6.setInt(5,node_id);
                                                ps6.executeUpdate();
                                            }
                                        } catch (Exception e) {
                                            System.out.println(e);
                                        }
                                    }
                                }
                            }
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
    public int getNode_id(int error_log_id){
        int error_log=0;
        String query="select node_id from type_of_error toe,error_log el "
                +" where el.type_of_error_id=toe.type_of_error_id "
                +" and el.error_log_id="+error_log_id;
        try{
        PreparedStatement ps1 = (PreparedStatement) connection.prepareStatement(query);
        ResultSet rs = ps1.executeQuery();
        while(rs.next()){
         error_log=rs.getInt("node_id");

        }
        }catch(Exception e){
            System.out.println(e);
        }

        return error_log;
    }

    public int checkErrorLogEntry(int distribution_id) {
        int id = 0;
        String query = " select error_log_id  from error_log where distribution_id=" + distribution_id;
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return id;
    }

    public String sendSmsToAssignedFor(String numberStr1, String messageStr1) {
        String result = "";
        try {
            String host_url = "http://login.smsgatewayhub.com/api/mt/SendSMS?";//"http://api.mVaayoo.com/mvaayooapi/MessageCompose?";
            String tempMessage = messageStr1;
            String sender_id = java.net.URLEncoder.encode("TEST SMS", "UTF-8");         // e.g. "TEST+SMS"
            System.out.println("messageStr1 is = " + messageStr1);
            messageStr1 = java.net.URLEncoder.encode(messageStr1, "UTF-8");
            String queryString = "APIKey=WIOg7OdIzkmYTrqTsw262w&senderid=JPSOFT&channel=2&DCS=8&flashsms=0&number=" + numberStr1 + "&text=" + messageStr1 + "&route=";
            String url = host_url + queryString;
            result = callURL(url);
            System.out.println("SMS URL: " + url);
        } catch (Exception e) {
            result = e.toString();
            System.out.println("SMSModel sendSMS() Error: " + e);
        }
        return result;
    }

    private String callURL(String strURL) {
        String status = "";
        try {
            java.net.URL obj = new java.net.URL(strURL);
            HttpURLConnection httpReq = (HttpURLConnection) obj.openConnection();
            httpReq.setDoOutput(true);
            httpReq.setInstanceFollowRedirects(true);
            httpReq.setRequestMethod("GET");
            status = httpReq.getResponseMessage();
        } catch (MalformedURLException me) {
            status = me.toString();
        } catch (IOException ioe) {
            status = ioe.toString();
        } catch (Exception e) {
            status = e.toString();
        }
        return status;
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void setConnection(Connection connection) {
        ScheduleModel.connection = connection;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println(" closeConnection() Error: " + e);
        }
    }
}
