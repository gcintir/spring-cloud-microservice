package spring.cloud.microservice.user.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("api/users")
@Log4j2
public class UsersController {

    @Autowired
    UserServiceImpl userService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<UserDto> save(@Valid @RequestBody UserDto userDto) {

        userDto = userService.createUser(userDto);

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping(path = {"/{userId}"}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<UserDto> get(
            @PathVariable(required = true) Long userId) {

        return new ResponseEntity<>(userService.getUser(userId), HttpStatus.OK);
    }

    @GetMapping(path = "/status")
    public ResponseEntity<String> statusString() {

        return ResponseEntity.ok().body("running");
    }

}
