package com.safecall.xj.safecall.activity;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;

import com.mobile.jptabbar.BadgeDismissListener;
import com.mobile.jptabbar.JPTabBar;
import com.mobile.jptabbar.OnTabSelectListener;
import com.mobile.jptabbar.anno.NorIcons;
import com.mobile.jptabbar.anno.SeleIcons;
import com.mobile.jptabbar.anno.Titles;
import com.safecall.xj.safecall.R;
import com.safecall.xj.safecall.adapter.FragmentAdapter;
import com.safecall.xj.safecall.fragment.HomeFragment;
import com.safecall.xj.safecall.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity implements BadgeDismissListener, OnTabSelectListener {

    @Titles
    private int[] titles = {R.string.home, R.string.add_people, R.string.show_teach};

    @NorIcons
    private int[] mNormalIcons = {R.drawable.home,R.drawable.add,R.drawable.show_t};

    @SeleIcons
    private int[] mSelectedIcons =  {R.drawable.home_select, R.drawable.add_select,R.drawable.show_t_select};

    private List<Fragment> fragments = new ArrayList<>();

    @BindView(R.id.view_pager)
    public NoScrollViewPager viewPager;

    @BindView(R.id.jpbar)
    public JPTabBar jpTabBar;

    @BindView(R.id.home_layout_id)
    LinearLayout homeLayout;

    @Override
    public void initView() {
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        fixedStatusBar(homeLayout);
        initJPTab();
    }

    private void initJPTab() {
        fragments.clear();
        fragments.add( new HomeFragment());
        fragments.add( new Fragment());
        fragments.add( new Fragment());

        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(),fragments));
        jpTabBar.setContainer(viewPager);
        jpTabBar.setTabListener(this);
        jpTabBar.setDismissListener(this);
        jpTabBar.setIconWidth(21);
        jpTabBar.setIconHeight(21);
    }

    @Override
    public void initEvent() {
        super.initEvent();
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void onDismiss(int position) {

    }

    @Override
    public void onTabSelect(int index) {

    }

    @Override
    public void onClickMiddle(View middleBtn) {

    }
}
