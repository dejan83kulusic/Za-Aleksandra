package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Product;
import com.example.service.ProductService;

@RestController
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping("/products")
	public ResponseEntity<List<Product>> getAllProducts() {
		return ResponseEntity.ok().body(productService.getAllProduct());
	}
	@GetMapping("/products/{id}")
	public ResponseEntity<Product> getAllProducts(@PathVariable Long id) {
		return ResponseEntity.ok().body(productService.getProductById(id));
	}
	@PostMapping("/products")
	public ResponseEntity<Product> createProduct(@RequestBody Product product) {
		return ResponseEntity.ok().body(productService.createProduct(product));
	}

	@PutMapping("/products/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable Long id,@RequestBody Product product) {
		product.setId(id);
		return ResponseEntity.ok().body(productService.updateProduct(product));
	}
	/*
	@DeleteMapping("/products/{id}")
	public ResponseEntity<Product> deleteProduct(@PathVariable Long id){
		this.productService.deleteProcut(id);
		return (ResponseEntity<Product>) ResponseEntity.status(HttpStatus.OK);
		
		*/
	
	@DeleteMapping("/products/{id}")
	public HttpStatus deleteProduct(@PathVariable Long id){
		this.productService.deleteProcut(id);
		return HttpStatus.OK;
	}
	
	
	
	
	
	
	
}