package spring.cloud.microservice.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Value("${account.id}")
    private String accountId;

    @Autowired
    private Environment environment;

    @GetMapping(path = "/status/check")
    public ResponseEntity<String> status() {

        return ResponseEntity.ok().body("account service working on port " + environment.getProperty("local.server.port"));
    }

    @GetMapping(path = "/info")
    public ResponseEntity<String> accountId() {

        return ResponseEntity.ok().body("accountId: " + accountId);
    }
}
