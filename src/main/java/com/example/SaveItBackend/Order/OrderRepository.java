package com.example.SaveItBackend.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE o.customer.id = ?1")
    List<Order> findCustomerOrders(Long customer_id);

    @Query("SELECT n FROM Order n WHERE n.orderNumber = ?1")
    Optional<Order> findOrderByOrderNumber(String orderNumber);

    @Query("SELECT o FROM Order o WHERE o.store.id = ?1 AND o.orderDate = ?2")
    List<Order> findStoreOrders(Long store_id, String orderDate);

    @Query("SELECT SUM(o.boxesAmount), SUM(o.totalPrice) FROM Order o WHERE o.store.id = ?1 AND o.orderDate = ?2")
    String getDayHistoryDetails(Long store_id, String orderDate);

}
