package com.cnhis.cloudhealth.common.utils;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.lang3.StringUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
  
/**
 * 加密/解密工具 
 * @Title:  EncryptUtil.java   
 * @Package com.cnhis.cloudhealth.common.utils   
 * @Description:    TODO(类功能描述)   
 * @author: huchaojing     
 * @date:   2018年3月2日 上午11:58:03   
 * @version V1.0
 */

@SuppressWarnings("restriction")
public class EncryptUtil {  
  
    private final byte[] DESIV = new byte[] { 0x12, 0x34, 0x56, 120, (byte) 0x90, (byte) 0xab, (byte) 0xcd, (byte) 0xef };// 向量  
  
    private AlgorithmParameterSpec iv = null;// 加密算法的参数接口  
    private Key key = null;  
      
    private String charset = "utf-8";  
      
    /** 
     * 初始化 
     * @param deSkey    密钥 
     * @throws Exception 
     */  
    public EncryptUtil(String deSkey, String charset) throws Exception {  
        if (StringUtils.isNotBlank(charset)) {  
            this.charset = charset;  
        }  
        DESKeySpec keySpec = new DESKeySpec(deSkey.getBytes(this.charset));// 设置密钥参数  
        iv = new IvParameterSpec(DESIV);// 设置向量  
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");// 获得密钥工厂  
        key = keyFactory.generateSecret(keySpec);// 得到密钥对象  
    }  
      
    /** 
     * 加密 
     * @author ershuai 
     * @date 2017年4月19日 上午9:40:53 
     * @param data 
     * @return 
     * @throws Exception 
     */  
    public String encode(String data) throws Exception {  
        Cipher enCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");// 得到加密对象Cipher  
        enCipher.init(Cipher.ENCRYPT_MODE, key, iv);// 设置工作模式为加密模式，给出密钥和向量  
        byte[] pasByte = enCipher.doFinal(data.getBytes("utf-8"));  
        BASE64Encoder base64Encoder = new BASE64Encoder();  
        return base64Encoder.encode(pasByte);  
    }  
      
    /** 
     * 解密 
     * @author ershuai 
     * @date 2017年4月19日 上午9:41:01 
     * @param data 
     * @return 
     * @throws Exception 
     */  
    public String decode(String data) throws Exception {  
        Cipher deCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");  
        deCipher.init(Cipher.DECRYPT_MODE, key, iv);  
        BASE64Decoder base64Decoder = new BASE64Decoder();  
        byte[] pasByte = deCipher.doFinal(base64Decoder.decodeBuffer(data));  
        return new String(pasByte, "UTF-8");  
    }  
      
    public static void main(String[] args) {  
        try {  
            String test = "ershuai";  
            String key = "a26592d249be4d90ac1004f864972bb0";// 自定义密钥  
            EncryptUtil des = new EncryptUtil(key, "utf-8");  
            System.out.println("加密前的字符：" + test);  
            System.out.println("加密后的字符：" + des.encode(test));  
            System.out.println("解密后的字符：" + des.decode(des.encode(test)));  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
} 