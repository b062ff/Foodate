package com.example.dbtest;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;
import java.util.List;

public class CardStackAdapter extends RecyclerView.Adapter<CardStackAdapter.myViewHolder> {
    public List<Post> cardList = new ArrayList<>();

    public CardStackAdapter(List<Post> cardList){
        this.cardList = cardList;
    }
    @NonNull
    @Override
    public CardStackAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout,parent,false);

        final myViewHolder holder = new myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CardStackAdapter.myViewHolder holder, int position) {
        Post cardItem = cardList.get(position);
        holder.name.setText(cardItem.store_name);
        holder.image.setImageDrawable(cardItem.image);
        if(cardItem.getAddress()!=null)holder.back_address.setText(cardItem.address);
        if(cardItem.getHoliday()!=null)holder.back_holiday.setText(cardItem.holiday);
        if(cardItem.getUrl()!=null)holder.back_url.setText(cardItem.url);
        if(cardItem.getName()!=null)holder.back_name.setText(cardItem.store_name);
        if(cardItem.getBiz_hour()!=null)holder.back_biz.setText(cardItem.biz_hour);
    }
    void onItemClick(View view,int position){
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }
    public static class myViewHolder extends RecyclerView.ViewHolder {

        private View frontLayout;
        private View backLayout;
        private boolean isFront = true;

        private View cardContainer;

        //front_card
        private TextView name;
        private ImageView image;
        //back_card
        private TextView back_address;
        private TextView back_biz;
        private TextView back_cat;
        private TextView back_name;
        private TextView back_holiday;
        private TextView back_url;
        private Button returnButton;

        myViewHolder(View view){
            super(view);
            frontLayout = view.findViewById(R.id.front_card);
            backLayout = view.findViewById(R.id.scroll);
            cardContainer = view.findViewById(R.id.card);
            //front
            name = view.findViewById(R.id.name);
            image = view.findViewById(R.id.image);
            //back
            back_address = view.findViewById(R.id.back_add);
            back_biz = view.findViewById(R.id.back_hour);
            back_name = view.findViewById(R.id.back_name);
            back_holiday = view.findViewById(R.id.back_day);
            back_url = view.findViewById(R.id.back_url);
            returnButton = view.findViewById(R.id.returnButton);
            showFront();
            //onclick
            cardContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    onItemClick(view,pos);
                    if(pos!=RecyclerView.NO_POSITION){
                        flipCard();
                    }
                }
            });
            returnButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        // カードがクリックされたときのアニメーション処理
                        flipCard();
                    }

                }
            });
        }
        private void showFront(){
            frontLayout.setVisibility(View.VISIBLE);
            backLayout.setVisibility(View.GONE);
        }
        private void showBack(){
            frontLayout.setVisibility(View.GONE);
            backLayout.setVisibility(View.VISIBLE);
            backLayout.setScaleX(-1f);
        }
        private void onItemClick(View view,int p){

        }
        private void flipCard(){
            ObjectAnimator flipAnimator = ObjectAnimator.ofFloat(cardContainer, "rotationY", isFront ? 0f : -180f, isFront ? 180f : 0f);
            flipAnimator.setDuration(500);
            flipAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            flipAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    if (!isFront) {
                        showBack();
                    } else {
                        showFront();
                    }
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    // アニメーション終了時に裏表を切り替える
                    if (!isFront) {
                        showBack();
                    } else {
                        showFront();
                    }

                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });

            flipAnimator.start();
            isFront = !isFront;

        }
    }
}
