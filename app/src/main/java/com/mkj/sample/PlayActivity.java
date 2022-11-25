package com.mkj.sample;

import static com.mkj.sample.Bullet.bullet_height;
import static com.mkj.sample.Bullet.bullet_width;
import static com.mkj.sample.Bullet.f22_bullet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PlayActivity extends AppCompatActivity {

    Animation aniBackground, aniBackground2, aniCloud_1, aniCloud_2, aniCloud_3, aniCloud_4, aniCloud_5, aniCloud_6, aniF22;
    ImageView ivBackground, ivBackground2, ivCloud_1, ivCloud_2, ivCloud_3, ivCloud_4, ivCloud_5, ivCloud_6,
              ivUpBtn, ivDownBtn, ivLeftBtn, ivRightBtn;
    LinearLayout layout_field;
    MediaPlayer media_player_play_bgm,      // 플레이 배경 오디오
                media_player_bullet_shoot,  // 총알 발사 오디오
                media_player_hit_explosive; // 기체 폭발 오디오
    public static MediaPlayer media_player_start_menu_2;  // 메뉴 배경 오디오
    ImageButton ib_menu_button,
                ib_fire_button;

    private MainActivity mainActivity;
    private long back_key_press_time = 0; // 뒤로 버튼을 누른 시간

    public static float f22_x;    // 비행기 좌표 X(가로)
    public static float f22_y;    // 비행기 좌표 Y(세로)
    public static float bullet_x; // 총알 좌표 X(가로)
    public static float bullet_y; // 총알 좌표 Y(세로)
    public static float enemy_x;  // 적 비행기 좌표 Y(세로)
    public static float enemy_y;  // 적 비행기 좌표 Y(세로)
    int speed = 30;               // 이동속도

    public static float layoutWidth, layoutHeight; // layout화면의 너비와 높이

    final Handler handler = new Handler(Looper.getMainLooper()); // 핸들러

    boolean isFire;           // 총알 발사여부
    boolean isHit;            // 폭발 여부
    private List<Bullet> bullet_list; // 총알 리스트

    // float을 int로 전환해주려고 만든 변수
    int int_f22_x;
    int int_f22_y;
    int int_f22_width;
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        layout_field = (LinearLayout)findViewById(R.id.layout_field);

        ivBackground = (ImageView) findViewById(R.id.ivBackground);
        ivBackground2 = (ImageView) findViewById(R.id.ivBackground2);
        ivCloud_1 = (ImageView) findViewById(R.id.ivcloud1);
        ivCloud_2 = (ImageView) findViewById(R.id.ivcloud2);
        ivCloud_3 = (ImageView) findViewById(R.id.ivcloud3);
        ivCloud_4 = (ImageView) findViewById(R.id.ivcloud4);
        ivCloud_5 = (ImageView) findViewById(R.id.ivcloud5);
        ivCloud_6 = (ImageView) findViewById(R.id.ivcloud6);
        ivUpBtn = (ImageView) findViewById(R.id.ivUp);
        ivDownBtn = (ImageView) findViewById(R.id.ivDown);
        ivLeftBtn = (ImageView) findViewById(R.id.ivLeft);
        ivRightBtn = (ImageView) findViewById(R.id.ivRight);

        ib_menu_button = (ImageButton) findViewById(R.id.ib_menu_button);
        ib_fire_button = (ImageButton) findViewById(R.id.ib_fire_button);

        aniBackground = AnimationUtils.loadAnimation(PlayActivity.this, R.anim.background);
        aniBackground2 = AnimationUtils.loadAnimation(PlayActivity.this, R.anim.background2);
        aniCloud_1 = AnimationUtils.loadAnimation(PlayActivity.this, R.anim.cloud_1_move);
        aniCloud_2 = AnimationUtils.loadAnimation(PlayActivity.this, R.anim.cloud_2_move);
        aniCloud_3 = AnimationUtils.loadAnimation(PlayActivity.this, R.anim.cloud_3_move);
        aniCloud_4 = AnimationUtils.loadAnimation(PlayActivity.this, R.anim.cloud_4_move);
        aniCloud_5 = AnimationUtils.loadAnimation(PlayActivity.this, R.anim.cloud_5_move);
        aniCloud_6 = AnimationUtils.loadAnimation(PlayActivity.this, R.anim.cloud_6_move);
        //aniF22 = AnimationUtils.loadAnimation(PlayActivity.this, R.anim.f22_startup);

        ivBackground.startAnimation(aniBackground);
        ivBackground2.startAnimation(aniBackground2);
        ivCloud_1.startAnimation(aniCloud_1);
        ivCloud_2.startAnimation(aniCloud_2);
        ivCloud_3.startAnimation(aniCloud_3);
        ivCloud_4.startAnimation(aniCloud_4);
        ivCloud_5.startAnimation(aniCloud_5);
        ivCloud_6.startAnimation(aniCloud_6);

        ivUpBtn.setOnTouchListener(mTouchEvent);
        ivDownBtn.setOnTouchListener(mTouchEvent);
        ivLeftBtn.setOnTouchListener(mTouchEvent);
        ivRightBtn.setOnTouchListener(mTouchEvent);
        ib_menu_button.setOnTouchListener(mTouchEvent);
        ib_fire_button.setOnTouchListener(mTouchEvent);

        media_player_play_bgm = MediaPlayer.create(PlayActivity.this, R.raw.play_media_07_far_from_the_cloud_sea);
        media_player_bullet_shoot = MediaPlayer.create(PlayActivity.this, R.raw.bullet_shoot);
        media_player_hit_explosive = MediaPlayer.create(PlayActivity.this, R.raw.hit_explosive);
        media_player_start_menu_2 = MediaPlayer.create(PlayActivity.this, R.raw.main_menu_02_in_the_name_of_strikers);

        media_player_play_bgm.start();          // 플레이 배경 오디오 시작
        media_player_play_bgm.setLooping(true); // 플레이 배경 오디오 무한루프

        //기기 자체 디스플레이화면 크기 구하기용 여기선 안씀
//        Display display = getWindowManager().getDefaultDisplay();
//        Point size = new Point();
//        display.getSize(size); // or getRealSize(size)
//        width = size.x;
//        height = size.y;
//        X = width / 2;
//        Y = height / 2;
//        Y += 300;

//        MyView myView = new MyView(this);
//        myView.setFocusable(true); //키이벤트를 받을 수 있도록 설정
//        //xml 이 아닌 내부클래스 (커스텀 뷰)로 화면 사용
//        setContentView(myView);

    }
    // 뒤로 버튼을 눌러서 액티비티 종료, 두번 눌러야 종료 됨
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() <= back_key_press_time + 2000) { // 뒤로 버튼을 눌렀을때 2초보다 길지 않으면 종료
            media_player_play_bgm.release();      // 플레이 배경 오디오 해제
            media_player_play_bgm = null;         // 플레이 배경 오디오 무효화
            media_player_bullet_shoot.release();  // 총알 발사 오디오 해제
            media_player_bullet_shoot = null;     // 총알 발사 오디오 무효화
            media_player_hit_explosive.release(); // 기체 폭발 오디오 해제
            media_player_hit_explosive = null;    // 기체 폭발 오디오 무효화
            media_player_start_menu_2.start();                     // 메인메뉴 오디오 시작 2
            media_player_start_menu_2.setLooping(true);            // 메인메뉴 오디오 무한루프 2
            finish();                             // 액티비티 종료
            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        } else if (System.currentTimeMillis() > back_key_press_time + 2000) { // 뒤로 버튼을 처음 눌렀거나 2초보다 길면 다시 토스트 띄움
            back_key_press_time = System.currentTimeMillis();
            Toast.makeText(getApplicationContext(), "한번 더 누르면 종료됩니다", Toast.LENGTH_SHORT).show();
        }
    }
    // 왜 필요한지 모르겠음;;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_exam, menu);
        return true;
    }
    // 레이아웃 크기 구하기, onCreate 안에서는 작동 안해서 이렇게 빼야함
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        layoutWidth = layout_field.getWidth();
        layoutHeight = layout_field.getHeight();
        f22_x = layoutWidth / 2;
        f22_y = layoutHeight - 300;
        bullet_x = f22_x;
        bullet_y = f22_y;
        //적 좌표
        enemy_x = layoutWidth - Enemy.enemy_width;
        enemy_y = 50;
    }
    // 비행기 조이스틱 터치 이동 구현
    private View.OnTouchListener mTouchEvent = new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {

            ImageView ivUpBtn = (ImageView) v;
            ImageView ivDownBtn = (ImageView) v;
            ImageView ivLeftBtn = (ImageView) v;
            ImageView ivRightBtn = (ImageView) v;

            int action = event.getAction();
            int id = v.getId();

            if (id == R.id.ivUp) {
                if (action == MotionEvent.ACTION_DOWN) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                f22_y -= speed;
                                handler.postDelayed(this, 20);
                                if(f22_y <= Character.f22_center_height) {
                                    f22_y = Character.f22_center_height;
                                }
                            }
                        }, 0, 20);
                    }
                } else if (action == MotionEvent.ACTION_UP) {
                    if (handler != null) {
                        handler.removeMessages(0);
                    }
                }
            }
            if (id == R.id.ivDown) {
                if (action == MotionEvent.ACTION_DOWN) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                f22_y += speed;
                                handler.postDelayed(this, 20);
                                if(f22_y >= layoutHeight-Character.f22_center_height) {
                                    f22_y = layoutHeight-Character.f22_center_height;
                                }
                            }
                        }, 0, 20);
                    }
                } else if (action == MotionEvent.ACTION_UP) {
                    if (handler != null) {
                        handler.removeMessages(0);
                    }
                }
            }
            if (id == R.id.ivLeft) {
                if (action == MotionEvent.ACTION_DOWN) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Character.f22 = BitmapFactory.decodeResource(getResources(), R.drawable.f22_left);
                                f22_x -= speed;
                                handler.postDelayed(this, 20);
                                if(f22_x <= Character.f22_center_width) {
                                    f22_x = Character.f22_center_width;
                                }
                            }
                        }, 0, 20);
                    }
                } else if (action == MotionEvent.ACTION_UP) {
                    if (handler != null) {
                        Character.f22 = BitmapFactory.decodeResource(getResources(), R.drawable.f22_top);
                        handler.removeMessages(0);
                    }
                }
            }
            if (id == R.id.ivRight) {
                if (action == MotionEvent.ACTION_DOWN) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Character.f22 = BitmapFactory.decodeResource(getResources(), R.drawable.f22_right);
                                f22_x += speed;
                                handler.postDelayed(this, 20);
                                if(f22_x >= layoutWidth-Character.f22_center_width) {
                                    f22_x = layoutWidth-Character.f22_center_width;
                                }
                            }
                        }, 0, 20);
                    }
                } else if (action == MotionEvent.ACTION_UP) {
                    if (handler != null) {
                        Character.f22 = BitmapFactory.decodeResource(getResources(), R.drawable.f22_top);
                        handler.removeMessages(0);
                    }
                }
            }
