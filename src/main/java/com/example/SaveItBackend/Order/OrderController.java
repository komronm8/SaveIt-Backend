package com.example.SaveItBackend.Order;

import com.example.SaveItBackend.Customer.Customer;
import com.example.SaveItBackend.Customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
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

    @GetMapping(path = "{order_id}")
    public Order getOrder(@PathVariable("order_id") Long order_id){
        return orderService.getOrder(order_id);
    }

    @GetMapping(path = "customer/{customer_id}")
    public List<Order> getCustomerOrders(
            @PathVariable("customer_id") Long customer_id
    ){
        return orderService.getCustomerOrders(customer_id);
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order){
        orderService.createOrder(order);
        return new ResponseEntity<>(orderService.getOrder(order.getId()), HttpStatus.OK);
    }

    @PutMapping(path = "{orderID}")
    public ResponseEntity<Order> changeOrderStatus(
            @PathVariable("orderID") Long orderId,
            @RequestParam(required = false) Integer status){
        orderService.changeStatus(orderId, status);
        return new ResponseEntity<>(orderService.getOrder(orderId), HttpStatus.OK);
    }

}
