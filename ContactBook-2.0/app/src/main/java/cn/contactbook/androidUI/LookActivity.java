package cn.contactbook.androidUI;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

import cn.contactbook.R;
import cn.contactbook.controller.Controller;
import cn.contactbook.model.Category;
import cn.contactbook.model.Contact;
import cn.contactbook.model.Names;
import cn.contactbook.service.MyService;
import cn.contactbook.utils.Eyes;
import cn.contactbook.viewbinder.CategoryViewBinder;
import cn.contactbook.viewbinder.NamesViewBinder;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

import static cn.contactbook.model.DBAdapter.KEY_ARMYFRIENDS;
import static cn.contactbook.model.DBAdapter.KEY_CLASSMATES;
import static cn.contactbook.model.DBAdapter.KEY_FAMILY;
import static cn.contactbook.model.DBAdapter.KEY_FELLOWTOWNSMAN;
import static cn.contactbook.model.DBAdapter.KEY_FRIENDS;


public class LookActivity extends AppCompatActivity {
    private TextView tv_name;
    private TextView tv_phone;
    private TextView tv_phone2;
    private TextView tv_email;
    private TextView tv_sex;
    private TextView tv_company;
    //
    private RecyclerView rv_army_friends;
    private CollapsingToolbarLayout mCollapsingToolbar;

