/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.customer.model;

import com.customer.tableClasses.ErrorView;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Com7_2
 */
public class ErrorViewModel {

    public static Connection connection;

    public static Connection getConnection() {
        return connection;
    }

    public static void setConnection(Connection connection) {
        ErrorViewModel.connection = connection;
    }


    public int getNoOfRows(String searchKeyPerson,String searchOverheadTank,HttpSession session){
        PreparedStatement ps=null;
        int noOfRows=0;
        int key_person_id=(Integer)session.getAttribute("key_person_id");
//      String query=  " select count(*) "
//                +" from login lg,watertreatmentplant wtp,overheadtank t,overheadtank_keyperson okp,error_log el,error_log_msg elm,status st,key_person kp,type_of_error tof "
//                +" where elm.error_log_id=el.error_log_id "
//                +" and elm.overheadtank_key_person_id=okp.overheadtank_keyperson_id "
//                +" and el.status_id=st.status_id"
//                +"  and el.overheadtank_id=t.overheadtank_id"
//                +"  and okp.overheadtank_id=t.overheadtank_id"
//                +"  and okp.key_person_id=kp.key_person_id"
//                +"  and wtp.watertreatmentplant_id=t.watertreatmentplant_id"
//                +"  and tof.type_of_error_id=el.type_of_error_id "
//                 +" and kp.key_person_id=lg.key_person_id "
//                 +" and kp.key_person_id="+key_person_id+" "
//                 +" and elm.active='Y' "
//                +" And IF('" + searchKeyPerson + "' = '',key_person_name LIKE '%%', key_person_name=?) "
//                +" And IF('" + searchOverheadTank + "' = '',t.name LIKE '%%', t.name=?) ";
        String query="select count(*) "
                +" from login lg,watertreatmentplant wtp,overheadtank t,overheadtank_keyperson okp,error_log el,error_log_msg elm,status st,key_person kp,type_of_error tof "
                      +" where elm.error_log_id=el.error_log_id "
                      +" and elm.overheadtank_key_person_id=okp.overheadtank_keyperson_id "
                      +" and el.status_id=st.status_id  and el.overheadtank_id=t.overheadtank_id "
                      +" and okp.overheadtank_id=t.overheadtank_id "
                      +" and wtp.watertreatmentplant_id=t.watertreatmentplant_id "
                      +" and tof.type_of_error_id=el.type_of_error_id "
                      +" and kp.key_person_id=lg.key_person_id "
                      +" and kp.key_person_id="+key_person_id
                      +" and elm.active='Y' "
                      +" And IF('" + searchKeyPerson + "' = '',key_person_name LIKE '%%', key_person_name=?) "
                      +" And IF('" + searchOverheadTank + "' = '',t.name LIKE '%%',t.name=?) ";


      try{

        ps=connection.prepareStatement(query);
         ps.setString(1,searchKeyPerson);
        ps.setString(2,searchOverheadTank);

        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            noOfRows=Integer.parseInt(rs.getString(1));
        }

    }catch(Exception e){
        System.out.println("com.customer.model ErrorViewModel Error"+e);
    }

