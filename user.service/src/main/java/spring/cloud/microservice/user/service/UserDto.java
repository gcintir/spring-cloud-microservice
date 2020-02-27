package spring.cloud.microservice.user.service;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UserDto implements Serializable {


    private String userId;

    @NotNull(message = "First name can not be null")
    private String firstName;

    @NotNull(message = "Last name can not be null")
    private String lastName;

    @NotNull(message = "Email can not be null")
    @Email(message = "Invalid email")
    private String email;

    @NotNull(message = "Password can not be null")
    @Size(min = 5, max = 10, message = "Password length must be between 5 and 10 characters")
    private String password;

    private String encryptedPassword;

    public UserDto copyFromOther(UserDto other) {

        this.setFirstName(other.getFirstName());
        this.setLastName(other.getLastName());
        this.setEmail(other.getEmail());
        this.setPassword(other.getPassword());
        this.setEncryptedPassword(other.getEncryptedPassword());

        return this;

    }

}

