package dao;

import database.MySQLConnection;
import model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    public void createOrder(Order order) {
        String query = "INSERT INTO orders (customer_id, restaurant_id, status, delivery_address, comment) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, order.getCustomerId());
            stmt.setInt(2, order.getRestaurantId());
            stmt.setString(3, order.getStatus());
            stmt.setString(4, order.getDeliveryAddress());
            stmt.setString(5, order.getComment());
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                order.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateOrderStatus(int orderId, String status, int deliveryManId) {
        String query = "UPDATE orders SET status = ?, delivery_man_id = ? WHERE id = ?";
        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, status);
            if (deliveryManId > 0) {
                stmt.setInt(2, deliveryManId);
            } else {
                stmt.setNull(2, Types.INTEGER);
            }
            stmt.setInt(3, orderId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Order> getOrdersByCustomer(int customerId) {
        return getOrdersByField("customer_id", customerId);
    }

    public List<Order> getOrdersByRestaurant(int restaurantId) {
        return getOrdersByField("restaurant_id", restaurantId);
    }

    public List<Order> getOrdersByDeliveryMan(int deliveryManId) {
        return getOrdersByField("delivery_man_id", deliveryManId);
    }

    private List<Order> getOrdersByField(String field, int value) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE " + field + " = ?";
        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, value);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                orders.add(extractOrderFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    private Order extractOrderFromResultSet(ResultSet rs) throws SQLException {
        return new Order(
                rs.getInt("id"),
                rs.getInt("customer_id"),
                rs.getInt("restaurant_id"),
                rs.getInt("delivery_man_id"),
                rs.getString("status"),
                rs.getTimestamp("order_date"),
                rs.getString("delivery_address"),
                rs.getString("comment"));
    }
}
