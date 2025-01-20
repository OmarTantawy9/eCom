package com.servlet.ecom.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.servlet.ecom.model.APIResponse;
import com.servlet.ecom.model.Category;
import com.servlet.ecom.repository.CategoryRepository;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final ObjectMapper objectMapper;

    public CategoryService() {
        categoryRepository = new CategoryRepository();
        objectMapper = new ObjectMapper();
    }

    public String getCategoryById(Long categoryId, HttpServletResponse response) throws JsonProcessingException {

        Category category = categoryRepository.getCategoryById(categoryId);
        String jsonResponse;

        if(category != null){ // Category with the specified Category ID was found
            jsonResponse = objectMapper.writeValueAsString(category);
            response.setStatus(HttpServletResponse.SC_FOUND);
        }
        else { // Category with the specified Category ID was NOT found
            jsonResponse = objectMapper.writeValueAsString(
                    APIResponse.builder()
                            .message("Category not found with categoryId: " + categoryId)
                            .status(false)
                            .build()
            );
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return jsonResponse;
    }

    public String getAllCategories(HttpServletResponse response) throws JsonProcessingException {

        List<Category> categories = categoryRepository.getAllCategories();
        String jsonResponse;

        if(categories.isEmpty()){ // There are no Category Records in the Database
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            jsonResponse = objectMapper.writeValueAsString(
                    APIResponse.builder()
                            .message("No Categories found")
                            .status(false)
                            .build()
            );
        }
        else{
            jsonResponse = objectMapper.writeValueAsString(categories);
            response.setStatus(HttpServletResponse.SC_OK);
        }
        return jsonResponse;
    }

    public String deleteCategoryById(Long categoryId, HttpServletResponse response) throws JsonProcessingException {

        String jsonResponse;

        if(categoryId != null){ // Category ID is provided

            boolean isDeleted = categoryRepository.deleteCategoryById(categoryId);

            if(isDeleted){ // Category with the specified Category ID was found and deleted
                response.setStatus(HttpServletResponse.SC_OK);
                jsonResponse = objectMapper.writeValueAsString(
                        APIResponse.builder()
                                .message("Category with categoryId: " + categoryId + " deleted successfully")
                                .status(true)
                                .build()
                );
            }
            else{ // Category with the specified Category ID was NOT found
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                jsonResponse = objectMapper.writeValueAsString(
                        APIResponse.builder()
                                .message("Category not found with categoryId: " + categoryId)
                                .status(false)
                                .build()
                );
            }
        }
        else{ // Category ID was not provided
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            jsonResponse = objectMapper.writeValueAsString(
                    APIResponse.builder()
                            .message("Category id must be provided")
                            .status(false)
                            .build()
            );
        }
        return jsonResponse;
    }
}
