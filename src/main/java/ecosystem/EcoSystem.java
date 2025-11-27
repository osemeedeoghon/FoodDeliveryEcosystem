package ecosystem;

import dao.*;
import model.*;

import java.util.List;

public class EcoSystem {
    private static EcoSystem instance;
    private UserDAO userDAO;
    private EnterpriseDAO enterpriseDAO;
    private OrganizationDAO organizationDAO;
    private OrderDAO orderDAO;
    private MenuItemDAO menuItemDAO;

    private User currentUser;

    private EcoSystem() {
        userDAO = new UserDAO();
        enterpriseDAO = new EnterpriseDAO();
        organizationDAO = new OrganizationDAO();
        orderDAO = new OrderDAO();
        menuItemDAO = new MenuItemDAO();
    }

    public static EcoSystem getInstance() {
        if (instance == null) {
            instance = new EcoSystem();
        }
        return instance;
    }

    // User Management
    public User login(String username, String password) {
        User user = userDAO.authenticate(username, password);
        if (user != null) {
            currentUser = user;
        }
        return user;
    }

    public void logout() {
        currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void createUser(User user) {
        userDAO.createUser(user);
    }

    // Enterprise Management
    public void createEnterprise(String name, String type) {
        enterpriseDAO.createEnterprise(new Enterprise(0, name, type));
    }

    public List<Enterprise> getAllEnterprises() {
        return enterpriseDAO.getAllEnterprises();
    }

    // Organization Management
    public void createOrganization(String name, String type, int enterpriseId) {
        organizationDAO.createOrganization(new Organization(0, name, type, enterpriseId));
    }

    public List<Organization> getOrganizations(int enterpriseId) {
        return organizationDAO.getOrganizationsByEnterpriseId(enterpriseId);
    }

    // Order Management
    public void placeOrder(Order order) {
        orderDAO.createOrder(order);
    }

    public List<Order> getOrdersForCustomer(int customerId) {
        return orderDAO.getOrdersByCustomer(customerId);
    }

    public List<Order> getOrdersForRestaurant(int restaurantId) {
        return orderDAO.getOrdersByRestaurant(restaurantId);
    }

    public List<Order> getOrdersForDeliveryMan(int deliveryManId) {
        return orderDAO.getOrdersByDeliveryMan(deliveryManId);
    }

    public void updateOrderStatus(int orderId, String status, int deliveryManId) {
        orderDAO.updateOrderStatus(orderId, status, deliveryManId);
    }

    // Menu Management
    public void addMenuItem(MenuItem item) {
        menuItemDAO.createMenuItem(item);
    }

    public List<MenuItem> getMenu(int restaurantId) {
        return menuItemDAO.getMenuItemsByRestaurant(restaurantId);
    }
}
