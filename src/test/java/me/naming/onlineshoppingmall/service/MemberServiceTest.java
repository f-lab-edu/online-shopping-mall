package me.naming.onlineshoppingmall.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import me.naming.onlineshoppingmall.domain.Member;
import me.naming.onlineshoppingmall.repository.MemberRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

  private MemberService memberService;

  @Mock
  private MemberRepository memberRepository;
  @Mock
  private AESServiceImpl aesService;
  @Mock
  private SHAServiceImpl shaService;

  private SHAServiceImpl autowiredSHAService = new SHAServiceImpl();

  private Member testMember;
  private static final String EMAIL = "test@test.com";
  private static final String PWD = "A1s2d3!@#232";
  private static final String NAME = "테스터";
  private static final String M_GENDER = "M";
  private static final Logger logger = LogManager.getLogger(MemberServiceTest.class);

  @BeforeEach
  public void setUp() {

    String from = "1990/06/17";
    SimpleDateFormat transFormat = new SimpleDateFormat("yyyy/MM/dd");

    Date tmpBirthDate = null;
    try {
      tmpBirthDate = transFormat.parse(from);
    } catch (ParseException e) {
      logger.error("Parsing Error : {}", e);
    }

    // given
    this.testMember = Member.builder()
        .email(EMAIL)
        .password(PWD)
        .name(NAME)
        .gender(M_GENDER)
        .hp((long) 01022021234)
        .birthDate(tmpBirthDate)
        .build();
  }

  @Test
  public void 가입된_이메일주소_확인() {

    //given
    when(memberRepository.findByEmail(EMAIL)).thenReturn(testMember);
    memberService = new MemberService(shaService, aesService, memberRepository);

    //when
    Exception exception = assertThrows(RuntimeException.class, () -> {
      memberService.signUp(testMember);
    });

    //then
    assertEquals("이미 가입된 이메일 주소입니다.", exception.getMessage());
  }

  @Test
  public void 로그인_실패_이메일주소_존재_X() {

    //given
    MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
    when(memberRepository.findMemnoAndPasswordByEmail(EMAIL)).thenReturn(null);
    memberService = new MemberService(shaService, aesService, memberRepository);

    //when
    Exception exception = assertThrows(RuntimeException.class, () -> {
      memberService.doLogin(EMAIL, PWD, mockHttpServletRequest);
    });

    //then
    assertEquals("존재하지 않는 이메일 주소 입니다.", exception.getMessage());
  }

  @Test
  public void 로그인_실패_비밀번호_일치_X() {

    //given
    MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
    when(memberRepository.findMemnoAndPasswordByEmail(EMAIL)).thenReturn(testMember);
    memberService = new MemberService(shaService, aesService, memberRepository);

    //when
    Exception exception = assertThrows(RuntimeException.class, () -> {
      memberService.doLogin(EMAIL, "testPassword", mockHttpServletRequest);
    });

    //then
    assertEquals("비밀번호가 틀렸습니다", exception.getMessage());
  }

//  @Test
//  public void 로그인_성공(){
//    //given
//    MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
//    when(memberRepository.findMemnoAndPasswordByEmail(EMAIL)).thenReturn(testMember);
//    memberService = new MemberService(shaService, aesService, memberRepository);
//
//    //when
//    memberService.doLogin(EMAIL, PWD, mockHttpServletRequest);
//
//    //then
//    assertNotNull(mockHttpServletRequest.getAttribute("loginInfo").toString());
//  }

}
