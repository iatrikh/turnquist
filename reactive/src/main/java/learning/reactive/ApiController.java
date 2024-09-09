package learning.reactive;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ApiController {

    // public static Map<String, Employee> DATABASE = new LinkedHashMap<>();

    // static {
    // DATABASE.put("alice", new Employee("alice", "management"));
    // DATABASE.put("bob", new Employee("bob", "payroll"));
    // }

    private final EmployeeRepository employeeRepo;

    public ApiController(EmployeeRepository employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @GetMapping("/api/employees")
    public Flux<Employee> employees() {

        return employeeRepo.findAll();

        // return Flux.fromIterable(DATABASE.values());

        // return Flux.just(
        // new Employee("alice", "management"),
        // new Employee("bob", "payroll"));
    }

    @PostMapping("/api/employees")
    public Mono<Employee> add(@RequestBody Mono<Employee> newEmployee) {

        // return employeeRepo.save(newEmployee);

        return newEmployee.flatMap(e -> {
            Employee employeeToLoad = new Employee(e.getName(), e.getRole());
            return employeeRepo.save(employeeToLoad);
        });

        // return newEmployee.map(employee -> {
        // DATABASE.put(employee.name(), employee);
        // return employee;
        // });
    }
}
