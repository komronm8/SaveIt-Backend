package com.example.SaveItBackend.Order;

import com.example.SaveItBackend.Customer.Customer;
import com.example.SaveItBackend.Customer.CustomerRepository;
import com.example.SaveItBackend.Store.Store;
import com.example.SaveItBackend.Store.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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
    public void createOrder(Order order, Customer customer) {
        Long storeId = order.getStore().getId();
        boolean storeExists = storeRepository.existsById(storeId);
        if(!storeExists){
            throw new IllegalStateException("Store with id " + storeId + " does not exist");
        }
        if(LocalTime.now().isAfter(storeRepository.getReferenceById(storeId).getCollectionTimeStart())){
            throw new IllegalStateException("Order cannot be created after the collection time has started");
        }
        Store store = storeRepository.findById(storeId).get();
        if(store.getBoxesAmount() < order.getBoxesAmount()){
            throw new IllegalStateException("Order boxes amount is more than store boxes amount");
        }
        store.setBoxesAmount(store.getBoxesAmount() - order.getBoxesAmount());
        order.setCustomer(customer);
        order.setOrderNumber(getOrderNumber());
        order.setOrderDate(LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)));
        order.setStatus(0);
        order.setPricePerBox(storeRepository.getReferenceById(storeId).getPrice());
        order.setTotalPrice(order.getPricePerBox()*order.getBoxesAmount());
        order.setPriceWithoutDiscount(storeRepository.getReferenceById(storeId).getPriceWithoutDiscount()*order.getBoxesAmount());
        order.setOrderTime(LocalTime.now());
        orderRepository.save(order);
        new java.util.Timer().schedule(
                new java.util.TimerTask(){
                    @Override
                    public void run(){
                        order.setStatus(2);
                        orderRepository.save(order);
                    }
                },
                Duration.between(order.getOrderTime(), storeRepository.getReferenceById(storeId).getCollectionTimeEnd())
                        .toMillis()
        );
    }

    @Transactional
    public List<Order> getCustomerOrders(Long customer_id) {
        return orderRepository.findCustomerOrders(customer_id);
    }

    private String getOrderNumber(){
        String sample = "0A1B2C3D4E5F6G7H8I9J0K1L2M3N4O5P6Q7R8S9T0U1V2W3X4Y5Z6";
        while(true){
            String orderNumber = createOrderNumber(sample);
            Optional<Order> orderOptional = orderRepository.findOrderByOrderNumber(orderNumber);
            if(orderOptional.isEmpty()){
                return orderNumber;
            }
        }
    }

    private String createOrderNumber(String sample){
        StringBuilder result = new StringBuilder();
        for(int i = 0; i < 5; i++){
            Random random = new Random();
            int index = random.nextInt(sample.length());
            result.append(sample.charAt(index));
        }
        return result.toString();
    }

    @Transactional
    public void changeStatus(Long orderId, Integer status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalStateException("Order does not exist"));
//        if(LocalTime.now().isBefore(order.getStore().getCollectionTimeStart())){
//            throw new IllegalStateException("Collection time has not started yet");
//        }
        order.setStatus(status);
    }
}
