package com.github.sankirthan.expenseme.service;

import com.github.sankirthan.expenseme.entity.Category;
import com.github.sankirthan.expenseme.entity.QCategory;
import com.github.sankirthan.expenseme.repository.CategoryRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public boolean categoryExistsWithName(String name) {
        return categoryRepository.exists(QCategory.category.name.likeIgnoreCase(name));
    }

    public Page<Category> find(String name, Integer page, Integer pageSize) {
        QCategory category = QCategory.category;
        BooleanExpression predicate = null;
        if (StringUtils.hasText(name)) {
            name = name.trim();
            predicate = category.name.containsIgnoreCase(name);
        }

        PageRequest pageRequest = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.ASC,
                category.name.getMetadata().getName()));
        if(predicate == null) {
            return categoryRepository.findAll(pageRequest);
        }

        return categoryRepository.findAll(predicate, pageRequest);
    }

}
