package com.mygame.talktofriends;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.skydoves.balloon.Balloon;
import com.skydoves.balloon.BalloonAnimation;

public class QuizActivity extends AppCompatActivity {
    Button button1,button2,button3,button4,button5,button6,button7,button8,button9;

    ImageView bee;
    Button geri;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);


        MobileAds.initialize(this,"ca-app-pub-2724540026642482~8500905698");
            final InterstitialAd interstitialAd = new InterstitialAd(this);
            interstitialAd.setAdUnitId("ca-app-pub-2724540026642482/5661073643");
            interstitialAd.loadAd(new AdRequest.Builder().build());
            interstitialAd.setAdListener(new AdListener(){
                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    interstitialAd.show();
                }
            });

        geri=findViewById(R.id.geri);
        button1=findViewById(R.id.test1);
        button2=findViewById(R.id.test2);
        button3=findViewById(R.id.test3);
        button4=findViewById(R.id.test4);
        button5=findViewById(R.id.test5);
        button6=findViewById(R.id.test6);
        button7=findViewById(R.id.test7);
        button8=findViewById(R.id.test8);
        button9=findViewById(R.id.test9);
        bee=findViewById(R.id.bee);


        try{


        }catch(Exception e){
            e.printStackTrace();
        }

       final Intent intenttes1 = new Intent(QuizActivity.this, TestActivity1.class);
        geri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizActivity.this,MainActivity.class);
                startActivity(intent);

            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                intenttes1.putExtra("button","test1");
                startActivity(intenttes1);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intenttes1.putExtra("button","test2");
                startActivity(intenttes1);

            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intenttes1.putExtra("button","test3");
                startActivity(intenttes1);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intenttes1.putExtra("button","test4");
                startActivity(intenttes1);
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intenttes1.putExtra("button","test5");
                startActivity(intenttes1);
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intenttes1.putExtra("button","test6");
                startActivity(intenttes1);
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intenttes1.putExtra("button","test7");
                startActivity(intenttes1);
            }
        });
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intenttes1.putExtra("button","test8");
                startActivity(intenttes1);
            }
        });
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intenttes1.putExtra("button","test9");
                startActivity(intenttes1);
            }
        });


        bee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Balloon balloon = new Balloon.Builder(getApplicationContext())

                        .setArrowSize(10)
                        .setArrowVisible(true)
                        .setWidthRatio(0.6f)
                        .setHeight(65)
                        .setTextSize(14f)
                        .setArrowPosition(1.5f)
                        .setCornerRadius(4f)
                        .setText("I have questions for you.")
                        .setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorspecieal))
                        .setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorbeyaz))
                        .setBalloonAnimation(BalloonAnimation.ELASTIC)
                        .build();
                balloon.showAlignRight(bee);
            }
        });

    }
}