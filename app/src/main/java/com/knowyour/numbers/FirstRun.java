package com.knowyour.numbers;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.TextView;

public class FirstRun extends Activity {
    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_first_run);


        final TelephonyManager tMgr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        final String number = PhoneNumberUtils.formatNumber(tMgr.getLine1Number());
        ((TextView)findViewById(R.id.first_run_phone_number)).setText(number);

        findViewById(android.R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO: Positive choice
            }
        });

        findViewById(android.R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO: Negative choice
            }
        });

    }
}
