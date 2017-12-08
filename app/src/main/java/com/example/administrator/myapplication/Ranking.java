package com.example.administrator.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class Ranking extends AppCompatActivity implements View.OnClickListener {

    public static int type_number = 3;

    public static ArrayList<Rank_base> Origins = new ArrayList<Rank_base>();


    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mConditionRef = mDatabase.child("users").child("user1").child("name");


    TextView testView;

    ArrayList<Rank_base> list = new ArrayList<Rank_base>(); //입력받은 데이터들을 리사이클러뷰에 출력하기 위한 변수.


    Context context;
    RecyclerView recyclerview_ranking;
    RecyclerViewAdapter2 recyclerViewAdapter;
    RecyclerView.LayoutManager recylerViewLayoutManager;

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        testView = (TextView) findViewById(R.id.subject_textview);

        findViewById(R.id.type_a).setOnClickListener(this);
        findViewById(R.id.type_b).setOnClickListener(this);


        context = getApplicationContext();
        recyclerview_ranking = (RecyclerView) findViewById(R.id.recyclerview_ranking);
        recylerViewLayoutManager = new LinearLayoutManager(getBaseContext());

        recyclerview_ranking.setLayoutManager(recylerViewLayoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter2(context, list);
        recyclerview_ranking.setAdapter(recyclerViewAdapter);


        final DatabaseReference testing = FirebaseDatabase.getInstance().getReference("users");
        testing.orderByChild("times").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Log.d("_test", dataSnapshot.getKey() + " , " + dataSnapshot.getValue().toString());

                Rank_base ranktest = dataSnapshot.getValue(Rank_base.class);

                if(ranktest == null) {
                    Log.d("_test", "null: " + dataSnapshot.getKey() + " , " + dataSnapshot.getValue().toString());
                    return;
                }

                list.add(ranktest);
                recyclerViewAdapter.notifyDataSetChanged();


                Origins.clear();
                Origins.addAll(recyclerViewAdapter.getSubjectValues());

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        //


    }

    @Override// 버튼 클릭시에 기능 구현
    public void onClick(View v) {
//        if(Origins.isEmpty()) {
//            Origins.addAll(recyclerViewAdapter.getSubjectValues());
//        }

        if(R.id.type_a == v.getId()) {
            listsetup();
            ArrayList<Rank_base> arrayList =  recyclerViewAdapter.getSubjectValues();

            ArrayList<Rank_base> arrayList_match = new ArrayList<>();

            for(int k=0; k<arrayList.size(); k++) {

                if ( arrayList.get(k).type == 3 ) {
                    arrayList_match.add(arrayList.get(k));
                }

            }

            recyclerViewAdapter.setSubjectValues(arrayList_match);

            String tmp = "";
        } else if(R.id.type_b == v.getId()) {
            listsetup();
            ArrayList<Rank_base> arrayList =  recyclerViewAdapter.getSubjectValues();

            ArrayList<Rank_base> arrayList_match = new ArrayList<>();

            for(int k=0; k<arrayList.size(); k++) {

                if ( arrayList.get(k).type == 4 ) {
                    arrayList_match.add(arrayList.get(k));
                }
            }
            recyclerViewAdapter.setSubjectValues(arrayList_match);
            String tmp = "";
        }
        recyclerViewAdapter.notifyDataSetChanged();
    }

    public void onBackPressed()
    {
        Intent mainmove = new Intent(this, MainActivity.class);
        startActivity(mainmove);
        finish();
    }
    public void Exit(View v) {
        Intent mainmove = new Intent(this, MainActivity.class);
        startActivity(mainmove);
        finish();
    }

////////////////////////////////////////////////////////
    public void listsetup() //랭킹 화면 초기화하려고 사용중.
    {
        recyclerview_ranking.setLayoutManager(recylerViewLayoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter2(context, Origins); //최초 이벤트시에 받아놓은 타입 구별없는 원본이 저장되었던 데이터(Origins)를 받아옴.
        recyclerview_ranking.setAdapter(recyclerViewAdapter);


        final DatabaseReference testing = FirebaseDatabase.getInstance().getReference("users");
        testing.orderByChild("times").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Rank_base ranktest = dataSnapshot.getValue(Rank_base.class);

                recyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

}






