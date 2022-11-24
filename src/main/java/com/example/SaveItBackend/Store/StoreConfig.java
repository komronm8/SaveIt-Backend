package com.example.SaveItBackend.Store;

import com.example.SaveItBackend.Order.Order;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Configuration
public class StoreConfig {

    @Bean
    CommandLineRunner storeCommandLineRunner(StoreRepository repository) {
        return args -> {
            Store Starbucks = new Store(
                    "Starbucks",
                    "info@starbucks.com",
                    "Thier Galerie, Westenhellweg 102-106",
                    LocalTime.of(21, 30)
            );


            repository.saveAll(
                    List.of(Starbucks)
            );

        };
    }
}
