package com.ds.safecall.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment  extends Fragment{
	public Activity mContext;
	private View mView;
	private boolean isVisible=false;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.d("tds-fragment","init=="+(mView ==null)+"=="+this.getClass().getCanonicalName());
		init();
		if(mView ==null) {
			mView=initView(inflater, container);
		}
		return mView;
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		/*if(getUserVisibleHint()) {
			performLazyLoad();
		} else {
			performInVisible();
		}*/

	}

	public void setBaseView(View view) {
		this.mView = view;
	}
	public View getBaseView() {
		return mView;
	}

	public boolean performLazyLoad() {
			if(!isVisible) {
				isVisible = true;
				lazyLoad();
				return true;
			}
		return false;
	}
	public boolean performInVisible() {
		Log.d("tds-fragment","performInVisible"+this.getClass().getCanonicalName());

		if(isVisible) {
			isVisible = false;
			onInVisible();
			return true;
		}
		return false;
	}
	protected void lazyLoad() {
		Log.d("tds-fragment","lazyLoad"+this.getClass().getCanonicalName());
	}
	protected void onInVisible (){
		Log.d("tds-fragment","onInVisible"+this.getClass().getCanonicalName());
	}
	public Context getBaseContext() {
		return this.getActivity().getBaseContext();
	}
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		if(mView!=null) {
			ViewGroup parent = (ViewGroup)mView.getParent();
			if(parent!=null)parent.removeView(mView);
		}
	}
	protected abstract View initView(LayoutInflater inflater,ViewGroup container);
	protected void init() {

	}
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mContext = activity;
	}

	protected  void switchFragment(int id,Fragment fragment) {
		FragmentManager fragmentManager =getActivity().getSupportFragmentManager();
		if(fragment !=null&& fragmentManager!=null) {
			fragmentManager.beginTransaction().add(id,fragment).commit();
		}
	}

}
