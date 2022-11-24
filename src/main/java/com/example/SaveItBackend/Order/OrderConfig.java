package com.example.SaveItBackend.Order;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class OrderConfig {

    @Bean
    CommandLineRunner orderCommandLineRunner(OrderRepository repository){

        return args -> {
            Order first = new Order(
                    LocalDate.of(2023, 12, 1),
                    40L,
                    30.40,
                    0
            );


            Order second = new Order(
                    LocalDate.of(2023, 4, 23),
                    10L,
                    4.80,
                    1
            );

//            repository.saveAll(
//                    List.of(first, second)
//            );

        };
    }

}
