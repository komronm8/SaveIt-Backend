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
                    "aab2a68fcf5278315da3a66d8dd803e32e1190d8e7ae8d421d874ae018935cf9",
                    LocalDate.of(2002, 1, 12)
            );

            Customer Alex = new Customer(
                    "Alex",
                    496345267583L,
                    "Alex@gmail.com",
                    "42c81568824258f7f6216337be7d381de744461dd58dad9c67ad20e58e57b729",
                    LocalDate.of(2000, 12, 12)
            );

            Customer Invoker = new Customer(
                    "Invoker",
                    491345728453L,
                    "sigmaMale@gmail.com",
                    "8fc4d17c104401ed4b8eaab91cb7d2f8ad342bb2bb5216f7ba86ef22d265b107",
                    LocalDate.of(1090, 3, 23)
            );

            repository.saveAll(
                    List.of(Mike, Alex, Invoker)
            );

        };
    }
}
