package com.ds.safecall.gps;

import android.content.Context;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

public class BaiduLocationHelper {

    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();

     public BaiduLocationHelper(Context context) {
            mLocationClient = new LocationClient(context);
            mLocationClient.registerLocationListener(myListener);
            LocationClientOption option = new LocationClientOption();
            option.setIsNeedAddress(true);
            mLocationClient.setLocOption(option);
     }

     public void start() {
         mLocationClient.start();
     }

     public void stop() {
         mLocationClient.unRegisterLocationListener(myListener);
         mLocationClient.stop();
     }
}
