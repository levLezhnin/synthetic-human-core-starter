package org.example.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MetricServiceImplementation implements MetricService {

    @Autowired
    private MeterRegistry meterRegistry;

    public MetricServiceImplementation(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Override
    public void incrementCounterMetric(String name) {
        meterRegistry.counter(name).increment();
        log.info("Incremented: {}", name);
    }
}
