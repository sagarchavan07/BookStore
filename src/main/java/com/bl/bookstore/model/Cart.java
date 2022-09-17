package com.bl.bookstore.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Cart {
    @Id
    private Long cartId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserData userData;
    @ElementCollection
    @CollectionTable(name = "cart_books",joinColumns = @JoinColumn(name = "cart_id"))
    private List<Long> BookIdList;
    @ElementCollection
    @CollectionTable(name = "cart_book_quantity",joinColumns = @JoinColumn(name = "cart_id"))
    private List<Long> quantities;
    private double totalCartPrice;

    public Cart(Long cartId, UserData userData, List<Long> bookIdList, List<Long> quantities, double totalCartPrice) {
        this.cartId = cartId;
        this.userData = userData;
        BookIdList = bookIdList;
        this.quantities = quantities;
        this.totalCartPrice = totalCartPrice;
    }
    public Cart(UserData userData, List<Long> bookIdList, List<Long> quantities) {
        this.userData = userData;
        BookIdList = bookIdList;
        this.quantities = quantities;
    }

    public Cart() {

    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public List<Long> getBookIdList() {
        return BookIdList;
    }

    public void setBookIdList(List<Long> bookIdList) {
        BookIdList = bookIdList;
    }

    public List<Long> getQuantities() {
        return quantities;
    }

    public void setQuantities(List<Long> quantity) {
        this.quantities = quantity;
    }

    public double getTotalCartPrice() {
        return totalCartPrice;
    }

    public void setTotalCartPrice(double totalCartPrice) {
        this.totalCartPrice = totalCartPrice;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartId=" + cartId +
                ", userData=" + userData +
                ", BookIdList=" + BookIdList +
                ", quantity=" + quantities +
                '}';
    }
}
