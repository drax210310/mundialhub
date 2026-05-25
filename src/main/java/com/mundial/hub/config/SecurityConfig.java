package com.mundial.hub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable()).headers(headers -> headers.frameOptions(frame -> frame.disable()))
				.authorizeHttpRequests(auth -> auth

						// 🔥 AQUÍ ESTÁN LAS RUTAS DE SWAGGER CORRECTAMENTE CONFIGURADAS
						.requestMatchers("/", "/index.html", "/login.html", "/registro.html", "/css/**", "/js/**",
								"/h2-console/**", "/api/usuarios/registro", "/api/partidos", "/swagger-ui.html",
								"/swagger-ui/**", "/v3/api-docs/**")
						.permitAll()

						.requestMatchers("/dashboard-admin.html").hasRole("ADMIN")
						.requestMatchers("/dashboard-operador.html").hasRole("OPERADOR")
						.requestMatchers("/dashboard-usuario.html").hasRole("USUARIO")
						.requestMatchers("/dashboard-album.html").hasRole("USUARIO")
						.requestMatchers("/dashboard-pollas.html").hasRole("USUARIO")

						// RUTA PARA EL PERFIL
						.requestMatchers("/perfil.html").hasRole("USUARIO")

						.anyRequest().authenticated())
				.formLogin(login -> login.loginPage("/login.html").loginProcessingUrl("/login")
						.usernameParameter("username").passwordParameter("password")
						.successHandler((request, response, authentication) -> {

							boolean esAdmin = authentication.getAuthorities().stream()
									.anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
							boolean esOperador = authentication.getAuthorities().stream()
									.anyMatch(a -> a.getAuthority().equals("ROLE_OPERADOR"));
							boolean esUsuario = authentication.getAuthorities().stream()
									.anyMatch(a -> a.getAuthority().equals("ROLE_USUARIO"));

							if (esAdmin) {
								response.sendRedirect("/dashboard-admin.html");
							} else if (esOperador) {
								response.sendRedirect("/dashboard-operador.html");
							} else if (esUsuario) {
								response.sendRedirect("/dashboard-usuario.html");
							} else {
								response.sendRedirect("/login.html?error=true");
							}
						}).failureUrl("/login.html?error=true").permitAll())
				.logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/login.html").permitAll());

		return http.build();
	}
}