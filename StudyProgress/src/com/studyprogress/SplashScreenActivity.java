package com.studyprogress;


import com.example.studyprogress.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
 
public class SplashScreenActivity extends Activity {
 
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 500;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
 
        new Handler().postDelayed(new Runnable() {

 
            @Override
            public void run() {
                // This method will be executed once the timer is over

                Intent i = new Intent(SplashScreenActivity.this, ChooseStartConfigurationActivity.class);
                startActivity(i);
 
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
 
}
