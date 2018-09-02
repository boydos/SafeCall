package com.ds.safecall.mail;

import android.content.Context;
import android.util.Log;

import com.ds.safecall.R;
import com.ds.safecall.bean.People;
import com.ds.safecall.util.SharedPreferencesUtils;

import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/4/10.
 */

public class SendMailUtil {

    private static final String HOST = "smtp.163.com";
    private static final String PORT = "25";
    private static final String FROM_ADD = "m18612236887@163.com";
    private static final String FROM_PSW = "admin2018";

    public static void send(Context context){
        String myName = SharedPreferencesUtils.getMyName(context);
        String location = SharedPreferencesUtils.getMyLocation(context);
        List<People> people = SharedPreferencesUtils.getPeople(context);

        String title = String.format(context.getResources()
                .getString(R.string.email_title),myName);

        String strFormat = context.getResources().getString(R.string.email_content);

        Log.d("tds-mail","title" + title);

        for(People p : people) {

            String msg = String.format(strFormat,
                    p.getName(),myName,location,System.currentTimeMillis()+"");

            Log.d("tds-mail","msg:" + msg);
            MailInfo info = creatMail(p.getEmail(),title,msg);
            sendMail(info);
        }

    }

    private static MailInfo creatMail(String toAdd,String title, String msg) {

        MailInfo mailInfo = new MailInfo();
        mailInfo.setMailServerHost(HOST);
        mailInfo.setMailServerPort(PORT);
        mailInfo.setValidate(true);
        mailInfo.setUserName(FROM_ADD); // 你的邮箱地址
        mailInfo.setPassword(FROM_PSW);// 您的邮箱密码
        mailInfo.setFromAddress(FROM_ADD); // 发送的邮箱
        mailInfo.setToAddress(toAdd); // 发到哪个邮件去
        mailInfo.setSubject(title); // 邮件主题
        mailInfo.setContent(msg); // 邮件文本
        return mailInfo;
    }

   private static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5,
            10,
            0,
            TimeUnit.SECONDS,
            new LinkedBlockingDeque<Runnable>(),
            new ThreadPoolExecutor.DiscardOldestPolicy());

    private static void sendMail(final MailInfo info) {
        final MailSender sms = new MailSender();
        poolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                sms.sendTextMail(info);
            }
        });
    }
}
