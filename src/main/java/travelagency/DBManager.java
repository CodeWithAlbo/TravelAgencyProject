/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package travelagency;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBManager {
    // Stoixeia sindeshs me to data base XAMPP MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/travel_agency_db?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Methodos pou epistrefei to antikeimeno sindeshs
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // fotosh poleon apo th MySQL me ta id tous
    public static ArrayList<City> loadCitiesFromDB() throws SQLException {
        ArrayList<City> list = new ArrayList<>();
        String sql = "SELECT * FROM cities";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new City(rs.getInt("id"), rs.getString("name"), rs.getDouble("cost_per_night")));
            }
        }
        return list;
    }

    // INSERT gia ipostiriksi id
    public static void insertCity(int id, String name, double cost) throws SQLException {
        String sql = "INSERT INTO cities (id, name, cost_per_night) VALUES (?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setDouble(3, cost);
            pstmt.executeUpdate();
        }
    }

    public static void updateCity(String name, double newCost) throws SQLException {
        String sql = "UPDATE cities SET cost_per_night = ? WHERE name = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, newCost);
            pstmt.setString(2, name);
            pstmt.executeUpdate();
        }
    }

    public static void deleteCity(String name) throws SQLException {
        String sql = "DELETE FROM cities WHERE name = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name); 
            pstmt.executeUpdate();
        }
    }

    // --- CRUD gia pelates ---
    public static void insertCustomer(String id, String name) throws SQLException {
        String sql = "INSERT INTO customers (id, name) VALUES (?, ?)";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, name);
            pstmt.executeUpdate();
        }
    }

    public static void updateCustomer(String id, String newName) throws SQLException {
        String sql = "UPDATE customers SET name = ? WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newName);
            pstmt.setString(2, id);
            pstmt.executeUpdate();
        }
    }

    public static void deleteCustomer(String id) throws SQLException {
        String sql = "DELETE FROM customers WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        }
    }
}
