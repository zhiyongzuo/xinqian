package cn.contactbook.androidUI;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import cn.contactbook.R;
import cn.contactbook.controller.Controller;
import cn.contactbook.model.Company;
import cn.contactbook.model.CompanyViewBinder;
import cn.contactbook.model.Contact;
import cn.contactbook.utils.LetterTileProvider;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class SortedByCompanyActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, View.OnClickListener {
    RecyclerView rv;  //为什么加private会有提醒呢。。。
    private MultiTypeAdapter mAdapter;
    private Items mItems;
    SearchView sv;
    private List<String> mList;
    Contact[] contacts;
    Controller controller;
    private FloatingActionButton mFloatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorted_by_company);
        rv = (RecyclerView) findViewById(R.id.as_recycler_view);
        sv = (SearchView) findViewById(R.id.searchView);
        mFloatingActionButton = (FloatingActionButton)findViewById(R.id.edit);
        mFloatingActionButton.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        //lv.setTextFilterEnabled(true);//设置lv可以被过虑
        // 设置该SearchView默认是否自动缩小为图标
        sv.setIconifiedByDefault(true);
        // 为该SearchView组件设置事件监听器
        sv.setOnQueryTextListener(this);
        // 设置该SearchView显示搜索按钮
        // sv.setSubmitButtonEnabled(true);
        // 设置该SearchView内默认显示的提示文本
        sv.setQueryHint("请输入单位名称");

        mList = new ArrayList<>();
        controller = new Controller(SortedByCompanyActivity.this);
        contacts = controller.getAllContact();

        for (int i = 0; i < contacts.length; i++) {
            String company = contacts[i].getCompany();
            if(company != null && !company.equals("") && !mList.contains(company)) {
                mList.add(company);
            }
        }
        mAdapter = new MultiTypeAdapter();
        mItems = new Items();
        mAdapter.register(Company.class, new CompanyViewBinder(SortedByCompanyActivity.this));
        for(String name : mList) {
            mItems.add(new Company(name));
        }
        mAdapter.setItems(mItems);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(mAdapter);
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        List<String> newList = new ArrayList<>();
        for(int i=0; i<mList.size(); i++) {
            int index = mList.get(i).indexOf(newText);
            if(index != -1) {
                newList.add(mList.get(i));
            }
        }
        Items items = new Items();
        for(String name : newList) {
            items.add(new Company(name));
        }
        mAdapter.setItems(items);
        mAdapter.notifyDataSetChanged();
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public void onClick(View v) {
        Intent mIntent = new Intent(this, AddActivity.class);
        startActivity(mIntent);
    }
}
