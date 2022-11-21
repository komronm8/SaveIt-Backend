package com.example.SaveItBackend.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Class responsible for data access of "Customer"
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    //jbql Query not straight sql
    @Query("SELECT c FROM Customer c WHERE c.email = ?1")
    Optional<Customer> findCustomerByEmail(String email);
}
