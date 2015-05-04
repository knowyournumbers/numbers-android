package com.knowyour.numbers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;

public class FirstRun extends BasicWizardRunner {

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        mWizardModel = new FirstRunModel(this);
        super.onCreate(savedInstanceState);

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPager.getCurrentItem() == mCurrentPageSequence.size()) {
                    final DialogFragment dg = new DialogFragment() {
                        @Override
                        public Dialog onCreateDialog(final Bundle savedInstanceState) {
                            return new AlertDialog.Builder(getActivity())
                                .setMessage(R.string.submit_confirm_message)
                                .setPositiveButton(
                                    R.string.submit_confirm_button, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(final DialogInterface dialog, final int which) {
                                            final Intent intent = new Intent(getActivity(), NumberView.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            getActivity().startActivity(intent);
                                        }
                                    })
                                .setNegativeButton(android.R.string.cancel, null)
                                .create();
                        }
                    };
                    dg.show(getSupportFragmentManager(), "place_order_dialog");
                } else {
                    if (mEditingAfterReview) {
                        mPager.setCurrentItem(mPagerAdapter.getCount() - 1);
                    } else {
                        mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                    }
                }
            }
        });

    }
}
