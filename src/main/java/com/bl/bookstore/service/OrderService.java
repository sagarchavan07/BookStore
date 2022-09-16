package com.bl.bookstore.service;

import com.bl.bookstore.dto.OrderDTO;
import com.bl.bookstore.email.EmailService;
import com.bl.bookstore.exception.BookStoreException;
import com.bl.bookstore.model.Book;
import com.bl.bookstore.model.Order;
import com.bl.bookstore.model.UserData;
import com.bl.bookstore.repository.OrderRepository;
import com.bl.bookstore.utility.TokenUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    TokenUtility tokenUtility;
    @Autowired
    UserService userService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    BookService bookService;
    @Autowired
    EmailService emailService;

    public Order placeOrder(String token, OrderDTO orderDTO) {
        long userId = tokenUtility.decodeToken(token);
        UserData user = userService.getUserById(userId).orElseThrow(() -> new BookStoreException("User id " + userId + " Not found!"));
        Book book = bookService.getBookById(token, orderDTO.getBookId());
        double price = book.getPrice() * orderDTO.getQuantity();
        LocalDate purchaseDate = LocalDate.now();
        Order order = new Order(user, user.getAddress(), book, orderDTO.getQuantity(), price, purchaseDate);
        Order newOrder = orderRepository.save(order);
        bookService.updateQuantity(token,book.getBookId(),book.getQuantity()-order.getQuantity());
//        emailService.sendEmail(user.getEmail(), "new Order Placed on BookSore", "Hello " + user.getFirstName() + user.getLastName() + ", Yous order of book " + book.getBookName() + " quantity " + order.getQuantity() + " is placed successfully.");
        return newOrder;
    }

    public List<Order> getAllOrders(String token) {
        long userId = tokenUtility.decodeToken(token);
        UserData user = userService.getUserById(userId).orElseThrow(() -> new BookStoreException("User id " + userId + " Not found!"));
        if (!user.isAdmin()) throw new BookStoreException("User is not Admin");
        if (orderRepository.findAll().isEmpty()) throw new BookStoreException("User table is empty");
        else return orderRepository.findAll();
    }

    public Order getOrderById(String token, long orderId) {
        long userId = tokenUtility.decodeToken(token);
        return orderRepository.findById(orderId).orElseThrow(() -> new BookStoreException("order id " + orderId + " not found"));
    }

    public String deleteOrderById(String token, long orderId) {
        long userId = tokenUtility.decodeToken(token);
        UserData user = userService.getUserById(userId).orElseThrow(() -> new BookStoreException("User id " + userId + " Not found!"));
        orderRepository.deleteById(orderId);
        return "deleted order of id "+orderId +" by "+ user.getFirstName();
    }
    public Order updateOrderById(String token, long orderId, OrderDTO orderDTO) {
        long userId = tokenUtility.decodeToken(token);
        UserData user = userService.getUserById(userId).orElseThrow(() -> new BookStoreException("User of userId " + userId + " Not found!"));
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new BookStoreException("Order od userId " + orderId + " Not found!"));
        Book book = bookService.getBookById(token, orderDTO.getBookId());
        order.setBook(book);
        double price = book.getPrice() * orderDTO.getQuantity();
        order.setPrice(price);
        LocalDate purchaseDate = LocalDate.now();
        order.setPurchaseDate(purchaseDate);
        order.setUser(user);
        order.setAddress(user.getAddress());
        order.setQuantity(orderDTO.getQuantity());
        Order updatedOrder = orderRepository.save(order);
//        emailService.sendEmail(user.getEmail(), "Order updated on BookSore", "Hello " + user.getFirstName() + user.getLastName() + ", Yous order of book " + book.getBookName() + " quantity " + order.getQuantity() + " is updated successfully.");
        return updatedOrder;
    }
}

