package com.example.administrator.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
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
public class gamestart_4 extends AppCompatActivity {
    public int startcounter = 0; //시간을 재기 위해서 넣음
    public double counter2 = 0; //타이머 동작 때 글자를 빠르게 반짝이게 하려고 사용
    public String Stop;
    public int dark;
    public int a = 0;
    public int first, second, third = 0;
    public TextView number_first;
    public TextView number_second;
    public TextView number_third;
    public TextView number_fourth;


    public Button btn_input;

    int input_number_first;
    int input_number_second;
    int input_number_third;
    int input_number_fourth;

    Context context;
    RecyclerView recyclerView;
    RelativeLayout relativeLayout;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recylerViewLayoutManager;
    String[] subjects = {};


    public Timer timer = new Timer();
    public Timer timer2 = new Timer();

    public TimerTask tt;

    public int gametimer = startcounter - 4;//게임 시작할 때 3,2,1 카운터 세는 시간 뺌.

    public int game_type = 4;//이 클래스의 게임은 숫자 3개를 이용하는 숫자야구다.

    public boolean menu_on = false;

    public boolean clear = false;

    ArrayList<DataModel> list = new ArrayList<DataModel>(); //입력받은 데이터들을 리사이클러뷰에 출력하기 위한 변수.

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    Handler mHandler = new Handler();

    MediaPlayer main4_music;
    //4구 음악. https://freesound.org/people/ShadyDave/sounds/325611/

    int music_position = 0; //홈버튼 등으로 나갔을 때 음악재생을 정지했다가 돌아올때 정지한 부분부터 시작하기 위해 넣은 변수.


    public SoundPool deletesound_pool;
    public int btndeletesound;

    public int streamId = 0;

    public ConstraintLayout screen_dark;
    @Override
    protected void onCreate(Bundle savedInstanceState) {//최초로 화면이 나올 때 한 번 실행
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamestart_4);

        main4_music = MediaPlayer.create(this, R.raw.main4_music);
        main4_music.setLooping(true);
        main4_music.start();//배경음악  재생

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //화면을 세로로 고정. 화면이 가로세로가 바뀔때마다 초기화가 되는데 이 앱에는 회전이 필요없다.

        number_first = (TextView) findViewById(R.id.Text_First);
        number_second = (TextView) findViewById(R.id.Text_Second);
        number_third = (TextView) findViewById(R.id.Text_Third);
        number_fourth = (TextView) findViewById(R.id.Text_Fourth);

//        for(int i = 0; i<subjects.length;i++)
//        {
//            DataModel data = new DataModel(subjects[i]);
//            list.add(data); //add - 배열 마지막에 원소 추가
//        }
        final SoundPool btnsound_pool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);// maxStreams, streamType, srcQuality
        final int btnclicksound = btnsound_pool.load(this, R.raw.btn_click, 1); //일반 버튼 클릭 효과음

        final SoundPool btninputsound_pool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);// maxStreams, streamType, srcQuality
        final int btninputsound = btninputsound_pool.load(this, R.raw.btn_input_sound, 1);//입력 버튼 효과음

        deletesound_pool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        btndeletesound = deletesound_pool.load(this, R.raw.delete_sound, 1);

        final SoundPool clearsound_pool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        final int clearsound =clearsound_pool.load(this, R.raw.clear_sound,1);
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
        ConstraintLayout end_menu = (ConstraintLayout) findViewById(R.id.endmenu_xml);
        end_menu.setVisibility(View.INVISIBLE);

        //////////////////////////////시작 3, 2, 1 시작 타이머 및 게임 플레이 시간/////////////////////////////////////////////////

        final ImageView startcountimage = (ImageView) findViewById(R.id.start_Count_Image);

        screen_dark = (ConstraintLayout) findViewById(R.id.screen_dark);
        screen_dark.bringToFront();
        startcountimage.bringToFront();

        final TextView TextTimer = (TextView) findViewById(R.id.timer_1);


       timer_use();


        //////////////////////////////시작 타이머 끝/////////////////////////////////////////////////////////

        ///////////////////////////////숫자야구 본격 함수/////////////////////////////////////////////////////
        Log.v("알림", "랜덤값 생성");
        final int rn4_1 = (int) (Math.random() * 10);//0~9
        int rn4_2 = (int) (Math.random() * 10);
        while (true)

        {
            if (rn4_2 == rn4_1) rn4_2 = (int) (Math.random() * 10);
            else break;
        }

        int rn4_3 = (int) (Math.random() * 10);
        while (true)

        {
            if (rn4_3 == rn4_1 || rn4_3 == rn4_2) rn4_3 = (int) (Math.random() * 9);
            else break;
        }

        int rn4_4 = (int) (Math.random() * 10);
        while (true)

        {
            if (rn4_4 == rn4_1 || rn4_4 == rn4_2 || rn4_4 == rn4_3)
                rn4_4 = (int) (Math.random() * 9);
            else break;
        }

        final String rn4_1s = String.valueOf(rn4_1);//TextView에 바로 int를 못 넣어서 String으로 변환
        final String rn4_2s = String.valueOf(rn4_2);
        final String rn4_3s = String.valueOf(rn4_3);
        final String rn4_4s = String.valueOf(rn4_4);
        Log.d("_test", rn4_1s + rn4_2s + rn4_3s + rn4_4s);
        //주석 해제시 게임 화면 상단 가운데에 정답 출력.
