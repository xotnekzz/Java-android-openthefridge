package com.example.kimseolki.refrigerator_acin;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kimseolki.refrigerator_acin.model.Food_location;

import java.util.List;

/**
 * Created by pc on 2017-05-03.
 */

public class FoodListAdapter extends ArrayAdapter<Food_location> {

    List<Food_location> foods;
    Context context;
    private LayoutInflater mInflater;

    public FoodListAdapter(@NonNull Context context, @NonNull List<Food_location> objects) {
        super(context, 0, objects);
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
        final ViewHolder vh;
        if (convertView == null) {
            View view = mInflater.inflate(R.layout.food_grid_layout, parent, false);
            vh = ViewHolder.create((LinearLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        Food_location item = getItem(position);

        vh.tvFoodName.setText(item.getFood_Name());
        if (item.getD_day() == 0) {
            vh.tvFoodDday.setText("D-day");
        } else if (item.getD_day() > 0) {
            vh.tvFoodDday.setText("D+" + String.valueOf(item.getD_day())); //지남
            vh.tvFoodDday.setTextColor(Color.parseColor("#ff0000"));
            vh.tvFoodDday.setBackgroundResource(R.drawable.textview1);
        } else
            vh.tvFoodDday.setText("D" + String.valueOf(item.getD_day())); //남음


        //vh.tvFoodExdate.setText(item.getFood_Exdate());
        //    Picasso.with(context).load(item.getFood_Image()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(vh.ivFood);
       if (item.getType().toString().equals("육류")) {
            vh.ivFood.setImageResource(R.mipmap.ic_launcher_0);
        } else if (item.getType().toString().equals("어류")) {
            vh.ivFood.setImageResource(R.mipmap.ic_launcher_1);
        } else if (item.getType().toString().equals("과일류")){
            vh.ivFood.setImageResource(R.mipmap.ic_launcher_2);
        } else if (item.getType().toString().equals("야채류")) {
            vh.ivFood.setImageResource(R.mipmap.ic_launcher_3);
        } else if (item.getType().toString().equals("유제품류")) {
            vh.ivFood.setImageResource(R.mipmap.ic_launcher_4);
        } else if (item.getType().toString().equals("음료류")) {
            vh.ivFood.setImageResource(R.mipmap.ic_launcher_5);
        } else if (item.getType().toString().equals("소스류")) {
            vh.ivFood.setImageResource(R.mipmap.ic_launcher_6);
        } else if (item.getType().toString().equals("기타")) {
            vh.ivFood.setImageResource(R.mipmap.ic_launcher_7);
        }

        if(item.getFood_Image() !=null) {
            vh.ivFood.setImageResource(Integer.parseInt(item.getFood_Image()));
        }

            return vh.rootView;
    }

    private static class ViewHolder {
        public final LinearLayout rootView;
        public final ImageView ivFood;
        public final TextView tvFoodName;
        public final TextView tvFoodDday;



        public ViewHolder(LinearLayout rootView,TextView tvFoodName, ImageView ivFood, TextView tvFoodDday) {
            this.rootView = rootView;
            this.tvFoodName = tvFoodName;
            this.ivFood = ivFood;
            this.tvFoodDday = tvFoodDday;
        }

        public static ViewHolder create(LinearLayout rootView) {

            TextView tvFoodName = (TextView) rootView.findViewById(R.id.ftvname);
            ImageView ivFood = (ImageView) rootView.findViewById(R.id.fivimage);
            TextView tvFoodDday = (TextView) rootView.findViewById(R.id.ftvdday);
            return new ViewHolder(rootView, tvFoodName, ivFood, tvFoodDday);
        }
    }
}
