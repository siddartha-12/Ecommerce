package com.ecommerce.demo.controllers;

import com.ecommerce.demo.Entities.Product;
import com.ecommerce.demo.Exceptions.ProductNotFoundException;
import com.ecommerce.demo.dtos.product.AddproductRequestDto;
import com.ecommerce.demo.dtos.product.ProductResponseDto;
import com.ecommerce.demo.services.productservice.ProductService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping("/add")
    public ResponseEntity<ProductResponseDto> addProduct(@RequestBody AddproductRequestDto product) {
        Product product1 = productService.createproduct(product.title,
                product.description,product.image,product.category, product.price);
        return new ResponseEntity<>(ProductResponseDto.createProductResponseDto(product1), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductResponseDto>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending,
            @RequestParam(required = false) String category
    ) {
        List<Product> products = new ArrayList<>();
        if(category == null) {
            products = this.productService.getAllProducts(PageRequest.of(page, size));
        }
        else{
            products = this.productService.getAllProductsByCategory(category, PageRequest.of(page, size));
        }
        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();
        for(Product product:products){
            productResponseDtoList.add(ProductResponseDto.createProductResponseDto(product));
        }
        return new ResponseEntity<>(productResponseDtoList,HttpStatus.OK);
    }

    // Read Product by Index
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable("id") Long id) throws ProductNotFoundException {
        Product product = productService.getProductDetails(id);
        return new ResponseEntity<>(ProductResponseDto.createProductResponseDto(product), HttpStatus.OK);
    }

    // Delete Product
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ProductResponseDto> deleteProduct(@PathVariable("id") Long id) {
        Product product = productService.deleteProduct(id);
        return new ResponseEntity<>(ProductResponseDto.createProductResponseDto(product),HttpStatus.OK);
    }

}
