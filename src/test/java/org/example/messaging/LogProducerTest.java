package org.example.messaging;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

import static junit.framework.Assert.assertEquals;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:29092", "port=29092"})
@Slf4j
public class LogProducerTest {

    @Value("${topic.log-event}")
    private String topic;

    @Autowired
    private LogProducer logProducer;

    public Properties getConsumerProperties() {
        Properties properties = new Properties();

        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "group-1");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        properties.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        properties.put(JsonDeserializer.VALUE_DEFAULT_TYPE, LogEvent.class);

        return properties;
    }


    @Test
    public void shouldSendLogEvent() {
        LogEvent logEvent = LogEvent.toLogEvent("method", new Object[]{new Object(), new Object()}, "null", "");
        try (KafkaConsumer<String, Object> consumer = new KafkaConsumer<>(getConsumerProperties())) {
            consumer.subscribe(List.of(topic));
            logProducer.sendLog(logEvent);
            ConsumerRecords<String, Object> records = consumer.poll(Duration.ofSeconds(10));
            assertEquals(1, records.count());
            assertEquals(records.iterator().next().value(), logEvent);
            log.info("Received value: {}", records.iterator().next().value());
        }
    }

}
