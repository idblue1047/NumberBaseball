package com.example.administrator.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
//    public TextView text_1;
//    public Button button1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //text_1 = (TextView)findViewById(R.id.text1);
    }

    //@Override

//    public void click1(View v){//기능 테스트 중
//        int i = 0;
//        if (i==0){
//            i = 1;
//            Toast.makeText(this,"메시지를 변경합니다.", Toast.LENGTH_SHORT).show();
//            text_1.setText("abcdefg");
//        }
//        else if (i==1){
//            i = 0;
//            Toast.makeText(this,"메시지를 복구합니다.", Toast.LENGTH_SHORT).show();
//            text_1.setText("Text1");
//        }
//    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////액티비티 전환 모음//////////////////////////////////////////////////////////
    public void help_move(View v)//버튼 클릭시 도움말 액티비티 창을 띄움
    {
        Intent Helpmove = new Intent(this, Help.class);//임시로 test로 넘기는 중. 나중에 다시 help로 바꿀 것.
        startActivity(Helpmove);
        finish();
    }
    public void game3_move(View v)
    {
        Intent game3move = new Intent(this, gamestart_3.class);
        startActivity(game3move);
        finish();
    }
    public void game4_move(View v)
    {
        Intent game4move = new Intent(this, gamestart_4.class);
        startActivity(game4move);
        finish();
    }
    public void rank_move(View v)
    {
        Intent rankmove = new Intent(this, Ranking.class);
        startActivity(rankmove);
        finish();
    }
    public void gameExit(View v) {
        super.onBackPressed();
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void onBackPressed()//스마트폰의 뒤로가기 버튼 클릭시 이벤트
    {
        double Exit_Time = 0;
        long t = System.currentTimeMillis();
        if (t - Exit_Time > 2500) //2.5초 안에 한번 더 뒤로가기 버튼 클릭하면 종료.
        {
            Exit_Time = t;
            Toast.makeText(this,"연속으로 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
        else{
            super.onBackPressed();
        }
    }

}
