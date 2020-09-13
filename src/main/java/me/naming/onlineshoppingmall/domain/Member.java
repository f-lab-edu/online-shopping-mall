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
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "MEMBERS")
@Getter
@NoArgsConstructor
public class Member {

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

  @Column
  @NotNull
  private String email;

  @Column
  @NotNull
  private String password;

  @Column
  @NotNull
  private String name;

  @Column(name = "GENDER", length = 1)
  @NotNull
  private String gender;

  @Temporal(TemporalType.TIMESTAMP)
  @Column
  @NotNull
  private Date birthDate;

  @Temporal(TemporalType.TIMESTAMP)
  @Column
  @NotNull
  @CreationTimestamp
  private Date regDts;

  @Temporal(TemporalType.TIMESTAMP)
  @Column
  @NotNull
  @CreationTimestamp
  private Date modDts;
}
