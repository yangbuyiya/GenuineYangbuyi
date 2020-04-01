package com.yby.sys.utils;

import org.apache.shiro.crypto.hash.Md5Hash;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MD5Utils {


    /**
     * 加密
     *
     * @param source        凭证信息 密码
     * @param salt          盐
     * @param hashlteration 散列次数
     * @return 加密信息
     */
    public static String md5Hash(String source, String salt, Integer hashlteration) {
        System.out.println("source:" + source);
        System.out.println("salt:" + salt);
        System.out.println("hashlteration:" + hashlteration);
        Md5Hash hash = new Md5Hash(source, salt, hashlteration);
        return hash.toString();
    }


    /**
     * 测试加密
     *
     * @param args
     */
    public static void main(String[] args) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh");
        String format = dateFormat.format(date);
        System.out.println(format);



        Md5Hash hash = new Md5Hash("123456", "打哈欠郴州", 2);
        System.out.println(hash);
//         928bfd2577490322a6e19b793691467e   e10adc3949ba59abbe56e057f20f883e

    }
}