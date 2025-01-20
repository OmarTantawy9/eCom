package com.servlet.ecom.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data   // Lombok Auto-Generated ToString, EqualsAndHashCode, Getter and Setter, and RequiredArgsConstructor
@NoArgsConstructor  // Lombok Auto-Generated NoArgsConstructor
@AllArgsConstructor // Lombok Auto-Generated AllArgsConstructor
@Builder    // Lombok Builder
public class Category {

    private Long categoryId;

    private String categoryName;

}
