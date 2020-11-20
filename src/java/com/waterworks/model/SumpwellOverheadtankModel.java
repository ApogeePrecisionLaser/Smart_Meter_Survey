/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.waterworks.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import com.waterworks.tableClasses.SumpwellOverheadtankBean;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Shobha
 */
public class SumpwellOverheadtankModel {

    public static Connection connection;
    public static String message;

    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";

    public SumpwellOverheadtankBean getBean() {
        return bean;
    }

    public void setBean(SumpwellOverheadtankBean bean) {
        this.bean = bean;
    }

    public String getMsgBgColor() {
        return msgBgColor;
    }

    public void setMsgBgColor(String msgBgColor) {
        this.msgBgColor = msgBgColor;
    }


    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        SumpwellOverheadtankModel.message = message;
    }


    public static Connection getConnection() {
        return connection;
    }

    public static void setConnection(Connection connection) {
        SumpwellOverheadtankModel.connection = connection;
    }
 SumpwellOverheadtankBean bean = new SumpwellOverheadtankBean();

    public List<String> getSumpwellName(String q){

        List<String> li = new ArrayList<String>();
        String query = " SELECT name FROM overheadtank oht where oht.type='NO' "
                + " GROUP BY name "
                + " ORDER BY name ";
        try {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            int count = 0;
            q = q.trim();
            while (rs.next()) {    // move cursor from BOR to valid record.
                String name = rs.getString("name");
                if (name.toUpperCase().startsWith(q.toUpperCase())) {
                    li.add(name);
                    count++;
                }
            }
            if (count == 0) {
                li.add("No such Status exists");
            }
        } catch (Exception e) {
            System.out.println("getP_name ERROR inside PersonModel" + e);
        }
        return li;

    }

    public List<String> getOverheadTankName(String q){

        List<String> li = new ArrayList<String>();
        String query = " SELECT name FROM overheadtank oht where oht.type='YES'"
                + " GROUP BY name "
                + " ORDER BY name ";
        try {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            int count = 0;
            q = q.trim();
            while (rs.next()) {    // move cursor from BOR to valid record.
                String name = rs.getString("name");
                if (name.toUpperCase().startsWith(q.toUpperCase())) {
                    li.add(name);
                    count++;
                }
            }
            if (count == 0) {
                li.add("No such Status exists");
            }
        } catch (Exception e) {
            System.out.println("getP_name ERROR inside PersonModel" + e);
        }
        return li;

    }

    public List<String> getJunctionImei_no(String q){

        List<String> li = new ArrayList<String>();
        String query =    " select j.imei_no "
                         +" from meter_survey.junction j "
                         +" where j.active='Y' "
                         +" GROUP BY j.imei_no "
                         +" ORDER BY j.imei_no ";
        try {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            int count = 0;
            q = q.trim();
            while (rs.next()) {    // move cursor from BOR to valid record.
                String name = rs.getString("imei_no");
                if (name.toUpperCase().startsWith(q.toUpperCase())) {
                    li.add(name);
                    count++;
                }
            }
            if (count == 0) {
                li.add("No such Status exists");
            }
        } catch (Exception e) {
            System.out.println("getP_name ERROR inside PersonModel" + e);
        }
        return li;

    }

    public List<String> getSearchSumpwellName(String q){

        List<String> li = new ArrayList<String>();
        String query = " select name "
                       +" from overheadtank oht,sumpwell_overheadtank sw_oht "
                       +" where oht.overheadtank_id=sw_oht.overheadtank_id1 "
                       +" and sw_oht.active='Y' "
                       + " GROUP BY name "
                       + " ORDER BY name ";
               
        try {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            int count = 0;
            q = q.trim();
            while (rs.next()) {    // move cursor from BOR to valid record.
                String name = rs.getString("name");
                if (name.toUpperCase().startsWith(q.toUpperCase())) {
                    li.add(name);
                    count++;
                }
            }
            if (count == 0) {
                li.add("No such Status exists");
            }
        } catch (Exception e) {
            System.out.println("getP_name ERROR inside PersonModel" + e);
        }
        return li;

    }
     public List<String> getSearchOverheadTankName(String q){

        List<String> li = new ArrayList<String>();
        String query = "  select name "
                       +" from overheadtank oht,sumpwell_overheadtank sw_oht "
                       +" where oht.overheadtank_id=sw_oht.overheadtank_id2 "
                       +" and sw_oht.active='Y' "
                       + " GROUP BY name "
                       + " ORDER BY name ";
               
        try {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            int count = 0;
            q = q.trim();
            while (rs.next()) {    // move cursor from BOR to valid record.
                String name = rs.getString("name");
                if (name.toUpperCase().startsWith(q.toUpperCase())) {
                    li.add(name);
                    count++;
                }
            }
            if (count == 0) {
                li.add("No such Status exists");
            }
        } catch (Exception e) {
            System.out.println("getP_name ERROR inside PersonModel" + e);
        }
        return li;

    }

    public int getTankOverheadId(String tank_overhead_name){
        PreparedStatement ps=null;
        int overheadtank_id=0;
        String query="select overheadtank_id from overheadtank "
                +" where name=?";

        try{
        ps= (PreparedStatement) connection.prepareStatement(query);
        ps.setString(1, tank_overhead_name);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            overheadtank_id=rs.getInt("overheadtank_id");
        }

        }catch(Exception e){
            System.out.println(e);
        }


        return overheadtank_id;
    }

    public int getJunctionId(String imei_no){
        PreparedStatement ps=null;
        int junction_id=0;
        String query=" select j.junction_id "
                     +" from meter_survey.junction j "
                     +" where j.imei_no='"+imei_no+"' "
                     +" and j.active='Y' ";

        try{
        ps= (PreparedStatement) connection.prepareStatement(query);

        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            junction_id=rs.getInt("junction_id");
        }

        }catch(Exception e){
            System.out.println(e);
        }


        return junction_id;
    }



