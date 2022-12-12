package com.example.SaveItBackend.Order;

import com.example.SaveItBackend.Customer.CustomerService;
import com.example.SaveItBackend.Security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    private final CustomerService customerService;

    private final JwtUtils jwtUtils;

    @GetMapping
    public List<Order> getOrders(){
        return orderService.getOrders();
    }

    @GetMapping(path = "{order_id}")
    public Order getOrder(@PathVariable("order_id") Long order_id){
        return orderService.getOrder(order_id);
    }

    @GetMapping(path = "customer")
    public List<Order> getCustomerOrders(
            @RequestHeader("AUTHORIZATION") String authHeader
    ){
        String email = jwtUtils.extractUserName(authHeader.substring(7));
        Long customerId = customerService.getCustomerByEmail(email).getId();
        return orderService.getCustomerOrders(customerId);
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
