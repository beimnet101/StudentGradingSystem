package org.example.Dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.Catagory;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookResDto {
    private String title;
    private Catagory catagory;
    private Integer count;
    private String bookStatus;
    //private StatusDto statusDto;
}
