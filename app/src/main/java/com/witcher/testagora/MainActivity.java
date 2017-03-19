package com.witcher.testagora;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import java.util.ArrayList;

import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;
import io.agora.rtc.video.VideoCanvas;
import io.agora.videoprp.AgoraYuvEnhancer;

public class MainActivity extends AppCompatActivity {

    public static RtcEngine engine;
    private AgoraYuvEnhancer yuvEnhancer = null;
    private FrameLayout fl;
    int uid = 10;
    Button bt;
    private ArrayList<SurfaceView> surfaceViews = new ArrayList<>(7);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fl = (FrameLayout) findViewById(R.id.fl);
        bt = (Button) findViewById(R.id.bt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });
    }

    void start() {
        engine = RtcEngine.create(this, "77efb518920c41e5af2688abf9c7fbf5", new IRtcEngineEventHandler() {
            @Override
            public void onJoinChannelSuccess(String channel, int uid, int elapsed) {
                super.onJoinChannelSuccess(channel, uid, elapsed);
                L.i("onJoinChannelSuccess,channel:" + channel + ",uid:" + uid + ",elapsed:" + elapsed);
            }
        });
        yuvEnhancer = new AgoraYuvEnhancer(this);
        yuvEnhancer.StartPreProcess();
        surfaceViews.add(RtcEngine.CreateRendererView(this));
        surfaceViews.add(RtcEngine.CreateRendererView(this));
        fl.addView(surfaceViews.get(0));
        engine.enableVideo();
        engine.setVideoProfile(IRtcEngineEventHandler.VideoProfile.VIDEO_PROFILE_360P,false);
        VideoCanvas canvas = new VideoCanvas(surfaceViews.get(0),VideoCanvas.RENDER_MODE_FIT,uid);
        engine.setupLocalVideo(canvas);
        VideoCanvas canvas2 =new VideoCanvas(surfaceViews.get(1),VideoCanvas.RENDER_MODE_FIT,uid+1);
        engine.setupRemoteVideo(canvas2);
        engine.joinChannel(null,"witcher","",uid);
    }
}
