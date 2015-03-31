package com.knowyour.numbers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class Demo extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_demo);

        findViewById(R.id.number_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                v.getContext().startActivity(new Intent(v.getContext(), NumberView.class));
            }
        });
    }

}
