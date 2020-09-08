package me.naming.onlineshoppingmall.controller;

import me.naming.onlineshoppingmall.constant.CommonConstant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

  private static final Logger logger = LogManager.getLogger(CommonConstant.ONLINE_SHOPPING_MALL);

  @GetMapping
  public void hello() {

  }
}
