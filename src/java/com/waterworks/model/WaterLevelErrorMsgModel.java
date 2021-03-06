/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.waterworks.model;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.sql.Connection;
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
 * @author Com7_2
 */
public class WaterLevelErrorMsgModel extends TimerTask{
private ServletContext ctx;
    private static Connection connection;

    public static Connection getConnection() {
        return connection;
    }

    public static void setConnection(Connection connection) {
        //ErrorMsgSchedulerModel.connection = connection;
    }

    public void setCtx(ServletContext ctx) {
        this.ctx = ctx;
    }
    Date dt = new Date();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String cut_dt = df.format(dt);
    String cut_dt1 = df1.format(dt);

    public void run() {
        try {
            System.out.println("run method is running");
            List list = getOverheadtankId();
            checkTimeDifference(list);
        } catch (Exception ex) {
            System.out.println(" run() Error: " + ex);
        }
    }

    public List getOverheadtankId() {
        List list = new ArrayList();
        String query1 = " select ohkp.overheadtank_id,ohkp.key_person_id "
                + " from overheadtank_keyperson ohkp,error_log_msg elm,error_log el "
                + "  where el.error_log_id = elm.error_log_id "
                + " and elm.overheadtank_key_person_id=ohkp.overheadtank_keyperson_id "
                + " AND IF('" + cut_dt + "'='', el.created_date LIKE '%%',el.created_date >='" + cut_dt + "') ";
        try {
            ResultSet rs1 = connection.prepareStatement(query1).executeQuery();
            while (rs1.next()) {
                String overheadtank_id = rs1.getString("overheadtank_id");
                list.add(overheadtank_id);
            }
        } catch (Exception e) {
            System.out.println(" ErrorMsgSchedulerMOdel getOverheadtankId() "+e);;
        }
        return list;
    }

    public void checkTimeDifference(List list) {
        Iterator<String> iter = list.iterator();
        while (iter.hasNext()) {
            String query = "";
            String data = iter.next();

            query = "select elm.created_date "
                    + " from error_log_msg elm,error_log el,overheadtank oht "
                    + " where elm.error_log_id=el.error_log_id "
                    + " and el.overheadtank_id= " + data
                    + " and elm.received !='Received' "
                    + " AND IF('" + cut_dt + "'='', elm.created_date LIKE '%%',elm.created_date >='" + cut_dt + "') "
                    + " group by created_date ";
            try {
                ResultSet rs1 = connection.prepareStatement(query).executeQuery();
                while (rs1.next()) {
                    String created_date = rs1.getString("created_date");
                    String query2 = "";
                    query2 = "SELECT TIMESTAMPDIFF(MINUTE,'" + created_date + "','" + cut_dt1 + "' ) ";
                    try {
                        ResultSet rs2 = connection.prepareStatement(query2).executeQuery();
                        if (rs2.next()) {
                            int time_diff = rs2.getInt(1);
                            if (time_diff > 30) {
                                String mobile_no = " ";
                                String query3 = "select mobile_no1 from key_person kp,overheadtank oh,overheadtank_keyperson ohkp "
                                        + " where ohkp.overheadtank_id= "+data
                                        + " and ohkp.key_person_id=kp.key_person_id "
                                        + " and designation_id=4 "
                                        + " group by mobile_no1 ";
                                try{
                                    ResultSet rs3 = connection.prepareStatement(query3).executeQuery();
                                    while(rs3.next()){
                                        mobile_no=rs3.getString("mobile_no1");
                                    }
                                }catch(Exception e){System.out.println(" ErrorMsgSchedulerMOdel checkTimeDifference() "+e);}
                                String message = "not done";
                                sendSmsToAssignedFor(mobile_no, message);
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(" ErrorMsgSchedulerMOdel checkTimeDifference() "+e);
                    }
                }
            } catch (Exception e) {
                System.out.println(" ErrorMsgSchedulerMOdel checkTimeDifference() "+e);
            }
        }
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

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println(" closeConnection() Error: " + e);
        }
    }
}
