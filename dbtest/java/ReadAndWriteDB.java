package com.example.dbtest;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ReadAndWriteDB {
    private static final String TAG = "ReadAndWriteDB";
    private String your_path = "https://sampledb-6192c-default-rtdb.firebaseio.com/";
    public String text;
    // データベースでデータの読み書きを行うために使用
    public DatabaseReference mDatabase;
    private TextView textView;
    private MainActivity mainActivity;
    private String search_word ;
    private Query query;
    public ReadAndWriteDB(MainActivity mainActivity) {
        this.mDatabase = FirebaseDatabase.getInstance().getReference();
        this.mainActivity = mainActivity;
        this.query = mDatabase.orderByChild("category").equalTo("");
    }

    //db読み込み
    public void addPostEventListener(DatabaseReference mPostReference){
        ValueEventListener postListener = new ValueEventListener() {
            @Override   // 特定のパスにあるコンテンツの静的スナップショットを、イベントの発生時に存在していたとおりに読み取ることができる
            // Listenerがアタッチされたときに1回、また、データ(子も含む)が変更されるたびにコールされる
            public void onDataChange(DataSnapshot dataSnapshot) {
                /**
                 * Snapshot.getValue():そのデータの Java オブジェクト表現が返され、その場所にデータが存在しない場合、null が返される。
                 */
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // データを取得して、必要な処理を行う
                    String address = snapshot.child("address").getValue(String.class);
                    String bizHour = snapshot.child("biz_hour").getValue(String.class);
                    String category = snapshot.child("category").getValue(String.class);

                    // 取得したデータを使って何かしらの処理を行う
                    // ...
                }


            }

            @Override   // 読み取りがキャンセルされた場合に呼び出される
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        mPostReference.addValueEventListener(postListener);
        //選択したデータを取得
        // データを取得する
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // データを取得して処理する
                    String storeName = (String) snapshot.child("store_name").getValue();

                    // 他のデータフィールドも同様に取得できます
                    Log.d("FirebaseData2", "Store Name: " + storeName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // キャンセル時の処理を記述します
            }
        });
    }
    public void getSnapshot(String userId) {
        mDatabase.child("users").child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase1", "Error getting data", task.getException());
                }
                else {

                    Log.d("firebase2", String.valueOf(task.getResult().getValue()));
                }
            }
        });
    }
    private String getUid() {
        return "";
    }
    public DatabaseReference getDatabaseReference() {
        return this.mDatabase;
    }
    public void setSearchWord(String searchWord){
        search_word = searchWord;
        query = mDatabase.orderByChild("category").equalTo(searchWord);
    }

}
