package ru.practicum;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.stats_client.client.StatsClient;

import static org.mockito.Mockito.verify;
import static ru.practicum.util.Utils.APP_NAME;

/**
 * Тестовый класс для {@link TestController}.
 */
@ExtendWith(MockitoExtension.class)
class TestControllerTest {

    @Mock
    private StatsClient statsClient;

    @InjectMocks
    private TestController testController;

    @Mock
    private HttpServletRequest request;

    @Test
    void saveHitTest_whenInvoked_thenStatsClientAddStatsCalled() {
        testController.saveHitTest(request);

        verify(statsClient).addStats(APP_NAME, request);
    }
}
