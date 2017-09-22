package cn.contactbook.androidUI;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import org.litepal.LitePal;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

import cn.contactbook.R;
import cn.contactbook.controller.Controller;
import cn.contactbook.fragment.ArmyFriendFragment;
import cn.contactbook.fragment.BasicFragment;
import cn.contactbook.fragment.ClassMatesFragment;
import cn.contactbook.fragment.FamilyFragment;
import cn.contactbook.fragment.FellowTownsManFragment;
import cn.contactbook.fragment.FriendFragment;
import cn.contactbook.model.Contact;
import cn.contactbook.utils.Eyes;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

import static cn.contactbook.androidUI.CompanyWorkerActivity.COMPANYWORKERACTIVITY_COMPANY_NAME;
import static com.ashokvarma.bottomnavigation.BottomNavigationBar.MODE_FIXED;


public class DetailActivity extends AppCompatActivity {
    private BottomNavigationBar mBottomNavigationBar;
    private RecyclerView rv_army_friends;
    private CollapsingToolbarLayout mCollapsingToolbar;
    private static Contact[] contacts;
    private Controller controller;
    private ImageView imageView;
    public int id;
    private String imgPath = "";
    private Bundle bundle;
    private String name;
    private String photo = "";
    private FragmentTransaction ft;
    public static String DETAIL_ACTIVITY_NAME = "BASIC";
    public static String ARMYFRIEDS = "armyfriends";
    public static String FRIEDS = "FRIEDS";
    public static String CLASSMATES = "CLASSMATES";
    public static String FAMILY = "FAMILY";
    public static String FELLOWTOWNSMAN = "FELLOWTOWNSMAN";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mBottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        imageView = (ImageView) findViewById(R.id.edit_imageView);
        rv_army_friends = (RecyclerView) findViewById(R.id.recycler_view_army_friends);
        AppBarLayout mAppBarLayout = (AppBarLayout)findViewById(R.id.app_bar);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        mCollapsingToolbar = (CollapsingToolbarLayout)findViewById(R.id.toolbar_layout);

        Intent intent = getIntent();
        name = intent.getStringExtra(COMPANYWORKERACTIVITY_COMPANY_NAME);
        Logger.addLogAdapter(new AndroidLogAdapter());
        controller = new Controller(this);
        Contact[] contact = controller.getContactByName(name);
        Logger.d(contact);
        if(contact!=null && contact.length>0) {
            photo = contact[0].getPhoto();
            id = contact[0].getId();
        }
        Eyes.setStatusBarColorForCollapsingToolbar(this, mAppBarLayout, mCollapsingToolbar, toolbar, ContextCompat.getColor(this, R.color.colorPrimary));

        ft = getSupportFragmentManager().beginTransaction();
        final BasicFragment bFragment = new BasicFragment();
        bundle = new Bundle();
        bundle.putString(DETAIL_ACTIVITY_NAME, name);
        bFragment.setArguments(bundle);
        ft.replace(R.id.frame_layout, bFragment).commit();
        mBottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.basic, "基本信息"))
                .addItem(new BottomNavigationItem(R.drawable.army_friend, "战友"))
                .addItem(new BottomNavigationItem(R.drawable.friend, "朋友"))
                .addItem(new BottomNavigationItem(R.drawable.classmates, "同学"))
                .addItem(new BottomNavigationItem(R.drawable.family, "家人"))
                .addItem(new BottomNavigationItem(R.drawable.fellowtownsman, "老乡"))
                .setMode(MODE_FIXED)
                .initialise();
        mBottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                switch(position) {
                    case 0:
                        ft.replace(R.id.frame_layout, bFragment).commit();
                        break;
                    case 1:
                        ArmyFriendFragment afFragment = new ArmyFriendFragment();
                        afFragment.setArguments(bundle);
                        ft.replace(R.id.frame_layout, afFragment).commit();
                        break;
                    case 2:
                        FriendFragment fFragment = new FriendFragment();
                        fFragment.setArguments(bundle);
                        ft.replace(R.id.frame_layout, fFragment).commit();
                        break;
                    case 3:
                        ClassMatesFragment cmFragment = new ClassMatesFragment();
                        cmFragment.setArguments(bundle);
                        ft.replace(R.id.frame_layout, cmFragment).commit();
                        break;
                    case 4:
                        FamilyFragment familyFragment = new FamilyFragment();
                        familyFragment.setArguments(bundle);
                        ft.replace(R.id.frame_layout, familyFragment).commit();
                        break;
                    case 5:
                        FellowTownsManFragment ftmFragment = new FellowTownsManFragment();
                        ftmFragment.setArguments(bundle);
                        ft.replace(R.id.frame_layout, ftmFragment).commit();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabUnselected(int position) {}

            @Override
            public void onTabReselected(int position) {}
        });
    }

    public void onStart() {
        super.onStart();
        if(mCollapsingToolbar!=null) {
            mCollapsingToolbar.setTitle(name);
        }

        Bitmap bitmap = getLoacalBitmap(photo); //根据路径从本地取图片
        imageView.setImageBitmap(bitmap);    //设置Bitmap
    }

    public void delete(View v) {
        buildDialog();
    }

    //删除时弹出的提示对话框
    private void buildDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
        builder.setTitle("将要删除联系人");
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Controller controller = new Controller(DetailActivity.this);
                controller.delete(id);
                Toast.makeText(DetailActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
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
                                        getAppDetailSettingIntent(DetailActivity.this);
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
