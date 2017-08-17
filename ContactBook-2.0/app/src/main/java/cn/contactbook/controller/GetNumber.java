package cn.contactbook.controller;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

import cn.contactbook.model.PhoneInfo;

/**
 * Created by tomsdeath on 2017/8/15.
 */

public class GetNumber {
    public static List<PhoneInfo> lists=new ArrayList<PhoneInfo>();//定义列表
    public static String getNumber(Context context){
        Cursor cursor=context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
        String phoneNumber;     //context.get获取通讯录。query查询Cursor为数据类型
        String phoneName;
        while (cursor.moveToNext()){
            phoneNumber=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER ));
            phoneName=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            PhoneInfo phoneInfo=new PhoneInfo(phoneName,phoneNumber);
            lists.add(phoneInfo);
            System.out.println(phoneName+phoneNumber);
        }
        return null;
    }
}
