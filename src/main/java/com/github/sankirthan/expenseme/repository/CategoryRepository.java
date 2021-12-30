package com.github.sankirthan.expenseme.repository;

import com.github.sankirthan.expenseme.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long>, QuerydslPredicateExecutor<Category> {
    List<Category> findByNameContainingIgnoreCase(String name);
}
