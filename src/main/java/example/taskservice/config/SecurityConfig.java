package example.taskservice.config;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.TimeZone;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    private static final String dateFormat = "yyyy-MM-dd ";

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            builder.simpleDateFormat(dateFormat);
            builder.timeZone(TimeZone.getTimeZone("GMT+1"));
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .httpBasic()
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .logout();
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager configure() throws Exception {
        UserDetails user = User
                .withDefaultPasswordEncoder()
                .username("UserMike")
                .password("123")
                .roles("USER")
                .build();
        UserDetails user2 = User
                .withDefaultPasswordEncoder()
                .username("AdminJohn")
                .password("321")
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, user2);
    }


}
