package com.main.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.main.model.Product;

public interface Products_Repository extends PagingAndSortingRepository<Product, Integer>{

}
