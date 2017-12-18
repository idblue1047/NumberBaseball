package com.example.administrator.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;


public class Ranking extends AppCompatActivity implements View.OnClickListener{

    public static int game_type = 3;
    public static String play_username = "";
    public static int play_times = 0;
    public static int play_playtimes = 0;

    public static ArrayList<Rank_base> Origins = new ArrayList<Rank_base>();


    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mConditionRef = mDatabase.child("users").child("user1").child("name");


    TextView testView;

    TextView now_calendar;

    ArrayList<Rank_base> list = new ArrayList<Rank_base>(); //입력받은 데이터들을 리사이클러뷰에 출력하기 위한 변수.


    Context context;
    RecyclerView recyclerview_ranking;
    RecyclerViewAdapter2 recyclerViewAdapter;
    RecyclerView.LayoutManager recylerViewLayoutManager;

//    Intent i = getIntent();
    public Button type_a;
    public Button type_b;

    public MediaPlayer ranking_music;
    int music_position;

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ranking_music = MediaPlayer.create(this, R.raw.ranking_music);
        ranking_music.setLooping(true);
        ranking_music.start();//배경음악  재생

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //화면을 세로로 고정. 화면이 가로세로가 바뀔때마다 초기화가 되는데 이 앱에는 회전이 필요없다.

        setContentView(R.layout.activity_ranking);

        testView = (TextView) findViewById(R.id.subject_textview);

        now_calendar = (TextView)findViewById(R.id.calendar_xml);
        Calendar cal = Calendar.getInstance(); // the value to be formatted

        DateFormat formatter4 = DateFormat.getDateInstance(DateFormat.LONG); //XXXX년 XX월 XX일 형태.
        formatter4.setTimeZone(cal.getTimeZone());

        String formatted4 = formatter4.format(cal.getTime());

