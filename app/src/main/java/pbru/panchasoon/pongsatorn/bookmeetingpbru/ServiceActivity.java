package pbru.panchasoon.pongsatorn.bookmeetingpbru;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Request;

import org.json.JSONArray;
import org.json.JSONObject;

public class ServiceActivity extends AppCompatActivity {
    private TextView textView;
    private ListView listView;
    private String urlJSON = "http://swiftcodingthai.com/pbru/get_service.php";
    private String[] nameRoomStringps, nameBuildStringps, sizeStrings, pricrDayStrings,
            priceHoliStrings, iconStrings, userloginStrings, image2Strings, image3Strings,
            image4Strings, image5Strings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        userloginStrings = getIntent().getStringArrayExtra("User");


        textView = (TextView) findViewById(R.id.textView7);
        listView = (ListView) findViewById(R.id.listView);

        textView.setText("Welcome " + userloginStrings[1] + " " + userloginStrings[2]);
        SynService synService = new SynService();
        synService.execute();


    }//ลูก

    public class SynService extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            try {

                OkHttpClient okHttpClient = new OkHttpClient();
                com.squareup.okhttp.Request.Builder builder = new com.squareup.okhttp.Request.Builder();
                com.squareup.okhttp.Request request = builder.url(urlJSON).build();
                Response response = okHttpClient.newCall(request).execute();

                return response.body().string();


            } catch (Exception e) {

                return null;
            }


        }// doin

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("pbruV2", "JSON ==>" + s);

            try {

                JSONArray jsonArray = new JSONArray(s);
                nameRoomStringps = new String[jsonArray.length()];
                nameBuildStringps = new String[jsonArray.length()];
                sizeStrings = new String[jsonArray.length()];
                pricrDayStrings = new String[jsonArray.length()];
                priceHoliStrings = new String[jsonArray.length()];
                iconStrings = new String[jsonArray.length()];
                image2Strings = new String[jsonArray.length()];
                image3Strings = new String[jsonArray.length()];
                image4Strings = new String[jsonArray.length()];
                image5Strings = new String[jsonArray.length()];

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    nameRoomStringps[i] = jsonObject.getString("NameRoom");
                    nameBuildStringps[i] = jsonObject.getString("NameBuild");
                    sizeStrings[i] = jsonObject.getString("Size");
                    pricrDayStrings[i] = jsonObject.getString("PriceDay");
                    priceHoliStrings[i] = jsonObject.getString("PriceHoliday");
                    iconStrings[i] = jsonObject.getString("Image1");
                    image2Strings[i] = jsonObject.getString("Image2");
                    image3Strings[i] = jsonObject.getString("Image3");
                    image4Strings[i] = jsonObject.getString("Image4");
                    image5Strings[i] = jsonObject.getString("Image5");


                }//for
                MyAdapter myAdapter = new MyAdapter(ServiceActivity.this, nameRoomStringps,
                        nameBuildStringps, sizeStrings, pricrDayStrings, priceHoliStrings,
                        iconStrings);
                listView.setAdapter(myAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int i, long id) {

                        Intent intent = new Intent(ServiceActivity.this, DetailActivity.class);
                        intent.putExtra("NameBuild", nameBuildStringps[i]);
                        intent.putExtra("NameRoom", nameRoomStringps[i]);
                        intent.putExtra("Size", sizeStrings[i]);
                        intent.putExtra("PriceDay", pricrDayStrings[i]);
                        intent.putExtra("PriceHoliday", priceHoliStrings[i]);
                        intent.putExtra("Image1", iconStrings[i]);
                        intent.putExtra("Image2", image2Strings[i]);
                        intent.putExtra("Image3", image3Strings[i]);
                        intent.putExtra("Image4", image4Strings[i]);
                        intent.putExtra("Image5", image5Strings[i]);

                        intent.putExtra("User", userloginStrings);
                        startActivity(intent);


                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


}//แม่