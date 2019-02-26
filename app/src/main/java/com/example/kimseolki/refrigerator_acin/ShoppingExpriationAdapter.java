package com.example.kimseolki.refrigerator_acin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kimseolki.refrigerator_acin.model.Food_location;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kimseolki on 2017-06-05.
 */

public class ShoppingExpriationAdapter extends ArrayAdapter<Food_location> {

    List<Food_location> foods;
    Context context;
    private LayoutInflater mInflater;

    public ShoppingExpriationAdapter(@NonNull Context context, @NonNull ArrayList<Food_location> objects) {
        super(context,0 , objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        foods = objects;
    }

    @Nullable
    @Override
    public Food_location getItem(int position) {
        return foods.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ShoppingExpriationAdapter.ViewHolder vh;

        if (convertView == null) {
            View view = mInflater.inflate(R.layout.shopping_exdate_list_layout, parent, false);
            vh = ShoppingExpriationAdapter.ViewHolder.create((LinearLayout) view);
            view.setTag(vh);
        } else {
            vh = (ShoppingExpriationAdapter.ViewHolder) convertView.getTag();
        }

        Food_location item = getItem(position);

        vh.stvname.setText(item.getFood_Name());

        if(item.getD_day()==0){
            vh.stvdday.setText("D-day");
        } else if(item.getD_day()>0){
            vh.stvdday.setText("D+"+ String.valueOf(item.getD_day()));
        }else
            vh.stvdday.setText("D" + String.valueOf(item.getD_day()));

        return vh.rootView;
    }

    private static class ViewHolder {
        public final LinearLayout rootView;
        public final TextView stvname;
        public final TextView stvdday;

        public ViewHolder(LinearLayout rootView, TextView tvFoodName, TextView tvFoodDday) {
            this.rootView = rootView;
            this.stvname = tvFoodName;
            this.stvdday = tvFoodDday;

        }
        public static ShoppingExpriationAdapter.ViewHolder create(LinearLayout rootView) {
            TextView tvFoodname = (TextView) rootView.findViewById(R.id.stvname);
            TextView tvFoodDday = (TextView) rootView.findViewById(R.id.stvdday);

            return new ShoppingExpriationAdapter.ViewHolder(rootView, tvFoodname, tvFoodDday);
        }
    }
}
