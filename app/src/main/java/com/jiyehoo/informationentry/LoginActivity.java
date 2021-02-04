package com.jiyehoo.informationentry;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.jiyehoo.informationentry.activity.SignUpActivity;
import com.jiyehoo.informationentry.util.BaseActivity;
import com.jiyehoo.informationentry.util.OnSwipeTouchListener;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends BaseActivity {

    private ImageView imageView;
    private TextView textView, mTvComp;
    private Button mBtnSignIn, mBtnSignUp;
    private EditText mEtUser;
    private int count = 0;
    private SpinKitView spinKitView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        fullScreen();
        //加载布局
        setContentView(R.layout.activity_login);
        setupWindowAnimations();
        //绑定控件
        bindView();
        //按钮
        btnClick();
        //时间背景
        autoPic();
        //滑动
        swipeChangePic();

    }

    private void toMorning() {
        imageView.setImageResource(R.drawable.good_morning_img);
        textView.setText("Morning");
        count = 0;
    }

    private void toNight() {
        imageView.setImageResource(R.drawable.good_night_img);
        textView.setText("Night");
        count = 1;
    }

    private void bindView() {
        spinKitView = findViewById(R.id.loading_anim);
        mEtUser = findViewById(R.id.et_user);
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);
        mBtnSignIn = findViewById(R.id.btn_sign_in);
        mBtnSignUp = findViewById(R.id.btn_sign_up);
        mTvComp = findViewById(R.id.tv_comp);
    }

    private void btnClick() {
        mBtnSignIn.setOnClickListener(v -> {
            mBtnSignIn.setEnabled(false);
            spinKitView.setVisibility(View.VISIBLE);

            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(() -> {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    });
                }
            };
            Timer timer = new Timer();
            timer.schedule(timerTask, 2500);
        });

        mBtnSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
            finish();
        });

        mTvComp.setOnClickListener(v -> {
            final Uri uri = Uri.parse("http://blog.jiyehoo.com:81/");
            final Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });
    }

    private void fullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void autoPic() {
//        过时
//        Date date = new Date();
//        Log.d("Time", String.valueOf(date.getHours()));
        Calendar cal = Calendar.getInstance();
        int hours = cal.get( Calendar.HOUR_OF_DAY );
        Log.d("Time", String.valueOf(hours));

        if (hours < 12) {
            //上午
            toMorning();
        } else {
            toNight();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void swipeChangePic() {
        imageView.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {
            public void onSwipeTop() {
            }

            public void onSwipeRight() {
                if (count == 0) {
                    toNight();
                } else {
                    toMorning();
                }
            }

            public void onSwipeLeft() {
                if (count == 0) {
                    toNight();
                } else {
                    toMorning();
                }
            }

            public void onSwipeBottom() {
            }

        });
    }

    private void setupWindowAnimations() {
        Fade fade = new Fade();
        fade.setDuration(1000);
        getWindow().setEnterTransition(fade);

        Slide slide = new Slide();
        slide.setDuration(1000);
        getWindow().setReturnTransition(slide);
    }

}