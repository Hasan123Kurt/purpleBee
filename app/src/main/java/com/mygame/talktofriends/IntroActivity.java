package com.mygame.talktofriends;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class IntroActivity extends AppIntro {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addSlide(AppIntroFragment.newInstance("First Step","Create a group or enter a group and chat to new friends ",R.drawable.group_intro,
                ContextCompat.getColor(getApplicationContext(),R.color.slide1)));

        addSlide(AppIntroFragment.newInstance("Second Step","If you want to practice English than click to Quiz",R.drawable.intro_quiz,
                ContextCompat.getColor(getApplicationContext(),R.color.slide2)));

        addSlide(AppIntroFragment.newInstance("Competitive Test","let's get the highest score",R.drawable.competitive,
                ContextCompat.getColor(getApplicationContext(),R.color.slide3)));
    }


    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        finish();
        Intent intent = new Intent(IntroActivity.this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        finish();
        Intent intent = new Intent(IntroActivity.this,MainActivity.class);
        startActivity(intent);
    }
}
