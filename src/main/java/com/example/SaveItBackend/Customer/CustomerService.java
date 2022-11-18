package com.example.SaveItBackend.Customer;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CustomerService {

    public List<String> getCustomers(){
        Customer Mike = new Customer(
                1L,
                "Mike",
                49695267583L,
                "Mike@gmail.com",
                "ExamplePassword123",
                LocalDate.of(2002, 1, 12)
        );
        return List.of(Mike.toString());
    }

}