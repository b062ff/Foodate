package com.example.dbtest;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
//build.gradleでSDKのバージョン指定をする必要があるみたい? 今は1.1.0
//テキストとhttps://qiita.com/toya108/items/7f92c8088d84d1f60434　を参照

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ViewHolder> {
    private LayoutInflater layoutInflater;
    private List<Post> objects;

    ListViewAdapter(List<Post> objects){
        this.objects = objects;
    }

    //アイテムクリック
    private View.OnClickListener listener;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        ImageView image;

        ViewHolder(View view){
            super(view);
            name = view.findViewById(R.id.name);
            image = view.findViewById(R.id.image);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout,parent,false);
        final ViewHolder holder= new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int position = holder.getAdapterPosition();
                Log.d("ListViewAdapter",position+"番目がクリック");
                //positionはクリックした行番号を返す。
                onItemClick(view,position);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post data = this.objects.get(position);
        holder.name.setText(data.store_name);
        holder.image.setImageDrawable(data.image);


    }
    @Override
    public int getItemCount() {
        return objects.size();
    }
    //アダプタのインスタンスを作る際にここをオーバーライドする。
    void onItemClick(View view,int position){
    }
    

}


