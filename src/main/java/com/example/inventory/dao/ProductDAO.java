package com.example.inventory.dao;

import com.example.inventory.model.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    private String jdbcURL = "jdbc:mysql://localhost:3306/inventory_db?useSSL=false&serverTimezone=UTC";
    private String jdbcUsername = "root";
    private String jdbcPassword = "your_mysql_password"; // <-- CHANGE THIS

    private static final String INSERT_PRODUCT_SQL = "INSERT INTO product (name, quantity, price) VALUES (?, ?, ?);";
    private static final String SELECT_PRODUCT_BY_ID = "SELECT id, name, quantity, price FROM product WHERE id = ?;";
    private static final String SELECT_ALL_PRODUCTS = "SELECT * FROM product;";
    private static final String DELETE_PRODUCT_SQL = "DELETE FROM product WHERE id = ?;";
    private static final String UPDATE_PRODUCT_SQL = "UPDATE product SET name = ?, quantity = ?, price = ? WHERE id = ?;";

    protected Connection getConnection() throws SQLException {
        // We need to manually register the driver for web apps
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }
    
    public void addProduct(Product product) throws SQLException {
        try (Connection connection = getConnection(); 
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT_SQL)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2, product.getQuantity());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.executeUpdate();
        }
    }

    public List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PRODUCTS);
             ResultSet rs = preparedStatement.executeQuery()) {
            
            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                int quantity = rs.getInt("quantity");
                double price = rs.getDouble("price");
                products.add(new Product(id, name, quantity, price));
            }
        }
        return products;
    }

    public boolean updateProduct(Product product) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_PRODUCT_SQL)) {
            statement.setString(1, product.getName());
            statement.setInt(2, product.getQuantity());
            statement.setDouble(3, product.getPrice());
            statement.setLong(4, product.getId());
            return statement.executeUpdate() > 0;
        }
    }

    public boolean deleteProduct(long id) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_PRODUCT_SQL)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        }
    }
}