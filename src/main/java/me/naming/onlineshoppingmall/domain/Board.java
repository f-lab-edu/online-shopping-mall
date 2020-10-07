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

@Entity
@Table(name = "BOARDS")
public class Board {

  @Id
  @GeneratedValue
  @Column(name = "BOARD_NO")
  private Long boardNo;

  @ManyToOne
  @JoinColumn(name = "PRODUCT_NO")
  private Product product;

  @Column(name = "TITLE")
  private String title;

  @Column(name = "CONTENTS")
  private String contents;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "REG_DTS")
  private Date regDts;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "MOD_DTS")
  private Date modDts;

}
