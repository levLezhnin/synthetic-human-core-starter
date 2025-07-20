package org.example.command_handler.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.example.command_handler.domain.Command;
import org.example.command_handler.domain.CommandPriority;
import org.example.command_handler.helper.DataHelper;
import org.example.error_handler.errors.BadRequestException;

@Data
@AllArgsConstructor
@Getter
@Builder
public class CommandDTO {

    private String description;
    private String commandPriority;
    private String author;
    private String time;

    public static Command toDomain(CommandDTO commandDTO) {

        if (commandDTO.getDescription().isEmpty()) {
            throw new BadRequestException("Incorrect request body: empty description");
        }
        if (commandDTO.getAuthor().isEmpty()) {
            throw new BadRequestException("Incorrect request body: unknown author");
        }
        if (commandDTO.getCommandPriority().isEmpty()) {
            throw new BadRequestException("Incorrect request body: unknown command priority");
        }
        if (commandDTO.getTime().isEmpty()) {
            throw new BadRequestException("Incorrect request body: empty time");
        }
        if (!DataHelper.isIsoFormat(commandDTO.getTime())) {
            throw new BadRequestException("Incorrect request body: time is not in ISO 8601 format");
        }

        return Command.builder()
                .description(commandDTO.getDescription())
                .author(commandDTO.getAuthor())
                .commandPriority(CommandPriority.valueOf(commandDTO.getCommandPriority()))
                .time(commandDTO.getTime())
                .build();
    }
}
