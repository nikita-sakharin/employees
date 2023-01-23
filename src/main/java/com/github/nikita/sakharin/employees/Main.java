package com.github.nikita.sakharin.employees;
import java.util.Scanner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static java.lang.Long.parseLong;
import static java.lang.System.in;
import static java.lang.System.out;

@SpringBootApplication
public class Main {
    private static final Scanner scanner = new Scanner(in);
    private static final String exit = "exit";

    public static void main(final String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public CommandLineRunner getCommandLineRunner(
        final EmployeeService service
    ) {
        return args -> {
            final var employeeHierarchy = service.getEmployeeHierarchy();
            out.println("Graph depth: " + employeeHierarchy.getDepth());
            var least = employeeHierarchy.getLeast();
            out.println("Least: " + least.getFullName());
            while (least != null) {
                out.println("next after: " + least.getFullName());
                least = least.getSupervisor();
            }
            while (true) {
                final var input = scanner.next();
                if (input.equals(exit))
                    break;
                handleInput(employeeHierarchy, input);
            }
       };
    }

    private static void handleInput(
        final EmployeeHierarchy employeeHierarchy,
        final String input
    ) {
        final var id = parseLong(input);
        final var employee = employeeHierarchy.get(id);
        if (employee == null) {
            out.println("No such employee");
            return;
        }

        final var superviser = employee.getSupervisor();
        final var fullName = employee.getFullName();
        var message = "For employee " + fullName + " superviser ";
        if (superviser == null)
            message += "not found";
        else
            message +=  "is " + superviser.getFullName();
        out.println(message);
        final var subordinates = employeeHierarchy.getSubordinates(id);
        if (subordinates.isEmpty())
            out.println("No subordinates");
        else {
            out.println("Subordinates:");
            subordinates.forEach(value -> out.println(value.getFullName()));
        }
        out.println();
    }
}

