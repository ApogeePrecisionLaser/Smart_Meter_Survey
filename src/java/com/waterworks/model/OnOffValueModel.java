/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.waterworks.model;

import com.waterworks.tableClasses.OnOffValue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jpss
 */
public class OnOffValueModel {
private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_username;
    private String db_password;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";




 public boolean insertRecord(OnOffValue onOffValue)
    {
    boolean b=false;
        String query = "insert into on_off_value (name,value,time,remark) values (?,?,?,?)";
        try {
             PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query);
             ps.setString(1,onOffValue.getOnOffName());
             ps.setString(2,onOffValue.getOnOffValue());
             ps.setString(3,onOffValue.getTime());
             ps.setString(4,onOffValue.getRemark());
             int rowsAffected=ps.executeUpdate();
        if (rowsAffected > 0) {
            message = "Record saved successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        } catch (Exception e) {
             message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
            System.out.println("properties ERROR inside propertiesModel - " + e);
        }
        return b;
    }

    public boolean deleteRecord(OnOffValue onOffValue) {
        boolean b=false;
        String query = "DELETE FROM on_off_value where on_off_value_id=?";
        try {
             PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query);
             ps.setInt(1,onOffValue.getOnOff_id());
             int rowsAffected=ps.executeUpdate();
        if (rowsAffected > 0) {
            message = "Record deleted successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot delete the record, some error.";
            msgBgColor = COLOR_ERROR;
        }

        } catch (Exception e)
        {
             message = "Cannot delete the record, some error.";
            msgBgColor = COLOR_ERROR;
            System.out.println("properties ERROR inside propertiesModel - " + e);
        }
         return b;
    }
    public boolean UpdateRecord(OnOffValue onOffValue)
    {
        String query = "update on_off_value set name='"+onOffValue.getOnOffName()+"',value='"+onOffValue.getOnOffValue()+"',time='"+onOffValue.getTime()+"',remark='"+onOffValue.getRemark()+"' where on_off_value_id="+onOffValue.getOnOff_id()+"";
        try {
             PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query);
             int rowsAffected=ps.executeUpdate();
             if (rowsAffected > 0) {
            message = "Record Update successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot update the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        } catch (Exception e) {
             message = "Cannot update the record, some error.";
            msgBgColor = COLOR_ERROR;
            System.out.println("getFuseType ERROR inside SurveyModel - " + e);
        }
        boolean b=false;
        return b;
    }



    public List getOnOffName(String q)
    {
        List<String> list = new ArrayList<String>();
        String query = " select name from on_off_value order by name ";
        try
        {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();

            while (rs.next())
            {
                list.add(rs.getString("name"));
                count++;
            }

            if (count == 0) {
                list.add("No such Item_name exists.");
            }
        }
        catch (Exception e)
        {
            System.out.println("getproperties ERROR inside SurveyModel - " + e);
        }
        return list;
    }

    public List<OnOffValue> ShowData(int lowerLimit, int noOfRowsToDisplay,String searchOnOff_Name)
    {
        List<OnOffValue> list = new ArrayList<OnOffValue>();
        String query = "SELECT * FROM on_off_value  "
                + " where  if('"+searchOnOff_Name+"'='', name LIKE '%%', name='"+searchOnOff_Name+"')"
                + " ORDER BY name LIMIT "+lowerLimit+","+noOfRowsToDisplay;
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next())
            {
                OnOffValue onOffValue=new OnOffValue();
                onOffValue.setOnOffName(rs.getString("name"));
                onOffValue.setOnOffValue(rs.getString("value"));
                onOffValue.setTime(rs.getString("time"));
                onOffValue.setRemark(rs.getString("remark"));
                onOffValue.setOnOff_id(rs.getInt("on_off_value_id"));
                list.add(onOffValue);
            }
        } catch (Exception e)
        {
            System.out.println("getproperties ERROR inside SurveyModel - " + e);
        }
        return list;
    }
    public int getNoOfRows(String searchOnOff_Name)
    {
        int count=0;
       String query = "SELECT count(*) as count FROM on_off_value  "
               + " where  if('"+searchOnOff_Name+"'='', name LIKE '%%', name='"+searchOnOff_Name+"')";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next())
            {
                count=rs.getInt("count");
            }
        } catch (Exception e) {
            System.out.println("properties ERROR inside propertiesModel - " + e);
        }
        return count;
    }

  public String getMessage() {
        return message;
    }

    public String getMsgBgColor() {
        return msgBgColor;
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
