// package learning.reactive;

// import static
// org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.linkTo;
// import static
// org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;

// import org.springframework.hateoas.CollectionModel;
// import org.springframework.hateoas.EntityModel;
// import org.springframework.hateoas.Link;
// import org.springframework.hateoas.LinkRelation;
// import org.springframework.hateoas.config.EnableHypermediaSupport;
// import
// org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RestController;

// import reactor.core.publisher.Mono;
// import reactor.util.function.Tuple2;

// @RestController
// @EnableHypermediaSupport(type = HypermediaType.HAL)
// public class HypermediaController {

// private final EmployeeRepository employeeRepo;

// public HypermediaController(EmployeeRepository employeeRepo) {
// this.employeeRepo = employeeRepo;
// }

// @GetMapping("/hypermedia/employees/{key}")
// public Mono<EntityModel<Employee>> employee(@PathVariable String key) {

// Mono<Link> selfLink = linkTo(
// methodOn(HypermediaController.class)
// .employee(key))
// .withSelfRel()
// .toMono();

// Mono<Link> aggregateRoot = linkTo(
// methodOn(HypermediaController.class)
// .employees())
// .withRel(LinkRelation.of("employees"))
// .toMono();

// Mono<Tuple2<Link, Link>> links = Mono.zip(selfLink, aggregateRoot);

// return links.map(objects -> EntityModel.of(
// employeeRepo.findByName(key).block(),
// objects.getT1(),
// objects.getT2()));
// }

// @GetMapping("/hypermedia/employees")
// public Mono<CollectionModel<EntityModel<Employee>>> employees() {

// Mono<Link> selfLink = linkTo(
// methodOn(HypermediaController.class)
// .employees())
// .withSelfRel()
// .toMono();

// return selfLink
// .flatMap(self -> employeeRepo.findAll().map(e -> e.getName())
// .flatMap(key -> employee(key))
// .collectList()
// .map(entityModels -> CollectionModel.of(entityModels, self)));

// // return selfLink
// // .flatMap(self -> Flux.fromIterable(DATABASE.keySet())
// // .flatMap(key -> employee(key))
// // .collectList()
// // .map(entityModels -> CollectionModel.of(entityModels, self)));
// }
// }