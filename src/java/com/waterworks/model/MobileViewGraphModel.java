/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.waterworks.model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Administrator
 */
public class MobileViewGraphModel {
    
        String waterlvlprev="";
    String waterlvlnext="";
    private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_username;
    private String db_password;
    String tempoid;
static final long Fifteen_MINUTE_IN_MILLIS=900000;
//    public String getSearchDate() {
//        return searchDate;
//    }
//
//    public void setSearchDate(String searchDate) {
//        this.searchDate = searchDate;
//    }



    Date dt = new Date();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String cut_dt = df.format(dt);     

 Date dtnew = new Date();
    SimpleDateFormat dfnew = new SimpleDateFormat("yy/MM/dd");
    SimpleDateFormat df1new = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
    String cut_dtnew = dfnew.format(dtnew);


public JSONArray  getAllDateTime(int ohlevel_id,String searchDate,String did) throws ParseException{
    //int overheadTank_id=getOverheadTankId(ohlevel_id);
    String status_id = getAllStatusId(did);
     JSONObject obj = new JSONObject();
        JSONArray arrayObj = new JSONArray();
        String data="";
         
   
        List list = new ArrayList();
        if(searchDate != null && !"".equals(searchDate) ){
                searchDate=searchDate.replace("2021", "21");
       searchDate=searchDate.replace("-", "/");
            cut_dtnew=searchDate;
        }
        String query="select date_time from water_data o "
                +"  where o.date_time LIKE '"+cut_dtnew+"%' and device_status_id in("+status_id+") order by o.date_time ";
              
//        String query="select date_time from smart_meter_survey.ohlevel o "
//                +"  where date_time LIKE '"+cut_dt+"%' "
//                +" and o.overheadtank_id="+30;
//        String query="select date_time from smart_meter_survey.ohlevel o "
//                +" where o.overheadtank_id="+overheadTank_id;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/smart_meter_survey","root","root");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
               //General general = new General();
                String dateTime=rs.getString("date_time");
                String dateTime1[]=dateTime.split(" ");
                String date1[]=dateTime1[0].split("/");
                String date2=("20").concat(date1[0])+","+date1[1]+","+date1[2]+",";
                String time1[]=dateTime1[1].split(":");
             //   System.out.println("hello----------------------------------------------"+time1[2]);
                String t1[]=time1[2].split("-");
                String time2=time1[0]+","+time1[1];
                String dateTime2=date2+time2;
          //       System.out.println("hello----------------------------------------------"+dateTime2);
               JSONObject jsonObj = new JSONObject();
               jsonObj.put("date_time",dateTime2);
// System.out.println("jsonObj----------------------------------------------"+jsonObj);
               arrayObj.add(jsonObj);
               
               
//              SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//Date date = sdf.parse(dateTime);
//Calendar cal = Calendar.getInstance();
//cal.setTime(date); 
//long date4 = cal.getTimeInMillis();
//
//long final_date = date4+Fifteen_MINUTE_IN_MILLIS;
//        
//         Date revdate = new Date(final_date);
//    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//    System.out.println(formatter.format(revdate)); 
//  cut_dt = formatter.format(revdate); 
  
  
  
            }
            con.close();
        }catch(Exception e){
            System.out.println(e);
        }

       return arrayObj;    
    }
public JSONArray  getAllDateTime1(int ohlevel_id,String searchDate,String did) throws ParseException{
    //int overheadTank_id=getOverheadTankId(ohlevel_id);
    String status_id = getAllStatusId(did);
     JSONObject obj = new JSONObject();
        JSONArray arrayObj = new JSONArray();
        String data="";
         
   
        List list = new ArrayList();
        if(searchDate != null && !"".equals(searchDate) ){
                searchDate=searchDate.replace("2021", "21");
       searchDate=searchDate.replace("-", "/");
            cut_dtnew=searchDate;
        }
        String query="select date_time from temp_water_data o "
                +"  where o.date_time LIKE '"+cut_dtnew+"%' and device_status_id in("+status_id+") order by o.date_time ";
              
//        String query="select date_time from smart_meter_survey.ohlevel o "
//                +"  where date_time LIKE '"+cut_dt+"%' "
//                +" and o.overheadtank_id="+30;
//        String query="select date_time from smart_meter_survey.ohlevel o "
//                +" where o.overheadtank_id="+overheadTank_id;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/smart_meter_survey","root","root");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
               //General general = new General();
                String dateTime=rs.getString("date_time");
                String dateTime1[]=dateTime.split(" ");
                String date1[]=dateTime1[0].split("/");
                String date2=("20").concat(date1[0])+","+date1[1]+","+date1[2]+",";
                String time1[]=dateTime1[1].split(":");
             //   System.out.println("hello----------------------------------------------"+time1[2]);
                String t1[]=time1[2].split("-");
                String time2=time1[0]+","+time1[1];
                String dateTime2=date2+time2;
          //       System.out.println("hello----------------------------------------------"+dateTime2);
               JSONObject jsonObj = new JSONObject();
               jsonObj.put("date_time",dateTime2);
// System.out.println("jsonObj----------------------------------------------"+jsonObj);
               arrayObj.add(jsonObj);
               
               
//              SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//Date date = sdf.parse(dateTime);
//Calendar cal = Calendar.getInstance();
//cal.setTime(date); 
//long date4 = cal.getTimeInMillis();
//
//long final_date = date4+Fifteen_MINUTE_IN_MILLIS;
//        
//         Date revdate = new Date(final_date);
//    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//    System.out.println(formatter.format(revdate)); 
//  cut_dt = formatter.format(revdate); 
  
  
  
            }
            con.close();
        }catch(Exception e){
            System.out.println(e);
        }

       return arrayObj;    
    }
