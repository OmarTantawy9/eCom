CREATE TABLE categories (
        category_id BIGINT AUTO_INCREMENT PRIMARY KEY, -- Primary Key
        category_name VARCHAR(255) NOT NULL            -- Category Name
);

CREATE TABLE products (
        product_id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- Primary Key
        product_name VARCHAR(255) NOT NULL,            -- Product Name
        product_description TEXT,                      -- Description
        quantity INT NOT NULL,                         -- Quantity
        price DOUBLE NOT NULL,                         -- Price
        category_id BIGINT,                            -- Foreign Key to Category Table
        CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES categories(category_id)
                    ON DELETE SET NULL                         -- Handle deletions in the parent table
);
