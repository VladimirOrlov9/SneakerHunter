package com.spbstu.SneakerHunter.config;

import com.spbstu.SneakerHunter.models.UserModel;
import com.spbstu.SneakerHunter.repos.UserRepo;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.time.LocalDateTime;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .mvcMatchers("/favorites/**").authenticated()
                .mvcMatchers("/**").permitAll()
                .and()
                .oauth2Login();
        System.out.println("555---------------------");
    }

    @Bean
    public PrincipalExtractor principalExtractor(UserRepo userRepo) {
        System.out.println("222---------------------");
        return map -> {
            String id = (String) map.get("id");

            UserModel user = userRepo.findById(id).orElseGet(() -> {
                UserModel newUser = new UserModel();
                newUser.setId(id);
                newUser.setEmail((String) map.get("email"));
                newUser.setGoods(null);

                System.out.println("123---------------------");
                newUser.setLastVisit(LocalDateTime.now());
                userRepo.save(newUser);
                return newUser;
            });



            return user;
        };
    }
}