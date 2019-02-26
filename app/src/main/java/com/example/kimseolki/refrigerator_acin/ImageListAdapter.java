package com.example.kimseolki.refrigerator_acin;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * Created by pc on 2017-05-03.
 */

public class ImageListAdapter extends BaseAdapter {

    Context context;
    int layout;
    Integer[] img;
    LayoutInflater inf;


    public ImageListAdapter(Context context, int layout, Integer[] img) {
        this.context = context;
        this.layout = layout;
        this.img = img;
        inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return img.length;
    }

    @Override
    public Object getItem(int position) {
        return img.length;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null)
            convertView = inf.inflate(layout, null);
        ImageView iv = (ImageView) convertView.findViewById(R.id.imageView);
        Bitmap size = BitmapFactory.decodeResource(context.getResources(), img[position]);
        size = Bitmap.createScaledBitmap(size, 300, 300, true);
        iv.setPadding(8,8,8,8);
        iv.setImageBitmap(size);
        FoodInfo.getInstance().setFood_type(img[position]);
        return convertView;}

}
