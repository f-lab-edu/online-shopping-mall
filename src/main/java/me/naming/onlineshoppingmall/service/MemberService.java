package me.naming.onlineshoppingmall.service;

import me.naming.onlineshoppingmall.config.MessageException;
import me.naming.onlineshoppingmall.domain.Member;
import me.naming.onlineshoppingmall.repository.MemberRepository;
import me.naming.onlineshoppingmall.util.EncryptUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private EncryptUtil encryptUtil;

  private final Logger logger = LogManager.getLogger(MemberService.class);

  /**
   * @description : 회원가입
   */
  public void signUp(Member member) {

    if (memberRepository.findByEmail(member.getEmail()) != null) {
      throw new MessageException("이미 가입된 이메일 주소입니다.");
    }
    member.setMemberStatus(Member.MemberStatus.DEFAULT);

    // 비밀번호 암호화(SHA-512)
    String encodePassword = encryptUtil.shaEncode(member.getPassword());
    if (StringUtils.isNotEmpty(encodePassword)) {
      member.setPassword(encodePassword);
      memberRepository.save(member);
    }
  }

  public Member getMemberInfo(Long memNo) {
    return memberRepository.findByMemNo(memNo);
  }

  public String getLoginInfo(String email, String password) {
    Long memNo = memberRepository.findMemnoForLoginInfo(email, password);
    String encodeMemNo = encryptUtil.aesEncode(String.valueOf(memNo));
    logger.info("** encodeMemNo : {}", encodeMemNo);
    return encodeMemNo;
  }
}
