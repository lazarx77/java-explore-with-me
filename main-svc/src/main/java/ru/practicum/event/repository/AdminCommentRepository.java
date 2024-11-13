package ru.practicum.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.event.model.admin_comment.AdminComment;

import java.util.List;

/**
 * Репозиторий для работы с комментариями администратора.
 * <p>
 * Этот интерфейс предоставляет методы для выполнения операций CRUD
 * с сущностями {@link AdminComment} и включает дополнительные методы
 * для поиска комментариев по идентификатору события.
 */
public interface AdminCommentRepository extends JpaRepository<AdminComment, Long> {

    /**
     * Находит все комментарии, связанные с указанным идентификатором события.
     *
     * @param eventId идентификатор события, для которого нужно найти комментарии.
     * @return список комментариев {@link AdminComment}, связанных с указанным событием.
     */
    List<AdminComment> findCommentsByEventId(Long eventId);

}
