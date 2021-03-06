/*
ShiftWorkBench-2
 */
package com.node.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import com.node.tableClasses.Date_TimeBean;
import com.util.KrutiDevToUnicodeConverter;
import com.util.UnicodeToKrutiDevConverter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Shubham Swarnkar
 */
public class Date_TimeModel {

    private static Connection connection;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";
    public static KrutiDevToUnicodeConverter krutiToUnicode = new KrutiDevToUnicodeConverter();
    public static UnicodeToKrutiDevConverter unicodeToKruti = new UnicodeToKrutiDevConverter();

    public static int getNoOfRows() {
        int noOfRows = 0;
        try {
            String query = "SELECT count(*) from level_date ";
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

    public static List<Date_TimeBean> showData(int lowerLimit, int noOfRowsToDisplay) {
        List list = new ArrayList();
        String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        if (lowerLimit == -1) {
            addQuery = "";
        }
        String query = "SELECT * from date_time"
                + addQuery;
        try {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Date_TimeBean sb = new Date_TimeBean();
                sb.setlevel_date_id(rs.getInt("date_time_id"));
                sb.setlevel_date(rs.getString("date"));
                sb.setStarting_time(rs.getString("starting_time"));
                list.add(sb);
            }
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
        return list;
    }

    public boolean insertRecord(Date_TimeBean bean) {
        boolean status = false;
        String query = "";
        int rowsAffected = 0;
        int level_date_id = bean.getlevel_date_id();
        if (level_date_id == 0) {
            query = "insert into date_time (starting_time, date) values (?,?)";
        }
        if (level_date_id > 0) {
            query = " update date_time set starting_time=?,date=? where date_time_id=?";
        }
        try {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
            ps.setString(2, bean.getlevel_date());
            ps.setString(1, bean.getStarting_time());
            if (level_date_id > 0) {
                ps.setInt(3, level_date_id);
            }
            rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                status = true;
            }
        } catch (Exception e) {
            System.out.println("ERROR: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record Inserted successfully......";
            msgBgColor = COLOR_OK;
            System.out.println("Inserted");
        } else {
            message = "Record Not Inserted Some Error!";
            msgBgColor = COLOR_ERROR;
            System.out.println("not Inserted");
        }
        return status;
    }

    public boolean deleteRecord(String level_date_id) {
        boolean status = false;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement("Delete from date_time where date_time_id=" + level_date_id + " ").executeUpdate();
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
}
