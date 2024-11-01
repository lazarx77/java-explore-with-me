package ru.practicum.stats_client.client;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.stats_dto.StatsRequestDto;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Клиент для взаимодействия со сервером статистики.
 */
@Service
@Slf4j
public class StatsClient extends BaseClient {
    private static final String API_PREFIX = "";

    @Autowired
    public StatsClient(@Value("${stats-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    /**
     * Добавляет статистику о запросе.
     *
     * @param appName имя приложения.
     * @param request объект HttpServletRequest с информацией о запросе.
     * @return ответ от сервера.
     */
    public ResponseEntity<Object> addStats(String appName, HttpServletRequest request) {
        log.info("Вызывается метод addStats в StatsClient");

        StatsRequestDto requestDto = StatsRequestDto.builder()
                .app(appName)
                .uri(request.getRequestURI())
                .ip(request.getRemoteAddr())
                .timestamp(LocalDateTime.now())
                .build();

        return post("/hit", requestDto);
    }

    /**
     * Получает статистику за указанный период.
     *
     * @param start  начальная дата.
     * @param end    конечная дата.
     * @param uris   массив URI.
     * @param unique флаг уникальности.
     * @return ответ от сервера.
     */
    public ResponseEntity<Object> getStats(String start, String end, String[] uris, Boolean unique) {
        log.info("Вызывается метод getStats в StatsClient");

        String urisParam = (uris != null) ? String.join(",", uris) : null;

        Map<String, Object> parameters = Map.of(
                "start", start,
                "end", end,
                "uris", urisParam,
                "unique", unique
        );
        return get("/stats?start={start}&end={end}&uris={uris}&unique=unique", parameters);
    }
}