        GregorianCalendar now = new GregorianCalendar();
        now.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));

        int hour = now.get((Calendar.HOUR_OF_DAY));
        int minute = now.get((Calendar.MINUTE));
        int second = now.get((Calendar.SECOND));

        String time = String.valueOf(hour) + "시 " + String.valueOf(minute) + "분 " + String.valueOf(second) + "초 기준";
        now_calendar.setText(formatted4 +"\n" + time);




        findViewById(R.id.type_a).setOnClickListener(this);
        findViewById(R.id.type_b).setOnClickListener(this);


        context = getApplicationContext();
        recyclerview_ranking = (RecyclerView) findViewById(R.id.recyclerview_ranking);
        recylerViewLayoutManager = new LinearLayoutManager(getBaseContext());

        recyclerview_ranking.setLayoutManager(recylerViewLayoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter2(context, list);
        recyclerview_ranking.setAdapter(recyclerViewAdapter);

        //3구를 끝내고 랭킹으로 오면 3구 랭킹을, 4구는 4구 랭킹을 보여주기 위해 값을 받아옴.
        Intent intent = getIntent();
        game_type = intent.getIntExtra("GAMETYPE", 3);
        play_username = intent.getStringExtra("USERNAME");
        play_times = intent.getIntExtra("TIMES", 0);
        play_playtimes = intent.getIntExtra("PLAYTIMES", 0);

        Button_Select();


        final DatabaseReference testing = FirebaseDatabase.getInstance().getReference("users");
        testing.orderByChild("times").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Log.d("_test", dataSnapshot.getKey() + " , " + dataSnapshot.getValue().toString());

                Rank_base ranktest = dataSnapshot.getValue(Rank_base.class);

                if(ranktest == null) {
                    Log.d("_teststart", "null: " + dataSnapshot.getKey() + " , " + dataSnapshot.getValue().toString());
                    return;
                }

                list.add(ranktest);//time순서로 정렬된 자료들을 list에 저장.
                Log.d("_testend", "null: " + dataSnapshot.getKey() + " , " + dataSnapshot.getValue().toString());


                ///////정렬 테스트 중 //////

                Collections.sort(list, Rank_base.Rank_mulitplessort);


                ///////////정렬 테스트 끝//////

                recyclerViewAdapter.notifyDataSetChanged();


                Origins.clear();
                Origins.addAll(recyclerViewAdapter.getSubjectValues());


                //원래 시작때는 3구 4구 구별 없이 모든 데이터를 뿜어내서 랭킹 화면을 들어가자마자 구별할 수 있도록(기본 3구) 아래 코드를 만든건데
                //지금 생각해도 대체 왜 handler와 timetask를 이용해야 제대로 출력이 된건지 알 수가 없다. 이것들을 사용하지 않으면 아예 어떤 데이터도 안 뜬다.
                //이 바로 위 코드들이 통합 데이터 출력인데도 말이다...
                final Handler mhandler = new Handler();
                TimerTask tt = new TimerTask() {
                    @Override
                    public void run() {
                        mhandler.post(new Runnable() {
                            @Override
                            public void run() {
                                listsetup();
                                ArrayList<Rank_base> arrayList =  recyclerViewAdapter.getSubjectValues();

                                ArrayList<Rank_base> arrayList_match = new ArrayList<>();

                                for(int k=0; k<arrayList.size(); k++) {
                                    if ( arrayList.get(k).type == game_type ) {
                                        arrayList_match.add(arrayList.get(k));
                                    }
                                }
                                recyclerViewAdapter.setSubjectValues(arrayList_match);
                                String tmp = "";
                                recyclerViewAdapter.notifyDataSetChanged();


                            }
                        });
                    }
                };
                Timer timer = new Timer();
                timer.schedule(tt, 0000);



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

    @Override// 버튼 클릭시에 기능 구현
    public void onClick(View v) {
//        if(Origins.isEmpty()) {
//            Origins.addAll(recyclerViewAdapter.getSubjectValues());
//        }
        ArrayList<Rank_base> arrayList = null;
        ArrayList<Rank_base> arrayList_match = null;

        int position_num = 0;
        if(R.id.type_a == v.getId()) {
            game_type = 3;
            type_a.setBackgroundColor(Color.WHITE);
            type_b.setBackgroundColor(Color.GRAY);
            listsetup();
            arrayList =  recyclerViewAdapter.getSubjectValues();

            arrayList_match = new ArrayList<>();

            for(int k=0; k<arrayList.size(); k++) {
                if ( arrayList.get(k).type == 3 ) {
                    arrayList_match.add(arrayList.get(k));
                }
            }

            recyclerViewAdapter.setSubjectValues(arrayList_match);

            String tmp = "";

        } else if(R.id.type_b == v.getId()) {
            game_type = 4;
            listsetup();
            arrayList =  recyclerViewAdapter.getSubjectValues();

            arrayList_match = new ArrayList<>();

            for(int k=0; k<arrayList.size(); k++) {

                if ( arrayList.get(k).type == 4 ) {
                    arrayList_match.add(arrayList.get(k));
                }
            }
            recyclerViewAdapter.setSubjectValues(arrayList_match);
            String tmp = "";
        }
        Button_Select();
        recyclerViewAdapter.notifyDataSetChanged();
    }

    public void onBackPressed()
    {
        ranking_music.stop();
        Intent mainmove = new Intent(this, MainActivity.class);

        startActivity(mainmove);
        finish();
    }
    public void Exit(View v) {
        ranking_music.stop();
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

    public void Button_Select()
    {
        type_a = (Button)findViewById(R.id.type_a);
        type_b = (Button)findViewById(R.id.type_b);

        if (game_type == 3)
        {
            type_a.setBackgroundColor(Color.WHITE);
            type_a.setTextSize(18);
            type_b.setBackgroundColor(Color.GRAY);
            type_b.setTextSize(14);
        }
        else if (game_type == 4)
        {

            type_a.setBackgroundColor(Color.GRAY);
            type_a.setTextSize(14);
            type_b.setBackgroundColor(Color.WHITE);
            type_b.setTextSize(20);
        }
    }

    protected void onStop()
    {
        super.onStop();
        ranking_music.pause();
        music_position = ranking_music.getCurrentPosition();

    }
    public void onResume(){
        ranking_music.seekTo(music_position);
        ranking_music.start();
        super.onResume();
    }

}







