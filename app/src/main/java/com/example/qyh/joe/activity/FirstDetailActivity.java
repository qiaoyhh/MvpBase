package com.example.qyh.joe.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.qyh.joe.R;
import com.example.qyh.joe.base.BaseActivity;
import com.example.qyh.joe.bean.DataBean;
import com.example.qyh.joe.presenter.FirstDetailPresenterImpl;
import com.example.qyh.joe.utils.ImageLoaderUtils;
import com.example.qyh.joe.view.FirstDetailView;

import org.sufficientlysecure.htmltextview.HtmlTextView;

/**
 * 详情页
 * Created by qyh on 2016/3/9.
 */
public class FirstDetailActivity extends BaseActivity implements FirstDetailView {

    private ImageView ivImage;
    // private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ProgressBar progressBar;
    private HtmlTextView htNewsContent;
    private FirstDetailPresenterImpl firstDetailPresenter;
    private DataBean dataBean;//详情数据
    // private Toolbar toolbar;
    // 定义一个变量，来标识是否退出

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_first_detail);
    }

    @Override
    protected void findViewById() {

        ivImage = (ImageView) findViewById(R.id.ivImage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        htNewsContent = (HtmlTextView) findViewById(R.id.htNewsContent);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setSupportActionBar(toolbar);
        // 给左上角图标的左边加上一个返回的图标
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //通过 NavigationDrawer 打开关闭 抽屉---返回
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();//返回上一级
            }
        });
    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void processBusinessLogic() {
        dataBean = (DataBean) getIntent().getSerializableExtra("news");
        collapsingToolbarLayout.setTitle(dataBean.getTitle());
        ImageLoaderUtils.display(getApplicationContext(), (ImageView) findViewById(R.id.ivImage), dataBean.getImgsrc());
        firstDetailPresenter = new FirstDetailPresenterImpl(FirstDetailActivity.this, this);
        firstDetailPresenter.loadContent(dataBean.getDocid());
    }

    @Override
    protected Context getActivityContext() {
        return this;
    }

    @Override
    public void showDetailContent(String s) {
        htNewsContent.setHtmlFromString(s, new HtmlTextView.LocalImageGetter());
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showFailure(Exception e, String s) {
    }

    @Override
    public void onClick(View v) {
    }
}
