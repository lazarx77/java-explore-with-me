package ru.practicum.stats_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Главный класс приложения для сервиса статистики.
 */
@SpringBootApplication
public class StatsSvcApp {

    public static void main(String[] args) {
        SpringApplication.run(StatsSvcApp.class, args);
    }
}