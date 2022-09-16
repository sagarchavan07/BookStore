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
        UserData user = userService.getUserById(userId).orElseThrow(()->new BookStoreException("User id " + userId + " Not found!"));
        Book book = bookService.getBookById(token,orderDTO.getBookId());
        double price = book.getPrice()*orderDTO.getQuantity();
        LocalDate purchaseDate = LocalDate.now();
        Order order = new Order(user,user.getAddress(),book,orderDTO.getQuantity(), price,purchaseDate);
        Order myOrder = orderRepository.save(order);
        emailService.sendEmail(user.getEmail(),"new Order Placed on BookSore","Hello "+user.getFirstName()+user.getLastName()+", Yous order of book "+book.getBookName()+" quantity "+order.getQuantity()+" is placed successfully.");
        return myOrder;
    }
    public List<Order> getAllOrders(String token) {
        long userId = tokenUtility.decodeToken(token);
        UserData user = userService.getUserById(userId).orElseThrow(()->new BookStoreException("User id " + userId + " Not found!"));
        if (user.isAdmin())
            return orderRepository.findAll();
        else throw new BookStoreException("User is not Admin");
    }
}
