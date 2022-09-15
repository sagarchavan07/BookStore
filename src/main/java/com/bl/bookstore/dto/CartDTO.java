package com.bl.bookstore.dto;

import com.bl.bookstore.model.UserData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    long userId;
    List<Integer> bookIdList;
    List<Integer> quantity;
}
