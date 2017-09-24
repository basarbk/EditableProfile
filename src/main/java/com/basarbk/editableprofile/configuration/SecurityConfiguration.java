package com.basarbk.editableprofile.configuration;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	@Autowired
	UserAuthService authService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
			.csrf().disable()
			.headers().frameOptions().disable()
			.and()
				.exceptionHandling().authenticationEntryPoint(getAuthEntryPoint())
			.and()
				.authorizeRequests().antMatchers("/api/**").permitAll()
			.and()
				.authorizeRequests().antMatchers(HttpMethod.GET, "/api/profiles/self").authenticated()
			.and()
				.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/profiles/**").authenticated()
			.and()
				.httpBasic()
			.and()
				.formLogin().successHandler(new RESTAuthenticationSuccessHandler()).failureHandler(new RESTAuthenticationFailHandler())
			.and()
				.logout().logoutSuccessHandler(getLogoutSuccessHandler());
			
	}
	
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.userDetailsService(authService);
    }

    @Bean
    AuthenticationEntryPoint getAuthEntryPoint(){
    	return (req, res, authExp) -> {
    		res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    	};
    }
    
    @Bean
    LogoutSuccessHandler getLogoutSuccessHandler() {
		return (request, response, authentication) -> {
			response.setStatus(HttpServletResponse.SC_OK);
		};
    }
    
	private class RESTAuthenticationFailHandler extends SimpleUrlAuthenticationFailureHandler{
    	
		@Override
    	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    	}
    }
    
	private class RESTAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
    	
        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        	clearAuthenticationAttributes(request);
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }
}
