package spring.cloud.microservice.customer.service;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CustomerDto {

    private Long customerId;
    private String name;
    private int age;
    private List<AccountDto> accounts;
}
