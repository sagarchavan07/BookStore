package com.bl.bookstore.repository;

import com.bl.bookstore.model.Book;
import com.bl.bookstore.model.UserData;
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

//    @Query(value = "SELECT * from users u where u.email = :mail",nativeQuery = true)
//    UserData findByEmail(@Param("mail") String mail);
}
