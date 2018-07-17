package com.court.oa.project.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by liuhong on 2018/7/17.
 */

public class MD5Utils {
    /*
	 * 加密算法
	 * 如果想得到大写的加密字符串只要在return sb.toString();这句话后面加上.toUpperCase()即可；return sb.toString().toUpperCase();
	 */
    public static String encode(String text){

        try {
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(text.getBytes());
            StringBuilder sb =new StringBuilder();
            for(byte b:result){
                int number = b&0xff;
                String hex = Integer.toHexString(number);
                if(hex.length() == 1){
                    sb.append("0"+hex);
                }else{
                    sb.append(hex);
                }
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "" ;
    }

}
