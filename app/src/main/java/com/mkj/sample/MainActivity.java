package com.mkj.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    LinearLayout layout_logo,                  // 로고 자체 레이아웃
                 layout_intro_spin_plane,      // 인트로 비행기 레이아웃
                 layout_intro_gate_background, // 게이트 선 가려주는 검은 백그라운드 레이아웃
                 layout_intro_gate,            // 게이트 전체 레이아웃
                 layout_touch_to_start,        // 터치 투 스타트 레이아웃
                 layout_title,                 // 메인메뉴 레이아웃
                 layout_main_menu_background,  // 메인메뉴 백그라운드 레이아웃
                 layout_main_menu_background2, // 메인메뉴 백그라운드 레이아웃 2
                 layout_main_menu_background3, // 메인메뉴 백그라운드 레이아웃 3
                 layout_open_cloud,            // 오프닝 상승구름 레이아웃
                 layout_open_cloud_2,          // 오프닝 상승구름 레이아웃 2
                 layout_open_cloud_3,          // 오프닝 상승구름 레이아웃 3
                 layout_menu_select_1,         // 게임 스코어 버튼 레이아웃
                 layout_menu_select_2,         // 게임 1 버튼 레이아웃
                 layout_menu_select_3,         // 게임 2 버튼 레이아웃
                 layout_menu_select_4;         // 게임 옵션 버튼 레이아웃

    ImageView iv_Logo,                    // 로고 이미지뷰
              iv_spin_plane_jet,          // 인트로 비행기 이미지뷰
              iv_intro_gate_left,         // 인트로 게이트 좌측문 이미지뷰
              iv_intro_gate_right,        // 인트로 게이트 우측문 이미지뷰
              iv_main_menu_background,    // 메인메뉴 백그라운드 무한루프용 이미지뷰
              iv_main_menu_background2,   // 메인메뉴 백그라운드 무한루프용 이미지뷰 2
              iv_main_menu_background3,   // 메인메뉴 백그라운드 무한루프용 이미지뷰 3
              iv_cloud_open,              // 오프닝 상승구름 무한루프용 이미지뷰
              iv_cloud_open_2,            // 오프닝 상승구름 무한루프용 이미지뷰 2
              iv_cloud_open_3,            // 오프닝 상승구름 무한루프용 이미지뷰 3
              iv_topgun_maverick_mustang, // 메뉴 탑건 매버릭 배경
              iv_f22_flying_move;         // 백그라운드 f22 무빙 이미지뷰

    Animation logo_alpha,                       // 로고 알파 애니메이션
              intro_spin_plane_jet,             // 인트로 비행기 올라가는 애니메이션
              intro_gate_background,            // 인트로 게이트 선 가려주는 검은 백그라운드 애니메이션
              intro_gate_left,                  // 인트로 게이트 좌측문 애니메이션
              intro_gate_right,                 // 인트로 게이트 우측문 애니메이션
              touch_to_start,                   // 터치 투 스타트 깜빡이는 애니메이션
              ani_main_menu_background,         // 메인메뉴 백그라운드 무한루프용 애니메이션
              ani_main_menu_background2,        // 메인메뉴 백그라운드 무한루프용 애니메이션 2
              ani_main_menu_background3,        // 메인메뉴 백그라운드 무한루프용 애니메이션 3
              ani_layout_title,                 // 메인메뉴 레이아웃용 애니메이션
              ani_layout_main_menu_background,  // 메인메뉴 백그라운드 레이아웃용 애니메이션
              ani_layout_main_menu_background2, // 메인메뉴 백그라운드 레이아웃용 애니메이션 2
              ani_layout_main_menu_background3, // 메인메뉴 백그라운드 레이아웃용 애니메이션 3
              ani_layout_open_cloud,            // 오프닝 상승구름 레이아웃용 애니메이션
              ani_layout_open_cloud_2,          // 오프닝 상승구름 레이아웃용 애니메이션 2
              ani_layout_open_cloud_3,          // 오프닝 상승구름 레이아웃용 애니메이션 3
              ani_topgun_maverick_mustang,      // 메뉴 탑건 매버릭 배경 애니메이션
              ani_f22_flying_move;              // 백그라운드 f22 무빙 애니메이션

    ImageButton ib_touch_to_start;        // 터치 투 스타트 이미지버튼
    ImageButton ib_menu_select_button_1;  // 게임 스코어 이미지버튼
    ImageButton ib_menu_select_button_2;  // 게임 1 버튼 레이아웃
    ImageButton ib_menu_select_button_3;  // 게임 2 버튼 레이아웃
    ImageButton ib_menu_select_button_4;  // 게임 옵션 버튼 레이아웃

    MediaPlayer media_player_logo,           // 짹짹 대는 로고 오디오
                media_player_intro,          // 인트로 비행기 오디오
                media_player_start_menu,     // 메인메뉴 오디오
                media_player_touch_to_start; // 터치 투 스타트 오디오
    public static MediaPlayer media_player_start_menu_2;   // 메인메뉴 오디오 2

    private long back_key_press_time = 0; // 뒤로 버튼을 누른 시간

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 레이아웃 모음
        layout_logo = (LinearLayout)findViewById(R.id.layout_logo);
        layout_intro_spin_plane = (LinearLayout)findViewById(R.id.layout_intro_spin_plane);
        layout_intro_gate_background = (LinearLayout)findViewById(R.id.layout_intro_gate_background);
        layout_intro_gate = (LinearLayout)findViewById(R.id.layout_intro_gate);
        layout_touch_to_start = (LinearLayout)findViewById(R.id.layout_touch_to_start);
        layout_title = (LinearLayout)findViewById(R.id.layout_title);
        layout_main_menu_background = (LinearLayout)findViewById(R.id.layout_main_menu_background);
        layout_main_menu_background2 = (LinearLayout)findViewById(R.id.layout_main_menu_background2);
        layout_main_menu_background3 = (LinearLayout)findViewById(R.id.layout_main_menu_background3);
        layout_open_cloud = (LinearLayout)findViewById(R.id.layout_open_cloud);
        layout_open_cloud_2 = (LinearLayout)findViewById(R.id.layout_open_cloud_2);
        layout_open_cloud_3 = (LinearLayout)findViewById(R.id.layout_open_cloud_3);
        layout_menu_select_1 = (LinearLayout)findViewById(R.id.layout_menu_select_1);
        layout_menu_select_2 = (LinearLayout)findViewById(R.id.layout_menu_select_2);
        layout_menu_select_3 = (LinearLayout)findViewById(R.id.layout_menu_select_3);
        layout_menu_select_4 = (LinearLayout)findViewById(R.id.layout_menu_select_4);

        // 이미지뷰 모음
        iv_Logo = (ImageView) findViewById(R.id.iv_logo);
        iv_spin_plane_jet = (ImageView) findViewById(R.id.iv_spin_plane_jet);
        iv_intro_gate_left = (ImageView) findViewById(R.id.iv_intro_gate_left);
        iv_intro_gate_right = (ImageView) findViewById(R.id.iv_intro_gate_right);
        iv_main_menu_background = (ImageView) findViewById(R.id.iv_main_menu_background);
        iv_main_menu_background2 = (ImageView) findViewById(R.id.iv_main_menu_background2);
        iv_main_menu_background3 = (ImageView) findViewById(R.id.iv_main_menu_background3);
        iv_cloud_open = (ImageView) findViewById(R.id.iv_cloud_open);
        iv_cloud_open_2 = (ImageView) findViewById(R.id.iv_cloud_open_2);
        iv_cloud_open_3 = (ImageView) findViewById(R.id.iv_cloud_open_3);
        iv_f22_flying_move = (ImageView) findViewById(R.id.iv_f22_flying);
        iv_topgun_maverick_mustang = (ImageView) findViewById(R.id.iv_topgun_maverick_mustang);

        // 이미지버튼 모음
        ib_touch_to_start = (ImageButton) findViewById(R.id.ib_touch_to_start);
        ib_menu_select_button_1 = (ImageButton) findViewById(R.id.ib_menu_select_button_1);
        ib_menu_select_button_2 = (ImageButton) findViewById(R.id.ib_menu_select_button_2);
        ib_menu_select_button_3 = (ImageButton) findViewById(R.id.ib_menu_select_button_3);
        ib_menu_select_button_4 = (ImageButton) findViewById(R.id.ib_menu_select_button_4);

        // gif용 글라이드
        Glide.with(MainActivity.this).load(R.drawable.project_logo).into(iv_Logo);
        Glide.with(MainActivity.this).load(R.drawable.spin_plane_jet).into(iv_spin_plane_jet);

        // 애니메이션 모음
        logo_alpha = AnimationUtils.loadAnimation(MainActivity.this, R.anim.logo_alpha);
        intro_spin_plane_jet = AnimationUtils.loadAnimation(MainActivity.this, R.anim.intro_spin_plane_jet_translate);
        intro_gate_background = AnimationUtils.loadAnimation(MainActivity.this, R.anim.intro_gate_background_translate);
        intro_gate_left = AnimationUtils.loadAnimation(MainActivity.this, R.anim.intro_gate_left);
        intro_gate_right = AnimationUtils.loadAnimation(MainActivity.this, R.anim.intro_gate_right);
        touch_to_start = AnimationUtils.loadAnimation(MainActivity.this, R.anim.touch_to_start_blink);
        ani_main_menu_background = AnimationUtils.loadAnimation(MainActivity.this, R.anim.background_main_menu);
        ani_main_menu_background2 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.background_main_menu2);
        ani_main_menu_background3 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.background_main_menu3);
        ani_layout_title = AnimationUtils.loadAnimation(MainActivity.this, R.anim.layout_title_up);
        ani_layout_main_menu_background = AnimationUtils.loadAnimation(MainActivity.this, R.anim.layout_title_up);
        ani_layout_main_menu_background2 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.layout_title_up);
        ani_layout_main_menu_background3 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.layout_title_up);
        ani_layout_open_cloud = AnimationUtils.loadAnimation(MainActivity.this, R.anim.open_cloud_move);
        ani_layout_open_cloud_2 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.open_cloud_move);
        ani_layout_open_cloud_3 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.open_cloud_move);
        ani_f22_flying_move = AnimationUtils.loadAnimation(MainActivity.this, R.anim.f22_flying_move);
        ani_topgun_maverick_mustang = AnimationUtils.loadAnimation(MainActivity.this, R.anim.topgun_maverick_mustang);

        // 애니메이션 시작 모음
        iv_Logo.startAnimation(logo_alpha);
        iv_spin_plane_jet.startAnimation(intro_spin_plane_jet);
        layout_intro_gate_background.startAnimation(intro_gate_background);
        iv_intro_gate_left.startAnimation(intro_gate_left);
        iv_intro_gate_right.startAnimation(intro_gate_right);
        ib_touch_to_start.startAnimation(touch_to_start);
        iv_main_menu_background.startAnimation(ani_main_menu_background);
        iv_main_menu_background2.startAnimation(ani_main_menu_background2);
        iv_main_menu_background3.startAnimation(ani_main_menu_background3);
        iv_cloud_open.startAnimation(ani_main_menu_background);
        iv_cloud_open_2.startAnimation(ani_main_menu_background2);
        iv_cloud_open_3.startAnimation(ani_main_menu_background3);
        iv_f22_flying_move.startAnimation(ani_f22_flying_move);

        // 온클릭리스너 모음
        findViewById(R.id.ib_touch_to_start).setOnClickListener(mClickListener);
        findViewById(R.id.ib_menu_select_button_1).setOnClickListener(mClickListener);
        findViewById(R.id.ib_menu_select_button_2).setOnClickListener(mClickListener);
        findViewById(R.id.ib_menu_select_button_3).setOnClickListener(mClickListener);
        findViewById(R.id.ib_menu_select_button_4).setOnClickListener(mClickListener);
        // 레이아웃을 터치하면 스킵하기 위한 클릭리스너 할당
