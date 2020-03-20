package spring.cloud.microservice.customer.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final List<CustomerDto> customers = new ArrayList<>();

    @Autowired
    AccountServiceClient accountServiceClient;

    @PostConstruct
    private void init() {
        customers.add(CustomerDto.builder().customerId(1L).name("abc").age(25).build());
        customers.add(CustomerDto.builder().customerId(2L).name("xyz").age(37).build());
        customers.add(CustomerDto.builder().customerId(3L).name("qbt").age(43).build());
    }

    @GetMapping()
    public ResponseEntity<List<CustomerDto>> getCustomers() {

        log.info("getCustomers received");
        //customers.stream().forEach(customer -> customer.getAccounts().add(accountServiceClient.getAccountByCustomerId(customer.getCustomerId())));
        customers.stream().forEach(customer -> customer.setAccounts(accountServiceClient.getAccounts()));
        return ResponseEntity.ok().body(customers);
    }

    @GetMapping(path = "/{customerId}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable Long customerId) {

        log.info("getCustomerById received for customerId:{}", () -> customerId);
        CustomerDto customerDto = customers.stream()
                .filter(c -> c.getCustomerId().longValue() == customerId.longValue())
                .findAny()
                .orElse(null);

        if (null != customerDto)
            customerDto.setAccounts(Arrays.asList(accountServiceClient.getAccountByCustomerId(customerDto.getCustomerId())));

        return ResponseEntity.ok().body(customerDto);
    }

}