    private String name = "";
    private String phone = "";
    private String phone2 = "";
    private String email = "";
    private String photo = "";
    private String sex = "";
    private String company = "";
    //
    private String army_friends = "";
    private String friends = "";
    private String classmates = "";
    private String family = "";
    private String fellowdownsman = "";
    private List<String> list_army_friends;
    private List<String> list_friends;
    private List<String> list_classmates;
    private List<String> list_family;
    private List<String> list_fellowdownsman;
    private static int id;
    private static Contact[] contacts;
    private Controller controller;
    private ImageView imageView;
    private String imgPath = "";
    private int sleepTime = 0;
    public String iName;
    MultiTypeAdapter adapter;
    Items items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //tv_name = (TextView) findViewById(R.id.name);
        tv_phone = (TextView) findViewById(R.id.phone);
        tv_phone2 = (TextView) findViewById(R.id.phone2);
        tv_email = (TextView) findViewById(R.id.email);
        tv_sex = (TextView) findViewById(R.id.sex);
        tv_company = (TextView) findViewById(R.id.company);
        imageView = (ImageView) findViewById(R.id.edit_imageView);
        //
        rv_army_friends = (RecyclerView) findViewById(R.id.recycler_view_army_friends);
        //
        AppBarLayout mAppBarLayout = (AppBarLayout)findViewById(R.id.app_bar);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        mCollapsingToolbar = (CollapsingToolbarLayout)findViewById(R.id.toolbar_layout);
        Eyes.setStatusBarColorForCollapsingToolbar(this, mAppBarLayout, mCollapsingToolbar, toolbar, ContextCompat.getColor(this, R.color.colorPrimary));

    }

    public void onStart() {
        super.onStart();
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        contacts = new Controller(LookActivity.this).getAllContact();
        controller = new Controller(this);
        Contact[] contact = controller.getContact(id);
        phone = contact[0].getPhone();
        name = contact[0].getName();
        phone2 = contact[0].getPhone2();
        email = contact[0].getEmail();
        photo = contact[0].getPhoto();
        sex = contact[0].getSex();
        company = contact[0].getCompany();
        //
        army_friends = contact[0].getArmyFriends();
        friends = contact[0].getFriends();
        classmates = contact[0].getClassmates();
        family = contact[0].getFamily();
        fellowdownsman = contact[0].getFellowtownsman();
        if (army_friends!= null && !army_friends.equals("")) {
            list_army_friends = Arrays.asList(army_friends.trim().split("，"));
            Log.d("army_friends ", army_friends);
        }
        if (friends != null &&!friends.equals("")) {
            list_friends = Arrays.asList(friends.trim().split("，"));
        }
        if (classmates != null && !classmates.equals("")) {
            list_classmates = Arrays.asList(classmates.trim().split("，"));
        }
        if (family != null && !family.equals("")) {
            list_family = Arrays.asList(family.trim().split("，"));
        }
        if (fellowdownsman != null && !fellowdownsman.equals("")) {
            list_fellowdownsman = Arrays.asList(fellowdownsman.trim().split("，"));
        }

        tv_phone.setText(phone);
        //tv_name.setText(name);
        if(mCollapsingToolbar!=null) {
            mCollapsingToolbar.setTitle(name);
        }
        tv_phone2.setText(phone2);
        tv_email.setText(email);
        tv_sex.setText(sex);
        tv_company.setText(company);
        Bitmap bitmap = getLoacalBitmap(photo); //根据路径从本地取图片
        imageView.setImageBitmap(bitmap);    //设置Bitmap
        //

        adapter = new MultiTypeAdapter();

        /* 注册类型和 View 的对应关系 */
        adapter.register(Category.class, new CategoryViewBinder());
        adapter.register(Names.class, new NamesViewBinder(LookActivity.this, contacts));
        rv_army_friends.setLayoutManager(new LinearLayoutManager(this));
        rv_army_friends.setAdapter(adapter);

        /* 模拟加载数据，也可以稍后再加载，然后使用
         * adapter.notifyDataSetChanged() 刷新列表 */
        items = new Items();
        items.add(new Category("战友"));
        if (list_army_friends !=null && list_army_friends.size() > 0) {
            for(int i=0; i<list_army_friends.size(); i++) {
                Log.d("CommonAdapter   " + i, list_army_friends.get(i));
                items.add(new Names(list_army_friends.get(i)));
            }
        }

        items.add(new Category("朋友"));
        if (list_friends !=null && list_friends.size() > 0) {
            for(int i=0; i<list_friends.size(); i++) {
                items.add(new Names(list_friends.get(i)));
            }
        }

        items.add(new Category("同学"));
        if (list_classmates !=null && list_classmates.size() > 0) {
            for(int i=0; i<list_classmates.size(); i++) {
                items.add(new Names(list_classmates.get(i)));
            }
        }

        items.add(new Category("家人"));
        if (list_family !=null && list_family.size() > 0) {
            for(int i=0; i<list_family.size(); i++) {
                items.add(new Names(list_family.get(i)));
            }
        }

        items.add(new Category("老乡"));
        if (list_fellowdownsman !=null && list_fellowdownsman.size() > 0) {
            for(int i=0; i<list_fellowdownsman.size(); i++) {
                items.add(new Names(list_fellowdownsman.get(i)));
            }
        }
        adapter.setItems(items);
        adapter.notifyDataSetChanged();
/*
        int[] layoutIds = {R.layout.item_simple, R.layout.item_super,};
        SuperAdapter adapter = new SuperAdapter(this, layoutIds);
        rv_army_friends.setLayoutManager(new LinearLayoutManager(this));
        rv_army_friends.setAdapter(adapter);

        DataHolder<SimpleBean> holderSimple = new DataHolder<SimpleBean>() {
            @Override
            public void bind(Context context, SuperViewHolder holder, SimpleBean item, int position) {
                //ImageView ivIcon = holder.getView(R.id.iv_icon);
                TextView tvName = holder.getView(R.id.tv_name);
                //ivIcon.setImageResource(item.getIcon());
                iName = item.getName();
                tvName.setText(iName);

                tvName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(LookActivity.this, iName, Toast.LENGTH_SHORT).show();
                        Intent mIntent;
                        for (int i = 0; i < contacts.length; i++) {
                            int index = contacts[i].getName().indexOf(iName);//搜索框内输入的内容在ListView各条目中的位置 ，内容不匹配就返回-1
                            System.out.println("index   " + index);
                            System.out.println("iName   " + iName);
                            // 存在匹配的数据
                            if (index != -1) {
                                mIntent = new Intent();
                                mIntent.putExtra("id", contacts[i].getId());//把id传递到下一个界面
                                LookActivity.this.startActivity(mIntent);
                            }
                        }
                    }
                });
            }
        };

        DataHolder<SuperBean> holderSuper = new DataHolder<SuperBean>() {
            @Override
            public void bind(Context context, SuperViewHolder holder, SuperBean item, int position) {
                TextView tvGroup = holder.getView(R.id.tv_group);
                //TextView tvArrow = holder.getView(R.id.tv_arrow);
                tvGroup.setText(item.getName());
                //tvArrow.setVisibility(item.isHasArrow() ? View.VISIBLE : View.GONE);
            }
        };

        List<LayoutWrapper> data = new ArrayList<>();

        data.add(new LayoutWrapper(R.layout.item_super, new SuperBean("战友", false), holderSuper));
        for(int i=0; i<list_army_friends.size(); i++) {
            Log.d("CommonAdapter   " + i, list_army_friends.get(i));
            data.add(new LayoutWrapper(R.layout.item_simple, new SimpleBean(list_army_friends.get(i)), holderSimple));
        }

        data.add(new LayoutWrapper(R.layout.item_super, new SuperBean("朋友", false), holderSuper));
        for(int i=0; i<list_friends.size(); i++) {
            data.add(new LayoutWrapper(R.layout.item_simple, new SimpleBean(list_friends.get(i)), holderSimple));
        }

        data.add(new LayoutWrapper(R.layout.item_super, new SuperBean("同学", false), holderSuper));
        for(int i=0; i<list_classmates.size(); i++) {
            data.add(new LayoutWrapper(R.layout.item_simple, new SimpleBean(list_classmates.get(i)), holderSimple));
        }

        data.add(new LayoutWrapper(R.layout.item_super, new SuperBean("家人", false), holderSuper));
        for(int i=0; i<list_family.size(); i++) {
            data.add(new LayoutWrapper(R.layout.item_simple, new SimpleBean(list_family.get(i)), holderSimple));
        }

        data.add(new LayoutWrapper(R.layout.item_super, new SuperBean("老乡", false), holderSuper));
        for(int i=0; i<list_fellowdownsman.size(); i++) {
            data.add(new LayoutWrapper(R.layout.item_simple, new SimpleBean(list_fellowdownsman.get(i)), holderSimple));
        }

        adapter.setData(data);  */
    }

    public void delete(View v) {
        buildDialog();
    }

    //删除时弹出的提示对话框
    private void buildDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LookActivity.this);
        builder.setTitle("将要删除联系人");
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                controller.delete(id);
                Toast.makeText(LookActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        builder.setPositiveButton("取消", null);
        builder.show();
    }

    public void back(View v) {
       finish();
    }

    /**
     * 跳转到编辑联系人
     * 把姓名，id等内容传递到编辑联系人界面
     *
     * @param v
     */
    public void edit(View v) {
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("name", name);
        intent.putExtra("phone", phone);
        intent.putExtra("phone2", phone2);
        intent.putExtra("email", email);
        intent.putExtra("photo", photo);
        intent.putExtra("sex", sex);
        intent.putExtra("company", company);
        //
        intent.putExtra(KEY_ARMYFRIENDS, army_friends);
        intent.putExtra(KEY_FRIENDS, friends);
        intent.putExtra(KEY_CLASSMATES, classmates);
        intent.putExtra(KEY_FAMILY, family);
        intent.putExtra(KEY_FELLOWTOWNSMAN, fellowdownsman);

        startActivity(intent);
    }


    /**
     * 加载本地图片
     *
     * @param url
     * @return
     */
    public static Bitmap getLoacalBitmap(String url) {
        try {
            if (url != null && !url.equals("")) {
                FileInputStream fis = new FileInputStream(url);
                return BitmapFactory.decodeStream(fis);
            } else return null;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


    //拨打电话
    public void call(View v) {
        //如果版本>=Android6.0并且检查自身权限没有被赋予时，请求权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
        } else {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.CALL");//调用系统拨打电话
            intent.setData(Uri.parse("tel:" + phone));
            startActivity(intent);
            //启动服务，在service中监听电话状态并进行重播提醒
            Intent callIntent = new Intent(LookActivity.this, MyService.class);
            callIntent.putExtra("phone", phone);
            startService(callIntent);
        }
    }

    //拨打电话
    public void call2(View v) {
        if (phone2 != null && phone2.equals("")) {
            Toast.makeText(this, "没有号码", Toast.LENGTH_SHORT).show();
        } else {
            //动态获取打电话权限
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
            } else {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.CALL");//调用系统拨打电话
                intent.setData(Uri.parse("tel:" + phone2));
                startActivity(intent);
                //启动服务，在service中监听电话状态并进行重播提醒
                Intent callIntent = new Intent(LookActivity.this, MyService.class);
                callIntent.putExtra("phone", phone2);
                startService(callIntent);
            }
        }
    }

    public void sendMessage(View v) {
        Uri smsToUri = Uri.parse("smsto:" + phone);
        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);//调用系统发短信
        //intent.putExtra("发送内容是", " ");
        startActivity(intent);

    }

    //发送短信
    public void sendMessage2(View v) {
        if (phone2 !=null && phone2.equals("")) {
            System.out.println("phone2:========" + phone2);
            Toast.makeText(this, "没有号码", Toast.LENGTH_SHORT).show();
        } else {
            Uri smsToUri = Uri.parse("smsto:" + phone2);
            Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);//调用系统发短信
            //intent.putExtra("发送内容是", " ");
            startActivity(intent);
        }
    }

    //处理权限申请回调
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == 1) {
            // 用户取消授权这个数组为空，如果你同时申请两个权限，那么grantResults的length就为2，分别记录你两个权限的申请结果
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //同意授权时。。。。。
            } else {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!this.shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
                        //用户已经完全拒绝，或手动关闭了权限开启此对话框缓解一下尴尬...
                        android.app.AlertDialog dialog = new android.app.AlertDialog.Builder(this)
                                .setMessage("不开启该权限将无法正常工作，请在设置中手动开启！")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        getAppDetailSettingIntent(LookActivity.this);
                                    }
                                })
                                .setNegativeButton("取消", null).create();
                        dialog.show();

                    } else {
                        //用户一直拒绝并一直不勾选“不再提醒”
                        //不执行该权限对应功能模块，也不用提示，因为下次需要权限还会弹出对话框
                    }
                }
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //以下代码可以跳转到应用详情，可以通过应用详情跳转到权限界面(6.0系统测试可用)
    private void getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
        }
        startActivity(localIntent);
    }

}
