package com.example.SaveItBackend.Order;

import com.example.SaveItBackend.Customer.Customer;
import com.example.SaveItBackend.Store.Store;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @SequenceGenerator(
            name = "orders_sequence",
            sequenceName = "orders_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "orders_sequence"
    )

    private Long id;
    private String orderNumber;
    private LocalDate orderDate;
    private LocalTime orderTime;
    private Integer status;
    private Integer boxesAmount;
    private Double pricePerBox;
    private Double priceWithoutDiscount;
    private Double totalPrice;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    public Order() {
    }

    public Order(Long id, String orderNumber, LocalDate orderDate, LocalTime orderTime, Integer status, Integer boxesAmount, Double pricePerBox,
                 Double priceWithoutDiscount, Double totalPrice) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.status = status;
        this.boxesAmount = boxesAmount;
        this.pricePerBox = pricePerBox;
        this.priceWithoutDiscount = priceWithoutDiscount;
        this.totalPrice = totalPrice;
    }

    public Order(String orderNumber, LocalDate orderDate, LocalTime orderTime, Integer status, Integer boxesAmount, Double pricePerBox,
                 Double priceWithoutDiscount, Double totalPrice) {
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.status = status;
        this.boxesAmount = boxesAmount;
        this.pricePerBox = pricePerBox;
        this.priceWithoutDiscount = priceWithoutDiscount;
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public LocalTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalTime orderTime) {
        this.orderTime = orderTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Integer getBoxesAmount() {
        return boxesAmount;
    }

    public void setBoxesAmount(Integer boxesAmount) {
        this.boxesAmount = boxesAmount;
    }

    public Double getPricePerBox() {
        return pricePerBox;
    }

    public void setPricePerBox(Double pricePerBox) {
        this.pricePerBox = pricePerBox;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getPriceWithoutDiscount() {
        return priceWithoutDiscount;
    }

    public void setPriceWithoutDiscount(Double priceWithoutDiscount) {
        this.priceWithoutDiscount = priceWithoutDiscount;
    }
}
