package com.example.SaveItBackend.Order;

import com.example.SaveItBackend.Customer.Customer;
import com.example.SaveItBackend.Customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/order")
public class OrderController {

    private final OrderService orderService;

    private final CustomerService customerService;

    @Autowired
    public OrderController(OrderService orderService, CustomerService customerService){
        this.orderService = orderService;
        this.customerService = customerService;
    }

    @GetMapping
    public List<Order> getOrders(){
        return orderService.getOrders();
    }

    @PostMapping
    public void createOrder(@RequestBody Order order){
        orderService.createOrder(order);
    }

    @PutMapping(path = "/{orderID}/customer/{customerID}")
    public void assignCustomerToOrder(
            @PathVariable("orderID") Long orderId,
            @PathVariable("customerID") Long customerId){
        Customer customer = customerService.getCustomer(customerId);
        Order order = orderService.getOrder(orderId);
        order.setCustomer(customer);
    }

}
