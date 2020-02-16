package spring.cloud.microservice.account.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {

    @GetMapping(path = "/status/check")
    public ResponseEntity<Object> status() {
        return ResponseEntity.ok().body("working");
    }
}
