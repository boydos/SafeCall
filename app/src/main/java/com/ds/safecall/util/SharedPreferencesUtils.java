package com.ds.safecall.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

import com.ds.safecall.bean.People;

import java.util.ArrayList;
import java.util.List;

public class SharedPreferencesUtils {

    public final static String STORE_FILE_NAME="sp_people_list";
    public final static String STORE_SETTINH_FILE_NAME="sp_people_setting";

    public final static String STORE_KEY_MY_NAME="key_my_name";
    public final static String STORE_KEY_MY_EMAIL="key_my_email";
    public final static String STORE_KEY_DIAL_NUMBER="key_dial_number";
    public final static String STORE_KEY_LAST_LOCATION="key_last_location";

    public final static String STORE_KEY_PEOPLE_ONE="key_people_1_user";
    public final static String STORE_KEY_PEOPLE_TWO="key_people_2_user";
    public final static String STORE_KEY_PEOPLE_THREE="key_people_3_user";

    public final static String PEOPLE_DATA_SPLIT="##_TDS_##";

    public static int getAppPrefInt(Context context, String prefName){
        int result = -1;
        if(context != null){
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            if(sharedPreferences!=null){
                result = sharedPreferences.getInt(
                        prefName, -1);
            }
        }
        return result;
    }

    public static void putAppPrefInt(Context context, String prefName, int value) {
        if(context!=null){
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putInt(prefName, value);
            if(Build.VERSION.SDK_INT>=9){
                edit.apply();
            }else{
                edit.commit();
            }
        }
    }

    public static void save(Context context, List<People> people) {
        SharedPreferences sp = context.getSharedPreferences(STORE_FILE_NAME,
                Activity.MODE_PRIVATE);//创建sp对象
        SharedPreferences.Editor editor = sp.edit();
        int size = people == null? 0 : people.size();
        if(size == 0) {
            editor.clear();
        } else {
            editor.putString(STORE_KEY_PEOPLE_ONE, size > 0 ? formatPeople(people.get(0)) : "");
            editor.putString(STORE_KEY_PEOPLE_TWO, size > 1 ? formatPeople(people.get(1)) : "");
            editor.putString(STORE_KEY_PEOPLE_THREE, size > 2 ? formatPeople(people.get(2)) : "");
        }
        editor.commit() ;
    }

    public static List<People> getPeople(Context context) {
        SharedPreferences sp = context.getSharedPreferences(STORE_FILE_NAME,
                Activity.MODE_PRIVATE);//创建sp对象
        String people1 = sp.getString(STORE_KEY_PEOPLE_ONE,"");
        String people2 = sp.getString(STORE_KEY_PEOPLE_TWO,"");
        String people3 = sp.getString(STORE_KEY_PEOPLE_THREE,"");
        List<People> peoples = new ArrayList<>();
        People p1 = getPeople(people1);
        if(p1 != null) {
          peoples.add(p1);
        }
        People p2 = getPeople(people2);
        if(p2 != null) {
            peoples.add(p2);
        }
        People p3 = getPeople(people3);
        if(p3 != null) {
            peoples.add(p3);
        }
        return peoples;
    }

    public static void saveMyInfo(Context context, String name, String email) {
        SharedPreferences sp = context.getSharedPreferences(STORE_SETTINH_FILE_NAME,
                Activity.MODE_PRIVATE);//创建sp对象
        SharedPreferences.Editor editor = sp.edit();
        if(name != null) {
            editor.putString(STORE_KEY_MY_NAME, name);
        }
        if(email !=null ) {
            editor.putString(STORE_KEY_MY_EMAIL,email);
        }
        editor.commit();
    }

    public static void saveMyName(Context context, String name) {
        saveMyInfo(context,name, null);
    }
    public static void saveMyEmail(Context context, String email) {
        saveMyInfo(context,null, email);
    }

    public static void saveDial(Context context, String number) {
        SharedPreferences sp = context.getSharedPreferences(STORE_SETTINH_FILE_NAME,
                Activity.MODE_PRIVATE);//创建sp对象
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(STORE_KEY_DIAL_NUMBER,number);
        editor.commit();
    }

    public static void saveLocation(Context context, String location) {
        SharedPreferences sp = context.getSharedPreferences(STORE_SETTINH_FILE_NAME,
                Activity.MODE_PRIVATE);//创建sp对象
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(STORE_KEY_LAST_LOCATION,location);
        editor.commit();
    }

    public static String getMyLocation(Context context) {
        SharedPreferences sp = context.getSharedPreferences(STORE_SETTINH_FILE_NAME,
                Activity.MODE_PRIVATE);//创建sp对象
        return sp.getString(STORE_KEY_LAST_LOCATION,"");
    }
    public static String getMyName(Context context) {
        SharedPreferences sp = context.getSharedPreferences(STORE_SETTINH_FILE_NAME,
                Activity.MODE_PRIVATE);//创建sp对象
       return sp.getString(STORE_KEY_MY_NAME,"");
    }
    public static String getMyEmail(Context context) {
        SharedPreferences sp = context.getSharedPreferences(STORE_SETTINH_FILE_NAME,
                Activity.MODE_PRIVATE);//创建sp对象
        return sp.getString(STORE_KEY_MY_EMAIL,"");
    }

    public static String getDial(Context context) {
        SharedPreferences sp = context.getSharedPreferences(STORE_SETTINH_FILE_NAME,
                Activity.MODE_PRIVATE);//创建sp对象
        return sp.getString(STORE_KEY_DIAL_NUMBER,"110");
    }

    private static String formatPeople(People people) {
        if(people == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(people.getName())
                .append(PEOPLE_DATA_SPLIT)
                .append(people.getEmail())
                .append(PEOPLE_DATA_SPLIT)
                .append(people.getPhone());
        return sb.toString();
    }

    private static People getPeople(String data) {
        if(data == null || data.length() == 0) {
            return null;
        }
        String[] temp = data.split(PEOPLE_DATA_SPLIT);
        if(temp.length >= 3) {
            People people = new People();
            people.setName(temp[0]);
            people.setEmail(temp[1]);
            people.setPhone(temp[2]);
            return people;
        }
        return null;
    }
}
