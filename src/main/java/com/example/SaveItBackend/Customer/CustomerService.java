package com.example.SaveItBackend.Customer;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;

@Service
public class CustomerService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerRepository.findCustomerByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Customer does not exist"));
//        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new org.springframework.security.core.userdetails.User(
                customer.getEmail(),
                customer.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
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
        customer.setPassword(new BCryptPasswordEncoder().encode(customer.getPassword()));
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

    public String hashPassword(String password){
        return DigestUtils.sha256Hex(password);
    }
}