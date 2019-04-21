package com.cy.dailywallpaper.view.activity;

import android.os.Bundle;
import android.view.View;

import com.cy.dailywallpaper.JniUtils;
import com.cy.dailywallpaper.R;
import com.cy.dailywallpaper.model.utils.LogUtils;
import com.cy.dailywallpaper.view.base.BaseActivity;

import java.util.HashMap;

public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

//        DiskInfo[] diskInfos=JniUtils.getStructArray();
//        for (DiskInfo diskInfo:diskInfos){
//            LogUtils.log("serialno",diskInfo.getSerialNo());
//            LogUtils.log("name",diskInfo.getName());
//        }

        HashMap<String,String> map=JniUtils.getHashMap();
        for(String key:map.keySet()){
            LogUtils.log("key",key);
            LogUtils.log("value",map.get(key));
        }
//        String[] strings=JniUtils.getApiAddress();
//        for (String str:strings){
//            LogUtils.log("str",str);
//        }
//        String[] mapApi= JniUtils.getApiAddress();
//        for (String api:mapApi){
//
//            LogUtils.log(api);
//        }
    }

    @Override
    public void onClick(View view) {

    }
}
