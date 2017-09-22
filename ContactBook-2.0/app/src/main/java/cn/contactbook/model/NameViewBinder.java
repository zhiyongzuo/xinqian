package cn.contactbook.model;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.contactbook.R;
import cn.contactbook.androidUI.DetailActivity;
import cn.contactbook.utils.LetterTileProvider;
import de.hdodenhof.circleimageview.CircleImageView;
import me.drakeet.multitype.ItemViewBinder;

import static cn.contactbook.androidUI.CompanyWorkerActivity.COMPANYWORKERACTIVITY_COMPANY_NAME;

/**
 * Created by zuo81 on 2017/9/11.
 */
public class NameViewBinder extends ItemViewBinder<Name, NameViewBinder.ViewHolder> {
    private  Context mContext;
    private LetterTileProvider mLetterTileProvider;

    public NameViewBinder(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.rv_company, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Name name) {
        holder.mTextView.setText(name.getName());
        mLetterTileProvider = new LetterTileProvider(mContext);
        holder.mImageView.setImageBitmap(mLetterTileProvider.getLetterTile(name.getName()));
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;
        private CircleImageView mImageView;

        ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView)itemView.findViewById(R.id.name);
            mImageView = (CircleImageView)itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, DetailActivity.class);
                    intent.putExtra(COMPANYWORKERACTIVITY_COMPANY_NAME, mTextView.getText());//把id传递到下一个界面
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