public int getStatusId( String did) {
        int vehicle_id = 0;
        String query = "select device_status_id from device_status where active='Y' and device_id='"+did+"'";
        try {
           Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mqtt_server","root","root");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                vehicle_id = rs.getInt("device_status_id");
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return vehicle_id;
    }
public String getAllStatusId( String did) {
        int vehicle_id = 0;
         String joined ="";
         //System.out.println("did---"+did);
          List<String> list = new ArrayList<String>();
        String query = "select device_status_id from device_status where device_id='"+did+"'";
       // System.out.println("query------"+query);
        try {
            
           Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mqtt_server","root","root");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                vehicle_id = rs.getInt("device_status_id");
                 list.add(String.valueOf(vehicle_id));
            }
             joined = String. join(",", list);
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return joined;
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
        System.out.println("query++++++++++++++++++++"+query);
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
public JSONArray  getAllOhLevel(int ohlevel_id,String searchDate,String did){
 
 
 
      String type1=getOverheadTankType(did);
        int a=getOverHeadTankHeight(did,type1); 
  String status_id = getAllStatusId(did);
     JSONObject obj = new JSONObject();
        JSONArray arrayObj = new JSONArray();      
        String data="";
        List list = new ArrayList();
    
        if(searchDate != null && !"".equals(searchDate) ){
               searchDate=searchDate.replace("2021", "21");
       searchDate=searchDate.replace("-", "/");
       cut_dtnew=searchDate;
        }
        String query="select water_level from water_data o "
                +"  where date_time LIKE '"+cut_dtnew+"%' and device_status_id in("+status_id+") order by o.date_time";
                 
       // System.out.println("query-------"+query);
//        String query="select remark from smart_meter_survey.ohlevel o "
//                +"  where date_time LIKE '"+cut_dt+"%' "
//                 +" and o.overheadtank_id="+30;
//        String query="select remark from smart_meter_survey.ohlevel o "
//                 +" where o.overheadtank_id="+overheadTank_id;
        try{
             Class.forName("com.mysql.jdbc.Driver");      
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/smart_meter_survey","root","root");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
               //General general = new General();
                String ohlevel=rs.getString("water_level");     
//                int a=Integer.parseInt(ohlevel)/10;
//                ohlevel=String.valueOf(a);
    int b=0;
        if(Integer.parseInt(ohlevel)>a){
          b= Integer.parseInt(ohlevel)-a;
        }else{
      b=a-Integer.parseInt(ohlevel);
        }
        
//      System.out.println("waterlvlnext--------"+waterlvlnext);
//        System.out.println("a -----------"+a);
                     ohlevel=Integer.toString(b);
//                     int diff=0;
//                        if(!"".equals(waterlvlnext)){
//                            if(Integer.parseInt(waterlvlnext) >=Integer.parseInt(ohlevel)){
//                                diff=Integer.parseInt(waterlvlnext) -Integer.parseInt(ohlevel);
//                            }else{
//                           diff= Integer.parseInt(ohlevel) -Integer.parseInt(waterlvlnext);
//                            }
//                        
//                        if(diff<=500){
//                         waterlvlnext=ohlevel;
//                        }else{
//                        ohlevel=waterlvlnext;
//                        }
//                        }else{
//                        waterlvlnext=ohlevel;
//                        }
						
	  int a1=Integer.parseInt(ohlevel)/10;
                 ohlevel=String.valueOf(a1);			
             
                 JSONObject jsonObj = new JSONObject();
               jsonObj.put("remark",ohlevel);
              //  System.out.println("-----------------------------"+ohlevel);
               arrayObj.add(jsonObj);
            }
            con.close();
        }catch(Exception e){
            System.out.println(e);
        }
           
       return arrayObj;
    }

public JSONArray  getAllOhLevel1(int ohlevel_id,String searchDate,String did){

    System.out.println("all oh level 1");
 
      String type1=getOverheadTankType(did);
        int a=getOverHeadTankHeight(did,type1); 
  String status_id = getAllStatusId(did);
     JSONObject obj = new JSONObject();
        JSONArray arrayObj = new JSONArray();      
        String data="";
        List list = new ArrayList();
    
         if(searchDate != null && !"".equals(searchDate) ){
               searchDate=searchDate.replace("2021", "21");
       searchDate=searchDate.replace("-", "/");
       cut_dtnew=searchDate;
        }
         
        String query="select water_level from temp_water_data o "
                +"  where date_time LIKE '"+cut_dtnew+"%' and device_status_id in("+status_id+") order by o.date_time";
        
        
                                        System.out.println("query---"+query);

   
        
//        String query="select remark from smart_meter_survey.ohlevel o "
//                +"  where date_time LIKE '"+cut_dt+"%' "
//                 +" and o.overheadtank_id="+30;
//        String query="select remark from smart_meter_survey.ohlevel o "
//                 +" where o.overheadtank_id="+overheadTank_id;
        try{
             Class.forName("com.mysql.jdbc.Driver");      
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/smart_meter_survey","root","root");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
               //General general = new General();
                String ohlevel=rs.getString("water_level");     
//                int a=Integer.parseInt(ohlevel)/10;
//                ohlevel=String.valueOf(a);
    int b=0;
        if(Integer.parseInt(ohlevel)>a){
          b= Integer.parseInt(ohlevel)-a;
        }else{
      b=a-Integer.parseInt(ohlevel);
        }
    
//      System.out.println("waterlvlnext--------"+waterlvlnext);
//        System.out.println("a -----------"+a);
                     ohlevel=Integer.toString(b);
//                     int diff=0;
//                        if(!"".equals(waterlvlnext)){
//                            if(Integer.parseInt(waterlvlnext) >=Integer.parseInt(ohlevel)){
//                                diff=Integer.parseInt(waterlvlnext) -Integer.parseInt(ohlevel);
//                            }else{
//                           diff= Integer.parseInt(ohlevel) -Integer.parseInt(waterlvlnext);
//                            }
//                        
//                        if(diff<=500){
//                         waterlvlnext=ohlevel;
//                        }else{
//                        ohlevel=waterlvlnext;
//                        }
//                        }else{
//                        waterlvlnext=ohlevel;
//                        }
						
	  int a1=Integer.parseInt(ohlevel)/10;
                 ohlevel=String.valueOf(a1);			
             
                 JSONObject jsonObj = new JSONObject();
               jsonObj.put("remark",ohlevel);
                
               arrayObj.add(jsonObj);
            }
            con.close();
        }catch(Exception e){
            System.out.println(e);
        }
           
       return arrayObj;
    }


 

