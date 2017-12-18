package com.example.administrator.myapplication;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //    public TextView text_1;
//    public Button button1;
    public boolean help_on = false;
    public ConstraintLayout helpboard_layout;

    double Exit_Time = 0;
    long t = System.currentTimeMillis();

    public MediaPlayer main_music;
    //메인 음악. https://freesound.org/people/ispeakwaves/sounds/384931/

    int music_position = 0; //홈버튼 등으로 나갔을 때 음악재생을 정지했다가 돌아올때 정지한 부분부터 시작하기 위해 넣은 변수.


    SoundPool sound;//버튼 클릭음
    int btnclicksound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        helpboard_layout = (ConstraintLayout) findViewById(R.id.helpboard_layout);
        helpboard_layout.setVisibility(View.INVISIBLE);

        main_music = MediaPlayer.create(this, R.raw.main_music);
        main_music.setLooping(true);
        main_music.start();//배경음악  재생


        Button help_button = (Button)findViewById(R.id.Help_Button);

        View.OnClickListener btn_help = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                help();
            }
        };
        help_button.setOnClickListener(btn_help);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //화면을 세로로 고정. 화면이 가로세로가 바뀔때마다 초기화가 되는데 이 앱에는 회전이 필요없다.

        sound = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);// maxStreams, streamType, srcQuality
        btnclicksound = sound.load(this, R.raw.btn_click, 1);


    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////액티비티 전환 모음//////////////////////////////////////////////////////////


    public void game3_move(View v) {
        main_music.stop();
        clicksoundplay();
        if (help_on == true) {
        }

        else{
            Intent game3move = new Intent(this, gamestart_3.class);
            startActivity(game3move);
            finish();
        }
    }

    public void game4_move(View v) {
        main_music.stop();
        clicksoundplay();
        if (help_on == true) {}
        else {
            Intent game4move = new Intent(this, gamestart_4.class);
            startActivity(game4move);
            finish();
        }
    }

    public void rank_move(View v) {
        clicksoundplay();
        main_music.stop();

        if (help_on == true){}
        else {
            Intent rankmove = new Intent(this, Ranking.class);
            startActivity(rankmove);
            finish();
        }
    }
    public void help_On(View v)//버튼 클릭시 도움말 액티비티 창을 띄움
    {

        clicksoundplay();
        help();
    }

    public void gameExit(View v) {
        main_music.stop();
        if(help_on == true){}
        else {
            System.exit(0);
        }
    }

    public void helpExit(View v)
    {
        help();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void onBackPressed()//스마트폰의 뒤로가기 버튼 클릭시 이벤트
    {
        clicksoundplay();
        if (help_on == true) {
            help();
        } else {
            if (t - Exit_Time > 2500) { //2.5초 안에 한번 더 뒤로가기 버튼 클릭하면 종료.
                Exit_Time = t;
                Toast.makeText(this, "연속으로 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
            } else {
                System.exit(0);
                super.onBackPressed();//일단 이거 지우지 말자. System.exit가 안 먹히는 것 같음.

            }
        }
    }

    public void help() {
        clicksoundplay();
        if (help_on == false) {
            help_on = true;
            helpboard_layout.setVisibility(View.VISIBLE);
        }
        else if (help_on == true) {
            help_on = false;
            helpboard_layout.setVisibility(View.INVISIBLE);
        }
    }
    public void clicksoundplay(){

        int streamId = sound.play(btnclicksound, 0.4F, 0.4F,  1,  0,  1.0F);
        //SoundPool: 간단한 효과음등을 재생할 때 사용. 대충 5~6초쯤인 것 같다.
        //volime = 0~1.0f. 소리 크기를 나타냄.
        //priority: 0 이상, 음원이 동시재생될 때 우선도 설정. 숫자가 클수록 우선도 높음
        //loop: -1 = 무한반복, 0 = 한 번 재생
    }

    protected void onStop()
    {
        super.onStop();
        main_music.pause();
        music_position = main_music.getCurrentPosition();

    }
    public void onResume(){
        main_music.seekTo(music_position);
        main_music.start();
        super.onResume();
    }

}
