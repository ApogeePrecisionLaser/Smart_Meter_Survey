/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.general.controller;

//import com.general.model.MapDetailClass;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jpss
 */
public class GeneralController extends HttpServlet {
   
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
        try {
            String task = request.getParameter("task");
            if(task == null)
                task = "";
            if (task.equals("showMapWindow"))
            {
                String longi = request.getParameter("logitude");
                String latti = request.getParameter("lattitude");
                request.setAttribute("longi", longi);
                request.setAttribute("latti", latti);
                System.out.println(latti + "," + longi);
                request.getRequestDispatcher("openMapWindowView").forward(request, response);
                return;
            }else if (task.equals("GetCordinates"))
            {
                String longi = request.getParameter("longitude");
                String latti = request.getParameter("latitude");
                if(longi == null || longi.equals("undefined"))
                    longi = "0";
                if(latti == null || latti.equals("undefined"))
                    latti = "0";
                request.setAttribute("longi", longi);
                request.setAttribute("latti", latti);
                System.out.println(latti + "," + longi);
                request.getRequestDispatcher("getCordinate").forward(request, response);
                return;
            }else if (task.equals("GetCordinates1"))
            {
                String longi = request.getParameter("longitude");
                String latti = request.getParameter("latitude");
                if(longi == null || longi.equals("undefined"))
                    longi = "0";
                if(latti == null || latti.equals("undefined"))
                    latti = "0";
                request.setAttribute("longi", longi);
                request.setAttribute("latti", latti);
                System.out.println(latti + "," + longi);
                request.getRequestDispatcher("getCordinate1").forward(request, response);
                return;
            }else if (task.equals("polyline"))
            {
                String longi1 = request.getParameter("longitude1");
                String longi2 = request.getParameter("longitude2");
                String latti1 = request.getParameter("latitude1");
                String latti2 = request.getParameter("latitude2");
                if(longi1 == null || longi1.equals("undefined"))
                    longi1 = "0";
                if(latti1 == null || latti1.equals("undefined"))
                    latti1 = "0";
                if(longi2 == null || longi2.equals("undefined"))
                    longi2 = "0";
                if(latti2 == null || latti2.equals("undefined"))
                    latti2 = "0";
                request.setAttribute("longi1", longi1);
                request.setAttribute("latti1", latti1);
                request.setAttribute("longi2", longi2);
                request.setAttribute("latti2", latti2);
                request.getRequestDispatcher("polyline").forward(request, response);
                return;
            }else if (task.equals("mapRouteView"))
            {
                request.getRequestDispatcher("mapRouteView").forward(request, response);
                return;
            }else if (task.equals("currentCordinate"))
            {
                String longi = request.getParameter("longitude");
                String latti = request.getParameter("latitude");
                if(longi == null || longi.equals("undefined"))
                    longi = "0";
                if(latti == null || latti.equals("undefined"))
                    latti = "0";
                request.setAttribute("longi", longi);
                request.setAttribute("latti", latti);
                System.out.println(latti + "," + longi);
                request.getRequestDispatcher("currentCordinate").forward(request, response);
                return;
            }else if (task.equals("GetBendCordinates"))
            {
                String longi = request.getParameter("longitude");
                String latti = request.getParameter("latitude");
                if(longi == null || longi.equals("undefined"))
                    longi = "0";
                if(latti == null || latti.equals("undefined"))
                    latti = "0";
                request.setAttribute("longi", longi);
                request.setAttribute("latti", latti);
                request.setAttribute("pipe_detail_id", request.getParameter("pipe_detail_id"));
                request.setAttribute("head_latitude", request.getParameter("head_latitude"));
                request.setAttribute("head_longitude", request.getParameter("head_longitude"));
                request.setAttribute("tail_latitude", request.getParameter("tail_latitude"));
                request.setAttribute("tail_longitude", request.getParameter("tail_longitude"));
                System.out.println(latti + "," + longi);
                request.getRequestDispatcher("routeMapView").forward(request, response);
                return;
            }else if (task.equals("mapCordinates"))
            {
                request.getRequestDispatcher("/view/MapView/routeView.jsp").forward(request, response);
                return;
            }else if (task.equals("meterMapCordinates"))
            {
//                request.getRequestDispatcher("meterMapView").forward(request, response);
                request.getRequestDispatcher("/view/MapView/meterMap.jsp").forward(request, response);
                return;
            }else if(task.equals("GetDistance"))
            {
                int disance = 0;//MapDetailClass.getDistance(request.getParameter("source"), request.getParameter("destination"));
                PrintWriter out = response.getWriter();
                out.print(disance);
                out.close();
                return;
            }
        } finally { 
           
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
