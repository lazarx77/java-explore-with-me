package ru.practicum.stats_dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatsRequestDto {

    @NotBlank(message = "App cannot be blank")
    private String app;
    @NotBlank(message = "Uri cannot be blank")
    private String uri;
    @NotBlank(message = "Ip cannot be blank")
    private String ip;
    @NotNull(message = "Timestamp cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

}
