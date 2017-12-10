package com.example.administrator.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class RecyclerViewAdapter2 extends RecyclerView.Adapter<RecyclerViewAdapter2.ViewHolder>{

    ArrayList<Rank_base> SubjectValues = new ArrayList<Rank_base>();
    Context context;
    View view1;
    ViewHolder viewHolder1;

    public ArrayList<Rank_base> getSubjectValues() {
        return SubjectValues;
    }

    public void setSubjectValues(ArrayList<Rank_base> subjectValues) {
        SubjectValues = subjectValues;
    }

    public RecyclerViewAdapter2(Context context1, ArrayList<Rank_base> SubjectValues1){

        SubjectValues = SubjectValues1;
        context = context1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public TextView rank_num;
        public TextView username;
        public TextView times;
        public TextView playtimes;


        public ViewHolder(View v){
            super(v);
//            textView = (TextView)v.findViewById(R.id.subject_textview);
            rank_num = (TextView)v.findViewById(R.id.rank_num_xml);
            username = (TextView)v.findViewById(R.id.username_xml);
            times = (TextView)v.findViewById(R.id.times_xml);
            playtimes = (TextView)v.findViewById(R.id.playtimes_xml);
        }
    }

    @Override
    public RecyclerViewAdapter2.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        view1 = LayoutInflater.from(context).inflate(R.layout.recyclerview_rankitems,parent,false);
        viewHolder1 = new ViewHolder(view1);
        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){

        Rank_base rank_base = SubjectValues.get(position);

        /*
        if ( Ranking.type_number && rank_base.type == 3 ) {
            msg = "Type: " + rank_base.type + ", " + rank_base.username + " was " + rank_base.times + " times, " + rank_base.play_times ;
        } else {
            msg = "Type: " + rank_base.type + ", " + rank_base.username + " was " + rank_base.times + " times, " + rank_base.play_times ;
        }
        */
        /*
        if ( Ranking.type_number == 3 && rank_base.type == 3 ) {
            msg = "Type: " + rank_base.type + ", " + rank_base.username + " was " + rank_base.times + " times, " + rank_base.play_times ;
            Log.d("_test", "전역: " + Ranking.type_number + " , 3");
        } else if ( Ranking.type_number == 4 && rank_base.type == 4 ) {
            msg = "Type: " + rank_base.type + ", " + rank_base.username + " was " + rank_base.times + " times, " + rank_base.play_times ;
            Log.d("_test", "전역: " + Ranking.type_number + " , 4");
        } else if ( Ranking.type_number == 1 ){
            msg = "Type: " + rank_base.type + ", " + rank_base.username + " was " + rank_base.times + " times, " + rank_base.play_times ;
            Log.d("_test", "전역: " + Ranking.type_number + " , 1");
        }
        */
//        Log.d("_test", "전역: " + Ranking.type_number + " , 3");
        String msg_rank = position+1+ "위";
        String msg_username = rank_base.username;
        String msg_times = rank_base.times + "번" ;
        String msg_playtimes = String.valueOf(rank_base.play_times / 60) + "분 " + String.valueOf(rank_base.play_times % 60) + "초" ;

//        if(msg_rank.equals("1위")) holder.rank_num.setTextColor(Color.YELLOW);
        //게임 액티비티에서 랭킹 액티비티로 Intent로 넘긴 username이 일치하면 색깔로 표시해주기
        if(msg_username.equals(Ranking.play_username) && msg_times.equals(Ranking.play_times + "번") && msg_playtimes.equals(String.valueOf(Ranking.play_playtimes / 60) + "분 " + String.valueOf(Ranking.play_playtimes % 60) + "초")){
            //Log.d("_test",msg_username + "<--> " +Ranking.play_username + ",    " + msg_times + "<---> " + Ranking.play_times + ",  " + msg_playtimes + "<-->  " + Ranking.play_playtimes);
            holder.rank_num.setTextColor(Color.GREEN);
            holder.username.setTextColor(Color.GREEN);
            holder.times.setTextColor(Color.GREEN);
            holder.playtimes.setTextColor(Color.GREEN);

        }
//        Log.d("_test",msg_username + "<--> " +Ranking.play_username + ",    " + msg_times + "<---> " + Ranking.play_times + ",  " + msg_playtimes + "<-->  " + Ranking.play_playtimes);
        holder.rank_num.setText(msg_rank);
        holder.username.setText(msg_username);
        holder.times.setText(msg_times);
        holder.playtimes.setText(msg_playtimes);

//        String msg = "";
//        msg = position+1+"위" + rank_base.username + "  " + rank_base.times + "번" + String.valueOf(rank_base.play_times / 60) + "분 " + String.valueOf(rank_base.play_times % 60) + "초" ;
//        holder.textView.setText(msg);
    }

    @Override
    public int getItemCount(){
        return SubjectValues.size();
    }
}