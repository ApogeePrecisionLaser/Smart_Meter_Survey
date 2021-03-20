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
import java.util.*;
//import java.util.TimerTask;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.ws.rs.core.Context;

public class MqttScheduleController implements ServletContextListener {

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
        timer.schedule(vodTimer, 20 * 1000, (20 * 2 * 1000));

    }

    class VodTimerTask extends TimerTask {
        //String waterlvl_prev="";

        public void insertWaterData() {
            Connection connection = null;
            String waterlvl_prev = "";
            int count = 0;
            String new_datetime = "";
            String status = "", water_level = "";
            List<String> li = new ArrayList<>();

            // int a=getOverHeadTankHeight(id,type1);
            try {
                int rowsAffected = 0;
                //String query1 = " delete from temp_water_data WHERE date(created_date) < CURDATE() ";

                //String query3 = " select count(*) from temp_water_data WHERE date(created_date) < CURDATE() ";

                String query4 = " select new_datetime from temp_water_data order by id desc limit 1 ";

                //String query = "select water_level from temp_water_data where date(created_date) = curdate()";

                Class.forName("com.mysql.jdbc.Driver");
                System.out.println("driverloaded");

                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/smart_meter_survey", "root", "root");

                Statement stmt = connection.createStatement();

//                ResultSet rs = stmt.executeQuery(query);
//                while (rs.next()) {
//                    water_level = rs.getString("water_level");
//                    li.add("9999");
//                }

//                String list = li.toString();
//                list = list.replace("[", "").replace("]", "");
                String query2 = " insert into temp_water_data (water_data_id,device_status_id, "
                        + " water_level,water_temperature,water_intensity,phase_voltage_R,phase_voltage_Y "
                        + " ,phase_voltage_B,phase_current_R,phase_current_Y,phase_current_B,connectivity,software_version, "
                        + " service,crc,created_date,date_time,relay_state,active,relay_state_2,magnetic_sensor_value) "
                        + " select water_data_id,device_status_id,water_level,water_temperature,water_intensity, "
                        + " phase_voltage_R,phase_voltage_Y, "
                        + " phase_voltage_B,phase_current_R,phase_current_Y,phase_current_B,connectivity,software_version, "
                        + " service,crc,created_date,date_time,relay_state,active,relay_state_2,magnetic_sensor_value "
                        + " from water_data where date(created_date)=curdate() ";
                        //+ " and water_level not in (" + list + ") order by water_data_id desc ";

                      //  System.err.println("**************** querryy2 water data *******************"+query2);
                stmt.executeUpdate(query2);
//                ResultSet result = stmt.executeQuery(query3);
//                while (result.next()) {
//                    count = result.getInt(1);
//                }
//                if (count > 0) {
//                    stmt.executeUpdate(query1);
//                }
                ResultSet rst = stmt.executeQuery(query4);
                while (rst.next()) {
                    new_datetime = rst.getString(1);
                }
                String query5 = " delete from temp_water_data where date(created_date)=curdate() and new_datetime < ('" + new_datetime + "' - interval 17 second) ";

                if (new_datetime != "") {
                    stmt.executeUpdate(query5);
                  //  System.err.println("query 5 for date time -"+query5);

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
                //String query1 = " delete from temp_energy_data WHERE date(created_date) < CURDATE() ";

                String query4 = " select new_datetime from temp_energy_data order by id desc limit 1 ";

                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/smart_meter_survey", "root", "root");

                Statement stmt = connection.createStatement();

                String query2 = " insert into temp_energy_data (energy_data_id,device_status_id,total_active_power,Cons_energy_mains, "
                        + " active_energy_dg,total_active_energy,phase_voltage_R, "
                        + " phase_voltage_Y,phase_voltage_B,phase_current_R,phase_current_Y,phase_current_B,connectivity, "
                        + " software_version,service,crc,created_date,date_time,relay_state) "
                        + " select energy_data_id,device_status_id,total_active_power,Cons_energy_mains, "
                        + " active_energy_dg,total_active_energy,phase_voltage_R, "
                        + " phase_voltage_Y,phase_voltage_B,phase_current_R,phase_current_Y,phase_current_B,connectivity, "
                        + " software_version,service,crc,created_date,date_time,relay_state "
                        + " from energy_data where date(created_date)=curdate() order by energy_data_id desc ";

              //  System.err.println("**************** querryy2 energy data *******************"+query2);
                stmt.executeUpdate(query2);

                ResultSet rst = stmt.executeQuery(query4);
                while (rst.next()) {
                    new_datetime = rst.getString(1);
                }
             //   System.err.println("new date time -"+new_datetime);

                String query5 = " delete from temp_energy_data where date(created_date)=curdate() and new_datetime < ('" + new_datetime + "' - interval 17 second) ";

                if (new_datetime != "") {
                    stmt.executeUpdate(query5);
                }
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
        }
    }
}
