package ru.practicum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Главный класс приложения, запускающий приложение основного сервиса.
 */
@SpringBootApplication
public class MainSvc {

    public static void main(String[] args) {
        SpringApplication.run(MainSvc.class, args);
    }
}