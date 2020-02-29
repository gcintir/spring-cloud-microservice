package spring.cloud.microservice.user.service;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class LoginDto {

    @NotNull(message = "Email can not be null")
    @Email(message = "Invalid email")
    private String email;

    @NotNull(message = "Password can not be null")
    @Size(min = 5, max = 10, message = "Password length must be between 5 and 10 characters")
    private String password;

}
