package com.util;

public class SpiderUtils {

    /**
     * 处理形如: //www.jianshu.com的url
     * @param url
     * @return
     */
    public static String handleUrl(String url){
        if(url.charAt(0) != 'h'){
            return "http:" + url;
        }else{
            return url;
        }
    }
}
