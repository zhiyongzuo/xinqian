package cn.contactbook.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by dell on 2016/10/10.
 */
public class DBAdapter {

    private static final String DB_NAME = "contactBook.db";
    private static final String DB_TABLE = "contact";
    private static final int DB_version = 1;

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_PHONE2 = "phone2";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHOTO = "photo";
    private static final String KEY_SEX = "sex";
    private static final String KEY_COMPANY = "company";
    //
    public static final String KEY_ARMYFRIENDS = "armyfriends";
    public static final String KEY_FRIENDS = "friends";
    public static final String KEY_CLASSMATES = "Classmates";
    public static final String KEY_FAMILY = "family";
    public static final String KEY_FELLOWTOWNSMAN = "fellowtownsman";


    private SQLiteDatabase db;
    private final Context context;

    private static class DBOpenHelper extends SQLiteOpenHelper {
        DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        private static final String DB_CREATE = "create table " +
                DB_TABLE + "(" + KEY_ID + " integer primary key autoincrement," +
                KEY_NAME + " varchar(20)," + KEY_PHONE + " varchar(20)," +
                KEY_PHONE2 + " varchar(20)," + KEY_EMAIL + " varchar(50)," +
                KEY_PHOTO + " varchar(100)," + KEY_SEX + " varchar(10)," + KEY_COMPANY +" varchar(100)," +
                KEY_ARMYFRIENDS + " varchar(100)," +
                KEY_FRIENDS + " varchar(100)," +
                KEY_CLASSMATES + " varchar(100)," +
                KEY_FAMILY + " varchar(100)," +
                KEY_FELLOWTOWNSMAN + " varchar(100))";

        @Override
        public void onCreate(SQLiteDatabase _db) {
            _db.execSQL(DB_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {
            _db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
            onCreate(_db);
        }

    }

    public DBAdapter(Context _context) {
        context = _context;
    }

    /**
     * 打开数据库
     * @throws SQLiteException
     */
    public void open() throws SQLiteException {
        DBOpenHelper dbOpenHelper = new DBOpenHelper(context, DB_NAME, null, DB_version);
        try {
            db = dbOpenHelper.getWritableDatabase();
        } catch (SQLiteException ex) {
            db = dbOpenHelper.getReadableDatabase();
        }
    }

    public void close() {
        if (db != null) {
            db.close();
            db = null;
        }
    }

    /**
     * 插入联系人
     *
     * @param contact
     * @return
     */
    public long insert(Contact contact) {
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_NAME, contact.getName());
        newValues.put(KEY_PHONE, contact.getPhone());
        newValues.put(KEY_PHONE2, contact.getPhone2());
        newValues.put(KEY_EMAIL, contact.getEmail());
        newValues.put(KEY_PHOTO, contact.getPhoto());
        newValues.put(KEY_SEX, contact.getSex());
        newValues.put(KEY_COMPANY, contact.getCompany());
        //
        newValues.put(KEY_ARMYFRIENDS, contact.getArmyFriends());
        newValues.put(KEY_FRIENDS, contact.getFriends());
        newValues.put(KEY_CLASSMATES, contact.getClassmates());
        newValues.put(KEY_FAMILY, contact.getFamily());
        newValues.put(KEY_FELLOWTOWNSMAN, contact.getFellowtownsman());
        return db.insert(DB_TABLE, null, newValues);
    }

    /**
     * 删除联系人
     * @param id
     * @return
     */
    public long delete(int id) {
        return db.delete(DB_TABLE, KEY_ID + " like ? ", new String[]{id + ""});
    }

    /**
     * 更新联系人
     * @param id
     * @param contact
     * @return
     */
    public long update(int id, Contact contact) {
        ContentValues updateValues = new ContentValues();
        updateValues.put(KEY_NAME, contact.getName());
        updateValues.put(KEY_PHONE, contact.getPhone());
        updateValues.put(KEY_PHONE2, contact.getPhone2());
        updateValues.put(KEY_EMAIL, contact.getEmail());
        updateValues.put(KEY_PHOTO, contact.getPhoto());
        updateValues.put(KEY_SEX, contact.getSex());
        updateValues.put(KEY_COMPANY, contact.getCompany());
        //
        updateValues.put(KEY_ARMYFRIENDS, contact.getArmyFriends());
        updateValues.put(KEY_FRIENDS, contact.getFriends());
        updateValues.put(KEY_CLASSMATES, contact.getClassmates());
        updateValues.put(KEY_FAMILY, contact.getFamily());
        updateValues.put(KEY_FELLOWTOWNSMAN, contact.getFellowtownsman());
        return db.update(DB_TABLE, updateValues, KEY_ID + " like ? ", new String[]{id + ""});
    }

