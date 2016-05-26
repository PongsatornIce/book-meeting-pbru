package pbru.panchasoon.pongsatorn.bookmeetingpbru;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    //ประกาศตัวแปร
    private MyManage myManage;
    private String strURL = "http://swiftcodingthai.com/pbru/get_user.php";
    private String strLogo = "http://swiftcodingthai.com/pbru/Image/logo_pbru1.png";
    private ImageView imageView;
    private EditText userEditText, passwordEditText;
    private String userString, passwordString;
    private String[] userLoginStrings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //วิธีใส่รูปภาพ
        imageView = (ImageView) findViewById(R.id.imageView);
        userEditText = (EditText) findViewById(R.id.editText6);
        passwordEditText = (EditText) findViewById(R.id.editText7);

        //การโชว์โลโก้
        Picasso.with(this).load(strLogo).into(imageView);

        myManage = new MyManage(this);

        //การเทสแอด User
        //myManage.addUser("name","sur","idcard","off", "user","pass");
        //myManage.addOrder("idCard", "NameRoom", "26/5/16", "1");


        //ลบทั้งหมดในฐานข้อมูล
        deleteAllSQLite();

        synJSON();

    } // Main Method

    @Override
    protected void onResume() {
        super.onResume();
        deleteAllSQLite();
        synJSON();
    }

    public void clickSingIn(View view) {

        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

        //เช็ค สเปส
        if (userString.equals("") || passwordString.equals("")) {
            //จะทำงานในสภาวะที่มีช่องว่าง หรือ ค่าว่าง
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this,"มีช่องว่าง","กรุณากรอกช่างว่าง");

        } else {
            //เช็คว่ามีค่าว่างไม

            searchUser(userString);
        }

    }  //ทำปุ่มคลิก ลงชื่อเข้าใช้ หรือ ปุ่ม SingIn

    private void searchUser(String userString) {

        try {
            //
            SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                    MODE_PRIVATE, null);
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM userTABLE WHERE User = " + "'" + userString + "'", null);
            cursor.moveToFirst();
            userLoginStrings = new String[cursor.getColumnCount()];
            for (int i=0;i<cursor.getColumnCount();i++){
                userLoginStrings[i] = cursor.getString(i);
            } //หลูบ
            cursor.close();

            //เช็ค Password
            if (passwordString.equals(userLoginStrings[6])) {
                //Password ที่ถูกต้องแล้ว
                Intent intent = new Intent(MainActivity.this, ServiceActivity.class);
                                                        //this คือจุดเริ่มต้น class คือจุดสิ้นสุด
                intent.putExtra("User", userLoginStrings);
                startActivity(intent);
                finish();

            } else {
                //Password ที่ไม่ถูกต้อง
                MyAlert myAlert = new MyAlert();
                myAlert.myDialog(this,"พาสเวอร์ผิดพลาด","กรุณาตรวจสอบ");
            }


        } catch (Exception e) {
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this,"ไม่มี User นี้", "ไม่ถูกต้อง" + userString + "ในฐานข้อมูล");
        }

    }   //โค้ดค้นหา User ในฐานข้อมูล
    private void synJSON() {
        SynUser synUser = new SynUser();
        synUser.execute();
    }

    //การสร้าง อินเน้อคลาส คือการสร้างจากด้านใน
    public class SynUser extends AsyncTask<Void, Void, String> {


        @Override
        protected String doInBackground(Void... params) {

            try {

                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(strURL).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();


            } catch (Exception e) {
                return null;
            }

        }   //นี้คือ คลาสเบื้องหลัง

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("pbruV1", "strJSON ==> " + s);

            try {

                JSONArray jsonArray = new JSONArray(s);

                for (int i=0;i<jsonArray.length();i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String strName = jsonObject.getString(MyManage.column_Name);
                    String strSurname = jsonObject.getString(MyManage.column_Surname);
                    String strIDcard = jsonObject.getString(MyManage.column_IDcard);
                    String strOffice = jsonObject.getString(MyManage.column_Office);
                    String strUser = jsonObject.getString(MyManage.column_User);
                    String strPassword = jsonObject.getString(MyManage.column_Password);
                    myManage.addUser(strName, strSurname, strIDcard,
                            strOffice, strUser, strPassword);


                }   // หลูบ for



            } catch (Exception e) {
                e.printStackTrace();
            }


        }   //นี้คือ onPost
    }   // เป็นแบบ คลาสเบื้องหลัง


    private void deleteAllSQLite() {
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE,null);
        sqLiteDatabase.delete(MyManage.user_table, null, null);
        sqLiteDatabase.delete(MyManage.order_table, null, null);
    }

    public void clickSingUpMain(View view) {
        startActivity(new Intent(MainActivity.this, SingUpActivity.class));
    }


} // Main Class
