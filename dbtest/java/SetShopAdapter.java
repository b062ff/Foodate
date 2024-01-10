package com.example.dbtest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SetShopAdapter extends RecyclerView.Adapter<SetShopAdapter.ViewHolder> {
    private Context context;
    private List<Post> posts;

    public SetShopAdapter(Context context,List<Post> posts)
    {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(posts.get(position).store_name);
        int resId = context.getResources().getIdentifier("green.jpg", "drawable", context.getPackageName());

        holder.imageView.setImageResource(resId);

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            // ... ViewHolder内の他の必要なコード
            name = itemView.findViewById(R.id.name);
            imageView = itemView.findViewById(R.id.image);
        }

        // ... ViewHolder内の他の必要なコード
    }
}
