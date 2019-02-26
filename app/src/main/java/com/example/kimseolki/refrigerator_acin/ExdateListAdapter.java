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
 * Created by pc on 2017-05-03.
 */

public class ExdateListAdapter extends ArrayAdapter<Food_location> {

    List<Food_location> foods;
    Context context;
    private LayoutInflater mInflater;

    public ExdateListAdapter(@NonNull Context context, @NonNull ArrayList<Food_location> objects) {
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
        final ViewHolder vh;
        if (convertView == null) {
            View view = mInflater.inflate(R.layout.exdate_list_layout, parent, false);
            vh = ViewHolder.create((LinearLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        Food_location item = getItem(position);

        vh.tvFoodName.setText(item.getFood_Name());

        if(item.getD_day()==0){
            vh.tvFoodDday.setText("D-day");
        } else if(item.getD_day()>0){
            vh.tvFoodDday.setText("D+"+ String.valueOf(item.getD_day()));
        }else
            vh.tvFoodDday.setText("D" + String.valueOf(item.getD_day()));

        vh.tvFoodPer.setText(item.getFood_purchase());

        return vh.rootView;
    }

    private static class ViewHolder {
        public final LinearLayout rootView;
        public final TextView tvFoodName;
        public final TextView tvFoodDday;
        public final TextView tvFoodPer;

        public ViewHolder(LinearLayout rootView, TextView tvFoodName, TextView tvFoodDday, TextView tvFoodPer) {
            this.rootView = rootView;
            this.tvFoodName = tvFoodName;
            this.tvFoodDday = tvFoodDday;
            this.tvFoodPer = tvFoodPer;
        }
        public static ViewHolder create(LinearLayout rootView) {
            TextView tvFoodname = (TextView) rootView.findViewById(R.id.tvFoodname);
            TextView tvFoodDday = (TextView) rootView.findViewById(R.id.tvFoodDday);
            TextView tvFoodPur = (TextView) rootView.findViewById(R.id.tvFoodPer);
            return new ViewHolder(rootView, tvFoodname, tvFoodDday,tvFoodPur);
        }
    }
}
