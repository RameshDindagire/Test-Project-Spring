package com.main.controller;

import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.main.model.Category;
import com.main.repository.Products_Repository;
import com.main.service.Category_Service;

import antlr.collections.List;

@RestController
@RequestMapping(path = "/api")
public class CategoryController {
	
	@Autowired
	private Category_Service cService;
	
	@Autowired
	private Products_Repository productRepository;

	public ResponseEntity<?> getAllCategorey(@RequestParam Integer page){
		return new ResponseEntity<>(this.cService.getAllCategory(page),HttpStatus.OK);
	}
	@PostMapping("/categories")
	public ResponseEntity<?> createCategory(@RequestBody Category category, BindingResult br) {
		if (br.hasErrors()) {
			java.util.List<String> errors = br.getFieldErrors().stream().map(error -> {
				return error.getField() + "-" + error.getDefaultMessage();
			}).collect(Collectors.toList());
			String errorMessage = String.join(",", errors);
			return new ResponseEntity<>(errorMessage, HttpStatus.BAD_GATEWAY);
		} else {
			this.cService.addCategory(category);
			return new ResponseEntity<>("New category added successfully", HttpStatus.OK);
		}
		
	}
		
		@GetMapping("/categories/{id}")
		public ResponseEntity<?> getCategoryById(@PathVariable Integer id) {
			return new ResponseEntity<>(this.cService.getCategoryById(id), HttpStatus.OK);
		}
		
		@PutMapping("/categories/{id}")
		public ResponseEntity<?> updateCategory(@PathVariable Integer id, @RequestBody Category category) {
			return new ResponseEntity<>(this.cService.updateCategoryById(id, category), HttpStatus.OK);
		}
		
		@DeleteMapping("/categories/{id}")
		public ResponseEntity<?> deleteCategory(@PathVariable Integer id) {
			Category exists = this.cService.getCategoryById(id);
			if (exists == null) {
				return new ResponseEntity<>("Category not found", HttpStatus.NOT_FOUND);
			} else {
				this.cService.deleteCategoryById(id);
				return new ResponseEntity<>("Category details deleted successfully", HttpStatus.OK);
			}
}
}
