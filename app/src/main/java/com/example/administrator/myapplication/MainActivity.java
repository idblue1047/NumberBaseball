package com.example.administrator.myapplication;

import android.content.Intent;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helpboard_layout = (ConstraintLayout) findViewById(R.id.helpboard_layout);
        helpboard_layout.setVisibility(View.INVISIBLE);

        Button help_button = (Button)findViewById(R.id.Help_Button);

        View.OnClickListener btn_help = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                help();
            }
        };
        help_button.setOnClickListener(btn_help);

    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////액티비티 전환 모음//////////////////////////////////////////////////////////


    public void game3_move(View v) {
        if (help_on == true) {
        }

        else{
            Intent game3move = new Intent(this, gamestart_3.class);
            startActivity(game3move);
            finish();
        }
    }

    public void game4_move(View v) {
        if (help_on == true) {}
        else {
            Intent game4move = new Intent(this, gamestart_4.class);
            startActivity(game4move);
            finish();
        }
    }

    public void rank_move(View v) {
        if (help_on == true){}
        else {
            Intent rankmove = new Intent(this, Ranking.class);
            startActivity(rankmove);
            finish();
        }
    }
    public void help_On(View v)//버튼 클릭시 도움말 액티비티 창을 띄움
    {
        help();
    }

    public void gameExit(View v) {
        if(help_on == true){}
        else {
            super.onBackPressed();
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
        if (help_on == true) {
            help();
        } else {
            double Exit_Time = 0;
            long t = System.currentTimeMillis();
            if (t - Exit_Time > 2500) { //2.5초 안에 한번 더 뒤로가기 버튼 클릭하면 종료.
                Exit_Time = t;
                Toast.makeText(this, "연속으로 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
            } else {
                super.onBackPressed();
            }
        }
    }

    public void help() {

        if (help_on == false) {
            help_on = true;
            helpboard_layout.setVisibility(View.VISIBLE);
        }
        else if (help_on == true) {
            help_on = false;
            helpboard_layout.setVisibility(View.INVISIBLE);
        }
    }

}
