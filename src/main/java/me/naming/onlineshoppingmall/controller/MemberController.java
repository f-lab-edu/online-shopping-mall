package me.naming.onlineshoppingmall.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import me.naming.onlineshoppingmall.constant.RequestUrlConstant;
import me.naming.onlineshoppingmall.domain.Member;
import me.naming.onlineshoppingmall.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MemberController {

  @Autowired
  private MemberService memberService;

  // 회원가입
  @PostMapping(value = RequestUrlConstant.MEMBERS_SIGNUP)
  @ResponseStatus(HttpStatus.CREATED)
  public void memberJoin(@RequestBody @Valid Member member) {
    memberService.signUp(member);
  }

  // 회원정보 갖고오기
  @GetMapping(value = RequestUrlConstant.MEMBERS +"/{memNo}")
  @ResponseStatus(HttpStatus.OK)
  public Member getMemberInfo(@PathVariable(name = "memNo") Long memNo) {
    return memberService.getMemberInfo(memNo);
  }

  @GetMapping(value = RequestUrlConstant.LOGIN)
  @ResponseStatus(HttpStatus.OK)
  public void login(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
    HttpSession httpSession = request.getSession();
    httpSession.setAttribute("loginInfo", memberService.getLoginInfo(userLoginRequest.getEmail(), userLoginRequest.getPassword()));
  }

  @Getter
  private static class UserLoginRequest {
    @NonNull String email;
    @NonNull String password;
  }
}
