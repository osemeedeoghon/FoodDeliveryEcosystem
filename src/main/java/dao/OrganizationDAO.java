package dao;

import database.MySQLConnection;
import model.Organization;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrganizationDAO {

    public void createOrganization(Organization org) {
        String query = "INSERT INTO organizations (name, type, enterprise_id) VALUES (?, ?, ?)";
        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, org.getName());
            stmt.setString(2, org.getType());
            stmt.setInt(3, org.getEnterpriseId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Organization> getOrganizationsByEnterpriseId(int enterpriseId) {
        List<Organization> orgs = new ArrayList<>();
        String query = "SELECT * FROM organizations WHERE enterprise_id = ?";
        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, enterpriseId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                orgs.add(new Organization(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("type"),
                        rs.getInt("enterprise_id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orgs;
    }
}
