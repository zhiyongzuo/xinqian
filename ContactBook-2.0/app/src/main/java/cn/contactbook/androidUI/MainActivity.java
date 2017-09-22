package cn.contactbook.androidUI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.chiclam.android.updater.Updater;
import com.chiclam.android.updater.UpdaterConfig;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import cn.contactbook.R;
import cn.contactbook.controller.Controller;
import cn.contactbook.controller.GetNumber;
import cn.contactbook.model.Album;
import cn.contactbook.model.Company;
import cn.contactbook.model.Contact;
import cn.contactbook.model.Name;
import cn.contactbook.model.NameViewBinder;
import cn.contactbook.utils.LetterTileProvider;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

import static cn.contactbook.R.id.searchView;
import static cn.contactbook.controller.GetNumber.lists;
import static cn.contactbook.model.DBAdapter.KEY_ARMYFRIENDS;
import static cn.contactbook.model.DBAdapter.KEY_CLASSMATES;
import static cn.contactbook.model.DBAdapter.KEY_FAMILY;
import static cn.contactbook.model.DBAdapter.KEY_FELLOWTOWNSMAN;
import static cn.contactbook.model.DBAdapter.KEY_FRIENDS;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private RecyclerView rv;
    private SearchView sv;
    private static final int item1 = Menu.FIRST;
    private static final int item2 = Menu.NONE;
    Contact[] contacts;
    Contact contact;
    String name, phone;
    Controller controller;
    public int x;
    String phone2, email, photo,sex,company, army_friends, friends, classmates, family, fellowtownsman;
    EditText mEditText;
    private LetterTileProvider mLetterTileProvider;
    private MultiTypeAdapter multiTypeAdapter;
    private Items items;
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Logger.addLogAdapter(new AndroidLogAdapter());
        Logger.d("MainActivity onCreate()");
        controller = new Controller(MainActivity.this);
        mLetterTileProvider = new LetterTileProvider(this);
        rv = (RecyclerView) findViewById(R.id.rv);
        sv = (SearchView) findViewById(R.id.searchView);
        //lv.setTextFilterEnabled(true);//设置lv可以被过虑
        // 设置该SearchView默认是否自动缩小为图标
        sv.setIconifiedByDefault(true);
        // 为该SearchView组件设置事件监听器
        sv.setOnQueryTextListener(this);
        // 设置该SearchView显示搜索按钮
        // sv.setSubmitButtonEnabled(true);
        // 设置该SearchView内默认显示的提示文本
        sv.setQueryHint("请输入名字");

        //把通讯录数据库里的记录传给数据库
        x = getSharedPreferences("a", MODE_WORLD_READABLE).getInt("x", 0);
        if (x == 0) {
            GetNumber.getNumber(this);
            for(int i = 0; i< lists.size(); i++) {
                name = lists.get(i).getName();
                phone = lists.get(i).getNumber();
                contact = new Contact(name, phone, phone2, email, photo,sex,company, army_friends, friends, classmates, family, fellowtownsman);
                controller.add(contact);
            }
            x = 2;
            getSharedPreferences("a", MODE_WORLD_READABLE).edit().putInt("x", x).apply();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        rv.setLayoutManager(new LinearLayoutManager(this));
        multiTypeAdapter = new MultiTypeAdapter();
        multiTypeAdapter.register(Name.class, new NameViewBinder(this));
        items = new Items();
        Controller controller = new Controller(this);
        contacts = controller.getAllContact();
        for(int i=0; i<contacts.length; i++) {
            items.add(new Name(contacts[i].getName()));
        }
        multiTypeAdapter.setItems(items);
        rv.setAdapter(multiTypeAdapter);
    }

    public void add(View v) {
        Logger.d("add");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Toast.makeText(this, "该软件需要悬浮窗权限，请授予！", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 10);
            } else {
                Intent intent = new Intent(this, AddActivity.class);
                startActivity(intent);
            }
        } else {
            Intent intent = new Intent(this, AddActivity.class);
            startActivity(intent);
        }
    }

    /**
     * 用户返回
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 10) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this)) {
                    Toast.makeText(this, "权限授予失败，无法开启悬浮窗", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "权限授予成功！", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(this, AddActivity.class);
                    startActivity(intent);
                }
            }

        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        // 实际应用中应该在该方法内执行实际查询
        // 此处仅使用Toast显示用户输入的查询内容
        // Toast.makeText(this, "您的选择是:" + query, Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        List<String> newList = new ArrayList<>();
        for (int i = 0; i < contacts.length; i++) {
            int index = contacts[i].getName().indexOf(newText);
            if (index != -1) {
                newList.add(contacts[i].getName());
            }
        }
        Items items = new Items();
        for (String name : newList) {
            items.add(new Name(name));
        }
        multiTypeAdapter.setItems(items);
        multiTypeAdapter.notifyDataSetChanged();
        return false;
    }
    //监听返回键退出事件
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (i!=2) {
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                i = 2;
            } else {
                i=0;
                finish();
            }
        }
        return false;
    }

    //菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, item1, 1, "切换到按单位排列");
        menu.add(0, item2, 2, "更新软件");
        return true;
    }

    //菜单列表
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case item1:
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, SortedByCompanyActivity.class);
                MainActivity.this.startActivity(intent);
                break;
            case item2:
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
                final View view = getLayoutInflater().inflate(R.layout.alert_dialog, null);
                mBuilder.setTitle("请输入网址").setView(view)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mEditText = (EditText) view.findViewById(R.id.edit_text);
                                String websit = mEditText.getText().toString();
                                if (!websit.equals("")) {
                                    UpdaterConfig config = new UpdaterConfig.Builder(MainActivity.this)
                                            .setTitle(getResources().getString(R.string.app_name))
                                            .setDescription(getString(R.string.system_download_description))
                                            .setFileUrl(mEditText.getText().toString())
                                            .setCanMediaScanner(true)
                                            .build();
                                    Updater.get().showLog(true).download(config);
                                } else {
                                    Toast.makeText(MainActivity.this, "请输入网址", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).create().show();
                break;
            case searchView:
                break;
        }
        return true;
    }
}
