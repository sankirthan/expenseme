package com.github.sankirthan.expenseme.controller;

import com.github.sankirthan.expenseme.dto.CategoryDto;
import com.github.sankirthan.expenseme.entity.Category;
import com.github.sankirthan.expenseme.mapper.CategoryMapper;
import com.github.sankirthan.expenseme.repository.CategoryRepository;
import com.github.sankirthan.expenseme.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@RestController
@RequestMapping(Api.BASE_URL + "/categories")
@Validated
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryMapper categoryMapper;

    @GetMapping
    public Page<CategoryDto> listCategories(@RequestParam(required = false) String name,
                                            @RequestParam(required = false, defaultValue = "0") @Min(0) Integer page,
                                            @RequestParam(required = false, defaultValue = "10") @Max(100) Integer pageSize) {
        return categoryService.find(name, page, pageSize).map(categoryMapper::toCategoryDto);
    }

    @PostMapping
    public CategoryDto createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        if (categoryDto.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id is not null");
        }

        if (categoryService.categoryExistsWithName(categoryDto.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "name already exists");
        }

        return categoryMapper.toCategoryDto(
                categoryRepository.saveAndFlush(categoryMapper.toCategory(categoryDto)));
    }

    @PutMapping("/{id}")
    public CategoryDto updateCategory(@PathVariable @NotNull Long id, @Valid @RequestBody CategoryDto categoryDto) {
        Optional<Category> existing = categoryRepository.findById(id);
        if (!existing.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "category not found");
        }

        if (!id.equals(categoryDto.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id does not match");
        }

        categoryMapper.update(existing.get(), categoryDto);

        return categoryMapper.toCategoryDto(categoryRepository.saveAndFlush(existing.get()));
    }

    @DeleteMapping("/{id}")
    public CategoryDto deleteCategory(@PathVariable @NotNull Long id) {
        Optional<Category> existing = categoryRepository.findById(id);
        if (!existing.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "category not found");
        }
        categoryRepository.delete(existing.get());
        return categoryMapper.toCategoryDto(existing.get());
    }

}
