package ru.practicum.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.category.model.Category;

import java.util.List;
import java.util.Optional;


/**
 * Репозиторий для работы с сущностями категории.
 * <p>
 * Этот интерфейс расширяет JpaRepository и предоставляет методы для выполнения
 * операций с таблицей "categories" в базе данных, включая поиск по имени и
 * получение всех категорий с поддержкой пагинации.
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Находит категорию по её названию.
     *
     * @param name название категории, которую необходимо найти.
     * @return Optional<Category> - найденная категория, если она существует, иначе пустой Optional.
     */
    Optional<Category> findByName(String name);

    /**
     * Получает список всех категорий с поддержкой пагинации.
     *
     * @param limit  максимальное количество категорий, которые необходимо вернуть.
     * @param offset смещение для пагинации, указывающее, с какой категории начинать выборку.
     * @return List<Category> - список категорий, соответствующих заданным параметрам.
     */
    @Query(value = "SELECT * FROM categories LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Category> findAllCategories(@Param("limit") int limit, @Param("offset") int offset);
}
