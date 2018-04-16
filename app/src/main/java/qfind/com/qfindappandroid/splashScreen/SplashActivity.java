package qfind.com.qfindappandroid.splashScreen;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import qfind.com.qfindappandroid.MainActivity;
import qfind.com.qfindappandroid.R;

public class SplashActivity extends AppCompatActivity {
    ImageView splash_icon;
    AnimationDrawable anim;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splash_icon = (ImageView) findViewById(R.id.splash_icon);
        anim = (AnimationDrawable) splash_icon.getBackground();
        addAnimation();
    }


    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void addAnimation() {
        anim.setOneShot(true);
        anim.start();
        checkIfAnimationDone(anim);
    }

    private void checkIfAnimationDone(AnimationDrawable anim) {
        final AnimationDrawable a = anim;
        int timeBetweenChecks = 250;
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            public void run() {
                if (a.getCurrent() != a.getFrame(a.getNumberOfFrames() - 1)) {
                    checkIfAnimationDone(a);
                } else {
                    openMainActivity();
                }
            }
        }, timeBetweenChecks);
    }


}
