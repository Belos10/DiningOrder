package com.example.utils.payUtil;

import com.bestpay.api.util.KeyCertInfo;
import org.bouncycastle.util.encoders.Base64;
import sun.misc.BASE64Decoder;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

/**
 * @Author: liujianqun
 * @Description:
 * @Date: 2017/8/7
 * @Moidfy by:
 * 加密工具
 */

public class CryptoUtil {
    private static CertificateFactory factory = initFactory();
    private static CertificateFactory initFactory() {
        try {
            return CertificateFactory.getInstance("X.509");
        } catch (CertificateException var1) {
            throw new RuntimeException(var1);
        }
    }

    /**
     * @param fisP12 读取的私钥文件流
     * @param pwd 证书密码 非必填
     * @param keyStoreType 必填：PKCS12
     * @param alias 证书别名(密钥标识)
     * @return
     */
    ///alias = conname
    public static KeyCertInfo fileStreamToKeyCertInfo(InputStream fisP12, String pwd, String keyStoreType, String alias){

        KeyCertInfo result = null;
        try {
            KeyStore inputKeyStore = KeyStore.getInstance(keyStoreType);
            char[] inPassword = (pwd == null) ? null : pwd.toCharArray();
            inputKeyStore.load(fisP12, inPassword);
            PrivateKey privateKey = (PrivateKey)inputKeyStore.getKey(alias, inPassword);
            X509Certificate x509Certificate = (X509Certificate)inputKeyStore.getCertificate(alias);
            byte[] encoded = x509Certificate.getEncoded();
            String base64Cert = new String(Base64.encode(encoded));
            result = new KeyCertInfo();
            result.setBase64Cert(base64Cert);
            result.setPrivateKey(privateKey);
            return result;
        } catch (Exception e) {
            return result;
        }
    }

    public static X509Certificate base64StrToCert(String base64Cert) throws GeneralSecurityException {
        try {
            ByteArrayInputStream ex = new ByteArrayInputStream((new BASE64Decoder()).decodeBuffer(base64Cert));
            X509Certificate cert = (X509Certificate)factory.generateCertificate(ex);
            if(cert == null) {
                throw new GeneralSecurityException("将cer从base64转换为对象失败");
            } else {
                return cert;
            }
        } catch (IOException var3) {
            throw new GeneralSecurityException("将cer从base64转换为对象失败", var3);
        }
    }

}
