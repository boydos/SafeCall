package com.ds.safecall.activity;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ds.safecall.R;
import com.ds.safecall.adapter.ScreenSlidePagerAdapter;
import com.ds.safecall.fragment.TourFragment;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TourActivity extends BaseActivity {

    private static final int NUM_PAGES = 4;

    @BindView(R.id.pager)
    ViewPager pager;

    PagerAdapter pagerAdapter;

    @BindView(R.id.circles)
    LinearLayout circles;
    @BindView(R.id.skip)
    Button skip;
    @BindView(R.id.done)
    Button done;
    @BindView(R.id.next)
    ImageButton next;

    boolean isOpaque = true;

    @Override
    public void initView() {
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_tutorial);
        ButterKnife.bind(this);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(TourFragment.newInstance(R.layout.tour_fragment1));
        fragments.add(TourFragment.newInstance(R.layout.tour_fragment2));
        fragments.add(TourFragment.newInstance(R.layout.tour_fragment3));
        fragments.add(TourFragment.newInstance(R.layout.tour_fragment_end));

        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(),fragments);

    }

    @Override
    public void initEvent() {
        skip.setOnClickListener(finishClick);
        done.setOnClickListener(finishClick);
        next.setOnClickListener(nextClick);

        pager.setAdapter(pagerAdapter);
        pager.setPageTransformer(true, new CrossfadePageTransformer());
        pager.addOnPageChangeListener(pageChangeListener);
    }

    @Override
    public void initData() {
        buildCircles();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(pager!=null){
            pager.clearOnPageChangeListeners();
        }
    }

    private void buildCircles(){
        float scale = getResources().getDisplayMetrics().density;
        int padding = (int) (5 * scale + 0.5f);

        for(int i = 0 ; i < NUM_PAGES - 1 ; i++){
            ImageView circle = new ImageView(this);
            circle.setImageResource(R.drawable.ic_swipe_indicator_white_18dp);
            circle.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            circle.setAdjustViewBounds(true);
            circle.setPadding(padding, 0, padding, 0);
            circles.addView(circle);
        }
        setIndicator(0);
    }

    private void setIndicator(int index){
        if(index < NUM_PAGES){
            for(int i = 0 ; i < NUM_PAGES - 1 ; i++){
                ImageView circle = (ImageView) circles.getChildAt(i);
                if(i == index){
                    circle.setColorFilter(getResources().getColor(R.color.text_selected));
                }else {
                    circle.setColorFilter(getResources().getColor(android.R.color.transparent));
                }
            }
        }
    }

    ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (position == NUM_PAGES - 2 && positionOffset > 0) {
                if (isOpaque) {
                    pager.setBackgroundColor(Color.TRANSPARENT);
                    isOpaque = false;
                }
            } else {
                if (!isOpaque) {
                    pager.setBackgroundColor(getResources().getColor(R.color.primary_material_light));
                    isOpaque = true;
                }
            }
        }

        @Override
        public void onPageSelected(int position) {
            setIndicator(position);
            if (position == NUM_PAGES - 2) {
                skip.setVisibility(View.GONE);
                next.setVisibility(View.GONE);
                done.setVisibility(View.VISIBLE);
            } else if (position < NUM_PAGES - 2) {
                skip.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);
                done.setVisibility(View.GONE);
            } else if (position == NUM_PAGES - 1) {
                endTutorial();
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    View.OnClickListener finishClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            endTutorial();
        }
    };

    View.OnClickListener nextClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            pager.setCurrentItem(pager.getCurrentItem() + 1, true);
        }
    };

    private void endTutorial(){
        finish();
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
    }

    @Override
    public void onBackPressed() {
        if (pager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            pager.setCurrentItem(pager.getCurrentItem() - 1);
        }
    }

    public class CrossfadePageTransformer implements ViewPager.PageTransformer {

        @Override
        public void transformPage(View page, float position) {
            int pageWidth = page.getWidth();
            View backgroundView = page.findViewById(R.id.welcome_fragment);
            View text_head= page.findViewById(R.id.heading);
            View text_content = page.findViewById(R.id.content);
            View welcomeImage01 = page.findViewById(R.id.welcome_01);
            View welcomeImage02 = page.findViewById(R.id.welcome_02);
            View welcomeImage03 = page.findViewById(R.id.welcome_03);

            if(0 <= position && position < 1){
                ViewHelper.setTranslationX(page,pageWidth * -position);
            }
            if(-1 < position && position < 0){
                ViewHelper.setTranslationX(page,pageWidth * -position);
            }

            if(position <= -1.0f || position >= 1.0f) {
            } else if( position == 0.0f ) {
            } else {
                if(backgroundView != null) {
                    ViewHelper.setAlpha(backgroundView,1.0f - Math.abs(position));

                }

                if (text_head != null) {
                    ViewHelper.setTranslationX(text_head,pageWidth * position);
                    ViewHelper.setAlpha(text_head,1.0f - Math.abs(position));
                }

                if (text_content != null) {
                    ViewHelper.setTranslationX(text_content,pageWidth * position);
                    ViewHelper.setAlpha(text_content,1.0f - Math.abs(position));
                }

                if (welcomeImage01 != null) {
                    ViewHelper.setTranslationX(welcomeImage01,(float)(pageWidth/2 * position));
                    ViewHelper.setAlpha(welcomeImage01,1.0f - Math.abs(position));
                }

                if (welcomeImage02 != null) {
                    ViewHelper.setTranslationX(welcomeImage02,(float)(pageWidth/2 * position));
                    ViewHelper.setAlpha(welcomeImage02,1.0f - Math.abs(position));
                }

                if (welcomeImage03 != null) {
                    ViewHelper.setTranslationX(welcomeImage03,(float)(pageWidth/2 * position));
                    ViewHelper.setAlpha(welcomeImage03,1.0f - Math.abs(position));
                }
            }


        }
    }
}
