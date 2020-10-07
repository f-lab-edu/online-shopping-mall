package me.naming.onlineshoppingmall.domain;

import java.util.Date;
import javax.persistence.Column;
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
@Table(name = "PAYMENTS")
public class Payment {

  @Id
  @GeneratedValue
  @Column(name = "PAYMENT_NO")
  private Long paymentNo;

  @ManyToOne
  @JoinColumn(name = "ORDER_NO")
  private Order order;

  @Column(name = "TYPE")
  @Enumerated(EnumType.STRING)
  private PaymentType paymentType;

  @Column(name = "AMOUNT")
  private int amount;

  @Column(name = "STATUS")
  @Enumerated(EnumType.STRING)
  private PaymentStatus paymentStatus;

  @Temporal(value = TemporalType.DATE)
  @Column(name = "PAY_DTS")
  private Date payDts;

  @Temporal(value = TemporalType.DATE)
  @Column(name = "REFUND_DTS")
  private Date refundDts;

  private enum PaymentStatus{
    PAY, REFUND
  }

  private enum PaymentType{
    CARD, ACCOUNT, POINT, PHONE
  }

}
