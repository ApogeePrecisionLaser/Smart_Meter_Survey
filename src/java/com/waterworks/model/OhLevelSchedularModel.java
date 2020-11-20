/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.waterworks.model;

import com.general.model.GeneralModel;
import com.waterworks.tableClasses.OhLevelSchedularBean;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimerTask;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Shobha
 */
public class OhLevelSchedularModel extends TimerTask{

    
    private ServletContext ctx;
    private static Connection connection;
    Date dt = new Date();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String cut_dt = df.format(dt);

    HttpServletRequest request;
    HttpServletResponse response;
    public void setCtx(ServletContext ctx) {
        this.ctx = ctx;
    }



    public void run() {
        try {

          List list=getAllRecords();
           createPDF(request,response,list);

        } catch (Exception ex) {
            System.out.println(" run() Error: " + ex);
        }
    }

public List getAllRecords(){

List<OhLevelSchedularBean> list = new ArrayList<OhLevelSchedularBean>();
    byte[] twoByteData = new byte[2];
        String query="select wtp.name as name1,oht.name,ol.remark,ol.ohlevel_id,d.type,ol.date_time,level3,level4,capacity_height "
                + " from watertreatmentplant AS wtp,overheadtank AS oht,"
                + " ohlevel as ol,distribution as d"
                + " where oht.watertreatmentplant_id = wtp.watertreatmentplant_id"
                + " and ol.overheadtank_id = oht.overheadtank_id and ol.ohlevel_id=d.ohlevel_id "
                + " and ol.date_time="+cut_dt
                + " and Limit="+2;
        try{
        PreparedStatement ps1 = (PreparedStatement) connection.prepareStatement(query);
        ResultSet rset = ps1.executeQuery();
        while(rset.next()){

            OhLevelSchedularBean ohLevelBean = new OhLevelSchedularBean();
               ohLevelBean.setWaterTreatmentPlantName(rset.getString(1));
                ohLevelBean.setOverHeadTankName(rset.getString(2));
                ohLevelBean.setOhLevelId(rset.getInt("ohlevel_id"));
                ohLevelBean.setType(rset.getString("type"));
                ohLevelBean.setDateTime(rset.getString("date_time"));
                ohLevelBean.setLevel(rset.getString("remark"));
                twoByteData[0] = rset.getByte("level3");
                twoByteData[1] = rset.getByte("level4");
                long voltage1 = (new BigInteger(twoByteData).longValue());
                ohLevelBean.setValue_of_34(("" + voltage1).trim());
                ohLevelBean.setLevel_of_34("" + df.format((voltage1 / (rset.getDouble("capacity_height") * 1000)) * 100));
                list.add(ohLevelBean);
            
        }
        }catch(Exception e){
            System.out.println(e);
        }
   return list;

}


public void createPDF(HttpServletRequest request, HttpServletResponse response,List list) {
                String jrxmlFilePath;
               
                try{
                //response.setContentType("application/pdf");
                //response.setCharacterEncoding("UTF-8");
                FileOutputStream fileOuputStream = new FileOutputStream("D:\\report.pdf");
                jrxmlFilePath = ctx.getRealPath("/report/waterWorks/OnOffLevel.jrxml");
                //list = getAllRecords();
                byte[] reportInbytes = generateRecordList(jrxmlFilePath, list);
                //response.setContentLength(reportInbytes.length);

                fileOuputStream.write(reportInbytes);
                fileOuputStream.close();

                     }catch(Exception e){
                      System.out.println(e);
                        }
            }



public static byte[] generateRecordList(String jrxmlFilePath, List list) {
        byte[] reportInbytes = null;
        HashMap mymap = new HashMap();
        try {
            JRBeanCollectionDataSource jrBean = new JRBeanCollectionDataSource(list);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null, jrBean);

        } catch (Exception e) {
            System.out.println("GeneralModel generateRecordList() JRException: " + e);
        }
        return reportInbytes;
    }

















    public static Connection getConnection() {
        return connection;
    }

    public static void setConnection(Connection connection) {
        OhLevelSchedularModel.connection = connection;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println(" closeConnection() Error: " + e);
        }
    }
}
