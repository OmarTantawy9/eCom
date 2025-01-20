package com.servlet.ecom.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.servlet.ecom.model.APIResponse;
import com.servlet.ecom.model.Category;
import com.servlet.ecom.repository.CategoryRepository;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;


@WebServlet("/api/categories")
public class CategoryController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private CategoryRepository categoryRepository;

    private ObjectMapper objectMapper;

    public void init(){
        categoryRepository = new CategoryRepository();
        objectMapper = new ObjectMapper();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String categoryId = request.getParameter("categoryId");
        String jsonResponse;

        if(categoryId != null){ // Category ID is provided
            Category category = categoryRepository.getCategoryById(Long.parseLong(categoryId));
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
        }
        else{
            List<Category> categories = categoryRepository.getAllCategories();
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

        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);
    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String categoryId = request.getParameter("categoryId");
        String jsonResponse;

        if(categoryId != null){ // Category ID is provided
            boolean isDeleted = categoryRepository.deleteCategoryById(Long.parseLong(categoryId));
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

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);
    }


    public void destroy(){

    }

}
