package com.example.qyh.joe.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qyh.joe.R;
import com.example.qyh.joe.adapter.ThreeAdapter;
import com.example.qyh.joe.bean.ThreeDataBean;
import com.example.qyh.joe.presenter.ThreePresenterImpl;
import com.example.qyh.joe.view.ThreeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/8/12.
 */
public class ThreeFragment extends Fragment implements ThreeView, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FloatingActionButton floatingActionButton;
    private LinearLayoutManager linearLayoutManager;
    private ThreePresenterImpl threePresenter;

    private ArrayList<ThreeDataBean> threeDataBeanArrayList;
    private ThreeAdapter threeAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        threePresenter = new ThreePresenterImpl(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.firstlist_fragment, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_widget);
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fa_firstlist);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                R.color.colorPrimaryDark, R.color.colorAccent,
                R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        threeAdapter = new ThreeAdapter(getActivity().getApplicationContext());
        recyclerView.setAdapter(threeAdapter);

        floatingActionButton.setOnClickListener(this);
        recyclerView.addOnScrollListener(OnScrollListener);

        onRefresh();
    }

    public RecyclerView.OnScrollListener OnScrollListener = new RecyclerView.OnScrollListener() {
        private int lastVisibleItem;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == threeAdapter.getItemCount()) {
                //加载更多
                Snackbar.make(getActivity().findViewById(R.id.drawer_layout), "一次只加载20条，查看更多内容请刷新哦", Snackbar.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fa_firstlist:
                recyclerView.scrollToPosition(0);
                break;
        }
    }

    @Override
    public void showImage(List<ThreeDataBean> list) {
        if (threeDataBeanArrayList == null) {
            threeDataBeanArrayList = new ArrayList<>();
        }
        threeDataBeanArrayList.addAll(list);
        threeAdapter.setData(threeDataBeanArrayList);
    }

    @Override
    public void showFailure(Exception e, String s) {
        View view = getActivity() == null ? recyclerView.getRootView() : getActivity().findViewById(R.id.drawer_layout);
        Snackbar.make(view, "数据加载失败!", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        System.out.println("刷新==============");
        if (threeDataBeanArrayList != null) {
            threeDataBeanArrayList.clear();
        }
        threePresenter.loadImageList();
    }
}
