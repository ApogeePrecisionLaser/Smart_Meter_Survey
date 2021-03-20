/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.waterworks.controller;

import com.connection.DBConnection.DBConnection;
import com.waterworks.model.CanvasJSModel;
import com.waterworks.model.CanvasJSModel1;
import com.waterworks.model.MobileViewGraphModel;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;

/**
 *
 * @author Shobha
 */
public class MobileViewGraphController extends HttpServlet {
static int ohlevel_id;
   static String searchDate;
  static int energy;
   static String did;
  static String oid;
  static String ohname;
  static String cut_dtnew;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
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
        //ItemName itemNameBean=new ItemName();
       // Map<String, String> map = new HashMap<String, String>();
        response.setContentType("text/html;charset=UTF-8");
         response.setCharacterEncoding("UTF-8");
         request.setCharacterEncoding("UTF-8");
        MobileViewGraphModel canvasJSModel=new MobileViewGraphModel();
        String task=request.getParameter("task");
        if(task==null||task.isEmpty())
            {
                task="";
            }
         if(task.equals("VIEW_GRAPH2")){
               did = request.getParameter("did");
               oid = request.getParameter("oid");
               ohname = request.getParameter("ohname");
               searchDate=request.getParameter("searchDate");
       //   ohlevel_id = Integer.parseInt(request.getParameter("ohlevel_id"));
       //   energy = Integer.parseInt(request.getParameter("energy_id"));
          
        }
          Date dtnew = new Date();
    SimpleDateFormat dfnew = new SimpleDateFormat("yy/MM/dd");
    SimpleDateFormat df1new = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
     cut_dtnew = dfnew.format(dtnew);     
   
    if( searchDate.equals(cut_dtnew)){
        request.setAttribute("date", cut_dtnew);
    searchDate="";
    }else {
     request.setAttribute("date", searchDate);
    }
        // System.out.println("task**************"+task);
       
       
  
       // System.out.println("search date--"+searchDate);
        try{
        if(task.equals("getAllDateTime"))
            {
                 JSONObject obj1 = new JSONObject();    
                 JSONArray arrayObj = new JSONArray();
                 JSONArray arrayObj1 = new JSONArray();
               if(searchDate==null || searchDate=="")
               {       //  System.out.println("search null**************"+searchDate);

                   arrayObj= canvasJSModel.getAllDateTime(ohlevel_id,searchDate,did);
               } else{      
                                       //   System.out.println("search not null**************"+searchDate);

               arrayObj= canvasJSModel.getAllDateTime1(ohlevel_id,searchDate,did);
               }
               obj1.put("dateTime", arrayObj);    
               PrintWriter out = response.getWriter();
               out.print(obj1);
                return;
            }

        if(task.equals("getAllOhLevel"))
            {
                 JSONObject obj1 = new JSONObject();
                 JSONArray arrayObj = new JSONArray();
                 JSONArray arrayObj1 = new JSONArray();  
                  if(searchDate==null || searchDate=="")
               {
                                         //  System.out.println("search level null**************"+searchDate);

                arrayObj1=canvasJSModel.getAllOhLevel(ohlevel_id,searchDate,did);
               }else{
                                           //   System.out.println("search level not null**************"+searchDate);

                    arrayObj1=canvasJSModel.getAllOhLevel1(ohlevel_id,searchDate,did);
                  }
                obj1.put("ohLevel", arrayObj1);
               PrintWriter out = response.getWriter();
               out.print(obj1);
                return;
            }
       
      


      

       


      

        
       
request.setAttribute("ohname", ohname);
  

            RequestDispatcher rd=request.getRequestDispatcher("/view/waterWorks/watergraph_1_1.jsp");
            rd.forward(request, response);
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
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
     *
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
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
