package com.main.service;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import com.main.model.Product;
import com.main.repository.Category_Repository;
import com.main.repository.Products_Repository;

@Service
public class Product_Service {

	@Autowired
	private Products_Repository productRepository;
	
	@Autowired
	private Category_Repository cRepository;
	
	public List<Product> getAllProducts(Integer page) {
		Pageable paging = PageRequest.ofSize(page);
		Page<Product> pagedResult = this.productRepository.findAll(paging);
		if (pagedResult.hasContent()) {
			return pagedResult.getContent();
		} else {
			return new ArrayList<Product>();
		}
	}

	public Product getProductsyById(Integer id) {
		return this.productRepository.findById(id).orElseThrow(() -> {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found !");
		});
	}

	// METHOD TO UPDATE PRODUCT BY ID
	public Product updateProduct(@PathVariable Integer id, @RequestBody Product requestedProduct) {
		return this.productRepository.findById(id).map(product -> {
			product.setName(requestedProduct.getName());
			product.setPrice(requestedProduct.getPrice());
			return this.productRepository.save(product);
		}).orElseThrow(() -> {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
		});
	}

	public void deleteProductsById(Integer id) {
		this.productRepository.deleteById(id);
	}
	
}
