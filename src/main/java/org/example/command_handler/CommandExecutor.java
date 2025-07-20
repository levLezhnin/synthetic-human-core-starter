package org.example.command_handler;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.command_handler.domain.Command;
import org.example.command_handler.domain.CommandPriority;
import org.example.command_handler.service.CommandService;
import org.example.error_handler.errors.FullTaskQueueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommandExecutor extends Thread {

    private static final int MAX_COMMAND_QUEUE_SIZE = 20;
    private static final int TIME_TO_EXECUTE_MS = 2000;
    private static final int WAITING_TIME_MS = 500;

    @Autowired
    private CommandService commandService;
    private final LinkedList<Command> commonCommandQueue, criticalCommandQueue;
    private final AtomicBoolean isInterruptedByCriticalTask = new AtomicBoolean(false);
    private volatile Command currentExecutableCommand;

    public CommandExecutor() {
        criticalCommandQueue = new LinkedList<>();
        commonCommandQueue = new LinkedList<>();
    }

    public long getQueueSize() {
        return commonCommandQueue.size() + criticalCommandQueue.size();
    }

    @SneakyThrows
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(TIME_TO_EXECUTE_MS);

                if (!criticalCommandQueue.isEmpty()) {

                    currentExecutableCommand = criticalCommandQueue.poll();
                    commandService.executeCommand(currentExecutableCommand);
                    currentExecutableCommand = null;

                } else if (!commonCommandQueue.isEmpty()) {

                    currentExecutableCommand = commonCommandQueue.poll();
                    commandService.executeCommand(currentExecutableCommand);
                    currentExecutableCommand = null;

                } else {

                    Thread.sleep(WAITING_TIME_MS);

                }

            } catch (InterruptedException e) {

                if (isInterruptedByCriticalTask.get()) {
                    log.info("Interrupted by critical task");
                    if (currentExecutableCommand != null && currentExecutableCommand.getCommandPriority().equals(CommandPriority.COMMON)) {
                        commonCommandQueue.addFirst(currentExecutableCommand);
                        currentExecutableCommand = null;
                    }
                } else {
                    throw new RuntimeException("Unexpected interruption");
                }
            }
        }
    }

    private void onCriticalTask(Command command) {
        criticalCommandQueue.add(command);
        if (currentExecutableCommand == null ||
                (currentExecutableCommand != null && currentExecutableCommand.getCommandPriority().equals(CommandPriority.COMMON))) {
            isInterruptedByCriticalTask.set(true);
            this.interrupt();
        }
    }

    public void scheduleTask(Command command) {
        if (commonCommandQueue.size() + criticalCommandQueue.size() > MAX_COMMAND_QUEUE_SIZE) {
            throw new FullTaskQueueException();
        }
        if (command.getCommandPriority().equals(CommandPriority.CRITICAL)) {
            onCriticalTask(command);
        } else {
            commonCommandQueue.add(command);
        }
    }
}
