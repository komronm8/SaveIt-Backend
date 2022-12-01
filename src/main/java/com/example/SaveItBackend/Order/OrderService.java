package com.example.SaveItBackend.Order;

import com.example.SaveItBackend.Customer.CustomerRepository;
import com.example.SaveItBackend.Store.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository, StoreRepository storeRepository) {
        this.orderRepository = orderRepository;
        this.storeRepository = storeRepository;
        this.customerRepository = customerRepository;
    }

    public List<Order> getOrders(){
        return orderRepository.findAll();
    }

    public Order getOrder(Long orderId){ return orderRepository.findById(orderId)
            .orElseThrow(() -> new IllegalStateException("Order does not exist"));
    }

    @Transactional
    public void createOrder(Order order) {
        Long storeId = order.getStore().getId();
        Long customerId = order.getCustomer().getId();
        boolean storeExists = storeRepository.existsById(storeId);
        if(!storeExists){
            throw new IllegalStateException("Store with id " + storeId + " does not exist");
        }
        boolean customerExists = storeRepository.existsById(storeId);
        if(!customerExists){
            throw new IllegalStateException("Customer with id " + customerId + " does not exist");
        }
        order.setOrderDate(LocalDate.now());
        order.setStatus(0);
        order.setPricePerBox(storeRepository.getReferenceById(storeId).getPrice());
        order.setTotalPrice(order.getPricePerBox()*order.getBoxesAmount());
        orderRepository.save(order);
    }

    @Transactional
    public List<Order> getCustomerOrders(Long customer_id) {
        return orderRepository.findCustomerOrders(customer_id);
    }
}
