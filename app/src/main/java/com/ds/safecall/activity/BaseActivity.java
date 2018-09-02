package com.ds.safecall.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ds.safecall.R;

import java.util.ArrayList;

/**
 * Created by xj on 18-8-30.
 */

public class BaseActivity extends AppCompatActivity {
    private ArrayList<BaseActivity> activities = new ArrayList<BaseActivity>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        synchronized (activities) {
            activities.add(this);
        }
        //StatusBarCompat.compat(this, getResources().getColor(R.color.colorPrimary));
        initView();
        initToolBar();
        initEvent();
        initData();
    }

    @Override
    protected void onDestroy() {
        synchronized (this) {
            activities.remove(this);
        }
        super.onDestroy();
    }
    protected void enableHomeButton() {
        ActionBar bar = getSupportActionBar();
        if(bar !=null) {
            bar.setHomeAsUpIndicator(R.drawable.ic_back);
            bar.setHomeButtonEnabled(true);
            bar.setDisplayHomeAsUpEnabled(true);
        }
    }
    protected void fixedStatusBar(LinearLayout layout) {
        if(layout ==null) return;
        ViewGroup.LayoutParams layoutParams = layout.getLayoutParams();
        if(layoutParams instanceof FrameLayout.LayoutParams) {
            ((FrameLayout.LayoutParams)layoutParams).topMargin = getStatusHeight();
        } else if(layoutParams instanceof LinearLayout.LayoutParams) {
            ((LinearLayout.LayoutParams)layoutParams).topMargin = getStatusHeight();
        } else if(layoutParams instanceof RelativeLayout.LayoutParams) {
            ((RelativeLayout.LayoutParams)layoutParams).topMargin = getStatusHeight();
        } else if(layoutParams instanceof RecyclerView.LayoutParams) {
            ((RecyclerView.LayoutParams)layoutParams).topMargin = getStatusHeight();
        }
    }

    protected int getStatusHeight() {
        Rect frame = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        return frame.top;
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void initToolBar() {

    }
    public void initView() {

    }
    public void initEvent(){

    }
    public void initData() {

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() ==android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public  void killAll() {
        ArrayList<BaseActivity> copy;
        synchronized (activities) {
            copy = new ArrayList<BaseActivity>(activities);
        }
        for(BaseActivity activity:copy) {
            activities.remove(activity);
        }
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    @Override
    public void finish() {
        super.finish();
    }

}
