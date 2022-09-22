package com.main.service;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.main.model.Category;
import com.main.model.Product;
import com.main.repository.Category_Repository;
import com.main.repository.Products_Repository;


@Service
public class Category_Service {

	@Autowired
	private Category_Repository categoreyRepository;
	
	@Autowired
	private Products_Repository productRepository;
	
	public Category addCategory(Category category) {
		return this.categoreyRepository.save(category);
	}
	
	public List<Category> getAllCategory(Integer page) {
		Pageable paging = PageRequest.ofSize(page);
		Page<Category> pagedResult = this.categoreyRepository.findAll(paging);
		if (pagedResult.hasContent()) {
			return pagedResult.getContent();
		} else {
			return new ArrayList<Category>();
		}
	}
		
		public Category getCategoryById(Integer id) {
			return this.categoreyRepository.findById(id).orElseThrow(() -> {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found !");
			});
		}
		public Category updateCategoryById(Integer id, Category category) {
			this.categoreyRepository.findById(id).orElseThrow(() -> {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND);
			});
			category.setId(id);
			return this.categoreyRepository.save(category);
		}
		public Product addProduct(Integer id, Product product) {
			Category foundCategory = this.getCategoryById(id);
			product.setCategory(foundCategory);
			Product savedProduct = this.productRepository.save(product);
			return savedProduct;
		}
		
		public void deleteCategoryById(Integer id) {
			this.categoreyRepository.deleteById(id);
		}
		
		public List<Product> getProducts(Integer id) {
			Category foundCategory = this.getCategoryById(id);
			return foundCategory.getProducts();
		}
}