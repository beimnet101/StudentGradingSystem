package org.example.Dto.req;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserReq {
    private String name;
    private String phone_number;
    private String password;
    private String role;
    private String conformPassword;

}
