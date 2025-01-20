package com.servlet.ecom.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.servlet.ecom.model.APIResponse;
import com.servlet.ecom.model.Product;
import com.servlet.ecom.repository.ProductRepository;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public class ProductService {

    private final ProductRepository productRepository;

    private final ObjectMapper objectMapper;

    public ProductService() {
        productRepository = new ProductRepository();
        objectMapper = new ObjectMapper();
    }

    public String getProductById(Long productId, HttpServletResponse response) throws JsonProcessingException {

        Product product = productRepository.getProductById(productId);
        String jsonResponse;

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
        return jsonResponse;
    }

    public String getProductByCategoryId(Long categoryId, HttpServletResponse response) throws JsonProcessingException {

        List<Product> products = productRepository.getProductByCategoryId(categoryId);
        String jsonResponse;

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

        return jsonResponse;
    }

    public String getAllProducts(HttpServletResponse response) throws JsonProcessingException {

        List<Product> products = productRepository.getAllProducts();
        String jsonResponse;

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

        return jsonResponse;
    }

    public String deleteProductById(Long productId, HttpServletResponse response) throws JsonProcessingException {

        String jsonResponse;

        if (productId != null) { // Product ID is provided
            boolean isDeleted = productRepository.deleteProductById(productId);
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

        return jsonResponse;
    }

}
