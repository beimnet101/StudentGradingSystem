package org.example.Dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.Book;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CatagoryReq {
    private String catagory;
    private Book book;
}
