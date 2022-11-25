package com.mkj.sample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.List;

public class Bullet extends View {

    public static Bitmap f22_bullet;                               // 불러온 총알 이미지 비트맵
    public static float bullet_width, bullet_height,               // 총알 이미지의 너비와 높이
                        bullet_center_width, bullet_center_height; // 총알 이미지의 너비와 높이 절반

    public Bullet(Context context, AttributeSet attrs) {
        super(context, attrs);

        f22_bullet = BitmapFactory.decodeResource(getResources(), R.drawable.f22_bullet); // 비트맵 팩토리로 비트맵 이미지 적용 (탄알)
        bullet_width = f22_bullet.getWidth();     // 총알 이미지의 너비
        bullet_height = f22_bullet.getHeight();   // 총알 이미지의 높이
        bullet_center_width = bullet_width / 2;   // 총알 이미지의 너비 절반
        bullet_center_height = bullet_height / 2; // 총알 이미지의 높이 절반
    }
//    @Override
//    protected void onDraw(Canvas canvas) { // 비행기 이미지 캔버스에 그리기로 화면에 이미지 적용
//        // TODO Auto-generated method stub
//        super.onDraw(canvas);
//        canvas.drawBitmap(f22_bullet, PlayActivity.bullet_x-bullet_center_width, PlayActivity.bullet_y - 50, null); // 총알 해당 좌표에 이미지 출력
//        invalidate();
//    }
}