    /**
     * 根据ID查询联系人
     * @param id
     * @return
     */
    public Contact[] getContact(int id) {
        Cursor cursor = db.query(DB_TABLE, new String[]{KEY_ID, KEY_NAME, KEY_PHONE, KEY_PHONE2, KEY_EMAIL,
                        KEY_PHOTO, KEY_SEX, KEY_COMPANY, KEY_ARMYFRIENDS, KEY_FRIENDS, KEY_CLASSMATES, KEY_FAMILY, KEY_FELLOWTOWNSMAN},
                KEY_ID + " like ? ", new String[]{id + ""}, null, null, null, null);
        return ConvertToContact(cursor);
    }

    /**
     * 根据company name 查询联系人
     */
    public Contact[] getContact(String company) {
        Cursor cursor = db.query(DB_TABLE, new String[]{KEY_ID, KEY_NAME, KEY_PHONE, KEY_PHONE2, KEY_EMAIL,
                KEY_PHOTO, KEY_SEX, KEY_COMPANY, KEY_ARMYFRIENDS, KEY_FRIENDS, KEY_CLASSMATES, KEY_FAMILY, KEY_FELLOWTOWNSMAN},
            KEY_COMPANY + " like ? ", new String[]{company + ""}, null, null, null, null);
        return ConvertToContact(cursor);
    }


    /**
     * 查询所有联系人
     * @return
     */
    public Contact[] getAll() {
        Cursor cursor = db.query(DB_TABLE, new String[]{KEY_ID, KEY_NAME, KEY_PHONE, KEY_PHONE2, KEY_EMAIL,
                        KEY_PHOTO, KEY_SEX, KEY_COMPANY, KEY_ARMYFRIENDS, KEY_FRIENDS, KEY_CLASSMATES, KEY_FAMILY, KEY_FELLOWTOWNSMAN},
                null, null, null, null, KEY_NAME + " asc");
        return ConvertToContact(cursor);
    }

    /**
     * 将游标cursor中的联系人取出，放到联系人数组中
     * @param cursor
     * @return 联系人数组
     */
    private Contact[] ConvertToContact(Cursor cursor) {
        int resultCounts = cursor.getCount();
        if (resultCounts == 0 || !cursor.moveToFirst()) return null;
        Contact[] peoples = new Contact[resultCounts];
        for (int i = 0; i < resultCounts; i++) {
            peoples[i] = new Contact();
            peoples[i].setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
            peoples[i].setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            peoples[i].setPhone(cursor.getString(cursor.getColumnIndex(KEY_PHONE)));
            peoples[i].setPhone2(cursor.getString(cursor.getColumnIndex(KEY_PHONE2)));
            peoples[i].setEmail(cursor.getString(cursor.getColumnIndex(KEY_EMAIL)));
            peoples[i].setPhoto(cursor.getString(cursor.getColumnIndex(KEY_PHOTO)));
            peoples[i].setSex(cursor.getString(cursor.getColumnIndex(KEY_SEX)));
            peoples[i].setCompany(cursor.getString(cursor.getColumnIndex(KEY_COMPANY)));
            //
            peoples[i].setArmyFriends(cursor.getString(cursor.getColumnIndex(KEY_ARMYFRIENDS)));
            peoples[i].setFriends(cursor.getString(cursor.getColumnIndex(KEY_FRIENDS)));
            peoples[i].setClassmates(cursor.getString(cursor.getColumnIndex(KEY_CLASSMATES)));
            peoples[i].setFamily(cursor.getString(cursor.getColumnIndex(KEY_FAMILY)));
            peoples[i].setFellowtownsman(cursor.getString(cursor.getColumnIndex(KEY_FELLOWTOWNSMAN)));
            cursor.moveToNext();
        }
        return peoples;
    }
}
