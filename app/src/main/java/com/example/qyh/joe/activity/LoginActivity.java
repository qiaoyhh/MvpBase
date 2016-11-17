package com.example.qyh.joe.activity;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.qyh.joe.R;
import com.example.qyh.joe.customview.MovingImageView;

/**
 * Created by qyh on 2016/9/6.
 */
public class LoginActivity extends AppCompatActivity {
    private MovingImageView image;
    private TextInputLayout name;
    private TextInputLayout password;
    private CardView cv_nameandpass;
    private TextView login;
    private TextView register;
    private TextView forget_password;
    private RelativeLayout rl_root;
    private ScrollView activity_register;
    private ObjectAnimator animator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        image = (MovingImageView) findViewById(R.id.image);
        name = (TextInputLayout) findViewById(R.id.name);
        password = (TextInputLayout) findViewById(R.id.password);
        cv_nameandpass = (CardView) findViewById(R.id.cv_nameandpass);
        login = (TextView) findViewById(R.id.login);
        register = (TextView) findViewById(R.id.register);
        forget_password = (TextView) findViewById(R.id.forget_password);
        rl_root = (RelativeLayout) findViewById(R.id.rl_root);
        activity_register = (ScrollView) findViewById(R.id.activity_register);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();
        animator = ObjectAnimator.ofFloat(image, "alpha", 1f, 0.5f, 0.5f, 1f);
        animator.setDuration(5000);
        animator.start();


    }


}
