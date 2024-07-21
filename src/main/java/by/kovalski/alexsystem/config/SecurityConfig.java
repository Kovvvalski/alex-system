package by.kovalski.alexsystem.config;

import by.kovalski.alexsystem.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final AuthenticationSuccessHandler successHandler;

  @Autowired
  public SecurityConfig(AuthenticationSuccessHandler successHandler) {
    this.successHandler = successHandler;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.csrf(c -> c.disable())
            .authorizeHttpRequests(request -> request
                    .requestMatchers("/admin")
                    .hasAuthority(Role.ADMIN.name())
                    .requestMatchers("/lecturer")
                    .hasAuthority(Role.LECTURER.name())
                    .requestMatchers("/student")
                    .hasAuthority(Role.STUDENT.name())
                    .anyRequest().authenticated())

            .formLogin(form -> form.loginPage("/login")
                    .loginProcessingUrl("/login")
                    .successHandler(successHandler).permitAll());
    return httpSecurity.build();
  }

//  @Autowired
//  public void configure(AuthenticationManagerBuilder builder) throws Exception {
//    builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//  }

}
