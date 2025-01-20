package com.servlet.ecom.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    private Long productId;

    private String productName;

    private String productDescription;

    private Integer quantity;

    private double price;

    private Long categoryId;
}
