package com.example.SaveItBackend.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getCustomers(){
        return customerService.getCustomers();
    }

    @GetMapping(path = "{customerID}")
    public Customer getCustomer(@PathVariable("customerID") Long customerId){
        return customerService.getCustomer(customerId);
    }

    @PostMapping(path = "registerCustomer")
    public void registerCustomer(@RequestBody Customer customer){
        customerService.addNewCustomer(customer);
    }

    @DeleteMapping(path = "{customerID}")
    public void deleteCustomer(@PathVariable("customerID") Long customerId){
        customerService.deleteCustomer(customerId);
    }

    @PutMapping(path = "{customerID}")
    public void updateCustomer(
            @PathVariable("customerID") Long customerId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email){
        customerService.updateCustomer(customerId, name, email);
    }

}
