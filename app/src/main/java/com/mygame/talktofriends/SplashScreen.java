package com.mygame.talktofriends;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_SCREEN_OUT =6000;

    AnimationDrawable animationDrawable;
    ImageView imageView;
    private AdView mAdView;
    MediaPlayer player;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
     //   animationDrawable.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        imageView=findViewById(R.id.image_gif);
       // imageView.setBackgroundResource(R.drawable.animation);
        //animationDrawable=(AnimationDrawable) imageView.getBackground();

        //admob
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //SPLASH SCREEN
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeintent = new Intent(SplashScreen.this,MainActivity.class);
                startActivity(homeintent);
                finish();
            }
        },SPLASH_SCREEN_OUT);

       // player = MediaPlayer.create(SplashScreen.this,R.raw.song);
        //player.start();

    }

}
