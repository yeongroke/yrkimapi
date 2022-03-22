package com.yrkim.yrkimapi.config;

import com.yrkim.yrkimapi.security.JwtAuthenticationEntryPoint;
import com.yrkim.yrkimapi.security.JwtRequestFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import java.util.Arrays;

@Slf4j
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] exposeHeaders = {
            "Access-Control-Allow-Origin",
            "Access-Control-Allow-Credentials",
            "Access-Control-Allow-Methods",
            "Access-Control-Max-Age",
            "Access-Control-Allow-Headers",
            "Origin",
            "X-Requested-With",
            "Content-Type",
            "Accept",
            "Key",
            "Authorization",
            "Vary",
            "Access-Control-Expose-Headers",
            "Access-Control-Request-Headers"};

    static String[] allowedOrigin = {
            "https://localhost:3000",
            "https://localhost:9000",
            "http://rokidev.com",
            "https://rokidev.com",
            "*"
    };

    private static final String[] CONF_POST_PATHS = {
            "/sign-in",
            "/sign-up",
            "/board",
            "https://localhost:9000/sign-in"
    };

    private static final String[] CONF_GET_PATHS = {
            "/sign-in",
            "/sign-up",
            "/hello",
            "/board",
            "/board/*"
    };

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final UserDetailsService userDetailService;
    private final JwtRequestFilter jwtRequestFilter;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // AuthenticationManagerBuilder 를 통해 customSecurityUsersService 에 PasswordEncoder 를 등록
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
     * WebSecurityConfig 내에서 설정하는 경우는 필요가 없지만 외부에서 인증관리자를 사용하기 위한 설정
     * - 외부로 표출해 주는 메소드를 강제로 호출하여 @Bean으로 등록 ex) 로그인 컨트롤러에서 사용
     * */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors().configurationSource(corsConfigurationSource())
            .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, CONF_POST_PATHS).permitAll()
                .antMatchers(HttpMethod.GET, CONF_GET_PATHS).permitAll()
                .anyRequest().authenticated()
            .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity.addFilterBefore(jwtRequestFilter , UsernamePasswordAuthenticationFilter.class);
    }

    // WebSecurity : 보안 예외 처리
    // Security filter chain 적용할 필요가 전혀 없는 요청을 ignore하고 싶을 때 사용
    @Override
    public void configure(WebSecurity webSecurity) {
        webSecurity.ignoring().antMatchers(
                "/v2/api-docs",
                "/swagger/**",
                "/swagger-resources/**",
                "/api/**",
                "/swagger-ui.html",
                "/swagger-ui/**");
    }

    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setExposedHeaders(Arrays.asList(exposeHeaders));
        configuration.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", configuration);
        FilterRegistrationBean bean = new FilterRegistrationBean(
                new CorsFilter(source));
        return bean;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowedOrigins(Arrays.asList(allowedOrigin));
        corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
        corsConfiguration.setAllowedMethods(Arrays.asList("*"));
        corsConfiguration.setExposedHeaders(Arrays.asList(exposeHeaders));
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        return urlBasedCorsConfigurationSource;
    }
}
