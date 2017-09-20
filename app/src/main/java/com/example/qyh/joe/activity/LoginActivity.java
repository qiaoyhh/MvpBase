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
    private MovingImageView ivMoving;
    private TextInputLayout tilName;
    private TextInputLayout tilPassword;
    private CardView cvNameAndPassword;
    private TextView tvLogin;
    private TextView tvRegister;
    private TextView tvForgetPassword;
    private RelativeLayout rlRoot;
    private ScrollView svRegister;
    private ObjectAnimator objectAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        ivMoving = (MovingImageView) findViewById(R.id.image);
        tilName = (TextInputLayout) findViewById(R.id.name);
        tilPassword = (TextInputLayout) findViewById(R.id.password);
        cvNameAndPassword = (CardView) findViewById(R.id.cv_nameandpass);
        tvLogin = (TextView) findViewById(R.id.login);
        tvRegister = (TextView) findViewById(R.id.register);
        tvForgetPassword = (TextView) findViewById(R.id.forget_password);
        rlRoot = (RelativeLayout) findViewById(R.id.rl_root);
        svRegister = (ScrollView) findViewById(R.id.activity_register);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();
        objectAnimator = ObjectAnimator.ofFloat(ivMoving, "alpha", 1f, 0.5f, 0.5f, 1f);
        objectAnimator.setDuration(5000);
        objectAnimator.start();
    }
}
