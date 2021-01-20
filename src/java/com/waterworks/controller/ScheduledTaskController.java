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

public class ScheduledTaskController implements ServletContextListener {

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
        System.out.println("************* before method call *********");
        timer.schedule(vodTimer, 10 * 1000, (10 * 1000));

    }

    class VodTimerTask extends TimerTask {

        public void insertWaterData() {
            Connection connection = null;
            int count = 0;
            try {
                int rowsAffected = 0;
                String query1 = " delete from water_data order by id limit 100 ";

                String query2 = " insert into water_data (water_data_id,device_status_id, "
                        + " water_level,water_temperature,water_intensity,phase_voltage_R,phase_voltage_Y "
                        + " ,phase_voltage_B,phase_current_R,phase_current_Y,phase_current_B,connectivity,software_version, "
                        + " service,crc,created_date,date_time,relay_state,active,relay_state_2,magnetic_sensor_value) "
                        + " select water_data_id,device_status_id,water_level,water_temperature,water_intensity, "
                        + " phase_voltage_R,phase_voltage_Y, "
                        + " phase_voltage_B,phase_current_R,phase_current_Y,phase_current_B,connectivity,software_version, "
                        + " service,crc,created_date,date_time,relay_state,active,relay_state_2,magnetic_sensor_value "
                        + " from mqtt_server.water_data order by water_data_id desc limit 100 ";

                String query3 = " select count(*) from water_data ";

                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/smart_meter_survey", "root", "root");

                Statement stmt = connection.createStatement();
                stmt.executeUpdate(query2);

                ResultSet result = stmt.executeQuery(query3);
                while (result.next()) {
                    count = result.getInt(1);
                }
                // System.out.println("count value before iffff --" + count);
                if (count > 100) {
                    // System.out.println("count value --" + count);
                    stmt.executeUpdate(query1);

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
            try {
                int rowsAffected = 0;
                String query1 = " delete from energy_data order by id limit 100 ";

                String query2 = " insert into energy_data (energy_data_id,device_status_id,total_active_power,Cons_energy_mains, "
                        + " active_energy_dg,total_active_energy,phase_voltage_R, "
                        + " phase_voltage_Y,phase_voltage_B,phase_current_R,phase_current_Y,phase_current_B,connectivity, "
                        + " software_version,service,crc,created_date,date_time,relay_state) "
                        + " select energy_data_id,device_status_id,total_active_power,Cons_energy_mains, "
                        + " active_energy_dg,total_active_energy,phase_voltage_R, "
                        + " phase_voltage_Y,phase_voltage_B,phase_current_R,phase_current_Y,phase_current_B,connectivity, "
                        + " software_version,service,crc,created_date,date_time,relay_state "
                        + " from mqtt_server.energy_data order by energy_data_id desc limit 100 ";
                
                String query3 = " select count(*) from energy_data ";

                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/smart_meter_survey", "root", "root");

                Statement stmt = connection.createStatement();
                stmt.executeUpdate(query2);

                ResultSet result = stmt.executeQuery(query3);
                while (result.next()) {
                    count = result.getInt(1);
                }
               // System.out.println("count value before iffff --" + count);
                if (count > 100) {
                   // System.out.println("count value --" + count);
                    stmt.executeUpdate(query1);

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
