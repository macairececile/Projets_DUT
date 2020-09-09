package com.example.cecile.testfloat;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * When run the app, show a progres bar and the name when app is loading
 */
public class SplashActivity extends AppCompatActivity {
    private static final long DELAY = 3000;
    private boolean scheduled = false;
    private Timer splashTimer;
    Typeface font;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_activity);

        //Font
        font = Typeface.createFromAsset(getAssets(), "fonts/font1.ttf");
        textView = (TextView) findViewById(R.id.textSplash);
        final Animation anim = AnimationUtils.loadAnimation(this, R.anim.bounce);

        textView.setTypeface(font);
        textView.startAnimation(anim);

        splashTimer = new Timer();
        splashTimer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                SplashActivity.this.finish();
                startActivity(new Intent(SplashActivity.this, HomeWindow.class));
            }
        }, DELAY);
        scheduled = true;
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (scheduled)
            splashTimer.cancel();
        splashTimer.purge();
    }
}
