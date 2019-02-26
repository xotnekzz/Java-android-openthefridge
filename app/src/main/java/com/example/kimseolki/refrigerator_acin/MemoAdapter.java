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

import com.example.kimseolki.refrigerator_acin.model.Memo;

import java.util.List;

/**
 * Created by kimseolki on 2017-05-20.
 */

public class MemoAdapter extends ArrayAdapter<Memo>{

    List<Memo> memo;
    Context context;
    private LayoutInflater mlnflater;

    public MemoAdapter(@NonNull Context context,  @NonNull List<Memo> objects) {
        super(context,0 , objects);
        this.context = context;
        this.mlnflater = LayoutInflater.from(context);
        memo = objects;
    }

    @Nullable
    @Override
    public Memo getItem(int position){
        return memo.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder vh;
        if(convertView == null){
            View view = mlnflater.inflate(R.layout.memo_list_layout,parent,false);
            vh = ViewHolder.create((LinearLayout) view);
            view.setTag(vh);
        }else {
            vh = (ViewHolder) convertView.getTag();
        }
        Memo item = getItem(position);

        vh.tvMauthor.setText(item.getAuthor());
        vh.tvMemo.setText(item.getMemo());


        return vh.rootView;
    }
    private static class ViewHolder{
        public final LinearLayout rootView;
        public final TextView tvMauthor;
        public final TextView tvMemo;



        public ViewHolder(LinearLayout rootView, TextView tvMauthor, TextView tvMemo) {
            this.rootView = rootView;
            this.tvMauthor = tvMauthor;
            this.tvMemo = tvMemo;

        }
        public static ViewHolder create(LinearLayout rootView){
            TextView tvMauthor = (TextView) rootView.findViewById(R.id.tvMauthor);
            TextView tvMemo = (TextView) rootView.findViewById(R.id.tvMmemo);

            return new ViewHolder(rootView,tvMauthor,tvMemo);
        }

    }
}
