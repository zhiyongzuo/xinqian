package cn.contactbook.androidUI;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import cn.contactbook.R;
import cn.contactbook.controller.Controller;
import cn.contactbook.model.Category;
import cn.contactbook.model.Contact;
import cn.contactbook.model.Names;
import cn.contactbook.viewbinder.CategoryViewBinder;
import cn.contactbook.viewbinder.NamesViewBinder;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class CompanyWorkerActivity extends AppCompatActivity {
    private RecyclerView rv;
    private MultiTypeAdapter adapter;
    private Items mItems;
    private int id;
    private String company;
    private Contact[] contacts;
    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contacts = new Controller(CompanyWorkerActivity.this).getAllContact();
        setContentView(R.layout.activity_company_worker);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        rv = (RecyclerView)findViewById(R.id.cw_rv);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        adapter = new MultiTypeAdapter();
        adapter.register(Category.class, new CategoryViewBinder());
        adapter.register(Names.class, new NamesViewBinder(CompanyWorkerActivity.this, contacts));
        rv.setLayoutManager(new LinearLayoutManager(CompanyWorkerActivity.this));
        rv.setAdapter(adapter);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        controller = new Controller(this);
        Contact[] contact = controller.getContact(id);
        company = contact[0].getCompany();
        mItems = new Items();
        mItems.add(new Category("company"));

        if (contacts != null) {
            for (int i = 0; i < contacts.length; i++) {
                if (contacts[i].getCompany() !=null && !contacts[i].getCompany().equals("")) {
                    int index = contacts[i].getCompany().indexOf(company);
                    if (index != -1) {
                        Log.d("CWA", contacts[i].getName());
                        mItems.add(new Names(contacts[i].getName()));
                    }
                }
            }
        }
        adapter.setItems(mItems);
        adapter.notifyDataSetChanged();
    }
}
