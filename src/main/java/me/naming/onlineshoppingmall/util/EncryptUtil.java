package me.naming.onlineshoppingmall.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import me.naming.onlineshoppingmall.config.MessageException;
import me.naming.onlineshoppingmall.constant.CommonConstant;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * [ 단방향 암호화 방식이란?(ex. SHA ...) ]
 *  : 암호화된 메시지로는 원본 메시지를 구할 수 없는 방식을 '단방향성'이라고 부릅니다.
 *    암호화된 메시지. 즉, 다이제스트를 통해 원본 메시지를 유추하기 어려우며 원본 메세지의 문자 한글자면 변경되고
 *    다이제스트 내용이 변경되는 것을 알 수 있습니다.
 *
 * - 단방향 해시 함수의 문제점은?
 *   1) 인식 가능성 : 공격자가 다양한 다이제스트를 개별적으로 모으고 원본 메시지를 찾아내거나 동일한 효과의 메시지를 찾을 수 있습니다.
 *   2) 속도 : 해시 함수 자체가 짧은 시간에 데이터를 검색하기 위해 설계된 것입니다.
 *            이를 활용해 공격자는 매우 빠른 속도로 임의의 문자열의 다이제스트와 해킹할 대상의 다이제스트를 빠르게 비교할 수 있습니다.
 *
 * - 단방향 해시 함수 보완
 *    1) 솔팅 : 다이제스트를 생성할 때 추가되는 바이트 단위의 임의의 문자열입니다.
 *             해당 방법을 사용하면 공격자가 원본 메시지를 알아내더라도 추가적으로 솔팅을 알아내야 하며
 *             위에서 언급한 '인식 가능성' 문제점을 개선 할 수 있습니다.
 *    2) 키 스트레칭 : 원본 메시지를 반복적으로 다이제스트를 생성하는 것입니다. 단순 1회성으로 다이제스트를 생성하는 것이 아니라
 *                  해시함수를 반복하여 시간 소요가 걸리게 만드는 것입니다.
 *
 * [ 양방향 암호화 방식이란?(ex. AES, RSA ..) ]
 *  : 양방향 알고리즘은 암호화된 다이제스트를 복호화 할 수 있는 알고리즘을 의미합니다.
 *
 *  - 대칭키, 비대칭키
 *    -> 대칭키란? 암복호화에 서로 동일한 키가 사용되는 암호화 방식
 *    -> 비대칭키란? 암복호화에 서로 다른 키가 사용되는 암호화 방식이며, 하나의 키는 공개키로 사용됩니다.
 *
 * [ 결 론 ]
 *  : 사용자의 비밀번호를 선택함에 있어서는 단방향 암호화로 저장 관리되야 합니다. 왜냐하면, 사용자 본인 외에는 비밀번호 원문을 알 필요가 없기 때문입니다.
 *    반면 사용자를 인증하기 위한 정보. 예를 들면, 로그인 이후 회원 번호를 통해 사용자를 인증한다면, 사용자 본인이 회원 번호를 인지 할 필요가 없습니다.
 *    이런 경우 서비스 내부적으로만 암복호화 시켜 관리해주면 됨으로 양방향 암호화를 통해 관리해주면 된다고 생각합니다.
 *
 * [참고]
 *  - https://d2.naver.com/helloworld/318732
 *  - https://dailyworker.github.io/AES-Algorithm-and-Chiper-mode/
 *  - https://javaplant.tistory.com/26
 */
@Component
public class EncryptUtil {

  @Value("${encrypt.aesKey}")
  private String aesKey;

  @Value("${encrypt.shaKey}")
  private String shaKey;

  private static final Logger logger = LogManager.getLogger(EncryptUtil.class);

  // 암호화
  public String aesEncode(String str) {

    byte[] encrypted = new byte[0];

    try {
      SecretKeySpec keySpec = getKeySpec();

      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
      cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(aesKey.substring(0, 16).getBytes()));
      encrypted = cipher.doFinal(str.getBytes(CommonConstant.UTF8));

    } catch (IllegalBlockSizeException e) {
      logger.error("** IllegalBlockSizeException : {}", e);
    } catch (BadPaddingException e) {
      logger.error("** BadPaddingException : {}", e);
    } catch (UnsupportedEncodingException e) {
      logger.error("** UnsupportedEncodingException : {}", e);
    } catch (InvalidKeyException e) {
      logger.error("** InvalidKeyException : {}", e);
    } catch (InvalidAlgorithmParameterException e) {
      logger.error("** InvalidAlgorithmParameterException : {}", e);
    } catch (NoSuchAlgorithmException e) {
      logger.error("** NoSuchAlgorithmException : {}", e);
    } catch (NoSuchPaddingException e) {
      logger.error("** NoSuchPaddingException : {}", e);
    }
    return new String(Base64.encodeBase64(encrypted));
  }

  //복호화
  public String aesDecode(String str) {
    String aesDecode = null;

    try {
      SecretKeySpec keySpec = getKeySpec();
      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
      cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(aesKey.substring(0, 16).getBytes(CommonConstant.UTF8)));

      byte[] byteStr = Base64.decodeBase64(str.getBytes());
      aesDecode =  new String(cipher.doFinal(byteStr), CommonConstant.UTF8);

    } catch (UnsupportedEncodingException e) {
      logger.error("** Decode UnsupportedEncodingException : {}", e);
    } catch (IllegalBlockSizeException e) {
      logger.error("** Decode IllegalBlockSizeException : {}", e);
    } catch (BadPaddingException e) {
      logger.error("** Decode BadPaddingException : {}", e);
    } catch (InvalidKeyException e) {
      logger.error("** Decode InvalidKeyException : {}", e);
    } catch (InvalidAlgorithmParameterException e) {
      logger.error("** Decode InvalidAlgorithmParameterException : {}", e);
    } catch (NoSuchAlgorithmException e) {
      logger.error("** Decode NoSuchAlgorithmException : {}", e);
    } catch (NoSuchPaddingException e) {
      logger.error("** Decode NoSuchPaddingException : {}", e);
    }

    if(StringUtils.isEmpty(aesDecode)) throw new MessageException("복호화 에러");
    return aesDecode;
  }

  public String shaEncode(String password) {
    String generatedPassword = null;
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-512");
      md.update(shaKey.getBytes(StandardCharsets.UTF_8));
      byte[] bytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
      StringBuilder sb = new StringBuilder();
      for(int i=0; i< bytes.length ;i++){
        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
      }
      generatedPassword = sb.toString();
    } catch (NoSuchAlgorithmException e) {
      logger.error("** SHA512 NoSuchAlgorithmException : {}", e);
    }

    if(StringUtils.isEmpty(generatedPassword)) {
      throw new MessageException("비밀번호 암호화 에러");
    }

    return generatedPassword;
  }

  private SecretKeySpec getKeySpec() throws UnsupportedEncodingException {
    byte[] keyBytes = new byte[16];
    byte[] defaultByte = aesKey.substring(0, 16).getBytes(CommonConstant.UTF8);

    int len = defaultByte.length;
    if (len > keyBytes.length) {
      len = keyBytes.length;
    }

    System.arraycopy(defaultByte, 0, keyBytes, 0, len);
    return new SecretKeySpec(keyBytes, "AES");
  }
}
