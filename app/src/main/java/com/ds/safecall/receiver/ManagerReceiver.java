package com.ds.safecall.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.ds.safecall.R;
import com.ds.safecall.bean.People;
import com.ds.safecall.mail.SendMailUtil;
import com.ds.safecall.util.ManagerUtil;
import com.ds.safecall.util.PhoneUtil;
import com.ds.safecall.util.SharedPreferencesUtils;

import java.util.List;

public class ManagerReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equalsIgnoreCase(ManagerUtil.ACTION_FOR_SEND_MESSAGE)) {
            String number = intent.getStringExtra("number");
            sendMessage(context,number);
        } else if(intent.getAction().equalsIgnoreCase(ManagerUtil.ACTION_FOR_SEND_EMAIL)) {
            String number = intent.getStringExtra("number");
            sendEmail(context,number);
        }
    }

    private void sendEmail(Context context, String number) {
        if(PhoneUtil.isNetworkAvailable(context)) {
            SendMailUtil.send(context);
        }
    }

    private void sendMessage(Context context, String number) {
        if(!PhoneUtil.hasSimCard(context)) {
            return;
        }
        String name = SharedPreferencesUtils.getMyName(context);
        if(TextUtils.isEmpty(name)) {
            name = SharedPreferencesUtils.getMyEmail(context);
        }
        String location = SharedPreferencesUtils.getMyLocation(context);
        String msg = String.format(context.getResources().getString(R.string.send_msg_content),
                name,number,location);
        List<People> people = SharedPreferencesUtils.getPeople(context);
        for(People p: people) {
            PhoneUtil.sendSMS(context,p.getPhone(),msg);
        }
    }
}
