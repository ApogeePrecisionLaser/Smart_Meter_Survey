/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author jpss
 */
public class JFreeChartModel {

    private Connection connection;
    private String MeterRepositoryPath;

    public void setConnection(Connection con) {
        try {
            connection = con;
        } catch (Exception e) {
            System.out.println("JFreeChartModel setConnection() Error: " + e);
        }
    }
public org.jfree.chart.JFreeChart generateBarChart(int overheadtank_id) {
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
        org.jfree.chart.JFreeChart chart = null;
        String time = "";
        String current_date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        try {
            for (int i = 0; i < 24; i++) {
                for(int j=0;j<60;j=j+5){
                if (i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 6 || i == 7 || i == 8 || i == 9 || i == 0) {
                    time = "0" + i+":"+j;
                } else {
                    time = "" + i+":"+j;
                }
                String query = "SELECT * FROM ohlevel where overheadtank_id='" + overheadtank_id + "' "
                        + " and IF('" + current_date + "' = '', date_time LIKE '%%', date_time LIKE'" + current_date + " " + time + "%')";

                PreparedStatement pstmt;
                pstmt = connection.prepareStatement(query);
                ResultSet rset = pstmt.executeQuery();
                if (rset.next()) {
                    dataSet.setValue(rset.getInt("remark"), "Level", time);
                }
                }
            }
            chart = ChartFactory.createLineChart(
                    "OverHeadTank Level Representation ", "Time", "Level",
                    dataSet, PlotOrientation.HORIZONTAL, false, true, false);
        } catch (Exception e) {
            System.out.println("JFreeChartModel generateBarChart() Error: " + e);
        }
        return chart;
    }


public org.jfree.chart.JFreeChart generateBarChart1(int overheadtank_id,String searchDateFrom,String searchDateTo) {
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
        org.jfree.chart.JFreeChart chart = null;
        String time = "";
        String current_date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        try {
            for (int i = 0; i < 24; i++) {
                if (i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 6 || i == 7 || i == 8 || i == 9 || i == 0) {
                    time = "0" + i;
                } else {
                    time = "" + i;
                }
                String query = "SELECT * FROM ohlevel le where le.overheadtank_id='" + overheadtank_id + "' "
                    //    + " and IF('" + current_date + "' = '', date_time LIKE '%%', date_time LIKE'" + current_date + " " + time + "%')";
                         + " and IF('" + searchDateFrom + "'='', le.date_time LIKE '%%',le.date_time >='" + searchDateFrom + "') "
                + " And IF('" + searchDateTo + "'='', le.date_time LIKE '%%',date_format(le.date_time, '%Y-%m-%d') <='" + searchDateTo + "') ";

                PreparedStatement pstmt;
                pstmt = connection.prepareStatement(query);
                ResultSet rset = pstmt.executeQuery();
                if (rset.next()) {
                    dataSet.setValue(rset.getInt("remark"), "Level", time);
                }
            }
            chart = ChartFactory.createLineChart(
                    "OverHeadTank Level Representation ", "Time", "Level",
                    dataSet, PlotOrientation.VERTICAL, false, true, false);
        } catch (Exception e) {
            System.out.println("JFreeChartModel generateBarChart() Error: " + e);
        }
        return chart;
    }
//    public org.jfree.chart.JFreeChart generateBarChart(int overheadtank_id) {
//        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
//        org.jfree.chart.JFreeChart chart = null;
//        String time = "";
//        String current_date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
//        try {
//            for (int i = 0; i < 24; i++) {
//                if (i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 6 || i == 7 || i == 8 || i == 9 || i == 0) {
//                    time = "0" + i;
//                } else {
//                    time = "" + i;
//                }
//                String query = "SELECT * FROM ohlevel where overheadtank_id='" + overheadtank_id + "' "
//                        + " and IF('" + current_date + "' = '', date_time LIKE '%%', date_time LIKE'" + current_date + " " + time + "%')";
//
//                PreparedStatement pstmt;
//                pstmt = connection.prepareStatement(query);
//                ResultSet rset = pstmt.executeQuery();
//                if (rset.next()) {
//                    dataSet.setValue(rset.getInt("remark"), "Level", time);
//                }
//            }
//            chart = ChartFactory.createLineChart(
//                    "OverHeadTank Level Representation ", "Time", "Level",
//                    dataSet, PlotOrientation.VERTICAL, false, true, false);
//        } catch (Exception e) {
//            System.out.println("JFreeChartModel generateBarChart() Error: " + e);
//        }
//        return chart;
//    }

    public int getOverHeadTankid(int ohlevel_id) {
        int overheadtank_id = 0;
        String query = " select overheadtank_id from ohlevel where ohlevel_id=" + ohlevel_id;
        try {
            PreparedStatement presta = connection.prepareStatement(query);
            ResultSet rs = presta.executeQuery();
            rs.next();
            overheadtank_id = rs.getInt(1);
        } catch (Exception e) {
            System.out.println("Error in getOverTankId " + e);

        }


        return overheadtank_id;
    }

    public String getMeterRepositoryPath() {
        return MeterRepositoryPath;
    }

    public void setMeterRepositoryPath(String MeterRepositoryPath) {
        this.MeterRepositoryPath = MeterRepositoryPath;
    }

    public Connection getConnection() {
        return connection;
    }
}
