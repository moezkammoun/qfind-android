package qfind.com.qfindappandroid.splashScreen;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;

import butterknife.BindView;
import qfind.com.qfindappandroid.MainActivity;
import qfind.com.qfindappandroid.R;

public class SplashActivity extends AppCompatActivity {
    @BindView(R.id.circular_progress_bar)
    ProgressBar circularProgressBar;
    ImageView splash_icon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splash_icon = (ImageView) findViewById(R.id.splash_icon);
        if(getResources().getBoolean(R.bool.isTablet)){
            splash_icon.setImageResource(R.drawable.splash_icon_for_tab);
        }else {
            splash_icon.setImageResource(R.drawable.splash_icon_for_phone);
        }
        circularProgressBar = (ProgressBar) findViewById(R.id.circular_progress_bar);
       final ObjectAnimator anim = ObjectAnimator.ofInt(circularProgressBar, "progress", 0, 5000);
        anim.setDuration(3000);
        anim.setInterpolator(new DecelerateInterpolator());
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 200ms
                anim.start();
            }
        }, 200);


        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                circularProgressBar.setProgress(0);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                openMainActivity();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });


    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
