package me.naming.onlineshoppingmall.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "MEMBERS")
@Getter
@NoArgsConstructor
@ToString
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "MEM_NO")
  private Long memNo;

  @Column(name = "EMAIL")
  @NotNull
//  @Pattern(regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
  @Email(message = "이메일 형식에 맞지 않습니다.")
  private String email;

  // 영문(소문자, 대문자), 숫자, 특수문자 조합 9~12자리 조합
  @Column(name = "PASSWORD")
  @NotNull
  @Setter
//  @Pattern(regexp = "^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-z])(?=.*[A-Z]).{9,12}$",
//      message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 9자 ~ 12자의 비밀번호여야 합니다.")
  private String password;

  @Column(name = "NAME", length = 30)
  @NotBlank(message = "이름은 필수 입력 값입니다.")
  @NotNull
  private String name;

  @Column(name = "GENDER", length = 1)
  @NotBlank(message = "성별은 필수 입력 값입니다.")
  @NotNull
  private String gender;

  @Column(name = "STATUS")
  @Setter
  private MemberStatus memberStatus;

  @Column(name = "HP", length = 11)
  @NotNull
  private Long hp;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "BIRTH_DTS") @NotNull
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd", timezone = "Asia/Seoul)")
  private Date birthDate;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "REG_DTS", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private Date regDts;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "MOD_DTS", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private Date modDts;

  public enum MemberStatus{
    // DEFAULT(가입), DELETE(탈퇴), DORMANT(휴면)
    DEFAULT, DELETE, DORMANT
  }

  @Builder
  public Member(String email, String password, String name, String gender, MemberStatus memberStatus, Date birthDate, Long hp) {
    this.email = email;
    this.password = password;
    this.name = name;
    this.gender = gender;
    this.memberStatus = memberStatus;
    this.birthDate = birthDate;
    this.hp = hp;
  }
}