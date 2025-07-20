package org.example;

import org.example.command_handler.CommandExecutor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class App 
{
    public static void main( String[] args ) throws InterruptedException {
        ConfigurableApplicationContext context = SpringApplication.run(App.class, args);
        CommandExecutor commandExecutor = context.getBean(CommandExecutor.class);
        commandExecutor.start();
        commandExecutor.join();
    }
}
