package com.knowyour.numbers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.gesture.GestureOverlayView;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class NumberView extends Activity {

    private boolean mInitialized;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_view);

        int[] textViews = new int[] {
                R.id.number0,
                R.id.number1,
                R.id.number2,
                R.id.number3,
                R.id.number4,
                R.id.number5,
                R.id.number6,
                R.id.number7,
                R.id.number8,
                R.id.number9,
                R.id.number10,
                R.id.number11,
                R.id.number12,
                R.id.number13
        };

        final View.OnGenericMotionListener onGenericMotionListener = new View.OnGenericMotionListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public boolean onGenericMotion(final View v, final MotionEvent event) {
                final int color;
                if (event.getAction() == MotionEvent.ACTION_HOVER_ENTER) {
                    color = Color.parseColor("#FFFF00");
                } else  if (event.getAction() == MotionEvent.ACTION_HOVER_EXIT) {
                    color = Color.parseColor("#00FFFF");
                } else {
                    return false;
                }

                final TextView v1 = (TextView) v;
                final Activity context = (Activity) v.getContext();
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        v1.setBackgroundColor(color);
                        v1.invalidate();
                    }
                });
                return true;
            }
        };

        for (final int textView : textViews) {
            findViewById(textView).setOnGenericMotionListener(onGenericMotionListener);
        }

        final GestureOverlayView gestureOverlayView = (GestureOverlayView)findViewById(R.id.gestureOverlayView);
        gestureOverlayView.setVisibility(View.GONE);
    }
}
