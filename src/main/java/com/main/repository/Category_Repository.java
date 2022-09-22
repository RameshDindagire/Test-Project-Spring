package com.main.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.main.model.Category;


@Repository
public interface Category_Repository extends PagingAndSortingRepository<Category, Integer>{

}
