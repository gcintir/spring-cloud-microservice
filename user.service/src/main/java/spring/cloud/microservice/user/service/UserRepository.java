package spring.cloud.microservice.user.service;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

    boolean existsById(Long id);

    UserEntity findByEmail(String email);
}
