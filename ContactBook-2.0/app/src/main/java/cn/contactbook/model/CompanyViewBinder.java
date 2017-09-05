package cn.contactbook.model;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import cn.contactbook.R;
import cn.contactbook.androidUI.CompanyWorkerActivity;
import cn.contactbook.androidUI.SortedByCompany;
import me.drakeet.multitype.ItemViewBinder;

import static cn.contactbook.androidUI.CompanyWorkerActivity.COMPANYWORKERACTIVITY_COMPANY_NAME;

/**
 * Created by zuo81 on 2017/9/5.
 */
public class CompanyViewBinder extends ItemViewBinder<Company, CompanyViewBinder.ViewHolder> {
    private Context mContext;

    public CompanyViewBinder(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.rv_company, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Company company) {
        holder.mTextView.setText(company.getCompany());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;

        ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView)itemView.findViewById(R.id.name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, CompanyWorkerActivity.class);
                    intent.putExtra(COMPANYWORKERACTIVITY_COMPANY_NAME, mTextView.getText());//把id传递到下一个界面
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
