package com.example.test.config;

import com.example.test.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // kết hợp với @Bean để tạo thành 1 bean trong spring IOC
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // Để có thể phân quyền tại controller
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private AccountService accountService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountService).// Cấu hình UserDetailsService để khi xác thực người dùng sẽ gọi tới hàm loadUserByUsername()
                passwordEncoder(new BCryptPasswordEncoder());// Cấu hình phương thức để mã hoá mật khẩu
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
// config những API ko cần xác thực
                .antMatchers("/auth/login","/user/create").permitAll()

//// Config những API phải có Authority là Admin thì mới được truy cập
                .antMatchers(HttpMethod.GET, "/user").hasAuthority("Admin")
                .antMatchers(HttpMethod.DELETE, "/user/delete/{userId}").hasAuthority("Admin")

// Config những API phải có Authority là ADMIN hoặc User thì mới được truy cập
                .antMatchers("/user/update/{userId}","/user/{userId}").hasAnyAuthority("Admin", "User")
                .anyRequest().authenticated()// Những đường dẫn còn lại cần được xác thực
                .and().httpBasic()// Kích hoạt cấu hình http basic trong Spring Security

// tắt tính năng Cross-Site Request Forgery (CSRF) trong Spring Security.
                .and().cors().and().csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        Khai báo lớp filter đc thực hiện trước phân quyền
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override // Config cho đường dẫn (swagger) ko bị chặn bởi security
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/swagger-ui/**")
                .antMatchers("/swagger-resources/**")
                .antMatchers("/v3/api-docs/**");
    }
}

