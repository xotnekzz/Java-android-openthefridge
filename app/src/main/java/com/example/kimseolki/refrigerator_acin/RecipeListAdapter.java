package com.example.kimseolki.refrigerator_acin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kimseolki.refrigerator_acin.model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by pc on 2017-05-03.
 */

public class RecipeListAdapter extends ArrayAdapter<Recipe> {

    List<Recipe> recipe;
    Context context;
    private LayoutInflater mInflater;

    public RecipeListAdapter(@NonNull Context context, @NonNull List<Recipe> objects) {
        super(context,0 , objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        recipe = objects;
    }

    @Nullable
    @Override
    public Recipe getItem(int position) {
        return recipe.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            View view = mInflater.inflate(R.layout.recipe_list_layout, parent, false);
            vh = ViewHolder.create((LinearLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        Recipe item = getItem(position);

        vh.tvTitle.setText(item.getTitle());
        Picasso.with(context).load(item.getImage()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(vh.ivRecipe);

        return vh.rootView;
    }

    private static class ViewHolder {
        public final LinearLayout rootView;
        public final ImageView ivRecipe;
        public final TextView tvTitle;

        public ViewHolder(LinearLayout rootView, ImageView ivRecipe, TextView tvTitle) {
            this.rootView = rootView;
            this.ivRecipe = ivRecipe;
            this.tvTitle = tvTitle;
        }

        public static ViewHolder create(LinearLayout rootView) {
            ImageView ivRecipe = (ImageView) rootView.findViewById(R.id.ivRecipe);
            TextView tvTitle = (TextView) rootView.findViewById(R.id.tvTitle);
            return new ViewHolder(rootView, ivRecipe, tvTitle);
        }
    }
}
