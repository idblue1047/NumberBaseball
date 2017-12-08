package com.example.administrator.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.InputFilter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/*
 * 숫자야구 게임 시작(숫자 3개 모드)
 */
public class gamestart_3 extends AppCompatActivity {
    public int startcounter = 0; //시간을 재기 위해서 넣음
    public String Stop;
    public int dark;
    public int a = 0;
    public int first, second, third = 0;
    public TextView number_first;
    public TextView number_second;
    public TextView number_third;


    int input_number_first;
    int input_number_second;
    int input_number_third;

    Context context;
    RecyclerView recyclerView;
    RelativeLayout relativeLayout;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recylerViewLayoutManager;
//    String[] subjects = {};

    public int game_type = 3;//이 클래스의 게임은 숫자 3개를 이용하는 숫자야구다.

    public boolean menu_on = false;

    ArrayList<DataModel> list = new ArrayList<DataModel>(); //입력받은 데이터들을 리사이클러뷰에 출력하기 위한 변수.




    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {//최초로 화면이 나올 때 한 번 실행
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamestart_3);

        number_first = (TextView) findViewById(R.id.Text_First);
        number_second = (TextView) findViewById(R.id.Text_Second);
        number_third = (TextView) findViewById(R.id.Text_Third);

//        for(int i = 0; i<subjects.length;i++)
//        {
//            DataModel data = new DataModel(subjects[i]);
//            list.add(data); //add - 배열 마지막에 원소 추가
//        }
        ////////////////////////////////////2열로 입력된 데이터들 표기하기///////////////////////////////////


        context = getApplicationContext();
        relativeLayout = (RelativeLayout) findViewById(R.id.RelativeLayout_xml);
        recyclerView = (RecyclerView) findViewById(R.id.RecyclerView_xml);
        recylerViewLayoutManager = new LinearLayoutManager(context);

