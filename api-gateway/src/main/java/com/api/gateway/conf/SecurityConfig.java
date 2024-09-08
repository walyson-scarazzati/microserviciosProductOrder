package com.api.gateway.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityWebFilterChain(ServerHttpSecurity serverHttpSecurity) {
        serverHttpSecurity.csrf()
        .disable()
        .authorizeExchange(exchange -> exchange
                        .pathMatchers("/eureka/**")
                        .permitAll()
                        .anyExchange()
                        .authenticated())
                        .oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::jwt);
               
        return serverHttpSecurity.build();
    }
	@Bean
	public ReactiveJwtDecoder jwtDecoder() {
		return NimbusReactiveJwtDecoder
				.withIssuerLocation("http://localhost:8081/realms/spring-boot-microservices-realm").build();
	}

}
