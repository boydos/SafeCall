package com.mobile.jptabbar.animate;

import android.view.View;

/**
 * Created by jpeng on 16-11-14.
 */
public interface Animatable {

    /**
     * 点击播放动画
     */
    void playAnimate(View target, boolean selected);

    /**
     * 页面切换时播放动画
     */
    void onPageAnimate(View target, float offset);

    /**
     * 是否需要页面切换动画
     */
    boolean isNeedPageAnimate();




}