    return noOfRows;
    }

    public List showData(int lowerLimit, int noOfRowsToDisplay,String searchKeyPerson,String searchOverheadTank,HttpSession session){

        PreparedStatement ps=null;
        int key_person_id=(Integer)session.getAttribute("key_person_id");
        List list = new ArrayList();
        String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        if(lowerLimit == -1){
            addQuery = "";
        }

//         String query= "select error_log_msg_id,wtp.name as name1,t.name,key_person_name,error_name,el.remark,status_name,elm.received "
//               +" from login lg,watertreatmentplant wtp,overheadtank t,overheadtank_keyperson okp,error_log el,error_log_msg elm,status st,key_person kp,type_of_error tof "
//               +" where elm.error_log_id=el.error_log_id "
//               +" and elm.overheadtank_key_person_id=okp.overheadtank_keyperson_id "
//               +" and el.status_id=st.status_id "
//               +" and el.overheadtank_id=t.overheadtank_id "
//               +" and okp.overheadtank_id=t.overheadtank_id "
//               +" and okp.key_person_id=kp.key_person_id "
//               +" and wtp.watertreatmentplant_id=t.watertreatmentplant_id "
//               +" and tof.type_of_error_id=el.type_of_error_id "
//               +" and kp.key_person_id=lg.key_person_id "
//               +" and kp.key_person_id="+key_person_id+""
//               +" and elm.active='Y' "
//               +" And IF('" + searchKeyPerson + "' = '',key_person_name LIKE '%%', key_person_name=?) "
//               +" And IF('" + searchOverheadTank + "' = '',t.name LIKE '%%',t.name=?) "
//               +addQuery;
         String query="select error_log_msg_id,wtp.name as name1,t.name,key_person_name,error_name,el.remark,status_name,elm.received "
                      +" from login lg,watertreatmentplant wtp,overheadtank t,overheadtank_keyperson okp,error_log el,error_log_msg elm,status st,key_person kp,type_of_error tof "
                      +" where elm.error_log_id=el.error_log_id "
                      +" and elm.overheadtank_key_person_id=okp.overheadtank_keyperson_id "
                      +" and el.status_id=st.status_id  and el.overheadtank_id=t.overheadtank_id "
                      +" and okp.overheadtank_id=t.overheadtank_id "
                      +" and wtp.watertreatmentplant_id=t.watertreatmentplant_id "
                      +" and tof.type_of_error_id=el.type_of_error_id "
                      +" and kp.key_person_id=lg.key_person_id "
                      +" and kp.key_person_id="+key_person_id
                      +" and elm.active='Y' "
                      +" And IF('" + searchKeyPerson + "' = '',key_person_name LIKE '%%', key_person_name=?) "
                      +" And IF('" + searchOverheadTank + "' = '',t.name LIKE '%%',t.name=?) "
                      +addQuery;

    try{
           ps=connection.prepareStatement(query);
           ps.setString(1,searchKeyPerson);
           ps.setString(2,searchOverheadTank);
           ResultSet rs  = ps.executeQuery();
           while(rs.next()){
            ErrorView errorView = new ErrorView();
            errorView.setError_log_msg_id(rs.getInt("error_log_msg_id"));
            errorView.setWtpName(rs.getString("name1"));
            errorView.setName(rs.getString("name"));
            errorView.setKey_person_name(rs.getString("key_person_name"));
            errorView.setError_name(rs.getString("error_name"));
            errorView.setRemark(rs.getString("remark"));
            errorView.setError_status_name(rs.getString("status_name"));
            errorView.setMessage_status(rs.getString("received"));

            list.add(errorView);
           }

        }catch(Exception e){
            System.out.println("com.customer.model ErrorViewModel showData() Error"+e);
        }
        return list;
    }

    public List getKeyPerson(String q){

        List<String> li = new ArrayList<String>();
        String query = " select key_person_name "
                        +" from error_log_msg elm,overheadtank_keyperson okp,key_person kp "
                          +" where elm.overheadtank_key_person_id=okp.overheadtank_keyperson_id "
                          +" and okp.key_person_id=kp.key_person_id ";
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
            System.out.println("com.customer.model ErrorViewModel getKeyPerson() Error"+e);
        }
        return li;



    }
    public List getOverheadTank(String q){

        List<String> li = new ArrayList<String>();
        String query = " select name "
                       +" from error_log_msg elm,overheadtank_keyperson okp,overheadtank oht "
                       +" where elm.overheadtank_key_person_id=okp.overheadtank_keyperson_id "
                       +" and okp.overheadtank_id=oht.overheadtank_id ";
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
            System.out.println("com.customer.model ErrorViewModel getOverheadTank() Error"+e);
        }
        return li;


    }

    public void setErrorMsgStatus(int id,String change_command_value){

        PreparedStatement ps=null;
        String query=" update error_log_msg elm"
                + " set received= '"+change_command_value+"' "
                + " where error_log_msg_id=? "
                +" and elm.active='Y' ";

     try{

        ps=connection.prepareStatement(query);
         ps.setInt(1,id);


        int x = ps.executeUpdate();
        if(x>0){
           System.out.println("ErrorMsgStatus update successfully");
        }

    }catch(Exception e){
        System.out.println("com.customer.model ErrorViewModel setErrorMsgStatus() Error"+e);
    }
        int set_status=0;
        if(change_command_value.equals("Received")){
            set_status=2;
        }
        else if(change_command_value.equals("Done")){
               set_status=3;

        }
        String query1="update error_log el,error_log_msg elm "
                      +" set el.status_id= "+set_status
                      +" where elm.error_log_id=el.error_log_id "
                      +" and elm.error_log_msg_id=? "
                      +" and elm.active='Y' ";
        try{

        ps=connection.prepareStatement(query1);
         ps.setInt(1,id);


        int x = ps.executeUpdate();
        if(x>0){
           System.out.println("ErrorMsgStatus update successfully");
        }

    }catch(Exception e){
        System.out.println("com.customer.model ErrorViewModel setErrorMsgStatus() Error"+e);
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

                    System.out.println("com.customer.model ErrorViewModel generatReport() JRException: " + e);
                }
                return reportInbytes;
     }

}
