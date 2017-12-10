package com.example.administrator.myapplication;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by Administrator on 2017-12-06.
 * 파이어베이스에 저장할 변수들을 집어넣은 클래스이고, 또한 파이어베이스에서 받아온 자료들을 Arraylist 형태에서 값에 따라 정렬할 수도 있습니다.
 */

public class Rank_base implements Serializable {
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public int getPlay_times() {
        return play_times;
    }

    public void setPlay_times(int play_times) {
        this.play_times = play_times;
    }


    // Arraylist 정렬 테스트 중. 성공!

//////////////////////////////////////////////////////////////////////////////////


    //    public static Comparator<Rank_base> Rank_timessort = new Comparator<Rank_base>() { //times 횟수로만 정렬
//
//    public int compare(Rank_base s1, Rank_base s2) {
//        int times1 = s1.getTimes();
//        int times2 = s2.getTimes();
//
//        //ascending order
//        return times1 - times2; //times1이 times2보다 크거나 작거나 같거나로 갈림.
//
//        //descending order
//        //return StudentName2.compareTo(StudentName1);
//    }};
//
//    public static Comparator<Rank_base> Rank_playtimesort = new Comparator<Rank_base>() {//playtimes 시간으로만 정렬
//
//        public int compare(Rank_base s1, Rank_base s2) {
//
//            int playtimes1 = s1.getPlay_times();
//            int playtimes2 = s2.getPlay_times();
//
//	   /*For ascending order*/
//            return playtimes1 - playtimes2;
//
//	   /*For descending order*/
//            //rollno2-rollno1;
//        }};
    //    @Override
//    public int compareTo(@NonNull Rank_base o) {
//        int ret = 0;
//        Rank_base other = (Rank_base)o;
//        if (times > other.times) ret = 1;
//        else if (times < other.times) ret = -1;
//        if (times == other.times) {
//            if (play_times > other.play_times) ret = 1;
//            else if (play_times < other.play_times) ret = -1;
//        }
//
//        return ret;
//    }
    public static Comparator<Rank_base> Rank_mulitplessort = new Comparator<Rank_base>() {
        //times 순서로 정렬 후 같은 times 내에서는 playtimes 기준으로 정렬. 성공!
        //정렬 순서를 바꾸려면 1, -1 등을 반대로 바꿔주면 되지 않을까 싶다. 일단 현 상태는 오름차순.

        public int compare(Rank_base s1, Rank_base s2) {
            int ret = 0;

            int times1 = s1.getTimes();
            int times2 = s2.getTimes();
            int playtimes1 = s1.getPlay_times();
            int playtimes2 = s2.getPlay_times();

            if (times1 > times2) ret = 1;
            else if (times1 < times2 ) ret = -1;
            if(times1 == times2){
                if (playtimes1 > playtimes2) ret = 1;
                else if(playtimes1 < playtimes2) ret = -1;
            }


            return ret;
        }
    };

    //    public String rank_string(){return "(" + type + username + times + " - " + play_times;
//    }
}
