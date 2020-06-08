package com.java.videoviewsample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;

public class FullScreenMediaController extends MediaController {
    private ImageButton fullScreen;
    private VideoView videoView;
    private String isFullScreen;

    public FullScreenMediaController(Context context, VideoView videoView) {
        super(context);
        this.videoView = videoView;
    }

    @Override
    public void setAnchorView(View view) {
        super.setAnchorView(view);

        //image button for full screen to be added to media controller
        fullScreen = new ImageButton(super.getContext());

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.RIGHT;
        params.rightMargin = 80;
        addView(fullScreen, params);

        //fullscreen indicator from intent
        isFullScreen = ((Activity) getContext()).getIntent().getStringExtra("fullScreenInd");

        if ("y".equals(isFullScreen)) {
            fullScreen.setImageResource(R.drawable.ic_fullscreen_exit);
        } else {
            fullScreen.setImageResource(R.drawable.ic_fullscreen);
        }

        //add listener to image button to handle full screen and exit full screen events
        fullScreen.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int stopPosition = 0;
                Intent intent = new Intent(getContext(), VideoViewActivity.class);
                if (videoView.isPlaying()) {
                    videoView.pause();
                    stopPosition = videoView.getCurrentPosition();
                }

                if ("y".equals(isFullScreen)) {
                    intent.putExtra("fullScreenInd", "");
                    intent.putExtra("stopPosition", stopPosition);
                } else {
                    intent.putExtra("fullScreenInd", "y");
                    intent.putExtra("stopPosition", stopPosition);
                }
                ((Activity) getContext()).startActivity(intent);
            }
        });
    }


}
