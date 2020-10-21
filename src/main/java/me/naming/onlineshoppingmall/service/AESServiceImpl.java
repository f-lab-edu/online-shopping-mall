package me.naming.onlineshoppingmall.service;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AESServiceImpl implements CryptoService{

  @Value("${encrypt.aesKey}")
  private String aesKey;

  private static final Logger logger = LogManager.getLogger(AESServiceImpl.class);

  @Override
  public String encrypt(String plainText) {

    byte[] encrypted = new byte[0];

    try {
      SecretKeySpec keySpec = getKeySpec();

      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
      cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(aesKey.substring(0, 16).getBytes()));
      encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8.name()));

    } catch (GeneralSecurityException e) {
      logger.error("** GeneralSecurityException : {}", e);
    } catch (UnsupportedEncodingException e) {
      logger.error("** UnsupportedEncodingException : {}", e);
    }
    return new String(Base64.encodeBase64(encrypted));
  }

  @Override
  public String decrypt(String encryptText) {
    String aesDecode = null;

    try {
      SecretKeySpec keySpec = getKeySpec();
      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
      cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(aesKey.substring(0, 16).getBytes(StandardCharsets.UTF_8.name())));

      byte[] byteStr = Base64.decodeBase64(encryptText.getBytes());
      aesDecode =  new String(cipher.doFinal(byteStr), StandardCharsets.UTF_8.name());

    } catch (Exception e) {
      logger.error("** Decode UnsupportedEncodingException : {}", e);
    }

    if(StringUtils.isEmpty(aesDecode)) throw new RuntimeException("복호화 에러");
    return aesDecode;
  }

  private SecretKeySpec getKeySpec() throws UnsupportedEncodingException {
    byte[] keyBytes = new byte[16];
    byte[] defaultByte = aesKey.substring(0, 16).getBytes(StandardCharsets.UTF_8.name());

    int len = defaultByte.length;
    if (len > keyBytes.length) {
      len = keyBytes.length;
    }

    System.arraycopy(defaultByte, 0, keyBytes, 0, len);
    return new SecretKeySpec(keyBytes, "AES");
  }
}
