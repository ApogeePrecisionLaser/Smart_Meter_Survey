/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.Webservice.WebService;

import com.Webservice.Model.SmartMeterSurveyServiceModel;
import com.connection.DBConnection.DBConnection;
import java.sql.Connection;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONException;

/**
 *
 * @author Shubham
 */
@Path("/SmartMeterSurveyWebService")
public class SmartMeterSurveyWebService
{
     @Context
    ServletContext serveletContext;
    Connection connection = null;

 @POST
    @Path("/pipeDetailInsert")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String pipeDetail(JSONObject json) throws Exception {
        int affected = 0;
        SmartMeterSurveyServiceModel webServiceModel = new SmartMeterSurveyServiceModel();
        try
        {
            webServiceModel.setConnection(DBConnection.getConnectionForUtf(serveletContext));
             affected  = webServiceModel.insertPipeDetailRecord(json);
        } catch (Exception ex) {
            System.out.println("ERROR : in meterReading() in SmartMeterSurveyWebservices : " + ex);
        }
        
        if (affected > 0) {
            return "success";
        } else {
            return "fail";
        }
    }

}