        recyclerView.setLayoutManager(recylerViewLayoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(context, list);
        recyclerView.setAdapter(recyclerViewAdapter);

        //리사이클러뷰를 2열로 만듬.
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ConstraintLayout end_menu = (ConstraintLayout)findViewById(R.id.endmenu_xml);
        end_menu.setVisibility(View.INVISIBLE);

        //////////////////////////////시작 3, 2, 1 시작 타이머/////////////////////////////////////////////////
        final Handler mHandler = new Handler();
        final ImageView startcountimage = (ImageView) findViewById(R.id.start_Count_Image);
        final ConstraintLayout screen_dark = (ConstraintLayout) findViewById(R.id.screen_dark);
//        screen_dark.bringToFront();
//        startcountimage.bringToFront();

        final TextView TextTimer = (TextView) findViewById(R.id.timer_1);

        Log.v("알림", "액티비티3 시작");
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        int gametimer = startcounter - 4;
                        if (startcounter == 0)
                            startcountimage.setImageResource(R.drawable.numberimage3);
                        else if (startcounter == 1)
                            startcountimage.setImageResource(R.drawable.numberimage2);
                        else if (startcounter == 2)
                            startcountimage.setImageResource(R.drawable.numberimage1);
                        else if (startcounter == 3)
                            startcountimage.setImageResource(R.drawable.start);
                        else if (startcounter == 4) {
                            screen_dark.setVisibility(View.INVISIBLE);
                            startcountimage.setVisibility(View.INVISIBLE);
                            TextTimer.setVisibility(View.VISIBLE);
                        }
                        if (startcounter < 4) {
                            TextTimer.setText("0분 0초");
                        } else
                            TextTimer.setText(String.valueOf(gametimer / 60) + "분 " + String.valueOf(gametimer % 60) + "초");
                        //게임 시작 후 경과시간 출력

                        startcounter++;
                        Log.v("알림", "타이머 종료");
                    }
                });
            }
        };
        Timer timer = new Timer();
        timer.schedule(tt, 0, 1000);

        //////////////////////////////시작 타이머 끝/////////////////////////////////////////////////////////

        ///////////////////////////////숫자야구 본격 함수/////////////////////////////////////////////////////
        Log.v("알림", "랜덤값 생성");
        final int rn3_1 = (int) (Math.random() * 10);//0~9
        int rn3_2 = (int) (Math.random() * 10);
        while (true) {
            if (rn3_2 == rn3_1) rn3_2 = (int) (Math.random() * 10);
            else break;
        }
        int rn3_3 = (int) (Math.random() * 10);
        while (true) {
            if (rn3_3 == rn3_1 || rn3_3 == rn3_2) rn3_3 = (int) (Math.random() * 9);
            else break;
        }
        final String rn3_1s = String.valueOf(rn3_1);//TextView에 바로 int를 못 넣어서 String으로 변환
        final String rn3_2s = String.valueOf(rn3_2);
        final String rn3_3s = String.valueOf(rn3_3);
        TextView test1 = (TextView) findViewById(R.id.test1234);
        test1.setText(rn3_1s + rn3_2s + rn3_3s);
        //rn3_1 + rn3_2 + rn3_3
        Log.v("알림", "랜덤값 출력");


        /////////////////////숫자버튼 클릭시 텍스트뷰에 입력////////////////////////////////////
        Button btn_0 = (Button) findViewById(R.id.button_0);
        Button btn_1 = (Button) findViewById(R.id.button_1);
        Button btn_2 = (Button) findViewById(R.id.button_2);
        Button btn_3 = (Button) findViewById(R.id.button_3);
        Button btn_4 = (Button) findViewById(R.id.button_4);
        Button btn_5 = (Button) findViewById(R.id.button_5);
        Button btn_6 = (Button) findViewById(R.id.button_6);
        Button btn_7 = (Button) findViewById(R.id.button_7);
        Button btn_8 = (Button) findViewById(R.id.button_8);
        Button btn_9 = (Button) findViewById(R.id.button_9);

        View.OnClickListener btn_number = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (number_first.getText().toString().equals("?"))
                    number_first.setText(((Button) v).getText().toString());
                else if (number_second.getText().toString().equals("?"))
                    if(((Button)v).getText().toString() == number_first.getText().toString()){
                        Toast.makeText(gamestart_3.this, "중복번호는 사용할 수 없습니다.", Toast.LENGTH_SHORT).show();
                    }
                    else number_second.setText(((Button) v).getText().toString());
                else if (number_third.getText().toString().equals("?"))
                    if(((Button)v).getText().toString() == number_first.getText().toString() || ((Button)v).getText().toString() == number_second.getText().toString()){
                        Toast.makeText(gamestart_3.this, "중복번호는 사용할 수 없습니다.", Toast.LENGTH_SHORT).show();
                    }
                    else number_third.setText(((Button) v).getText().toString());
            }
        };
        btn_0.setOnClickListener(btn_number);
        btn_1.setOnClickListener(btn_number);
        btn_2.setOnClickListener(btn_number);
        btn_3.setOnClickListener(btn_number);
        btn_4.setOnClickListener(btn_number);
        btn_5.setOnClickListener(btn_number);
        btn_6.setOnClickListener(btn_number);
        btn_7.setOnClickListener(btn_number);
        btn_8.setOnClickListener(btn_number);
        btn_9.setOnClickListener(btn_number);

        /////////////////////////////////////////////////////////////////////////////////////////////

        //입력 버튼 클릭시 리사이클러뷰에 입력한 정보들을 출력
        Button btn_input = (Button) findViewById(R.id.button_input);

        final int[] input_num = {0};
        View.OnClickListener datainput = new View.OnClickListener() { //입력 버튼 클릭시 사용할 기능들
            @Override
            public void onClick(View v) { //////  입력 버튼 클릭시의 기능////////////
                //현재 기능이 1, 2번쨰 숫자가 채워져야 3번째까지 입력이 가능하므로 3번째가 비어있지 않으면 모든 숫자가 채워졌다는 의미.
                if(number_third.getText().toString().equals("?"))
                    Toast.makeText(gamestart_3.this, "모든 번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                else {
                    input_num[0] = input_num[0] + 1;

                    String input_first = number_first.getText().toString();
                    String input_second = number_second.getText().toString();
                    String input_third = number_third.getText().toString();

                    int strike = 0;
                    int ball = 0;

                    // == 는 주소값을 비교하므로 사용할 수 없고 equals을 쓰자.
                    if (rn3_1s.equals(input_first)) strike++;
                    else if (rn3_2s.equals(input_first) || rn3_3s.equals(input_first)) ball++;

                    if (rn3_2s.equals(input_second)) strike++;
                    else if (rn3_1s.equals(input_second) || rn3_3s.equals(input_second)) ball++;
                    if (rn3_3s.equals(input_third)) strike++;
                    else if (rn3_1s.equals(input_third) || rn3_2s.equals(input_third)) ball++;

                    String strike_s = String.valueOf(strike);
                    String ball_s = String.valueOf(ball);


                    DataModel tesing = new DataModel(Html.fromHtml(String.valueOf(input_num[0]) + "회 "
                            + input_first + input_second + input_third + " " + String.valueOf(strike)
                            + "<font color = 'blue'>S</font> " + String.valueOf(ball) + "<font color = 'red'>B</font>"));
                    list.add(tesing);

                    /////////////////////////////모두 스트라이크가 되어서 게임을 종료/////////////////////////
                    if(strike == 3) {//게임 종료 후 메뉴 출력. 랭킹 등록, 나가기, 새로 시작하기 등을 제공.
                        screen_dark.setVisibility(View.VISIBLE);
                        ConstraintLayout menu = (ConstraintLayout) findViewById(R.id.endmenu_xml);
                        menu.setVisibility(View.VISIBLE);
                        menu.bringToFront();

                        ConstraintLayout abcde = (ConstraintLayout)findViewById((R.id.Start_image_admin));
                        abcde.bringChildToFront(menu);

                        final EditText nameedit = (EditText)findViewById(R.id.name_edit);
                        nameedit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                            @Override
                            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                                boolean handled = false;
                                if (actionId == EditorInfo.IME_ACTION_SEND)
                                {
                                    handled = true;
                                }
                                return handled;
                            }
                        });

                        InputFilter[] FilterArray = new InputFilter[1];//EditText에 들어갈 수 있는 글자수를 7글자로 제한
                        FilterArray[0] = new InputFilter.LengthFilter(7);
                        nameedit.setFilters(FilterArray);
                        TextView number_times = (TextView)findViewById(R.id.number_times);
                        number_times.setText("시도 횟수: " + input_num[0] + "회");
                        final int gameover_times = input_num[0];
                        TextView playtimes = (TextView)findViewById((R.id.play_time_xml));
                        final int gameover_playtimes = startcounter - 4;
                        playtimes.setText("걸린 시간: " + String.valueOf(gameover_playtimes / 60) + "분 " + String.valueOf(gameover_playtimes % 60) + "초");


                        Button ranking_input = (Button)findViewById(R.id.ranking3_input_button);
                        View.OnClickListener rankinput = new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String username = nameedit.getText().toString();
                                writeNewUser(game_type, username, gameover_times, gameover_playtimes);
                                rank_move();
                            }
                        };
                        ranking_input.setOnClickListener(rankinput);




                    }

                    recyclerViewAdapter.notifyDataSetChanged();
                    number_first.setText("?");
                    number_second.setText("?");
                    number_third.setText("?");

                    //데이터 입력시 자동으로 스크롤이 가장 마지막으로 가도록 함.
                    recyclerView.scrollToPosition(list.size() - 1);
                }
            }
        };
        btn_input.setOnClickListener(datainput);



        Button menu_button = (Button)findViewById(R.id.button_menu);
        ////////////////////////////onCreate 종료////////////////////////////////////////////////
    }

    public void onBackPressed()//메뉴 화면 만들고 누르면 뜨게 하자.
    {
        if (startcounter < 5) ;//게임 시작 카운트 중에는 뒤로가기 버튼을 먹통으로 만듬.
        else {
            //  ////////////////이 부분은 메뉴를 띄우게 하고 나가기 버튼에다가 복붙하면 될 듯
            Intent mainmove = new Intent(this, MainActivity.class);
            startActivity(mainmove);
            finish();
        }
    }


