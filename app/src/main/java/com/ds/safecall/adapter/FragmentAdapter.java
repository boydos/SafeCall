package com.ds.safecall.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by jpeng on 16-11-14.
 */
public class FragmentAdapter extends FragmentPagerAdapter{
    private List<Fragment> list;
    private List<String> titles;
    private FragmentManager fm;
    public FragmentAdapter(FragmentManager fm, List<Fragment> list) {
        this(fm,list,null);
    }
    public FragmentAdapter(FragmentManager fm, List<Fragment> list,List<String> titles) {
        super(fm);
        this.fm =fm;
        this.list = list;
        this.titles = titles;
    }

    public void update(List<Fragment> list,List<String> titles) {
        this.titles = titles;
        /*if (this.list != null) {
            FragmentTransaction ft = fm.beginTransaction();
            for (Fragment f : this.list) {
                ft.remove(f);
            }
            ft.commit();
            ft = null;
            fm.executePendingTransactions();
        }*/
        this.list = list;

        Log.d("tds-ds",titles.size()+"===");
        notifyDataSetChanged();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        // TODO Auto-generated method stub
        Log.d("tds-ds","getPageTitle+"+position +"=="+(titles==null||titles.size()==0));
        if(titles==null||titles.size()==0) {
            return super.getPageTitle(position);
        } else {
            Log.d("tds-ds","titles="+titles.get(position));
            return titles.get(position);
        }
    }
    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }


    @Override
    public int getCount() {
        return list.size();
    }

    /*@Override
     public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }*/
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public Object instantiateItem(ViewGroup arg0, int arg1) {
        return super.instantiateItem(arg0, arg1);
    }

}
