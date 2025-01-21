package com.ecommerce.demo.services.productservice;

import com.ecommerce.demo.Entities.Product;
import com.ecommerce.demo.Exceptions.ProductNotFoundException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    Product createproduct(String title, String description, String image,
                          String category, double price);

    public Product getProductDetails(Long id) throws ProductNotFoundException;
    public Product deleteProduct(Long id);
    public List<Product> getAllProducts(Pageable pageable);
    List<Product> getAllProductsByCategory(String category, Pageable pageable);
}
