package com.vk.totality;

import com.vk.totality.user.TotalityUserDetailsService;
import com.vk.totality.user.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/login", "webjars/**", UserController.USER_PATH + "newUser").permitAll()
                .antMatchers("/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/*/game/").hasAnyRole("USER")  // "/index", "/user/**"
                //                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler)
                .and()
                .csrf().disable()
                .headers().frameOptions().disable();
    }


    //    @Override
    //    protected void configure(HttpSecurity http) throws Exception {
    //        http
    //                .authorizeRequests()
    //                .antMatchers(HttpMethod.GET, "/images/**", "/main.css", "/webjars/**").permitAll()
    //                .antMatchers(HttpMethod.GET, "/admin/**").hasRole("ADMIN")
    //                .and()
    //                .formLogin().permitAll()
    //                .and()
    //                .csrf().disable()
    //                .headers().frameOptions().disable()
    //                .and().logout().logoutSuccessUrl("/")
    //                .and().httpBasic();
    //    }
    //
    //
    @Autowired
    public void configureJPAUsers(AuthenticationManagerBuilder auth, TotalityUserDetailsService detailsService) throws Exception {
        auth.userDetailsService(detailsService);
    }

    //    @Autowired
    //    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    //
    //        auth.inMemoryAuthentication()
    //                .withUser("user").password("{noop}pass").roles("USER")
    //                .and()
    //                .withUser("admin").password("{noop}pass").roles("ADMIN");
    //    }
}
