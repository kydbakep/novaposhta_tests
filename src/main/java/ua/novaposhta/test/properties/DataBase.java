package ua.novaposhta.test.properties;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class DataBase {

    private String DB_URL;
    private String USER;
    private String PASS;
    private Connection conn = null;
    private Statement stmt = null;

    public DataBase(String dbName) throws IOException {
        PropertyLoader property = new PropertyLoader("database");

        String jdbc = "jdbc:mysql://";
        if (dbName.equals("awis")) {
            DB_URL = jdbc + property.load("db.awis.url");
            USER = property.load("db.awis.login");
            PASS = property.load("db.awis.password");
        }
        if (dbName.equals("web")) {
            DB_URL = jdbc + property.load("db.web.url");
            USER = property.load("db.web.login");
            PASS = property.load("db.web.password");
        }

        try {
            //STEP 2: Register JDBC driver
            Class.forName(property.load("db.driver"));
            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public String getPassword(String login) throws SQLException {
        //STEP 4: Execute a query
        System.out.println("Creating statement...");
        stmt = conn.createStatement();
        String sql;
        sql = "select password from catalogvipusers where FirstName = '" + login + "'";
        ResultSet rs = stmt.executeQuery(sql);

        ArrayList<String> list = new ArrayList<>();
        while (rs.next()) {
            list.add(rs.getString("password"));
        }
        rs.close();
        stmt.close();
        conn.close();
        return list.get(0);
    }

    public String getCode(String phoneNumber) throws SQLException {
        System.out.println("Creating statement...");
        stmt = conn.createStatement();
        String sql;
        sql = "SELECT code FROM CatalogCustomerLoyaltyCard WHERE PhoneCustomer = '" + phoneNumber + "'";
        ResultSet rs = stmt.executeQuery(sql);

        ArrayList<String> list = new ArrayList<>();
        while (rs.next()) {
            list.add(rs.getString("code"));
        }
        rs.close();
        stmt.close();
        conn.close();
        return list.get(0);
    }

    public ArrayList<String> getResponse(String cell, String table, String parameter, String value) throws SQLException {
        System.out.println("Creating statement...");
        stmt = conn.createStatement();
        String sql;
        sql = "SELECT " + cell + " FROM " + table + " WHERE " + parameter + " = '" + value + "'";
        ResultSet rs = stmt.executeQuery(sql);
        ArrayList<String> list = new ArrayList<>();
        while (rs.next()) {
            list.add(rs.getString("code"));
        }
        rs.close();
        stmt.close();
        conn.close();
        return list;
    }
}
