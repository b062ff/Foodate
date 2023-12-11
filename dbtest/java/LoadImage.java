package com.example.dbtest;

import android.graphics.drawable.Drawable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class LoadImage extends AppCompatActivity {
    public Drawable load(String imgName){
        int imgId=0;
        imgId = getResources().getIdentifier(imgName,"drawable",getPackageName());
        if(imgId==0)imgId = getResources().getIdentifier("noimage","drawable",getPackageName());
        Drawable d= ContextCompat.getDrawable(this,imgId);
        return ContextCompat.getDrawable(this,imgId);
    }
}
