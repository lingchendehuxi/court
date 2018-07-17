package com.court.oa.project.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * 字符串辅助类
 */
public class StringUtils {

    // 设置字符串null为""
    public static String checkNull(String str) {
        if (null == str || "null".equals(str) || "NULL".equals(str) || "(null)".equals(str) || "(NULL)".equals(str)) {
            str = "";
            return str;
        }
        return str;
    }

    // 判断字符串是否为空
    public static boolean isEmpty(String str) {
        return !(!"".equals(str) && str != null && !"null".equals(str)
                && !"NULL".equals(str) && (!"(null)".equals(str)) && (!"(NULL)".equals(str)));
    }

    /**
     * 检查对象是否为数字型字符串。
     */
    public static boolean isNumeric(Object obj) {
        if (obj == null) {
            return false;
        }
        String str = obj.toString();
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 把通用字符编码的字符串转化为汉字编码。
     */
    public static String unicodeToChinese(String unicode) {
        StringBuilder out = new StringBuilder();
        if (!isEmpty(unicode)) {
            for (int i = 0; i < unicode.length(); i++) {
                out.append(unicode.charAt(i));
            }
        }
        return out.toString();
    }

    /**
     * 过滤不可见字符
     */
    public static String stripNonValidXMLCharacters(String input) {
        if (input == null || ("".equals(input)))
            return "";
        StringBuilder out = new StringBuilder();
        char current;
        for (int i = 0; i < input.length(); i++) {
            current = input.charAt(i);
            if ((current == 0x9) || (current == 0xA) || (current == 0xD)
                    || ((current >= 0x20) && (current <= 0xD7FF))
                    || ((current >= 0xE000) && (current <= 0xFFFD))
                    || ((current >= 0x10000) && (current <= 0x10FFFF)))
                out.append(current);
        }
        return out.toString();
    }

    /*
     * 去除字符串中的空格、回车、换行符、制表符 注：\n 回车( ) \t 水平制表符( ) \s 空格(\u0008) \r 换行( )
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    public static String replaceLine(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    public static String setNickname(String nickname) {
        if (isEmpty(nickname)) {
            return "师兄";
        }
        return nickname;
    }

    public static String setAutograph(String autograph){
        if (isEmpty(autograph)){
            return "这个人很懒，什么也没留下~";
        }
        return autograph;
    }

    public static String setShowCity(String cityname) {
        if (isEmpty(cityname)) {
            return "暂无定位";
        } else if (cityname.endsWith("市")) {
            return cityname.replace("市", "");
        } else if (cityname.endsWith("区")) {
            return cityname.replace("市", "");
        } else if (cityname.endsWith("州")) {
            return cityname.replace("州", "");
        } else if (cityname.endsWith("县")) {
            return cityname.replace("县", "");
        } else if (cityname.endsWith("岛")) {
            return cityname.replace("岛", "");
        }
        return cityname;
    }


    /**
     * 保留两位小数显示
     * @param number
     * @return
     */
    public static String setNumber(int number) {
        String numberStr = "";
        if (number < 10000) {
            numberStr = String.valueOf(number);
        } else {
            numberStr = NumberUtils.getDFormat2(number / 10000);
            if (numberStr.endsWith(".00")) {
                return number / 10000 + "万";
            } else {
                return numberStr + "万";
            }
        }
        return numberStr;
    }

    /**
     * 保留一位小数点显示数字
     * @param number
     * @return
     */
    public static String setNumber2(int number) {
        String numberStr = "";
        if (number < 10000) {
            numberStr = String.valueOf(number);
        } else {
            numberStr = NumberUtils.getDFormat1(number / 10000);
            if (numberStr.endsWith(".0")) {
                return number / 10000 + "万";
            } else {
                return numberStr + "万";
            }
        }
        return numberStr;
    }


    /**
     * webview中iframe视屏 加全屏标签allowfullscreen
     *
     * @param webContent 初始的web详情
     * @return
     */
    public static String formatVideoFullScreen(String webContent) {
        String fullScreenContent = webContent;
        if (!StringUtils.isEmpty(webContent) && fullScreenContent.contains("<iframe") && fullScreenContent.contains("</iframe>")) {
            int startIndex = fullScreenContent.indexOf("<iframe");
            int endIndex = fullScreenContent.indexOf("</iframe>");
            String videoStr = fullScreenContent.substring(startIndex, endIndex);
            String updateStr = "";
            if (videoStr.toLowerCase().contains("allowfullscreen")) {
                int fullIndex = videoStr.toLowerCase().indexOf("allowfullscreen");
                updateStr = videoStr.substring(0, fullIndex) + "allowfullscreen>";
            } else { //没有则添加
                updateStr = videoStr.replace(" >", "").replace(">", "") + " allowfullscreen" + ">";
            }
            fullScreenContent = fullScreenContent.replace(videoStr, updateStr);
//                    fullScreenContent=fullScreenContent.replace("<iframe frameborder=\"0\" width=\"640\" height=\"498\" src=\"https://v.qq.com/iframe/player.html?vid=c0348mkk650&amp;tiny=0&amp;auto=0\" allowfullscreen=\"\">",
//                    "<embed src=\"https://imgcache.qq.com/tencentvideo_v1/playerv3/TPout.swf?max_age=86400&v=20161117&vid=j03169n5fpx&auto=0\" allowFullScreen=\"true\" quality=\"high\" width=\"480\" height=\"400\" align=\"middle\" allowScriptAccess=\"always\" type=\"application/x-shockwave-flash\"></embed>");
            fullScreenContent = fullScreenContent.replace("https","http");
        }
        return fullScreenContent;
    }
}
