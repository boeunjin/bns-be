package com.bns.bookhubservice.config.oauth;

import com.bns.bookhubservice.config.oauth.repository.HttpCookieOAuth2AuthorizationRequestRepository;
import com.bns.bookhubservice.config.oauth.user.OAuth2UserInfo;

import com.bns.bookhubservice.service.MemberService;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;



@Slf4j
@Component
@Getter
public class CustomOAuth2UserService extends OidcUserService {
    public static Boolean Member_OAuth = Boolean.TRUE;
    public static String Email = "default@lguplus.co.kr";
    public static String Id = "DEFAULT";

    @Autowired
    private MemberService memberService;
    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);


        try {
            processOAuth2User(userRequest, oidcUser);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the
            // OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }

        return oidcUser;
    }

    private void processOAuth2User(OidcUserRequest userRequest, OidcUser oidcUser) {
        OAuth2UserInfo oAuth2UserInfo = new OAuth2UserInfo(oidcUser.getAttributes());
        String email = oAuth2UserInfo.getEmail();//.substring(0, oAuth2UserInfo.getEmail().indexOf("@"));
        String id = oAuth2UserInfo.getId();

        try {
            Email = email;
            Id = id;
            String result = memberService.getMemberLocation(Id);
            log.info(result);
            if (result!=null) {
                // Create User
                // TODO Register new user into Bookhub service
                Member_OAuth = Boolean.FALSE;

                log.debug("existing user!! {} {}", Id, result);

            } else {
                // Login User
                Member_OAuth = Boolean.TRUE;
                log.debug("new user!! {} {}", Id, Email);

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }



    }


}