//        findViewById(R.id.layout_logo).setOnClickListener(mClickListener);
//        findViewById(R.id.layout_intro_spin_plane).setOnClickListener(mClickListener);
//        findViewById(R.id.layout_intro_gate).setOnClickListener(mClickListener);

        // 오디오 연결 모음
        media_player_logo = MediaPlayer.create(MainActivity.this, R.raw.logo_bird);
        media_player_intro = MediaPlayer.create(MainActivity.this, R.raw.intro_spin_plane);
        media_player_start_menu = MediaPlayer.create(MainActivity.this, R.raw.main_menu_01_devils_tower_arrange_version);
        media_player_touch_to_start = MediaPlayer.create(MainActivity.this, R.raw.touch_to_start);

        media_player_logo.start(); // 짹짹 대는 로고 오디오 시작

        new Handler().postDelayed(new Runnable() { // 로고 전체 레이아웃 없애는 핸들러
            @Override
            public void run() {
                media_player_intro.start();           // 인트로 비행기 오디오 시작
                layout_logo.setVisibility(View.GONE); // 로고 전체 레이아웃 없앰
            }
        }, 3000);
        new Handler().postDelayed(new Runnable() { // 게이트 백그라운드 레이아웃, 인트로 비행기 레이아웃 없애는 핸들러
            @Override
            public void run() {
                media_player_start_menu.start();                       // 메인메뉴 오디오 시작
                media_player_start_menu.setLooping(true);              // 메인메뉴 오디오 무한루프
                layout_intro_gate_background.setVisibility(View.GONE); // 게이트 백그라운드 레이아웃 없앰
                layout_intro_spin_plane.setVisibility(View.GONE);      // 인트로 비행기 레이아웃 없앰
            }
        }, 8000);
        new Handler().postDelayed(new Runnable() { // 게이트 레이아웃은 없애고, 터치 투 스타트 레이아웃 보이는 핸들러
            @Override
            public void run() {
                layout_intro_gate.setVisibility(View.GONE);        // 게이트 전체 레이아웃 없앰
                layout_touch_to_start.setVisibility(View.VISIBLE); // 터치 투 스타트 레이아웃 보임
            }
        }, 11000);
    }
    // 온클릭리스너 모음
    View.OnClickListener mClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ib_touch_to_start:                   // 터치 투 스타트를 누르면 작동
                        media_player_logo.release();           // 짹짹 대는 로고 오디오 해제
                        media_player_logo = null;              // 짹짹 대는 로고 오디오 무효화
                        media_player_intro.release();          // 인트로 비행기 오디오 해제
                        media_player_intro = null;             // 인트로 비행기 오디오 무효화
                        media_player_start_menu.release();     // 메인메뉴 오디오 해제
                        media_player_start_menu = null;        // 메인메뉴 오디오 무효화
                        media_player_touch_to_start.start();   // 터치 투 스타트 오디오 시작
                    new Handler().postDelayed(new Runnable() { // 게이트 레이아웃 없앰, 터치 투 스타트 레이아웃 보임
                        @Override
                        public void run() {
                            layout_title.startAnimation(ani_layout_title);                                 // 메인메뉴 전체 레이아웃 올리는 애니메이션
                            layout_main_menu_background.startAnimation(ani_layout_main_menu_background);   // 메인메뉴 백그라운드 자체 레이아웃 올리는 애니메이션 1
                            layout_main_menu_background2.startAnimation(ani_layout_main_menu_background2); // 메인메뉴 백그라운드 자체 레이아웃 올리는 애니메이션 2
                            layout_main_menu_background3.startAnimation(ani_layout_main_menu_background3); // 메인메뉴 백그라운드 자체 레이아웃 올리는 애니메이션 3
                            layout_open_cloud.startAnimation(ani_layout_open_cloud);                       // 오프닝 상승구름 애니메이션 1
                            layout_open_cloud_2.startAnimation(ani_layout_open_cloud_2);                   // 오프닝 상승구름 애니메이션 2
                            layout_open_cloud_3.startAnimation(ani_layout_open_cloud_3);                   // 오프닝 상승구름 애니메이션 3
                            layout_touch_to_start.setVisibility(View.INVISIBLE);                           // 터치 투 스타트 레이아웃 안보임
                            iv_topgun_maverick_mustang.startAnimation(ani_topgun_maverick_mustang);
                        }
                    }, 1000);
                    new Handler().postDelayed(new Runnable() { // 메인메뉴, 구름 레이아웃 없앰, 서브메뉴 레이아웃 보임
                        @Override
                        public void run() {
                            media_player_start_menu_2 = MediaPlayer.create(MainActivity.this, R.raw.main_menu_02_in_the_name_of_strikers);
                            media_player_touch_to_start.release();                 // 터치 투 스타트 오디오 해제
                            media_player_touch_to_start = null;                    // 터치 투 스타트 오디오 무효화
                            media_player_start_menu_2.start();                     // 메인메뉴 오디오 시작 2
                            media_player_start_menu_2.setLooping(true);            // 메인메뉴 오디오 무한루프 2
                            layout_title.setVisibility(View.GONE);                 // 메인메뉴 전체 레이아웃 없앰
                            layout_main_menu_background.setVisibility(View.GONE);  // 메인메뉴 백그라운드 자체 레이아웃 없앰 1
                            layout_main_menu_background2.setVisibility(View.GONE); // 메인메뉴 백그라운드 전체 레이아웃 없앰 2
                            layout_main_menu_background3.setVisibility(View.GONE); // 메인메뉴 백그라운드 전체 레이아웃 없앰 3
                            layout_open_cloud.setVisibility(View.GONE);            // 오프닝 상승구름 전체 레이아웃 없앰 1
                            layout_open_cloud_2.setVisibility(View.GONE);          // 오프닝 상승구름 전체 레이아웃 없앰 2
                            layout_open_cloud_3.setVisibility(View.GONE);          // 오프닝 상승구름 전체 레이아웃 없앰 3
                        }
                    }, 3000);
                    break;
                case R.id.ib_menu_select_button_1:       // 스코어 버튼을 누르면 작동
                    Intent intent = new Intent(MainActivity.this, Score.class);
                    startActivity(intent); // 스코어 액티비티 시작
                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                    break;
                case R.id.ib_menu_select_button_2:       // 게임 1 버튼을 누르면 작동
                    if(PlayActivity.media_player_start_menu_2 != null &&  PlayActivity.media_player_start_menu_2.isPlaying()) {
                        PlayActivity.media_player_start_menu_2.release();
                    } else if(PlayActivity2.media_player_start_menu_2 != null &&  PlayActivity2.media_player_start_menu_2.isPlaying()) {
                        PlayActivity2.media_player_start_menu_2.release();
                    } else if(media_player_start_menu_2!= null &&  media_player_start_menu_2.isPlaying()) {
                        media_player_start_menu_2.release(); // 메인메뉴 오디오 해제 2
                    } else {
                        return;
                    }
                    Intent intent2 = new Intent(MainActivity.this, PlayActivity.class);
                    startActivity(intent2); // 플레이 액티비티 시작
                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                    break;
                case R.id.ib_menu_select_button_3:       // 게임 2 버튼을 누르면 작동
                    if(PlayActivity.media_player_start_menu_2 != null &&  PlayActivity.media_player_start_menu_2.isPlaying()) {
                        PlayActivity.media_player_start_menu_2.release();
                    }else if(PlayActivity2.media_player_start_menu_2 != null &&  PlayActivity2.media_player_start_menu_2.isPlaying()) {
                        PlayActivity2.media_player_start_menu_2.release();
                    } else if(media_player_start_menu_2!= null &&  media_player_start_menu_2.isPlaying()) {
                        media_player_start_menu_2.release(); // 메인메뉴 오디오 해제 2
                    } else {
                        return;
                    }
                    Intent intent3 = new Intent(MainActivity.this, PlayActivity2.class);
                    startActivity(intent3); // 플레이 액티비티2 시작
                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                    break;
                case R.id.ib_menu_select_button_4:       // 스코어인풋 버튼을 누르면 작동

                    break;

            }
        }
    };
    // 뒤로 버튼을 눌러서 오디오와 액티비티 바로 종료
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() <= back_key_press_time + 2000) { // 뒤로 버튼을 눌렀을때 2초보다 길지 않으면 종료
            //super.onBackPressed();               // 뒤로가기 버튼 기능 살리는 역할
            media_player_logo.release();           // 짹짹 대는 로고 오디오 해제
            media_player_logo = null;              // 짹짹 대는 로고 오디오 무효화
            media_player_intro.release();          // 인트로 비행기 오디오 해제
            media_player_intro = null;             // 인트로 비행기 오디오 무효화
            media_player_start_menu.release();     // 메인메뉴 오디오 해제
            media_player_start_menu = null;        // 메인메뉴 오디오 무효화
            media_player_start_menu_2.release();   // 메인메뉴 오디오 해제 2
            media_player_start_menu_2 = null;      // 메인메뉴 오디오 무효화 2
            media_player_touch_to_start.release(); // 터치 투 스타트 오디오 해제
            media_player_touch_to_start = null;    // 터치 투 스타트 오디오 무효화
            finish();
        } else if (System.currentTimeMillis() > back_key_press_time + 2000) { // 뒤로 버튼을 처음 눌렀거나 2초보다 길면 다시 토스트 띄움
            back_key_press_time = System.currentTimeMillis();
            Toast.makeText(getApplicationContext(), "한번 더 누르면 종료됩니다", Toast.LENGTH_SHORT).show();
        }
    }
