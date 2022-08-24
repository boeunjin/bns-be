package com.bns.bookhubservice.config.oauth.handler;

import com.bns.bookhubservice.config.oauth.repository.HttpCookieOAuth2AuthorizationRequestRepository;
import com.bns.bookhubservice.util.CookieUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.bns.bookhubservice.config.oauth.repository.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;


@Component
public class SecurityLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final HttpCookieOAuth2AuthorizationRequestRepository
            httpCookieOAuth2AuthorizationRequestRepository;

    public SecurityLoginFailureHandler(
            HttpCookieOAuth2AuthorizationRequestRepository
                    httpCookieOAuth2AuthorizationRequestRepository) {
        this.httpCookieOAuth2AuthorizationRequestRepository =
                httpCookieOAuth2AuthorizationRequestRepository;
    }

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception)
            throws IOException, ServletException {
        String targetUrl =
                CookieUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                        .map(Cookie::getValue)
                        .orElse(("/"));

        targetUrl =
                UriComponentsBuilder.fromUriString(targetUrl)
                        .queryParam("error", exception.getLocalizedMessage())
                        .build()
                        .toUriString();

        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(
                request, response);

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}