public void save(SumpwellOverheadtankBean bean){
     PreparedStatement ps = null;
     int sumpwell_id=getTankOverheadId(bean.getSumpwell_name());
     int overheadtank_id=  getTankOverheadId(bean.getOverheadtank_name());
     int junction_id=getJunctionId(bean.getJunction_name());
     String query = "insert into sumpwell_overheadtank(overheadtank_id1,overheadtank_id2,remark,junction_id) values(?,?,?,?)";
     try{
     ps=    (PreparedStatement) connection.prepareStatement(query);
     ps.setInt(1,sumpwell_id);
     ps.setInt(2,overheadtank_id);
     ps.setString(3,bean.getRemark());
     ps.setInt(4, junction_id);
     int x=ps.executeUpdate();
     if(x>0){
         setMessage("Record inserted");
         msgBgColor = COLOR_OK;
     }
 else{
         setMessage("Record not inserted");
         msgBgColor = COLOR_ERROR;
 }
    }catch(Exception e){
        System.out.println(" save() "+e);
    }
}

public List showData(int lowerLimit, int noOfRowsToDisplay,String search_sumpwell_name,String search_overheadtank_name){
    PreparedStatement ps=null;
    List list = new ArrayList();
    String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        if(lowerLimit == -1){
            addQuery = "";
        }
//    String query=" select sw_oht.sumpwell_overheadtank_id,oht1.name as sumpwell,oht2.name as ohtname,sw_oht.remark "
//                 +" from sumpwell_overheadtank sw_oht,overheadtank as oht1,overheadtank as oht2 "
//                 +" where oht1.overheadtank_id=sw_oht.overheadtank_id1 "
//                 +" and oht2.overheadtank_id=sw_oht.overheadtank_id2 "
//                 +" and sw_oht.active='Y' "
//                  + " AND IF('" + search_sumpwell_name + "' = '',oht1.name LIKE '%%', oht1.name='" + search_sumpwell_name + "')"
//                + " And IF('" + search_overheadtank_name + "' = '',oht2.name LIKE '%%', oht2.name='" + search_overheadtank_name + "')"
//                  + addQuery;

     String query=" select sw_oht.sumpwell_overheadtank_id,oht1.name as sumpwell,oht2.name as ohtname,sw_oht.remark,j.imei_no "
                  +" from sumpwell_overheadtank sw_oht,overheadtank as oht1,overheadtank as oht2,meter_survey.junction j "
                  +" where oht1.overheadtank_id=sw_oht.overheadtank_id1 "
                  +" and oht2.overheadtank_id=sw_oht.overheadtank_id2 "
                  +" and sw_oht.junction_id=j.junction_id "
                  +" and sw_oht.active='Y' "
                  +" and j.active='Y' "
                  +" AND IF('" + search_sumpwell_name + "' = '',oht1.name LIKE '%%', oht1.name='" + search_sumpwell_name + "')"
                  +" And IF('" + search_overheadtank_name + "' = '',oht2.name LIKE '%%', oht2.name='" + search_overheadtank_name + "')"
                  + addQuery;
    try{
    ps=     (PreparedStatement) connection.prepareStatement(query);
    ResultSet rs=ps.executeQuery();
    while(rs.next()){
     SumpwellOverheadtankBean bean = new SumpwellOverheadtankBean();
     bean.setSumpwell_overheadtank_id(rs.getString("sumpwell_overheadtank_id"));
     bean.setSumpwell_name(rs.getString("sumpwell"));
     bean.setOverheadtank_name(rs.getString("ohtname"));
     bean.setRemark(rs.getString("remark"));
     bean.setJunction_name(rs.getString("imei_no"));

     list.add(bean);
    }
    }catch(Exception e){
        System.out.println(e);
    }
    return list;
}

