package org.example.command_handler.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
@Getter
@Builder
public class Command {

    private String description;
    private CommandPriority commandPriority;
    private String author;
    private String time;

}