//        TextView test1 = (TextView) findViewById(R.id.test1234);
//        test1.setText(rn4_1s + rn4_2s + rn4_3s + rn4_4s); //테스트 중
//        Log.v("알림", "랜덤값 출력");


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
                streamId = btnsound_pool.play(btnclicksound, 0.4F, 0.4F,  1,  0,  1.0F);
                if (startcounter < 5 || clear == true) ;
                else {
                    if (number_first.getText().toString().equals("?"))
                        number_first.setText(((Button) v).getText().toString());
                    else if (number_second.getText().toString().equals("?"))
                        if (((Button) v).getText().toString() == number_first.getText().toString()) {
                            Toast.makeText(gamestart_4.this, "중복번호는 사용할 수 없습니다.", Toast.LENGTH_SHORT).show();
                        } else number_second.setText(((Button) v).getText().toString());
                    else if (number_third.getText().toString().equals("?"))
                        if (((Button) v).getText().toString() == number_first.getText().toString() || ((Button) v).getText().toString() == number_second.getText().toString()) {
                            Toast.makeText(gamestart_4.this, "중복번호는 사용할 수 없습니다.", Toast.LENGTH_SHORT).show();
                        } else number_third.setText(((Button) v).getText().toString());
                    else if (number_fourth.getText().toString().equals("?"))
                        if (((Button) v).getText().toString() == number_first.getText().toString() || ((Button) v).getText().toString() == number_second.getText().toString() || ((Button) v).getText().toString() == number_third.getText().toString()) {
                            Toast.makeText(gamestart_4.this, "중복번호는 사용할 수 없습니다.", Toast.LENGTH_SHORT).show();
                        } else number_fourth.setText(((Button) v).getText().toString());
                }
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
        btn_input = (Button) findViewById(R.id.button_input);

        final int[] input_num = {0};
        View.OnClickListener datainput = new View.OnClickListener() { //입력 버튼 클릭시 사용할 기능들
            @Override
            public void onClick(View v) { //입력 버튼 클릭시의 기능
                //현재 기능이 1, 2, 3번 숫자가 채워져야 4번째까지 입력이 가능하므로 3번째가 비어있지 않으면 모든 숫자가 채워졌다는 의미.
                if(clear==true);
                else {
                    if (number_fourth.getText().toString().equals("?"))
                        Toast.makeText(gamestart_4.this, "모든 번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    else {
                        int streamId = btninputsound_pool.play(btninputsound, 0.4f, 0.4f, 0, 0, 1.0f);
                        input_num[0] = input_num[0] + 1;

                        String input_first = number_first.getText().toString();
                        String input_second = number_second.getText().toString();
                        String input_third = number_third.getText().toString();
                        String input_fourth = number_fourth.getText().toString();

                        int strike = 0;
                        int ball = 0;

                        // == 는 주소값을 비교하므로 사용할 수 없고 equals을 쓰자.
                        if (rn4_1s.equals(input_first)) strike++;
                        else if (rn4_2s.equals(input_first) || rn4_3s.equals(input_first) || rn4_4s.equals(input_first))
                            ball++;
                        if (rn4_2s.equals(input_second)) strike++;
                        else if (rn4_1s.equals(input_second) || rn4_3s.equals(input_second) || rn4_4s.equals(input_second))
                            ball++;
                        if (rn4_3s.equals(input_third)) strike++;
                        else if (rn4_1s.equals(input_third) || rn4_2s.equals(input_third) || rn4_4s.equals(input_third))
                            ball++;
                        if (rn4_4s.equals(input_fourth)) strike++;
                        else if (rn4_1s.equals(input_fourth) || rn4_2s.equals(input_fourth) || rn4_3s.equals(input_fourth))
                            ball++;

                        String strike_s = String.valueOf(strike);
                        String ball_s = String.valueOf(ball);

                        if(strike==0 && ball ==0){
                            DataModel tesing = new DataModel(Html.fromHtml(String.valueOf(input_num[0]) + "회 "
                                    + input_first + input_second + input_third +  input_fourth + " "  + "<font color = 'RED'>OUT</font> "));
                            list.add(tesing);}
                        else {
                            DataModel tesing = new DataModel(Html.fromHtml(String.valueOf(input_num[0]) + "회 "
                                    + input_first + input_second + input_third +  input_fourth + " " + String.valueOf(strike)
                                    + "<font color = 'blue'>S</font> " + String.valueOf(ball) + "<font color = 'yellow'>B</font>"));
                            list.add(tesing);
                        }
                        TextView[] textview = {number_first, number_second, number_third};
                        for (int i = 0; i < textview.length; i++) {
                            textview[i].setText("?");
                            textview[i].setTextColor(Color.BLACK);
                        }

                        //////////////////////////게임 클리어/////////////////////////////////

                        if (strike == 4) {//게임 종료 후 메뉴 출력. 랭킹 등록, 나가기, 새로 시작하기 등을 제공.
                            timer.cancel();
                            main4_music.stop();
                            clear = true;
                            streamId = clearsound_pool.play(clearsound, 0.4f, 0.4f, 0, 0, 1.0f);
                            screen_dark.setVisibility(View.VISIBLE);
                            ConstraintLayout endmenu = (ConstraintLayout) findViewById(R.id.endmenu_xml);
                            endmenu.setVisibility(View.VISIBLE);
                            endmenu.bringToFront();

                            final EditText nameedit = (EditText) findViewById(R.id.name_edit);
                            nameedit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                                @Override
                                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                                    boolean handled = false;
                                    if (actionId == EditorInfo.IME_ACTION_SEND) {
                                        handled = true;
                                    }
                                    return handled;
                                }
                            });

                            InputFilter[] FilterArray = new InputFilter[1];//EditText에 들어갈 수 있는 글자수를 7글자로 제한
                            FilterArray[0] = new InputFilter.LengthFilter(7);
                            nameedit.setFilters(FilterArray);
                            TextView number_times = (TextView) findViewById(R.id.number_times);
                            number_times.setText("시도 횟수: " + input_num[0] + "회");

                            TextView playtimes = (TextView) findViewById((R.id.play_time_xml));
                            final int gameover_playtimes = startcounter - 4;
                            playtimes.setText("걸린 시간: " + String.valueOf(gameover_playtimes / 60) + "분 " + String.valueOf(gameover_playtimes % 60) + "초");
                            Button ranking_input = (Button) findViewById(R.id.ranking3_input_button);


                            View.OnClickListener rankinput = new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (nameedit.getText().toString().equals(""))
                                        Toast.makeText(gamestart_4.this, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                                    else {
                                        final String username = nameedit.getText().toString();
                                        writeNewUser(game_type, username, input_num[0], gameover_playtimes);
                                        //위의 writenewuser를 실행하고 랭킹으로 넘어가면 랭킹 불러오기가 제대로 안 되서 입력한 정보만 나온다. 어떻게 고쳐야 할까?

                                        //로딩 시간을 안 줘서 그럴까? 한 번 타이머로 랭킹 로딩이 될 수 있게 딜레이를 줬다가 실행해보자.
                                        final Handler mhandler = new Handler();
                                        Timer rankinput = new Timer();
                                        TimerTask tt = new TimerTask() {
                                            @Override
                                            public void run() {
                                                mhandler.post(new Runnable() {
                                                    @Override
                                                    public void run() {

                                                        rank_move(username, input_num[0], gameover_playtimes);

                                                    }
                                                });
                                            }
                                        };
                                        Timer timer = new Timer();
                                        timer.schedule(tt, 1000);
                                        //정상 작동 확인. 아마 파이어베이스에 값들이 입력되고, 다시 불러오는 시간 때문에 안 됐던 모양이다.

                                    }
                                }
                            };
                            ranking_input.setOnClickListener(rankinput);
                        }

                        recyclerViewAdapter.notifyDataSetChanged();
                        number_first.setText("?");
                        number_second.setText("?");
                        number_third.setText("?");
                        number_fourth.setText("?");

                        //데이터 입력시 자동으로 스크롤이 가장 마지막으로 가도록 함.
                        recyclerView.scrollToPosition(list.size() - 1);
                    }
                }
            }
        };
        btn_input.setOnClickListener(datainput);

        Button ranking_input = (Button) findViewById(R.id.ranking3_input_button);


        ////////////////////////////onCreate 종료////////////////////////////////////////////////
    }


    public void onBackPressed()//메뉴 화면 만들고 누르면 뜨게 하자.
    {
        if (startcounter < 5 || clear == true) ;//게임 시작 카운트 중에는 뒤로가기 버튼을 먹통으로 만듬.
        else {
            if (menu_on == false) {
                menu_on = true;
                menu_start();
            } else if (menu_on == true) {
                menu_on = false;
                timer2.cancel();
                menu_start();
            }
        }
    }

    public void menu_click(View v) {
        if (startcounter < 5 || clear == true) ;//게임 시작 카운트 중에는 메뉴 버튼을 먹통으로 만듬.
        else {
            if (menu_on == false) {
                menu_on = true;
                menu_start();
            } else if (menu_on == true) {
                menu_on = false;
                menu_start();
            }
        }
    }


    public void menu_start() {
        final ConstraintLayout menu = (ConstraintLayout) findViewById(R.id.menu_layout);
        final ConstraintLayout screen_dark2 = (ConstraintLayout) findViewById(R.id.screen_dark);
        final ImageView startcountimage = (ImageView) findViewById(R.id.start_Count_Image);


        final TextView TextTimer = (TextView) findViewById(R.id.timer_1);
        final Handler mHandler2 = new Handler();
        final int[] startcounter2 = {startcounter};

        if (menu_on == true) {
            timer.cancel();
            timer2.cancel();

            screen_dark2.setVisibility(View.VISIBLE);
            menu.setVisibility(View.VISIBLE);
            menu.bringToFront();
        } else if (menu_on == false) {
            timer_use();

            screen_dark2.setVisibility(View.INVISIBLE);
            menu.setVisibility(View.INVISIBLE);
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
        int streamId = deletesound_pool.play(btndeletesound, 1.0f, 1.0f, 1, 0, 1.0f);
        TextView[] textview = {number_first, number_second, number_third, number_fourth};
        for(int i=0; i < textview.length;i++){
            textview[i].setText("?");
            textview[i].setTextColor(Color.BLACK);
        }

    }

    public void game4_move(View v) {
        Intent game4move = new Intent(this, gamestart_4.class);
        startActivity(game4move);
        finish();
    }

    public void exit(View v) {
        Intent main_move = new Intent(this, MainActivity.class);
        startActivity(main_move);
        finish();
    }


    //    public void Click_Number(View v) //
//    {
//        //if (!minView.getText().toString().trim().equals("--")) //텍스트뷰 값 비교할 때 사용 가능한 문장.
//        if (number_first.getText().toString().equals("?")) number_first.setText("abcde");
//        //\u003f = ?를 의미하는 유니코드. 텍스트뷰에 직접 저런 문자를 입력시엔 유니코드로 적어야 함
//        //특수문자의 유니코드 표가 필요하다면 실행 - charmap 을 해보면 상세히 나온다.
//        else if(number_second.getText().toString().equals("?")) number_second.setText("fghijk");
//        else if(number_third.getText().toString().equals("?")) number_third.setText("fghijk");
//    }
    private void writeNewUser(int type, String name, int times, int play_times) {//랭킹 등록버튼을 누르면 파이어베이스에 지정한 값들을 집어넣는다.
        Rank_base rank = new Rank_base(type, name, times, play_times);
        mDatabase.child("users").push().setValue(rank); // push() - 시간 순서에 따라 임의의 키 값을 생성한다.
    }

    public void rank_move(String username, int times, int play_times) {
        Intent rankmove = new Intent(this, Ranking.class);
        rankmove.putExtra("GAMETYPE", game_type);
        rankmove.putExtra("USERNAME", username);
        rankmove.putExtra("TIMES", times);
        rankmove.putExtra("PLAYTIMES", play_times);
        startActivity(rankmove);
        finish();
    }

    protected void onStop()
    {
        super.onStop();
        main4_music.pause();
        music_position = main4_music.getCurrentPosition();
        if(menu_on == false){
            menu_on = true;
            menu_start();
        }
    }
    public void timer_use()
    {
        final ConstraintLayout menu = (ConstraintLayout) findViewById(R.id.menu_layout);
        final ConstraintLayout screen_dark2 = (ConstraintLayout) findViewById(R.id.screen_dark);
        final ImageView startcountimage = (ImageView) findViewById(R.id.start_Count_Image);


        final TextView TextTimer = (TextView) findViewById(R.id.timer_1);
        final Handler mHandler2 = new Handler();
        final int[] startcounter2 = {startcounter};

        final SoundPool countsound_pool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);// maxStreams, streamType, srcQuality
        final int countsound = countsound_pool.load(this, R.raw.startcount_sound, 1);

        final SoundPool startsound_pool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);// maxStreams, streamType, srcQuality
        final int startsound = startsound_pool.load(this, R.raw.game_start_sound, 1);

        final int[] streamId = new int[1];

        timer2 = new Timer();
        TimerTask abcd = new TimerTask() {
            @Override
            public void run() {
                mHandler2.post(new Runnable() {
                    @Override
                    public void run() {
                        int gametimer = startcounter - 4;
                        if (startcounter == 0) {
                            if(counter2%2 ==0) streamId[0] = countsound_pool.play(countsound, 0.4F, 0.4F,  1,  0,  1.0F);

                            startcountimage.setImageResource(R.drawable.numberimage3);
                        }
                        else if (startcounter == 1){
                            if(counter2%2 ==0)streamId[0] = countsound_pool.play(countsound, 0.4F, 0.4F,  1,  0,  1.0F);
                            startcountimage.setImageResource(R.drawable.numberimage2);
                        }

                        else if (startcounter == 2) {
                            if(counter2%2 ==0)streamId[0] = countsound_pool.play(countsound, 0.4F, 0.4F,  1,  0,  1.0F);
                            startcountimage.setImageResource(R.drawable.numberimage1);
                        }
                        else if (startcounter == 3) {
                            if(counter2%2 ==0)streamId[0] = startsound_pool.play(startsound, 0.4F, 0.4F,  1,  0,  1.0F);
                            startcountimage.setImageResource(R.drawable.start);
                        }
                        else if (startcounter == 4) {
                            screen_dark2.setVisibility(View.INVISIBLE);
                            startcountimage.setVisibility(View.INVISIBLE);
                            TextTimer.setVisibility(View.VISIBLE);
                        }
                        if (startcounter < 4) TextTimer.setText("0분 0초");
                        else {
                            //게임 시작후 경과한 시간을 출력.
                            TextTimer.setText(String.valueOf(gametimer / 60) + "분 " + String.valueOf(gametimer % 60) + "초");

                            /////비어있는 텍스트 창이 반짝거리게 함. 칸이 채워졌다면 검정색으로 고정.
                            if (number_first.getText().toString().equals("?")) {
                                if (counter2 % 2 == 0) number_first.setTextColor(Color.RED);
                                else if (counter2 % 2 == 1)
                                    number_first.setTextColor(Color.YELLOW);
                            } else {
                                number_first.setTextColor(Color.BLACK);
                                if (number_second.getText().toString().equals("?")) {
                                    if (counter2 % 2 == 0)
                                        number_second.setTextColor(Color.RED);
                                    else if (counter2 % 2 == 1)
                                        number_second.setTextColor(Color.YELLOW);
                                } else {
                                    number_second.setTextColor(Color.BLACK);
                                    if (number_third.getText().toString().equals("?")) {
                                        if (counter2 % 2 == 0)
                                            number_third.setTextColor(Color.RED);
                                        else if (counter2 % 2 == 1)
                                            number_third.setTextColor(Color.YELLOW);
                                    }
                                    else {
                                        number_third.setTextColor(Color.BLACK);
                                        if (number_fourth.getText().toString().equals("?")) {
                                            if (counter2 % 2 == 0)
                                                number_fourth.setTextColor(Color.RED);
                                            else if (counter2 % 2 == 1)
                                                number_fourth.setTextColor(Color.YELLOW);
                                        }
                                        else number_fourth.setTextColor(Color.BLACK);
                                    }
                                }
                            }
                        }
                        counter2 += 1;
                        if(counter2 == 2) {
                            counter2 = 0;
                            startcounter++;
                        }
                        Log.v("알림", "타이머 종료");
                    }
                });
            }
        };
        timer2.schedule(abcd, 0, 500);

    }
    public void onResume(){
        main4_music.seekTo(music_position);
        main4_music.start();
        super.onResume();
    }
}
