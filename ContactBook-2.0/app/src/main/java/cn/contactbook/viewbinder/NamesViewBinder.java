package cn.contactbook.viewbinder;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import cn.contactbook.R;
import cn.contactbook.androidUI.DetailActivity;
import cn.contactbook.model.Contact;
import cn.contactbook.model.Names;
import me.drakeet.multitype.ItemViewBinder;

import static cn.contactbook.androidUI.CompanyWorkerActivity.COMPANYWORKERACTIVITY_COMPANY_NAME;

/**
 * Created by tomsdeath on 2017/8/14.
 */

public class NamesViewBinder extends ItemViewBinder<Names, NamesViewBinder.ViewHolder> {
    private Context mContext;
    private Contact[] contacts;
    private String iName;
    public NamesViewBinder(Context mContext, Contact[] contacts) {
        this.mContext = mContext;
        this.contacts = contacts;
    }

    @NonNull @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_simple, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Names item) {
        iName = item.text;
        holder.name.setText(iName);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @NonNull private final TextView name;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.tv_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                //getPosition(holder); 有个bug 若战友列表里第一个的名字叫 1 ，则点击会打开朋友里的第一个列表。。。。。
                @Override
                public void onClick(View v) {
                    Intent mIntent;
                    for (int i = 0; i < contacts.length; i++) {
                        int index = contacts[i].getName().indexOf((String)name.getText());//搜索框内输入的内容在ListView各条目中的位置 ，内容不匹配就返回-1
                        // 存在匹配的数据
                        if (index != -1) {
                            if (contacts[i].getName().equals(name.getText())) {
                                mIntent = new Intent(mContext, DetailActivity.class);
                                mIntent.putExtra(COMPANYWORKERACTIVITY_COMPANY_NAME, contacts[i].getName());//把id传递到下一个界面
                                mContext.startActivity(mIntent);
                            }
                        }
                    }
                }
            });
        }
    }
}


