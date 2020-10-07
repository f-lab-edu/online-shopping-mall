package me.naming.onlineshoppingmall.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="STORES")
public class Store {

  @Id
  @GeneratedValue
  @Column(name = "STORE_NO")
  private Long storeNo;

  @ManyToOne
  @NotNull
  @JoinColumn(name = "MEM_NO")
  private Member member;

  // 사업자 번호
  @Column(name = "BUSINESS_REG_NUM")
  @NotNull
  private Long businessRegNum;

  @Column(name = "HP", length = 11)
  @NotNull
  private Long hp;

  @ManyToOne
  @JoinColumn(name = "ROAD_ADDR_NO")
  private Address address;

  @Column(name = "ADDR_DETAIL")
  private String addrDetail;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "REG_DTS", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private Date regDts;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "MOD_DTS", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private Date modDts;

}
