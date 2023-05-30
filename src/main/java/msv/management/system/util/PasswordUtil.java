package msv.management.system.util;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordUtil.class);
    private static String key = "Bar12345Bar12345"; // 128 bit key
    private static String initVector = "RandomInitVector"; // 16 bytes initVector

    public static String encrypt(String value) {
        try {
            if (value != null) {
                SecureRandom secureRandom = new SecureRandom();
                byte[] ivBytes = new byte[16];
                secureRandom.nextBytes(ivBytes);
                IvParameterSpec iv = new IvParameterSpec(ivBytes);

                SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
                Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
                cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
                byte[] encrypted = cipher.doFinal(value.getBytes(StandardCharsets.UTF_8));
                return Base64.encodeBase64String(iv.getIV()) + ":" + Base64.encodeBase64String(encrypted);
            }
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | BadPaddingException
                 | InvalidAlgorithmParameterException | InvalidKeyException | IllegalBlockSizeException ex) {
            LOGGER.error("Error while encrypting password! ", ex);
        }
        return value;
    }


    public static String decrypt(String encrypted) {
        try {
            if (encrypted != null) {
                IvParameterSpec iv = new IvParameterSpec(initVector.getBytes(StandardCharsets.UTF_8));
                SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
                Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");  // Use AES/GCM/NoPadding cipher mode

                cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
                byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));
                return new String(original, StandardCharsets.UTF_8);
            }
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | BadPaddingException
                 | InvalidAlgorithmParameterException | InvalidKeyException | IllegalBlockSizeException ex) {
            LOGGER.error("Error while decrypting password! ", ex);
        }
        return encrypted;
    }

}
