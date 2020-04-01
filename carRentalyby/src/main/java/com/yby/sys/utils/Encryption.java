package com.yby.sys.utils;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * 这里是shiro加密
 * */
public class Encryption {

    //加密解密次数
    private static int number = 2;

    /**
     * 封装好的shiro加密
     * */
    public static String encode(String souce){
        for(int i = 0 ; i < number ;i++){
            souce = encBase64(souce);
        }
        return souce;
    }

    /**
     * 封装好的shiro解密
     * */
    public static String decode(String souce){
        for(int i = 0 ; i < number ;i++){
            souce = decBase64(souce);
        }
        return souce;
    }

    /**
     * shiro Base64加密
     * */
    public static String encBase64(String souce){
        return Base64.encodeToString(souce.getBytes());
    }

    /**
     * shiro Base64解密
     * */
    public static String decBase64(String souce){
        return Base64.decodeToString(souce);
    }
    /**
     * souce需要加密的字符串
     * salt自定义盐
     * */
    public static String md5(String souce,String salt){
        return new Md5Hash(souce,salt).toString();
    }

    public static void main(String args[]){
        String password = "jiangpeng241241";
        String enCode = Encryption.encode(password);
        String deCode = Encryption.decode(enCode);
        System.out.println("解密之前 : " + password );
        System.out.println("解密后 : " + enCode );
        System.out.println("还原后 : " + deCode );

    }
}