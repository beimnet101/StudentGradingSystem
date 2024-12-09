package org.example.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
// DTO for login response
public  class LoginResponseDto {
    private String accessToken;

    public String getMsg() {
        return msg;
    }

    public LoginResponseDto(String accessToken, String msg) {
        this.accessToken = accessToken;
        this.msg = msg;
    }

    public Object setMsg(String msg) {
        this.msg = msg;
        return null;
    }

    private String msg;

    public LoginResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
