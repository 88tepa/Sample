package com.mkj.sample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class Enemy extends View {

    public static Bitmap enemy;                                  // 불러온 적 비행기 이미지 비트맵
    public static float enemy_width, enemy_height,               // 적 비행기 이미지의 너비와 높이
                        enemy_center_width, enemy_center_height; // 적 비행기 이미지의 너비와 높이 절반

    public Enemy(Context context, AttributeSet attrs) {
        super(context, attrs);

        enemy = BitmapFactory.decodeResource(getResources(), R.drawable.enemy); // 비트맵 팩토리로 비트맵 이미지 적용 (정상상태 비행기)
        enemy_width = enemy.getWidth();         // 적 비행기 이미지의 너비
        enemy_height = enemy.getHeight();       // 적 비행기 이미지의 높이
        enemy_center_width = enemy_width / 2;   // 적 비행기 이미지의 너비 절반
        enemy_center_height = enemy_height / 2; // 적 비행기 이미지의 높이 절반
    }
    @Override
    protected void onDraw(Canvas canvas) { // 적 비행기 이미지 캔버스에 그리기로 화면에 이미지 적용
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        canvas.drawBitmap(enemy, PlayActivity.enemy_x-enemy_center_width, PlayActivity.enemy_y-enemy_center_height, null);  // 적 비행기 해당 좌표에 이미지 출력
        invalidate();
    }
}