//    //오디오 예제들
//    //Star() + Stop() 예제
//        button.setOnClickListener(new View.OnClickListener(){
//        @Override
//        public void onClick(View view) {
//            // 플레이 중이라면
//            if(mediaPlayer != null &&  mediaPlayer.isPlaying()) {
//                mediaPlayer.stop();
//                mediaPlayer.release();
//                mediaPlayer = null;
//
//                // reset() 함수만으로도 동작한다.
//                //mediaPlayer.reset();
//                button.setText("재생 하기");
//            } else { // 미디어 리소스를 생성하고 플레이 시킨다.
//                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.car);
//                mediaPlayer.start();
//
//                // 반복재생하기
//                mediaPlayer.setLooping(true);
//                button.setText("종료 하기");
//            }
//        }
//    });
//    //Star() + Pause() 예제
//        button2.setOnClickListener(new View.OnClickListener(){
//        @Override
//        public void onClick(View view) {
//            if(mediaPlayer != null  ) {
//                //플레이 중이면 일시정지
//                if (mediaPlayer.isPlaying()) {
//                    mediaPlayer.pause();
//                    button2.setText("재생 하기");
//                } else // 미디어 리소스를 생성하고 플레이 시킨다.
//                {
//                    mediaPlayer.start();
//                    mediaPlayer.setLooping(true);
//                    button2.setText("일시 정지 하기");
//                }
//            }
//        }
//    });
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        // mediaPlayer 리소스를 해제 해준다.
//        if(mediaPlayer != null){
//            mediaPlayer.release();
//            mediaPlayer = null;
//        }
//    }
}
