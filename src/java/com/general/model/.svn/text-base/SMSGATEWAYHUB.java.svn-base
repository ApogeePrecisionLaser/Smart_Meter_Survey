/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.model;

/**
 *
 * @author jpss
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class SMSGATEWAYHUB {

    public static String retval = "";

    public static String SMSSender(String APIKey, String number, String text, String senderid, String fl, String route) {
        String rsp = "";
        try {
            // Construct The Post Data
            String data = URLEncoder.encode("APIKey", "UTF-8") + "=" + URLEncoder.encode(APIKey, "UTF-8");
            data += "&" + URLEncoder.encode("number", "UTF-8") + "=" + URLEncoder.encode(number, "UTF-8");
            data += "&" + URLEncoder.encode("text", "UTF-8") + "=" + URLEncoder.encode(text, "UTF-8");//यात्री XXXXXXX ने वाहन क्र. XXXXXXXXXX से XXXXXXXXXXXXXXX के लिए XXXXXXXXXXXXXXXXXX बजे यात्रा प्रारंभ की है .वाहन चालक का मो.नXXXXXXXXXXशुभ यात्रा
            data += "&" + URLEncoder.encode("senderid", "UTF-8") + "=" + URLEncoder.encode(senderid, "UTF-8");
            data += "&" + URLEncoder.encode("flashsms", "UTF-8") + "=" + URLEncoder.encode(fl, "UTF-8");
            data += "&" + URLEncoder.encode("route", "UTF-8") + "=" + URLEncoder.encode(route, "UTF-8");//Push the HTTP Request
            URL url = new URL("http://login.smsgatewayhub.com/api/mt/SendSMS?");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
            //Read The Response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                // Process line...
                retval += line;
            }
            wr.close();
            rd.close();
            System.out.println(retval);
            rsp = retval;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsp;
    }

    public static void main(String[] args) {
        String sample = "यात्री XXXXXXX ने वाहन क्र. XXXXXXXXXX से XXXXXXXXXXXXXXX के लिए XXXXXXXXXXXXXXXXXX बजे यात्रा प्रारंभ की है .वाहन चालक का मो.नXXXXXXXXXXशुभ यात्रा";
        int sample_len = sample.length();
        String name = "अमित   ";//XXXXXXX 7
        int name_length = name.length();
        String vehicle_no = "MP20R3434 ";//XXXXXXXXXX 10
        String destination = "रेल्वे स्टेशन06";//XXXXXXXXXXXXXXX 15
        int dest_length = destination.length();
        String date_time = "2016-04-21 11:03:0";//XXXXXXXXXXXXXXXXXX 18
        int date_len = date_time.length();
        String contact = "2222222222";//XXXXXXXXXX 10
        String message = "यात्री " + name + " ने वाहन क्र. " + vehicle_no + " से " + destination + " के लिए " + date_time + " बजे यात्रा प्रारंभ की है .वाहन चालक का मो.न" + contact + "शुभ यात्रा";
        int message_len = message.length();
        String response = SMSSender("WIOg7OdIzkmYTrqTsw262w", "7053946948", "यात्री अमित ने वाहन क्र. MP20R3434 से रेल्वे स्टेशन के लिए 2016-04-21 11:03 बजे यात्रा प्रारंभ की है .वाहन चालक का मो.न2222222222शुभ यात्रा", "WEBSMS", "0", "");
        System.out.println(response);
    }
}
