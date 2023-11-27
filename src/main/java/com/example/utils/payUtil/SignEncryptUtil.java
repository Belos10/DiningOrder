package com.example.utils.payUtil;

import com.bestpay.api.util.Base64Encrypt;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhuchengcheng
 * @date 2021/7/27
 * 签名加密
 */
public class SignEncryptUtil {
    /**
     * 编码 格式
     */
    private static final String ENCODING = "UTF-8";

    /**
     * 加解密算法/工作模式/填充方式,Java6.0支持PKCS5Padding填充方式,BouncyCastle支持PKCS7Padding填充方式
     */
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";

    /**
     * AES对称加密算法
     */
    private static final String KEY_ALGORITHM = "AES";
    private static BouncyCastleProvider bouncyCastleProvider;
    public static final Object LOCK = new Object();
    private KeyGenerator keyGenerator;
    private Cipher aesCipher;
    private Lock lock;

    public SignEncryptUtil() {
        try {
            BouncyCastleProvider bouncyCastleProvider = new BouncyCastleProvider();
            lock = new ReentrantLock();

            this.aesCipher = Cipher.getInstance(CIPHER_ALGORITHM, bouncyCastleProvider);

            keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);
        }catch (Exception e){

        }
    }

    private static BouncyCastleProvider getInstanceProvider() {
        if (bouncyCastleProvider == null) {
            Object var0 = LOCK;
            synchronized (LOCK) {
                if (bouncyCastleProvider == null) {
                    bouncyCastleProvider = new BouncyCastleProvider();
                }
            }
        }
        return bouncyCastleProvider;
    }


    /**
     * 签名
     * @param priKey
     * @param tobeSigned
     * @return
     */
    public static String sign(PrivateKey priKey, String tobeSigned) {
        try {
            Signature sign = Signature.getInstance(RsaCipher.SignHashAlgoMode.SHA256.getCode());
            sign.initSign(priKey);
            sign.update(tobeSigned.getBytes(ENCODING));
            byte signed[] = sign.sign();
            return Base64Encrypt.getBASE64ForByte(signed);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 验签
     * @param algorithm
     * @param data
     * @param sign
     * @param publicKey
     * @return
     */
    public static boolean verify(RsaCipher.SignHashAlgoMode algorithm, byte[] data, byte[] sign, PublicKey publicKey) {
        if (data == null || sign == null || publicKey == null) {
            return false;
        }
        try {
            Signature signature = Signature.getInstance(algorithm.getCode(), getInstanceProvider());
            signature.initVerify(publicKey);
            signature.update(data);
            return signature.verify(sign);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 加密
     */
    public static String encryptByAES(String data, Key key, String iv) {

        try {
            IvParameterSpec ivs = new IvParameterSpec(iv.getBytes());
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key, ivs);
            return Base64Encrypt.getBASE64ForByte(cipher.doFinal(data.getBytes()));
        } catch (RuntimeException e) {
            return "";
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 解密
     */
    public static String decryptByAES(String data, Key priKey, String iv) {
        try {
            IvParameterSpec ivs = new IvParameterSpec(iv.getBytes());
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, priKey, ivs);
            return new String(cipher.doFinal(Base64Encrypt.getByteArrFromBase64(data)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Key genAESRandomKey() {
        keyGenerator.init(128);
        return keyGenerator.generateKey();
    }


    public static Key getKey(byte[] keyBytes) {
        Key aesKey;
        try {
            aesKey = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
        } catch (IllegalArgumentException e) {
            return null;
        }
        return aesKey;
    }

}
