package cn.contactbook.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.contactbook.R;

/**
 * Created by tomsdeath on 2017/8/7.
 */

public class CommonAdapter extends RecyclerView.Adapter<CommonAdapter.ViewHolder> {
    private List<String> mList;
    private Context mContext;

    public CommonAdapter(Context mContext, List<String> mList) {
        Log.d("CommonAdapter", "constract method");
        this.mList = mList;
        this.mContext = mContext;
    }

    @Override
    public CommonAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("CommonAdapter", "onCreateViewHolder");
        View view = LayoutInflater.from(mContext).inflate(R.layout.common_item, parent, false);
        CommonAdapter.ViewHolder vh = new CommonAdapter.ViewHolder(view);
        vh.mTextView = (TextView)view.findViewById(R.id.common_name);
        return vh;
    }


    @Override
    public void onBindViewHolder(CommonAdapter.ViewHolder holder, int position) {
        Log.d("CommonAdapter", mList.get(position));
        holder.mTextView.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        Log.d("CommonAdapter", "mList.size()====" +  mList.size());
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
