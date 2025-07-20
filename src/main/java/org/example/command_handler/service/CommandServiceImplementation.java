package org.example.command_handler.service;

import lombok.extern.slf4j.Slf4j;
import org.example.command_handler.domain.Command;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CommandServiceImplementation implements CommandService {

    @Override
    public void executeCommand(Command command) {
        log.info("Command executed: {}", command);
    }

}
