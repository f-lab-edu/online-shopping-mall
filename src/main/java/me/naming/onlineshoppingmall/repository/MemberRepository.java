package me.naming.onlineshoppingmall.repository;

import me.naming.onlineshoppingmall.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long> {
  Member findByMemNo(Long memNo);
  Member findByEmail(String email);

  @Query("select mb.memNo from Member mb where mb.email = ?1 and mb.password = ?2")
  Long findMemnoForLoginInfo(String email, String password);
}