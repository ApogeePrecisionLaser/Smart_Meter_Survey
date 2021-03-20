package com.waterworks.controller;

import com.connection.DBConnection.DBConnection;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;
//import java.util.TimerTask;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.ws.rs.core.Context;

public class ScheduledTaskController implements ServletContextListener {

    int scheduler_count=0;
    @Context
    static ServletContext serveletContext;

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {

        // Your code here
        //System.out.println("HelloWorld Listener has been shutdown");
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        // Your code here
        //System.out.println("HelloWorld Listener initialized.");
        TimerTask vodTimer = new VodTimerTask();

        Timer timer = new Timer();
        // System.out.println("************* before method call *********");
        timer.schedule(vodTimer, 10 * 1000, (40 * 1 * 1000));

    }

    class VodTimerTask extends TimerTask {
        //String waterlvl_prev="";

        public void insertWaterData() {
            Connection connection = null;
            //String waterlvl_prev = "";
            int count = 0;
            String new_datetime = "";
            String status = "", device_status_id = "";
            //List<String> li = new ArrayList<>();
            List<String> listDevice = new ArrayList<>();

            // int a=getOverHeadTankHeight(id,type1);
            try {
                scheduler_count++;
                int rowsAffected = 0;
                Date current_date = new Date();
                SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd");
                String date = format.format(current_date);
                date = date.replace('-', '/');

                //  String query1 = " delete from water_data WHERE date(created_date) < CURDATE() ";
                String query3 = " select distinct device_status_id from mqtt_server.water_data WHERE date_time like '" + date + "%' ";

                Class.forName("com.mysql.jdbc.Driver");
                //System.out.println("driverloaded");
                //        
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/smart_meter_survey", "root", "root");

              //  System.err.println("################## starttt ######################");
                PreparedStatement psmt;
                ResultSet rst;
                psmt = connection.prepareStatement(query3);
                //ResultSet rst = stmt.executeQuery(query3);
                rst = psmt.executeQuery();
                while (rst.next()) {
                    device_status_id = rst.getString("device_status_id");
                    listDevice.add(device_status_id);
                }
                //System.out.println("************* list device length **********" + listDevice.size());

                for (int k = 0; k < listDevice.size(); k++) {
                    List<String> li = new ArrayList<>();
                    String waterlvl_prev = "";
                   // System.err.println("********* device status idddd ********" + listDevice.get(k));
                    //System.err.println("*********** iterate value **********" + k);
                    rst = null;
                    psmt = null;

                    String query = "select water_level from mqtt_server.water_data where date_time like '" + date + "%' "
                            + "and device_status_id='" + listDevice.get(k) + "' ";
                    //stmt = connection.createStatement();
                    psmt = connection.prepareStatement(query);
                    rst = psmt.executeQuery();

//                    while (rst.next()) {
//                        int water_level = rst.getInt(1);
//                        //System.out.println("************* water lwvel **********");
//                        int diff = 0;
//                        if (waterlvl_prev !=0) {
//                            if (waterlvl_prev >= water_level) {
//                                diff = waterlvl_prev - water_level;
//                            } else {
//                                diff = water_level - waterlvl_prev;
//                            }
//
//                            if (diff <= 500) {
//                                waterlvl_prev = water_level;
//                            } else {
//                                // garbage value
//                                li.add(water_level);
//                                water_level = waterlvl_prev;
//                            }
//                        } else {
//                            waterlvl_prev = water_level;
//                        }
//                    }
                    while (rst.next()) {
                        String water_level = rst.getString("water_level");
                        //System.out.println("************* water lwvel **********");

                        int diff = 0;
                        if (!"".equals(waterlvl_prev)) {

                            if (!water_level.equals("9999")) {

                                if (Integer.parseInt(waterlvl_prev) >= Integer.parseInt(water_level)) {
                                    diff = Integer.parseInt(waterlvl_prev) - Integer.parseInt(water_level);
                                } else {
                                    diff = Integer.parseInt(water_level) - Integer.parseInt(waterlvl_prev);
                                }

                                if (diff <= 500) {
                                    waterlvl_prev = water_level;
                                } else {
                                    // garbage value
                                    li.add(water_level);
                                    water_level = waterlvl_prev;
                                }

                            }

                        } else {
                            if (!water_level.equals("9999")) {
                                waterlvl_prev = water_level;
                            }
                        }
                    }
                    System.out.println("************* water level int size  **********" + li.size());
                    //System.out.println("************* water level listtt  **********" + li.toString());

                    psmt = null;
                    rst = null;
                    String list = li.toString();
                    list = list.replace("[", "").replace("]", "");
                    System.out.println("************* water level length **********" + list.length());
                    if (list.length() == 0) {
                        list = "9999";
                    }
                    String query2 = " insert into water_data (water_data_id,device_status_id, "
                            + " water_level,water_temperature,water_intensity,phase_voltage_R,phase_voltage_Y "
                            + " ,phase_voltage_B,phase_current_R,phase_current_Y,phase_current_B,connectivity,software_version, "
                            + " service,crc,created_date,date_time,relay_state,active,relay_state_2,"
                            
                            + " magnetic_sensor_value,scheduler_count) "
                            
                            + " select water_data_id,device_status_id,water_level,water_temperature,water_intensity, "
                            + " phase_voltage_R,phase_voltage_Y, "
                            + " phase_voltage_B,phase_current_R,phase_current_Y,phase_current_B,connectivity,software_version, "
                            + " service,crc,created_date,date_time,relay_state,active,relay_state_2,magnetic_sensor_value "
                            
                            + " ,"+scheduler_count+" "
                            
                            + " from mqtt_server.water_data where date_time like '" + date + "%' "
                            + " and water_level not in (" + list + ",9999) and device_status_id='" + listDevice.get(k) + "' "
                            + " order by water_data_id  ";

                   // System.err.println("***** qyery printtt ++++++++" + query2);
                    psmt = connection.prepareStatement(query2);
                    psmt.executeUpdate();
                }

                psmt = null;
                rst = null;
                String query4 = " select new_datetime from water_data order by id desc limit 1 ";
                //System.err.println("****************** queryyyyyyyy 4444444 *****************");
                psmt = connection.prepareStatement(query4);
                rst = psmt.executeQuery();
                //System.err.println("******** query 4 for date time *****************--" + query4);
                while (rst.next()) {
                    new_datetime = rst.getString(1);
                }
               // System.err.println("************** new date time **************-" + new_datetime);

                psmt = null;
                rst = null;
                String qry = " select count(*) from water_data ";
                psmt = connection.prepareStatement(qry);
                rst = psmt.executeQuery();
                while (rst.next()) {
                  //  System.err.println("********* total count after all insert ********" + rst.getInt(1));
                }

                psmt = null;
                rst = null;

               // int sCount=scheduler_count-1;
                
                String query5 = " delete from water_data where scheduler_count<"+scheduler_count+" ";
                        //+ " new_datetime < ('" + new_datetime + "' - interval 32 second) ";
                //  System.err.println("******** query 55555 --"+query5);

                if (new_datetime != "") {
                    psmt = connection.prepareStatement(query5);
                    psmt.executeUpdate();
                  //  System.err.println("*********** query 5 for deleteeee ******* -" + query5);

                }

                //stmt.executeUpdate(query1);
                connection.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        public void insertEnergyData() {
            Connection connection = null;
            int count = 0;
            String new_datetime = "";
            try {
                int rowsAffected = 0;
                Date current_date = new Date();
                SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd");
                String date = format.format(current_date);
                date = date.replace('-', '/');

                //  String query1 = " delete from energy_data WHERE date(created_date) < CURDATE() ";
                String query4 = " select new_datetime from energy_data order by id desc limit 1 ";

                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/smart_meter_survey", "root", "root");

                Statement stmt = connection.createStatement();

                String query2 = " insert into energy_data (energy_data_id,device_status_id,total_active_power,Cons_energy_mains, "
                        + " active_energy_dg,total_active_energy,phase_voltage_R, "
                        + " phase_voltage_Y,phase_voltage_B,phase_current_R,phase_current_Y,phase_current_B,connectivity, "
                        + " software_version,service,crc,created_date,date_time,relay_state) "
                        + " select energy_data_id,device_status_id,total_active_power,Cons_energy_mains, "
                        + " active_energy_dg,total_active_energy,phase_voltage_R, "
                        + " phase_voltage_Y,phase_voltage_B,phase_current_R,phase_current_Y,phase_current_B,connectivity, "
                        + " software_version,service,crc,created_date,date_time,relay_state "
                        + " from mqtt_server.energy_data where date_time like '" + date + "%' order by energy_data_id  ";

                stmt.executeUpdate(query2);

                ResultSet rst = stmt.executeQuery(query4);
                while (rst.next()) {
                    new_datetime = rst.getString(1);
                }
                //   System.err.println("new date time -"+new_datetime);

                String query5 = " delete from energy_data where new_datetime < ('" + new_datetime + "' - interval 32 second) ";

                if (new_datetime != "") {
                    stmt.executeUpdate(query5);
                    //   System.err.println("query 5 for date time -"+query5);

                }

                //stmt.executeUpdate(query1);
                connection.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        @Override
        public void run() {
            // insertData();
            try {
                insertWaterData();
                insertEnergyData();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    /*public static void main(String[] args) {
		Timer timer = new Timer();
		timer.schedule(new CheckHighTemperature(), 3000, 1*60*1000);
	}*/
}