public boolean update(SumpwellOverheadtankBean bean){
    boolean status=false;

    int rowsAffected=0;
    int sumpwell_overheadtank_id=Integer.parseInt(bean.getSumpwell_overheadtank_id());
      int sumpwell_id=getTankOverheadId(bean.getSumpwell_name());
     int overheadtank_id=  getTankOverheadId(bean.getOverheadtank_name());
     int junction_id=getJunctionId(bean.getJunction_name());

      String query1 = "SELECT max(rev_no) rev_no FROM sumpwell_overheadtank sw_oht WHERE sumpwell_overheadtank_id = "+sumpwell_overheadtank_id+" && active='Y' ORDER BY rev_no DESC";
      String query2 = " UPDATE sumpwell_overheadtank sw_oht SET active=? WHERE sumpwell_overheadtank_id = ? && rev_no = ? ";
      String query3 = "INSERT INTO sumpwell_overheadtank(sumpwell_overheadtank_id,overheadtank_id1,overheadtank_id2,remark,rev_no, active,junction_id) VALUES (?,?,?,?,?,?,?) ";
      int updateRowsAffected = 0;
      try {
           PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query1);
           ResultSet rs = ps.executeQuery();
           if(rs.next()){
           PreparedStatement pst = (PreparedStatement) connection.prepareStatement(query2);
           pst.setString(1,  "N");
           pst.setInt(2,sumpwell_overheadtank_id);
           pst.setInt(3, rs.getInt("rev_no"));
           updateRowsAffected = pst.executeUpdate();
             if(updateRowsAffected >= 1){
             int rev = rs.getInt("rev_no")+1;
             PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
             psmt.setInt(1,sumpwell_overheadtank_id);
             psmt.setInt(2,sumpwell_id);
             psmt.setInt(3,overheadtank_id);
             psmt.setString(4,bean.getRemark());
             psmt.setInt(5, rev);
             psmt.setString(6, "Y");
             psmt.setInt(7, junction_id);
             int a = psmt.executeUpdate();
              if(a > 0)
              status=true;
             setMessage("Record Updated Successfully...");
         msgBgColor = COLOR_OK;
             }else{
               setMessage("Record not updated...");
         msgBgColor = COLOR_ERROR;
             }
           }
          } catch (Exception e)
             {
              System.out.println("CityDAOClass reviseRecord() Error: " + e);
             }
      if (updateRowsAffected > 0) {
             message = "Record Updated successfully......";
            //msgBgColor = COLOR_OK;
            System.out.println("Inserted");
        } else {
             message = "Record Not Updated Some Error!";
           // msgBgColor = COLOR_ERROR;
            System.out.println("not Inserted");
        }

       return status;


}

public boolean delete(SumpwellOverheadtankBean bean){
    int sumpwell_overheadtank_id=Integer.parseInt(bean.getSumpwell_overheadtank_id());
  boolean status=false;
   int rowsAffected=0;
   String query = "update sumpwell_overheadtank SET active='N' where sumpwell_overheadtank_id=?";
try{
    PreparedStatement pst = (PreparedStatement) connection.prepareStatement(query);
    pst.setInt(1,sumpwell_overheadtank_id);
    rowsAffected= pst.executeUpdate();
 if(rowsAffected > 0)
    status=true;
else status=false;
}catch(Exception e){
    System.out.println("ERROR: " + e);
}
 if (rowsAffected > 0) {
             message = "Record Deleted successfully......";
             msgBgColor = COLOR_OK;
             System.out.println("Deleted");
        } else {
             message = "Record Not Deleted Some Error!";
            msgBgColor = COLOR_ERROR;
            System.out.println("not Deleted");}
return status;

}

public  byte[] generateRecordList(String jrxmlFilePath,List li)
     {
                byte[] reportInbytes = null;
                try {
                    JRBeanCollectionDataSource jrBean=new JRBeanCollectionDataSource(li);
                    JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
                    reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null, jrBean);
                } catch (Exception e) {

                    System.out.println("TypeOfErrorModel generatReport() JRException: " + e);
                }
                return reportInbytes;
     }

public int getNoOfRows(String search_sumpwell_name,String search_overheadtank_name){
    int noOfRows=0;
    PreparedStatement ps=null;
    String query="select count(*) "
                 +" from sumpwell_overheadtank sw_oht,overheadtank as oht1,overheadtank as oht2,meter_survey.junction j "
                 +" where oht1.overheadtank_id=sw_oht.overheadtank_id1 "
                 +" and oht2.overheadtank_id=sw_oht.overheadtank_id2 "
                 +" and sw_oht.junction_id=j.junction_id "
                 +" and sw_oht.active='Y' "
                 +" and j.active='Y' "
                 + " AND IF('" + search_sumpwell_name + "' = '',oht1.name LIKE '%%', oht1.name='" + search_sumpwell_name + "')"
                + " And IF('" + search_overheadtank_name + "' = '',oht2.name LIKE '%%', oht2.name='" + search_overheadtank_name + "')";

    try{

        ps= (PreparedStatement) connection.prepareStatement(query);
        //ps.setString(1, searchPersonName);
        //ps.setString(2,searchOverheadTankName);

        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            noOfRows=Integer.parseInt(rs.getString(1));
        }

    }catch(Exception e){
        System.out.println(e);
    }

    return noOfRows;
}



}
