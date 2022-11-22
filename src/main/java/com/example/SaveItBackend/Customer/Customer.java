package com.example.SaveItBackend.Customer;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
public class Customer {

    @Id
    @SequenceGenerator(
            name = "customer_sequence",
            sequenceName = "customer_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "customer_sequence"
    )
    private Long id;
    private String name;
    private Long number;
    private String email;
    private String password;

    private LocalDate dob;

    public Customer(){}
    public Customer(Long id, String name, Long number, String email, String password, LocalDate dob) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.email = email;
        this.password = password;
        this.dob = dob;
    }

    public Customer(String name, Long number, String email, String password, LocalDate dob) {
        this.name = name;
        this.number = number;
        this.email = email;
        this.password = password;
        this.dob = dob;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getNumber() {
        return number;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", number=" + number +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", dob=" + dob +
                '}';
    }

}
