package com.example.SaveItBackend.Store;

import com.example.SaveItBackend.Order.Order;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Set;

@Entity
@Table(name = "stores")
public class Store {

    @Id
    @SequenceGenerator(
            name = "store_sequence",
            sequenceName = "store_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "store_sequence"
    )

    private Long id;
    private String name;
    private String email;
    private String address;
    private LocalTime pickupTime;

    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "store_id")
    private Set<Order> orders;

    public Store() {
    }

    public Store(Long id, String name, String email, String address, LocalTime pickupTime) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.pickupTime = pickupTime;
    }

    public Store(String name, String email, String address, LocalTime pickupTime) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.pickupTime = pickupTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalTime getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(LocalTime pickupTime) {
        this.pickupTime = pickupTime;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "Store{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
