package by.itechart.api.configuration.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * This class is created to avoid cors and will be improved later. Current implementation is for test requests and
 * responses
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .disable()
                .authorizeRequests()
                .antMatchers("/user/**")
                .permitAll()
                .anyRequest()
                .authenticated();
    }
}
