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
    private List<Integer> BookIdList;
    @ElementCollection
    @CollectionTable(name = "cart_book_quantity",joinColumns = @JoinColumn(name = "cart_id"))
    private List<Integer> quantity;

    public Cart(Long cartId, UserData userData, List<Integer> bookIdList, List<Integer> quantity) {
        this.cartId = cartId;
        this.userData = userData;
        BookIdList = bookIdList;
        this.quantity = quantity;
    }
    public Cart(UserData userData, List<Integer> bookIdList, List<Integer> quantity) {
        this.userData = userData;
        BookIdList = bookIdList;
        this.quantity = quantity;
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

    public List<Integer> getBookIdList() {
        return BookIdList;
    }

    public void setBookIdList(List<Integer> bookIdList) {
        BookIdList = bookIdList;
    }

    public List<Integer> getQuantity() {
        return quantity;
    }

    public void setQuantity(List<Integer> quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartId=" + cartId +
                ", userData=" + userData +
                ", BookIdList=" + BookIdList +
                ", quantity=" + quantity +
                '}';
    }
}
