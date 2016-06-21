package com.esgi.android.news.client.recycler;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.esgi.android.news.R;
import com.esgi.android.news.metier.model.Item;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Sam on 10/06/16.
 */
public class RVAdapter  extends RecyclerView.Adapter<RVAdapter.ItemViewHolder> {

    Context context;
    List<Item> items;

    public RVAdapter(Context context, List<Item> items){
        this.items = items;
        this.context = context;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        ItemViewHolder holder = new ItemViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.title.setText(items.get(position).getTitle());
        holder.description.setText(items.get(position).getDescription());
        if(items.get(position).getUrlPicture() == null){
            holder.imageView.setVisibility(View.GONE);
        } else {
            Picasso.with(context).load(items.get(position).getUrlPicture()).into(holder.imageView);
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView title;
        TextView description;
        ImageView imageView;

        ItemViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            title = (TextView)itemView.findViewById(R.id.item_title);
            description = (TextView)itemView.findViewById(R.id.item_description);
            imageView = (ImageView) itemView.findViewById(R.id.item_img);
        }

    }
}
