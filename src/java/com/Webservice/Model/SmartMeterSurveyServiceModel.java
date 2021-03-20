package com.Webservice.Model;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONException;

/**
 *
 * @author Administrator
 */
public class SmartMeterSurveyServiceModel {

    private static Connection connection;
    DecimalFormat df = new DecimalFormat("0.00");

    public JSONArray getCityList() {
        JSONArray jsonArray = new JSONArray();
        String query = "select city_name from city order by city_name asc ";
        try {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String city_name = rs.getString("city_name");
                jsonArray.put(city_name);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return jsonArray;
    }

    public int registerUser(JSONObject json) {
        int rowAffected = 0, key_person_id = 0;
        try {
            String mobile_no = json.get("mobile_no").toString();
            String query = "insert into key_person (key_person_name,org_office_id,mobile_no1,emp_code,designation_id) "
                    + " values(?,?,?,?,?)";
            String userQuery = "INSERT INTO user (user_name, password, key_person_id) VALUES(?, ?, ?)";
            String selectQuery = "SELECT key_person_id "
                    + " FROM key_person WHERE mobile_no1='" + mobile_no + "' ";

            connection.setAutoCommit(false);
            ResultSet rs = connection.prepareStatement(selectQuery).executeQuery();
            String is_verified = "";
            if (rs.next()) {
                rowAffected = -1;
            } else {
                PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, json.get("name").toString());
                ps.setInt(2, 1);
                ps.setString(3, mobile_no);
                ps.setInt(4, getMaxEmpCode());
                ps.setInt(5, 2);
                ps.executeUpdate();
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    key_person_id = rs.getInt(1);
                }
                if (key_person_id > 0) {
                    PreparedStatement pst = connection.prepareStatement(userQuery);
                    pst.setString(1, json.get("password").toString());
                    pst.setString(2, json.get("password").toString());
                    pst.setInt(3, key_person_id);
                    rowAffected = pst.executeUpdate();
                    if (rowAffected > 0) {
                        connection.commit();
                    } else {
                        connection.rollback();
                    }
                } else {
                    connection.rollback();
                }
            }
        } catch (Exception ex) {
            System.out.println("ERROR: in registerUser in SmartMeterSurveyServiceModel : " + ex);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (Exception ex) {
                System.out.println("ERROR: in commiting connection in registerUser in SmartMeterSurveyServiceModel : " + ex);
            }
        }
        return rowAffected;
    }

    public int verifyMobileNo(String mobile_no) {
        int user_id = 0;
        String query = " SELECT key_person_id FROM key_person WHERE mobile_no1 = '" + mobile_no + "' ";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if (rs.next()) {
                user_id = rs.getInt("key_person_id");
            }
        } catch (Exception ex) {
            System.out.println("ERROR: in verifyMobileNo in SmartMeterSurveyServiceModel : " + ex);
        }
        return user_id;
    }

    public int resetPassword(JSONObject json) {
        int affected = 0;
        try {
            String query = " UPDATE user u, key_person kp SET password='" + json.get("password") + "' WHERE kp.key_person_id=u.key_person_id AND kp.mobile_no1 = '" + json.get("mobile_no") + "' ";

            affected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception ex) {
            System.out.println("ERROR: in resetPassword() in SmartMeterSurveyServiceModel : " + ex);
        }
        return affected;
    }

    public int getMaxEmpCode() {
        int id = 0;
        String query = "SELECT max(emp_code) as emp_code from key_person";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if (rs.next()) {
                id = rs.getInt("emp_code");
                id = id + 1;
            }
        } catch (Exception ex) {
            System.out.println("ERROR : in getKeyPersonId inside SmartMeterSurveyServiceModel : " + ex);
        }
        return id;
    }

    public JSONObject isExits(JSONObject jsonObject) {
        JSONArray jsonArray = new JSONArray();
        try {
            String query = "select u.key_person_id, key_person_name, designation, address_line1, mobile_no1, "
                    + " email_id1 from user as u,key_person as kp "
                    + " where kp.key_person_id=u.key_person_id and kp.mobile_no1='" + jsonObject.get("username").toString() + "' and u.password='" + jsonObject.get("password").toString() + "'";

            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rset = ps.executeQuery();
            if (rset.next()) {
                jsonObject.put("key_person_id", rset.getInt("key_person_id"));
                jsonObject.put("key_person_name", rset.getString("key_person_name"));
                // jsonObject.put("designation", rset.getString("designation"));
                jsonObject.put("mobile_no1", rset.getString("mobile_no1"));
                jsonObject.put("address_line1", rset.getString("address_line1"));
                jsonObject.put("email_id1", rset.getString("email_id1"));
                jsonObject.put("result", "success");
                // jsonArray.put(jsonObject);
            } else {
                jsonObject.put("result", "error");
            }
        } catch (Exception e) {
            // jsonObject.put("result", "error");
            System.out.println(e);
        }
        return jsonObject;
    }

    public JSONArray getMeterDetilData() {
        JSONArray jsonArray = new JSONArray();
        String query = "SELECT kp.key_person_id, kp.key_person_name, kp. mobile_no1, md.meter_detail_id, "
                + " md.meter_no, md.meter_reading, md.latitude, md.longitude "
                + " FROM meter_detail md INNER JOIN key_person kp ON kp.key_person_id=md.key_person_id "
                + " WHERE md.active='Y'";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rset = ps.executeQuery();
            while (rset.next()) {
                try {
                    JSONObject json = new JSONObject();
                    json.put("key_person_id", rset.getInt("key_person_id"));
                    json.put("key_person_name", rset.getString("key_person_name"));
                    json.put("mobile_no", rset.getString("mobile_no1"));
                    json.put("meter_detail_id", rset.getInt("meter_detail_id"));
                    json.put("meter_no", rset.getString("meter_no"));
                    json.put("meter_reading", rset.getString("meter_reading"));
                    json.put("latitude", rset.getString("latitude"));
                    json.put("longitude", rset.getString("longitude"));
                    jsonArray.put(json);
                } catch (JSONException j) {
                }
            }
        } catch (SQLException e) {
            //System.out.println("ERROR: in getMeterDetilData in SmartMeterSurveyServiceModel : " + e);
        }
        return jsonArray;
    }

    public JSONArray getReasons() {
        JSONArray jsonArray = new JSONArray();
        String query = "SELECT reason_id, reason FROM reason ";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rset = ps.executeQuery();
            while (rset.next()) {
                try {
                    JSONObject json = new JSONObject();
                    json.put("reason_id", rset.getInt("reason_id"));
                    json.put("reason", rset.getString("reason"));
                    jsonArray.put(json);
                } catch (JSONException j) {
                }
            }
        } catch (SQLException e) {
            System.out.println("ERROR: in getReasons in SmartMeterSurveyServiceModel : " + e);
        }
        return jsonArray;
    }

    public int insertRecord(String meter_no, String meter_reading, String date_time, String latitude, String longitude, String noOccupants, String reason_id) {
        int rowsAffected = 0;

//        String qry="select * from meter_reading where meter_reading_id=6 ";
//        try{
//            PreparedStatement psmt=connection.prepareStatement(qry);
//            ResultSet rst=psmt.executeQuery();
//            while(rst.next()){
//                System.out.println("get idd - "+rst.getString(3));
//            }
//        }catch(Exception e){
//            System.out.println("com.Webservice.Model.SmartMeterSurveyServiceModel.insertRecord()"+e);
//        }
        String query = "INSERT INTO meter_reading (meter_detail_id, meter_no, meter_reading, date_time, latitude, longitude, number_of_occupants, reason_id) "
                + " VALUES (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            int meter_detail_id = getMeterDetailId(meter_no);
            if (meter_detail_id > 0) {
                ps.setInt(1, meter_detail_id);
            } else {
                ps.setNull(1, java.sql.Types.NULL);
            }
            ps.setString(2, meter_no);
            ps.setString(3, meter_reading);
            ps.setString(4, date_time);
            ps.setString(5, latitude);
            ps.setString(6, longitude);
            ps.setString(7, noOccupants);
            if (reason_id.isEmpty() || reason_id.equals("0")) {
                ps.setNull(8, java.sql.Types.NULL);
            } else {
                ps.setString(8, reason_id);
            }
            rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    rowsAffected = rs.getInt(1);
                }
            }
        } catch (Exception e) {
            System.out.println("insertRecord ERROR inside SmartMeterSurveyServiceModel - " + e);
        }
        return rowsAffected;
    }

    public int insertPipeDetailRecord(JSONObject json) throws SQLException {
        int rowsAffected = 0;
        String query = "INSERT INTO pipe_detail_temp "
                + "( node_id, head_latitude, head_longitude, tail_longitude, tail_lattitude, diameter, diameter_unit, length, length_unit, pipe_type_id, pipe_name) "
                + " VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        try {
//            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(json.get("node_id").toString()));
            ps.setString(2, json.get("header_latitude").toString());
            ps.setString(3, json.get("header_longitude").toString());
            ps.setString(4, json.get("tail_longitude").toString());
            ps.setString(5, json.get("tail_latitude").toString());
            ps.setString(6, json.get("pipe_diameter").toString());
            ps.setString(7, json.get("diameter_unit").toString());
            ps.setString(8, json.get("pipe_length").toString());
            ps.setString(9, json.get("length_unit").toString());
            ps.setInt(10, Integer.parseInt(json.get("pipe_type_id").toString()));
            ps.setString(11, json.get("pipe_type").toString());
            rowsAffected = ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            int id = 0;
            if (rs != null && rs.next()) {
                id = rs.getInt(1);
            }
            if (rowsAffected > 0) {
                try {
                    rowsAffected = 0;
                    String array = json.get("bend").toString();
//                    JSONArray jArray = (JSONArray) json.get("bend");
                    org.json.JSONArray jsonArray = new org.json.JSONArray(array);
                    query = " insert into pipe_bend_temp (latitude, longitude, type_of_bend_id, pipe_detail_temp_id) "
                            + " VALUES (?,?,?,?)";
                    int length = jsonArray.length();
                    for (int i = 0; i < length; i++) {
                        rowsAffected = 0;
                        org.json.JSONObject jObj = jsonArray.getJSONObject(i);
                        ps = connection.prepareStatement(query);
                        ps.setDouble(1, jObj.getDouble("bend_latitude"));
                        ps.setDouble(2, jObj.getDouble("bend_longitude"));
                        ps.setInt(3, jObj.getInt("bend_type_id"));
                        ps.setInt(4, id);
                        rowsAffected = ps.executeUpdate();
                    }

                    if (rowsAffected > 0) {
//                        connection.commit();
                    } else {
//                        connection.rollback();
                    }
                } catch (Exception e) {
//                    connection.rollback();
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            System.out.println("insertRecord ERROR inside SmartMeterSurveyServiceModel - " + e);
        }
//        connection.setAutoCommit(true);
        return rowsAffected;
    }

    public int insertImageRecord(String image_name, int id, int image_upload_for) {
        int rowsAffected = 0;
        DateFormat dateFormat = new SimpleDateFormat("dd.MMMMM.yyyy/ hh:mm:ss aaa");
        Date date = new Date();
        String current_date = dateFormat.format(date);
        String imageQuery = "INSERT INTO general_image_details (image_name, image_destination_id, date_time, description,key_person_id) "
                + " VALUES(?, ?, ?, ?,?)";
        try {
            PreparedStatement pstmt = connection.prepareStatement(imageQuery);
            pstmt.setString(1, image_name);
            pstmt.setInt(2, image_upload_for);
            pstmt.setString(3, current_date);
            pstmt.setString(4, "this file is for complaint");
            pstmt.setInt(5, id);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                rowsAffected = rs.getInt(1);
            }
            pstmt.close();
        } catch (Exception e) {
            System.out.println("Error:keypersonModel--insertRecord-- " + e);
        }
        return rowsAffected;
    }

    public String getDeviceId(String oid) {
        String id = "";
        String query = "SELECT remark FROM overheadtank WHERE overheadtank_id='" + oid + "'";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if (rs.next()) {
                id = rs.getString("remark");
            }
        } catch (Exception ex) {
            System.out.println("Error:keypersonModel--insertRecord-- " + ex);
        }
        return id;
    }

    public int getMeterDetailId(String meter_no) {
        int id = 0;
        String query = "SELECT meter_detail_id FROM meter_detail WHERE meter_no='" + meter_no + "'";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if (rs.next()) {
                id = rs.getInt("meter_detail_id");
            }
        } catch (Exception ex) {
            System.out.println("Error:keypersonModel--insertRecord-- " + ex);
        }
        return id;
    }

    public int getfolderId(String Smart_Meter_Survey) {
        int id = 0;
        String query = "SELECT folder_id FROM folder f,image_uploaded_for i WHERE "
                + " f.image_uploaded_for_id=i.image_uploaded_for_id "
                + " and i.image_uploaded_for='" + Smart_Meter_Survey + "' ";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if (rs.next()) {
                id = rs.getInt("folder_id");
            }
        } catch (Exception ex) {
            System.out.println("Error:keypersonModel--insertRecord-- " + ex);
        }
        return id;
    }

    public int getMeterfolderId(String Meter_Reading_Folder) {
        int id = 0;
        String query = "SELECT folder_id FROM folder f,image_uploaded_for i WHERE "
                + " f.image_uploaded_for_id=i.image_uploaded_for_id "
                + " and i.image_uploaded_for='" + Meter_Reading_Folder + "' ";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if (rs.next()) {
                id = rs.getInt("folder_id");
            }
        } catch (Exception ex) {
            System.out.println("Error:keypersonModel--insertRecord-- " + ex);
        }
        return id;
    }

    public String getDrivefolderId(String Meter_Reading_Folder) {
        String id = "";
        String query = "SELECT drive_folder_id FROM folder f,image_uploaded_for i WHERE "
                + " f.image_uploaded_for_id=i.image_uploaded_for_id "
                + " and i.image_uploaded_for='" + Meter_Reading_Folder + "' ";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if (rs.next()) {
                id = rs.getString("drive_folder_id");
            }
        } catch (Exception ex) {
            System.out.println("Error:keypersonModel--insertRecord-- " + ex);
        }
        return id;
    }

    public int insertfolder(String folder_id, String image_uploaded_for_id) {
        int rowsAffected = 0;
        String query = "INSERT INTO folder (drive_folder_id, image_uploaded_for_id) "
                + " VALUES (?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, folder_id);
            ps.setString(2, image_uploaded_for_id);

            rowsAffected = ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                rowsAffected = rs.getInt(1);
            }

        } catch (Exception e) {
            System.out.println("insertRecord ERROR inside SmartMeterSurveyServiceModel - " + e);
        }
        return rowsAffected;
    }

    public int insertfile(int folder_id, int general_image_details_id, String file_id) {
        int rowsAffected = 0;
        String query = "INSERT INTO drivefile (folder_id, general_image_details_id,file_id) "
                + " VALUES (?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, folder_id);
            ps.setInt(2, general_image_details_id);
            ps.setString(3, file_id);

            rowsAffected = ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("insertRecord ERROR inside SmartMeterSurveyServiceModel - " + e);
        }
        return rowsAffected;
    }

    public String getFileIdFromDatabase(String image_name) {
        String file_id = "";
        String query = "select file_id from general_image_details as g,drivefile as d "
                + " where g.general_image_details_id=d.general_image_details_id and g.image_name='" + image_name + "' ";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if (rs.next()) {
                file_id = rs.getString("file_id");
            }
        } catch (Exception ex) {
            System.out.println("Error:keypersonModel--insertRecord-- " + ex);
        }
        return file_id;
    }

    public JSONObject currentno(int appoinment_id, int doc_availability_info_id) {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        String current_date = dateFormat.format(date);
        JSONObject jsonObject = new JSONObject();
        String query = "select count(appointment_id) as appointment_id from appointment where doc_availability_info_id=" + doc_availability_info_id + " and appointment_date='" + current_date + "'  "
                + " and appointment_status_id=1 and appointment_id<=" + appoinment_id;
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rset = ps.executeQuery();
            while (rset.next()) {
                jsonObject.put("current_appointment_no", rset.getInt("appointment_id"));
                int total = totalAppointment(doc_availability_info_id);
                jsonObject.put("total_appointment", total);
                String status = getDoctorStatus(doc_availability_info_id);
                jsonObject.put("status", status);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return jsonObject;
    }

    public int totalAppointment(int doc_availability_info_id) {
        int total = 0;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        String current_date = dateFormat.format(date);
        String query = "select count(appointment_id) as total from appointment where doc_availability_info_id=" + doc_availability_info_id + " and appointment_date='" + current_date + "' ";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rset = ps.executeQuery();
            while (rset.next()) {
                total = rset.getInt("total");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return total;
    }

    public String getDoctorStatus(int doc_availability_info_id) {
        String status = "";
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        String current_date = dateFormat.format(date);
        String query = "select status from appointment_log where doc_availability_info_id=" + doc_availability_info_id + " and  date='" + current_date + "' ";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rset = ps.executeQuery();
            while (rset.next()) {
                status = rset.getString("status");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return status;
    }

    public JSONArray AppointmentDetail(int patient_id) {

        JSONArray jsonArray = new JSONArray();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        String current_date = dateFormat.format(date);
        String query = "select appointment_id,appointment_date,employee_name,a.doc_availability_info_id,branch_name from appointment as a,doc_availability_info as dai,doctor_info as di ,employee_detail as e,patient_detail as pd ,pathology_branch as pb"
                + "  where a.doc_availability_info_id=dai.doc_availability_info_id and dai.doctor_info_id=di.doctor_info_id and di.pathology_branch_id=pb.pathology_branch_id and di.employee_detail_id=e.employee_detail_id "
                + "  and a.patient_detail_id=pd.patient_detail_id and pd.patient_detail_id=" + patient_id + " and a.appointment_date>='" + current_date + "' and a.appointment_status_id='1' order by appointment_id ";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rset = ps.executeQuery();
            while (rset.next()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("appointment_no", rset.getInt("appointment_id"));
                jsonObject.put("doctor_name", rset.getString("employee_name"));
                String appointment_date = rset.getString("appointment_date").replace("/", "-");
                jsonObject.put("appointment_date", appointment_date);
                jsonObject.put("doc_availability_info_id", rset.getInt("doc_availability_info_id"));
                jsonObject.put("branch_name", rset.getString("branch_name"));
                jsonArray.put(jsonObject);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return jsonArray;
    }

    public JSONArray PreviousAppointmentDetail(int patient_id) {
        JSONArray jsonArray = new JSONArray();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        String current_date = dateFormat.format(date);
        String query = "select appointment_id,appointment_date,employee_name,a.doc_availability_info_id,branch_name from appointment as a,doc_availability_info as dai,doctor_info as di ,employee_detail as e,patient_detail as pd ,pathology_branch as pb"
                + "  where a.doc_availability_info_id=dai.doc_availability_info_id and dai.doctor_info_id=di.doctor_info_id and di.pathology_branch_id=pb.pathology_branch_id and di.employee_detail_id=e.employee_detail_id "
                + "  and a.patient_detail_id=pd.patient_detail_id and pd.patient_detail_id=" + patient_id + " and a.appointment_date<='" + current_date + "' order by appointment_id desc";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rset = ps.executeQuery();
            while (rset.next()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("appointment_no", rset.getInt("appointment_id"));
                jsonObject.put("doctor_name", rset.getString("employee_name"));
                String appointment_date = rset.getString("appointment_date").replace("/", "-");
                jsonObject.put("appointment_date", appointment_date);
                jsonObject.put("doc_availability_info_id", rset.getInt("doc_availability_info_id"));
                jsonObject.put("branch_name", rset.getString("branch_name"));
                jsonArray.put(jsonObject);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return jsonArray;
    }

    public JSONArray getDataPipeType() {
        JSONArray jsonArray = new JSONArray();
        String query = "SELECT pipe_type_id,pipe_type FROM pipe_type order by pipe_type";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rset = ps.executeQuery();
            while (rset.next()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("pipe_type_id", rset.getInt("pipe_type_id"));
                jsonObject.put("pipe_type", rset.getString("pipe_type"));
                jsonArray.put(jsonObject);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return jsonArray;
    }

    public JSONArray getwatertpn() {
        JSONArray jsonArray = new JSONArray();
        String query = "SELECT water.name,water.capacity_mld,ci.city_name,water.address1,water.address2,water.remark, water.date_time, water.latitude, water.longitude "
                + "FROM watertreatmentplant AS water "
                + "LEFT JOIN city AS ci ON water.city_id = ci.city_id "
                + "ORDER BY water.name ";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rset = ps.executeQuery();
            while (rset.next()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", rset.getString("name"));
                jsonObject.put("capacity", rset.getString("capacity_mld"));
                jsonObject.put("city", rset.getString("city_name"));
                jsonObject.put("address1", rset.getString("address1"));
                jsonObject.put("address2", rset.getString("address2"));
                jsonObject.put("date", rset.getString("date_time"));
                jsonObject.put("latitude", rset.getString("latitude"));
                jsonObject.put("longitude", rset.getString("longitude"));

                jsonArray.put(jsonObject);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return jsonArray;
    }

    public JSONArray gettank() {
        JSONArray jsonArray = new JSONArray();
        String query = "SELECT wtp.name,oht.name,oht.capacity_height,oht.capacity_ltr,oht.height,ci.city_name,oht.address1,"
                + " oht.address2,oht.date_time,oht.remark, oht.latitude, oht.longitude "
                + "FROM overheadtank AS oht "
                + "LEFT JOIN watertreatmentplant AS wtp ON oht.watertreatmentplant_id = wtp.watertreatmentplant_id "
                + "LEFT JOIN city AS ci ON oht.city_id = ci.city_id "
                + "ORDER BY oht.name ";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rset = ps.executeQuery();
            while (rset.next()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("watertpname", rset.getString("name"));
                jsonObject.put("tankname", rset.getString("oht.name"));
                jsonObject.put("capacity_ht", rset.getString("capacity_height"));
                jsonObject.put("capacity_ltr", rset.getString("capacity_ltr"));
                jsonObject.put("height", rset.getString("height"));
                jsonObject.put("city", rset.getString("city_name"));
                jsonObject.put("address1", rset.getString("address1"));
                jsonObject.put("address2", rset.getString("address2"));
                jsonObject.put("date", rset.getString("date_time"));
                jsonObject.put("latitude", rset.getString("latitude"));
                jsonObject.put("longitude", rset.getString("longitude"));

                jsonArray.put(jsonObject);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return jsonArray;
    }
    java.util.Date dt = new java.util.Date();
    SimpleDateFormat dfa = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String cut_dt = dfa.format(dt);

    public JSONArray gettanklevel() {
        JSONArray jsonArray = new JSONArray();
        byte[] twoByteData = new byte[2];
        String query = "SELECT le.ohlevel_id,le.overheadtank_id,wtp.name as name1, oht.name,le.level_a,le.level_b,le.date_time,le.remark,\n"
                + " level_datetime,step,level1,level2,level3,level4,oht.capacity_height,oht.type  FROM ohlevel AS le\n"
                + " LEFT JOIN overheadtank AS oht ON le.overheadtank_id = oht.overheadtank_id \n"
                + " LEFT JOIN watertreatmentplant AS wtp ON oht.watertreatmentplant_id = wtp.watertreatmentplant_id \n"
               // + "WHERE  le.date_time like  '" + cut_dt + "%' and type='yes'\n"
                + "WHERE type='yes'\n"

                + " and le.ohlevel_id in(select max(ohlevel_id) from ohlevel group by overheadtank_id)group by overheadtank_id ";
        
//        String query = "select * from (SELECT le.ohlevel_id,le.overheadtank_id,wtp.name as name1,"
//                + " oht.name,le.level_a,le.level_b,le.date_time,le.remark, level_datetime,step,level1,level2,level3,level4,oht.capacity_height,oht.type "
//                + "  FROM ohlevel AS le"
//                + "  LEFT JOIN overheadtank AS oht ON le.overheadtank_id = oht.overheadtank_id"
//                + "  LEFT JOIN watertreatmentplant AS wtp ON oht.watertreatmentplant_id = wtp.watertreatmentplant_id"
//                + "  ORDER BY le.ohlevel_id desc)as a group by name";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rset = ps.executeQuery();
            while (rset.next()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("watertpname", rset.getString("name1"));
                jsonObject.put("tankname", rset.getString("name"));
                jsonObject.put("level_a", rset.getString("level_a"));
                jsonObject.put("level_b", rset.getString("level_b"));
                jsonObject.put("remark", rset.getString("remark"));
                jsonObject.put("date", rset.getString("date_time"));
                jsonObject.put("step", rset.getString("step"));
                jsonObject.put("level1", rset.getString("level1"));
                jsonObject.put("level2", rset.getString("level2"));
                jsonObject.put("level3", rset.getString("level3"));
                jsonObject.put("level4", rset.getString("level4"));
                twoByteData[0] = rset.getByte("level3");
                twoByteData[1] = rset.getByte("level4");
                long voltage1 = (new BigInteger(twoByteData).longValue());
                jsonObject.put("value_of_34", voltage1);
                jsonObject.put("level_in_percent", df.format((voltage1 / (rset.getDouble("capacity_height") * 1000)) * 100));
                jsonObject.put("overheadtank_id", rset.getInt("overheadtank_id"));
                jsonObject.put("oht_type", rset.getString("type"));

                jsonArray.put(jsonObject);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return jsonArray;
    }

    public JSONArray gettanklevelHistory(String overheadtank) {
        String overheadtank_id = overheadtank.split(",")[0];
        String searchDate = "";
        try {
            searchDate = overheadtank.split(",")[1];
        } catch (Exception e) {
            searchDate = "";
        }

        JSONArray jsonArray = new JSONArray();
        byte[] twoByteData = new byte[2];
        String query = " SELECT le.ohlevel_id,le.overheadtank_id,wtp.name as name1,oht.name,le.level_a,"
                + "  le.level_b,le.date_time,le.remark, level_datetime,step,level1,level2,level3,level4,oht.capacity_height"
                + "  FROM ohlevel AS le"
                + "  LEFT JOIN overheadtank AS oht ON le.overheadtank_id = oht.overheadtank_id"
                + "  LEFT JOIN watertreatmentplant AS wtp ON oht.watertreatmentplant_id = wtp.watertreatmentplant_id"
                + "  where le.overheadtank_id='" + overheadtank_id + "' "
                + "  And IF('" + searchDate + "' = '', le.date_time LIKE '%%', le.date_time LIKE'" + searchDate + "%') order by ohlevel_id desc limit 50 ";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rset = ps.executeQuery();
            while (rset.next()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("watertpname", rset.getString("name1"));
                jsonObject.put("tankname", rset.getString("name"));
                jsonObject.put("level_a", rset.getString("level_a"));
                jsonObject.put("level_b", rset.getString("level_b"));
                jsonObject.put("date", rset.getString("date_time"));
                jsonObject.put("remark", rset.getString("remark"));
                jsonObject.put("step", rset.getString("step"));
                jsonObject.put("level1", rset.getString("level1"));
                jsonObject.put("level2", rset.getString("level2"));
                jsonObject.put("level3", rset.getString("level3"));
                jsonObject.put("level4", rset.getString("level4"));
                twoByteData[0] = rset.getByte("level3");
                twoByteData[1] = rset.getByte("level4");
                long voltage1 = (new BigInteger(twoByteData).longValue());
                jsonObject.put("value_of_34", voltage1);
                jsonObject.put("level_in_percent", df.format((voltage1 / (rset.getDouble("capacity_height") * 1000)) * 100));
                jsonObject.put("overheadtank_id", overheadtank_id);
                jsonArray.put(jsonObject);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return jsonArray;
    }

    public JSONArray getMinMaxLevel(String overheadtank_id) {

        String current_date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        JSONArray jsonArray = new JSONArray();
        String query = " SELECT le.overheadtank_id,wtp.name as name1 ,oht.name "
                + "  FROM ohlevel AS le, overheadtank AS oht,watertreatmentplant AS wtp where le.overheadtank_id = oht.overheadtank_id"
                + " and oht.watertreatmentplant_id = wtp.watertreatmentplant_id and le.overheadtank_id='" + overheadtank_id + "' limit 1";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rset = ps.executeQuery();
            if (rset.next()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("watertpname", rset.getString("name1"));
                jsonObject.put("tankname", rset.getString("name"));
                String query4 = "select max(ol.remark) as m_max,min(ol.remark) as m_min  from distribution d,ohlevel ol where d.ohlevel_id=ol.ohlevel_id "
                        + " and ol.overheadtank_id='" + overheadtank_id + "' and "
                        + " ol.date_time between '" + current_date + " 00:01:00' and '" + current_date + " 11:59:59'";
                try {
                    ResultSet rs4 = connection.prepareStatement(query4).executeQuery();
                    if (rs4.next()) {
                        jsonObject.put("m_max", rs4.getString("m_max"));
                        jsonObject.put("m_min", rs4.getString("m_min"));
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
                String query5 = "select max(ol.remark) as e_max,min(ol.remark) as e_min  from distribution d,ohlevel ol where d.ohlevel_id=ol.ohlevel_id "
                        + " and ol.overheadtank_id='" + overheadtank_id + "' and "
                        + " ol.date_time between '" + current_date + " 12:01:00' and '" + current_date + " 23:59:59'";
                try {
                    ResultSet rs5 = connection.prepareStatement(query5).executeQuery();
                    if (rs5.next()) {
                        jsonObject.put("e_max", rs5.getString("e_max"));
                        jsonObject.put("e_min", rs5.getString("e_min"));
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
                jsonArray.put(jsonObject);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return jsonArray;
    }

    public JSONArray getOnOffLevel() {
        byte[] twoByteData = new byte[2];
        JSONArray jsonArray = new JSONArray();
        String query = " select wtp.name as name1,oht.name,ol.remark,ol.ohlevel_id,d.type,ol.date_time,level3,level4,capacity_height,oht.type as overheadtank_type,oht.overheadtank_id "
                + " from watertreatmentplant AS wtp,overheadtank AS oht,"
                + " ohlevel as ol,distribution as d"
                + " where oht.watertreatmentplant_id = wtp.watertreatmentplant_id"
                + " and ol.overheadtank_id = oht.overheadtank_id and ol.ohlevel_id=d.ohlevel_id  order by ol.date_time desc  ";
        try {
            PreparedStatement ps = connection.prepareStatement(query);

            ResultSet rset = ps.executeQuery();
            while (rset.next()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("watertpname", rset.getString("name1"));
                jsonObject.put("tankname", rset.getString("name"));
                jsonObject.put("level", rset.getString("remark"));
                jsonObject.put("type", rset.getString("type"));
                jsonObject.put("date_time", rset.getString("date_time"));
                twoByteData[0] = rset.getByte("level3");
                twoByteData[1] = rset.getByte("level4");
                long voltage1 = (new BigInteger(twoByteData).longValue());
                jsonObject.put("value_of_34", ("" + voltage1).trim());
                jsonObject.put("level_in_percentage", "" + df.format((voltage1 / (rset.getDouble("capacity_height") * 1000)) * 100));
                jsonObject.put("overheadtank_type", rset.getString("overheadtank_type"));
                jsonObject.put("overheadtank_id", rset.getString("overheadtank_id"));
                jsonArray.put(jsonObject);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return jsonArray;
    }

    public JSONArray getOht_sumpwell() {
 
        JSONArray jsonArray = new JSONArray();
        String query = " select overheadtank_id1 as sumpwell_id,overheadtank_id2 as oht_id,junction_id "
                + " from smart_meter_survey.sumpwell_overheadtank s "
                + " where s.active='Y' ";
        try {
            int dsid=getStatusId();
            String waterlevel=getWaterLevel(String.valueOf(dsid));
            String Energylevel=getEnergyLevel(String.valueOf(dsid));
             String[] energylevel = Energylevel.split("_");
             String[] Watlevel = waterlevel.split("_");
           
        Energylevel = energylevel[0];
        waterlevel = Watlevel[0];
      String   sumpwelldate_time = Watlevel[1];
         Energylevel=String.valueOf(Integer.parseInt(Energylevel)/1000);
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
            PreparedStatement ps = connection.prepareStatement(query);

            ResultSet rset = ps.executeQuery();
            while (rset.next()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("sumpwell_id", rset.getString("sumpwell_id"));
                jsonObject.put("oht_id", rset.getString("oht_id"));
                jsonObject.put("junction_id", rset.getInt("junction_id"));
                jsonObject.put("SumpwellTankName", "Ranjhi marghatai Sumpwell");
                jsonObject.put("sumpwell_waterlevel", waterlevel);
                jsonObject.put("totalactivepower",Energylevel);
                jsonObject.put("Cons_energy_mains",Cons_energy_mains);
                jsonObject.put("active_energy_dg",active_energy_dg);
                jsonObject.put("total_active_energy",total_active_energy);
                jsonObject.put("phase_voltage_R",phase_voltage_R);
                jsonObject.put("phase_voltage_Y",phase_voltage_Y);
                jsonObject.put("phase_voltage_B",phase_voltage_B);
                jsonObject.put("phase_current_R",phase_current_R);
                jsonObject.put("phase_current_Y",phase_current_Y);
                jsonObject.put("phase_current_B",phase_current_B);
                jsonObject.put("datetimeenergy",datetimeenergy);
                jsonObject.put("energy_id",energy_id);
                jsonObject.put("sumpwelldate_time",sumpwelldate_time);
                
              

                jsonArray.put(jsonObject);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return jsonArray;
    }
 public String getWaterLevel(String device_status_id) throws SQLException {
        Connection con = null;
       
String status="";
String datetime="";
        String query = "select distinct wd.water_level,wd.date_time from water_data wd where wd.device_status_id='" + device_status_id + "'  order by date_time desc limit 1";

        try {
              PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rset = ps.executeQuery();   
            while (rset.next()) {
                status = rset.getString("water_level");
                datetime = rset.getString("date_time");
              

            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
           // con.close();
        }
        return status + "_" + datetime ;
    }
  public String getEnergyLevel(String device_status_id) throws SQLException {
        Connection con = null;
        PreparedStatement stmt;
        ResultSet rs;
     //   int device_status_id = getWaterStatusId(id);
        // int device_status_id = 5449;
        String total_active_power = "";
        String Cons_energy_mains = "";
        String active_energy_dg = "";
        String total_active_energy = "";
        String phase_voltage_R = "";
        String phase_voltage_Y = "";
        String phase_voltage_B = "";
        String phase_current_R = "";
        String phase_current_Y = "";
        String phase_current_B = "";

        String datetime = "";
        String relay_status = "";
        int energy_id = 0;
        String query = "select distinct wd.total_active_power,wd.Cons_energy_mains,wd.active_energy_dg,wd.total_active_energy,wd.phase_voltage_R,wd.phase_voltage_Y,wd.phase_voltage_B,wd.phase_current_R,phase_current_Y,wd.phase_current_B,wd.date_time,wd.relay_state,wd.energy_data_id from energy_data wd where wd.device_status_id='" + device_status_id + "' order by date_time desc limit 1";

        try {
           stmt = connection.prepareStatement(query);
          
            rs = stmt.executeQuery();
            while (rs.next()) {
                total_active_power = rs.getString("total_active_power");
                Cons_energy_mains = rs.getString("Cons_energy_mains");
                active_energy_dg = rs.getString("active_energy_dg");
                total_active_energy = rs.getString("total_active_energy");
                phase_voltage_R = rs.getString("phase_voltage_R");
                phase_voltage_Y = rs.getString("phase_voltage_Y");
                phase_voltage_B = rs.getString("phase_voltage_B");
                phase_current_R = rs.getString("phase_current_R");
                phase_current_Y = rs.getString("phase_current_Y");
                phase_current_B = rs.getString("phase_current_B");

                datetime = rs.getString("date_time");
                relay_status = rs.getString("relay_state");
                energy_id = rs.getInt("energy_data_id");
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
           // con.close();
        }
        return total_active_power + "_" + Cons_energy_mains + "_" + active_energy_dg + "_" + total_active_energy + "_"
                + phase_voltage_R + "_" + phase_voltage_Y + "_" + phase_voltage_B + "_" + phase_current_R + "_" + phase_current_Y + "_" + phase_current_B
                + "_" + datetime + "_" + relay_status + "_" + energy_id;
    }
 public String getEnergydataLevel(String device_status_id) throws SQLException {
        Connection con = null;
       
String status="";
        String query = "select distinct wd.total_active_power from energy_data wd where wd.device_status_id='" + device_status_id + "'  order by created_date desc limit 1";

        try {
              PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rset = ps.executeQuery();   
            while (rset.next()) {
                status = rset.getString("total_active_power");
              

            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
          //  con.close();
        }
        return status ;
    }
 
    public JSONArray getMeterReading() {

        JSONArray jsonArray = new JSONArray();
//        String query = " Select meter_readings_id,junction_id,connected_load,timestamp "
//                      +" from meter_survey.meter_readings m ";

        String query1 = "Select * from(Select meter_readings_id,m.junction_id,m.connected_load,m.timestamp,j.imei_no "
                + " from meter_survey.meter_readings m,meter_survey.junction j "
                + " ORDER BY meter_readings_id desc ) as a group by a.junction_id ";

        try {
            PreparedStatement ps = connection.prepareStatement(query1);

            ResultSet rset = ps.executeQuery();
            while (rset.next()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("meter_readings_id", rset.getInt("meter_readings_id"));
                jsonObject.put("junction_id", rset.getInt("junction_id"));
                jsonObject.put("connected_load", rset.getDouble("connected_load"));
                jsonObject.put("timestamp", rset.getString("timestamp"));
                jsonObject.put("imei_no", rset.getString("imei_no"));

                jsonArray.put(jsonObject);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return jsonArray;
    }

    public JSONArray getHistory(String meter_no) {
        JSONArray jsonArray = new JSONArray();
        String query = "SELECT meter_reading_id, meter_detail_id, meter_reading, date_time, latitude, longitude FROM meter_reading m where meter_no=?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, meter_no);
            ResultSet rset = ps.executeQuery();
            while (rset.next()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("meter_reading_id", rset.getInt("meter_reading_id"));
                jsonObject.put("meter_detail_id", rset.getInt("meter_detail_id"));
                jsonObject.put("meter_reading", rset.getString("meter_reading"));
                jsonObject.put("date_time", rset.getString("date_time"));
                jsonObject.put("latitude", rset.getString("latitude"));
                jsonObject.put("longitude", rset.getString("longitude"));
                jsonArray.put(jsonObject);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return jsonArray;
    }
public int getStatusId() throws SQLException {
        int vehicle_id = 0;
        Connection con = null;
        String query = "select device_status_id from device_status where active='Y' and device_id='D_20'";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mqtt_server", "root", "root");
            Statement stmt = con.createStatement();
           
            ResultSet rs = stmt.executeQuery(query);
            //System.err.println("ergtrgrtbrtb");
            while (rs.next()) {
                vehicle_id = rs.getInt("device_status_id");
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            con.close();
        }
        return vehicle_id;
    }
    public JSONArray getDataNode() {
        JSONArray jsonArray = new JSONArray();
        String query = "SELECT node_id, node_name, generation_no FROM node n where active='Y' order by node_name";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rset = ps.executeQuery();
            while (rset.next()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("node_id", rset.getInt("node_id"));
                jsonObject.put("node_name", rset.getString("node_name"));
                jsonObject.put("generation_no", rset.getString("generation_no"));
                jsonArray.put(jsonObject);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return jsonArray;
    }

    public JSONArray getDataTree() {
        JSONArray jsonArray = new JSONArray();
        String query = "SELECT tree_id, node_id, node_parent_id, index_no, isSuperChild FROM tree t order by index_no";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rset = ps.executeQuery();
            while (rset.next()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("tree_id", rset.getInt("tree_id"));
                jsonObject.put("node_id", rset.getInt("node_id"));
                jsonObject.put("node_parent_id", rset.getInt("node_parent_id"));
                jsonObject.put("index_no", rset.getString("index_no"));
                jsonObject.put("isSuperChild", rset.getString("isSuperChild"));
                jsonArray.put(jsonObject);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return jsonArray;
    }

    public JSONArray getTypeOfBendData() {
        JSONArray jsonArray = new JSONArray();
        String query = "SELECT type_of_bend_id, type_of_bend FROM smart_meter_survey.type_of_bend t group by type_of_bend";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rset = ps.executeQuery();
            while (rset.next()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("bend_type_id", rset.getInt("type_of_bend_id"));
                jsonObject.put("bend_type", rset.getString("type_of_bend"));
                jsonArray.put(jsonObject);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return jsonArray;
    }

    public void changeMobileStatus(String mobile_no) {
        String query = "update patient_detail set mobile_status='Y' where mobile_no='" + mobile_no + "'";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public String isExits(String curr_latitude, String curr_longtitude) {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
//        jsonObject.put("patient_detail_id", rset.getInt("patient_detail_id"));
//        jsonObject.put("patient_name", rset.getString("patient_name"));
        String query = "SELECT wtp.name,oht.overheadtank_id,oht.name,oht.capacity_height,oht.capacity_ltr,oht.height,"
                + " ci.city_name,oht.address1,oht.address2,oht.date_time,oht.remark, oht.latitude, oht.longitude "
                + "FROM overheadtank AS oht "
                + "LEFT JOIN watertreatmentplant AS wtp ON oht.watertreatmentplant_id = wtp.watertreatmentplant_id "
                + "LEFT JOIN city AS ci ON oht.city_id = ci.city_id "
                + "ORDER BY oht.name ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                String latitude = rset.getString("latitude");
                String longitude = rset.getString("longitude");
                int range = 2;// KM/h
                if ((!latitude.isEmpty() || !longitude.isEmpty()) && (!latitude.startsWith("0.00") && !longitude.startsWith("0.00"))) {
                    double d = distance(Double.parseDouble(curr_latitude), Double.parseDouble(curr_longtitude), Double.parseDouble(latitude), Double.parseDouble(longitude), "K");
                    if (range >= d) {
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.put("overheadtank_id", rset.getInt("overheadtank_id"));
                        jsonObject1.put("name", rset.getString("name"));
                        jsonObject1.put("latitude", rset.getString("latitude"));
                        jsonObject1.put("longitude", rset.getString("longitude"));
                        jsonArray.put(jsonObject1);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error in getAllRecrod -- OverHeadTankModel : " + e);
        }
        try {
            jsonObject.put("data", jsonArray);

        } catch (Exception e) {
        }
        return jsonObject.toString();
    }

    private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == "K") {
            dist = dist * 1.609344;
        } else if (unit == "N") {
            dist = dist * 0.8684;
        }

        return (dist);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
 /*::    This function converts decimal degrees to radians                         :*/
 /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
 /*::    This function converts radians to decimal degrees                         :*/
 /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    public int getPatientId(String mobile_no) {

        int rowsAffected = 0;

        String query = "select patient_detail_id from patient_detail where mobile_no='" + mobile_no + "' ";
        try {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                rowsAffected = rs.getInt("patient_detail_id");
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return rowsAffected;
    }

    public String sendSmsToAssignedFor(String numberStr1, String messageStr1) {
        String result = "";
        try {
            String host_url = "http://login.smsgatewayhub.com/api/mt/SendSMS?";//"http://api.mVaayoo.com/mvaayooapi/MessageCompose?";
            String tempMessage = messageStr1;
            String sender_id = java.net.URLEncoder.encode("TEST SMS", "UTF-8");         // e.g. "TEST+SMS"
            System.out.println("messageStr1 is = " + messageStr1);
            messageStr1 = java.net.URLEncoder.encode(messageStr1, "UTF-8");
            String queryString = "APIKey=WIOg7OdIzkmYTrqTsw262w&senderid=JPSOFT&channel=2&DCS=8&flashsms=0&number=" + numberStr1 + "&text=" + messageStr1 + "&route=";
            String url = host_url + queryString;
            result = callURL(url);
            System.out.println("SMS URL: " + url);
        } catch (Exception e) {
            result = e.toString();
            System.out.println("SMSModel sendSMS() Error: " + e);
        }
        return result;
    }

    public boolean makeDirectory(String dirPathName) {
        boolean result = false;
        File directory = new File(dirPathName);
        if (!directory.exists()) {
            result = directory.mkdir();
        }
        return result;
    }

    public static String random(int size) {
        StringBuilder generatedToken = new StringBuilder();
        try {
            SecureRandom number = SecureRandom.getInstance("SHA1PRNG");
            // Generate 20 integers 0..20
            for (int i = 0; i < size; i++) {
                generatedToken.append(number.nextInt(9));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return generatedToken.toString();
    }

    private String callURL(String strURL) {
        String status = "";
        try {
            java.net.URL obj = new java.net.URL(strURL);
            HttpURLConnection httpReq = (HttpURLConnection) obj.openConnection();
            httpReq.setDoOutput(true);
            httpReq.setInstanceFollowRedirects(true);
            httpReq.setRequestMethod("GET");
            status = httpReq.getResponseMessage();
        } catch (MalformedURLException me) {
            status = me.toString();
        } catch (IOException ioe) {
            status = ioe.toString();
        } catch (Exception e) {
            status = e.toString();
        }
        return status;
    }

    public String dhex(int str) {
        return Integer.toString(str, 16).toUpperCase();
    }

    public String calstr(String str) {// convert unicode to hexadecimal
        int haut = 0;
        String CPstring = "";
        for (int i = 0; i < str.length(); i++) {
            int b = str.charAt(i);
            if (b < 0 || b > 0xFFFF) {
                CPstring += "Error " + dhex(b) + "!";
            }
            if (haut != 0) {
                if (0xDC00 <= b && b <= 0xDFFF) {
                    CPstring += dhex(0x10000 + ((haut - 0xD800) << 10) + (b - 0xDC00)) + ' ';
                    haut = 0;
                    continue;
                } else {
                    CPstring += "!erreur " + dhex(haut) + "!";
                    haut = 0;
                }
            }
            if (0xD800 <= b && b <= 0xDBFF) {
                haut = b;
            } else {
                CPstring += dhex(b) + ' ';
            }
        }
        CPstring = CPstring.substring(0, CPstring.length() - 1);

        String hex = convertCP2HexNCR(CPstring);
        return hex;
    }

    public String convertCP2HexNCR(String argstr) {
        String outputString = "";
        argstr = argstr.replace("\\s+", "");
        if (argstr.length() == 0) {
            return "";
        }
        argstr = argstr.replace("\\s+/g", " ");
        String[] listArray = argstr.split(" ");
        for (int i = 0; i < listArray.length; i++) {
            int n = Integer.parseInt(listArray[i], 16);
            if ((n == 32 || n >= 65 && n <= 90) || (n >= 97 && n <= 122) || (n >= 48 && n <= 57) || n == 45 || n == 46 || n == 58) {
                outputString += "00" + dhex(n);// + ';';
            } else {
                outputString += "0" + dhex(n);// + ';';
            }
        }
        return (outputString);
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println(" closeConnection() Error: " + e);
        }
    }
}
