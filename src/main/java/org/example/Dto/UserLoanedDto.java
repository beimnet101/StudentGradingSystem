package org.example.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//import org.example.cassiomolin.user.domain.User;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoanedDto {
    private Date dateLoaned;
    private String bookTitle;
    private String bookCatagory;
    private List<userDto> user;
    private Date dueDate;

}
