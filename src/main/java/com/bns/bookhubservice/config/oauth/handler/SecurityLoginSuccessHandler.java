package com.bns.bookhubservice.config.oauth.handler;

import com.bns.bookhubservice.config.oauth.CustomOAuth2UserService;
import com.bns.bookhubservice.config.oauth.repository.HttpCookieOAuth2AuthorizationRequestRepository;
import com.bns.bookhubservice.util.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static com.bns.bookhubservice.config.oauth.CustomOAuth2UserService.*;
import static com.bns.bookhubservice.config.oauth.repository.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;


@Slf4j
@Component
public class SecurityLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final HttpCookieOAuth2AuthorizationRequestRepository
            httpCookieOAuth2AuthorizationRequestRepository;


//    private final AppConfigProperties appConfigProperties;

//    private final SessionService sessionService;
    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    public SecurityLoginSuccessHandler(
            HttpCookieOAuth2AuthorizationRequestRepository
                    httpCookieOAuth2AuthorizationRequestRepository){

        this.httpCookieOAuth2AuthorizationRequestRepository =
                httpCookieOAuth2AuthorizationRequestRepository;

    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }
        logger.debug("success success");

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) {
        Optional<String> redirectUri =
                CookieUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME).map(Cookie::getValue);

        //String targetUrl = "https://service.uplusbookhub.site/";
        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());
        log.debug("target_url"+targetUrl);


        if (Member_OAuth.equals(Boolean.TRUE)){
            CookieUtil.addCookie(response,"Email",CookieUtil.serialize(Email),30*60);
            CookieUtil.addCookie(response,"ID",CookieUtil.serialize(Id),30*60);

            return targetUrl+"signup";
        }
        else{
            CookieUtil.addCookie(response,"ID",CookieUtil.serialize(Id),30*60);
            return targetUrl+"main";
        }
    }

    protected void clearAuthenticationAttributes(
            HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(
                request, response);
    }

}