package ru.practicum.category.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;
import ru.practicum.category.mapper.CategoryDtoMapper;
import ru.practicum.category.model.Category;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.exception.CategoryNameDoubleException;
import ru.practicum.exception.NotFoundException;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto createNewCategory(NewCategoryDto dto) {
        log.info("Вызывается метод createNewCategory в CategoryServiceImpl");
        isCategoryExistByName(dto.getName());
        Category category = new Category();
        category.setName(dto.getName());
        return CategoryDtoMapper.toCategoryDto(categoryRepository.save(category));
    }

    @Override
    public CategoryDto updateCategory(Long catId, CategoryDto dto) {
        log.info("Вызывается метод updateCategory в CategoryServiceImpl");
        Category category = findCategoryById( catId);
        isCategoryExistByName(dto.getName());
        category.setName(dto.getName());
        log.info("Новое название категории с id {} направлено на сохранение в БД", catId);
        return CategoryDtoMapper.toCategoryDto(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(Long catId) {
        log.info("Вызывается метод deleteCategory в CategoryServiceImpl");
        Category category = findCategoryById(catId);
        log.info("Запрос на удаление категории с id {} направлен в БД", catId);
        categoryRepository.delete(category);
    }

    @Override
    public List<CategoryDto> getCategories(int from, int size) {
        log.info("Вызывается метод getCategories в CategoryServiceImpl");
        List<Category> categoryList = categoryRepository.findAllCategories(size, from);
        return categoryList.stream().map(CategoryDtoMapper::toCategoryDto).toList();
    }

    @Override
    public CategoryDto getCategory(Long catId) {
        log.info("Вызывается метод getCategoryById в CategoryServiceImpl");
        Category category = findCategoryById(catId);
        return CategoryDtoMapper.toCategoryDto(category);
    }

    private void isCategoryExistByName(String name) {
        log.info("Проверка уникальности названия категории {}", name);
        if (categoryRepository.findByName(name).isPresent()) {
            log.info("Категория с названием {} уже существует", name);
            throw new CategoryNameDoubleException("Категория с названием " + name + " уже существует");
        }
    }

    private Category findCategoryById(Long catId) {
        log.info("Проверка наличия категории с id {}", catId);
        return categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException("Категория с id " + catId + " не найдена"));
    }
}
