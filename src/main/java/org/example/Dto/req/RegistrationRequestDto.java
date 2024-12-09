package org.example.Dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//import org.example.cassiomolin.security.domain.Authority;
//import org.example.cassiomolin.security.domain.Authority;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequestDto {

        private String username;
        private String password;

        public String getUsername() {
                return username;
        }

        public void setUsername(String username) {
                this.username = username;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }
        //      private Authority authorities;


}
