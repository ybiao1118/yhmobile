package com.yinhai.common.util;

import com.alibaba.fastjson.JSONObject;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
/** aes加密工具类
 * @author yanbiao
 * @since 2019/11/11 23:08
 */
public class Aes256Util {
    /**
     * AES_256_cbc pkcs7
     */
    private static final String ALGORITHM = "AES/CBC/PKCS7Padding";

    /**
     * 加密
     * @param srcData 代价密参数
     * @param key 加密秘钥
     * @param iv 加密长度
     * @return 加密参数
     */
    private static byte[] encrypt(byte[] srcData,byte[] key,byte[] iv) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException
    {
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        Security.addProvider(new BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance(ALGORITHM,"BC");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv));
        byte[] encData = cipher.doFinal(srcData);
        return encData;
    }

    /**
     * 解密
     * @param encData 待解密参数
     * @param key 秘钥
     * @param iv 长度
     * @return 解密参数
     */
    private static byte[] decrypt(byte[] encData,byte[] key,byte[] iv) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException
    {
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        Security.addProvider(new BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance(ALGORITHM,"BC");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv));
        byte[] decbbdt = cipher.doFinal(encData);
        return decbbdt;
    }

    /**
     * aes加密方法
     * @param data 待加密字符串
     * @param key 秘钥
     * @return 加密字符串
     */
    public static String getAesEncrypt(String data, String key) throws NoSuchPaddingException, UnsupportedEncodingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException, InvalidKeyException {
        String iv=key.substring(0,16);
        byte[] bKey=key.getBytes(StandardCharsets.UTF_8);
        byte[] bIv=iv.getBytes(StandardCharsets.UTF_8);
        byte[] bData = data.getBytes(StandardCharsets.UTF_8);
        return  (new BASE64Encoder()).encode(encrypt(bData,bKey,bIv));
    }

    /**
     *  aes解密方法
     * @param data 待解密字符串
     * @param key 秘钥
     * @return  已解密字符串
     */
    public static String getAesDecrypt(String data, String key) throws NoSuchPaddingException, UnsupportedEncodingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException, InvalidKeyException {
        data = data.replace('*', '+').replace('-', '/').replace('.', '=');
        String iv=key.substring(0,16);
        byte[] bKey=key.getBytes(StandardCharsets.UTF_8);
        byte[] bIv=iv.getBytes(StandardCharsets.UTF_8);
        byte[] bData = null;
        try {
            //先用base64解密
            bData =  (new BASE64Decoder()).decodeBuffer(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return   new String(decrypt(bData,bKey,bIv), StandardCharsets.UTF_8);
    }
    public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        JSONObject json=new JSONObject();
        json.put("version","1.0.1");
        json.put("source","wxmini");
        json.put("timestamp","1579325922482");
        json.put("nonce","344613");
        String req= Aes256Util.getAesEncrypt(json.toJSONString(), "16bdfaeaa9016bdf16bdfaeaa9016bdf");
        //System.out.println("加密===="+req);
//        String res1=Aes256Util.getAesDecrypt(req,"16bdfaeaa9016bdf16bdfaeaa9016bdf");
//        //System.out.println("解密----===="+res1);
        String res=Aes256Util.getAesDecrypt("zpuLa5V+GclZBcQdwWHFvsvaQ5JrW7m4pKcGa1iYpkTnwvGxxx8n1IX3Z37XTNRFm+TjlzsES1Ir3YGAv6jVkElXI0DaHNNlI04RNgJsBJz+g1b8bFwMZIiQMkLdBmRC","16bdfaeaa9016bdf16bdfaeaa9016bdf");
        //System.out.println("解密===="+res);
        JSONObject jsonObject=JSONObject.parseObject(res);
        //System.out.println(jsonObject.equals(json));
        //System.out.println("cBu/zlNGiVfSmFsu2KMW9w==".equals(req));
    }

}