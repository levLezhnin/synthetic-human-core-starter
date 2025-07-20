package org.example.command_handler.rest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.command_handler.CommandExecutor;
import org.example.command_handler.domain.Command;
import org.example.command_handler.rest.dto.CommandDTO;
import org.example.error_handler.ErrorHandler;
import org.example.metrics.MetricPrefixes;
import org.example.metrics.MetricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@Import({ErrorHandler.class})
public class CommandReceiver {

    @Autowired
    private final CommandExecutor commandExecutor;
    @Autowired
    private final MetricService metricService;

    @PostMapping(value = "/send", consumes = "application/json")
    public void receiveCommand(@RequestBody CommandDTO commandDTO) {

        Command command = CommandDTO.toDomain(commandDTO);

        metricService.incrementCounterMetric(MetricPrefixes.AUTHOR_ORDER_COUNT_PREFIX + command.getAuthor());
        metricService.incrementCounterMetric(MetricPrefixes.ORDERS_TOTAL);

        commandExecutor.scheduleTask(command);

    }
}