public int getOverheadTankId(int ohlevel_id){
    int overheadTank_id=0;
    String query="select overheadtank_id from smart_meter_survey.ohlevel o "
                +" where o.ohlevel_id="+ohlevel_id;

    try{
         ResultSet rs = connection.prepareStatement(query).executeQuery();
         while(rs.next()){
            overheadTank_id=rs.getInt("overheadtank_id");
        }
    }catch(Exception e){
        System.out.println(e);
    }
    return overheadTank_id;
}


public int getSumpWell_Id(int overheadTank_id){
    int sumpwell_id=0;
    String query="select overheadtank_id1 "
                  +" from sumpwell_overheadtank sw_oht "
                  +" where overheadtank_id2="+overheadTank_id
                  +" and sw_oht.active='Y' ";

    try{
         ResultSet rs = connection.prepareStatement(query).executeQuery();
         while(rs.next()){
            sumpwell_id=rs.getInt("overheadtank_id1");
        }
    }catch(Exception e){
        System.out.println(e);
    }
    return sumpwell_id;
}

public int getJunction_Id(int sump_well_id,int overheadTank_id){
    int junction_id=0;
    String query="select sw_oht.junction_id "
                 +" from smart_meter_survey.sumpwell_overheadtank sw_oht "
                 +" where sw_oht.overheadtank_id1="+sump_well_id
                 +" and  sw_oht.overheadtank_id2="+overheadTank_id
                 +" and sw_oht.active='Y' ";

    try{
         ResultSet rs = connection.prepareStatement(query).executeQuery();
         while(rs.next()){
            junction_id=rs.getInt("junction_id");
        }
    }catch(Exception e){
        System.out.println(e);
    }
    return junction_id;
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

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }


    
}
