package learning.springboot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    // interface GrantedAuthorityCnv extends Converter<String, GrantedAuthority> {
    // }

    // @Bean
    // @ConfigurationPropertiesBinding
    // GrantedAuthorityCnv converter() {
    //     return SimpleGrantedAuthority::new;
    // }

    // @Bean
    // @ConfigurationPropertiesBinding
    // Converter<String, GrantedAuthority> converter() {
    // return new Converter<String, GrantedAuthority>() {
    // @Override
    // public GrantedAuthority convert(String source) {
    // return new SimpleGrantedAuthority(source);
    // }
    // };
    // }

    @Bean
    UserDetailsService userService(UserRepository userRepo) {
        return username -> userRepo.findByUsername(username).asUser();
    }

    // @Bean
    // // @Profile("setup")
    // CommandLineRunner initUsers(UserManagementRepository userManageRepo, AppConfig appConfig) {
    //     return args -> userManageRepo.saveAll(appConfig.users());
    // }

    @Bean
    CommandLineRunner initUsers(UserManagementRepository userManageRepo) {
    return args -> {
    userManageRepo.save(new UserAccount("user", "pass", "ROLE_USER"));
    userManageRepo.save(new UserAccount("alice", "pass", "ROLE_USER"));
    userManageRepo.save(new UserAccount("bob", "pass", "ROLE_USER"));
    userManageRepo.save(new UserAccount("admin", "admin", "ROLE_ADMIN"));
    };
    }

    // <input type="hidden" name="{{_csrf.parameterName}}" value="{{_csrf.token}}">

    @Bean
    SecurityFilterChain configureSecurity(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(req -> req.requestMatchers("/login").permitAll()
                .requestMatchers("/", "/multi-field-search", "/universal-search").authenticated()
                .requestMatchers(HttpMethod.POST, "/delete/**", "/new-video").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN")
                .requestMatchers("/admin").hasRole("ADMIN")
                .anyRequest().denyAll())
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .csrf((csrf) -> csrf.disable());

        return http.build();
    }

    // @Bean
    // SecurityFilterChain configureSecurity(HttpSecurity http) throws Exception {
    // http.authorizeHttpRequests()
    // .requestMatchers("/login").permitAll()
    // // .requestMatchers("/", "/multi-field-search",
    // // "/universal-search").authenticated()
    // .requestMatchers("/", "/search").authenticated()
    // .requestMatchers(HttpMethod.GET, "/api/**").authenticated()
    // .requestMatchers("/admin").hasRole("ADMIN")
    // .requestMatchers(HttpMethod.POST, "/new-video", "/api/**").hasRole("ADMIN")
    // // .anyRequest().denyAll()
    // .and()
    // .formLogin()
    // .and()
    // .httpBasic();
    // return http.build();
    // }

    // @Bean
    // SecurityFilterChain configureSecurity(HttpSecurity http) throws Exception {
    // http.authorizeHttpRequests()
    // .requestMatchers("/login").permitAll()
    // .requestMatchers("/", "/search").hasRole("USER")
    // .requestMatchers(HttpMethod.GET, "/api/**").hasRole("USER")
    // .requestMatchers(HttpMethod.POST, "/new-video", "/api/**").hasRole("ADMIN")
    // //
    // // .anyRequest().denyAll() //
    // .and() //
    // .formLogin()
    // // .and()
    // // .logout()
    // // .logoutSuccessUrl("/login")
    // .and() //
    // .httpBasic() //
    // .and() //
    // .csrf().disable();
    // return http.build();
    // }
}
