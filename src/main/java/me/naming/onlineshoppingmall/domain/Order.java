package me.naming.onlineshoppingmall.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "ORDERS")
public class Order {

  @Id
  @GeneratedValue
  @Column(name = "ORDER_NO")
  private Long orderNo;

  @ManyToOne
  @JoinColumn(name = "MEM_NO")
  private Member member;

  @Enumerated(EnumType.STRING)
  @Column(name = "STATUS")
  private OrderStatus orderStatus;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "REG_DTS", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private Date regDts;
  
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "MOD_DTS", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private Date modDts;

  public enum OrderStatus{
    // ORDER(주문), CANCEL(취소)
    ORDER, CANCEL
  }
}
