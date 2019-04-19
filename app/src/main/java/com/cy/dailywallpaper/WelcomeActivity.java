package com.cy.dailywallpaper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.cy.dailywallpaper.model.utils.LogUtils;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        LogUtils.log("++++++++++++++++++"+stringFromJNI());
//        String[] mapApi= JniUtils.getApiAddress();
//        for (String api:mapApi){
//
//            LogUtils.log(api);
//        }
//        // Example of a call to a native method
//        TextView tv = (TextView) findViewById(R.id.sample_text);
//        tv.setText(stringFromJNI());
    }
//
//    /**
//     * A native method that is implemented by the 'native-lib' native library,
//     * which is packaged with this application.
//     */
    public native String stringFromJNI();
//
//    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }
}
