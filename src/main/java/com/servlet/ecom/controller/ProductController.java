package com.servlet.ecom.controller;

import com.servlet.ecom.service.ProductService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/api/products")
public class ProductController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ProductService productService;

    public void init(){
        productService = new ProductService();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String productId = request.getParameter("productId");
        String categoryId = request.getParameter("categoryId");
        String jsonResponse;

        if(productId != null){  // Product ID was provided
            jsonResponse = productService.getProductById(Long.parseLong(productId), response);
        }
        else if(categoryId != null){ // Category ID was provided
            jsonResponse = productService.getProductByCategoryId(Long.parseLong(categoryId), response);
        }
        else{ // Both Product ID and Category ID were NOT provided
            jsonResponse = productService.getAllProducts(response);
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);

    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Long productId;

        try{
            productId = Long.parseLong(request.getParameter("productId"));
        }
        catch(Exception e){
            productId = null;
        }

        String jsonResponse = productService.deleteProductById(productId, response);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);
    }

    public void destroy(){

    }


}
