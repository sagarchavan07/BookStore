package com.bl.bookstore.service;

import com.bl.bookstore.dto.OrderDTO;
import com.bl.bookstore.email.EmailService;
import com.bl.bookstore.exception.BookStoreException;
import com.bl.bookstore.model.Book;
import com.bl.bookstore.model.Cart;
import com.bl.bookstore.model.Order;
import com.bl.bookstore.model.UserData;
import com.bl.bookstore.repository.BookRepository;
import com.bl.bookstore.repository.CartRepository;
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
    BookRepository bookRepository;
    @Autowired
    EmailService emailService;
    @Autowired
    CartRepository cartRepository;

    /**
     * place new order of books and quantity as passed in OrderDTO
     *
     * @param token
     * @param orderDTO
     */
    public Order placeOrder(String token, OrderDTO orderDTO) {
        List<Long> bookIdList = orderDTO.getBookIdList();
        List<Long> quantityList = orderDTO.getQuantityList();
        long userId = tokenUtility.decodeToken(token);
        UserData user = userService.getUserById(userId).orElseThrow(() -> new BookStoreException("User id " + userId + " Not found!"));
        /**
         *check the order quantity is not more than book quantity and calculate total price of books
         */
        double totalPrice = 0;
        for (int i = 0; i < orderDTO.getBookIdList().size(); i++) {
            Book book = bookRepository.findBookById(bookIdList.get(i));
            if (quantityList.get(i) > book.getQuantity())
                throw new BookStoreException("Book quantity exceeded for book id " + bookIdList.get(i));
            else {
                totalPrice += book.getPrice() * quantityList.get(i);
            }
        }
        LocalDate purchaseDate = LocalDate.now();
        Order order = new Order(user, user.getAddress(), bookIdList, orderDTO.getQuantityList(), totalPrice, purchaseDate);
        Order newOrder = orderRepository.save(order);
        /**
         *update the quantity of book in book repository as per order quantity
         */
        for (int i = 0; i < bookIdList.size(); i++) {
            long bookId = bookIdList.get(i);
            long orderQuantity = quantityList.get(i);
            Book book = bookRepository.findById(bookId).orElseThrow();
            bookService.updateQuantity(token, book.getBookId(), book.getQuantity() - orderQuantity);
        }
        /**
         * Send mail to user email when order is placed
         */
        emailService.sendEmail(user.getEmail(), "new Order Placed on BookSore", "Hello " + user.getFirstName() + user.getLastName() + ", Yous order of book id's " + bookIdList + " is placed successfully.");
        return newOrder;
    }

    /**
     * place order of books which is present in user's cart
     *
     * @param token
     * @param cartId
     * @return
     */
    public Order placeOrderByCart(String token, long cartId) {
        long userId = tokenUtility.decodeToken(token);
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new BookStoreException("Cart id " + cartId + " Not found!"));
        UserData user = userService.getUserById(userId).orElseThrow(() -> new BookStoreException("User id " + userId + " Not found!"));
        LocalDate purchaseDate = LocalDate.now();
        Order order = new Order(user, user.getAddress(), cart.getBookIdList(), cart.getQuantities(), cart.getTotalCartPrice(), purchaseDate);
        Order newOrder = orderRepository.save(order);
        /**
         *update the quantity of book in book repository as per order quantity
         */
        for (int i = 0; i < cart.getBookIdList().size(); i++) {
            long bookId = cart.getBookIdList().get(i);
            long orderQuantity = cart.getQuantities().get(i);
            Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookStoreException("Book id " + bookId + " Not found!"));
            bookService.updateQuantity(token, book.getBookId(), book.getQuantity() - orderQuantity);
        }
        /**
         * Send mail to user email when order is placed
         */
        emailService.sendEmail(user.getEmail(), "new Order Placed on BookSore", "Hello " + user.getFirstName() + user.getLastName() + ", Yous order of book id " + cart.getBookIdList() + " is placed successfully.");
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
        return "deleted order of id " + orderId + " by " + user.getFirstName();
    }

    public Order updateOrderById(String token, long orderId, OrderDTO orderDTO) {
        List<Long> bookIdList = orderDTO.getBookIdList();
        List<Long> quantityList = orderDTO.getQuantityList();
        long userId = tokenUtility.decodeToken(token);
        LocalDate purchaseDate = LocalDate.now();
        UserData user = userService.getUserById(userId).orElseThrow(() -> new BookStoreException("User of userId " + userId + " Not found!"));
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new BookStoreException("Order od userId " + orderId + " Not found!"));
        /**
         *check the order quantity is not more than book quantity and calculate total price of books
         */
        double price = 0;
        for (int i = 0; i < orderDTO.getBookIdList().size(); i++) {
            Book book = bookRepository.findBookById(bookIdList.get(i));
            if (quantityList.get(i) > book.getQuantity())
                throw new BookStoreException("Book quantity exceeded for book id " + bookIdList.get(i));
            else {
                price += book.getPrice() * quantityList.get(i);
            }
        }
        /**
         * update the current order with new data from OrderDTO and save it
         */
        order.setBookIdList(orderDTO.getBookIdList());
        order.setPrice(price);
        order.setPurchaseDate(purchaseDate);
        order.setUser(user);
        order.setAddress(user.getAddress());
        order.setQuantities(orderDTO.getQuantityList());
        Order updatedOrder = orderRepository.save(order);
        /**
         * Send mail to user email when order is updated
         */
        emailService.sendEmail(user.getEmail(), "Order updated on BookSore", "Hello " + user.getFirstName() + user.getLastName() + ", Yous order of book id " + orderDTO.getBookIdList() + " is updated successfully.");
        return updatedOrder;
    }
}

