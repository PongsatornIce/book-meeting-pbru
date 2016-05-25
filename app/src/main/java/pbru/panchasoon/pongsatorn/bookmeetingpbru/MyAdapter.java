package pbru.panchasoon.pongsatorn.bookmeetingpbru;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by addoid on 5/25/2016.
 */
public class MyAdapter extends BaseAdapter{
    private Context context;
    private String[] nameRoomStrings, nameBuildStrings, sizeStrings,
                    priceDayStrings, priceHolidayStrings, iconStrings;

    public MyAdapter(Context context,
                     String[] nameRoomStrings,
                     String[] nameBuildStrings,
                     String[] sizeStrings,
                     String[] priceDayStrings,
                     String[] priceHolidayStrings,
                     String[] iconStrings) {
        this.context = context;
        this.nameRoomStrings = nameRoomStrings;
        this.nameBuildStrings = nameBuildStrings;
        this.sizeStrings = sizeStrings;
        this.priceDayStrings = priceDayStrings;
        this.priceHolidayStrings = priceHolidayStrings;
        this.iconStrings = iconStrings;
    }

    @Override
    public int getCount() {
        return nameRoomStrings.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View View, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view1 = layoutInflater.inflate(R.layout.my_listview, viewGroup, false);

        TextView nameRoomTextView = (TextView) view1.findViewById(R.id.textView8);
        nameRoomTextView.setText(nameRoomStrings[i]);

        TextView nameBuildTextView = (TextView) view1.findViewById(R.id.textView9);
        nameBuildTextView.setText(nameBuildStrings[i]);

        TextView sizeTextView = (TextView) view1.findViewById(R.id.textView10);
        sizeTextView.setText("จำนวนที่นั่ง " + sizeStrings[i]);

        TextView priceDayTextView = (TextView) view1.findViewById(R.id.textView11);
        priceDayTextView.setText("วันธรรมดา " + priceDayStrings[i]);

        TextView priceHoliTextView = (TextView) view1.findViewById(R.id.textView12);
        priceHoliTextView.setText("วันหยุด " + priceHolidayStrings[i]);

        //  โค้ดการใส่รูป และขนานของรูปที่เราตั้งไว
        ImageView imageView =  (ImageView) view1.findViewById(R.id.imageView2);
        Picasso.with(context).load(iconStrings[i]).resize(110, 100).into(imageView);


        return view1;
    }
} // Main Class
