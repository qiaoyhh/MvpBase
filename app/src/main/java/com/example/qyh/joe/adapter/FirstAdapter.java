package com.example.qyh.joe.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qyh.joe.R;
import com.example.qyh.joe.bean.DataBean;
import com.example.qyh.joe.utils.ImageLoaderUtils;

import java.util.List;

/**
 * Created by admin on 2016/8/8.
 */
public class FirstAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    private List<DataBean> dataBeanList;
    private boolean showFooter = true;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public FirstAdapter(Context context) {
        this.context = context;
    }

    /**
     * 设置数据
     **/
    public void setData(List<DataBean> data) {
        this.dataBeanList = data;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (!showFooter) {
            return TYPE_ITEM;
        }
        if (position + 1 == getItemCount()) {//到了底部
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    public DataBean getItem(int position) {
        return dataBeanList.get(position);
    }

    //根据传递过来的值 控制是否显示加载更多布局
    public void isShowFooter(boolean b) {
        this.showFooter = b;
    }

    public boolean isShowFooter() {
        return this.showFooter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View news = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_first_news, parent, false);
            return new ItemViewHolder(news);
        } else {
            View footer = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_first_footer, null);
            footer.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            return new FooterViewHolder(footer);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            DataBean dataBean = dataBeanList.get(position);
            ((ItemViewHolder) holder).mTitle.setText(dataBean.getTitle());
            ((ItemViewHolder) holder).mDesc.setText(dataBean.getDigest());
            ImageLoaderUtils.display(context, ((ItemViewHolder) holder).mNewsImg, dataBean.getImgsrc());
        }
    }

    @Override
    public int getItemCount() {
        int begin = showFooter ? 1 : 0;
        if (dataBeanList == null) {
            return begin;
        } else {
            return dataBeanList.size() + begin;
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTitle;
        public TextView mDesc;
        public ImageView mNewsImg;

        public ItemViewHolder(View v) {
            super(v);
            mTitle = (TextView) v.findViewById(R.id.tvTitle);
            mDesc = (TextView) v.findViewById(R.id.tvDesc);
            mNewsImg = (ImageView) v.findViewById(R.id.ivNews);
            v.setOnClickListener(this);//给item注册点击监听 必须写
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, this.getPosition());
            }
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    //给RecyclerView注册点击回调接口
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
