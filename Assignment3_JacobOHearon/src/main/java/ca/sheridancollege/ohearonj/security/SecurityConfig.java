package ca.sheridancollege.ohearonj.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception 
	{
		MvcRequestMatcher.Builder mvc = new MvcRequestMatcher.Builder(introspector);
		
		return http
				.authorizeHttpRequests(authorize -> authorize.requestMatchers(mvc.pattern("/secure/**")).hasRole("USER")
				.requestMatchers(mvc.pattern("/")).permitAll().requestMatchers(mvc.pattern("/js/**"))
				.permitAll().requestMatchers(mvc.pattern("/css/**")).permitAll()
				.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST,"/register")).permitAll()
				.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/register")).permitAll()
				.requestMatchers(mvc.pattern("/images/**")).permitAll()
				.requestMatchers(mvc.pattern("/permission-denied")).permitAll()
				.requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
				.requestMatchers(mvc.pattern("/browseGames")).permitAll()
				.requestMatchers(mvc.pattern("/viewDetails/**")).permitAll()
				.requestMatchers(mvc.pattern("/index")).permitAll()
				.requestMatchers(mvc.pattern("/register")).permitAll()
				.requestMatchers(mvc.pattern("/**")).permitAll()
				.anyRequest().hasRole("ADMIN"))
				.csrf(csrf -> csrf.ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**"))
				.disable())
				.headers(headers -> headers.frameOptions(FrameOptionsConfig::disable))
				.formLogin(form -> form.loginPage("/login").permitAll())
				.exceptionHandling(exception -> exception.accessDeniedPage("/permission-denied"))
				.logout(logout -> logout.permitAll()).build();
	}

	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
}
