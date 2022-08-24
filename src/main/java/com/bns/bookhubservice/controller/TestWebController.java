package com.bns.bookhubservice.controller;


import com.bns.bookhubservice.entity.MemberEntity;
import com.bns.bookhubservice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping("/joinForm")
    public String joinForm(){
        return "joinForm";
    }
    @PostMapping("/join")
	public String join(MemberEntity memberEntity) {

        //memberRepository.save(memberEntity);

		return "redirect:/main";
	}
}
