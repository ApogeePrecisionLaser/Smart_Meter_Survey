/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.waterworks.model;



import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.DriverManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
/**
 *
 * @author Com7_2
 */
public class DashboardModel {

    private static Connection connection;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";
   

    public static int getNoOfRows(String vehicleType, String key_person_name) {
        vehicleType = (vehicleType);
        key_person_name =(key_person_name);
        int noOfRows = 0;
        try {
            String query = " SELECT  count(dvm.device_vehicle_mapping_id) "
                    + " from device_vehicle_mapping dvm,vehicle v"
                        + " where  dvm.vehicle_id=v.vehicle_id "
                        + " and v.active='Y' and dvm.active='Y'";
                   

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("No of Rows in Table for search is****....." + noOfRows);
        return noOfRows;
    }

  

//    public boolean insertRecord(VehicleKeyPerson bean) {
//        boolean status = false;
//        String query = "";
//        int rowsAffected = 0;
//        int vehicle_key_person_id = bean.getVehicle_key_person_id();
//        if (vehicle_key_person_id == 0) {
//            query = "insert into vehicle_key_person(vehicle_id,shift_key_person_map_id) values(?,?)";
//        }
//        if (vehicle_key_person_id > 0) {
//            query = " update vehicle_key_person set vehicle_id=?,shift_key_person_map_id=? where vehicle_key_person_id=?";
//        }
//
//        try {
//            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
//            ps.setInt(1, bean.getVehicle_id());
//            ps.setInt(2, bean.getKey_person_id());
//            if (vehicle_key_person_id > 0) {
//                ps.setInt(3, vehicle_key_person_id);
//            }
//            rowsAffected = ps.executeUpdate();
//            if (rowsAffected > 0) {
//                status = true;
//            }
//        } catch (Exception e) {
//            System.out.println("ERROR: " + e);
//        }
//        if (rowsAffected > 0) {
//            message = "Record Inserted successfully......";
//            msgBgColor = COLOR_OK;
//            System.out.println("Inserted");
//        } else {
//            message = "Record Not Inserted Some Error!";
//            msgBgColor = COLOR_ERROR;
//            System.out.println("not Inserted");
//        }
//        return status;
//    }

    
    
      
//    public int insertRecord(devicevehicleBean bean) {
//        String query = "INSERT INTO device_vehicle_mapping(vehicle_no,device_no,vehicle_id,device_id,revision_no,active,remark) VALUES(?,?,?,?,?,?,?) ";
//        int rowsAffected = 0;
//        try {
//            PreparedStatement pstmt = connection.prepareStatement(query);
////            pstmt.setInt(1,(organisation_name.getOrganisation_id()));
//            pstmt.setInt(1, (bean.getVehicle_no()));
//            pstmt.setString(2,(bean.getDevice_no()));
//            pstmt.setInt(3, (bean.getVehicle_id()));
//               pstmt.setInt(4,bean.getDevice_id());
//             pstmt.setInt(5,bean.getRevision_no());
//            pstmt.setString(6,"Y");
//            pstmt.setString(7,bean.getRemark());
//            rowsAffected = pstmt.executeUpdate();
//        } catch (Exception e) {
//            System.out.println("OrganisationNameModel insertRecord() Error: " + e);
//        }
//        if (rowsAffected > 0) {
//            message = "Record saved successfully.";
//            msgBgColor = COLOR_OK;
//        } else {
//            message = "Cannot save the record, some error.";
//            msgBgColor = COLOR_ERROR;
//        }
//        return rowsAffected;
//    }
    
    
    
//    public boolean reviseRecords(VehicleKeyPerson bean) {
//
//        boolean status = false;
//        String query = "";
//        int rowsAffected = 0;
//        int vehicle_key_person_id = bean.getVehicle_key_person_id();
//
//        String query1 = "SELECT max(rev_no) rev_no FROM vehicle_key_person WHERE vehicle_key_person_id = " + vehicle_key_person_id + " && active='Y' ORDER BY rev_no DESC";
//        String query2 = " UPDATE vehicle_key_person SET active=? WHERE vehicle_key_person_id = ? && rev_no = ? ";
//        String query3 = "INSERT INTO vehicle_key_person (vehicle_key_person_id,vehicle_id,shift_key_person_map_id, rev_no, active) VALUES (?,?,?,?,?) ";
//        int updateRowsAffected = 0;
//        try {
//            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query1);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                PreparedStatement pst = (PreparedStatement) connection.prepareStatement(query2);
//                pst.setString(1, "N");
//                pst.setInt(2, vehicle_key_person_id);
//                pst.setInt(3, rs.getInt("rev_no"));
//                updateRowsAffected = pst.executeUpdate();
//                if (updateRowsAffected >= 1) {
//                    int rev = rs.getInt("rev_no") + 1;
//                    PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
//                    psmt.setInt(1, vehicle_key_person_id);
//                    psmt.setInt(2, bean.getVehicle_id());
//                    psmt.setInt(3, bean.getKey_person_id());
//
//                    psmt.setInt(4, rev);
//                    psmt.setString(5, "Y");
//                    int a = psmt.executeUpdate();
//                    if (a > 0) {
//                        status = true;
//                    }
//                }
//            }
//        } catch (Exception e) {
//            System.out.println("CityDAOClass reviseRecord() Error: " + e);
//        }
//        if (updateRowsAffected > 0) {
//            message = "Record Inserted successfully......";
//            msgBgColor = COLOR_OK;
//            System.out.println("Inserted");
//        } else {
//            message = "Record Not Inserted Some Error!";
//            msgBgColor = COLOR_ERROR;
//            System.out.println("not Inserted");
//        }
//
//        return status;
//
//    }

    
//        public int updateRecord(devicevehicleBean bean,int device_vehicle_mapping_id) {
//      int revision=devicevehiclemappingModel.getRevisionno(bean,device_vehicle_mapping_id);
//         int updateRowsAffected = 0;
//           boolean status=false;
//        String query1 = "SELECT max(revision_no) revision_no FROM device_vehicle_mapping WHERE device_vehicle_mapping_id = "+device_vehicle_mapping_id+"  && active=? ";
//        String query2 = "UPDATE device_vehicle_mapping SET active=? WHERE device_vehicle_mapping_id=? and revision_no=?";
//        String query = "INSERT INTO device_vehicle_mapping(device_vehicle_mapping_id,vehicle_no,device_no,vehicle_id,device_id,revision_no,active,remark) VALUES(?,?,?,?,?,?,?,?) ";
//        int rowsAffected = 0;
//        try {
//            PreparedStatement pstmt = connection.prepareStatement(query1);
////           pstmt.setInt(1,organisation_type_id);
//           pstmt.setString(1, "Y");
//           
//           
//           ResultSet rs = pstmt.executeQuery();
//                if(rs.next()){
//                PreparedStatement pstm = connection.prepareStatement(query2);
//               
//                 pstm.setString(1,"n");
//               
//                 pstm.setInt(2,device_vehicle_mapping_id);
//                 pstm.setInt(3,revision);
//                  updateRowsAffected = pstm.executeUpdate();
//             if(updateRowsAffected >= 1){
//                   revision = rs.getInt("revision_no")+1;
//                    PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query);
//                     psmt.setInt(1,(bean.getDevice_vehicle_mapping_id()));
//             psmt.setInt(2, (bean.getVehicle_no()));
//            psmt.setString(3,(bean.getDevice_no()));
//            psmt.setInt(4, (bean.getVehicle_id()));
//             psmt.setInt(5,bean.getDevice_id());
//             psmt.setInt(6,revision);
//            psmt.setString(7,"Y");
//            psmt.setString(8,bean.getRemark());
//                    rowsAffected = psmt.executeUpdate();
//                   if(rowsAffected > 0)
//                   status=true;
//                else 
//                  status=false;
//             }
//                 
//                 
//                }
//        } catch (Exception e) {
//            System.out.println("OrganisationNameModel updateRecord() Error: " + e);
//        }
//        if (rowsAffected > 0) {
//            message = "Record updated successfully.";
//            msgBgColor = COLOR_OK;
//        } else {
//            message = "Cannot update the record, some error.";
//            msgBgColor = COLOR_ERROR;
//        }
//        return rowsAffected;
//    }

    
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
            message = "Something going wrong";
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
            message = "Something going wrong";
          //  messageBGColor = "red";
            return 0;
        }
    }
    public boolean deleteRecord(int device_vehicle_mapping_id) {
        boolean status = false;
        int rowsAffected = 0;
        String query = "DELETE FROM device_vehicle_mapping WHERE device_vehicle_mapping_id = " + device_vehicle_mapping_id;
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setInt(1, device_vehicle_mapping_id);
            rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                status = true;
            } else {
                status = false;
            }
        } catch (Exception e) {
            System.out.println("ERROR: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record Deleted successfully......";
            msgBgColor = COLOR_OK;
            System.out.println("Deleted");
        } else {
            message = "Record Not Deleted Some Error!";
            msgBgColor = COLOR_ERROR;
            System.out.println("not Deleted");
        }
        return status;
    }
    
      public String getFuellevel(int id) {
       String status="";
       String combo="";
       double r = 21.5/100.00;
        double theta=0.0;
        double length=113/100.00;
        String query = "select dd.fuel_level,dd.speed,dd.fuel_intensity,dd.door_status from device_data dd where dd.device_status_id=" + id + " and dd.active='Y'";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3309/mqtt_server","root","root");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                status = rs.getString("fuel_level");
                String speed = rs.getString("speed");
                String fuel_intensity = rs.getString("fuel_intensity");
                String door_status = rs.getString("door_status");
                combo = status+"_"+speed+"_"+fuel_intensity+"_"+door_status;
//                int statusint = Integer.parseInt(status);
////                 if(statusint >= 0 && statusint<=70)
////                 {
////                   status="";
////                   // return status;
////                 }
////                 else
//                 {
//                   double convert = Integer.parseInt(status)/1000.00;
//                
//                
//                
//                double p = (double) (r-convert);
//                // double radians = Math.toRadians(theta); 
//                 theta= 2*Math.acos(p/r);
//            
//            double area = ((theta/360.00000)*3.14159*Math.sqrt(r))-(1/2*r*Math.sin(theta/2)*p);
//            double volume = (area*length);
//            
//            status=Double.toString(volume); 
//          
//                 }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return combo;
    }
    
      
        public String getFuellevellitre(int id) {
      // String status="";
      double r = 215.0;
    //double h = 0.132;
    int percentage=0;
    double p1=143.0;
   String vol_per="";
    double length = 113.0;
  // double h = 131.0;
  double h = 0.0;
   double volume2=0.00;
        double theta=0.0;
     
        String query = "select dd.fuel_level from device_data dd where dd.device_status_id='"+id+"' and dd.active='Y' order by  device_data_id desc limit 1";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3309/mqtt_server","root","root");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
             
               h=rs.getDouble("fuel_level");
                  
    double rh = 2.0 * (r * h);
    double hh = h * h;
    double rr = r * r ;
    double diffRH = r- h;
    double divValue = diffRH/r;
    double cos_value = Math.acos(divValue);
    double BeforeSubValue = rr * cos_value;
    double diffV_2 = rh - hh ;
    double sqrtDiff2 = Math.sqrt(diffV_2);
    double AfterSubValue = diffRH * sqrtDiff2;
    double area = BeforeSubValue - AfterSubValue;
    double volume = (area * length);
   
     //   System.out.println("volume is ..... "+volume);
       
       
       
       
       double degreeValue = 2.0 *  (Math.toDegrees(cos_value));
       double sin_value = Math.sin(degreeValue/2.0);
       double diffRH1 = (r- h)/10.0;
       double befSub = (degreeValue/360.0) * 3.142 * 215 * 215;
       double afterSub = Math.abs((sin_value * 215 * diffRH)/2.0);
      // double afterSub = Math.abs((21.5/2) * (sin_value * diffRH));
       double area2 = befSub - afterSub;
        volume2 = (area2 * 113.0)/100000;
        DecimalFormat df = new DecimalFormat("#.####");      
        volume2 = Double.valueOf(df.format(volume2));
        percentage =((int) (volume2*100/p1));
         vol_per=  volume2+"_"+percentage;
  
      //  System.out.println("volume is ..... "+volume2);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return vol_per;
    }
      
      
      
      
        public static JSONObject getStatus(String fuel_level,String refresh,String fuel_level_litre,String waterlevel,String water_temperature,
                String water_intensity,String datetime,String relay_state,String total_active_power,String Cons_energy_mains,String  active_energy_dg,
                String total_active_energy,String phase_voltage_R,String phase_voltage_Y,String phase_voltage_B,String phase_current_R,String phase_current_Y,
                String phase_current_B,String energy_id) 
    {
        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray(); 
     String id="";
     String id2="";
     String id3="";
     double id4=0;
     String id5="";
     String id6="";
     String id7="";
     String id8="";
     String id9="";
     String id10="";
     String id11="";
     String id12="";
     String id13="";
     String id14="";
     String id15="";
     String id16="";
     String id17="";
     String id18="";
     String id19 = "";
     String id20 = "";
     String id21 = "";
     String id22 = "";
     String id23 = "";
     String id24 = "";
     String id25 = "";
     String id26 = "";
     String id27 = "";
  //   String query = "select dvm.device_status_id from device_status dvm where dvm.device_id=" + device + " and dvm.active='Y'";
 String query = "select dd.created_date,dd.gpss_accuracy,dd.battery_voltage,dd.latitude,dd.longitude,dd.speed,dd.fuel_intensity,dd.door_status from device_data dd where  dd.active='Y' ORDER BY created_date desc LIMIT 1";
// String query = "select  water_level, water_temperature, water_intensity, phase_voltage_R, phase_voltage_Y,phase_voltage_B, phase_current_R, phase_current_Y, phase_current_B,created_date, date_time,relay_state, relay_state_2, magnetic_sensor_value FROM water_data where  active='Y' ORDER BY created_date desc Limit 1";
   
 try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mqtt_server","root","root");
               Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
       rs.next();    // move cursor from BOR to valid record.
         JSONObject json1 = new JSONObject();
            id = fuel_level;
            id2 = rs.getString("created_date");
            id3 = rs.getString("gpss_accuracy");
            id4 = 0;
            id5 = refresh;
            id6 = fuel_level_litre;
            id7 = rs.getString("latitude");
            id8 = rs.getString("longitude");
            id9 = rs.getString("speed");
            id10 = rs.getString("fuel_intensity");
            id11 = rs.getString("door_status");
            id12 = waterlevel;
            id13 = water_temperature;
            id14 = water_intensity;
            id15 = datetime;
            id16 = relay_state;
            id17 =  total_active_power;
            id18 = Cons_energy_mains;
            id19 = active_energy_dg;
            id20 = total_active_energy;
            id21 = phase_voltage_R;
            id22 = phase_voltage_Y;
            id23 = phase_voltage_B;
            id24 = phase_current_R;
            id25 = phase_current_Y;
            id26 = phase_current_B;
            id27 = energy_id;
            
            if(id11.equals("0"))
            {
              id11 = "Closed";
            }
            else{
                id11="Open";
                
            }
             json1.put("id", id);
              json1.put("id2", id2);
                 json1.put("id3", id3);
                    json1.put("id4", id4);
                     json1.put("id5", id5);
                     json1.put("id6", id6);
                     json1.put("id7", id7);
                     json1.put("id8", id8);
                        json1.put("id9", id9);
                        json1.put("id10", id10);
                           json1.put("id11", id11);
                            json1.put("id12", id12);
                             json1.put("id13", id13);
                             json1.put("id14", id14);
                              json1.put("id15", id15);
                               json1.put("id16", id16);
                jsonArray.add(json1);
            
        } catch(Exception e) {
            System.out.println("Error: " + e);
        }
        json.put("data", jsonArray);
        return json;
    }
      
         public static JSONObject getStatus1(String fuel_level,double voltage,String refresh,String fuel_level_litre,String model, String manufacturer,String vehicle,String key_person)
    {
        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray(); 
     String id="";
     String id2="";
     String id3="";
     double id4=0;
     String id5="";
     String id6="";
     String id7="";
     String id8="";
     String id9="";
     String id10="";
     String id11="";
     String id12="";
  //   String query = "select dvm.device_status_id from device_status dvm where dvm.device_id=" + device + " and dvm.active='Y'";
  String query = "select dd.created_date,dd.gpss_accuracy,dd.battery_voltage,dd.latitude,dd.longitude from device_data dd where  dd.active='Y' ORDER BY created_date desc LIMIT 1";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3309/mqtt_server","root","root");
               Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
       rs.next();    // move cursor from BOR to valid record.
         JSONObject json1 = new JSONObject();
            id = fuel_level;
            id2 = rs.getString("created_date");
            id3 = rs.getString("gpss_accuracy");
            id4 = voltage;
            id5 = refresh;
            id6 = fuel_level_litre;
            id7 = rs.getString("latitude");
            id8 = rs.getString("longitude");
            id9 = model;
            id10 = manufacturer;
            id11 = vehicle;
            id12 = key_person;
             json1.put("id", id);
              json1.put("id2", id2);
                 json1.put("id3", id3);
                    json1.put("id4", id4);
                     json1.put("id5", id5);
                     json1.put("id6", id6);
                     json1.put("id7", id7);
                     json1.put("id8", id8);
                       json1.put("id9", id9);
                         json1.put("id10", id10);
                           json1.put("id11", id11);
                             json1.put("id12", id12);
                jsonArray.add(json1);
            
        } catch(Exception e) {
            System.out.println("Error: " + e);
        }
        json.put("data", jsonArray);
        return json;
    }
    
    public String getFuelIndicator(int id) {
       String status="";
        String query = "select dd.fuel_level from device_data dd where dd.device_status_id=" + id + " and dd.active='Y' ";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3309/mqtt_server","root","root");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                status = rs.getString("fuel_level");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return status;
    }

     public String getKeyPerson(String id) {
       String vehicle_id = getVehicleId(id);
       String status="";
        String query = "select kp.key_person_name from key_person kp,vehicle_key_person vkp ,shift_key_person_map skm where skm.shift_key_person_map_id=vkp.shift_key_person_map_id and  vkp.vehicle_id='"+vehicle_id+"' and kp.active='Y' and vkp.active='Y' and skm.active='Y' ";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3309/health_department","root","root");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                status = rs.getString("key_person_name");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return status;
    }
    
      public String getVehicleId( String code) {
       String status="";
        String query = "select v.vehicle_id from vehicle v where v.vehicle_no= '"+ code +"' and v.active='Y' ";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3309/health_department","root","root");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                status = rs.getString("vehicle_id");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return status;
    }
     
      public String getDeviceId(int device_id) {
       String status="";
        String query = "select device_id from device_status  where  device_status_id ='"+device_id+"'";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mqtt_server","root","root");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                status = rs.getString("device_id");
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return status;
    }
     
     
       public String getVehicleno(String id) {
       String status="";
        String query = "select dvm.vehicle_no from device_vehicle_mapping dvm where dvm.device_id='" + id + "' and dvm.active='Y' ";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3309/health_department","root","root");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                status = rs.getString("vehicle_no");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return status;
    }
      
      
      
      
     
     
     
     
      public String getIdleRunningStatus(int id) {
       String status="";
       String speed="";
       int i=0;
       String result_lat="";
       String result_lon="";
       String result = getIdleRunningStatus1(id);
       if(result.equals("_"))
       {
           result="0_0";
       }
        String lattitude= result.split("_")[0];
        String longitude=result.split("_")[1];
        
     String latt ="";
       String lonn="";
       
//       if(lattitude.equals("0") && longitude.equals("0"))
//       {
//       latt="0";
//       lonn="0";
//       }
       
       
       
       
       if(latt=="" && lonn=="")
       {
          latt="";
          lonn="";
       }
       
       
       int lat[] = null;
       int lon[] = null;
        String query = "select dd.latitude,dd.longitude,dd.Speed from device_data dd where dd.device_status_id=" + id + " ORDER BY  device_data_id DESC  LIMIT 1 ";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3309/mqtt_server","root","root");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                
//                lat[i] = rs.getInt("latitude");
//                lon[i] = rs.getInt("longitude");
                   latt = rs.getString("latitude");
                  lonn = rs.getString("longitude");
                  speed = rs.getString("Speed");
//                  if(lattitude.equals(latt)&&longitude.equals(lonn))
//                  {
//                        status="idle";
//                  }
//                  else{
//                      status="running";
//                  }
                     if(Double.parseDouble(speed)<=5)
                     {
                        status="idle";
                     }
                   else
                     {
                       status = "running";
                     }

                 if(lattitude.equals("0")&&longitude.equals("0")&&speed.equals("0"))
                 {
                    status="null";
                 }
             // status = latt+"_"+lonn;
                 
            }
            con.close();
          // result_lat=lattitude
        } catch (Exception e) {
            System.out.println(e);
        }
        return status;
    }
     
    
    
      public String getIdleRunningStatus1(int id) {
       String status="";
       int i=0;
     
     String latt ="";
       String lonn="";
       
        if(latt==null && lonn==null)
       {
          latt="";
          lonn="";
       }
       
       
       
       int lat[] = null;
       int lon[] = null;
        String query = "select dd.latitude,dd.longitude from device_data dd where dd.device_status_id=" + id + " ORDER BY  device_data_id DESC  LIMIT 1";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3309/mqtt_server","root","root");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
//                lat[i] = rs.getInt("latitude");
//                lon[i] = rs.getInt("longitude");
                  latt = rs.getString("latitude");
                  lonn = rs.getString("longitude");
                   if(latt==null && lonn==null)
       {
          latt="";
          lonn="";
       }
                 status = latt+"_"+lonn;
                
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return status;
    }
      
      public String getException(String id)
      {
      String excep = "";
      
       String query = "select r.reference from reference r where r.vehicle_no=" + id + " and r.active='Y'";
       try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3309/health_department","root","root");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                excep = rs.getString("reference");
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return excep;
      
      }
      
      
     public String getVehicleStatus(int id) {
       String status="";
        String query = "select dd.engine_status from device_data dd where dd.device_status_id=" + id + " and dd.active='Y'";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3309/mqtt_server","root","root");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                status = rs.getString("engine_status");
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return status;
    }
     
        public double getVoltage(int id) {
       double status=0;
        String query = "select dd.battery_voltage from device_data dd where dd.device_status_id=" + id + " and dd.active='Y'";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3309/mqtt_server","root","root");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                status = rs.getInt("battery_voltage");
                status = (status)/1000;
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return status;
    }
    
    
        
           public String getWaterLevel(String id) {
               
               int device_status_id = getWaterStatusId(id);
              // int device_status_id = 5449;
       String status="";
       String temp = "";
       String intensity = "";
       String datetime="";
       String relay_status = "";
       int water_id = 0;
       String relay_status1 = "";
       String magnetic_sensor_value="";
       
        String query = "select distinct wd.water_level,wd.water_temperature,wd.water_intensity,wd.date_time,wd.relay_state,wd.relay_state_2,wd.water_data_id,wd.magnetic_sensor_value from water_data wd where wd.device_status_id='" + device_status_id + "'  order by date_time ";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/smart_meter_survey","root","root");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);    
            while (rs.next()) {
                status = rs.getString("water_level");
                temp = rs.getString("water_temperature");
                intensity = rs.getString("water_intensity");
                datetime =  rs.getString("date_time");
               relay_status = rs.getString("relay_state");
               water_id = rs.getInt("water_data_id");
               relay_status1 = rs.getString("relay_state_2");
               
                magnetic_sensor_value = rs.getString("magnetic_sensor_value");
             
                if(magnetic_sensor_value.equals("1"))
                {
                   magnetic_sensor_value =" Level 1";
                }
              else if(magnetic_sensor_value.equals("3"))
                {
                   magnetic_sensor_value =" Level 2";
                }
               else if(magnetic_sensor_value.equals("7"))
                {
                   magnetic_sensor_value =" Level 3";
                }
                 else if(magnetic_sensor_value.equals("15"))
                {
                   magnetic_sensor_value =" Level 4";
                }  else if(magnetic_sensor_value.equals("0") || magnetic_sensor_value.equals(""))
                {
                   magnetic_sensor_value =" Level 0";
                }
                else
                 {
                 magnetic_sensor_value =" INVALID INPUT";
                 }
                
                
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return status+"_"+temp+"_"+intensity+"_"+datetime+"_"+relay_status+"_"+water_id+"_"+relay_status1+"_"+magnetic_sensor_value;
    }
    
     public String getEnergyLevel(String id) {
               
               int device_status_id = getWaterStatusId(id);
              // int device_status_id = 5449;
       String total_active_power="";
       String Cons_energy_mains = "";
       String active_energy_dg = "";
       String total_active_energy = "";
       String phase_voltage_R = "";
       String phase_voltage_Y = "";
       String phase_voltage_B = "";
       String phase_current_R = "";
       String phase_current_Y = "";
       String phase_current_B = "";
       
       String datetime="";
       String relay_status = "";
       int energy_id = 0;
        String query = "select distinct wd.total_active_power,wd.Cons_energy_mains,wd.active_energy_dg,wd.total_active_energy,wd.phase_voltage_R,wd.phase_voltage_Y,wd.phase_voltage_B,wd.phase_current_R,phase_current_Y,wd.phase_current_B,wd.date_time,wd.relay_state,wd.energy_data_id from energy_data wd where wd.device_status_id='" + device_status_id + "' order by date_time desc limit 1";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/smart_meter_survey","root","root");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                total_active_power = rs.getString("total_active_power");
                Cons_energy_mains = rs.getString("Cons_energy_mains");
                active_energy_dg = rs.getString("active_energy_dg");
                total_active_energy = rs.getString("total_active_energy");
                phase_voltage_R = rs.getString("phase_voltage_R");
                phase_voltage_Y = rs.getString("phase_voltage_Y");
                phase_voltage_B =  rs.getString("phase_voltage_B");
                phase_current_R =  rs.getString("phase_current_R");
                phase_current_Y = rs.getString("phase_current_Y");
                 phase_current_B = rs.getString("phase_current_B");
                
                datetime =  rs.getString("date_time");
               relay_status = rs.getString("relay_state");
               energy_id = rs.getInt("energy_data_id");
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return total_active_power+"_"+Cons_energy_mains+"_"+active_energy_dg+"_"+total_active_energy+"_"
                +phase_voltage_R+"_"+phase_voltage_Y+"_"+phase_voltage_B+"_"+phase_current_R+"_"+phase_current_Y+"_"+phase_current_B+
               "_"+datetime+"_"+relay_status+"_"+energy_id;
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

     
      public int updaterelay(String relay,String id,String relay1) {
        int vehicle_id = 0;
        int update = 0;
        int update1 = 0;
        int update1_1=0;
        String query="";
        String query1 = "";
        String query2="";
        String query3="";
        if(relay.equals("1"))
        {
         query = " UPDATE water_data " +
                       " SET relay_state = '1' ,active='Y' " +
                       " WHERE water_data_id = "+id;
         
         query1 = " UPDATE water_data SET active='Y' WHERE water_data_id < "+id;
        }
        else
        {
          query = " UPDATE water_data " +
                       " SET relay_state = '0',active='Y' " +
                       " WHERE water_data_id = "+id;
          
           query1 = " UPDATE water_data SET active='Y' WHERE water_data_id < "+id;

        }
          if(relay1.equals("1"))
        {
         query2 = " UPDATE water_data " +
                       " SET relay_state_2 = '1' ,active='Y' " +
                       " WHERE water_data_id = "+id;
         
         query3 = " UPDATE water_data SET active='Y' WHERE water_data_id < "+id;
        }
        else
        {
          query2 = " UPDATE water_data " +
                       " SET relay_state_2 = '0',active='Y' " +
                       " WHERE water_data_id = "+id;
          
           query3 = " UPDATE water_data SET active='N' WHERE water_data_id < "+id;

        }
        
        try {
           Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mqtt_server","root","root");
            Statement stmt = con.createStatement();
             Statement stmt1 = con.createStatement();
               Statement stmt2 = con.createStatement();
               Statement stmt3 = con.createStatement();
            vehicle_id = stmt.executeUpdate(query);
            update= stmt1.executeUpdate(query1);
            update1 = stmt2.executeUpdate(query2);
            update1_1 = stmt3.executeUpdate(query3);
            if(vehicle_id>0 && update>1 && update1>0 && update1_1>0)
            {
               System.out.print("Record Updated");
            }
            
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return vehicle_id;
    }

       public int updaterelay1(String relay,String id) {
        int vehicle_id = 0;
        int update = 0;
        String query="";
        String query1 = "";
        if(relay.equals("0"))
        {
         query = " UPDATE water_data " +
                       " SET relay_state_2 = '1' ,active='Y' " +
                       " WHERE water_data_id = "+id;
         
         query1 = " UPDATE water_data SET active='N' WHERE water_data_id < "+id;
        }
        else
        {
          query = " UPDATE water_data " +
                       " SET relay_state_2 = '1',active='Y' " +
                       " WHERE water_data_id = "+id;
          
           query1 = " UPDATE water_data SET active='N' WHERE water_data_id < "+id;

        }
        try {
           Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mqtt_server","root","root");
            Statement stmt = con.createStatement();
             Statement stmt1 = con.createStatement();
            vehicle_id = stmt.executeUpdate(query);
            update= stmt1.executeUpdate(query1);
            
            if(vehicle_id>0 && update>1)
            {
               System.out.print("Record Updated");
            }
            
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return vehicle_id;
    }

     
     public int getWaterStatusId( String device_id) {
        int vehicle_id = 0;
        String query = "select device_status_id from device_status where active='Y' and device_id='"+device_id+"'";
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

    
    
  

  
    public static List<String> getSearchVehicleNo(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select vehicle_no from vehicle v "
               
                + "and v.active='Y' ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String vehicle_code = (rset.getString("vehicle_no"));
                if (vehicle_code.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(vehicle_code);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Status exists.......");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public static int getManuId(String model) {
        int vehicle_id = 0;
        String query = "select m.manu_id from model m where m.active='Y' and m.model='" +model+"'" ;
        try {
           Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3309/health_department","root","root");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                vehicle_id = rs.getInt("manu_id");
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return vehicle_id;
    }
    
    
    
    
    
    public  String Manufacturer(String model)
    {
       
    int manu_id = getManuId(model);
     String vehicle_id = "";
        String query = "select manufacturer from manufacturer m where m.active='Y' and m.manu_id="+manu_id;
        try {
           Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3309/health_department","root","root");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                vehicle_id = rs.getString("manufacturer");
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return vehicle_id;
    
    }
    
    
    public  String getDate()
    {
       
     String vehicle_id = "";
        String query = "select dd.created_date from water_data dd where dd.active='Y' ORDER BY created_date desc LIMIT 1";
        try {
           Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mqtt_server","root","root");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                vehicle_id = rs.getString("created_date");
                
                
                
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return vehicle_id;
    
    }
    
     public  String getAccuracy()
    {
       
    
     String vehicle_id = "";
        String query = "select dd.gpss_accuracy from device_data dd where dd.active='Y' ORDER BY created_date desc LIMIT 1";
        try {
           Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3309/mqtt_server","root","root");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                vehicle_id = rs.getString("gpss_accuracy");
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return vehicle_id;
    
    }
     
        public static  String getDeviceStatus(String device_id) {
     //   int equip_type_id = getEquipmentType_id(equip_name);
        String query = "SELECT device_status_id FROM device_status WHERE  device_id='"+ device_id + "'and active='Y'";
       String designation_id ="";
        try {
             Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3309/mqtt_server","root","root");
            Statement stmt = con.createStatement();
            
            ResultSet rset = stmt.executeQuery(query);
            int count = 0;
           
            while (rset.next()) {    // move cursor from BOR to valid record.
                 designation_id = rset.getString("device_status_id");
            
                   
              
            }
            if (count == 0) {
            //    list.add("No such equipment_type_name exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:equipment_type Model-" + e);
        }
        return designation_id;
    }
    
     
       public static JSONObject showDataBean4(String device_id)
    {
        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        
          Date dt = new Date();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String cut_dt = df1.format(dt);
        
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime datetime = LocalDateTime.parse(cut_dt,formatter);
                datetime = datetime.minusMinutes(50);
                  String aftersubtraction=datetime.format(formatter);
        
        String device_status_id = getDeviceStatus(device_id);
        
//        String query = " select latitude,longitude from device_data where  created_date BETWEEN '"+aftersubtraction+"' AND '"+cut_dt+"' ";
        //String query = " select latitude,longitude from device_data dd where dd.active='Y'  ";
     //   String query="select latitude,longitude from device_data where  created_date ='2020-02-11 12:52:08'  ";
        //String query = " select position_data1,position_data2 from position where active='Y'";
         String query = " select dd.latitude,dd.longitude from device_data dd"
                      + " where dd.device_status_id="+device_status_id+" and dd.active='Y' ORDER BY device_data_id desc LIMIT 1";
        

        try
        { Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3309/mqtt_server","root","root");
            Statement stmt = con.createStatement();
            
            ResultSet rset = stmt.executeQuery(query); 
            while (rset.next())
            {
                JSONObject json1 = new JSONObject();
                String latitude = rset.getString("latitude");
//                String arr1[] = latitude.split("\\.");
//                String beforePoint = arr1[0];
//                String firsthalf = beforePoint.substring(0, 2);
//                String secondhalf = beforePoint.substring(2, 4);
//                String afterPoint = arr1[1];
//                String finalSubString = (secondhalf+afterPoint);
//                int value = (Integer.parseInt(finalSubString))/60;
//                String afterMultiply =  Integer.toString(value);
//                String finalString = firsthalf+"."+afterMultiply;

                String longitude = rset.getString("longitude");
//                String arr2[] = longitude.split("\\.");
//                String beforePoint2 = arr2[0];
//                String firsthalf2 = beforePoint2.substring(0, 3);
//                String secondhalf2 = beforePoint2.substring(3, 5);
//                String afterPoint2 = arr2[1];
//                String finalSubString2 = (secondhalf2+afterPoint2);
//                int value2 = (Integer.parseInt(finalSubString2))/60;
//                String afterMultiply2 =  Integer.toString(value2);
//                String finalString2 = firsthalf2+"."+afterMultiply2;

                json1.put("latitude", latitude);
                json1.put("longitude", longitude);
                jsonArray.add(json1);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowDataBean() :ShiftLoginModel" + e);
        }
        json.put("data", jsonArray);
        json.put("cordinateLength", jsonArray.size());
        return json;
    }

       
       public static JSONObject showDataBean5(String from,String To)
    {
        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        
        long min = Long.parseLong(from);
        
          Date dt = new Date();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String cut_dt = df1.format(dt);
        
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime datetime = LocalDateTime.parse(cut_dt,formatter);
                datetime = datetime.minusMinutes(min);
                  String aftersubtraction=datetime.format(formatter);
        System.out.println("date and time -"+aftersubtraction); 
        
                  
                 String query = " select latitude,longitude from device_data  where active='Y'and (latitude!='') and created_date >= '"+aftersubtraction+"'";
//      String query = " select latitude,longitude from device_data where  created_date BETWEEN '"+cut_dt+"' AND '"+aftersubtraction+"' ";
        //String query = " select position_data1,position_data2 from position where active='Y'";

        try
        { Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3309/mqtt_server","root","root");
            Statement stmt = con.createStatement();
            System.out.println("query --"+query);
            ResultSet rset = stmt.executeQuery(query);
            while (rset.next())
            {
                JSONObject json1 = new JSONObject();
                String latitude = rset.getString("latitude");
//                String arr1[] = latitude.split("\\.");
//                String beforePoint = arr1[0];
//                String firsthalf = beforePoint.substring(0, 2);
//                String secondhalf = beforePoint.substring(2, 4);
//                String afterPoint = arr1[1];
//                String finalSubString = (secondhalf+afterPoint);
//                int value = (Integer.parseInt(finalSubString))/60;
//                String afterMultiply =  Integer.toString(value);
//                String finalString = firsthalf+"."+afterMultiply;

                String longitude = rset.getString("longitude");
//                String arr2[] = longitude.split("\\.");
//                String beforePoint2 = arr2[0];
//                String firsthalf2 = beforePoint2.substring(0, 3);
//                String secondhalf2 = beforePoint2.substring(3, 5);
//                String afterPoint2 = arr2[1];
//                String finalSubString2 = (secondhalf2+afterPoint2);
//                int value2 = (Integer.parseInt(finalSubString2))/60;
//                String afterMultiply2 =  Integer.toString(value2);
//                String finalString2 = firsthalf2+"."+afterMultiply2;

                json1.put("latitude", latitude);
                json1.put("longitude", longitude);
                jsonArray.add(json1);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowDataBean() :ShiftLoginModel" + e);
        }
        json.put("data", jsonArray);
        json.put("cordinateLength", jsonArray.size());
        return json;
    }
      
      
      
   
    
    
       public  String getModel()
    {
     
     String vehicle_id = "";
        String query = "select m.model from model m,vehicle_model_map vmm, vehicle v where m.active='Y' and vmm.active='Y' and m.model_id=vmm.model_id and v.vehicle_model_map_id=vmm.vehicle_model_map_id";
        try {
           Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3309/health_department","root","root");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                vehicle_id = rs.getString("model");
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return vehicle_id;
    
    }
    
    
    
    public static List<String> getVehicle_code(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select vehicle_code from vehicle";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                int vehicle_code = rset.getInt("vehicle_code");
                //if(key_person_name.toUpperCase().startsWith(q.toUpperCase())){
                list.add(Integer.toString(vehicle_code));
                count++;
                //}
            }
            if (count == 0) {
                list.add("No such Status exists.......");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

   

    public int getKeyPersonId(String device) {
 

       int id=0;
        String query = "select device_id  from device_vehicle_mapping kp"
                + " where device_no= '" + device + "'";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                id = rs.getInt("device_id");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMsgBgColor() {
        return msgBgColor;
    }

    public void setMsgBgColor(String msgBgColor) {
        this.msgBgColor = msgBgColor;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public ArrayList<ArrayList<String>> getData() {
        ArrayList<ArrayList<String>> al1=new ArrayList<ArrayList<String>>();
        
         String status="";
        String query = "select device_id from device_status  where active='Y' and device_id !='0'";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3309/mqtt_server","root","root");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                status = rs.getString("device_id");
                
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
     
        
        
        
        return al1; //To change body of generated methods, choose Tools | Templates.
    }
    
    
    

}

