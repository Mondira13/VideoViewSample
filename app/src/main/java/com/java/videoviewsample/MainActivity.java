package com.java.videoviewsample;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {
    VideoView videoView;
    LinearLayout click;
    ImageView playButton;
    ImageView fullscreen;
    ImageView previous;
    ImageView next;
    int stopPosition = 0;
//    private MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeView();
        onClickListener();

    }

    private void initializeView() {
        videoView = findViewById(R.id.videoView);
        click = findViewById(R.id.click);
        playButton = findViewById(R.id.playButton);
        fullscreen = findViewById(R.id.fullscreen);
    }

    private void onClickListener() {
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (videoView.isPlaying()) {
                    playButton.setVisibility(View.VISIBLE);
                    playButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
                    onPause();
                } else {
                    if (stopPosition == 0) {
                        playVideo();
                    } else {
                        onResume();
                    }
                }

            }
        });

//        fullscreen.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String fullScreen =  getIntent().getStringExtra("fullScreenInd");
//                if("y".equals(fullScreen)){
//                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//                    getSupportActionBar().hide();
//                }
//
//
//                mediaController = new FullScreenMediaController(MainActivity.this);
//                mediaController.setAnchorView(videoView);
//                videoView.setMediaController(mediaController);
//
//                if(isLandScape()){
//                    mediaController = new FullScreenMediaController(MainActivity.this);
//                }else {
//                    mediaController = new MediaController(MainActivity.this);
//                }
//            }
//        });

    }

    private void playVideo() {
        Uri video = Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4");
        videoView.setVideoURI(video);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                videoView.start();
            }
        });
        playButton.setVisibility(View.GONE);
    }

    @Override
    public void onPause() {
        super.onPause();

        if (videoView != null && videoView.isPlaying()) {
            videoView.pause();
            stopPosition = videoView.getCurrentPosition();
            playButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_play));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        videoView.seekTo(stopPosition);
        //vvOnboardingVideos.resume();// this is not working for now - need to investigate
        videoView.start();
        playButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
    }

    private boolean isLandScape(){
        Display display = ((WindowManager) getSystemService(WINDOW_SERVICE))
                .getDefaultDisplay();
        int rotation = display.getRotation();

        if (rotation == Surface.ROTATION_90
                || rotation == Surface.ROTATION_270) {
            return true;
        }
        return false;
    }


}