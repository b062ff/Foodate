package com.example.dbtest;

import android.graphics.drawable.Drawable;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Post {

    public String address;
    public String biz_hour;
    public int category;
    public String holiday;
    public String store_id;
    public String store_name;
    public String url;

    public Drawable image;

    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Post(String address,String biz_hour,int category,String holiday,
                String store_id,String store_name,String url) {
        this.address = address;
        this.biz_hour = biz_hour;
        this.category = category;
        this.holiday = holiday;
        this.store_id = store_id;
        this.store_name = store_name;
        this.url = url;
    }
    public void setImage(Drawable drawable){
        this.image = drawable;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("address", address);
        result.put("biz_hour", biz_hour);
        result.put("category", category);
        result.put("holiday", holiday);
        result.put("store_id", store_id);
        result.put("store_name", store_name);
        result.put("url",url);

        return result;
    }
}