package com.ds.safecall.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManagerUtil {

    public final static String ACTION_FOR_SEND_MESSAGE ="android.intent.action.for_send_message";
    public final static String ACTION_FOR_SEND_EMAIL ="android.intent.action.for_send_email";

    public static boolean HOME_ACTIVITY_START = false;

    public static boolean isMobile(String mobiles) {
        Pattern p = Pattern.compile("^((1[6-7][0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    public static boolean isEmail(String email) {
        String str="^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public static void main(String[] args) {
        System.out.print(isEmail("973171883@qq.com"));
        System.out.print(isEmail("t@163.com"));
        System.out.print(isEmail("tong@.com"));

        System.out.print(isMobile("17890"));
        System.out.print(isMobile("56925060"));
        System.out.print(isMobile("15910529587"));
        System.out.print(isMobile("16889877770"));

    }

}
