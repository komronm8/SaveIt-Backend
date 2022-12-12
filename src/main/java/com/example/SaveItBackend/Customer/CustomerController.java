package com.example.SaveItBackend.Customer;

import com.example.SaveItBackend.Security.JwtUtils;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpHeaders;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    private final JwtUtils jwtUtils;

    @Autowired
    public CustomerController(CustomerService customerService, JwtUtils jwtUtils){
        this.customerService = customerService;
        this.jwtUtils = jwtUtils;
    }

//    @GetMapping
//    public List<Customer> getCustomers(){
//        return customerService.getCustomers();
//    }

    @GetMapping
    public Customer getCustomer(@RequestHeader("AUTHORIZATION") String authHeader){
        String email = jwtUtils.extractUserName(authHeader.substring(7));
        return customerService.getCustomerByEmail(email);
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
