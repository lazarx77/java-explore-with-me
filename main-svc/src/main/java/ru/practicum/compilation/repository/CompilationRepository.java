package ru.practicum.compilation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.compilation.model.Compilation;

import java.util.List;
import java.util.Optional;

public interface CompilationRepository extends JpaRepository<Compilation, Long> {

    @Query("SELECT c FROM Compilation c WHERE LOWER(c.title) = LOWER(:title)")
    Optional<Compilation> findByTitle(@Param("title") String title);

    @Query(value = "SELECT * FROM compilations c WHERE c.pinned = :pinned ORDER BY c.id DESC LIMIT :size OFFSET :from", nativeQuery = true)
    List<Compilation>getCompilationsPublicPinned(@Param("pinned") Boolean pinned, @Param("from") int from, @Param("size") int size);

    @Query(value = "SELECT * FROM compilations c ORDER BY c.id DESC LIMIT :size OFFSET :from", nativeQuery = true)
    List<Compilation>getCompilationsPublic(@Param("from") int from, @Param("size") int size);
}
