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

@Entity
@Table(name = "ADDRESS")
public class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ROAD_ADDR_NO")
  private Long roadAddrNo;

  @Column(name = "ROAD_CODE")
  @NotNull
  private String roadCode;

  @Column(name = "ROAD_NAME")
  @NotNull
  private String roadName;

  @Column(name = "ADDR_NAME")
  @NotNull
  private String addrName;

  @Column(name = "CITY")
  @NotNull
  private String city;

  @Column(name = "REGION")
  @NotNull
  private String region;

  @Column(name = "TOWN")
  @NotNull
  private String town;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "NOTICE_DTS", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private Date noticeDts;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "ERASE_DTS", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private Date eraseDts;

}
