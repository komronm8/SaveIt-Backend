package com.example.SaveItBackend.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Class responsible for data access of "Customer"
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
