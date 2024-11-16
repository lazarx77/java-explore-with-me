package ru.practicum.event.dto.admin_comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import static ru.practicum.util.Utils.DATE_TIME_FORMAT;

/**
 * DTO для ответа с комментариями администратора.
 * <p>
 * Этот класс используется для передачи данных о комментарии администратора,
 * включая его идентификатор, текст комментария и дату создания.
 */
@Getter
@Setter
@Builder
public class AdminCommentResponse {

    private Long id;
    private String text;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private LocalDateTime created;
}
