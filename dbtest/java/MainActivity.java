package com.example.dbtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    public List<String> word ;
    public Boolean CheckRDB = Boolean.FALSE;
    private DBViewModel viewModel;

    //リスト表示用フィールド
    /*
    input_dataに読み込んだデータを入れる
    data_setをリストビューに利用
    mainAdapter:recycleViewに利用
    * */
    private List<Post> input_data;
    private List<Post> data_set = new ArrayList<>();
    RecyclerView.Adapter mainAdapter;
    private RecyclerView recyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.text);
        Button button1 = findViewById(R.id.Bbutton);
        Button button2 = findViewById(R.id.Cbutton);
        //DBViewModelのインスタンスを作成
        viewModel = new ViewModelProvider(this).get(DBViewModel.class);


        //ボタンがクリックされたとき,サーチワードをBとしてRDBへ検索を実行
        button1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                viewModel.setSearchWord("B");
                viewModel.reloadRDB();
            }
        });
        button2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                viewModel.setSearchWord("C");
                viewModel.reloadRDB();
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
                textView.setText(data); // データをUIにセットする
                Log.d("main",input_data.get(0).store_name);
                loadData();
                mainAdapter.notifyDataSetChanged();
            }
        });
        //test用
        Post pt1 = new Post();
        pt1.store_name = "test1";
        pt1.image = image_load("green");
        data_set.add(pt1);

        //ここからリストビュー
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
        recyclerView.setAdapter(mainAdapter);

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
            Log.d("mainAct", pt.image.toString());
            data_set.add(pt);
        }
    }
}