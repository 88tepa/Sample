package com.mkj.sample;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ScoreInput extends AppCompatActivity {

    //SQLITE 데이타베이스 관련변수
    SQLiteDatabase db;
    MySQLiteOpenHelper helper;

    String name, score; //입력값을 변수에 저장해서 insert처리할변수

    Button btn_name;
    EditText et_highscore_name, et_highscore_score;
    //TextView  tv_highscore_score;

    String TAG ="회원가입 예제";

    public static MediaPlayer media_player_start_menu_2; // 메뉴 배경 오디오

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_input);

        //데이베이스 생성.
        helper = new MySQLiteOpenHelper(
                ScoreInput.this, // 현재 화면의 context
                "member2.db", // 파일명
                null, // 커서 팩토리
                1); // 버전 번호

        btn_name = (Button)findViewById(R.id.btn_name);

        et_highscore_name = (EditText)findViewById(R.id.et_highscore_name);
        et_highscore_score = (EditText)findViewById(R.id.et_highscore_score);
        //tv_highscore_score = (TextView) findViewById(R.id.tv_highscore_score);

        btn_name.setOnClickListener(mClickListener);

        String point = Integer.toString(PlayActivity2.point);
        et_highscore_score.setText(point);
        score = et_highscore_score.getText().toString();
//        tv_highscore_score.setText(point);
//        score = tv_highscore_score.getText().toString();
    }
    Button.OnClickListener mClickListener = new Button.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_name:
                    //공백체크
                    if(et_highscore_name.getText().toString().equals("")) {
                        et_highscore_name.setText("AAA");
                        Toast.makeText(ScoreInput.this, "이름이 없어 AAA 자동 입력!", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    //공백없이 입력잘되었을경우 변수에 저장
                    name = et_highscore_name.getText().toString();

                    //회원정보를 다 입력하였을경우 데이타베이스에 insert를 호출
                    insert(name, score);

//                    //회원가입 후 저장정보 확1인하기.
//                    intentJoinOk.putExtra("pw", name);
//                    intentJoinOk.putExtra("name", score);
//                    startActivity(intentJoinOk);
                    if(media_player_start_menu_2 != null &&  media_player_start_menu_2.isPlaying()) {
                        return;
                    } else {
                        media_player_start_menu_2 = MediaPlayer.create(ScoreInput.this, R.raw.main_menu_02_in_the_name_of_strikers);
                        media_player_start_menu_2.start();                     // 메인메뉴 오디오 시작 2
                        media_player_start_menu_2.setLooping(true);            // 메인메뉴 오디오 무한루프 2
                    }

                    PlayActivity2 PA = (PlayActivity2)PlayActivity2.play_activity2; // 다른 액티비티 종료 3, 다른 액티비티에서 객체 생성
                    PA.finish(); // 다른 액티비티 종료 4, 만든 객체로 원하는 작업 실행
                    finish();
                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                    break;
            }
        }
    };

    //데이타베이스 메서드 처리  ////////////////////////////
    public void insert(String name, String score) {

        db = helper.getWritableDatabase(); // db 객체를 얻어온다. 쓰기 가능

        //값들을 컨트롤 하려고 클래스 생성
        ContentValues values = new ContentValues();

        // db.insert의 매개변수인 values가 ContentValues 변수이므로 그에 맞춤
        // 데이터의 삽입은 put을 이용한다.
        values.put("name", name);
        values.put("score", score);
        db.insert("member", null, values); // 테이블/널컬럼핵/데이터(널컬럼핵=디폴트)

        // tip : 마우스를 db.insert에 올려보면 매개변수가 어떤 것이 와야 하는지 알 수 있다.
        db.close();

        Log.d(TAG, name+"/"+score+" 의 정보로 디비저장완료.");
    }
    // 뒤로 버튼을 눌러서 액티비티 종료
    @Override
    public void onBackPressed() {
        if (PlayActivity2.handler2 != null) {
            PlayActivity2.handler2.removeMessages(0);
            PlayActivity2.handler2.removeCallbacks(null);
            PlayActivity2.handler2.removeCallbacksAndMessages(null);
        }
//        if(MainActivity.media_player_start_menu_2 != null &&  MainActivity.media_player_start_menu_2.isPlaying()) {
//            return;
//        } else {
//            media_player_start_menu_2 = MediaPlayer.create(ScoreInput.this, R.raw.main_menu_02_in_the_name_of_strikers);
//            media_player_start_menu_2.start();                     // 메인메뉴 오디오 시작 2
//            media_player_start_menu_2.setLooping(true);            // 메인메뉴 오디오 무한루프 2
//        }
        PlayActivity2.point = 1000;
        super.finish(); // 액티비티 종료
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}