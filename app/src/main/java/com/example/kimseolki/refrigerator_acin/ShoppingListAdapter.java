package com.example.kimseolki.refrigerator_acin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kimseolki.refrigerator_acin.model.Shopping;

import java.util.List;

/**
 * Created by hey on 2017-05-20.
 */

class ShoppingListAdapter extends ArrayAdapter<Shopping> implements View.OnClickListener {
    List<Shopping> shoppings;
    Context context;
    private LayoutInflater mInflater;

    public interface ListBtnClickListener {
        void onListBtnClick(int position);
    }

    // 생성자로부터 전달된 resource id 값을 저장.

    // 생성자로부터 전달된 ListBtnClickListener  저장.
    private ListBtnClickListener listBtnClickListener;

    public ShoppingListAdapter(@NonNull Context context ,@NonNull List<Shopping> objects, ListBtnClickListener clickListener) {
        super(context,0,objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.listBtnClickListener = clickListener ;
        shoppings = objects;
    }

    @Nullable
    @Override
    public Shopping getItem(int position) {
        return shoppings.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            View view = mInflater.inflate(R.layout.listview_shoppinglist, parent, false);
            vh = ViewHolder.create((LinearLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        Shopping item = getItem(position);

        vh.tvFoodname.setText(item.getShoppingName());
        vh.tvFoodmemo.setText(item.getShoppingMemo());

        vh.ivAdd.setTag(position);
        vh.ivAdd.setOnClickListener(this);

        return vh.rootView;
    }

    @Override
    public void onClick(View view) {
        if(this.listBtnClickListener != null){
            this.listBtnClickListener.onListBtnClick((int) view.getTag());
        }
    }

    private static class ViewHolder {
        public final LinearLayout rootView;
        public final Button ivAdd;
        public final TextView tvFoodname;
        public final TextView tvFoodmemo;

        public ViewHolder(LinearLayout rootView, Button ivAdd, TextView tvFoodname, TextView tvFoodmemo) {
            this.rootView = rootView;
            this.ivAdd = ivAdd;
            this.tvFoodname = tvFoodname;
            this.tvFoodmemo = tvFoodmemo;
        }

        public static ViewHolder create(LinearLayout rootView) {
            TextView tvFoodName = (TextView) rootView.findViewById(R.id.tvFoodname);
            TextView tvFoodExdate = (TextView) rootView.findViewById(R.id.tvFoodmemo);
            Button ibAdd = (Button) rootView.findViewById(R.id.btAdd);
            return new ViewHolder(rootView, ibAdd, tvFoodName, tvFoodExdate);
        }
    }
}