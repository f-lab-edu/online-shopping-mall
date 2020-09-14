package me.naming.onlineshoppingmall.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import me.naming.onlineshoppingmall.domain.Member;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class MemberRepositoryTest {

  @Autowired
  private MemberRepository memberRepository;

  private static final Logger logger = LogManager.getLogger(MemberRepositoryTest.class);

  @Test
  public void 회원정보_저장() {

    String from = "1990/06/17";
    SimpleDateFormat transFormat = new SimpleDateFormat("yyyy/MM/dd");

    Date tmpBirthDate = null;
    try {
      tmpBirthDate = transFormat.parse(from);
    } catch (ParseException e) {
      logger.error("Parsing Error : {}", e);
    }

    // given
    Member member = Member.builder()
        .email("test@test.com")
        .password("a1s2d3!@#")
        .name("테스터")
        .gender("M")
        .birthDate(tmpBirthDate)
        .build();

    // when
    memberRepository.save(member);

    // then...
    Member selectMember = memberRepository.findByMemNo(member.getMemNo());
    assertEquals(selectMember.getEmail(), member.getEmail());
    assertEquals(selectMember.getPassword(), member.getPassword());
    assertEquals(selectMember.getName(), member.getName());
    assertEquals(selectMember.getGender(), member.getGender());
  }
}