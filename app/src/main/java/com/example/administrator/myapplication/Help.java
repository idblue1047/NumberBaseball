package com.example.administrator.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Help extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }
    public void onBackPressed()//도움말 화면에서 스마트폰의 뒤로 가기 버튼을 눌렀다면 이 액티비티를 완전히 종료
    {
        Intent mainmove = new Intent(this, MainActivity.class);
        startActivity(mainmove);
        finish();
    }
}
