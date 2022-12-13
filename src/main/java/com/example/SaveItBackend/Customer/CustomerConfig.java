package com.example.SaveItBackend.Customer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
                    new BCryptPasswordEncoder().encode("moefoe"),
                    "13/12/2002"
            );

            Customer Alex = new Customer(
                    "Alex",
                    496345267583L,
                    "Alex@gmail.com",
                    new BCryptPasswordEncoder().encode("klia0494"),
                    "4/10/2000"
            );

            Customer Invoker = new Customer(
                    "Invoker",
                    491345728453L,
                    "sigmaMale@gmail.com",
                    new BCryptPasswordEncoder().encode("examplepassword"),
                    "26/05/2001"
            );

            repository.saveAll(
                    List.of(Mike, Alex, Invoker)
            );

        };
    }
}
