package learning.reactive;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ApiController {

    public static Map<String, Employee> DATABASE = new LinkedHashMap<>();

    static {
        DATABASE.put("alice", new Employee("alice", "management"));
        DATABASE.put("bob", new Employee("bob", "payroll"));
    }

    @GetMapping("/api/employees")
    public Flux<Employee> employees() {

        return Flux.fromIterable(DATABASE.values());

        // return Flux.just(
        // new Employee("alice", "management"),
        // new Employee("bob", "payroll"));
    }

    @PostMapping("/api/employees")
    public Mono<Employee> add(@RequestBody Mono<Employee> newEmployee) {
        return newEmployee.map(employee -> {
            DATABASE.put(employee.name(), employee);
            return employee;
        });
    }
}
