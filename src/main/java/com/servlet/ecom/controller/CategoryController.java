package com.servlet.ecom.controller;

import com.servlet.ecom.service.CategoryService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet("/api/categories")
public class CategoryController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private CategoryService categoryService;

    public void init(){
        categoryService = new CategoryService();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String categoryId = request.getParameter("categoryId");
        String jsonResponse;

        if(categoryId != null){ // Category ID is provided
            jsonResponse = categoryService.getCategoryById(Long.parseLong(categoryId), response);
        }
        else{
            jsonResponse = categoryService.getAllCategories(response);
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);
    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Long categoryId;

        try{
            categoryId = Long.parseLong(request.getParameter("categoryId"));
        }
        catch(Exception e){
            categoryId = null;
        }

        String jsonResponse = categoryService.deleteCategoryById(categoryId, response);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);
    }


    public void destroy(){

    }

}
