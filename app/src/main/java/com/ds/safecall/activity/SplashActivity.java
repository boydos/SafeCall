package com.ds.safecall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.ds.safecall.R;
import com.ds.safecall.bean.People;
import com.ds.safecall.util.ManagerUtil;
import com.ds.safecall.util.SharedPreferencesUtils;

import java.util.List;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        String myName = SharedPreferencesUtils.getMyName(SplashActivity.this);

        if(TextUtils.isEmpty(myName)) {
            gotoActivity(SettingActivity.class);
            return;
        }
        List<People> peoples = SharedPreferencesUtils.getPeople(SplashActivity.this);

        if(peoples == null || peoples.size() == 0) {
            gotoActivity(EditActivity.class);
            return;
        }

        gotoActivity(HomeActivity.class);
    }

    private void gotoActivity(final Class cls) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, cls));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        finish();
                    }
                }, 300);
            }
        }, 500);
    }
}
