package me.naming.onlineshoppingmall.repository;

import me.naming.onlineshoppingmall.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
  Member findByMemNo(Long memNo);
}
