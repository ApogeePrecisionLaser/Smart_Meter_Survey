/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.waterworks.model;

import com.waterworks.tableClasses.OverheadTankKeyPerson;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Com7_2
 */
public class OverheadTankKeyPersonModel {

    public static Connection connection;
    public static String message;

    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        OverheadTankKeyPersonModel.message = message;
    }


    public static Connection getConnection() {
        return connection;
    }

    public static void setConnection(Connection connection) {
        OverheadTankKeyPersonModel.connection = connection;
    }
 OverheadTankKeyPerson otkp = new OverheadTankKeyPerson();

    public List<String> getPersonName(String q){

        List<String> li = new ArrayList<String>();
        String query = " SELECT key_person_name FROM key_person "
                + " GROUP BY key_person_name "
                + " ORDER BY key_person_name ";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            int count = 0;
            q = q.trim();
            while (rs.next()) {    // move cursor from BOR to valid record.
                String name = rs.getString("key_person_name");
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
        String query = " SELECT name FROM overheadtank "
                + " GROUP BY name "
                + " ORDER BY name ";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
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
//    public void setRecordsId(String  person_name ,String overhead_tank_name){
//        PreparedStatement ps=null;
//        String query="select key_person_id,overheadtank_id "
//                       +" from overheadtank o,key_person k"
//                       +" where k.key_person_name= '" +person_name+ "' "
//                       +" and o.name= '" +overhead_tank_name+ "'";
//        try{
//        ps=connection.prepareStatement(query);
//
//        ResultSet rs = ps.executeQuery();
//        while(rs.next()){
//            otkp.setKey_person_id(rs.getInt("key_person_id"));
//            otkp.setOverheadtank_id(rs.getInt("overheadtank_id"));
//
//        }
//
//        }catch(Exception e){
//            System.out.println(e);
//        }
//
//    }
    public int getKeyPersonId(String key_person_name){
        PreparedStatement ps=null;
        int key_person_id=0;
        String query="select key_person_id from key_person "
                +" where key_person_name=?";
        try{
        ps=connection.prepareStatement(query);
        ps.setString(1, key_person_name);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            key_person_id=rs.getInt("key_person_id");
        }

        }catch(Exception e){
            System.out.println(e);
        }
        return key_person_id;
    }
    public int getTankOverheadId(String tank_overhead_name){
        PreparedStatement ps=null;
        int overheadtank_id=0;
        String query="select overheadtank_id from overheadtank "
                +" where name=?";

        try{
        ps=connection.prepareStatement(query);
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


//    public void deleteRecord(int client_id){
//    PreparedStatement ps = null;
//
//    String query = "delete from client_details where client_id=?";
//    try{
//    ps= connection.prepareStatement(query);
//    ps.setInt(1,client_id);
//    int i = ps.executeUpdate();
//    if(i>0){
//        System.out.println("record deleted successfully");
//    }
//    }catch(Exception e){
//        System.out.println("com.uesrDetailModel ClientDetailModel deleteRecord() "+e);}
//}
public void save(int key_person_id,int overheadtank_id){
     PreparedStatement ps = null;
     String query = "insert into overheadtank_keyperson(key_person_id,overheadtank_id) values(?,?)";
     try{
     ps= connection.prepareStatement(query);
     ps.setInt(1,key_person_id);
     ps.setInt(2,overheadtank_id);
     int x=ps.executeUpdate();
     if(x>0){
         setMessage("Record inserted");
     }
    }catch(Exception e){
        System.out.println(" save() "+e);
    }
}

public List showData(int lowerLimit, int noOfRowsToDisplay,String searchPersonName,String searchOverheadTankName){
    PreparedStatement ps=null;
    List list = new ArrayList();
    String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        if(lowerLimit == -1){
            addQuery = "";
        }
    String query="select name,key_person_name,overheadtank_keyperson_id "
                 + " from overheadtank oht,key_person kp,overheadtank_keyperson ohkp "
                  +" where ohkp.overheadtank_id=oht.overheadtank_id "
                  +" and ohkp.key_person_id=kp.key_person_id "
                  + " AND IF('" + searchPersonName + "' = '',key_person_name LIKE '%%', key_person_name='" + searchPersonName + "')"
                + " And IF('" + searchOverheadTankName + "' = '',name LIKE '%%', name='" + searchOverheadTankName + "')"
                  + addQuery;
    try{
    ps= connection.prepareStatement(query);
    ResultSet rs=ps.executeQuery();
    while(rs.next()){
     OverheadTankKeyPerson otkp = new OverheadTankKeyPerson();
     otkp.setOverheadtank_keyperson_id(rs.getInt("overheadtank_keyperson_id"));
     otkp.setKey_person_name(rs.getString("key_person_name"));
     otkp.setName(rs.getString("name"));

     list.add(otkp);
    }
    }catch(Exception e){
        System.out.println(e);
    }
    return list;
}

public void update(int overheadtank_keyperson_id,int key_person_id,int overheadtank_id){
    PreparedStatement ps=null;
    String query=" update overheadtank_keyperson ohkp "
                  +" set ohkp.key_person_id='"+key_person_id+"' , ohkp.overheadtank_id='"+overheadtank_id+"' "
                  +" where overheadtank_keyperson_id='"+overheadtank_keyperson_id+"' ";
    try{
    ps=connection.prepareStatement(query);
    int i = ps.executeUpdate();
    if(i>0){
        System.out.println("Record updated successfully");
    }
 else{
        System.out.println("Record not updated ");
 }

    }catch(Exception e){
        System.out.println(e);
    }
}

public void delete(int overheadtank_keyperson_id){
  PreparedStatement ps=null;

  String query = "delete from overheadtank_keyperson "
          + " where overheadtank_keyperson_id="+overheadtank_keyperson_id ;

  try{
  ps=connection.prepareStatement(query);

  int i=ps.executeUpdate();
  if(i>0){
      System.out.println("Record deleted successfully");
  }else{
      System.out.println("Record not deleted ");
  }

    }catch(Exception e){
        System.out.println(e);
    }
    
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

public int getNoOfRows(String searchPersonName,String searchOverheadTankName){
    int noOfRows=0;
    PreparedStatement ps=null;
    String query="select count(*) "
                  +" from overheadtank_keyperson ohkp,key_person kp,overheadtank oht"
                  +" where oht.overheadtank_id=ohkp.overheadtank_id and  kp.key_person_id=ohkp.key_person_id "
                  +" AND IF('" + searchPersonName + "' = '', key_person_name LIKE '%%',key_person_name=?)"
                    + " And IF('" + searchOverheadTankName + "' = '',name LIKE '%%', name=?)";
    try{

        ps=connection.prepareStatement(query);
        ps.setString(1, searchPersonName);
        ps.setString(2,searchOverheadTankName);

        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            noOfRows=Integer.parseInt(rs.getString(1));
        }

    }catch(Exception e){
        System.out.println(e);
    }

    return noOfRows;
}

//public void update(ClientDetail clientDetail){
//     PreparedStatement ps = null;
//     String query = "update client_details SET client_name=?,client_email=? where client_id=?";
//     try{
//         ps=  connection.prepareStatement(query);
//         ps.setString(1,clientDetail.getClient_name());
//         ps.setString(2,clientDetail.getClient_email());
//         ps.setInt(3,clientDetail.getClient_id());
//         int i = ps.executeUpdate();
//         if(i>0){
//             System.out.println("record updated successfully");
//         }
//
//     }catch(Exception e){
//         System.out.println("com.uesrDetailModel ClientDetailModel update() "+e);
//     }
//
//}



}
