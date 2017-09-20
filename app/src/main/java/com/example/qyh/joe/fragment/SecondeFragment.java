package com.example.qyh.joe.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.qyh.joe.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qyh on 2016/8/10.
 */
public class SecondeFragment extends Fragment {
    private ImageView ivImage1;
    private ImageView ivImage2;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static final int NEWS_TYPE_TOP = 0;
    public static final int NEWS_TYPE_NBA = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.seconde_fragment, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        ivImage1 = (ImageView) view.findViewById(R.id.ivImage1);
        ivImage2 = (ImageView) view.findViewById(R.id.ivImage2);
        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout.addTab(tabLayout.newTab().setText("头条"));
        tabLayout.addTab(tabLayout.newTab().setText("NBA"));

        tabLayout.setupWithViewPager(viewPager);
    }

    public void setupViewPager(ViewPager upViewPager) {
        MyPagerAdapter adapter = new MyPagerAdapter(getChildFragmentManager());
        adapter.addFragment(SecondListFragment.newInstance(NEWS_TYPE_TOP), "头条");
        adapter.addFragment(SecondListFragment.newInstance(NEWS_TYPE_NBA), "NBA");
        viewPager.setAdapter(adapter);
    }


    public static class MyPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
