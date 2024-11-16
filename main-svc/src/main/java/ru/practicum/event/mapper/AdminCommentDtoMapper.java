package ru.practicum.event.mapper;

import ru.practicum.event.dto.admin_comment.AdminCommentResponse;
import ru.practicum.event.model.admin_comment.AdminComment;

import java.time.LocalDateTime;

/**
 * Mapper для преобразования объектов комментариев администратора.
 * <p>
 * Этот класс предоставляет методы для преобразования текстового комментария
 * в объект {@link AdminComment} и для преобразования объекта
 * {@link AdminComment} в {@link AdminCommentResponse}.
 */
public class AdminCommentDtoMapper {

    /**
     * Mapper для преобразования объектов комментариев администратора.
     * <p>
     * Этот класс предоставляет методы для преобразования текстового комментария
     * в объект {@link AdminComment} и для преобразования объекта
     * {@link AdminComment} в {@link AdminCommentResponse}.
     */
    public static AdminComment toAdminComment(String text) {
        return AdminComment.builder()
                .created(LocalDateTime.now())
                .text(text)
                .build();
    }

    /**
     * Mapper для преобразования объектов комментариев администратора.
     * <p>
     * Этот класс предоставляет методы для преобразования текстового комментария
     * в объект {@link AdminComment} и для преобразования объекта
     * {@link AdminComment} в {@link AdminCommentResponse}.
     */
    public static AdminCommentResponse toAdminCommentResponse(AdminComment adminComment) {
        return AdminCommentResponse.builder()
                .id(adminComment.getId())
                .text(adminComment.getText())
                .created(adminComment.getCreated())
                .build();
    }
}
