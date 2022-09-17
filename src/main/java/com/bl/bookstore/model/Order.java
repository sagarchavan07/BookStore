package com.bl.bookstore.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserData user;
    private String address;
    @ElementCollection
    @CollectionTable(name = "order_books", joinColumns = @JoinColumn(name = "order_id"))
    private List<Long> bookIdList;
    @ElementCollection
    @CollectionTable(name = "order_book_quantities", joinColumns = @JoinColumn(name = "order_id"))
    private List<Long> quantities;
    private double price;
    private LocalDate purchaseDate;
    private boolean isCanceled;

    public Order() {
    }

    public Order(UserData user, String address, List<Long> bookIdList, List<Long> quantities, double price, LocalDate purchaseDate) {
        this.user = user;
        this.address = address;
        this.bookIdList = bookIdList;
        this.quantities = quantities;
        this.price = price;
        this.purchaseDate = purchaseDate;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public UserData getUser() {
        return user;
    }

    public void setUser(UserData user) {
        this.user = user;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Long> getBookIdList() {
        return bookIdList;
    }

    public void setBookIdList(List<Long> book) {
        this.bookIdList = book;
    }

    public List<Long> getQuantities() {
        return quantities;
    }

    public void setQuantities(List<Long> quantity) {
        this.quantities = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDateTime) {
        this.purchaseDate = purchaseDateTime;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public void setCanceled(boolean canceled) {
        isCanceled = canceled;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", user=" + user +
                ", address='" + address + '\'' +
                ", book=" + bookIdList +
                ", quantity=" + quantities +
                ", price=" + price +
                ", purchaseDate=" + purchaseDate +
                ", isCanceled=" + isCanceled +
                '}';
    }
}
