package org.example.Dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.cassiomolin.user.domain.User;
import org.example.model.Book;


import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanReq {
    private String bookTitle;
    //private User user;
    private Date dateLoaned;
    private Date dueDate;
    //private Date dateReturned;
    //private String bookStatus;
}
