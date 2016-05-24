package pbru.panchasoon.pongsatorn.bookmeetingpbru;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class MainActivity extends AppCompatActivity {

    //ประกาศตัวแปร
    private MyManage myManage;
    private String strURL = "http://swiftcodingthai.com/pbru/get_user.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myManage = new MyManage(this);

        //การเทสแอด User

        //myManage.addUser("name","sur","idcard","off", "user","pass");

        //ลบทั้งหมดในฐานข้อมูล
        deleteAllSQLite();

        synJSON();

    } // Main Method

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

        }   //นี้คือ onPost
    }   // เป็นแบบ คลาสเบื้องหลัง


    private void deleteAllSQLite() {
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE,null);
        sqLiteDatabase.delete(MyManage.user_table, null, null);
    }

    public void clickSingUpMain(View view) {
        startActivity(new Intent(MainActivity.this, SingUpActivity.class));
    }


} // Main Class
