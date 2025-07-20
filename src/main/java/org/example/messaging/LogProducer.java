package org.example.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LogProducer {

    @Value("${topic.log-event}")
    private String logTopic;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendLog(LogEvent logEvent) {
        kafkaTemplate.send(logTopic, "", logEvent);
        log.info("Log sent through Kafka.");
    }
}
