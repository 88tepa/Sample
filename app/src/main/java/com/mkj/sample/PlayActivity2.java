package com.mkj.sample;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class PlayActivity2 extends AppCompatActivity {

    //SQLITE 데이타베이스 관련변수
    SQLiteDatabase db;
    MySQLiteOpenHelper helper;

    public static int point = 1000; //점수

    public static Activity play_activity2; // 다른 액티비티 종료 1, 액티비티를 전역변수로 설정

    private long back_key_press_time = 0; // 뒤로 버튼을 누른 시간
    //SoundPool 사운드(1M), MediaPlayer 사운드(1M 이상) 동영상)
    MediaPlayer fire,                      //발사음
                hit,                       //폭발음
                media_player_play_bgm2;    //배경음
    public static MediaPlayer media_player_start_menu_2; // 메뉴 배경 오디오

    public static Handler handler2 = new Handler(); // 핸들러

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        play_activity2 = PlayActivity2.this; // 다른 액티비티 종료 2, 액티비티의 정보를 저장, 다음은 다른 액티비티에 가서 작성(현재는 ScoreInput에 있음)

//        //상태 바 없애기
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//
//        //타이틀바 숨김
//        requestWindowFeature(Window.FEATURE_NO_TITLE);

        MyView myView = new MyView(this);
        myView.setFocusable(true); //키이벤트를 받을 수 있도록 설정

        //xml 이 아닌 내부클래스 (커스텀 뷰)로 화면 사용
        setContentView(myView);

        new Handler().postDelayed(new Runnable() { // 스코어 점수 나오고 끝남
            @Override
            public void run() {
                Intent intent = new Intent(PlayActivity2.this, ScoreInput.class);
                startActivity(intent); // 플레이 액티비티 시작
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        }, 10000);

    }
    //내부 클래스
    class MyView extends View implements Runnable{
        Drawable backImg;       // 배경이미지
        Drawable backImg2;       // 배경이미지
        Drawable gunship;       // 비행기이미지
        Drawable gunship_left;  // 비행기이미지
        Drawable gunship_right; // 비행기이미지
        Drawable missile;       // 총알이미지
        Drawable enemy;         // 적 이미지
        Drawable explosure;     // 폭발이미지
        Drawable up;     // 폭발이미지
        Drawable down;     // 폭발이미지
        Drawable left;     // 폭발이미지
        Drawable right;     // 폭발이미지


        int width, height;               //화면의 가로 , 세로 길이
        int gunshipWidth, gunshipHeight; //비행기의 가로, 세로
        int missileWidth, missileHeight; //총알의 가로, 세로
        int enemyWidth, enemyHeight;     //적의 가로, 세로
        int hitWidth, hitHeight;         //폭발이미지의 가로, 세로
        int x, y;                        //비행기의 좌표
        int mx, my;                      //총알 좌표
        int ex, ey;                      //적 좌표
        int hx, hy;                      //폭발 좌표
        boolean isFire;                  //총알 발사여부
        boolean isHit;                   //폭발 여부
        List<Missile> mlist;             //총알 리스트

        //생성자
        public MyView(Context context) {
            super(context);

            //이미지 생성
            backImg = getResources().getDrawable(R.drawable.sea_background, null);
            backImg2 = getResources().getDrawable(R.drawable.sea_background, null);
            gunship = getResources().getDrawable(R.drawable.f22_top, null);
            gunship_left = getResources().getDrawable(R.drawable.f22_left, null);
            gunship_right = getResources().getDrawable(R.drawable.f22_right, null);
            missile = getResources().getDrawable(R.drawable.f22_bullet, null);
            enemy = getResources().getDrawable(R.drawable.enemy, null);
            explosure = getResources().getDrawable(R.drawable.hit, null);
            up = getResources().getDrawable(R.drawable.joystick_up2, null);
            down = getResources().getDrawable(R.drawable.joystick_down2, null);
            left = getResources().getDrawable(R.drawable.joystick_left2, null);
            right = getResources().getDrawable(R.drawable.joystick_right2, null);

            //사운드 생성
            fire = MediaPlayer.create(PlayActivity2.this, R.raw.bullet_shoot);
            hit = MediaPlayer.create(PlayActivity2.this, R.raw.hit_explosive);
            media_player_play_bgm2 = MediaPlayer.create(PlayActivity2.this, R.raw.play_media_08_battle_of_extreme_north);
            media_player_start_menu_2 = MediaPlayer.create(PlayActivity2.this, R.raw.main_menu_02_in_the_name_of_strikers);

            media_player_play_bgm2.start();
            //리스트 생성
            mlist = new ArrayList<>();

            //백그라운드 스레드 생성
            Thread th =new Thread(this);
            th.start();
        }

        //화면 사이즈가 변경될때 (최초 표시, 가로/세로 전환)
        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            //화면의 가로, 세로
            width = getWidth();
            height = getHeight();
            //이미지의 가로, 세로 길이
            gunshipWidth = gunship.getIntrinsicWidth();
            gunshipHeight = gunship.getIntrinsicHeight();
            gunshipWidth = gunship_left.getIntrinsicWidth();
            gunshipHeight = gunship_left.getIntrinsicHeight();
            gunshipWidth = gunship_right.getIntrinsicWidth();
            gunshipHeight = gunship_right.getIntrinsicHeight();
            missileWidth = missile.getIntrinsicWidth();
            missileHeight = missile.getIntrinsicHeight();
            enemyWidth = enemy.getIntrinsicWidth();
            enemyHeight = enemy.getIntrinsicHeight();
            hitWidth = explosure.getIntrinsicWidth();
            hitHeight = explosure.getIntrinsicHeight();
            //비행기 좌표
            x = width / 2 - gunshipWidth / 2;
            y = height - 300;
            //총알 좌표
            mx = x;
            my = y + 20;
            //적 좌표
            ex = width - enemyWidth;
            ey = 50;
        }


        @Override
        protected void onDraw(Canvas canvas) {
            //배경이미지 출력
            //setBounds(x1,y1, x2, y2) 영역 지정
            backImg.setBounds(0, 0, width, height);
            backImg.draw(canvas);//이미지를 캔버스에 출력시킴
            backImg.setBounds(0, 0-height, width, 0);
            backImg.draw(canvas);//이미지를 캔버스에 출력시킴

            //적 출력
            if(isHit){ //폭발 상태
                //폭발 이미지 출력
                explosure.setBounds(hx, hy, hx +hitWidth, hy+hitHeight);
                explosure.draw(canvas);
                try{
                    Thread.sleep(200);
                }catch (Exception e){
                    e.printStackTrace();
                }
                isHit=false;//폭발 하지 않은 상태로 전환
            }else{
                //폭발하지 않은 상태
                enemy.setBounds(ex, ey, ex+enemyWidth, ey+enemyHeight);
                enemy.draw(canvas);
            }

            //총알 출력
            for( int i=0; i<mlist.size(); i++){
                Missile m=mlist.get(i);//i번째 총알
                //총알 이미지의 출력범위
                missile.setBounds(m.getMx(), m.getMy(), m.getMx()+missileWidth,
                        m.getMy()+missileHeight);
                missile.draw(canvas); //총알 이미지 출력
            }

            //비행기 출력
            gunship.setBounds(x, y, x+gunshipWidth, y+gunshipHeight);
            gunship.draw(canvas);

            //점수 출력
            String str ="Point : " + point;
            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            paint.setTextSize(50);//폰트 사이즈
            //텍스트 출력 drawText(문자열, x, y, 페인트 객체)
            canvas.drawText(str, 20, 70, paint);
        }

        //키이벤트 처리
        @Override
        public boolean onKeyDown(int keyCode, KeyEvent event) {
            switch (keyCode){
                case KeyEvent.KEYCODE_DPAD_LEFT:
                    x -= 30;
                    x = Math.max(0, x); //큰값
                    break;
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    x += 30;
                    x = Math.min(width-gunshipWidth, x); //작은 값
                    break;

            }
            postInvalidate(); //그래픽 갱신 요청 =>다시 onDraw() 호출됨
            return super.onKeyDown(keyCode, event);
        }

        @Override
        public void run() {
            //적 좌표
            while (true){
                ex -= 6;
                ey += 5;

                if(ex < 0) {
                    ex = width - enemyWidth;
                    ey = 0;
                }

                //총알 좌표
                for(int i=0;  i<mlist.size(); i++){
                    Missile m=mlist.get(i);//i번째 총알
                    m.setMy(m.getMy()-20); //y좌표 감소 처리
                    if(m.getMy() <0){
                        mlist.remove(i); //리스트에서 제거
                    }

                    //충돌여부 판정
                    //적의 사각영역
                    Rect rect1 = new Rect(ex, ey, ex+enemyWidth, ey+enemyHeight);
                    //총알 사각영역
                    Rect rect2 = new Rect(m.getMx(), m.getMy(),
                            m.getMx()+missileWidth, m.getMy()+missileHeight);

                    if(rect1.intersect(rect2)){ //겹친 부분이 있으면
                        hit.start();  //폭발음 플레이
                        isHit = true; // 폭발 상태로 변경
                        point += 100; //점수 증가
                        hx = ex;        //폭발한 좌표 저장
                        hy = ey;
                        mlist.remove(i); //총알 리스트에서 제거
                        ex = width-enemyWidth; //적 좌표 초기화
                        ey = 0;
                    }
                }


                try{
                    Thread.sleep(20);
                }catch (Exception e){
                    e.printStackTrace();
                }
                postInvalidate();//그래픽 갱신 요청 =>다시 onDraw() 호출됨
            }

        }
        @Override
        public boolean onTouchEvent(MotionEvent event) {

            isFire = true; //발사 상태로 전환
            fire.start(); //사운드 플레이

            //총알 객체 생성
            Missile ms = new Missile(x+30, y);
            mlist.add(ms); //리스트에 추가
            return super.onTouchEvent(event);
        }
    }
    public class Missile {

        private int mx;
        private int my;

        public Missile(int mx, int my) {
            this.mx = mx;
            this.my = my;
        }

        public int getMy() {
            return my;
        }

        public void setMy(int my) {
            this.my = my;
        }

        public int getMx() {
            return mx;
        }

        public void setMx(int mx) {
            this.mx = mx;
        }

        @Override
        public String toString() {
            return "Missile{" +
                    "mx=" + mx +
                    ", my=" + my +
                    '}';
        }
    }
    // 뒤로 버튼을 눌러서 액티비티 종료, 두번 눌러야 종료 됨
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() <= back_key_press_time + 2000) { // 뒤로 버튼을 눌렀을때 2초보다 길지 않으면 종료
            point = 1000;
            if (handler2 != null) {
                handler2.removeMessages(0);
                handler2.removeCallbacks(null);
                handler2.removeCallbacksAndMessages(null);
            }
            media_player_play_bgm2.release(); // 플레이 배경 오디오 해제
            media_player_play_bgm2 = null;    // 플레이 배경 오디오 무효화
            fire.release();                   // 총알 발사 오디오 해제
            fire = null;                      // 총알 발사 오디오 무효화
            hit.release();                    // 기체 폭발 오디오 해제
            hit = null;                       // 기체 폭발 오디오 무효화
            if(media_player_start_menu_2 != null &&  media_player_start_menu_2.isPlaying()) {
                return;
            } else {
                media_player_start_menu_2 = MediaPlayer.create(PlayActivity2.this, R.raw.main_menu_02_in_the_name_of_strikers);
                media_player_start_menu_2.start();                     // 메인메뉴 오디오 시작 2
                media_player_start_menu_2.setLooping(true);            // 메인메뉴 오디오 무한루프 2
            }
            media_player_start_menu_2.start();                     // 메인메뉴 오디오 시작 2
            media_player_start_menu_2.setLooping(true);            // 메인메뉴 오디오 무한루프 2
            finish();                         // 액티비티 종료
            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        } else if (System.currentTimeMillis() > back_key_press_time + 2000) { // 뒤로 버튼을 처음 눌렀거나 2초보다 길면 다시 토스트 띄움
            back_key_press_time = System.currentTimeMillis();
            Toast.makeText(getApplicationContext(), "한번 더 누르면 종료됩니다", Toast.LENGTH_SHORT).show();
        }
    }
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
        Toast.makeText(getApplicationContext(), name+"로 회원 가입 완료.", Toast.LENGTH_LONG).show();

        Log.d("TAG", name+"/"+score+" 의 정보로 디비저장완료.");
    }
}