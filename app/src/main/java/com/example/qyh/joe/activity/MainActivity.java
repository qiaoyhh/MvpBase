package com.example.qyh.joe.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.qyh.joe.R;
import com.example.qyh.joe.base.BaseActivity;
import com.example.qyh.joe.fragment.FirstFragment;
import com.example.qyh.joe.fragment.SecondeFragment;
import com.example.qyh.joe.fragment.ThreeFragment;
import com.example.qyh.joe.theme.SkinManager;
import com.example.qyh.joe.view.MainView;


public class MainActivity extends BaseActivity implements MainView, NavigationView.OnNavigationItemSelectedListener
        , View.OnClickListener {

    private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    private static boolean isExit = false;

    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };
    private ImageView ivAvatar;//个人头像
    private boolean isChangeTheme;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void findViewById() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        // toolbar.setTitle("新闻");
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        ivAvatar = (ImageView) findViewById(R.id.iv_touxiang);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //ActionBarDrawerToggle 提供了一个方便的方式来配合DrawerLayout和ActionBar，以实现推荐的抽屉功能。
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        if (drawer != null) {
            drawer.setDrawerListener(toggle);
        }

        //该方法会自动和actionBar关联, 将开关的图片显示在了action上，如果不设置，也可以有抽屉的效果，不过是默认的图标
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }

        switchFirst();
    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void processBusinessLogic() {
    }

    @Override
    protected Context getActivityContext() {
        return this;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * 菜单键点击的事件处理
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return (item.getItemId() == R.id.action_settings || super.onOptionsItemSelected(item));
    }

    /**
     * 设置当导航项被点击时的回调。OnNavigationItemSelectedListener会提供给我们被选中的MenuItem
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            switchFirst();
        } else if (id == R.id.nav_gallery) {
            switchSecond();

        } else if (id == R.id.nav_slideshow) {
            switchThree();

        } else if (id == R.id.nav_share) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));

        } else if (id == R.id.nav_send) {
            // startActivity(new Intent(getApplicationContext(),PersonalActivity.class));
            switchMain();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(MainActivity.this, "头像", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void switchFirst() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new FirstFragment()).commitAllowingStateLoss();
        toolbar.setTitle("新闻");
    }

    @Override
    public void switchSecond() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new SecondeFragment()).commitAllowingStateLoss();
        toolbar.setTitle("Test");
    }

    @Override
    public void switchThree() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new ThreeFragment()).commitAllowingStateLoss();
        toolbar.setTitle("图片");
    }

    /**
     * 护眼模式，只在【新闻】界面实现了
     * <p>
     * PS：虽然更换主题显的不伦不类，不过就是为了实现这个功能，将就着 看吧
     */
    @Override
    public void switchMain() {
        if (isChangeTheme) {
            isChangeTheme = false;
            SkinManager.getInstance().changeSkin("blue");
        } else {
            isChangeTheme = true;
            SkinManager.getInstance().removeAnySkin();
        }
    }

    //双击退出应用
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            // 利用handler延迟发送更改状态信息
            View view = findViewById(R.id.drawer_layout);
            if (view != null) {
                Snackbar.make(view, "再按一次退出程序", Snackbar.LENGTH_SHORT).show();
            }
            handler.sendEmptyMessageDelayed(0, 2000);
        } else {
            this.finish();
        }
    }
}
