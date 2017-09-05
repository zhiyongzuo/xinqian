package cn.contactbook.androidUI;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
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
import cn.contactbook.utils.Eyes;
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
    public static String COMPANYWORKERACTIVITY_COMPANY_NAME = "COMPANY_WORKER_ACTIVITY_COMPANY_NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_worker);
        controller = new Controller(this);
        Intent intent = getIntent();
        /*id = intent.getIntExtra("id", 0);
        contacts = controller.getContact(id);
        company = contacts[0].getCompany();*/
        company = intent.getStringExtra(COMPANYWORKERACTIVITY_COMPANY_NAME);
        contacts = controller.getContact(company);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        rv = (RecyclerView)findViewById(R.id.cw_rv);
        setSupportActionBar(toolbar);

        AppBarLayout appbarLayout = (AppBarLayout)findViewById(R.id.app_bar);
        CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.toolbar_layout);
        toolbarLayout.setTitle(company);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(CompanyWorkerActivity.this, AddActivity.class);
                mIntent.putExtra(COMPANYWORKERACTIVITY_COMPANY_NAME, company);
                startActivity(mIntent);
            }
        });

        adapter = new MultiTypeAdapter();
        adapter.register(Category.class, new CategoryViewBinder());
        adapter.register(Names.class, new NamesViewBinder(CompanyWorkerActivity.this, contacts));
        rv.setLayoutManager(new LinearLayoutManager(CompanyWorkerActivity.this));
        rv.setAdapter(adapter);

        mItems = new Items();
        mItems.add(new Category("单位成员"));

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

        Eyes.setStatusBarColorForCollapsingToolbar(this, appbarLayout, toolbarLayout, toolbar, ContextCompat.getColor(this, R.color.colorPrimary));

    }
}
