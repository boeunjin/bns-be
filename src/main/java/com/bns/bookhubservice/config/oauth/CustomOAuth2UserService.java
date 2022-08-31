package com.bns.bookhubservice.config.oauth;

import com.bns.bookhubservice.config.oauth.user.OAuth2UserInfo;
import com.bns.bookhubservice.controller.TestWebController;
import com.bns.bookhubservice.entity.MemberEntity;
import com.bns.bookhubservice.util.ValidateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;


@Slf4j
@Component
public class CustomOAuth2UserService extends OidcUserService {
    public static Boolean Member_OAuth = Boolean.TRUE;
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
        String Id = oAuth2UserInfo.getId();
        System.out.println("email:  "+email);
        System.out.println("slack_id: "+Id);
        String userId = null;

        //유저 엔티티 MemberEntity member = MemberEntity.builder().memberEmail(email).memberSlackId(Id).build();
//        UserQueryParams params =
//                UserQueryParams.builder().userId(userId).authYn(CommonConstants.YES_FLAG).build();
//        User user = userService.getValidUser(params);
        if (ValidateUtil.isEmpty(userId)) {
            // Create User
            log.debug("new user!! {} {}", userId);
            // TODO Register new user into Bookhub service
            Member_OAuth = Boolean.FALSE;

        } else {
            // Login User

            log.debug("existing user!! {} {}", userId);

        }
    }


}
