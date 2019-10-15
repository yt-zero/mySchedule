package com.example.myschedule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));
    }

    public class MyView extends View {

        public MyView(Context context) {
            super(context);
            //int x = 100;
            //int y = 100;
            //int sideLength = 200;
        }
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Paint paint=new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(3);
            paint.setColor(Color.BLACK);
            Period.loadPeriods();
            //Period.drawPeriods(canvas);
            Period.drawTodayP(canvas);
        }
    }
}