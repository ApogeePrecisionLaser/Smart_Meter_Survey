/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.connection.DBConnection;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
//import org.apache.commons.dbcp.BasicDataSource;

/**
 *
 * @author jpss
 */
public class DBConnection {

    private static BasicDataSource dataSource = null;

    public static Connection getConnection(ServletContext ctx) throws SQLException
    {
        Connection conn = null;
        try {
            Class.forName(ctx.getInitParameter("driverClass"));
            conn = DriverManager.getConnection(ctx.getInitParameter("connectionString"), (String) ctx.getInitParameter("db_username"), (String) ctx.getInitParameter("db_password"));
        } catch (Exception e) {
            System.out.println("DBConncetion getConnection() Error: " + e);
        }
        return conn;
    }
   public static synchronized Connection getConnectionForUtf(ServletContext ctx) throws SQLException {
        Connection conn = null;
        try {
            Class.forName(ctx.getInitParameter("driverClass"));
            conn = (Connection) DriverManager.getConnection((String) ctx.getInitParameter("connectionString") + "?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", (String) ctx.getInitParameter("db_username"), (String) ctx.getInitParameter("db_password"));
        } catch (Exception e) {
            System.out.println(" getConnectionForUtf() Error: " + e);
        }
        return conn;
    }
    public static void closeConncetion(Connection con) {

        try {
            con.close();
        } catch (Exception e) {
            System.out.println("DBConncetion closeConnection() Error: " + e);
        }

    }
}
