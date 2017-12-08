package com.example.administrator.myapplication;

import java.io.Serializable;

/**
 * Created by Administrator on 2017-12-06.
 */

public class Rank_base implements Serializable{
    private static final long seralVersionUID = 1L;

    public int type;
    public String username;
    public int times;
    public int play_times;
    //public transient 자료형 변수이름 - 자료를 가져올 때 transient가 있는 자료는 제외한다.


    public Rank_base() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Rank_base(int type, String username, int times, int play_times) {
        this.type = type;
        this.username = username;
        this.times = times;
        this.play_times = play_times;
    }
//    public String rank_string(){return "(" + type + username + times + " - " + play_times;
//    }
}
