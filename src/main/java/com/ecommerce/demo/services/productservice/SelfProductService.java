package com.ecommerce.demo.services.productservice;

import com.ecommerce.demo.Entities.Product;
import com.ecommerce.demo.Exceptions.ProductNotFoundException;
import com.ecommerce.demo.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("selfproductservice")
public class SelfProductService implements ProductService{
    private static final Logger log = LoggerFactory.getLogger(SelfProductService.class);
    private ProductRepository productRepository;
    public SelfProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }
    @Override
    public Product createproduct(String title, String description, String image, String category, double price) {
        Product product = new Product(title,price,description,image,category);
        product = productRepository.save(product);
        return product;
    }

    @Override
    public Product getProductDetails(Long id) throws ProductNotFoundException {
        Optional<Product> product = productRepository.findById(id);
        if(product.isEmpty()){
            throw  new ProductNotFoundException(id);
        }
        return product.get();
    }

    @Override
    public Product deleteProduct(Long id) {
        return null;
    }

    @Override
    public List<Product> getAllProducts(Pageable pageable) {
        Page<Product> page = productRepository.findAll(pageable);
        List<Product> products = page.getContent();
        return products;
    }

    public List<Product> getAllProductsByCategory(String category,Pageable pageable){
        Page<Product> page = productRepository.findAllByCategory(category,pageable);
        List<Product> products = page.getContent();
        return products;
    }
}
