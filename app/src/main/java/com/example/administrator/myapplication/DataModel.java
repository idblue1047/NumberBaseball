package com.example.administrator.myapplication;

import android.text.Spanned;

/**
 * Created by Administrator on 2017-12-03.
 */

public class DataModel {
    public Spanned text;



    public DataModel(Spanned t)
    {
        this.text=t;
    }
    public Spanned getText()
    {
        return text;
    }
}
