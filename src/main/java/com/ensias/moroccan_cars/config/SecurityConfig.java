package com.ensias.moroccan_cars.config;

import com.ensias.moroccan_cars.filters.JwtFilter;
import com.ensias.moroccan_cars.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final BCryptPasswordEncoder encoder;
    private final JwtFilter jwtFilter;
    @Value("${jwt.hdr}")
    private String header;

    public SecurityConfig(UserService userService, BCryptPasswordEncoder encoder, JwtFilter jwtFilter) {
        this.userService = userService;
        this.encoder = encoder;
        this.jwtFilter = jwtFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers(HttpMethod.POST,"/profile/user")
                .permitAll()
                .mvcMatchers(HttpMethod.GET,"/cars","/cars/**")
                .permitAll()
                .mvcMatchers(HttpMethod.POST,"/cars")
                .permitAll()
                .mvcMatchers(HttpMethod.GET,"/claim")
                .access("hasAuthority('Directeur') or hasAuthority('Responsable')")
                .mvcMatchers(HttpMethod.PUT,"/claim/*")
                .access("hasAuthority('Directeur') or hasAuthority('Responsable')")
                .mvcMatchers(HttpMethod.POST,"/cars/**")
                .hasAuthority("Directeur")
                .mvcMatchers(HttpMethod.DELETE,"/cars/**")
                .hasAuthority("Directeur")
                .antMatchers("/profile/login")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and().addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().accessDeniedHandler(new AccessDeniedHandler() {
            @Override
            public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
                Logger logger = LoggerFactory.getLogger(getClass().getName());
                logger.error(e.getMessage());
            }
        })
                .and().exceptionHandling().and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable()
        .cors();



    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
        .passwordEncoder(encoder);
    }
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Bean
    CorsConfigurationSource corsConfigurationSource()
    {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.addExposedHeader(header);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
