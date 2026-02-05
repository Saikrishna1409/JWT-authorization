package jwt.security.dto;

import lombok.Data;
@Data
public class RegisterRequest {
    public String username;
    public String password;
}
