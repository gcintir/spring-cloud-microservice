package spring.cloud.microservice.customer.service;

import feign.FeignException;
import feign.hystrix.FallbackFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "account-service", fallbackFactory = AccountServiceFallbackFactory.class)
public interface AccountServiceClient {

    @GetMapping("/account")
    public List<AccountDto> getAccounts();

    @GetMapping("/account/{customerId}")
    public AccountDto getAccountByCustomerId(@PathVariable Long customerId);
}

@Component
class AccountServiceFallbackFactory implements FallbackFactory<AccountServiceClient> {

    @Override
    public AccountServiceClient create(Throwable throwable) {
        return new AccountFallback(throwable);
    }
}

@Component
class AccountFallback implements AccountServiceClient {

    private final Throwable throwable;

    @Autowired
    public AccountFallback(Throwable cause) {
        this.throwable = cause;
    }

    @Override
    public List<AccountDto> getAccounts() {
        return null;
    }

    @Override
    public AccountDto getAccountByCustomerId(Long customerId) {

        if (throwable instanceof FeignException) {
            FeignException exception = (FeignException)throwable;
            if (exception.status() == 404) {

            }
        } else {

        }

        return null;
    }
}