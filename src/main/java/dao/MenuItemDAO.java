package dao;

import database.MySQLConnection;
import model.MenuItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuItemDAO {

    public void createMenuItem(MenuItem item) {
        String query = "INSERT INTO menu_items (restaurant_id, name, price, description) VALUES (?, ?, ?, ?)";
        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, item.getRestaurantId());
            stmt.setString(2, item.getName());
            stmt.setBigDecimal(3, item.getPrice());
            stmt.setString(4, item.getDescription());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<MenuItem> getMenuItemsByRestaurant(int restaurantId) {
        List<MenuItem> items = new ArrayList<>();
        String query = "SELECT * FROM menu_items WHERE restaurant_id = ?";
        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, restaurantId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                items.add(new MenuItem(
                        rs.getInt("id"),
                        rs.getInt("restaurant_id"),
                        rs.getString("name"),
                        rs.getBigDecimal("price"),
                        rs.getString("description")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
}
