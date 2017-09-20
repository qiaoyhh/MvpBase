package com.example.qyh.joe.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qyh.joe.R;
import com.example.qyh.joe.activity.FirstDetailActivity;
import com.example.qyh.joe.adapter.FirstAdapter;
import com.example.qyh.joe.bean.DataBean;
import com.example.qyh.joe.commons.Urls;
import com.example.qyh.joe.presenter.FirstFragmentImpl;
import com.example.qyh.joe.presenter.FirstPresenter;
import com.example.qyh.joe.view.FirstView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qyh on 2016/8/5.
 */
public class FirstListFragment extends Fragment implements FirstView, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int type = FirstFragment.ONE;
    private FirstPresenter firstPresenter;
    private LinearLayoutManager linearLayoutManager;
    private FirstAdapter firstAdapter;
    private int pageIndex = 0;

    private ArrayList<DataBean> dataBeanArrayList;
    private FloatingActionButton fabFirstList;

    public static FirstListFragment newInstance(int type) {
        Bundle bundle = new Bundle();
        FirstListFragment fragment = new FirstListFragment();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firstPresenter = new FirstFragmentImpl(this);
        type = getArguments().getInt("type");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.firstlist_fragment, null);
        initView(view);
        onRefresh();
        return view;
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
    public void addData(List<DataBean> dataBeanList) {
        if (null == dataBeanArrayList) {
            dataBeanArrayList = new ArrayList<>();
        }
        dataBeanArrayList.addAll(dataBeanList);
        if (pageIndex == 0) {
            firstAdapter.setData(dataBeanArrayList);
        } else {
            //没有加载更多的数据时候，，隐藏加载更多的布局
            if (dataBeanList.size() == 0) {
                firstAdapter.isShowFooter(false);
            }
            firstAdapter.notifyDataSetChanged();
        }
        pageIndex += Urls.PAGE_SIZE;
    }

    //当没有网络或者加载失败的时候，隐藏进度，自动弹出提示框
    @Override
    public void showLoadFail() {
        if (pageIndex == 0) {
            firstAdapter.isShowFooter(false);
            firstAdapter.notifyDataSetChanged();
        }
        View view = getActivity() == null ? recyclerView.getRootView() : getActivity().findViewById(R.id.drawer_layout);
        Snackbar.make(view, "数据加载失败", Snackbar.LENGTH_SHORT).show();
    }

    private void initView(View view) {
        fabFirstList = (FloatingActionButton) view.findViewById(R.id.fa_firstlist);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_widget);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                R.color.colorPrimaryDark, R.color.colorAccent,
                R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        firstAdapter = new FirstAdapter(getActivity());
        recyclerView.setAdapter(firstAdapter);
        firstAdapter.setOnItemClickListener(mOnItemClickListener);
        recyclerView.addOnScrollListener(mOnScrollListener);

        fabFirstList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoTop(0);
            }
        });
    }

    //点击滚动到列表的最顶端
    private void gotoTop(int position) {
        recyclerView.scrollToPosition(position);
    }

    //列表下拉刷新监听事件
    public RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        private int mLastVisibleItemPosition;//最后一个角标位置

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            mLastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            //正在滚动
            if (newState == RecyclerView.SCROLL_STATE_IDLE && mLastVisibleItemPosition + 1 == firstAdapter.getItemCount()
                    && firstAdapter.isShowFooter()) {

                firstPresenter.loadData(type, pageIndex + Urls.PAGE_SIZE);
            }
        }
    };

    //刷新
    @Override
    public void onRefresh() {
        // System.out.println("onRefresh=================");
        pageIndex = 0;
        if (null != dataBeanArrayList) {
            dataBeanArrayList.clear();
        }
        firstPresenter.loadData(type, pageIndex);
    }

    //FirstAdapter点击，跳转到新闻详情界面
    private FirstAdapter.OnItemClickListener mOnItemClickListener = new FirstAdapter.OnItemClickListener() {


        @Override
        public void onItemClick(View view, int position) {
            DataBean data = firstAdapter.getItem(position);
            System.out.println("点击的数据======" + data.getTitle());
            Intent intent = new Intent(getActivity(), FirstDetailActivity.class);
            intent.putExtra("news", data);

            View intoView = view.findViewById(R.id.ivNews);
            ActivityOptionsCompat options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                            intoView, getString(R.string.transition_news_img));
            ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
        }
    };
}
