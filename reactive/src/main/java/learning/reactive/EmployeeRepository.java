package learning.reactive;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Mono;

public interface EmployeeRepository extends ReactiveCrudRepository<Employee, Long> {

    Mono<Employee> findByName(String key);

}
