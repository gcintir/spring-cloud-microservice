package spring.cloud.microservice.user.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserDto createUser(UserDto userDetails);

    UserDto getUser(Long id);

    UserDto getUserDetailsByEmail(String email);



}
