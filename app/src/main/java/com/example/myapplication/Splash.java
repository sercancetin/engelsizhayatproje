package com.example.myapplication;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.VideoView;

public class Splash extends AppCompatActivity {
    VideoView video;
    Button btngetstarted;
    Animation animationZ;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        video = findViewById(R.id.video);
        btngetstarted = findViewById(R.id.btngetstarted);
        linearLayout = findViewById(R.id.lytsplashslogan);
        animationZ = AnimationUtils.loadAnimation(this,R.anim.lefttoright);
        linearLayout.startAnimation(animationZ);
        videoYukle();
        btngetstarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                finish();
            }
        });
    }
    private void videoYukle(){
        Uri adres = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.video);
        video.setVideoURI(adres);
        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
        video.start();
    }
}
