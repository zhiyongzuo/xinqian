package cn.contactbook.androidUI;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.prefs.Preferences;

import cn.contactbook.R;
import cn.contactbook.controller.Controller;
import cn.contactbook.model.Contact;

import static cn.contactbook.model.DBAdapter.KEY_ARMYFRIENDS;
import static cn.contactbook.model.DBAdapter.KEY_CLASSMATES;
import static cn.contactbook.model.DBAdapter.KEY_FAMILY;
import static cn.contactbook.model.DBAdapter.KEY_FELLOWTOWNSMAN;
import static cn.contactbook.model.DBAdapter.KEY_FRIENDS;

public class SettingActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private ListView lv;
    private SearchView sv;
    private static final int item1 = Menu.FIRST;
    Contact[] contacts;
    Contact contact;
    String name, phone;
    Controller controller;
    Preferences preferences;
    public int x;//run app 后静态的值还是没有改变
    String phone2, email, photo,sex,company, army_friends, friends, classmates, family, fellowtownsman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_setting);
        setContentView(R.layout.activity_main);
    }



    @Override
    public void onStart() {
        super.onStart();
        lv = (ListView) findViewById(R.id.listView);

        sv = (SearchView) findViewById(R.id.searchView);
        //lv.setTextFilterEnabled(true);//设置lv可以被过虑
        // 设置该SearchView默认是否自动缩小为图标
        sv.setIconifiedByDefault(true);
        // 为该SearchView组件设置事件监听器
        sv.setOnQueryTextListener(this);
        // 设置该SearchView显示搜索按钮
        // sv.setSubmitButtonEnabled(true);
        // 设置该SearchView内默认显示的提示文本
        sv.setQueryHint("请输入单位名称");

        ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();

        controller = new Controller(SettingActivity.this);
        contacts = controller.getAllContact();

        if (contacts != null)
            for (int i = 0; i < contacts.length; i++) {
                HashMap<String, Object> item = new HashMap<String, Object>();
                item.put("id", contacts[i].getId());
                item.put("name", contacts[i].getName());
                item.put("phone", contacts[i].getPhone());
                item.put("phone2", contacts[i].getPhone2());
                item.put("email", contacts[i].getEmail());
                item.put("photo", contacts[i].getPhoto());
                item.put("sex", contacts[i].getSex());
                item.put("company", contacts[i].getCompany());
                //
                item.put(KEY_ARMYFRIENDS, contacts[i].getArmyFriends());
                item.put(KEY_FRIENDS, contacts[i].getFriends());
                item.put(KEY_CLASSMATES, contacts[i].getClassmates());
                item.put(KEY_FAMILY, contacts[i].getFamily());
                item.put(KEY_FELLOWTOWNSMAN, contacts[i].getFellowtownsman());
                if (contacts[i].getCompany() !=null && !contacts[i].getCompany().equals("")) {
                    data.add(item);
                }
            }
        //创建SimpleAdapter适配器将数据绑定到item显示控件上
        SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.listview,
                new String[]{"photo", "company"}, new int[]{R.id.edit_imageView, R.id.name});
        //把头像填充到适配器中
        adapter.setViewBinder(new SimpleAdapter.ViewBinder() {

            @Override
            public boolean setViewValue(View view, Object data, String textRepresentation) {
                if (view instanceof ImageView && data instanceof Bitmap) {
                    ImageView iv = (ImageView) view;
                    iv.setImageBitmap((Bitmap) data);
                    return true;
                }
                return false;
            }
        });

        /**
         * 给ListView绑定适配器，并设置点击事件
         */
        if (lv != null) {
            lv.setAdapter(adapter);
            //listView点击事件
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                //设置点击事件要判断这个position是对应原来的list，还是搜索后的list，parent.getAdapter().getItem(position)。
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    System.out.println("--------点击的是-----" + parent.getAdapter().getItem(position).toString());
                    List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();

                    data.add((HashMap<String, Object>) parent.getAdapter().getItem(position));
                    Set<String> keySet = data.get(0).keySet();//用Set的keySet方法取出key的集合
                    Iterator<String> it = keySet.iterator();
                    while (it.hasNext()) {
                        String key = it.next();
                        if (key.equals("id")) {
                            int value = (int) data.get(0).get(key);//拿到key对应的value
                            Intent intent = new Intent(SettingActivity.this, CompanyWorkerActivity.class);
                            intent.putExtra("id", value);//把id传递到下一个界面
                            startActivity(intent);
                        }
                    }
                }
            });
        }

    }

    @Override
    public boolean onQueryTextChange(String newText) {
        ArrayList<HashMap<String, Object>> obj = searchItem(newText);
        updateLayout(obj);
        return false;
    }
    /**
     * 搜索主要逻辑。数据库中contact的姓名和输入框中输入的文字一致就存放到新ArrayList
     *
     * @param name 输入框中输入的文字
     * @return dataList  搜索结果存放的ArrayList
     */
    public ArrayList<HashMap<String, Object>> searchItem(String name) {
        ArrayList dataList = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < contacts.length; i++) {
            if (contacts[i].getCompany() != null && !contacts[i].getCompany().equals("")) {
                int index = contacts[i].getCompany().indexOf(name);//搜索框内输入的内容在ListView各条目中的位置 ，内容不匹配就返回-1
                System.out.println("index-" + index);
                // 存在匹配的数据
                if (index != -1) {
                    HashMap<String, Object> item = new HashMap<String, Object>();
                    item.put("id", contacts[i].getId());
                    item.put("name", contacts[i].getName());
                    item.put("phone", contacts[i].getPhone());
                    item.put("phone2", contacts[i].getPhone2());
                    item.put("email", contacts[i].getEmail());
                    item.put("photo", contacts[i].getPhoto());
                    item.put("sex", contacts[i].getSex());
                    item.put("company", contacts[i].getCompany());

                    dataList.add(item);

                }
            }
        }
        return dataList;
    }
    public void updateLayout(ArrayList<HashMap<String, Object>> obj) {
        lv.setAdapter(new SimpleAdapter(this, obj, R.layout.listview,
                new String[]{"photo", "company"}, new int[]{R.id.edit_imageView, R.id.name}));
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }
}
