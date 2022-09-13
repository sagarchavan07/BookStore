package com.bl.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    private String bookName;
    private String authorName;
    private String bookDescription;
    private String bookImg;
    private String price;
    private long quantity;
}
