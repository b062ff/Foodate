package com.example.dbtest;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DBViewModel extends ViewModel {

    private String search_word = "A";
    private Query query;
    private MutableLiveData<String> myData;

    private List<String> word = new ArrayList<String>();
    private List<Post> data = new ArrayList();

    public LiveData<String> getMyData() {
        if(myData == null){
            myData = new MutableLiveData<>();
            loadDataFromFirebase();
        }
        return myData;
    }
    private void loadDataFromFirebase(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        query = databaseReference.orderByChild("category").equalTo(search_word);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //queryオブジェクトで取得したデータの各行からデータを取得

                word.clear();
                data.clear();
                String store_name = null;
                String store_id = null;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //その行の列store_nameの値を取得
                    Post pt = new Post();
                    store_name = (String) snapshot.child("store_name").getValue();
                    Long value =  (Long)snapshot.child("store_id").getValue();
                    store_id = String.valueOf(value);
                    pt.store_name = store_name;
                    pt.store_id =store_id;
                    // 確認
                    Log.d("FirebaseData2", "Store Name: " + store_name);
                    if(store_name!=null)word.add(store_name);
                    if(pt!=null)data.add(pt);
                }

                myData.setValue(store_name); // LiveDataにデータをセット

                //現状最後の行しか表示しないので注意
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // キャンセル時の処理を記述
            }
        });

    }
    public void setSearchWord(String searchWord){
        search_word = searchWord;
    }
    public void reloadRDB(){
        loadDataFromFirebase();
        //MainActivityなどで利用するため
    }
    public List<String> getWord(){
        return word;
    }
    public List<Post> getData(){
        return data;
    }
}
