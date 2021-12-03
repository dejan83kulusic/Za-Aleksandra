package com.example.service;

import java.util.List;
import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.exceptions.ResourceNotFoundException;
import com.example.model.Product;
import com.example.repository.ProductRepository;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public Product createProduct(Product product) {

		return productRepository.save(product);
	}

	@Override
	public Product updateProduct(Product product) {
		Optional<Product> productDB = this.productRepository.findById(product.getId());

		if (productDB.isPresent()) {
			Product productUpdate = productDB.get();
			productUpdate.setId(product.getId());
			productUpdate.setName(product.getName());
			productUpdate.setDescription(product.getDescription());
			productUpdate.setPrice(product.getPrice());
			productRepository.save(productUpdate);
			return productUpdate;
		} else {
			throw new ResourceNotFoundException("Record Not Found" + product.getId());
		}

	}

	@Override
	public List<Product> getAllProduct() {
		return productRepository.findAll();
	}

	@Override
	public Product getProductById(Long productId) {
		Optional<Product> productDB = this.productRepository.findById(productId);

		if (productDB.isPresent()) {
			return productDB.get();
		} else {
			throw new ResourceNotFoundException("Record Not Found" + productId);

		}

	}

	@Override
	public void deleteProcut(Long productId) {
		Optional<Product> productDB = this.productRepository.findById(productId);

		if (productDB.isPresent()) {
			this.productRepository.delete(productDB.get());
		} else {
			throw new ResourceNotFoundException("Record Not Found" + productId);

		}

	}

}
