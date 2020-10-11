package me.naming.onlineshoppingmall.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import me.naming.onlineshoppingmall.domain.Member;
import me.naming.onlineshoppingmall.repository.MemberRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

  private final MemberRepository memberRepository;
  private final CryptoService shaService;
  private final CryptoService aesService;

  @Autowired
  public MemberService(SHAServiceImpl shaService, AESServiceImpl aesService,
      MemberRepository memberRepository) {
    this.shaService = shaService;
    this.aesService = aesService;
    this.memberRepository = memberRepository;
  }

  private final Logger logger = LogManager.getLogger(MemberService.class);

  public void signUp(Member member) {

    if (memberRepository.findByEmail(member.getEmail()) != null) {
      throw new RuntimeException("이미 가입된 이메일 주소입니다.");
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
      throw new RuntimeException("존재하지 않는 이메일 주소 입니다.");
    }
    if(!member.getPassword().equals(encodePassword)) {
      throw new RuntimeException("비밀번호가 틀렸습니다");
    }

    String encodeMemNo = aesService.encrypt(String.valueOf(member.getMemNo()));
    httpSession.setAttribute("loginInfo", encodeMemNo);
  }
}
