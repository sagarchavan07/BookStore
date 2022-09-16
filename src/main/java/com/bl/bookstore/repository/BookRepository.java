package com.bl.bookstore.repository;

import com.bl.bookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(value = "select * from books book where book.book_name = :name",nativeQuery = true)
    List<Book> findBookByName(@Param("name") String name);

    @Query(value = "select * from books book where book.book_id = :id",nativeQuery = true)
    Book findBookById(@Param("id") long id);

    @Query(value = "select * from books book order by book.book_name",nativeQuery = true)
    List<Book> sortBooksAscending();
    @Query(value = "select * from books book order by book.book_name desc",nativeQuery = true)
    List<Book> sortBooksDescending();
}
