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
    @JsonIgnore
    private String addressURL;
    private Double price;
    private LocalTime collectionTimeStart;
    private LocalTime collectionTimeEnd;
    private Integer boxesAmount;
    private String tags;
    @JsonIgnore
    @Column(columnDefinition="TEXT")
    private String description;

    @Lob
    private byte[] logoImage;

    @JsonIgnore
    @Lob
    private byte[] coverImage;

    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "store_id")
    private Set<Order> orders;

    public Store() {
    }

    public Store(Long id, String name, String email, String address, String addressURL, Double price,
                 LocalTime collectionTimeStart, LocalTime collectionTimeEnd, Integer boxesAmount, String description,
                 String tags, byte[] logoImage, byte[] coverImage) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.addressURL = addressURL;
        this.price = price;
        this.collectionTimeStart = collectionTimeStart;
        this.collectionTimeEnd = collectionTimeEnd;
        this.boxesAmount = boxesAmount;
        this.description = description;
        this.tags = tags;
        this.logoImage = logoImage;
        this.coverImage = coverImage;
    }

    public Store(String name, String email, String address, String addressURL, Double price,
                 LocalTime collectionTimeStart, LocalTime collectionTimeEnd, Integer boxesAmount, String description,
                 String tags, byte[] logoImage, byte[] coverImage) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.addressURL = addressURL;
        this.price = price;
        this.collectionTimeStart = collectionTimeStart;
        this.collectionTimeEnd = collectionTimeEnd;
        this.boxesAmount = boxesAmount;
        this.description = description;
        this.tags = tags;
        this.logoImage = logoImage;
        this.coverImage = coverImage;
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

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public String getAddressURL() {
        return addressURL;
    }

    public void setAddressURL(String addressURL) {
        this.addressURL = addressURL;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalTime getCollectionTimeStart() {
        return collectionTimeStart;
    }

    public void setCollectionTimeStart(LocalTime collectionTimeStart) {
        this.collectionTimeStart = collectionTimeStart;
    }

    public LocalTime getCollectionTimeEnd() {
        return collectionTimeEnd;
    }

    public void setCollectionTimeEnd(LocalTime collectionTimeEnd) {
        this.collectionTimeEnd = collectionTimeEnd;
    }

    public Integer getBoxesAmount() {
        return boxesAmount;
    }

    public void setBoxesAmount(Integer boxesAmount) {
        this.boxesAmount = boxesAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getLogoImage() {
        return logoImage;
    }

    public void setLogoImage(byte[] logoImage) {
        this.logoImage = logoImage;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public byte[] getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(byte[] coverImage) {
        this.coverImage = coverImage;
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
