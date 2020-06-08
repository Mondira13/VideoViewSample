package com.java.videoviewsample;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.VideoView;

public class VideoViewActivity extends AppCompatActivity {
    VideoView videoView;
    ImageView previous;
    ImageView play;
    ImageView next;
    ImageView fullscreen;

    int stopPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);
        initializeView();
        onClickListener();

    }


    private void initializeView() {
        previous = findViewById(R.id.previous);
        play = findViewById(R.id.play);
        next = findViewById(R.id.next);
        fullscreen = findViewById(R.id.fullscreen);
        videoView = findViewById(R.id.videoView);
    }

    private void onClickListener() {
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (videoView.isPlaying()) {
                    play.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
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
    }

    @Override
    public void onPause() {
        super.onPause();

        if (videoView != null && videoView.isPlaying()) {
            videoView.pause();
            stopPosition = videoView.getCurrentPosition();
            play.setImageDrawable(getResources().getDrawable(R.drawable.ic_play));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        videoView.seekTo(stopPosition);
        //vvOnboardingVideos.resume();// this is not working for now - need to investigate
        videoView.start();
        play.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
    }


}