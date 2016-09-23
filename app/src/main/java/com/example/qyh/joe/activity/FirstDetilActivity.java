package com.example.qyh.joe.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.qyh.joe.R;
import com.example.qyh.joe.bean.DataBean;
import com.example.qyh.joe.presenter.FirstDetilPresenterImpl;
import com.example.qyh.joe.utils.ImageLoaderUtils;
import com.example.qyh.joe.view.FirstDetilView;

import org.sufficientlysecure.htmltextview.HtmlTextView;

/**
 * 详情页
 * Created by qyh on 2016/3/9.
 */
public class FirstDetilActivity extends AppCompatActivity implements FirstDetilView {

    private ImageView ivImage;
   // private Toolbar toolbar;
    private CollapsingToolbarLayout collapsing_toolbar;
    private ProgressBar progress;
    private HtmlTextView htNewsContent;
    private FirstDetilPresenterImpl mfirstPresenter;
    private DataBean mData;//详情数据
   // private Toolbar toolbar;
   // 定义一个变量，来标识是否退出

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_detil);
        initView();
        intData();


    }

    private void initView() {
        ivImage = (ImageView) findViewById(R.id.ivImage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        collapsing_toolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        progress = (ProgressBar) findViewById(R.id.progress);
        htNewsContent = (HtmlTextView) findViewById(R.id.htNewsContent);

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
    private void intData(){
        mData = (DataBean) getIntent().getSerializableExtra("news");
        collapsing_toolbar.setTitle(mData.getTitle());
        ImageLoaderUtils.display(getApplicationContext(), (ImageView) findViewById(R.id.ivImage), mData.getImgsrc());

        mfirstPresenter = new FirstDetilPresenterImpl(FirstDetilActivity.this, this);
        mfirstPresenter.loadContent(mData.getDocid());
    }

    @Override
    public void showDetilContent(String s) {
       htNewsContent.setHtmlFromString(s, new HtmlTextView.LocalImageGetter());
    }

    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideprogress() {
        progress.setVisibility(View.GONE);
    }

    @Override
    public void showFailure(Exception e, String s) {
    }

}
