package com.ds.safecall.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.ds.safecall.R;
import com.ds.safecall.gps.BaiduLocationHelper;
import com.ds.safecall.gps.GPSLocationHelper;
import com.ds.safecall.util.ManagerUtil;
import com.ds.safecall.util.PhoneUtil;
import com.ds.safecall.util.SharedPreferencesUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity {

    @BindView(R.id.home_layout_id)
    LinearLayout homeLayout;

    @BindView(R.id.emergency_btn_id)
    ImageView emergencyBtn;

    @BindView(R.id.setting_btn)
    ImageView settingBtn;

    BaiduLocationHelper baiduLocationHelper;

    @Override
    public void initView() {
        ManagerUtil.HOME_ACTIVITY_START = true;
        checkShowTutorial();
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        fixedStatusBar(homeLayout);
        baiduLocationHelper = new BaiduLocationHelper(HomeActivity.this);
        baiduLocationHelper.start();
    }

    @Override
    protected void onDestroy() {
        ManagerUtil.HOME_ACTIVITY_START = false;
        baiduLocationHelper.stop();
        super.onDestroy();
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(HomeActivity.this, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.main, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(menuClick);
        popupMenu.show();
    }

    private void checkShowTutorial(){
        int oldVersionCode = SharedPreferencesUtils.getAppPrefInt(this, "version_code");
        int currentVersionCode = PhoneUtil.getAppVersionCode(this);
        if(currentVersionCode>oldVersionCode){
            startActivity(new Intent(this,TourActivity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            SharedPreferencesUtils.putAppPrefInt(this, "version_code", currentVersionCode);
        }
    }

    @Override
    public void initEvent() {
        emergencyBtn.setOnTouchListener(viewPress);
        settingBtn.setOnClickListener(settingOnClick);
    }

    private void emergencyClick() {
        final String phoneNumber = SharedPreferencesUtils.getDial(HomeActivity.this);
        TextView msg = new TextView(this);
        msg.setText(phoneNumber);
        msg.setPadding(10, 20, 10, 10);
        msg.setGravity(Gravity.CENTER);
        msg.setTextColor(Color.BLACK);
        msg.setTextSize(18);

        sendEmail(phoneNumber);
        AlertDialog dialog = new AlertDialog.Builder(HomeActivity.this)
                .setView(msg)
                .setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(getResources().getString(R.string.confrim), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(PhoneUtil.requestCallPermission(HomeActivity.this)) {
                            callPhone(phoneNumber);
                        }
                        if(PhoneUtil.requestSMSPermission(HomeActivity.this)) {
                            sendMessage(phoneNumber);
                        }
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }


    /**
     * 注册权限申请回调
     * @param requestCode 申请码
     * @param permissions 申请的权限
     * @param grantResults 结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        switch (requestCode)
        {
            case  PhoneUtil.REQUEST_CODE_ASK_CALL_PHONE:
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callPhone(SharedPreferencesUtils.getDial(HomeActivity.this));
                } else {
                    Toast.makeText(HomeActivity.this, "没有打电话权限", Toast.LENGTH_SHORT).show();
                }
                break;
            case PhoneUtil.REQUEST_CODE_ASK_SMS_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sendMessage(SharedPreferencesUtils.getDial(HomeActivity.this));
                } else {
                    Toast.makeText(this, "没有发短信权限", Toast.LENGTH_SHORT).show();
                }
                break;
            case GPSLocationHelper.SETTING_GPS_GRANT_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    baiduLocationHelper.start();
                } else {
                    Toast.makeText(this, "没有发短信权限", Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void callPhone(String number)
    {
        if(PhoneUtil.hasSimCard(HomeActivity.this)) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + number));
            startActivity(intent);
        } else {
            Toast.makeText(this, "没有手机卡", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendEmail(String number) {
        Intent intent = new Intent(ManagerUtil.ACTION_FOR_SEND_EMAIL);
        intent.putExtra("number", number);
        sendBroadcast(intent);
    }

    private void sendMessage(String number) {
        Intent intent = new Intent(ManagerUtil.ACTION_FOR_SEND_MESSAGE);
        intent.putExtra("number", number);
        sendBroadcast(intent);
    }

    View.OnClickListener settingOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showPopupMenu(v);
        }
    };

    View.OnTouchListener viewPress = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {

            if(event.getAction() == MotionEvent.ACTION_DOWN) {
              Animation animation = AnimationUtils.loadAnimation(HomeActivity.this, R.anim.btn_scale_down);
              view.startAnimation(animation);
              view.setScaleX((float)0.8);
              view.setScaleY((float)0.8);
              return true;
            }

            if(event.getAction() == MotionEvent.ACTION_UP) {
                Animation animation = AnimationUtils.loadAnimation(HomeActivity.this, R.anim.btn_scale_up);
                view.startAnimation(animation);
                view.setScaleX((float)1);
                view.setScaleY((float)1);
                emergencyClick();
                return true;
            }
            return false;
        }
    };

    PopupMenu.OnMenuItemClickListener menuClick = new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            Intent intent;
            switch (item.getItemId()) {
                case R.id.add_people:
                    intent = new Intent(HomeActivity.this,EditActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;
                case R.id.setting:
                    intent = new Intent(HomeActivity.this,SettingActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;
                case R.id.show_tutorial:
                    startActivity(new Intent(HomeActivity.this,TourActivity.class));
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    break;
                default:
                    break;
            }
            return false;
        }
    };

}
