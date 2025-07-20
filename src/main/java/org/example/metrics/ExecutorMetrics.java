package org.example.metrics;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.example.command_handler.CommandExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class ExecutorMetrics {

    @Lazy
    @Autowired
    private CommandExecutor commandExecutor;

    public Supplier<Number> getQueueSize() {
        return () -> commandExecutor.getQueueSize();
    }

    public ExecutorMetrics (MeterRegistry meterRegistry) {
        Gauge.builder(MetricPrefixes.EXECUTOR_QUEUE_SIZE, getQueueSize()).register(meterRegistry);
    }
}
