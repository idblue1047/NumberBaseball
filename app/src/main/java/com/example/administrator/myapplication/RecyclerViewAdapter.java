package com.example.administrator.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    ArrayList<DataModel> SubjectValues = new ArrayList<>();
    Context context;
    View view1;
    ViewHolder viewHolder1;
    TextView textView;


//    public void add(DataModel text)
//    {
//        SubjectValues.add(text);
//        notifyDataSetChanged();
//    }


    public RecyclerViewAdapter(Context context1, ArrayList<DataModel> SubjectValues1){

        SubjectValues = SubjectValues1;
        context = context1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textView;
        public ViewHolder(View v){
            super(v);
            textView = (TextView)v.findViewById(R.id.subject_textview);
        }
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        view1 = LayoutInflater.from(context).inflate(R.layout.recyclerview_items,parent,false);
        viewHolder1 = new ViewHolder(view1);
        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        DataModel item = SubjectValues.get(position);
        holder.textView.setText(item.getText());
    }

    @Override
    public int getItemCount(){
        return SubjectValues.size();
    }
}