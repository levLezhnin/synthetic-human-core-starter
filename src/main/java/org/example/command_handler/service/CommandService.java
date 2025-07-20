package org.example.command_handler.service;

import org.example.command_handler.domain.Command;

public interface CommandService {
    void executeCommand(Command command);
}
