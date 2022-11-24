package com.example.SaveItBackend.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getCustomers(){
        return customerRepository.findAll();
    }

    public Customer getCustomer(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalStateException("Customer does not exist"));
    }

    public void addNewCustomer(Customer customer) {
        Optional<Customer> customerOptional = customerRepository.
                findCustomerByEmail(customer.getEmail());
        if(customerOptional.isPresent()){
            throw new IllegalStateException("Email taken");
        }
        customerRepository.save(customer);
    }

    public void deleteCustomer(Long customerId) {
        boolean exists = customerRepository.existsById(customerId);
        if(!exists){
            throw new IllegalStateException("Customer with id " + customerId + " does not exist");
        }
        customerRepository.deleteById(customerId);
    }

    @Transactional
    public void updateCustomer(Long customerId, String name, String email){
        boolean exists = customerRepository.existsById(customerId);
        if(!exists){
            throw new IllegalStateException("Customer with id " + customerId + " does not exist");
        }
        Customer customer = customerRepository.getReferenceById(customerId);

        if(name != null && name.length() > 0 && !Objects.equals(customer.getName(), name)){
            customer.setName(name);
        }

        if(email != null && email.length() > 0 && !Objects.equals(customer.getEmail(), email)){
            Optional<Customer> customerOptional = customerRepository.findCustomerByEmail(email);
            if(customerOptional.isPresent()){
                throw new IllegalStateException("Email taken");
            }
            customer.setEmail(email);
        }
    }

}