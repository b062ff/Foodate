package com.example.tindertest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.tindertest.model.CardModel;
import com.example.tindertest.view.CardContainer;
import com.example.tindertest.view.CardStackAdapter;
import com.example.tindertest.view.SimpleCardStackAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private CardContainer mCardContainer;
    int p =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCardContainer = (CardContainer) findViewById(R.id.layoutview);

        Resources r = getResources();

        SimpleCardStackAdapter adapter = new SimpleCardStackAdapter(this);

        //cardmodelsセット
        List<CardModel> cardModelList = new ArrayList<>();
        CardModel c1 = new CardModel("Title1", "Description goes here", r.getDrawable(R.drawable.picture1));
        CardModel c2 = new CardModel("Title2", "Description goes here", r.getDrawable(R.drawable.picture1));
        CardModel c3 = new CardModel("Title3", "Description goes here", r.getDrawable(R.drawable.picture1));

        adapter.setOnCardClickListener(new SimpleCardStackAdapter.OnCardClickListener() {
            @Override
            public void onCardClick(CardModel model) {
                String title = model.getTitle();
                Log.i(" Cards", "I am pressing the card with title: " + title);
                // ここにカードがクリックされたときの処理を記述します
            }
        });

        adapter.setOnCardDismissedListener(new SimpleCardStackAdapter.OnCardDismissedListener() {
            @Override
            public void onLike(CardModel model) {
                Log.i("Swipeable Cards", "I like the card: " + model.getTitle());
            }

            @Override
            public void onDislike(CardModel model) {
                Log.i("Swipeable Cards", "I dislike the card: " + model.getTitle());
            }
        });

        adapter.add(c1);
        adapter.add(c2);
        adapter.add(c3);


        mCardContainer.setAdapter(adapter);
    }

}