package jwt.security.dto;

import lombok.Data;

@Data
public class LoginRequest {
    public String username;
    public String password;
}
