package pbru.panchasoon.pongsatorn.bookmeetingpbru;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class SingUpActivity extends AppCompatActivity {

    //Explicit ประกาศตัวแปร
    private EditText nameEditText, surnameEditText, idCardEditText,
            userEditText, passwordEditText;
    private RadioGroup radioGroup;
    private RadioButton officeRadioButton, outOfficeRadioButton;
    private String nameString, surnameString, idCardString, userString, passwordString,
            officeString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        //bind Widget
        bindWidget();

        //Radio Controller
        radioController();



    } // Main Method

    private void radioController() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.radioButton:
                        officeString = "0";
                        break;
                    case R.id.radioButton2:
                        officeString = "1";
                        break;
                    default:
                        officeString = "0";
                        break;
                }


            }
        });
    }

    private void bindWidget() {
        nameEditText = (EditText) findViewById(R.id.editText);
        surnameEditText = (EditText) findViewById(R.id.editText2);
        idCardEditText = (EditText) findViewById(R.id.editText3);
        userEditText = (EditText) findViewById(R.id.editText4);
        passwordEditText = (EditText) findViewById(R.id.editText5);
        radioGroup = (RadioGroup) findViewById(R.id.ragOffice);
        officeRadioButton = (RadioButton) findViewById(R.id.radioButton);
        outOfficeRadioButton = (RadioButton) findViewById(R.id.radioButton2);
    }

    public void clickSingUpSing(View view) {

        nameString = nameEditText.getText().toString().trim();
        surnameString = surnameEditText.getText().toString().trim();
        idCardString = idCardEditText.getText().toString().trim();
        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();


        //Check Space
        if (checkSpace()) {
            //Have Space
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "ข้อมลูไม่พอ", "กรุณากรอกทุกช่อง");
        } else if (idCardString.length()!= 13) {  //check idCard
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this,"รหัสบัตรประชาชนผิด", "รหัสบัตรประชาชน ต้องมี 13 หลักเท่านั้น");
        } else if (checkRadioChoose()) {
            //Non Check
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "ยังไม่เลือกสถานะ", "โปรดเลือกสถานะ");
        } else if (checkser()) {
            //จะแสดงสภาวะว่ามี User อยู่แล้ว
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "มี User นี่แล้ว", "กรุณาตรวจสอบ");
        } else if (chekcIDcard()) {
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this,"รหัสบัตรประชาชนถูกใช้แล้ว", "กรุณาตรวจสอบ");
        } else {
            uploadValuetoServer();
        }

    }   // clickSing

    private boolean chekcIDcard() {

        try {

            SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                    MODE_PRIVATE, null);
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FORM userTABLE WHERE IDcard = " + "'" + idCardString + "'", null);
            cursor.moveToFirst();

            Log.d("25May", "ID ==> " + idCardString);
            Log.d("25May", "ID get ==>" + cursor.getString(3));

            return true;

        } catch (Exception e) {
            return false;
        }


    }

    private boolean checkser() {

        try {

            SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                    MODE_PRIVATE, null);
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FORM userTABLE WHERE User = " + "'" + userString + "'", null);
            cursor.moveToFirst();

            Log.d("25May", "ID ==> " + userString);
            Log.d("25May", "ID get ==>" + cursor.getString(5));

            return true;

        } catch (Exception e) {
            return false;
        }

    }

    private void uploadValuetoServer() {

        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormEncodingBuilder()
                .add("isAdd", "true")
                .add("Name", nameString)
                .add("Surname", surnameString)
                .add("IDcard", idCardString)
                .add("Office", officeString)
                .add("User", userString)
                .add("Password", passwordString)
                .build();
        Request.Builder builder = new Request.Builder();
        Request request = builder.url("http://swiftcodingthai.com/pbru/add_user_master.php")
                .post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                finish();
            }
        });

    } //uploadValue

    private boolean checkRadioChoose() {

        boolean result = true;

        result = officeRadioButton.isChecked() || outOfficeRadioButton.isChecked();

        return !result;
    }

    private boolean checkSpace() {

        boolean result = true;

        result = nameString.equals("") ||
                surnameString.equals("") ||
                idCardString.equals("") ||
                userString.equals("") ||
                passwordString.equals("");


        return result;
    }

} // Main Class
