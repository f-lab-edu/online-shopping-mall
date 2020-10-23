package me.naming.onlineshoppingmall.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "ORDERS_ITEM")
public class OrderItem {

  @Id
  @GeneratedValue
  @Column(name = "ORDER_ITEM_NO")
  private Long orderItemNo;

  @ManyToOne
  @JoinColumn(name = "ORDER_NO")
  private Order order;

  @ManyToOne
  @JoinColumn(name = "PRODUCT_NO")
  private Product product;

  @Column(name = "ORDER_PRICE")
  private int orderPrice;

  @Column(name = "ORDER_COUNT")
  private int orderCount;

}