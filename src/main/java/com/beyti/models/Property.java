package com.beyti.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class Property extends BaseModel {
    private int id;
    private String address;
    private String city;
    private String state;
    private String postCode;

    private Property(int id, String address, String city, String state, String postCode) {
        this.id = id;
        this.address = address;
        this.city = city;
        this.state = state;
        this.postCode = postCode;
    }

    public static Property create(String address, String city, String state, String postCode) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Property (address, city, state, postCode) VALUES (?, ?, ?, ?)");
            pstmt.setString(1, address);
            pstmt.setString(2, city);
            pstmt.setString(3, state);
            pstmt.setString(4, postCode);
            pstmt.executeUpdate();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT TOP 1 * FROM Property ORDER BY id DESC");
            if(rs.next()) {
                int id = Integer.parseInt(rs.getString("id"));
                return new Property(id, address, city, state, postCode);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public static void update(int id, String address, String city, String state, String postCode) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("UPDATE Property SET address = ?, city = ?, state = ?, postCode = ? WHERE id = ?");
            pstmt.setString(1, address);
            pstmt.setString(2, city);
            pstmt.setString(3, state);
            pstmt.setString(4, postCode);
            pstmt.setInt(5, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void delete(int id) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Property WHERE id = ?");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static LinkedList<Property> get() {
        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Property");
            ResultSet rs = pstmt.executeQuery();
            LinkedList<Property> properties = new LinkedList<Property>();
            while(rs.next()) {
                int id = Integer.parseInt(rs.getString("id"));
                String address = rs.getString("address");
                String city = rs.getString("city");
                String state = rs.getString("state");
                String postCode = rs.getString("postCode");
                properties.add(new Property(id, address, city, state, postCode));
            }
            return properties;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
