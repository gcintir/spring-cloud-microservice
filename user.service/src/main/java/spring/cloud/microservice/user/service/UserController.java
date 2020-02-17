package spring.cloud.microservice.user.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/users")
@Log4j2
public class UserController {

    @Autowired
    private Environment environment;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping(path = "/status/check")
    public ResponseEntity<Object> status() {

        return ResponseEntity.ok().body("working on port " + environment.getProperty("local.server.port"));
    }

    @GetMapping(path = "/info/account")
    public ResponseEntity<Object> account() {

        ResponseEntity<String> result = restTemplate.getForEntity("http://account-service/account/status/check", String.class);

        return ResponseEntity.ok().body(result);
    }

    @GetMapping(path = "/info/account2")
    public ResponseEntity<Object> account2() {

        List<String> serviceIds = discoveryClient.getServices();
        if (serviceIds == null && serviceIds.isEmpty()) {
            log.info("There is any service in registry");
        } else {
            discoveryClient.getServices().stream().forEach(s -> log.info("serviceId: " + s));
        }

        List<ServiceInstance> instances = discoveryClient.getInstances("account-service");
        instances.stream().forEach(i -> {
            log.info("id: {} uri: {}", i.getInstanceId(), i.getUri().toString());
        });

        String host = instances.get(0).getHost();
        int port = instances.get(0).getPort();

        ResponseEntity<String> result = restTemplate.getForEntity(instances.get(0).getUri().toString() + "/account/status/check", String.class);

        return ResponseEntity.ok().body(result);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
