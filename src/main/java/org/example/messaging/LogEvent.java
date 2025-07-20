package org.example.messaging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogEvent {
    private String invokableMethodName;
    private String args;
    private String result;

    public static LogEvent toLogEvent(String invokableMethodName, Object[] args, Object result, String messageFromThrowable) {
        String invokeResult = (messageFromThrowable.isEmpty() ? result.toString() : messageFromThrowable);
        return LogEvent.builder()
                .invokableMethodName(invokableMethodName)
                .args(Arrays.stream(args).map(Object::toString).collect(Collectors.joining(", ")))
                .result(invokeResult)
                .build();
    }
}
