package com.github.sankirthan.expenseme.mapper;

import com.github.sankirthan.expenseme.dto.CategoryDto;
import com.github.sankirthan.expenseme.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryDto categoryDto);

    CategoryDto toCategoryDto(Category category);

    List<CategoryDto> toCategoryDtoList(List<Category> categories);

    void update(@MappingTarget Category category, CategoryDto categoryDto);
}
