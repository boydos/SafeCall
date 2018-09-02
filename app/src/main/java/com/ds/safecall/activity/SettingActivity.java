package com.ds.safecall.activity;

import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ds.safecall.R;
import com.ds.safecall.bean.People;
import com.ds.safecall.util.ManagerUtil;
import com.ds.safecall.util.SharedPreferencesUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.setting_home_layout)
    LinearLayout homeLayout;

    @BindView(R.id.back_btn)
    ImageView backBtn;

    @BindView(R.id.save_btn)
    TextView saveBtn;

    @BindView(R.id.my_name_id)
    TextView myName;

    @BindView(R.id.my_email)
    TextView myEmail;

    @BindView(R.id.call_number)
    TextView callNumber;

    @Override
    public void initView() {
        setContentView(R.layout.setting_activity);
        ButterKnife.bind(this);
        fixedStatusBar(homeLayout);
    }

    @Override
    public void initEvent() {
        backBtn.setOnClickListener(finishClick);
        saveBtn.setOnClickListener(saveClick);
    }

    @Override
    public void initData() {
        String call = SharedPreferencesUtils.getDial(SettingActivity.this);
        String name = SharedPreferencesUtils.getMyName(SettingActivity.this);
        String email = SharedPreferencesUtils.getMyEmail(SettingActivity.this);
        callNumber.setText(call);
        myName.setText(name);
        myEmail.setText(email);
    }

    private boolean checkInput() {
        String name = myName.getText().toString();
        String email = myEmail.getText().toString();
        if(TextUtils.isEmpty(name)) {
            myName.requestFocus();
            Toast.makeText(SettingActivity.this,
                    getResources().getString(R.string.tip_for_empty),
                    Toast.LENGTH_SHORT)
                    .show();
            return false;
        }

        if(TextUtils.isEmpty(email)) {
            myEmail.requestFocus();
        } else if(!ManagerUtil.isEmail(email)) {
            myEmail.requestFocus();
            Toast.makeText(SettingActivity.this,
                    String.format(getResources().getString(R.string.tip_for_email)
                            ,email),
                    Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
        return true;
    }

    private void saveMyInfo() {
        String name = myName.getText().toString();
        String email = myEmail.getText().toString();
        SharedPreferencesUtils.saveMyInfo(SettingActivity.this, name, email);
    }

    private void saveDial() {
        String number = callNumber.getText().toString();
        SharedPreferencesUtils.saveDial(SettingActivity.this, number);
    }

    View.OnClickListener saveClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           closeAndSave();
        }
    };

    View.OnClickListener finishClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            closeAndSave();
        }
    };

    private void closeAndSave() {
        if(checkInput()) {
            saveMyInfo();
            saveDial();
            Toast.makeText(SettingActivity.this,
                    getResources().getString(R.string.tip_for_success),
                    Toast.LENGTH_SHORT)
                    .show();
            if(checkPeopleEmpty()) {
                return;
            }
            if(!ManagerUtil.HOME_ACTIVITY_START) {
                gotoActivity(HomeActivity.class);
                return;
            }
            SettingActivity.this.finish();
            return;
        }

    }

    private boolean checkPeopleEmpty() {
        List<People> peoples = SharedPreferencesUtils.getPeople(SettingActivity.this);
        if(peoples == null || peoples.size() == 0) {
            gotoActivity(EditActivity.class);
            return true;
        }
        return false;
    }

    private void gotoActivity(final Class cls) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SettingActivity.this, cls));
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
