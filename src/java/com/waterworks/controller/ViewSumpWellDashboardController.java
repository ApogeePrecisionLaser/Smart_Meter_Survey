package com.waterworks.controller;

import com.connection.DBConnection.DBConnection;
import com.waterworks.model.DashboardModel;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Com7_2
 */
public class ViewSumpWellDashboardController extends HttpServlet {
  String newdevidcheck="";
    String waterlvlnext="";
    String waterlvlnexttemp="";
    String did1="";
    String oid1="";
    String ohname1="";
    String ohdevicenameidtemp="";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {
        try{
        response.setContentType("text/html;charset=UTF-8");
        //PrintWriter out = response.getWriter();

        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        ServletContext ctx = getServletContext(); 
        DashboardModel vkpm = new DashboardModel();
        HttpSession session = request.getSession();
        String task = request.getParameter("task");
        if (task == null) {
            task = "";
        }

        String status = "";
        String fuel_level = "";
        String fuel_level_litre = "";
        String indicator = "";
        String key_person = "";
        String vehicle_no = "";
        int lowerLimit = 0, noOfRowsTraversed = 0, noOfRowsToDisplay = 10, noOfRowsInTable = 0;
 String device_idfromjsp = request.getParameter("did");
 
   String oht_idfromjsp = request.getParameter("ohid");
   String ohname = request.getParameter("ohname");
   String ohdevicenameid = request.getParameter("ohdevicename");
    if(!device_idfromjsp.equals(newdevidcheck)){
   newdevidcheck=device_idfromjsp;
   waterlvlnext="";
    
   did1=device_idfromjsp;
   oid1=oht_idfromjsp;
   ohname1=ohname;
    
   }
   if(  device_idfromjsp == null){
       
   device_idfromjsp=did1;
   oht_idfromjsp=oid1;
   ohname=ohname1;
     
  
   }else{
   did1=device_idfromjsp;
   oid1=oht_idfromjsp;
   ohname1=ohname;
    ohdevicenameidtemp=ohdevicenameid;
   }
   
   
    
   
   
   
   
   //
        int status_id = vkpm.getStatusId(device_idfromjsp);
        String device_id = vkpm.getDeviceId(status_id);   

        //  status = vkpm.getVehicleStatus(status_id);
        // String idle_running = vkpm.getIdleRunningStatus(status_id);
        //  double voltage = vkpm.getVoltage(status_id);
        String datetime1 = vkpm.getDate();
        String refresh = "";

        //Water Level Data---- Start
        String waterlevel = vkpm.getWaterLevel(device_id);
        String[] waterleveldata = waterlevel.split("_");
        waterlevel = waterleveldata[0];
         String type1=vkpm.getOverheadTankType(device_id);
        int a=vkpm.getOverHeadTankHeight(device_id,type1); 
        if(Integer.parseInt(waterlevel)>a){
          a= Integer.parseInt(waterlevel)-a;
        }else{
      a=a-Integer.parseInt(waterlevel);
        }
        waterlevel=Integer.toString(a);
                     int diff1=0;
                        if(!"".equals(waterlvlnext)){
                            if(Integer.parseInt(waterlvlnext) >=Integer.parseInt(waterlevel)){
                                diff1=Integer.parseInt(waterlvlnext) -Integer.parseInt(waterlevel);
                            }else{
                           diff1= Integer.parseInt(waterlevel) -Integer.parseInt(waterlvlnext);
                            }
                        
                        if(diff1<=500){
                             waterlvlnext=waterlevel;
                        }else{
                        waterlevel=waterlvlnext;
                        }
                        }else{
                        waterlvlnext=waterlevel;
                        }
						
	  int a1=Integer.parseInt(waterlevel)/10;
                 waterlevel=String.valueOf(a1);
        String water_temperature = waterleveldata[1];
        String water_intensity = waterleveldata[2];
        String datetime = waterleveldata[3];
        String relay_state = waterleveldata[4];
        String relay_in_int = relay_state;
        String relay_in_int1 = waterleveldata[5];
        if (relay_state.equals("0")) {
            relay_state = "OFF";

        } else {
            relay_state = "ONN";
        }
        if (relay_in_int1.equals("0")) {
            relay_in_int1 = "OFF";
        } else {
            relay_in_int1 = "ONN";
        }
        String water_id = waterleveldata[5];
        String magnetic_sensor_value = waterleveldata[7];
        //--------------------END

        //Energy Level Data -----Start
        String total_active_power = vkpm.getEnergyLevel(device_id);
        String[] energylevel = total_active_power.split("_");
        total_active_power = energylevel[0];
         total_active_power=String.valueOf(Integer.parseInt(total_active_power)/1000);
        String Cons_energy_mains = energylevel[1];
          Cons_energy_mains=String.valueOf(Integer.parseInt(Cons_energy_mains)/10);
        String active_energy_dg = energylevel[2];
          active_energy_dg=String.valueOf(Integer.parseInt(active_energy_dg)/100);
        String total_active_energy = energylevel[3];
        String phase_voltage_R = energylevel[4];
        String phase_voltage_Y = energylevel[5];
        String phase_voltage_B = energylevel[6];
        String phase_current_R = energylevel[7];
           phase_current_R=String.valueOf(Integer.parseInt(phase_current_R)/100);
        String phase_current_Y = energylevel[8];
           phase_current_Y=String.valueOf(Integer.parseInt(phase_current_Y)/100);
        String phase_current_B = energylevel[9];
           phase_current_B=String.valueOf(Integer.parseInt(phase_current_B)/100);
        String datetimeenergy = energylevel[10];
        String energy_id = energylevel[12];
        //-------------------End

        Date dt = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String cut_dt = df1.format(dt);

//               DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        LocalDateTime datetime = LocalDateTime.parse(cut_dt,formatter);
//                datetime = datetime.m;
//                  String aftersubtraction=datetime.format(formatter);         
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date d1 = null;
        Date d2 = null;

        try {
            d1 = format.parse(cut_dt);
            d2 = format.parse(datetime1);
            int c = 1;

            //in milliseconds
            long diff = d1.getTime() - d2.getTime();

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            //refresh = diffDays+" days "+diffHours+" hours "+diffMinutes+" minutes "+diffSeconds+" seconds ago";
            if (diffDays > 0 && c == 1) {
                refresh = diffDays + " days ago";
                request.setAttribute("refresh", refresh);
                c = 0;

            }
            if (diffHours > 0 && c == 1) {
                refresh = diffHours + " hours ago";
                request.setAttribute("refresh", refresh);

                c = 0;
            }

            if (diffMinutes > 0 && c == 1) {
                refresh = diffMinutes + " minutes ago";
                request.setAttribute("refresh", refresh);
                c = 0;
            }
            if (diffSeconds > 0 && c == 1) {
                refresh = diffSeconds + " seconds ago";
                request.setAttribute("refresh", refresh);
                c = 0;
            }

            System.out.print(diffDays + " days, ");
            System.out.print(diffHours + " hours, ");
            System.out.print(diffMinutes + " minutes, ");
            System.out.print(diffSeconds + " seconds.");

        } catch (Exception e) {

            System.out.println("Exception=" + e);
        }

        String accuracy = vkpm.getAccuracy();
        if (accuracy.equals("")) {
            accuracy = "0";
        }

        double acc = Double.parseDouble(accuracy);
        
//          if (!(acc>=80 && acc<=90))
//          {
//             accuracy = accuracy+" ---------(NOT VALID VALUE)";
//          }

        if (task.equals("check")) {
            String fuel = "";
            String date = "";
            JSONObject obj1 = new JSONObject();
            JSONObject obj2 = new JSONObject();
            JSONObject obj3 = new JSONObject();
            JSONObject obj4 = new JSONObject();
            JSONObject obj5 = new JSONObject();
            JSONObject obj6 = new JSONObject();
            JSONObject obj7 = new JSONObject();
            JSONObject obj8 = new JSONObject();
            JSONObject obj9 = new JSONObject();
            JSONObject obj10 = new JSONObject();
            JSONObject obj11 = new JSONObject();
            JSONObject obj12 = new JSONObject();
            JSONObject obj13 = new JSONObject();
            JSONObject obj14 = new JSONObject();
            JSONObject obj15 = new JSONObject();
            JSONObject obj16 = new JSONObject();
//                String litre = request.getParameter("litre");
//                String vehicle = request.getParameter("vehicle");
            JSONObject data = vkpm.getStatus(fuel_level, refresh, fuel_level_litre,
                    waterlevel, water_temperature, water_intensity, datetime, relay_state, total_active_power,
                    Cons_energy_mains, active_energy_dg, total_active_energy, phase_voltage_R, phase_voltage_Y, phase_voltage_B,
                    phase_current_R, phase_current_Y, phase_current_B, energy_id);
            obj1.put("id", data);
            obj2.put("id2", data);
            obj3.put("id3", data);
            obj4.put("id4", data);
            obj5.put("id5", data);
            obj6.put("id6", data);
            obj7.put("id7", data);
            obj8.put("id8", data);
            obj9.put("id9", data);
            obj10.put("id10", data);
            obj11.put("id11", data);
            obj12.put("id12", data);
            obj13.put("id13", data);
            obj14.put("id14", data);
            obj15.put("id15", data);
            obj16.put("id16", data);
            System.out.println("data  ----- " + data);
            PrintWriter out = response.getWriter();
            out.println(data);
            out.flush();
            return;
            //    request.getRequestDispatcher("/Insert.jsp").forward(request, response);
        }

        if (task.equals("getLatestStatusWithCoordinate")) {
            String latitude = request.getParameter("latitude");
            String longitude = request.getParameter("longitude");
            System.out.println("lati -" + latitude + " -- nlongi--" + longitude);
            String from = request.getParameter("from");
            String To = request.getParameter("To");

 
            request.setAttribute("latitude", latitude);
            request.setAttribute("longitude", longitude);
            //request.getRequestDispatcher("/view/showLatestPreviousCoordinateMap.jsp").forward(request, response);
            request.getRequestDispatcher("/view/device_vehicle_mapping/SupermapwithPreviousCoordinates.jsp").forward(request, response);
            return;

        }

        if (task.equals("Waterrelay")) {
            JSONObject obj = new JSONObject();
            String relay1 = request.getParameter("relay");
            String relay2 = request.getParameter("relay1");
            String waterid = request.getParameter("waterid");

            int relayupdate = vkpm.updaterelay(relay1, waterid, relay2);

            obj.put("relayupdate", relayupdate);

            PrintWriter out = response.getWriter();
            out.println(obj);
            return;
        }

//           if(task.equals("Waterrelay1"))
//        {
//             JSONObject obj = new JSONObject();
//           String relay = request.getParameter("relay1");
//           String waterid = request.getParameter("waterid");
//           
//           int relayupdate = vkpm.updaterelay(relay, waterid);
//           
//           obj.put("relayupdate", relayupdate);
//           
//           PrintWriter out = response.getWriter();
//            out.println(obj);
//           return;
//        }
        if (task.equals("showMapWindow1")) {
            //JSONObject json = areaTypeModel.getLatLonJSONArray();
            //JSONObject json = areaTypeModel.getLatLonJSONArrayLatest();
            JSONObject json = vkpm.showDataBean4(device_id);
            // String result[] = areaTypeModel.getLatLonArray();
            PrintWriter out = response.getWriter();
            out.println(json);
            out.flush();
            return;
        }

        if (task.equals("showMapWindow2")) {
            System.out.println("");
            //JSONObject json = areaTypeModel.getLatLonJSONArray();
            //JSONObject json = areaTypeModel.getLatLonJSONArrayLatest();
            String from = request.getParameter("from");
            String To = request.getParameter("To");
            System.out.println("from -" + from);
            JSONObject json = vkpm.showDataBean5(from, To);
            // String result[] = areaTypeModel.getLatLonArray();
            PrintWriter out = response.getWriter();
            out.println(json);
            out.flush();
            return;
        }

 
        try {
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;

                if (JQstring.equals("getVehicle_code")) {

                    list = vkpm.getVehicle_code(q);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                    out.println(data);
                }
                return;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        String key_person_name = request.getParameter("search_key_person_name");
        if (key_person_name == null) {
            key_person_name = "";
        }
        String vehicle = request.getParameter("search_vehicle_code");
        if (vehicle == null) {
            vehicle = "";
        }
        if (task == null || task.isEmpty()) {
            task = "";
        }
        try {
            if (task.equals("Delete")) {
                vkpm.deleteRecord(Integer.parseInt(request.getParameter("device_vehicle_mapping_id").trim()));
            } else if (task.equals("Save") || task.equals("Save AS New")) {
                int device_vehicle_mapping_id = 0;
                try {
                    device_vehicle_mapping_id = Integer.parseInt(request.getParameter("device_vehicle_mapping_id").trim());
                } catch (Exception ex) {
                    device_vehicle_mapping_id = 0;
                }

                if (task.equals("Save AS New")) {
                    device_vehicle_mapping_id = 0;
                }

                if (device_vehicle_mapping_id > 0) {
                    //  vkpm.updateRecord(vt,device_vehicle_mapping_id);
                } else {
                    //    vkpm.insertRecord(vt);
                }
            } else if (task.equals("Show All Records")) {
                key_person_name = "";
                vehicle = "";
            }
//            String buttonAction = request.getParameter("buttonAction");
//            if (buttonAction == null) {
//                buttonAction = "none";
//            }
//
//            try {
//                lowerLimit = Integer.parseInt(request.getParameter("lowerLimit"));
//                noOfRowsTraversed = Integer.parseInt(request.getParameter("noOfRowsTraversed"));
//            } catch (Exception e) {
//                lowerLimit = noOfRowsTraversed = 0;
//            }
//
//            noOfRowsInTable = vkpm.getNoOfRows(vehicle, key_person_name);
//
//            if (buttonAction.equals("Next")); else if (buttonAction.equals("Previous")) {
//                int temp = lowerLimit - noOfRowsToDisplay - noOfRowsTraversed;
//                if (temp < 0) {
//                    noOfRowsToDisplay = lowerLimit - noOfRowsTraversed;
//                    lowerLimit = 0;
//                } else {
//                    lowerLimit = temp;
//                }
//            } else if (buttonAction.equals("First")) {
//                lowerLimit = 0;
//            } else if (buttonAction.equals("Last")) {
//                lowerLimit = noOfRowsInTable - noOfRowsToDisplay;
//                if (lowerLimit < 0) {
//                    lowerLimit = 0;
//                }
//            }
//            if (task.equals("save") || task.equals("Save AS New") || task.equals("Delete")) {
//                lowerLimit = lowerLimit - noOfRowsTraversed;
//            }
//
//            List<DashboardController> list = vkpm.showData(lowerLimit, noOfRowsToDisplay, vehicle, key_person_name);
//
//            lowerLimit = lowerLimit + list.size();
//            noOfRowsTraversed = list.size();
//            if ((lowerLimit - noOfRowsTraversed) == 0) {
//                request.setAttribute("showFirst", "false");
//                request.setAttribute("showPrevious", "false");
//            }
//            if (lowerLimit == noOfRowsInTable) {
//                request.setAttribute("showNext", "false");
//                request.setAttribute("showLast", "false");
//            }
            // request.setAttribute("list", list);

//          vkpm.getStatus(fuel_level, voltage, refresh, fuel_level_litre,
//                    waterlevel,water_temperature,water_intensity,datetime,relay_state);
            request.setAttribute("datetime", datetime1);
          //  request.setAttribute("datetimetemp", datetimetemp);
            request.setAttribute("device_idfromjsp", device_idfromjsp);
            request.setAttribute("oht_idfromjsp", oht_idfromjsp);
            request.setAttribute("ohname", ohname);

            request.setAttribute("waterlevel", waterlevel);
         //   request.setAttribute("waterleveltemp", waterleveltemp);
            request.setAttribute("water_intensity", water_intensity);
            request.setAttribute("water_temperature", water_temperature);
            request.setAttribute("watertime", datetime);
            request.setAttribute("relaystate", relay_state);
            request.setAttribute("relay_in_int", relay_in_int);
            request.setAttribute("relay_in_int1", relay_in_int1);
            request.setAttribute("waterid", water_id);
            request.setAttribute("magnetic_sensor_value", magnetic_sensor_value);

            request.setAttribute("total_active_power", total_active_power);
            request.setAttribute("Cons_energy_mains", Cons_energy_mains);
            request.setAttribute("active_energy_dg", active_energy_dg);
            request.setAttribute("total_active_energy", total_active_energy);
            request.setAttribute("phase_voltage_R", phase_voltage_R);
            request.setAttribute("phase_voltage_Y", phase_voltage_Y);
            request.setAttribute("phase_voltage_B", phase_voltage_B);
            request.setAttribute("phase_current_R", phase_current_R);
            request.setAttribute("phase_current_Y", phase_current_Y);
            request.setAttribute("phase_current_B", phase_current_B);
            request.setAttribute("energytime", datetimeenergy);
            request.setAttribute("energyid", energy_id);

            request.setAttribute("lowerLimit", lowerLimit);
            // request.setAttribute("vehicleType", vehicleType);
            request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);

            request.setAttribute("message", vkpm.getMessage());

            RequestDispatcher rd = request.getRequestDispatcher("/view/waterWorks/dashboard_2.jsp");
            // RequestDispatcher rd = request.getRequestDispatcher("/view/login/dashboard_new_1.jsp");
            //       RequestDispatcher rd = request.getRequestDispatcher("/view/login/dashboard_new.jsp");
            rd.forward(request, response);
        } catch (Exception e) {
            System.out.println(e);
            //out.close();
        }
        } catch (Exception e) {
            System.out.println(e);
            RequestDispatcher rd = request.getRequestDispatcher("/view/waterWorks/error500.jsp");
              rd.forward(request, response);
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

        try {
            processRequest(request, response);

        } catch (ParseException ex) {
            Logger.getLogger(ViewSumpWellDashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }

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
        try {
            response.setContentType("text/html;charset=UTF-8");
            //PrintWriter out = response.getWriter();

            response.setContentType("text/html;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            request.setCharacterEncoding("UTF-8");
            ServletContext ctx = getServletContext();
            DashboardModel vkpm = new DashboardModel();
            try {

                vkpm.setConnection((Connection) DBConnection.getConnectionForUtf(ctx));
            } catch (Exception e) {
                System.out.print(e);
            }
            String task = request.getParameter("task");

            if (task.equals("view")) {
                view(request, response);
            }

            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(ViewSumpWellDashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void view(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext ctx = getServletContext();
        DashboardModel vkpm = new DashboardModel();

        String task = request.getParameter("task");

        String status = "";
        String fuel_level = "";
        String fuel_level_litre = "";
        String indicator = "";
        String key_person = "";
        String vehicle_no = "";

        try {

            vkpm.setConnection((Connection) DBConnection.getConnectionForUtf(ctx));
//        } 
//        catch (Exception e) {
//            System.out.print(e);
//        }
 String device_idfromjsp = request.getParameter("did");
   String oht_idfromjsp = request.getParameter("ohid");
   
            int status_id = vkpm.getStatusId(device_idfromjsp);

            String device_id = vkpm.getDeviceId(status_id);
            status = vkpm.getVehicleStatus(status_id);
            String idle_running = vkpm.getIdleRunningStatus(status_id);
            double voltage = vkpm.getVoltage(status_id);

            fuel_level = vkpm.getFuellevel(status_id);
            fuel_level_litre = vkpm.getFuellevellitre(status_id);
            indicator = vkpm.getFuelIndicator(status_id);
            vehicle_no = vkpm.getVehicleno((device_id));
            key_person = vkpm.getKeyPerson((vehicle_no));
            String exception = vkpm.getException(vehicle_no);
            String model = vkpm.getModel();
            String manufacturer = vkpm.Manufacturer(model);
            String datetime1 = vkpm.getDate();
            String refresh = "";

            String accuracy = vkpm.getAccuracy();

            ArrayList<ArrayList<String>> al1 = vkpm.getData();

            // if (task.equals("view")) {
            String fuel = "";
            String date = "";
            JSONObject obj1 = new JSONObject();
            JSONObject obj2 = new JSONObject();
            JSONObject obj3 = new JSONObject();
            JSONObject obj4 = new JSONObject();
            JSONObject obj5 = new JSONObject();
            JSONObject obj6 = new JSONObject();
            JSONObject obj7 = new JSONObject();
            JSONObject obj8 = new JSONObject();
            JSONObject obj9 = new JSONObject();
            JSONObject obj10 = new JSONObject();
            JSONObject obj11 = new JSONObject();
            JSONObject obj12 = new JSONObject();
//                String litre = request.getParameter("litre");
//                String vehicle = request.getParameter("vehicle");
            JSONObject data = vkpm.getStatus1(fuel_level, voltage, refresh, fuel_level_litre, model, manufacturer, vehicle_no, key_person);
            obj1.put("id", data);
            obj2.put("id2", data);
            obj3.put("id3", data);
            obj4.put("id4", data);
            obj5.put("id5", data);
            obj6.put("id6", data);
            obj7.put("id7", data);
            obj8.put("id8", data);
            obj9.put("id9", data);
            obj10.put("id10", data);
            obj11.put("id11", data);
            obj12.put("id12", data);

            PrintWriter out = response.getWriter();
            out.println(data);       
            out.flush();
            return;
            //    request.getRequestDispatcher("/Insert.jsp").forward(request, response);
            // }

//        try {
//
//            RequestDispatcher rd = request.getRequestDispatcher("/view/login/dashboadversion.jsp");
//            // RequestDispatcher rd = request.getRequestDispatcher("/view/login/dashboard_new_1.jsp");
//            //       RequestDispatcher rd = request.getRequestDispatcher("/view/login/dashboard_new.jsp");
//            rd.forward(request, response);
        } catch (Exception e) {
            System.out.println(e);
            //out.close();
        }
        RequestDispatcher rd = request.getRequestDispatcher("/view/login/dashboadversion.jsp");
        rd.forward(request, response);

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
