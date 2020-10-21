package me.naming.onlineshoppingmall.service;

import java.util.concurrent.RejectedExecutionException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import me.naming.onlineshoppingmall.constant.CommonConstant;
import me.naming.onlineshoppingmall.domain.Member;
import me.naming.onlineshoppingmall.repository.MemberRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class MemberService {

  private final MemberRepository memberRepository;
  private final CryptoService shaService;
  private final CryptoService aesService;

  public MemberService(SHAServiceImpl shaService, AESServiceImpl aesService, MemberRepository memberRepository) {
    this.shaService = shaService;
    this.aesService = aesService;
    this.memberRepository = memberRepository;
  }

  public void signUp(Member member) {

    if (memberRepository.findByEmail(member.getEmail()) != null) {
      throw new RejectedExecutionException("이미 가입된 이메일 주소입니다.");
    }
    member.setMemberStatus(Member.MemberStatus.DEFAULT);

    // 비밀번호 암호화(SHA-512)
    String encodePassword = shaService.encrypt(member.getPassword());
    if (StringUtils.isNotEmpty(encodePassword)) {
      member.setPassword(encodePassword);
      memberRepository.save(member);
    }
  }

  public Member getMemberInfo(Long memNo) {
    return memberRepository.findByMemNo(memNo);
  }

  public void doLogin(String email, String password, HttpServletRequest request) {
    HttpSession httpSession = request.getSession();

    Member member = memberRepository.findMemnoAndPasswordByEmail(email);
    String encodePassword = shaService.encrypt(password);
    if(member == null) {
      throw new NullPointerException("존재하지 않는 이메일 주소 입니다.");
    }
    if(!member.getPassword().equals(encodePassword)) {
      throw new IllegalArgumentException("비밀번호가 틀렸습니다");
    }

    httpSession.setAttribute(CommonConstant.LOGIN_INFO, member.getMemNo());
  }
}
