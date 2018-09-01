package com.safecall.xj.safecall.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.safecall.xj.safecall.R;

import butterknife.ButterKnife;

public class HomeFragment extends BaseFragment {
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        ButterKnife.bind(this,view);
        return view;
    }
}
