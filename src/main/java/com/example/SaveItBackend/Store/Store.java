package com.example.SaveItBackend.Store;

import com.example.SaveItBackend.Order.Order;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
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
    private String addressURL;
    private LocalDate creationDate;
    private Double price;
    private Double priceWithoutDiscount;
    private LocalTime collectionTimeStart;
    private LocalTime collectionTimeEnd;
    private Integer defaultBoxesAmount;
    private Integer currentBoxesAmount;
    private String tags;
    private String password;
    @Column(columnDefinition="TEXT")
    private String description;

    @JsonIgnore
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

    public Store(Long id, String name, String email, String address, String addressURL, LocalDate creationDate, Double price,
                 Double priceWithoutDiscount, LocalTime collectionTimeStart, LocalTime collectionTimeEnd,
                 Integer defaultBoxesAmount, Integer currentBoxesAmount, String tags, String password, String description, byte[] logoImage, byte[] coverImage) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.addressURL = addressURL;
        this.creationDate = creationDate;
        this.price = price;
        this.priceWithoutDiscount = priceWithoutDiscount;
        this.collectionTimeStart = collectionTimeStart;
        this.collectionTimeEnd = collectionTimeEnd;
        this.defaultBoxesAmount = defaultBoxesAmount;
        this.currentBoxesAmount = currentBoxesAmount;
        this.tags = tags;
        this.password = password;
        this.description = description;
        this.logoImage = logoImage;
        this.coverImage = coverImage;
    }

    public Store(String name, String email, String address, String addressURL, LocalDate creationDate, Double price,
                 Double priceWithoutDiscount, LocalTime collectionTimeStart, LocalTime collectionTimeEnd,
                 Integer defaultBoxesAmount, Integer currentBoxesAmount, String tags, String password,
                 String description, byte[] logoImage, byte[] coverImage) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.addressURL = addressURL;
        this.creationDate = creationDate;
        this.price = price;
        this.priceWithoutDiscount = priceWithoutDiscount;
        this.collectionTimeStart = collectionTimeStart;
        this.collectionTimeEnd = collectionTimeEnd;
        this.defaultBoxesAmount = defaultBoxesAmount;
        this.currentBoxesAmount = currentBoxesAmount;
        this.tags = tags;
        this.password = password;
        this.description = description;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public LocalDate getCreationDate() {return creationDate;}

    public void setCreationDate(LocalDate creationDate) {this.creationDate = creationDate;}

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

    public Integer getDefaultBoxesAmount() {
        return defaultBoxesAmount;
    }

    public void setDefaultBoxesAmount(Integer boxesAmount) {
        this.defaultBoxesAmount = boxesAmount;
    }

    public Integer getCurrentBoxesAmount() {
        return currentBoxesAmount;
    }

    public void setCurrentBoxesAmount(Integer currentBoxesAmount) {
        this.currentBoxesAmount = currentBoxesAmount;
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

    public Double getPriceWithoutDiscount() {
        return priceWithoutDiscount;
    }

    public void setPriceWithoutDiscount(Double priceWithoutDiscount) {
        this.priceWithoutDiscount = priceWithoutDiscount;
    }

    @Override
    public String toString() {
        return "Store{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", addressURL='" + addressURL + '\'' +
                ", creationDate=" + creationDate +
                ", price=" + price +
                ", priceWithoutDiscount=" + priceWithoutDiscount +
                ", collectionTimeStart=" + collectionTimeStart +
                ", collectionTimeEnd=" + collectionTimeEnd +
                ", defaultBoxesAmount=" + defaultBoxesAmount +
                ", currentBoxesAmount=" + currentBoxesAmount +
                ", tags='" + tags + '\'' +
                ", description='" + description + '\'' +
                ", orders=" + orders +
                '}';
    }
}
