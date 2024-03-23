package ru.hse.monitoringagent.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hse.monitoringagent.service.ConfigService;

@RestController
@RequestMapping("/config")
@Tag(name = "Конфигурация агента", description = "Настройка конфигурации через API")
public class ConfigController {

    private final ConfigService configService;

    public ConfigController(ConfigService configService) {
        this.configService = configService;
    }

    @PutMapping("/metrics/url")
    @Operation(summary = "Добавить новый эндпоинт", description = "Добавляет новый опрашиваемый эндпоинт в конфиг")
    public void addMetricURL(
            @RequestParam(name = "value")
            @Parameter(description = "Добавляемый эндпоинт", in = ParameterIn.QUERY)
            String url
    ) {
        configService.addMetricURL(url);
    }

    @DeleteMapping("/metrics/url")
    @Operation(summary = "Убрать эндпоинт", description = "Убирает эндпоинт из конфига")
    public void deleteMetricURL(
            @RequestParam(name = "value")
            @Parameter(description = "Удаляемый эндпоинт")
            String url
    ) {
        configService.removeMetricURL(url);
    }

    @PostMapping("/poll/rate")
    @Operation(summary = "Настроить время опроса эндпоинтов", description = "Устанавливает новую переодичность запросов в эндпоинт")
    @ApiResponse()
    @ApiResponse(responseCode = "bad_request", description = "новое значение меньше или равно 0 ")
    public ResponseEntity<?> setPollRate(
            @RequestParam(name = "value")
            @Parameter(description = "Новое значение переодичности")
            int rate
    ) {
        if (rate <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("rate must be positive number");
        }

        configService.updatePollMetricRate(rate);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/save/rate")
    @Operation(
            summary = "Настроить время сохранения данных в снапшот",
            description = "Устанавливает новую переодичность снапшотирования данных в базе"
    )
    @ApiResponse()
    @ApiResponse(responseCode = "bad_request", description = "новое значение меньше или равно 0 ")
    public ResponseEntity<?> setSaveRate(
            @RequestParam(name = "value")
            @Parameter(description = "Новое значение переодичности")
            int rate
    ) {
        if (rate <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("rate must be positive number");
        }

        configService.updateSaveRate(rate);
        return ResponseEntity.ok().build();
    }

}
