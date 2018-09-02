package com.ds.safecall.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TourFragment extends BaseFragment {

    private final static String LAYOUT_ID = "layoutid";

    public static TourFragment newInstance(int layoutId) {
        TourFragment pane = new TourFragment();
        Bundle args = new Bundle();
        args.putInt(LAYOUT_ID, layoutId);
        pane.setArguments(args);
        return pane;
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        ViewGroup view = (ViewGroup) inflater.inflate(
                getArguments().getInt(LAYOUT_ID, -1),
                container, false);
        return view;
    }
}
