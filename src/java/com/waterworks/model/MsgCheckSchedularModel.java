/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.waterworks.model;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;
import javax.servlet.ServletContext;

/**
 *
 * @author Shobha
 */
public class MsgCheckSchedularModel extends TimerTask{

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

            checkSentErrorMsg();
            
        } catch (Exception ex) {
            System.out.println(" run() Error: " + ex);
        }
    }

public void checkSentErrorMsg(){

     int error_log_msg_id=0;
     int node_id=0;
     int overheadtank_id=0;
//        String query="select elm.error_log_msg_id,elm.node_id "
//                     +" from error_log_msg elm "
//                     +" where received !='DONE' AND active='Y' ";
        String query=" select elm.error_log_msg_id,elm.node_id,el.overheadtank_id "
                      +" from error_log_msg elm,error_log el,overheadtank_keyperson ohtkp "
                       +" where elm.error_log_id=el.error_log_id "
                     +" and elm.received !='DONE' AND elm.active='Y' "
                      +" group by el.overheadtank_id ";
        try{
        PreparedStatement ps1 = (PreparedStatement) connection.prepareStatement(query);
        ResultSet rs = ps1.executeQuery();
        while(rs.next()){
            error_log_msg_id=rs.getInt("error_log_msg_id");
            node_id=rs.getInt("node_id");
            overheadtank_id=rs.getInt("overheadtank_id");
            if(node_id != 1){
            sendMsg(error_log_msg_id,node_id,overheadtank_id);
            }
        }
        }catch(Exception e){
            System.out.println(e);
        }

}
public void sendMsg(int error_log_msg_id,int node_id,int overheadtank_id){

    int superior_node_id=0;
    String mob_no="";
    String name="";
    //get supirior node_id and then send message
    String query="select node_parent_id "
            +" from tree1 where node_id="+node_id;
     try{
        PreparedStatement ps1 = (PreparedStatement) connection.prepareStatement(query);
        ResultSet rs = ps1.executeQuery();
        while(rs.next()){
            superior_node_id=rs.getInt("node_parent_id");

        }
        }catch(Exception e){
            System.out.println(e);
        }

//    String query1="select kp.mobile_no1,kp.key_person_name "
//            +" from key_person kp,overheadtank_keyperson otkp,node1 n "
//            +" where n.node_id=otkp.node_id "
//            +" and otkp.key_person_id=kp.key_person_id "
//            +" and n.node_id="+superior_node_id;
    String query1="select kp.mobile_no1,kp.key_person_name "
            +" from error_log el,type_of_error toe,overheadtank_keyperson ohtkp,key_person kp,overheadtank o "
             +" where ohtkp.key_person_id=kp.key_person_id "
              +" and ohtkp.overheadtank_id="+overheadtank_id
            +" and ohtkp.node_id="+superior_node_id
            +" group by kp.key_person_name";

     try{
        PreparedStatement ps1 = (PreparedStatement) connection.prepareStatement(query1);
        ResultSet rs = ps1.executeQuery();
        while(rs.next()){
            mob_no=rs.getString("mobile_no1");
            name=rs.getString("key_person_name");

        }
        }catch(Exception e){
            System.out.println(e);
        }

     //mob_no="880057362";
    String message=name;

    sendSmsToAssignedFor(mob_no,message);

    updateErrorLogMsg(error_log_msg_id,superior_node_id);

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


public void updateErrorLogMsg(int error_log_msg_id,int superior_node_id){
    int error_log_id=0;
    int overheadtank_key_person_id=0;
    String date_time="";
    String received="";

    String query="select error_log_id,overheadtank_key_person_id,date_time,received"
            + " from error_log_msg "
            +" where error_log_msg_id="+error_log_msg_id;

     try{
        PreparedStatement ps1 = (PreparedStatement) connection.prepareStatement(query);
        ResultSet rs = ps1.executeQuery();
        while(rs.next()){
          error_log_id=rs.getInt("error_log_id");
          overheadtank_key_person_id=rs.getInt("overheadtank_key_person_id");
          date_time=rs.getString("date_time");
          received=rs.getString("received");

        }
        }catch(Exception e){
            System.out.println(e);
        }




    String query1 = "SELECT max(rev_no) rev_no FROM error_log_msg WHERE error_log_msg_id = "+error_log_msg_id+" && active='Y' ORDER BY rev_no DESC";
      String query2 = " UPDATE error_log_msg SET active=? WHERE error_log_msg_id = ? && rev_no = ? ";
      String query3 = "INSERT INTO error_log_msg (error_log_msg_id,error_log_id,overheadtank_key_person_id,date_time,received,node_id, rev_no, active) VALUES (?,?,?,?,?,?,?,?) ";
      int updateRowsAffected = 0;
      try {
           PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query1);
           ResultSet rs = ps.executeQuery();
           if(rs.next()){
           PreparedStatement pst = (PreparedStatement) connection.prepareStatement(query2);
           pst.setString(1,  "N");
           pst.setInt(2,error_log_msg_id);
           pst.setInt(3, rs.getInt("rev_no"));
           updateRowsAffected = pst.executeUpdate();
             if(updateRowsAffected >= 1){
             int rev = rs.getInt("rev_no")+1;
             PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
             psmt.setInt(1,error_log_msg_id);
             psmt.setInt(2,error_log_id);
             psmt.setInt(3,overheadtank_key_person_id);
             psmt.setString(4,date_time);
             psmt.setString(5, received);
             psmt.setInt(6,superior_node_id);

             psmt.setInt(7, rev);
             psmt.setString(8, "Y");
             int a = psmt.executeUpdate();
             }
           }
          } catch (Exception e)
             {
              System.out.println("CityDAOClass reviseRecord() Error: " + e);
             }

}



    

     public static Connection getConnection() {
        return connection;
    }

    public static void setConnection(Connection connection) {
        MsgCheckSchedularModel.connection = connection;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println(" closeConnection() Error: " + e);
        }
    }
}
