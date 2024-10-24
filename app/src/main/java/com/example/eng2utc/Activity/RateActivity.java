package com.example.eng2utc.Activity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RatingBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.eng2utc.R;

public class RateActivity extends AppCompatActivity {

    private float userRate = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);


        final AppCompatButton rateNowBtn = findViewById(R.id.rateNowbtn);
        final AppCompatButton returnBtn = findViewById(R.id.laterbtn);
        final RatingBar ratingBar = findViewById(R.id.ratingBar);
        final ImageView ratingImage =findViewById(R.id.ratingImage);
        rateNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Quay trở lại SettingActivity
                finish();  // Kết thúc RateActivity
            }
        });
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(rating <= 1){
                    ratingImage.setImageResource(R.drawable.ic_rate_one_star);
                }
                else if(rating <= 2){
                    ratingImage.setImageResource(R.drawable.ic_rate_two_star);
                }
                else if(rating <= 3){
                    ratingImage.setImageResource(R.drawable.ic_rate_three_star);
                }
                else if(rating <= 4){
                    ratingImage.setImageResource(R.drawable.ic_rate_four_star);
                }
                else if(rating <= 5){
                    ratingImage.setImageResource(R.drawable.ic_rate_five_star);
                }
                animateImage(ratingImage);
                userRate = rating;
            }
        });

    }
    private void animateImage(ImageView ratingImage){
        ScaleAnimation scaleAnimation = new ScaleAnimation(0,1f, 0,1f,
                Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(200);
        ratingImage.startAnimation(scaleAnimation);
    }
}