package spring.cloud.microservice.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final List<AccountDto> accounts = new ArrayList<>();


    @PostConstruct
    private void init() {
        accounts.add(AccountDto.builder().customerId(1L).currentBalance(Double.valueOf(10.0)).build());
        accounts.add(AccountDto.builder().customerId(2L).currentBalance(Double.valueOf(20.0)).build());
        accounts.add(AccountDto.builder().customerId(3L).currentBalance(Double.valueOf(30.0)).build());
    }

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

    @GetMapping()
    public ResponseEntity<List<AccountDto>> getAllAccounts() {

        return ResponseEntity.ok().body(accounts);
    }

    @GetMapping(path = "/{customerId}")
    public ResponseEntity<AccountDto> getAccountByCustomerId(@PathVariable Long customerId) {

        return ResponseEntity.ok().body(
                accounts.stream()
                .filter(a -> a.getCustomerId().longValue() == customerId.longValue())
                .findAny()
                .orElse(null));
    }


}
