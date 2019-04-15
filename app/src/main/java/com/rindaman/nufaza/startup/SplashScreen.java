package com.rindaman.nufaza.startup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.rindaman.nufaza.R;

public class SplashScreen extends AppCompatActivity {
private ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        iv = (ImageView)findViewById(R.id.iv);
        final Intent i = new Intent(SplashScreen.this,Login.class);
        Thread timer = new Thread(){
            public void run(){
                try{
                    sleep(5000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                finally {
                    finish();
                    startActivity(i);
                }
            }
        };
        timer.start();
    }
}
