package me.naming.onlineshoppingmall.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class SHAServiceImpl implements CryptoService {

  @Value("${encrypt.shaKey}")
  private String shaKey;

  @Override
  public String encrypt(String plainText) {
    String generatedPassword = null;
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-512");
      md.update(shaKey.getBytes(StandardCharsets.UTF_8));
      byte[] bytes = md.digest(plainText.getBytes(StandardCharsets.UTF_8));
      StringBuilder sb = new StringBuilder();
      for(int i=0; i< bytes.length ;i++){
        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
      }
      generatedPassword = sb.toString();
    } catch (NoSuchAlgorithmException e) {
      log.error("** SHA512 NoSuchAlgorithmException : {}", e);
    }

    if(StringUtils.isEmpty(generatedPassword)) {
      throw new RuntimeException("비밀번호 암호화 에러");
    }

    return generatedPassword;
  }

  @Override
  public String decrypt(String encryptText) {
    return null;
  }
}