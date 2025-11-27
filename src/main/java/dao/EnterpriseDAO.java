package dao;

import database.MySQLConnection;
import model.Enterprise;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnterpriseDAO {

    public void createEnterprise(Enterprise enterprise) {
        String query = "INSERT INTO enterprises (name, type) VALUES (?, ?)";
        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, enterprise.getName());
            stmt.setString(2, enterprise.getType());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Enterprise> getAllEnterprises() {
        List<Enterprise> enterprises = new ArrayList<>();
        String query = "SELECT * FROM enterprises";
        try (Connection conn = MySQLConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                enterprises.add(new Enterprise(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("type")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return enterprises;
    }
}
