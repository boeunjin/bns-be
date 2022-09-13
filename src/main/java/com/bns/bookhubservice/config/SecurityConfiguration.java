package com.bns.bookhubservice.config;

import com.bns.bookhubservice.config.oauth.CustomOAuth2UserService;
import com.bns.bookhubservice.config.oauth.handler.SecurityLoginFailureHandler;
import com.bns.bookhubservice.config.oauth.handler.SecurityLoginSuccessHandler;
import com.bns.bookhubservice.config.oauth.repository.HttpCookieOAuth2AuthorizationRequestRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.frameoptions.WhiteListedAllowFromStrategy;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// oauth2 과정
// 1.코드받기(인증), 2.액세스토큰(권한),
// 3-1.사용자프로필 정보를 가지고 회원가입 진행
// 3-2. 기본 데이터 (이메일, 전화번호, 이름, 아이디)
// 쇼핑몰 -> (집주소) 정보 필요 / 백화점몰 -> (vip, 일반등급) 정보 필요

@EnableWebSecurity
@Slf4j
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private final CustomOAuth2UserService customOAuth2UserService;

	private final SecurityLoginSuccessHandler securityLoginSuccessHandler;


	private final SecurityLoginFailureHandler securityLoginFailureHandler;

	private final HttpCookieOAuth2AuthorizationRequestRepository
			httpCookieOAuth2AuthorizationRequestRepository;

	public SecurityConfiguration(
			CustomOAuth2UserService customOAuth2UserService,
			SecurityLoginSuccessHandler securityLoginSuccessHandler,
			SecurityLoginFailureHandler securityLoginFailureHandler,
			HttpCookieOAuth2AuthorizationRequestRepository
					httpCookieOAuth2AuthorizationRequestRepository) {
		this.customOAuth2UserService = customOAuth2UserService;
		this.securityLoginSuccessHandler = securityLoginSuccessHandler;
		this.securityLoginFailureHandler = securityLoginFailureHandler;
		this.httpCookieOAuth2AuthorizationRequestRepository =
				httpCookieOAuth2AuthorizationRequestRepository;
	}

	@Override
	public void configure(WebSecurity web) throws Exception{
		web.ignoring().antMatchers("/static/css/**, /static/js/**, *.ico");
		// swagger
		web.ignoring().antMatchers(
				"/v2/api-docs", "/v3/api-docs", "/configuration/ui",
				"/swagger-resources", "/configuration/security",
				"/swagger-ui.html", "/webjars/**","/swagger/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
				.cors()
					.and()
				.sessionManagement()
					.and()
				.csrf()
					.disable()
				.formLogin()
					.disable()
				.httpBasic()
					.disable()
				.authorizeRequests()
					.antMatchers("/", "/joinForm", "/**/*.html", "/**/*.css", "/**/*.js")
						.permitAll()
					.antMatchers("/bookhub-service/**","/slack/**")

						.permitAll()

					.antMatchers("/swagger-resources/**","/swagger-ui/**","/v3/api-docs")
						.permitAll()
					.antMatchers("/h2-console/**")
						.permitAll()
					.anyRequest()

						.authenticated()
					.and()
					.headers()
						.addHeaderWriter(
							new XFrameOptionsHeaderWriter(
								new WhiteListedAllowFromStrategy(Arrays.asList("localhost"))    // 여기!
							)
						)
					.frameOptions().sameOrigin()
					.and()
				.oauth2Login()


				.authorizationEndpoint()
				.authorizationRequestRepository(httpCookieOAuth2AuthorizationRequestRepository)
				.and()
				.userInfoEndpoint()
					.userAuthoritiesMapper(this.userAuthoritiesMapper())
				.oidcUserService(customOAuth2UserService)
				.and()
				.successHandler(securityLoginSuccessHandler)
				.failureHandler(securityLoginFailureHandler);

//		http.addFilterBefore(customOncePerRequestFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	private GrantedAuthoritiesMapper userAuthoritiesMapper() {
		return (authorities) -> {
			Set<GrantedAuthority> mappedAuthorities = new HashSet<>();

			authorities.forEach(authority -> {
				if (OidcUserAuthority.class.isInstance(authority)) {
					OidcUserAuthority oidcUserAuthority = (OidcUserAuthority)authority;

					OidcIdToken idToken = oidcUserAuthority.getIdToken();
					OidcUserInfo userInfo = oidcUserAuthority.getUserInfo();

					// Map the claims found in idToken and/or userInfo
					// to one or more GrantedAuthority's and add it to mappedAuthorities

				} else if (OAuth2UserAuthority.class.isInstance(authority)) {
					OAuth2UserAuthority oauth2UserAuthority = (OAuth2UserAuthority)authority;

					Map<String, Object> userAttributes = oauth2UserAuthority.getAttributes();

					// Map the attributes found in userAttributes
					// to one or more GrantedAuthority's and add it to mappedAuthorities

				}
			});

			return mappedAuthorities;
		};
	}
}
