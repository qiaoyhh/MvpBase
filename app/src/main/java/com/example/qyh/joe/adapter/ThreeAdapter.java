package com.example.qyh.joe.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qyh.joe.R;
import com.example.qyh.joe.bean.ThreeDataBean;
import com.example.qyh.joe.utils.ImageLoaderUtils;

import java.util.List;

/**
 * Created by admin on 2016/8/12.
 */
public class ThreeAdapter extends RecyclerView.Adapter<ThreeAdapter.ItemViewHolder>{
    private List<ThreeDataBean> mData;
    private int mMaxWidth;
    private int mMaxHeight;
    private Context mContext;
    public ThreeAdapter(Context context) {
        this.mContext=context;
    }

    public void setData(List<ThreeDataBean> list){
        this.mData=list;
        this.notifyDataSetChanged();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_treefragment, parent,false);
        ItemViewHolder vh = new ItemViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        ThreeDataBean imageBean = mData.get(position);
        if(imageBean == null) {
            return;
        }
        holder.mTitle.setText(imageBean.getTitle());
        System.out.println("图片url============"+imageBean.getThumburl());
        ImageLoaderUtils.display(mContext, holder.mImage, imageBean.getThumburl());
    }

    @Override
    public int getItemCount() {
        return mData==null?0:mData.size();
    }

    public class   ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView mTitle;
        public ImageView mImage;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            mImage = (ImageView) itemView.findViewById(R.id.ivImage);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
