package me.naming.onlineshoppingmall.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "MEMBERS")
@Getter
public class Member {

  public Member() {}

  @Builder
  public Member(String email, String password, String name, String gender, Date birthDate) {
    this.email = email;
    this.password = password;
    this.name = name;
    this.gender = gender;
    this.birthDate = birthDate;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long memNo;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String name;

  @Column(name = "GENDER", length = 1, nullable = false)
  private String gender;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false)
  private Date birthDate;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false)
  @CreationTimestamp
  private Date regDts;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false)
  @CreationTimestamp
  private Date modDts;
}
