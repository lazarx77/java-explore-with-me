package ru.practicum;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.stats_client.client.StatsClient;

/**
 * Контроллер для обработки запросов, связанных с событиями.
 */
@RestController
@RequestMapping
@AllArgsConstructor
@Slf4j
public class TestController {

    private final StatsClient statsClient;
    private static final String APP_NAME = "ewm-main-service";

    /**
     * Обрабатывает POST-запрос для сохранения статистики по событию.
     *
     * @param request объект HttpServletRequest, содержащий информацию о запросе.
     */
    @PostMapping("/events/2")
    public void saveHitTest(HttpServletRequest request) {

        statsClient.addStats(APP_NAME, request);
    }
}