//            if (id == R.id.ib_menu_button) {
//
//            }
            if (id == R.id.ib_fire_button) {
                if (action == MotionEvent.ACTION_DOWN) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                isFire = true; //발사 상태로 전환
                                media_player_bullet_shoot.start(); //사운드 플레이

                                int_f22_x = (int) f22_x;
                                int_f22_y = (int) f22_y;
                                int_f22_width = (int) Character.f22_width;

                                //총알 객체 생성
                                Bullet bs = new Bullet(int_f22_x + int_f22_width / 2, int_f22_y);
                                Log.d("값체크","bs.bullet_x : "+bs.bullet_x);
                                Log.d("값체크","bs.bullet_y : "+bs.bullet_y);
                                //bullet_list.add(bs); //리스트에 추가
                            }
                        }, 0, 20);
                    }
                } else if (action == MotionEvent.ACTION_UP) {
                    if (handler != null) {
                        handler.removeMessages(0);
                    }
                }
            }
            return false;
        }
    };
    // 총알 클래스
    public class Bullet {

        private int bullet_x;
        private int bullet_y;

        public Bullet(int bullet_x, int bullet_y) {
            this.bullet_x = bullet_x;
            this.bullet_y = bullet_y;
        }
        public int getBullet_y() {
            return bullet_y;
        }
        public void setBullet_y(int bullet_y) {
            this.bullet_y = bullet_y;
        }
        public int getBullet_x() {
            return bullet_x;
        }
        public void setBullet_x(int bullet_x) {
            this.bullet_x = bullet_x;
        }
        @Override
        public String toString() {
            return "Bullet{"+"bullet_x="+bullet_x+", bullet_y="+bullet_y+'}';
        }
    }
    //내부 클래스
    class MyView extends View implements Runnable {
//        Drawable f22_bullet; //총알이미지

        //생성자
        public MyView(Context context) {
            super(context);

//            f22_bullet = getResources().getDrawable(R.drawable.f22_bullet, null);

            //리스트 생성
            bullet_list = new ArrayList<>();
            //백그라운드 스레드 생성
            Thread th = new Thread(this);
            th.start();
        }

        @Override
        protected void onDraw(Canvas canvas) { // 비행기 이미지 캔버스에 그리기로 화면에 이미지 적용
            super.onDraw(canvas);
            //총알 출력
            for (int i = 0; i < bullet_list.size(); i++) {
                Bullet b = bullet_list.get(i); //i번째 총알
                canvas.drawBitmap(f22_bullet, f22_x, f22_y - 50, null); // 총알 해당 좌표에 이미지 출력
                invalidate();
            }
        }
//        @Override
//        protected void onDraw(Canvas canvas) {
//            //총알 출력
//            for (int i = 0; i < bullet_list.size(); i++) {
//                Bullet b = bullet_list.get(i); //i번째 총알
//                //총알 이미지의 출력범위
//                f22_bullet.setBounds(
//                        b.getBullet_x(),
//                        b.getBullet_y(),
//                        b.getBullet_x() + (int) bullet_width,
//                        b.getBullet_y() + (int) bullet_height);
//                f22_bullet.draw(canvas); //총알 이미지 출력
//                //canvas.drawBitmap(Character.f22_bullet, bullet_x - Character.bullet_center_width, bullet_y - 50, null); // 총알 해당 좌표에 이미지 출력
//            }
//        }
        // 총알 발사상태 전환 (자동으로 바꿔보자)
//        @Override
//        public boolean onTouchEvent(MotionEvent event) {
//
//            isFire = true; //발사 상태로 전환
//            media_player_bullet_shoot.start(); //사운드 플레이
//
//            int_f22_x = (int) f22_x;
//            int_f22_y = (int) f22_y;
//            int_f22_width = (int) Character.f22_width;
//
//            //총알 객체 생성
//            Bullet bs = new Bullet(int_f22_x + int_f22_width / 2, int_f22_y - 50);
//            bullet_list.add(bs); //리스트에 추가
//            return super.onTouchEvent(event);
//        }

        @Override
        public void run() {
            //적 좌표
            while (true){
                enemy_x -= 10;
                if(enemy_x < 0){
                    enemy_x = layoutWidth - Enemy.enemy_width;
                }
                Log.d("값체크","enemy_x"+enemy_x);
                //총알 좌표
                for(int i=0;  i<bullet_list.size(); i++){
                    Bullet b=bullet_list.get(i);//i번째 총알
                    b.setBullet_y(b.getBullet_y()-5); //y좌표 감소 처리
                    if(b.getBullet_y() <0){
                        bullet_list.remove(i); //리스트에서 제거
                    }
                    //충돌여부 판정
                    //적의 사각영역
                    Rect rect1 = new Rect((int) enemy_x, (int) enemy_y, (int) enemy_x + (int) Enemy.enemy_width, (int) enemy_y + (int) Enemy.enemy_height);
                    //총알 사각영역
                    Rect rect2 = new Rect(b.getBullet_x(), b.getBullet_y(),
                            b.getBullet_x()+(int) bullet_width, b.getBullet_y()+(int)bullet_height);

                    if(rect1.intersect(rect2)){ //겹친 부분이 있으면
                        media_player_hit_explosive.start(); //폭발음 플레이
                        isHit = true; // 폭발 상태로 변경
                        //point += 100; //점수 증가
//                        hx = ex;    //폭발한 좌표 저장
//                        hy = ey;
                        bullet_list.remove(i); //총알 리스트에서 제거
                        enemy_x = layoutWidth - Enemy.enemy_width; //적 좌표 초기화
                    }
                }
                try {
                    Thread.sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                postInvalidate();//그래픽 갱신 요청 => 다시 onDraw() 호출됨
            }
        }
    }


}


