package com.java.videoviewsample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoViewActivity extends AppCompatActivity {
    private MediaController mediaController;
    private VideoView videoView;
    private int stopPosition = 0;
    private String fullScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);
        initializeView();

        if (getIntent().hasExtra("fullScreenInd")) {
            fullScreen = getIntent().getStringExtra("fullScreenInd");
        }
        if (getIntent().hasExtra("stopPosition")) {
            stopPosition = getIntent().getIntExtra("stopPosition", 0);
        }

        if ("y".equals(fullScreen)) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getSupportActionBar().hide();

            if (isLandScape()) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                mediaController = new FullScreenMediaController(VideoViewActivity.this, videoView);
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                mediaController = new MediaController(this);
            }
        }

        if (stopPosition == 0) {
            playMyVideo();
        } else {
            onResume();
        }


    }

    private void initializeView() {
        videoView = findViewById(R.id.videoView);
    }

    private void playMyVideo() {
        Uri videoUri = Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4");
        videoView.setVideoURI(videoUri);

        mediaController = new FullScreenMediaController(this, videoView);
        mediaController.setAnchorView(videoView);

        videoView.setMediaController(mediaController);
        videoView.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        Uri videoUri = Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4");
        videoView.setVideoURI(videoUri);
        mediaController = new FullScreenMediaController(this, videoView);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        videoView.seekTo(stopPosition);
        videoView.start();
    }

    private boolean isLandScape() {
        boolean res;
        Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        int rotation = display.getRotation();

        if (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270) {
            res = true;
        } else {
            res = false;
        }
        return res;
    }


}