//    public void Click_Input(View v){
//        input_number_first = Integer.parseInt(number_first.getText().toString());//String을 int로 변환
//        input_number_second = Integer.parseInt(number_second.getText().toString());
//        input_number_third = Integer.parseInt(number_third.getText().toString());
//        RecyclerViewAdapter.notifyDataSetChanged();
//        DataModel tesing = new DataModel("test 해보고 있는 중.");
//        list.add(tesing);
//
//    }

    public void Click_Delete(View v)//텍스트뷰에 있는 글자들을 모두 초기 상태로 돌림.
    {
        number_first.setText("?");
        number_second.setText("?");
        number_third.setText("?");
    }
    public void game3_move(View v)
    {
        Intent game3move = new Intent(this, gamestart_3.class);
        startActivity(game3move);
        finish();
    }
    public void exit(View v){
        Intent main_move = new Intent(this, MainActivity.class);
        startActivity(main_move);
        finish();
    }
    public void menu_on(View v)
    {

    }
    private void writeNewUser(int type, String name, int times, int play_times){
        Rank_base rank = new Rank_base(type, name, times, play_times);
        mDatabase.child("users").push().setValue(rank); // push() - 시간 순서에 따라 임의의 키 값을 생성한다.
    }
    public void rank_move()
    {
        Intent rankmove = new Intent(this, Ranking.class);
        startActivity(rankmove);
        finish();
    }

}

