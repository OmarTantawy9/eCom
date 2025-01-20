package com.servlet.ecom.repository;

import com.servlet.ecom.model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {

    public Category getCategoryById(Long categoryId) {

        try{
            Category category = null;
            Connection connection = Database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from categories where category_id = ?"
            );
            preparedStatement.setLong(1, categoryId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                category = Category.builder()
                        .categoryId(resultSet.getLong("category_id"))
                        .categoryName(resultSet.getString("category_name"))
                        .build();
            }
            connection.close();
            return category;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();

        try{
            Category category;
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from categories");
            while(resultSet.next()) {
                category = Category.builder()
                        .categoryId(resultSet.getLong("category_id"))
                        .categoryName(resultSet.getString("category_name"))
                        .build();
                categories.add(category);
            }
            connection.close();
            return categories;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean deleteCategoryById(Long categoryId) {

        try{
            Connection connection = Database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "delete from categories where category_id = ?"
            );
            preparedStatement.setLong(1, categoryId);
            int deletedRows = preparedStatement.executeUpdate();
            connection.close();
            return deletedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
