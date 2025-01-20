package com.servlet.ecom.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.servlet.ecom.model.APIResponse;
import com.servlet.ecom.model.Product;
import com.servlet.ecom.repository.ProductRepository;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/api/products")
public class ProductController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ProductRepository productRepository;

    private ObjectMapper objectMapper;

    public void init(){
        productRepository = new ProductRepository();
        objectMapper = new ObjectMapper();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String productId = request.getParameter("productId");
        String categoryId = request.getParameter("categoryId");
        String jsonResponse;

        if(productId != null){  // Product ID was provided
            Product product = productRepository.getProductById(Long.parseLong(productId));
            if(product != null){ // Product with the specified Product ID was found
                jsonResponse = objectMapper.writeValueAsString(product);
                response.setStatus(HttpServletResponse.SC_FOUND);
            }
            else{ // Product with the specified Product ID was NOT found
                jsonResponse = objectMapper.writeValueAsString(
                        APIResponse.builder()
                                .message("Product not found with productId: " + productId)
                                .status(false)
                                .build()
                );
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }
        else if(categoryId != null){ // Category ID was provided
            List<Product> products = productRepository.getProductByCategoryId(Long.parseLong(categoryId));
            if(products.isEmpty()){ // No Product found with the provided Category ID
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                jsonResponse = objectMapper.writeValueAsString(
                        APIResponse.builder()
                                .message("Product not found with categoryId: " + categoryId)
                                .status(false)
                                .build()
                );
            }
            else {
                jsonResponse = objectMapper.writeValueAsString(products);
                response.setStatus(HttpServletResponse.SC_FOUND);
            }
        }
        else{ // Both Product ID and Category ID were NOT provided
            List<Product> products = productRepository.getAllProducts();
            if(products.isEmpty()){ // No Product Records found in the Database
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                jsonResponse = objectMapper.writeValueAsString(
                        APIResponse.builder()
                                .message("No Products found")
                                .status(false)
                                .build()
                );
            }
            else {
                jsonResponse = objectMapper.writeValueAsString(products);
                response.setStatus(HttpServletResponse.SC_OK);
            }
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);

    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String productId = request.getParameter("productId");
        String jsonResponse;

        if (productId != null) { // Product ID is provided
            boolean isDeleted = productRepository.deleteProductById(Long.parseLong(productId));
            if (isDeleted) { // Product with the specified Product ID was found and deleted
                response.setStatus(HttpServletResponse.SC_OK);
                jsonResponse = objectMapper.writeValueAsString(
                        APIResponse.builder()
                                .message("Product with productId: " + productId + " deleted successfully")
                                .status(true)
                                .build()
                );
            }
            else { // Product with the specified Product ID was NOT found
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                jsonResponse = objectMapper.writeValueAsString(
                        APIResponse.builder()
                                .message("Product not found with productId: " + productId)
                                .status(false)
                                .build()
                );
            }
        }
        else { // Product ID was not provided
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            jsonResponse = objectMapper.writeValueAsString(
                    APIResponse.builder()
                            .message("Product id must be provided")
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
