package me.naming.onlineshoppingmall.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PRODUCTS_TYPE")
public class ProductType {

  @Id
  @GeneratedValue
  @Column(name = "PRD_TP_NO")
  private Long prdtpNo;

  @Column(name = "PRD_TP_CD")
  private String prdTpCd;

  @Column(name = "PRD_TP_DTL")
  private String prdTpDtl;

}
