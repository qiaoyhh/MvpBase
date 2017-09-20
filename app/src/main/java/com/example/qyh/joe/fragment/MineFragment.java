package com.example.qyh.joe.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.qyh.joe.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/8/17.
 */
public class MineFragment extends Fragment {
    private ImageView ivImage;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public static final int ONE = 0;
    public static final int TWO = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        ivImage = (ImageView) view.findViewById(R.id.ivImage);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        tabLayout = (TabLayout) view.findViewById(R.id.sliding_tabs);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);

        viewPager.setOffscreenPageLimit(3);
        setupViewPager(viewPager);

        tabLayout.addTab(tabLayout.newTab().setText("头条"));
        tabLayout.addTab(tabLayout.newTab().setText("NBA"));
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewpager) {
        MyPagerAdapter adapter = new MyPagerAdapter(getChildFragmentManager());
        adapter.addFragment(FirstListFragment.newInstance(ONE), "头条");
        adapter.addFragment(FirstListFragment.newInstance(TWO), "NBA");
        viewpager.setAdapter(adapter);
    }


    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragment = new ArrayList<Fragment>();
        private final List<String> mFragmentTitle = new ArrayList<String>();

        public void addFragment(Fragment fragment, String title) {
            mFragment.add(fragment);
            mFragmentTitle.add(title);
        }

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragment.get(position);
        }

        @Override
        public int getCount() {
            return mFragment.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitle.get(position);
        }
    }
}
