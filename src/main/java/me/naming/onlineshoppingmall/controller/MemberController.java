package me.naming.onlineshoppingmall.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

  private static final Logger logger = LogManager.getLogger(MemberController.class);

  @GetMapping
  public void hello() {

  }
}
