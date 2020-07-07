package com.yinhai.common.util;


import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Random;

/** rsa加解密工具类
 * @author yanbiao
 * @since 2019/11/15 15:54
 */
public class RsaUtil {
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * 获取公钥字符串
     * @param keyPair Key对象
     * @return 公钥字符串
     */
    public static String getPublicKeyStr(KeyPair keyPair){
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        byte[] by = publicKey.getEncoded();
        return new String(Base64.encodeBase64(by));
    }

    /**
     * 获取私钥字符串
     * @param keyPair Key对象
     * @return 私钥字符串
     */
    public static String getPrivateKeyStr(KeyPair keyPair){
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        byte[] by = privateKey.getEncoded();
        return new String(Base64.encodeBase64(by));
    }

    /**
     * 获取密钥对
     * @return 密钥对
     */
    public static KeyPair getKeyPair() {
        KeyPairGenerator generator = null;
        try {
            generator = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        generator.initialize(1024);
        return generator.generateKeyPair();
    }

    /**
     * 获取私钥
     *
     * @param privateKey 私钥字符串
     * @return
     */
    public static PrivateKey getPrivateKey(String privateKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] decodedKey = Base64.decodeBase64(privateKey.getBytes());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * 获取公钥
     * @param publicKey 公钥字符串
     * @return 公钥对象
     */
    public static PublicKey getPublicKey(String publicKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] decodedKey = Base64.decodeBase64(publicKey.getBytes());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * RSA加密
     * @param data 待加密数据
     * @param publicKey 公钥
     * @return 加密字符串
     */
    public static String encrypt(String data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        int inputLen = data.getBytes().length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offset > 0) {
            if (inputLen - offset > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data.getBytes(), offset, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data.getBytes(), offset, inputLen - offset);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        // 获取加密内容使用base64进行编码,并以UTF-8为标准转化成字符串
        // 加密后的字符串
        return new String(Base64.encodeBase64(encryptedData));
    }
    /**
     * RSA解密
     * @param data 待解密数据
     * @param privateKey 私钥
     * @return 已解密字符串
     */
    public static String decrypt(String data, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] dataBytes = Base64.decodeBase64(data);
        int inputLen = dataBytes.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offset > 0) {
            if (inputLen - offset > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(dataBytes, offset, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(dataBytes, offset, inputLen - offset);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        // 解密后的内容
        return new String(decryptedData, "UTF-8");
    }

    /**
     * 签名
     * @param data 待签名数据
     * @param privateKey 私钥
     * @return 签名
     */
    public static String sign(String data, PrivateKey privateKey) throws Exception {
        byte[] keyBytes = privateKey.getEncoded();
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey key = keyFactory.generatePrivate(keySpec);
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initSign(key);
        signature.update(data.getBytes());
        return new String(Base64.encodeBase64(signature.sign()));
    }

    /**
     * 验签
     * @param srcData 原始字符串
     * @param publicKey 公钥
     * @param sign 签名
     * @return 是否验签通过
     */
    public static boolean verify(String srcData, PublicKey publicKey, String sign) throws Exception {
        byte[] keyBytes = publicKey.getEncoded();
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey key = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initVerify(key);
        signature.update(srcData.getBytes());
        return signature.verify(Base64.decodeBase64(sign.getBytes()));
    }
    /**
     * 将json对象转成字典升序字符串
     * @param json 原始字符串
     * @return 字符串
     */
    public static String getSignString(JSONObject json){
        //map对象
        Map<String, Object> params =new HashMap<>();
        //循环转换
        Iterator it =json.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it.next();
            params.put(entry.getKey(), entry.getValue());
        }

        StringBuilder sb = new StringBuilder();
        // 将参数以参数名的字典升序排序
        Map<String, Object> sortParams = new TreeMap<String, Object>(params);
        // 遍历排序的字典,并拼接"key=value"格式
        for (Map.Entry<String, Object> entry : sortParams.entrySet()) {
            String key = entry.getKey();
            if(entry.getValue() instanceof Integer){
                Integer value = (Integer)entry.getValue();
                if (!StringUtils.isEmpty(value+"")){
                    sb.append("&").append(key).append("=").append(value);
                }
            }else{
                String value = ((String)entry.getValue()).trim();
                if (!StringUtils.isEmpty(value)){
                    sb.append("&").append(key).append("=").append(value);
                }
            }


        }
        String stringA = sb.toString().replaceFirst("&","").toUpperCase();

//        String stringSignTemp = stringA + "&"+"key="+privateKey;
//        //将签名使用MD5加密并全部字母变为大写
//        String signValue = md5(stringSignTemp).toUpperCase();
        return stringA ;
    }
    public static void main(String[] args) throws Exception {
        PrivateKey  privateKey= getPrivateKey("MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAI3CoyFlg2rAbr11SIMSoTZueHIIdabbjHLvvrnus+UChrDotwvYQHWUtZXT4cp+d9w2T6A7JJoSz7OGvdscFarq6pE7uoVP53T/ka6xVIktxQRKPLhknc9/YsFNEGnWGwgQycSOhFmkORT7rnKF8CkM1OlTohGguhUZwDFS2ZOfAgMBAAECgYBzTc9WSLV9zQvF+nGCe1K5bV12kz7u+0Df8/VUUr3sFgPsPT012y2C9fRE69SJ2hgv+9UEZVa+I3LheUVPxSYxa10c8ur8nRvmcnZBpz8wvIYW7HOy/m7QrfoR0aPpIUAaiFDmPRJDrU8hgupB9mqIvKZeLixQVxoBJZx/2G810QJBAMQ0kiC5YBN/kH5nzZNy8ZeEBnfnzWYIj2HeR1tLk+Gkh2k0jBOPzu7CVsifxMBvNmW1ZFSVVEwfwaMl+JIEESUCQQC49mfWYKstF9ZzZ+02MmOKCsPGxiS4K5TTsMGKeCCNAJpputwSTC7jSPi41feVBALXTkM/fJ/2II26hUG6uGBzAkBowOCb1B01i8/rjSQ26xGNoZGKhxoktcFHsVNLrOhBeoRJaSK4dvk2hE+tQY/cZgQW/WeZuTiOhA3wOx+hZcZ5AkEAhiXQcd9yxyW+17zMgr5RC1F7ZLNY9mpFarOL643srdd+Jqjr7Cls6FOIcSZehql+FgIhK+6LZct+f4UegvoumQJAe4YNoqR4pc0ZHpzY4sNtKIcMZ1Rw82zfEDlDXIbkfu+Kr7PMfLWnoY9pwFpmfmBFr8T+8fSc21HKjekg05EP+w==");
        PublicKey publicKey = getPublicKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCNwqMhZYNqwG69dUiDEqE2bnhyCHWm24xy77657rPlAoaw6LcL2EB1lLWV0+HKfnfcNk+gOySaEs+zhr3bHBWq6uqRO7qFT+d0/5GusVSJLcUESjy4ZJ3Pf2LBTRBp1hsIEMnEjoRZpDkU+65yhfApDNTpU6IRoLoVGcAxUtmTnwIDAQAB");

        String aesSignKey="16bdfaeaa9016bdf16bdfaeaa9016bdf";

        //生成秘钥对
//        KeyPair  key=getKeyPair();
//        String pubKey=getPublicKeyStr(key);
//        //System.out.println("pubKey=========="+pubKey);
//        String priKey=getPrivateKeyStr(key);
//        //System.out.println("priKey=========="+priKey);
//        JSONObject object=new JSONObject();
//        object.put("hospitalId","Y");
//        object.put("sign","AA/UTFULvdofWzsn4hO/2igsrMT6YFsLq5TNJlUy0UObK8WeIWjK4gki484/qi7ThT5LFB39SOfBOq45S/l6mnqnIq0YkZpndHOYJisZjESVBTqqeJl/ayYmtoqfCmoQsg52eRS9Oe5OqbN0vg3EO1dJHHmlCl+GXgggG+qcFc4=");
//        object.put("patientName","张三");
//        object.put("patientSex","男");
//        object.put("patientIdCard","500101199611186037");
//        String res=encrypt(object.toJSONString(),getPublicKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCNwqMhZYNqwG69dUiDEqE2bnhyCHWm24xy77657rPlAoaw6LcL2EB1lLWV0+HKfnfcNk+gOySaEs+zhr3bHBWq6uqRO7qFT+d0/5GusVSJLcUESjy4ZJ3Pf2LBTRBp1hsIEMnEjoRZpDkU+65yhfApDNTpU6IRoLoVGcAxUtmTnwIDAQAB"));
//        //System.out.println("加密======"+res);
//        String s=decrypt(res,getPrivateKey("MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAI3CoyFlg2rAbr11SIMSoTZueHIIdabbjHLvvrnus+UChrDotwvYQHWUtZXT4cp+d9w2T6A7JJoSz7OGvdscFarq6pE7uoVP53T/ka6xVIktxQRKPLhknc9/YsFNEGnWGwgQycSOhFmkORT7rnKF8CkM1OlTohGguhUZwDFS2ZOfAgMBAAECgYBzTc9WSLV9zQvF+nGCe1K5bV12kz7u+0Df8/VUUr3sFgPsPT012y2C9fRE69SJ2hgv+9UEZVa+I3LheUVPxSYxa10c8ur8nRvmcnZBpz8wvIYW7HOy/m7QrfoR0aPpIUAaiFDmPRJDrU8hgupB9mqIvKZeLixQVxoBJZx/2G810QJBAMQ0kiC5YBN/kH5nzZNy8ZeEBnfnzWYIj2HeR1tLk+Gkh2k0jBOPzu7CVsifxMBvNmW1ZFSVVEwfwaMl+JIEESUCQQC49mfWYKstF9ZzZ+02MmOKCsPGxiS4K5TTsMGKeCCNAJpputwSTC7jSPi41feVBALXTkM/fJ/2II26hUG6uGBzAkBowOCb1B01i8/rjSQ26xGNoZGKhxoktcFHsVNLrOhBeoRJaSK4dvk2hE+tQY/cZgQW/WeZuTiOhA3wOx+hZcZ5AkEAhiXQcd9yxyW+17zMgr5RC1F7ZLNY9mpFarOL643srdd+Jqjr7Cls6FOIcSZehql+FgIhK+6LZct+f4UegvoumQJAe4YNoqR4pc0ZHpzY4sNtKIcMZ1Rw82zfEDlDXIbkfu+Kr7PMfLWnoY9pwFpmfmBFr8T+8fSc21HKjekg05EP+w=="));
//        //System.out.println("解密======"+s);
//        //System.out.println("object中的签名======"+object.get("sign"));
//        object.remove("sign");
//        String sign=sign(object.toJSONString(),getPrivateKey("MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAI3CoyFlg2rAbr11SIMSoTZueHIIdabbjHLvvrnus+UChrDotwvYQHWUtZXT4cp+d9w2T6A7JJoSz7OGvdscFarq6pE7uoVP53T/ka6xVIktxQRKPLhknc9/YsFNEGnWGwgQycSOhFmkORT7rnKF8CkM1OlTohGguhUZwDFS2ZOfAgMBAAECgYBzTc9WSLV9zQvF+nGCe1K5bV12kz7u+0Df8/VUUr3sFgPsPT012y2C9fRE69SJ2hgv+9UEZVa+I3LheUVPxSYxa10c8ur8nRvmcnZBpz8wvIYW7HOy/m7QrfoR0aPpIUAaiFDmPRJDrU8hgupB9mqIvKZeLixQVxoBJZx/2G810QJBAMQ0kiC5YBN/kH5nzZNy8ZeEBnfnzWYIj2HeR1tLk+Gkh2k0jBOPzu7CVsifxMBvNmW1ZFSVVEwfwaMl+JIEESUCQQC49mfWYKstF9ZzZ+02MmOKCsPGxiS4K5TTsMGKeCCNAJpputwSTC7jSPi41feVBALXTkM/fJ/2II26hUG6uGBzAkBowOCb1B01i8/rjSQ26xGNoZGKhxoktcFHsVNLrOhBeoRJaSK4dvk2hE+tQY/cZgQW/WeZuTiOhA3wOx+hZcZ5AkEAhiXQcd9yxyW+17zMgr5RC1F7ZLNY9mpFarOL643srdd+Jqjr7Cls6FOIcSZehql+FgIhK+6LZct+f4UegvoumQJAe4YNoqR4pc0ZHpzY4sNtKIcMZ1Rw82zfEDlDXIbkfu+Kr7PMfLWnoY9pwFpmfmBFr8T+8fSc21HKjekg05EP+w=="));
//       //System.out.println("签名======"+sign);
//      //System.out.println("验签======"+verify(object.toJSONString(),getPublicKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCNwqMhZYNqwG69dUiDEqE2bnhyCHWm24xy77657rPlAoaw6LcL2EB1lLWV0+HKfnfcNk+gOySaEs+zhr3bHBWq6uqRO7qFT+d0/5GusVSJLcUESjy4ZJ3Pf2LBTRBp1hsIEMnEjoRZpDkU+65yhfApDNTpU6IRoLoVGcAxUtmTnwIDAQAB"),sign));

        /* //退号成功入参
        JSONObject jo=new JSONObject();
        jo.put("sms_type","APPOINT_DROP");
        jo.put("mobile","17347909621");
        jo.put("patientname","张测一");
        jo.put("hospital","人民医院");
        jo.put("deptdoctor","神经内科孙思邈医生");
        jo.put("time","2019年12月12日13:14");
        jo.put("sign",sign(jo.toJSONString(),privateKey));
        String result = encrypt(jo.toJSONString(),publicKey);
        //System.out.println(result);*/

        JSONObject jo=new JSONObject();
        String timestamp = System.currentTimeMillis()+"";
        String nonceStr = new Random().nextInt(100000)+"";
        String version = "1.0.1";
        jo.put("timestamp",timestamp);
        jo.put("nonceStr",nonceStr);
        jo.put("version",version);
        jo.put("source","wxmp");


        String signature = Aes256Util.getAesEncrypt(jo.toJSONString(),aesSignKey);
        //System.out.println("==timestamp: "+timestamp);
        //System.out.println("==nonceStr: "+nonceStr);
        //System.out.println("==version: "+version);
        //System.out.println("==signature: "+signature);
    }
}
