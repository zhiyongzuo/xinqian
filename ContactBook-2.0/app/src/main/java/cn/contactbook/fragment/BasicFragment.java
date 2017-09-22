package cn.contactbook.fragment;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.contactbook.R;
import cn.contactbook.controller.Controller;
import cn.contactbook.model.Contact;
import cn.contactbook.service.MyService;

import static cn.contactbook.androidUI.CompanyWorkerActivity.COMPANYWORKERACTIVITY_COMPANY_NAME;
import static cn.contactbook.androidUI.DetailActivity.DETAIL_ACTIVITY_NAME;

/**
 * A simple {@link Fragment} subclass.
 */
public class BasicFragment extends Fragment {
    private TextView tv_name;
    private EditText tv_phone;
    private EditText tv_phone2;
    private EditText tv_email;
    private EditText tv_sex;
    private EditText tv_company;
    private Button p2;
    private Button msg2;
    private Button button;
    private String name = "";
    private String phone = "";
    private String phone2 = "";
    private String email = "";
    private String sex = "";
    private String company = "";
    Controller controller;
    Contact[] contact;
    private int id;

    public BasicFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basic, container, false);
        tv_phone = (EditText) view.findViewById(R.id.phone);
        tv_phone2 = (EditText) view.findViewById(R.id.phone2);
        tv_email = (EditText) view.findViewById(R.id.email);
        tv_sex = (EditText) view.findViewById(R.id.sex);
        tv_company = (EditText) view.findViewById(R.id.company);
        button = (Button) view.findViewById(R.id.button);
        p2 = (Button) view.findViewById(R.id.call_phone_2);
        msg2 = (Button) view.findViewById(R.id.msg_phone_2);

        Bundle bundle = getArguments();
        if(bundle != null) {
            name = (String)bundle.get(DETAIL_ACTIVITY_NAME);
        }
        controller = new Controller(getContext());
        contact = controller.getContactByName(name);

        button.setOnClickListener(listener);
        tv_phone.setEnabled(false);
        tv_phone2.setEnabled(false);
        tv_email.setEnabled(false);
        tv_sex.setEnabled(false);
        tv_company.setEnabled(false);

        id = contact[0].getId();
        phone = contact[0].getPhone();
        phone2 = contact[0].getPhone2();
        email = contact[0].getEmail();
        sex = contact[0].getSex();
        company = contact[0].getCompany();
        tv_phone.setText(phone);
        tv_phone2.setText(phone2);
        tv_email.setText(email);
        tv_sex.setText(sex);
        tv_company.setText(company);
        if(tv_phone2!=null && tv_phone2.length()>0) {
            p2.setVisibility(View.VISIBLE);
            msg2.setVisibility(View.VISIBLE);
        } else {
            p2.setVisibility(View.GONE);
            msg2.setVisibility(View.GONE);
        }
        return view;
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(button.getText().toString().equals("修改")) {
                button.setText("完成");
                tv_phone.setEnabled(true);
                tv_phone2.setEnabled(true);
                tv_email.setEnabled(true);
                tv_sex.setEnabled(true);
                tv_company.setEnabled(true);
            } else {
                button.setText("修改");
                tv_phone.setEnabled(false);
                tv_phone2.setEnabled(false);
                tv_email.setEnabled(false);
                tv_sex.setEnabled(false);
                tv_company.setEnabled(false);
                contact[0].setPhone(tv_phone.getText().toString());
                contact[0].setPhone2(tv_phone2.getText().toString());
                contact[0].setEmail(tv_email.getText().toString());
                contact[0].setSex(tv_sex.getText().toString());
                contact[0].setCompany(tv_company.getText().toString());
                Controller controller = new Controller(getContext());
                controller.update(id, contact[0]);
                if(tv_phone2!=null && tv_phone2.length()>0) {
                    p2.setVisibility(View.VISIBLE);
                    msg2.setVisibility(View.VISIBLE);
                } else {
                    p2.setVisibility(View.GONE);
                    msg2.setVisibility(View.GONE);
                }
            }
        }
    };

    //拨打电话
    public void call(View v) {
        //如果版本>=Android6.0并且检查自身权限没有被赋予时，请求权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 1);
        } else {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.CALL");//调用系统拨打电话
            intent.setData(Uri.parse("tel:" + phone));
            startActivity(intent);
            //启动服务，在service中监听电话状态并进行重播提醒
            Intent callIntent = new Intent(getContext(), MyService.class);
            callIntent.putExtra("phone", phone);
            getContext().startService(callIntent);
        }
    }

    //拨打电话
    public void call2(View v) {
        if (phone2 != null && phone2.equals("")) {
            Toast.makeText(getContext(), "没有号码", Toast.LENGTH_SHORT).show();
        } else {
            //动态获取打电话权限
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ActivityCompat.checkSelfPermission(getContext(),
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 1);
            } else {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.CALL");//调用系统拨打电话
                intent.setData(Uri.parse("tel:" + phone2));
                startActivity(intent);
                //启动服务，在service中监听电话状态并进行重播提醒
                Intent callIntent = new Intent(getContext(), MyService.class);
                callIntent.putExtra("phone", phone2);
                getContext().startService(callIntent);
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
            Toast.makeText(getContext(), "没有号码", Toast.LENGTH_SHORT).show();
        } else {
            Uri smsToUri = Uri.parse("smsto:" + phone2);
            Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);//调用系统发短信
            //intent.putExtra("发送内容是", " ");
            startActivity(intent);
        }
    }

}
