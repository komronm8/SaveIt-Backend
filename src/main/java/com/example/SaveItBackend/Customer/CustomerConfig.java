package com.example.SaveItBackend.Customer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class CustomerConfig {

    @Bean
    CommandLineRunner customerCommandLineRunner(CustomerRepository repository){
        return args -> {
            Customer Mike = new Customer(
                    "Mike",
                    49695267583L,
                    "Mike@gmail.com",
                    "ExamplePassword123",
                    LocalDate.of(2002, 1, 12)
            );

            Customer Alex = new Customer(
                    "Alex",
                    496345267583L,
                    "Alex@gmail.com",
                    "ExamplePassword456",
                    LocalDate.of(2000, 12, 12)
            );

            repository.saveAll(
                    List.of(Mike, Alex)
            );

        };
    }
}
