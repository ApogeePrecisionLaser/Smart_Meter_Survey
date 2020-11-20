/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.waterworks.controller;

import com.connection.DBConnection.DBConnection;
import java.sql.Connection;
import com.util.UniqueIDGenerator;
import com.waterworks.model.SumpwellOverheadtankModel;
import com.waterworks.tableClasses.SumpwellOverheadtankBean;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Shobha
 */
public class SumpwellOverheadtankController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        //PrintWriter out = response.getWriter();
        ServletContext ctx = getServletContext();
        SumpwellOverheadtankBean bean = new SumpwellOverheadtankBean();
         int lowerLimit = 0, noOfRowsTraversed = 0, noOfRowsToDisplay = 5, noOfRowsInTable = 0;


        try {

            SumpwellOverheadtankModel otkpm = new SumpwellOverheadtankModel();
            try {
            otkpm.setConnection((Connection) DBConnection.getConnectionForUtf(ctx));
        } catch (Exception e) {
            System.out.print(e);
        }

            String sumpwell_overheadtank_id=  request.getParameter("sumpwell_overheadtank_id");
            String overheadtank_name=  request.getParameter("overheadtank_name");
            String sumpwell_name=  request.getParameter("sumpwell_name");
            String junction_name = request.getParameter("junction_name");
            String remark=  request.getParameter("remark");

            bean.setSumpwell_overheadtank_id(sumpwell_overheadtank_id);
            bean.setOverheadtank_name(overheadtank_name);
            bean.setSumpwell_name(sumpwell_name);
            bean.setJunction_name(junction_name);
            bean.setRemark(remark);

            if(sumpwell_overheadtank_id== null){
                sumpwell_overheadtank_id="0";
            }

            String task=request.getParameter("task");
            if(task == null){
                task="";
            }

            String search_overheadtank_name=request.getParameter("search_overheadtank_name");
            String search_sumpwell_name=request.getParameter("search_sumpwell_name");
              if(search_overheadtank_name == null){
                  search_overheadtank_name="";
              }
            if(search_sumpwell_name == null){
                  search_sumpwell_name="";

              }

            if (task.equals("Show All Records")) {
            search_overheadtank_name = "";
            search_sumpwell_name="";
            }
             try {
                //PrintWriter pw = response.getWriter();
                String JQString = request.getParameter("action1");
                String q = request.getParameter("q");

                if (JQString != null) {
                    PrintWriter pw = response.getWriter();
                    List<String> list = null;
                    if (JQString.equals("getSumpwellName")) {
                        String action2 = request.getParameter("action2");
                        if (action2 == null) {
                            action2 = "";
                        }
                        //call getPersonName() method
                        list = otkpm.getSumpwellName(q);
                    }
                    if (JQString.equals("getOverheadTankName")) {
                        String action2 = request.getParameter("action2");
                        if (action2 == null) {
                            action2 = "";
                        }
                        //call getOverheadTankName() method
                        list = otkpm.getOverheadTankName(q);
                    }
                    if (JQString.equals("getJunctionImei_no")) {
                        String action2 = request.getParameter("action2");
                        if (action2 == null) {
                            action2 = "";
                        }
                        //call getOverheadTankName() method
                        list = otkpm.getJunctionImei_no(q);
                    }

                    if (JQString.equals("getSearchSumpwellName")) {
                        String action2 = request.getParameter("action2");
                        if (action2 == null) {
                            action2 = "";
                        }
                        //call getPersonName() method
                        list = otkpm.getSearchSumpwellName(q);
                    }

                    if (JQString.equals("getSearchOverheadTankName")) {
                        String action2 = request.getParameter("action2");
                        if (action2 == null) {
                            action2 = "";
                        }
                        //call getPersonName() method
                        list = otkpm.getSearchOverheadTankName(q);
                    }

                    Iterator<String> itr = list.iterator();
                    while (itr.hasNext()) {
                        String data = itr.next();
                        pw.println(data);
                    }
                    return;
                }
            } catch (Exception e) {
                System.out.println(e);
            }
//           int key_person_id= otkpm.getKeyPersonId(person_name);
//           int overheadtank_id=otkpm.getTankOverheadId(overhead_tank_name);


            if(task.equals("Save")){

//                otkp.setName(overhead_tank_name);
//               otkp.setKey_person_name(person_name);

                otkpm.save(bean);


            }
//            if(task.equals("update") || (task == null ? "delete" == null : task.equals("delete"))){
//             int id=Integer.parseInt(overhead_tank_keyperson_id);
//
//            }
           if(task.equals("Update")){
               //int overheadtank_keyperson_id = Integer.parseInt(overhead_tank_keyperson_id);
               otkpm.update(bean);
           }
           if(task.equals("Delete")){
              //int overheadtank_keyperson_id = Integer.parseInt(overhead_tank_keyperson_id);

              otkpm.delete(bean);
           }

           try{
             if (task.equals("generateHSReport"))
           {
              String jrxmlFilePath;
              List li=null;
              response.setContentType("application/pdf");
              ServletOutputStream servletOutputStream = response.getOutputStream();
              jrxmlFilePath = ctx.getRealPath("/report/waterWorks/OverheadTankKeyPersonReport.jrxml");
              li=otkpm.showData(-1,-1,search_sumpwell_name,search_overheadtank_name);
              byte[] reportInbytes =otkpm.generateRecordList(jrxmlFilePath,li);
              response.setContentLength(reportInbytes.length);
              servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
              servletOutputStream.flush();
              servletOutputStream.close();

              return;
                 }
           }catch(Exception e){
               System.out.println(e);
           }

            String buttonAction = request.getParameter("buttonAction");
            if (buttonAction == null) {
                buttonAction = "none";
            }


           try {
                lowerLimit = Integer.parseInt(request.getParameter("lowerLimit"));
                noOfRowsTraversed = Integer.parseInt(request.getParameter("noOfRowsTraversed"));
            } catch (Exception e) {
                lowerLimit = noOfRowsTraversed = 0;
            }

           noOfRowsInTable = otkpm.getNoOfRows(search_sumpwell_name,search_overheadtank_name);//call getNoOfRows() method

           if (buttonAction.equals("Next")); else if (buttonAction.equals("Previous")) {
               int temp = lowerLimit - noOfRowsToDisplay - noOfRowsTraversed;
                if (temp < 0) {
                    noOfRowsToDisplay = lowerLimit - noOfRowsTraversed;
                    lowerLimit = 0;
                } else {
                    lowerLimit = temp;
                }
            } else if (buttonAction.equals("First")) {
                lowerLimit = 0;
            } else if (buttonAction.equals("Last")) {
                lowerLimit = noOfRowsInTable - noOfRowsToDisplay;
                if (lowerLimit < 0) {
                    lowerLimit = 0;
                }
            }

           if (task.equals("Save") || task.equals("Update") || task.equals("Delete")) {
               lowerLimit = lowerLimit - noOfRowsTraversed;
           }
            //List list = cdm.showData(lowerLimit, noOfRowsToDisplay);
            List list = otkpm.showData(lowerLimit, noOfRowsToDisplay,search_sumpwell_name,search_overheadtank_name);
            lowerLimit = lowerLimit + list.size();
            noOfRowsTraversed = list.size();

            if ((lowerLimit - noOfRowsTraversed) == 0) {
                request.setAttribute("showFirst", "false");
                request.setAttribute("showPrevious", "false");
            }
            if (lowerLimit == noOfRowsInTable) {
                request.setAttribute("showNext", "false");
                request.setAttribute("showLast", "false");
            }


             request.setAttribute("List",list);
             request.setAttribute("lowerLimit", lowerLimit);
             request.setAttribute("message",SumpwellOverheadtankModel.getMessage());
             request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
             request.setAttribute("IDGenerator", new UniqueIDGenerator());
             request.setAttribute("message", SumpwellOverheadtankModel.getMessage());
        request.setAttribute("msgBgColor", otkpm.getMsgBgColor());

             request.getRequestDispatcher("/sumpwell_oht").forward(request, response);
        } finally {
           // out.close();
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
