package com.home.connections.services.commands;

import com.home.connections.services.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("math")
public class MathCommand implements Command {

    @Override
    public void execute(MessageReceivedEvent event) {
        // Get the message content
        List<String> parts = List.of(event.getMessage().getContentRaw().split("\\s+"));

        if (parts.size() < 3) {
            event.getChannel().sendMessage("❌ Usage: !math <operation> <number1> <number2> ... <numberN>").queue();
            return;
        }

        String operation = parts.get(1).toLowerCase(); // Get the operation type
        List<String> numbers = parts.subList(2, parts.size());

        try {
            int result;
            switch (operation) {
                case "add":
                    result = numbers.stream()
                            .mapToInt(Integer::parseInt)
                            .sum();
                    break;
                case "subtract":
                    result = numbers.stream()
                            .mapToInt(Integer::parseInt)
                            .reduce((a, b) -> a - b)
                            .orElse(0);
                    break;
                case "multiply":
                    result = numbers.stream()
                            .mapToInt(Integer::parseInt)
                            .reduce(1, (a, b) -> a * b); // Start with 1 for multiplication
                    break;
                case "divide":
                    result = numbers.stream()
                            .mapToInt(Integer::parseInt)
                            .reduce((a, b) -> a / b)
                            .orElseThrow(() -> new IllegalArgumentException("Cannot divide by zero or missing input"));
                    break;
                default:
                    event.getChannel().sendMessage("❌ Unsupported operation: `" + operation + "`.\nSupported operations: add, subtract, multiply, divide.").queue();
                    return;
            }

            event.getChannel().sendMessage("✅ The result of `" + operation + "` is: `" + result + "`").queue();
        } catch (NumberFormatException e) {
            event.getChannel().sendMessage("❌ All inputs must be valid numbers!").queue();
        } catch (ArithmeticException e) {
            event.getChannel().sendMessage("❌ Error during calculation: Division by zero is not allowed!").queue();
        }
    }
}
