package neu.edu.skyfinder.configuration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import neu.edu.skyfinder.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder());
		provider.setUserDetailsService(userService);
		return provider;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		System.out.println(authConfig.getAuthenticationManager());
		return authConfig.getAuthenticationManager();
	}
	
	
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
		
		http.cors().configurationSource(request -> {
		    CorsConfiguration cors = new CorsConfiguration();
		    cors.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
		    cors.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
		    cors.setAllowCredentials(true);
		    cors.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
		    return cors;
		});

		
		//http.cors().disable();
		http.csrf().disable();
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		//http.authorizeRequests().antMatchers("/**").permitAll();
		
		http.authorizeRequests().antMatchers("/").permitAll();
		http.authorizeRequests().antMatchers("/user/testApp").permitAll();
		http.authorizeRequests().antMatchers("/auth").permitAll();
		http.authorizeRequests().antMatchers("/user/createUser").permitAll();
		http.authorizeRequests().antMatchers("/h2-admin/**").permitAll();
		http.authorizeRequests().antMatchers("/displayFlightsBasedOnInput").permitAll();
		
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/user/getAllusers").hasAuthority("ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/displayBookings").hasAuthority("ADMIN");

		
		http.authenticationProvider(authenticationProvider());
		http.authorizeRequests().anyRequest().authenticated();
		
		http.formLogin().disable();
		http.headers().frameOptions().sameOrigin();		

		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();

	}


}
