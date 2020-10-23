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
@Table(name = "PRODUCTS")
public class Product {

  @Id @GeneratedValue
  @Column(name = "PRODUCT_NO")
  private Long productNo;

  @ManyToOne
  @JoinColumn(name = "PRD_TP_NO")
  private ProductType productType;

  @Column(name = "NAME")
  private String name;

  @Column(name = "PRICE")
  private int price;

  @Column(name = "IMAGE_PATH")
  private int imagePath;

  @Column(name = "STOCK_QUANTITY")
  private int stockQuantity;

  @ManyToOne
  @JoinColumn(name = "STORE_NO", referencedColumnName = "STORE_NO")
  private Store store;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "REG_DTS", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private Date regDts;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "MOD_DTS", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private Date modDts;

}
