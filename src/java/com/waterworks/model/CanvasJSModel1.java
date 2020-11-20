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
public class CanvasJSModel1 {
    
    
    private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_username;
    private String db_password;
    
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




public JSONArray  getAllDateTime(int ohlevel_id,String searchDate) throws ParseException{
    //int overheadTank_id=getOverheadTankId(ohlevel_id);
     JSONObject obj = new JSONObject();
        JSONArray arrayObj = new JSONArray();
        String data="";
       
        
        
        List list = new ArrayList();
        if(searchDate != null){
            cut_dt=searchDate;
        }
        String query="select created_date from water_data o "
                +"  where o.created_date LIKE '"+cut_dt+"%' order by o.water_data_id ";
              
//        String query="select date_time from smart_meter_survey.ohlevel o "
//                +"  where date_time LIKE '"+cut_dt+"%' "
//                +" and o.overheadtank_id="+30;
//        String query="select date_time from smart_meter_survey.ohlevel o "
//                +" where o.overheadtank_id="+overheadTank_id;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mqtt_server","root","root");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
               //General general = new General();
                String dateTime=rs.getString("created_date");
                String dateTime1[]=dateTime.split(" ");
                String date1[]=dateTime1[0].split("-");
                String date2=date1[0]+","+date1[1]+","+date1[2]+",";
                String time1[]=dateTime1[1].split(":");
                System.out.println(time1[2]);
                String t1[]=time1[2].split("-");
                String time2=time1[0]+","+time1[1];
                String dateTime2=date2+time2;
               JSONObject jsonObj = new JSONObject();
               jsonObj.put("date_time",dateTime2);

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

public JSONArray  getAllOhLevel(int ohlevel_id,String searchDate){
  //  int overheadTank_id=getOverheadTankId(ohlevel_id);
     JSONObject obj = new JSONObject();
        JSONArray arrayObj = new JSONArray();
        String data="";
        List list = new ArrayList();
        if(searchDate != null){
            cut_dt=searchDate;
        }
        String query="select water_level from water_data o "
                +"  where created_date LIKE '"+cut_dt+"%' order by water_data_id ";
                 
//        String query="select remark from smart_meter_survey.ohlevel o "
//                +"  where date_time LIKE '"+cut_dt+"%' "
//                 +" and o.overheadtank_id="+30;
//        String query="select remark from smart_meter_survey.ohlevel o "
//                 +" where o.overheadtank_id="+overheadTank_id;
        try{
             Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mqtt_server","root","root");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
               //General general = new General();
                String ohlevel=rs.getString("water_level");
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


///////////////////////////////////////////////////////
public JSONArray  getAllSumpwellDateTime(int ohlevel_id,String searchDate){
    int overheadTank_id=getOverheadTankId(ohlevel_id);
    int sump_well_id=getSumpWell_Id(overheadTank_id);
     JSONObject obj = new JSONObject();
        JSONArray arrayObj = new JSONArray();
        String data="";
        List list = new ArrayList();
        if(searchDate != null){
            cut_dt=searchDate;
        }
        String query="select created_date from smart_meter_survey.ohlevel o "
                +"  where created_date LIKE '"+cut_dt+"%' ";
               

//        String query="select date_time from smart_meter_survey.ohlevel o "
//                +"  where date_time LIKE '"+cut_dt+"%' "
//                +" and o.overheadtank_id="+15;


//        String query="select date_time from smart_meter_survey.ohlevel o "
//                +" where o.overheadtank_id="+overheadTank_id;
        try{
           Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mqtt_server","root","root");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
               //General general = new General();
                String dateTime=rs.getString("created_date");
                String dateTime1[]=dateTime.split(" ");
                String date1[]=dateTime1[0].split("-");
                String date2=date1[0]+","+date1[1]+","+date1[2]+",";
                String time1[]=dateTime1[1].split(":");
                System.out.println(time1[2]);
                String t1[]=time1[2].split("-");
                String time2=time1[0]+","+time1[1];
                String dateTime2=date2+time2;
               JSONObject jsonObj = new JSONObject();
               jsonObj.put("date_time",dateTime2);

               arrayObj.add(jsonObj);
            }
             con.close();
        }catch(Exception e){
            System.out.println(e);
        }

       return arrayObj;
    }

public JSONArray  getAllSumpwellOhLevel(int ohlevel_id,String searchDate){
    int overheadTank_id=getOverheadTankId(ohlevel_id);
    int sump_well_id=getSumpWell_Id(overheadTank_id);
     JSONObject obj = new JSONObject();
        JSONArray arrayObj = new JSONArray();
        String data="";
        List list = new ArrayList();
        if(searchDate != null){
            cut_dt=searchDate;
        }
        String query="select remark from smart_meter_survey.ohlevel o "
                +"  where date_time LIKE '"+cut_dt+"%' "
                 +" and o.overheadtank_id="+sump_well_id;
//        String query="select remark from smart_meter_survey.ohlevel o "
//                +"  where date_time LIKE '"+cut_dt+"%' "
//                 +" and o.overheadtank_id="+15;


//        String query="select remark from smart_meter_survey.ohlevel o "
//                 +" where o.overheadtank_id="+overheadTank_id;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mqtt_server","root","root");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
               //General general = new General();
                String ohlevel=rs.getString("remark");
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
/////////////////////////////energyMeter/////////////
public JSONArray  getAllEnergyMeterDateTime(int ohlevel_id,String searchDate){
    int overheadTank_id=getOverheadTankId(ohlevel_id);
     int sump_well_id=getSumpWell_Id(overheadTank_id);
     int junction_id=getJunction_Id(sump_well_id,overheadTank_id);
     JSONObject obj = new JSONObject();
        JSONArray arrayObj = new JSONArray();
        String data="";
        List list = new ArrayList();
        if(searchDate != null){
            cut_dt=searchDate;
        }
//        String query="select date_time from smart_meter_survey.ohlevel o "
//                +"  where date_time LIKE '"+cut_dt+"%' "
//                +" and o.overheadtank_id="+overheadTank_id;

//        String query="select timestamp from meter_survey.meter_readings mr "
//                +" where timestamp LIKE '"+cut_dt+"%' ";
//               // +" and o.overheadtank_id="+34;
        String query="select created_date from energy_data "
                      +" where created_date LIKE '"+cut_dt+"%' order by energy_data_id ";
                     
               // +" and o.overheadtank_id="+34;


//        String query="select date_time from smart_meter_survey.ohlevel o "
//                +" where o.overheadtank_id="+overheadTank_id;
        try{
           Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mqtt_server","root","root");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
               //General general = new General();
                String dateTime=rs.getString("created_date");
                String dateTime1[]=dateTime.split(" ");
                String date1[]=dateTime1[0].split("-");
                String date2=date1[0]+","+date1[1]+","+date1[2]+",";
                String time1[]=dateTime1[1].split(":");
                System.out.println(time1[2]);
                String t1[]=time1[2].split("-");
                String time2=time1[0]+","+time1[1];
                String dateTime2=date2+time2;
               JSONObject jsonObj = new JSONObject();
               jsonObj.put("date_time",dateTime2);

               arrayObj.add(jsonObj);
            }
             con.close();
        }catch(Exception e){
            System.out.println(e);
        }

       return arrayObj;
    }

public JSONArray  getAllEnergyMeterData(int ohlevel_id,String searchDate){
    int overheadTank_id=getOverheadTankId(ohlevel_id);
    int sump_well_id=getSumpWell_Id(overheadTank_id);
     int junction_id=getJunction_Id(sump_well_id,overheadTank_id);
     JSONObject obj = new JSONObject();
        JSONArray arrayObj = new JSONArray();
        String data="";
        List list = new ArrayList();
        if(searchDate != null){
            cut_dt=searchDate;
        }
//        String query="select remark from smart_meter_survey.ohlevel o "
//                +"  where date_time LIKE '"+cut_dt+"%' "
//                 +" and o.overheadtank_id="+overheadTank_id;
        String query="select total_active_power from energy_data "
                +"  where created_date LIKE '"+cut_dt+"%' order by energy_data_id ";
                                //+" and o.overheadtank_id="+34;


//        String query="select remark from smart_meter_survey.ohlevel o "
//                 +" where o.overheadtank_id="+overheadTank_id;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mqtt_server","root","root");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
               //General general = new General();
                String ohlevel=rs.getString("total_active_power");
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
//////////////endEnergyMeter////////////////


////////////////////////////////////////////////////////
public JSONArray  getNewDateTime(int ohlevel_id,String searchDate){
    int overheadTank_id=getOverheadTankId(ohlevel_id);
     JSONObject obj = new JSONObject();
        JSONArray arrayObj = new JSONArray();
        String data="";
        List list = new ArrayList();
        if(searchDate != null){
            cut_dt=searchDate;
        }
        String query="SELECT date_time FROM smart_meter_survey.ohlevel o WHERE ohlevel_id = (SELECT MAX(ohlevel_id) FROM smart_meter_survey.ohlevel) "
                      +" and o.date_time LIKE '"+cut_dt+"%' "
                      +" and o.overheadtank_id="+overheadTank_id;
//        String query="SELECT date_time FROM smart_meter_survey.ohlevel o WHERE ohlevel_id = (SELECT MAX(ohlevel_id) FROM smart_meter_survey.ohlevel) "
//
//                      +" and o.overheadtank_id="+overheadTank_id;
        try{
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while(rs.next()){

                String dateTime=rs.getString("date_time");
                String dateTime1[]=dateTime.split(" ");
                String date1[]=dateTime1[0].split("-");
                String date2=date1[0]+","+date1[1]+","+date1[2]+",";
                String time1[]=dateTime1[1].split(":");
                System.out.println(time1[2]);
                String t1[]=time1[2].split("-");
                String time2=time1[0]+","+time1[1];
                String dateTime2=date2+time2;
               JSONObject jsonObj = new JSONObject();
               jsonObj.put("date_time",dateTime2);

               arrayObj.add(jsonObj);
            }
        }catch(Exception e){
            System.out.println(e);
        }

       return arrayObj;
    }

public JSONArray  getNewOhLevel(int ohlevel_id,String searchDate){
    int overheadTank_id=getOverheadTankId(ohlevel_id);
     JSONObject obj = new JSONObject();
        JSONArray arrayObj = new JSONArray();
        String data="";
        List list = new ArrayList();
        if(searchDate != null){
            cut_dt=searchDate;
        }
        String query="SELECT remark FROM smart_meter_survey.ohlevel o WHERE ohlevel_id = (SELECT MAX(ohlevel_id) FROM smart_meter_survey.ohlevel) "
                     +" and o.date_time LIKE '"+cut_dt+"%' "
                     +" and o.overheadtank_id="+overheadTank_id;
//        String query="SELECT remark FROM smart_meter_survey.ohlevel o WHERE ohlevel_id = (SELECT MAX(ohlevel_id) FROM smart_meter_survey.ohlevel) "
//
//                     +" and o.overheadtank_id="+overheadTank_id;
        try{
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while(rs.next()){
               //General general = new General();
                String ohlevel=rs.getString("remark");
               JSONObject jsonObj = new JSONObject();
               jsonObj.put("remark",ohlevel);

               arrayObj.add(jsonObj);
            }
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
