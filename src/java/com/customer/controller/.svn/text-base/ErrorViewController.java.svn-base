/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.customer.controller;

import com.connection.DBConnection.DBConnection;
import com.customer.model.ErrorViewModel;
import com.util.UniqueIDGenerator;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
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
 * @author Com7_2
 */
public class ErrorViewController extends HttpServlet {
   
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
       // PrintWriter out = response.getWriter();

         ServletContext ctx = getServletContext();
          int lowerLimit = 0, noOfRowsTraversed = 0, noOfRowsToDisplay = 5, noOfRowsInTable = 0;

          ErrorViewModel errorViewModel = new ErrorViewModel();
          HttpSession session = request.getSession(false);
        if (session == null) {
            request.getRequestDispatcher("/beforeLoginHomeView").forward(request, response);
            return;
        }

        try {

           try {
            ErrorViewModel.setConnection((Connection) DBConnection.getConnectionForUtf(ctx));
        } catch (Exception e) {
            System.out.print("com.customer.controller ErrorViewController DBConnection Error "+e);
        }


            String searchKeyPerson=request.getParameter("search_key_person");
            String searchOverheadTank=request.getParameter("search_overhead_tank");

              if(searchKeyPerson == null){
                  searchKeyPerson="";
              }
            if(searchOverheadTank == null){
                  searchOverheadTank="";

              }

            try {
               // PrintWriter pw = response.getWriter();
                String JQString = request.getParameter("action1");
                String q = request.getParameter("q");

                if (JQString != null) {
                     PrintWriter pw = response.getWriter();
                    List<String> list = null;
                    if (JQString.equals("getKeyPerson")) {
                        String action2 = request.getParameter("action2");
                        if (action2 == null) {
                            action2 = "";
                        }
                        //call getPriority() method
                        list = errorViewModel.getKeyPerson(q);
                    }
                    if (JQString.equals("getOverheadTank")) {
                        String action2 = request.getParameter("action2");
                        if (action2 == null) {
                            action2 = "";
                        }
                        //call getDesignation() method
                        list = errorViewModel.getOverheadTank(q);
                    }
                 if (JQString.equals("setErrorMsg")) {

                      int id=Integer.parseInt(request.getParameter("error_log_msg_id"));
                      String change_command_value=request.getParameter("change_command_value");
                      errorViewModel.setErrorMsgStatus(id,change_command_value);

                        return;
                    }


                    Iterator<String> itr = list.iterator();
                    while (itr.hasNext()) {
                        String data = itr.next();
                        pw.println(data);
                    }
                    return;
                }
            } catch (Exception e) {
                System.out.println("com.customer.controller ErrorViewController Error "+e);
            }
            String task=request.getParameter("task");
            if(task == null){
                task="";
            }
            if (task.equals("Show All Records")) {
            searchKeyPerson = "";
            searchOverheadTank="";
            }

                
             try{
             if (task.equals("generateHSReport"))
           {
              String jrxmlFilePath;
              List li=null;
              response.setContentType("application/pdf");
              ServletOutputStream servletOutputStream = response.getOutputStream();
              jrxmlFilePath = ctx.getRealPath("/report/customer/ErrorViewReport.jrxml");
              li=errorViewModel.showData(-1,-1,searchKeyPerson,searchOverheadTank,session);
              byte[] reportInbytes =errorViewModel.generateRecordList(jrxmlFilePath,li);
              response.setContentLength(reportInbytes.length);
              servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
              servletOutputStream.flush();
              servletOutputStream.close();

              return;
                 }
           }catch(Exception e){
               System.out.println("com.customer.controller ErrorViewController generateHSReport Error "+e);
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

           noOfRowsInTable = errorViewModel.getNoOfRows(searchKeyPerson,searchOverheadTank,session);//call getNoOfRows() method

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

           List list = errorViewModel.showData(lowerLimit,noOfRowsToDisplay,searchKeyPerson,searchOverheadTank,session);
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

           // List list = otkpm.showData();

             request.setAttribute("List",list);
             request.setAttribute("lowerLimit", lowerLimit);
             request.setAttribute("searchKeyPerson", searchKeyPerson);
             request.setAttribute("searchOverheadTank", searchOverheadTank);
             //request.setAttribute("message", TypeOfErrorModel.getMessage());
             request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
             request.setAttribute("IDGenerator", new UniqueIDGenerator());




            request.getRequestDispatcher("errorView").forward(request, response);
        } finally { 
            //out.close();
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
