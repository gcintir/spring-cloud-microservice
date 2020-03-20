package spring.cloud.microservice.customer.service;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountDto {

    private Long customerId;
    private Double currentBalance;
}
