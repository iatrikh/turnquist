package learning.reactive;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.result.view.Rendering;

import reactor.core.publisher.Mono;

@Controller
public class HomeController {

    private final EmployeeRepository employeeRepo;

    public HomeController(EmployeeRepository employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @GetMapping("/")
    public Mono<Rendering> index() {

        return employeeRepo.findAll()
                .collectList()
                .map(employees -> Rendering
                        .view("index")
                        .modelAttribute("employees", employees)
                        .modelAttribute("newEmployee", new Employee("", ""))
                        .build());

        // return Flux.fromIterable(DATABASE.values())
        // .collectList()
        // .map(employees -> Rendering
        // .view("index")
        // .modelAttribute("employees", employees)
        // .modelAttribute("newEmployee", new Employee("", ""))
        // .build());
    }

    @PostMapping("/new-employee")
    Mono<String> newEmployee(@ModelAttribute Mono<Employee> newEmployee) {

        return newEmployee
                .flatMap(e -> {
                    Employee employeeToSave = new Employee(e.getName(), e.getRole());
                    return employeeRepo.save(employeeToSave);
                })
                .map(employee -> "redirect:/");

        // return newEmployee
        // .map(employee -> {
        // DATABASE.put(employee.name(), employee);
        // return "redirect:/";
        // });
    }
}
