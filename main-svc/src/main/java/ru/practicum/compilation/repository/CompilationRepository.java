package ru.practicum.compilation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.compilation.model.Compilation;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с сущностями компиляции.
 * <p>
 * Этот интерфейс расширяет JpaRepository и предоставляет методы для выполнения
 * операций с таблицей "compilations" в базе данных, включая поиск по заголовку,
 * получение закрепленных компиляций и получение всех компиляций с поддержкой пагинации.
 */
public interface CompilationRepository extends JpaRepository<Compilation, Long> {

    /**
     * Находит компиляцию по её заголовку.
     *
     * @param title заголовок компиляции, которую необходимо найти.
     * @return Optional<Compilation> - найденная компиляция, если она существует, иначе пустой Optional.
     */
    @Query("SELECT c FROM Compilation c WHERE LOWER(c.title) = LOWER(:title)")
    Optional<Compilation> findByTitle(@Param("title") String title);

    /**
     * Получает список закрепленных компиляций с поддержкой пагинации.
     *
     * @param pinned статус закрепления компиляции (true для закрепленных).
     * @param from   индекс, с которого начинается выборка компиляций.
     * @param size   максимальное количество компиляций, которые необходимо вернуть.
     * @return List<Compilation> - список закрепленных компиляций.
     */
    @Query(value = "SELECT * FROM compilations c WHERE c.pinned = :pinned ORDER BY c.id DESC LIMIT :size OFFSET :from", nativeQuery = true)
    List<Compilation> getCompilationsPublicPinned(@Param("pinned") Boolean pinned, @Param("from") int from, @Param("size") int size);

    /**
     * Получает список всех компиляций с поддержкой пагинации.
     *
     * @param from индекс, с которого начинается выборка компиляций.
     * @param size максимальное количество компиляций, которые необходимо вернуть.
     * @return List<Compilation> - список всех компиляций.
     */
    @Query(value = "SELECT * FROM compilations c ORDER BY c.id DESC LIMIT :size OFFSET :from", nativeQuery = true)
    List<Compilation> getCompilationsPublic(@Param("from") int from, @Param("size") int size);
}
