package com.servlet.ecom.repository;

import com.servlet.ecom.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {


    public Product getProductById(Long productId) {

        try{
            Product product = null;
            Connection connection = Database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from products where product_id = ?"
            );
            preparedStatement.setLong(1, productId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                product = Product.builder()
                        .productId(resultSet.getLong("product_id"))
                        .productName(resultSet.getString("product_name"))
                        .productDescription(resultSet.getString("product_description"))
                        .quantity(resultSet.getInt("quantity"))
                        .price(resultSet.getDouble("price"))
                        .categoryId(resultSet.getObject("category_id", Long.class))
                        .build();
            }
            connection.close();
            return product;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Product> getProductByCategoryId(Long categoryId) {

        List<Product> products = new ArrayList<>();

        try{
            Connection connection = Database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from products where category_id = ?"
            );
            preparedStatement.setLong(1, categoryId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return getProducts(products, connection, resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        try{
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from products");
            return getProducts(products, connection, resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Helper Function to retrieve the Products from the ResultSet
    private List<Product> getProducts(List<Product> products, Connection connection, ResultSet resultSet) throws SQLException {
        Product product;
        while(resultSet.next()) {
            product = Product.builder()
                    .productId(resultSet.getLong("product_id"))
                    .productName(resultSet.getString("product_name"))
                    .productDescription(resultSet.getString("product_description"))
                    .quantity(resultSet.getInt("quantity"))
                    .price(resultSet.getDouble("price"))
                    .categoryId(resultSet.getObject("category_id", Long.class))
                    .build();
            products.add(product);
        }
        connection.close();
        return products;
    }

    public boolean deleteProductById(Long productId) {
        try{
            Connection connection = Database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "delete from products where product_id = ?"
            );
            preparedStatement.setLong(1, productId);
            int deletedRows = preparedStatement.executeUpdate();
            connection.close();
            return deletedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
