package com.example.dbtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import com.example.dbtest.databinding.ActivityMainBinding;
import com.google.firebase.FirebaseApp;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;

import java.util.ArrayList;

import java.util.List;


public class MainActivity extends AppCompatActivity implements CardStackListener {
    public List<String> word ;
    private DBViewModel viewModel;


    //リスト表示用フィールド
    /*
    input_dataに読み込んだデータを入れる
    data_setをリストビューに利用
    mainAdapter:recycleViewに利用
    * */
    private List<Post> input_data;
    private List<Post> data_set = new ArrayList<>();


    //RecyclerView.Adapter mainAdapter;
   // private RecyclerView recyclerView;

    /*
        private CardContainer mCardContainer;
    private CardModel cardModel ;
    private SimpleCardStackAdapter adapter;
    *
    * */

    //tinder 実装2
    private ActivityMainBinding binding;
    private CardStackAdapter adapter;
    private CardStackView cardStackView;
    private CardStackLayoutManager cardStackLayoutManager;
    private ImageView reload;
    //スピナー
    private String[] cateItem = {"ラーメン", "うどん", "イタリア", "寿司", "焼肉"}; //カテゴリーアイテム


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);


        //24/01/16追加
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //DBViewModelのインスタンスを作成
        viewModel = new ViewModelProvider(this).get(DBViewModel.class);
        // スピナ-
        Spinner spnCate = findViewById(R.id.spn_cate);
        ArrayAdapter<String> sp_adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                cateItem
        );
        spnCate.setAdapter(sp_adapter);
        spnCate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //　アイテムが選択された時
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner spnCate = (Spinner)parent;
                String spnItem = (String)spnCate.getSelectedItem(); //スピナ―選択時のアイテム格納
                String Cate;    //カテゴリーを保存

                // 選択されているアイテムからカテゴリーを取得
                if (spnItem == "ラーメン") Cate="A";
                else if (spnItem == "うどん") Cate="B";
                else if (spnItem == "イタリア") Cate="C";
                else if (spnItem == "寿司") Cate="D";
                else if (spnItem == "焼肉") Cate="E";
                else Cate="A";  //これは無くても良い？

                viewModel.setSearchWord(Cate);
                viewModel.reloadRDB();
            }
            //　アイテムが選択されなかった
            public void onNothingSelected(AdapterView<?> parent) {}

            //==== ここまでスピナ―処理 ====//
        });
        reload = findViewById(R.id.reloadImage);
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //インスタンスviewModelのフィールドmyDataを保持する
        viewModel.getMyData().observe(this, new Observer<String>() {
            //データの変更が起きたとき 今回はインスタンスviewModelのフィールドmydata(String)が変化起きたとき
            //onChange()の引数 String dataは新しく保持する値を指す
            @Override
            public void onChanged(String data) {
                // データが変更されたときの処理
                word = viewModel.getWord();
                input_data = viewModel.getData();
                loadData();
                input_data.clear();
                //mainAdapter.notifyDataSetChanged();
                adapter.notifyDataSetChanged();
            }
        });
        //test用

        //ここからリストビュー
        /*
        recyclerView = findViewById(R.id.content);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);

        //mainAdapterの設定
        mainAdapter = new ListViewAdapter(data_set){
            @Override
            public void onItemClick(View view,int p) {
                final int position = p;
                //positionはクリックした行番号を返す。
            }
        };
                //tinder
        context = this;
       // mCardContainer = (CardContainer) findViewById(R.id.layoutview);
        adapter = new SimpleCardStackAdapter(context);
        mCardContainer.setOnCardDismissedListener(new CardContainer.OnCardDismissedListener() {
            @Override
            public void onLike(CardModel model) {
                Log.i("simple1","fuck"+model.getTitle());
            }
            @Override
            public void onDislike(CardModel model) {
                Log.i("simple2","fuck"+model.getTitle());
            }
        });
        adapter.setOnCardClickListener(new SimpleCardStackAdapter.OnCardClickListener() {
            @Override
            public void onCardClick(CardModel model) {
                String title = model.getTitle();
                Log.i(" Cards", "I am pressing the card with title: " + title);
                // ここにカードがクリックされたときの処理を記述します
            }
        });
        mCardContainer.setAdapter(adapter);
        recyclerView.setAdapter(mainAdapter);
         */
        adapter = new CardStackAdapter(data_set);
        cardStackView = findViewById(R.id.card_stack);
        setupCardStackView();
        cardStackView.setAdapter(adapter);

    }
    public Drawable image_load(String imgName){
        int imgId=0;
        imgId = getResources().getIdentifier(imgName,"drawable",getPackageName());
        if(imgId==0)imgId = getResources().getIdentifier("green","drawable",getPackageName());
        Drawable d= ContextCompat.getDrawable(this,imgId);
        return ContextCompat.getDrawable(this,imgId);
    }
    public void loadData(){
        data_set.clear();
        for(int i= 0; i<input_data.size();i++){
            Post pt = input_data.get(i);
            String a = "a_" +String.valueOf(input_data.get(i).store_id)+"_cropped";
            pt.image = image_load(a);
            data_set.add(pt);
        }
    }
    /*
    *     private void loadCard(String title,String des,String id){
        Resources r = getResources();
        cardModel = new CardModel(title, des, r.getDrawable(R.drawable.a_10000_cropped));
        adapter.add(cardModel);
    }
    private void loadCards(){
        Resources r = getResources();
        int cardCount = Math.min(3, input_data.size()); // 最初の5枚のカードだけを表示
        for (int i = 0; i < cardCount; i++) {
            Post pt = input_data.get(i);
            String imgName = "a_" + pt.store_id + "_cropped";
            int imgId = getResources().getIdentifier(imgName, "drawable", getPackageName());
            if (imgId == 0) imgId = getResources().getIdentifier("green", "drawable", getPackageName());
            cardModel = new CardModel(pt.store_name, pt.url, r.getDrawable(imgId));
            adapter.add(cardModel);
        }
    }
    * */
    private void setupCardStackView() {
        cardStackLayoutManager = new CardStackLayoutManager(this, this);
        cardStackLayoutManager.setTranslationInterval(8.0f);
        cardStackLayoutManager.setSwipeThreshold(0.05f);
        cardStackLayoutManager.setStackFrom(StackFrom.Right);
        cardStackLayoutManager.setDirections(Direction.FREEDOM);

        cardStackView.setLayoutManager(cardStackLayoutManager);
        cardStackView.getWidth();
        adapter = new CardStackAdapter(data_set);
        cardStackView.setAdapter(adapter);
    }
    @Override
    public void onCardDragging(Direction direction, float ratio) {
        // カードがドラッグされているときの処理
    }

    @Override
    public void onCardSwiped(Direction direction) {
        // カードがスワイプされたときの処理
        String name = "first";
        int currentPosition = cardStackLayoutManager.getTopPosition() - 1;
        if (currentPosition >= 0 && currentPosition < adapter.getItemCount()) {
            Post card = adapter.cardList.get(currentPosition);
            name = card.getName();
        }
        switch(direction){
            case Top:
                Log.d("MainActivity","Like="+ name);
                break;
            case Bottom:
                Log.d("MainActivity","disLike="+ name);
                break;
            case Left:
                Log.d("MainActivity","left+back from ");
                if (currentPosition >= 0 && currentPosition < adapter.getItemCount()) {
                    Post card = adapter.cardList.get(currentPosition);
                    input_data.add(card);
                }
                break;
            case Right:
                Log.d("MainActivity","right+back from ");
                if (currentPosition >= 0 && currentPosition < adapter.getItemCount()) {
                    Post card = adapter.cardList.get(currentPosition);
                    input_data.add(card);
                }
                break;
        }
    }

    @Override
    public void onCardRewound() {
        // カードが元に戻されたときの処理
    }

    @Override
    public void onCardCanceled() {
        // カードのスワイプがキャンセルされたときの処理
    }

    @Override
    public void onCardAppeared(View view, int position) {
        // 新しいカードが表示されたときの処理
        // textViewを使って必要な処理を行う
    }

    @Override
    public void onCardDisappeared(View view, int position) {
        // カードが画面から消えたときの処理
        if(data_set.size()-1 == position){
            Log.d("main","cardなし");

        }


    }


}