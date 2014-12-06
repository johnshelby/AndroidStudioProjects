package com.ebookfrenzy.videoplayer;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.VideoView;
import android.widget.MediaController;
import android.util.Log;
import android.media.MediaPlayer;

public class VideoPlayerActivity extends ActionBarActivity {

    String TAG = "com.ebookfrenzy.videoplayer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        final VideoView videoView =
                (VideoView) findViewById(R.id.videoView1);

        videoView.setVideoPath(
                "http://www.ebookfrenzy.com/android_book/movie.mp4");

        MediaController mediaController = new
                MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        videoView.setOnPreparedListener(new
                                                MediaPlayer.OnPreparedListener()  {
                                                    @Override
                                                    public void onPrepared(MediaPlayer mp) {
                                                        Log.i(TAG, "Duration = " +
                                                                videoView.getDuration());
                                                    }
                                                });


        videoView.start();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.video_player, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
