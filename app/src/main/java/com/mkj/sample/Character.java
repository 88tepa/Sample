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

public class Character extends View {

    public static Bitmap f22;                                // 불러온 비행기 이미지 비트맵
    public static float f22_width, f22_height,               // 비행기 이미지의 너비와 높이
                        f22_center_width, f22_center_height; // 비행기 이미지의 너비와 높이 절반

    public Character(Context context, AttributeSet attrs) {
        super(context, attrs);

        f22 = BitmapFactory.decodeResource(getResources(), R.drawable.f22_top); // 비트맵 팩토리로 비트맵 이미지 적용 (정상상태 비행기)
        f22_width = f22.getWidth();         // 비행기 이미지의 너비
        f22_height = f22.getHeight();       // 비행기 이미지의 높이
        f22_center_width = f22_width / 2;   // 비행기 이미지의 너비 절반
        f22_center_height = f22_height / 2; // 비행기 이미지의 높이 절반
    }
    @Override
    protected void onDraw(Canvas canvas) { // 비행기 이미지 캔버스에 그리기로 화면에 이미지 적용
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        canvas.drawBitmap(f22, PlayActivity.f22_x-f22_center_width, PlayActivity.f22_y-f22_center_height, null);  // 비행기 해당 좌표에 이미지 출력
        invalidate();
    }
}