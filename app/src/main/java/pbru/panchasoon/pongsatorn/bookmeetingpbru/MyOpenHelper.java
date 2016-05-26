package pbru.panchasoon.pongsatorn.bookmeetingpbru;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by addoid on 5/24/2016.
 */
public class MyOpenHelper extends SQLiteOpenHelper {

    // ประกาศตัวแปร
    public static final String database_name = "Pbru.db";
    private static final int database_version = 1;
    private static final String create_user_table = "create table userTABLE(" +
            "_id integer primary key," +
            "Name text," +
            "Surname text," +
            "IDcard text," +
            "Office text," +
            "User text," +
            "Password text);";

    private static final String create_order_table = "create table orderTABLE(" +
            "_id integer primary key," +
            "IDcard text," +
            "NameRoom text," +
            "Data text," +
            "Time text);";



    public MyOpenHelper(Context context) {
        super(context, database_name, null, database_version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(create_user_table);
        sqLiteDatabase.execSQL(create_order_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}   // Main Class
