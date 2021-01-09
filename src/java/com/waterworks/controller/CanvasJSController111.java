/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.waterworks.controller;

import com.waterworks.model.CanvasJSModel1;
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

/**
 *
 * @author Administrator
 */
public class CanvasJSController111 extends HttpServlet {
static int ohlevel_id;
   static String searchDate;
  static int energy;
   static String did;
  static String oid;
  static String ohname;
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
        CanvasJSModel1 canvasJSModel=new CanvasJSModel1();
        String task=request.getParameter("task");
        if(task==null||task.isEmpty())
            {
                task="";
            }
         if(task.equals("VIEW_GRAPH2")){
               did = request.getParameter("did");
               oid = request.getParameter("oid");
               ohname = request.getParameter("ohname");
       //   ohlevel_id = Integer.parseInt(request.getParameter("ohlevel_id"));
       //   energy = Integer.parseInt(request.getParameter("energy_id"));
          
        }
        
       
        if(task.equals("Search")){
        String search_level_dateTime=request.getParameter("level_datetime");
try{
        if(!(search_level_dateTime==null)){

            String searchDate1[] = search_level_dateTime.split("-");
            String formatedSearchDate=searchDate1[2]+"-"+searchDate1[1]+"-"+searchDate1[0];

            searchDate=formatedSearchDate;

        }
        }catch(Exception e){
            System.out.println("error on CANVASJSCONTROLLER class ...."+e);
        }
        }
        if(task.equals("Show Current Records")){
            searchDate=null;

        }

        try{
        if(task.equals("getAllDateTime"))
            {
                 JSONObject obj1 = new JSONObject();    
                 JSONArray arrayObj = new JSONArray();
                 JSONArray arrayObj1 = new JSONArray();
               
                arrayObj= canvasJSModel.getAllDateTime(ohlevel_id,searchDate,did);
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
                arrayObj1=canvasJSModel.getAllOhLevel(ohlevel_id,searchDate,did);

                obj1.put("ohLevel", arrayObj1);
               PrintWriter out = response.getWriter();
               out.print(obj1);
                return;
            }
        if(task.equals("getAllSumpwellDateTime"))
            {
                 JSONObject obj1 = new JSONObject();
                 JSONArray arrayObj = new JSONArray();
                 JSONArray arrayObj1 = new JSONArray();

                arrayObj= canvasJSModel.getAllSumpwellDateTime(ohlevel_id,searchDate,oid);
                obj1.put("dateTime", arrayObj);
               PrintWriter out = response.getWriter();
               out.print(obj1);
                return;
            }

        if(task.equals("getAllSumpwellOhLevel"))
            {
                 JSONObject obj1 = new JSONObject();
                 JSONArray arrayObj = new JSONArray();
                 JSONArray arrayObj1 = new JSONArray();
                arrayObj1=canvasJSModel.getAllSumpwellOhLevel(ohlevel_id,searchDate,oid);

                obj1.put("ohLevel", arrayObj1);
               PrintWriter out = response.getWriter();
               out.print(obj1);
                return;
            }


        if(task.equals("getAllEnergyMeterDateTime"))
            {
                 JSONObject obj1 = new JSONObject();
                 JSONArray arrayObj = new JSONArray();
                 JSONArray arrayObj1 = new JSONArray();

                arrayObj= canvasJSModel.getAllEnergyMeterDateTime(energy,searchDate,did);
                obj1.put("dateTime", arrayObj);
               PrintWriter out = response.getWriter();
               out.print(obj1);
                return;
            }

        if(task.equals("getAllEnergyMeterData"))
            {
                 JSONObject obj1 = new JSONObject();
                 JSONArray arrayObj = new JSONArray();
                 JSONArray arrayObj1 = new JSONArray();
                arrayObj1=canvasJSModel.getAllEnergyMeterData(energy,searchDate,did);

                obj1.put("ohLevel", arrayObj1);
               PrintWriter out = response.getWriter();
               out.print(obj1);
                return;
            }



         if(task.equals("getNewDateTime"))
            {
                 JSONObject obj1 = new JSONObject();
                 JSONArray arrayObj = new JSONArray();
                 JSONArray arrayObj1 = new JSONArray();

                arrayObj= canvasJSModel.getNewDateTime(ohlevel_id,searchDate);
                obj1.put("dateTime", arrayObj);
               PrintWriter out = response.getWriter();
               out.print(obj1);
                return;
            }

        if(task.equals("getNewOhLevel"))
            {
                 JSONObject obj1 = new JSONObject();
                 JSONArray arrayObj = new JSONArray();
                 JSONArray arrayObj1 = new JSONArray();
                arrayObj1=canvasJSModel.getNewOhLevel(ohlevel_id,searchDate);

                obj1.put("ohLevel", arrayObj1);
               PrintWriter out = response.getWriter();
               out.print(obj1);
                return;
            }
        Date dtnew = new Date();
    SimpleDateFormat dfnew = new SimpleDateFormat("yy/MM/dd");
    SimpleDateFormat df1new = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
    String cut_dtnew = dfnew.format(dtnew);
    if(searchDate == null || searchDate==""){
       // searchDate=cut_dtnew;
    }else{
    cut_dtnew=searchDate;
    }
request.setAttribute("ohname", ohname);
request.setAttribute("date", cut_dtnew);
            RequestDispatcher rd=request.getRequestDispatcher("/view/waterWorks/watergraph_2.jsp");
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
