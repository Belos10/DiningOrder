package com.example.utils.payUtil;

import com.bestpay.api.util.Base64Encrypt;
import com.bestpay.api.util.KeyCertInfo;

import java.io.InputStream;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;

/**
 * @author zhuchengcheng
 * @date 2021/7/27
 * 证书工具??
 */
public class KeyCertUtil {

    public static PrivateKey getPrivateKey(String privateKeyPath, String privateKeyPwd, String keyStoreType, String alias) {
        // 私钥加签:1、通过读取私钥文件，获取私钥
        InputStream fisP12 = KeyCertUtil.class.getClassLoader().getResourceAsStream(privateKeyPath);

        KeyCertInfo keyCertInfo = CryptoUtil.fileStreamToKeyCertInfo(fisP12, privateKeyPwd, keyStoreType, alias);
        return (PrivateKey) keyCertInfo.getPrivateKey();
    }

    public static PublicKey getPublicKey(String publicKeyPath){
        //获取公钥
        PublicKey pubKey = null;
        InputStream pubStream = KeyCertUtil.class.getClassLoader().getResourceAsStream(publicKeyPath);
        try {
            byte pubByte[] = new byte[2048];
            pubStream.read(pubByte);
            pubStream.close();
            X509Certificate x509Certificate =CryptoUtil.base64StrToCert(Base64Encrypt.getBASE64ForByte(pubByte));
            pubKey = x509Certificate.getPublicKey();
        } catch (Exception e) {

        }
        return pubKey;
    }
}
