package com.bns.bookhubservice.config.oauth.handler;

import com.bns.bookhubservice.config.oauth.repository.HttpCookieOAuth2AuthorizationRequestRepository;
import com.bns.bookhubservice.util.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static com.bns.bookhubservice.config.oauth.CustomOAuth2UserService.Member_OAuth;
import static com.bns.bookhubservice.config.oauth.repository.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;


@Slf4j
@Component
public class SecurityLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final HttpCookieOAuth2AuthorizationRequestRepository
            httpCookieOAuth2AuthorizationRequestRepository;

//    private final AppConfigProperties appConfigProperties;

//    private final SessionService sessionService;

    public SecurityLoginSuccessHandler(
            HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository){
//            AppConfigProperties appConfigProperties,
//            SessionService sessionService) {
        this.httpCookieOAuth2AuthorizationRequestRepository =
                httpCookieOAuth2AuthorizationRequestRepository;
//        this.appConfigProperties = appConfigProperties;
//        this.sessionService = sessionService;
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

//        if (redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
//            throw new BusinessException(
//                    " Unauthorized Redirect URI", StatusCodeConstants.LOGIN_FAIL);
//        }
//
//        LoginResp loginOidcResp = sessionService.loginOidc(authentication);
        String targetUrl = "https://service.uplusbookhub.site/";
        //String targetUrl = redirectUri.orElse(getDefaultTargetUrl());
        log.debug("targetulr"+targetUrl);


        if (Member_OAuth.equals(Boolean.TRUE)){
            return targetUrl+"signup";
        }
        else{
        return targetUrl+"main";}
    }

    protected void clearAuthenticationAttributes(
            HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(
                request, response);
    }

//    private boolean isAuthorizedRedirectUri(String uri) {
//        URI clientRedirectUri = URI.create(uri);
//
//        return appConfigProperties.getOauth2().getAuthorizedRedirectUris().stream()
//                .anyMatch(
//                        authorizedRedirectUri -> {
//                            // Only validate host and port. Let the clients use different paths if
//                            // they want to
//                            URI authorizedURI = URI.create(authorizedRedirectUri);
//                            if (authorizedURI
//                                    .getHost()
//                                    .equalsIgnoreCase(clientRedirectUri.getHost())
//                                    && authorizedURI.getPort() == clientRedirectUri.getPort()) {
//                                return true;
//                            }
//                            return false;
//                        });
//    }
}