package com.bns.bookhubservice.controller;


import com.bns.bookhubservice.entity.MemberEntity;
import com.bns.bookhubservice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

import static com.bns.bookhubservice.config.oauth.CustomOAuth2UserService.Id;
import static com.bns.bookhubservice.config.oauth.CustomOAuth2UserService.email;

@Controller
@Slf4j
@RequiredArgsConstructor
public class TestWebController {

    //private final MemberRepository memberRepository;
    @GetMapping({"","/"})
    public String index(){
        // src/main/resources/{prefix}/name.{suffix}
        return "index";
    }
    @GetMapping({"/main"})
    public String main(){
        // src/main/resources/{prefix}/name.{suffix}
        return "main";
    }

    @GetMapping("/loginForm")
    public String loginForm(){
        return "loginForm";
    }

    @GetMapping("/signup")
    public String joinForm(){
        return "joinForm";
    }
    @PostMapping(value = "/join",produces = MediaType.APPLICATION_JSON_VALUE)
	public String join(@RequestBody Map<String, String> data) {
        log.info(data.get("NAME"));
        log.info("id",Id);
        log.info("email",email);


		return "main";
	}
}
